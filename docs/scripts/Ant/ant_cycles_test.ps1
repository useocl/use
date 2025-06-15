# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# JAVA_HOME will need to be set to Java 14 / 8
param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

#########################################
# Global variables start here #
#########################################

$ErrorActionPreference = "Stop"
# Set paths based on parameters or use defaults
$ORIGINAL_USE_DIR = if ($OriginalUseDir -ne "") { 
    $OriginalUseDir 
} else { 
    Join-Path (Get-Location) "use" 
}

# Validate that the original use directory exists
if (-not (Test-Path $ORIGINAL_USE_DIR)) {
    Write-Host "Error: Original USE directory not found at: $ORIGINAL_USE_DIR"
    Write-Host "Please specify the correct path using -OriginalUseDir parameter"
    exit 1
}

$RESULTS_DIR = if ($ResultsDir -ne "") { 
    $ResultsDir 
} else { 
    Get-Location 
}

# Create results directory if it doesn't exist
if (-not (Test-Path $RESULTS_DIR)) {
    New-Item -ItemType Directory -Force -Path $RESULTS_DIR | Out-Null
}

$TEMP_DIR = Join-Path $RESULTS_DIR "USE_TEMP_$(Get-Random)"
$RESULTS_FILE = Join-Path $RESULTS_DIR "ant_cycles_test_results.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "ant_cycles_test_log.txt"
$RELEVANT_PATHS = @(("src/main"))
$TEST_FILE_PATH = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture\AntCyclicDependenciesCoreTest.java"
$PROPERTIES_FILE_PATH = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"

$ARCHUNIT_JUNIT_PATH = ""
$ARCHUNIT_CORE_PATH = ""
$SLF4J_API_PATH = ""
$SLF4J_SIMPLE_PATH = ""

#########################################
# Functions start here #
#########################################

#########################################
# Util Functions #
#########################################

function Log-Message {
    param([string]$message)
    Write-Host $message
    $message | Out-File -FilePath $LOG_FILE -Append
}

# Create header line in CSV file if it doesn't exist
function Initialize-Results-File {
    if (-not (Test-Path $RESULTS_FILE)) {
        $header = "date,time,commit,all_modules,analysis,api,config,gen,graph,main,parser,uml,util"
        $header | Out-File -FilePath $RESULTS_FILE -Encoding UTF8
        Log-Message "Created results file with header"
    }
}

function Record-Result {
    param(
        [string]$CommitHash,
        [hashtable]$CycleCounts
    )
    
    $commitDate = git show -s --format=%cd --date=format:"%Y-%m-%d" $CommitHash
    $commitTime = git show -s --format=%cd --date=format:"%H:%M:%S" $CommitHash
    
    $csvLine = "$commitDate,$commitTime,$CommitHash"
    
    # Add metrics in the correct order for CSV
    $metrics = @(
        "all_modules",
        "analysis",
        "api",
        "config",
        "gen",
        "graph",
        "main", 
        "parser",
        "uml",
        "util"
    )
    
    foreach ($metric in $metrics) {
        $value = if ($CycleCounts.ContainsKey($metric)) { $CycleCounts[$metric] } else { 0 }
        $csvLine += ",$value"
    }
    
    $utf8NoBomEncoding = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::AppendAllText($RESULTS_FILE, "$csvLine`r`n", $utf8NoBomEncoding)
    Log-Message "Recorded result for commit $CommitHash with all metrics"
    
    # Save the cycle counts to use for the next commit if needed
    $script:previousCycleCounts = $CycleCounts.Clone()
}

function Has-RelevantChanges {
    param (
        [string]$CommitHash,
        [string]$PreviousCommitHash
    )
    $changedFiles = git diff --name-only $PreviousCommitHash $CommitHash

    foreach ($file in $changedFiles) {
        foreach ($path in $RELEVANT_PATHS) {
            if ($file.StartsWith($path) -and $file.EndsWith(".java")) {
                return $true
            }
        }
    }
    return $false
}

function Remove-BOM-From-New-Test-File {
    param (
        [string]$FilePath
    )
    $full_path = Join-Path $TEMP_DIR $FilePath
    $content = [System.IO.File]::ReadAllBytes($full_path)
    if ($content.Length -ge 3 -and $content[0] -eq 0xEF -and $content[1] -eq 0xBB -and $content[2] -eq 0xBF) {
        $content = $content[3..$content.Length]
        [System.IO.File]::WriteAllBytes($full_path, $content)
        Log-Message "Removed BOM from $full_path"
    }
}

function Parse-CycleResults {
    param(
        [string[]]$OutputLines
    )
    
    $metrics = @{}
    
    # Regex pattern to extract cycle counts
    $pattern = "Number of cycles in (org\.tzi\.use(?:\.\w+)?): (\d+)"
    $patternCore = "Cycles in core module: (\d+)"
    
    foreach ($line in $OutputLines) {
        # Match for core module counts (all_modules)
        if ($line -match $patternCore) {
            $metrics["all_modules"] = [int]$matches[1]
        }
        # Match for specific package counts
        elseif ($line -match $pattern) {
            $package = $matches[1] -replace "org\.tzi\.use\.", ""
            # Handle root package
            if ($package -eq "org.tzi.use") {
                $package = "all_modules"
            }
            $metrics[$package] = [int]$matches[2]
        }
    }
    
    return $metrics
}

#########################################
# Fixing Functions #
#########################################

function Check-And-Fix-Bag-File {
    $bagFile = Get-ChildItem -Recurse -Filter "Bag.java" | Select-Object -First 1

    if ($bagFile) {   
    $content = Get-Content $bagFile.FullName -Raw
    $needsFixing = $content -match 'boolean contains\(T obj\)' -or
                   $content -match 'boolean remove\(T o\)' -or
                   (-not ($content -match '@Override.*boolean contains\(Object obj\)' -or
                         $content -match '@Override.*boolean remove\(Object o\)'))

        if ($needsFixing) {
            Log-Message "Faulty Bag interface - Applying fix..."
            $content = $content -replace 'boolean contains\(T obj\)', 'boolean contains(Object obj)'
            $content = $content -replace 'boolean remove\(T o\)', 'boolean remove(Object o)'
            $content | Set-Content $bagFile.FullName
        }
    }
}

function Check-And-Fix-DiagramView-File {
    $diagramViewFile = Get-ChildItem -Recurse -Filter "DiagramView.java" | Select-Object -First 1

    if ($diagramViewFile) {   
    $content = Get-Content $diagramViewFile.FullName -Raw
    $needsFixing = $content -match 'new OutputFormat\(doc\);'

        if ($needsFixing) {
            Log-Message "Faulty DiagramView file - Applying fix..."
            $content = $content -replace 'new OutputFormat\(doc\);', 'new OutputFormat();'
            $content | Set-Content $diagramViewFile.FullName
        }
    }
}

function Check-And-Fix-ExtendedJTable-File {
    $extendedJTableFile = Get-ChildItem -Recurse -Filter "ExtendedJTable.java" | Select-Object -First 1

    if ($extendedJTableFile) {
        $content = Get-Content $extendedJTableFile.FullName -Raw
        $needsFixing = $content -match 'public ExtendedJTable\(Vector\<\?\> rowData\, Vector\<\?\> columnNames\)'

        if ($needsFixing) {
            Log-Message "Faulty ExtendedJTable constructor - Applying fix..."
            $content = $content -replace 'public ExtendedJTable\(Vector\<\?\> rowData\, Vector\<\?\> columnNames\)', 'public ExtendedJTable(Vector<? extends Vector<?>> rowData, Vector<?> columnNames)'
            $content | Set-Content $extendedJTableFile.FullName
        }
    }
}

function Remove-Id-Tags {
    Get-ChildItem -Recurse -Filter *.java | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        if ($content -match '\$Id\$') {
            $content = $content -replace '\$Id\$', ''
            $content | Set-Content $_.FullName
        }
    }
    if (Test-Path "build.xml") {
        $content = Get-Content "build.xml" -Raw
        if ($content -match '<!--\$Id\$ -->') {
            $content = $content -replace '<!--\$Id\$-->\s*\n', ''
            $content | Set-Content "build.xml"
        }
    }
}

#########################################
# Dependency Functions #
#########################################

function Download-Jar {
    param(
        [string]$GroupId,
        [string]$ArtifactId,
        [string]$Version,
        [string]$TargetDir
    )
    
    $groupPath = $GroupId -replace '\.', '/'
    $jarName = "$ArtifactId-$Version.jar"
    $url = "https://repo1.maven.org/maven2/$groupPath/$ArtifactId/$Version/$jarName"
    $targetPath = Join-Path $TargetDir $jarName
    
    Log-Message "Downloading $jarName from Maven Central..."
    try {
        Invoke-WebRequest -Uri $url -OutFile $targetPath -UseBasicParsing
        Log-Message "Successfully downloaded $jarName"
        return $targetPath
    }
    catch {
        Log-Message "Failed to download $jarName`: $_"
        exit 1
    }
}

function Setup-Dependencies {
    Log-Message "Setting up dependencies..."
    
    # Create a shared lib directory for downloads
    $shared_lib_dir = Join-Path $RESULTS_DIR "dependencies"
    if (-not (Test-Path $shared_lib_dir)) {
        New-Item -ItemType Directory -Force -Path $shared_lib_dir | Out-Null
    }

    # Download dependencies once and store paths in global variables
    $script:ARCHUNIT_JUNIT_PATH = Download-Jar "com.tngtech.archunit" "archunit-junit4" "1.0.1" $shared_lib_dir
    $script:ARCHUNIT_CORE_PATH = Download-Jar "com.tngtech.archunit" "archunit" "1.0.1" $shared_lib_dir
    $script:SLF4J_API_PATH = Download-Jar "org.slf4j" "slf4j-api" "1.7.25" $shared_lib_dir
    $script:SLF4J_SIMPLE_PATH = Download-Jar "org.slf4j" "slf4j-nop" "1.7.25" $shared_lib_dir
    
    Log-Message "Dependencies setup completed"
}


function Add-ArchUnit-Dependencies {
    $lib_dir = Join-Path $TEMP_DIR "lib"
    if (-not (Test-Path $lib_dir)) {
        New-Item -ItemType Directory -Force -Path $lib_dir | Out-Null
    }

    # Copy pre-downloaded dependencies to temp lib directory
    Copy-Item $ARCHUNIT_JUNIT_PATH (Join-Path $lib_dir "archunit-junit4-0.23.1.jar")
    Copy-Item $ARCHUNIT_CORE_PATH (Join-Path $lib_dir "archunit-0.23.1.jar")
    Copy-Item $SLF4J_API_PATH (Join-Path $lib_dir "slf4j-api-1.7.25.jar")
    Copy-Item $SLF4J_SIMPLE_PATH (Join-Path $lib_dir "slf4j-simple-1.7.25.jar")
    
    Log-Message "Copied ArchUnit & SLF4J dependencies to temp lib directory"
}

function Inject-ArchUnit-Test {
    $test_dir = Join-Path $TEMP_DIR "src\test\org\tzi\use\architecture"
    if (-not (Test-Path $test_dir)) {
        New-Item -ItemType Directory -Force -Path $test_dir | Out-Null
        Log-Message "Created directory: $test_dir"
    }

    $new_test_file = Join-Path $test_dir "AntCyclicDependenciesCoreTest.java"
    $test_file_content | Out-File -FilePath $new_test_file -Encoding UTF8
    Remove-BOM-From-New-Test-File -FilePath "src\test\org\tzi\use\architecture\AntCyclicDependenciesCoreTest.java"
    if (Test-Path $new_test_file) {
        Log-Message "Successfully added test to commit"
    }
}

function Inject-ArchUnit-Properties {
    $resources_dir = Join-Path $TEMP_DIR "src\test\resources"
    if (-not (Test-Path $resources_dir)) {
        New-Item -ItemType Directory -Force -Path $resources_dir | Out-Null
        Log-Message "Created directory: $resources_dir"
    }

    $new_properties_file = Join-Path $resources_dir "archunit.properties"
    $properties_file_content | Out-File -FilePath $new_properties_file -Encoding UTF8
    Remove-BOM-From-New-Test-File -FilePath "src\test\resources\archunit.properties"
    if (Test-Path $new_properties_file) {
        Log-Message "Successfully added properties file to commit"
    }
}

function Update-Java-Version-And-Encoding {
    $buildXml = Get-Content "build.xml" -Raw

    $buildXml = $buildXml -replace 'source="(1\.)?(\d+)"', 'source="8"'
    $buildXml = $buildXml -replace 'target="(1\.)?(\d+)"', 'target="8"'
    $buildXml = $buildXml -replace '(java\.targetversion"\s+value=")1\.?\d+(")', 'java.targetversion" value="8"'
    $buildXml = $buildXml -replace '(java\.sourceversion"\s+value=")1\.?\d+(")', 'java.sourceversion" value="8"'
    $buildXml = $buildXml -replace 'value="1\.[0-7]"', 'value="8"'
    $buildXml = $buildXml -replace 'value="1\.[0-7]\s*"', 'value="8"'
    
    $buildXml | Set-Content "build.xml"

    $content = Get-Content "build.xml" -Raw
    if ($content -match 'value="7"') {
        Log-Message "Successfully updated Java version to 8"
    } else {
        Log-Message "Warning: Java version update may not have worked"
    }
}

function Check-Buildxml-Classpath {
    $buildXml = Get-Content "build.xml" -Raw
    
    # Check for ArchUnit property definitions
    $hasArchunitJar = $buildXml -match "archunit\.jar"
    $hasArchunitJunit4Jar = $buildXml -match "archunit-junit4\.jar"
    
    # Check for ArchUnit in compilation classpath
    $compileClasspathHasArchUnit = $buildXml -match '<javac.*classpath.*archunit\.jar'
    
    Log-Message "build.xml checks:"
    Log-Message "Has archunit.jar property: $hasArchunitJar"
    Log-Message "Has archunit-junit4.jar property: $hasArchunitJunit4Jar"
    Log-Message "Compile classpath includes ArchUnit: $compileClasspathHasArchUnit"
}

function Update-Buildxml-For-ArchUnit-Dependencies {
    $buildXml = Get-Content "build.xml" -Raw
    
    $buildXml = $buildXml -replace '(<target name="test-classcycles".*?>)[\s\S]*?</target>', '$1</target>'
    
    if ($buildXml -match "archunit-junit4-0.23.1.jar|archunit-0.23.1.jar") {
        Log-Message "ArchUnit dependencies already present in build.xml"
        return
    }
    
    $propertyInsert = @"
    <property name="archunit.jar" location="`${lib.dir}/archunit-0.23.1.jar"/>
    <property name="archunit-junit4.jar" location="`${lib.dir}/archunit-junit4-0.23.1.jar"/>
    <property name="slf4j-api.jar" location="`${lib.dir}/slf4j-api-1.7.25.jar"/>
    <property name="slf4j-simple.jar" location="`${lib.dir}/slf4j-simple-1.7.25.jar"/>
    <property name="src.test.resources.dir" location="`${src.test.dir}/resources"/>
"@

    # either replace with antlr or have two versions and check which one is in buildxml
    $buildXml = $buildXml -replace '(<property name="guava.jar".*?/>)', "`$1`n$propertyInsert"
    $buildXml = $buildXml -replace '(<property name="combinatoricslib\.jar".*?/>)', "`$1`n$propertyInsert"

    $newDeps = ':${archunit.jar}:${archunit-junit4.jar}:${slf4j-api.jar}:${slf4j-simple.jar}:${src.test.resources.dir}'
    
    $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\}:\$\{combinatoricslib\.jar\}:\$\{vtd-xml\.jar\}:\$\{itextpdf\.jar\})', "`$1$newDeps"
    $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\}:\$\{combinatoricslib\.jar\})', "`$1$newDeps"
    
    $buildXml = $buildXml -replace '(classpath path="\$\{antlr\.jar\}:\$\{build\.classes\.dir\}:\$\{gsbase\.jar\}:\$\{jruby\.jar\}:\$\{junit\.jar\}:\$\{guava\.jar\})', "`$1$newDeps"
    $buildXml = $buildXml -replace '(classpath path="\$\{antlr\.jar\}:\$\{build\.classes\.dir\}:\$\{gsbase\.jar\}:\$\{jruby\.jar\}:\$\{junit\.jar\})', "`$1$newDeps"
    
    $buildXml | Set-Content "build.xml" -Force
    Log-Message "Successfully added ArchUnit dependencies to build.xml"
}

function Add-Test-Target-If-Missing {
    $buildXml = Get-Content "build.xml" -Raw
    
    if ($buildXml -notmatch '<target\s+name="test-junit"') {
        # Add a test-junit target if it doesn't exist
        $testJunitTarget = @"
    <target name="test-junit" depends="build" description="Runs the JUnit tests">
        <junit fork="yes" haltonfailure="no" printsummary="yes">
            <classpath>
                <pathelement location="`${build.classes.dir}"/>
                <pathelement path="`${archunit.jar}:`${archunit-junit4.jar}:`${slf4j-api.jar}:`${slf4j-simple.jar}"/>
                <pathelement path="`${junit.jar}"/>
                <!-- Add other classpath elements as needed -->
            </classpath>
            <formatter type="plain"/>
            <test name="`${test.case}" if="test.case"/>
            <batchtest todir="`${build.dir}" unless="test.case">
                <fileset dir="`${src.test.dir}">
                    <include name="**/*Test.java"/>
                </fileset>
            </batchtest>
        </junit>
    </target>
"@
        
        # Find a good place to add the target
        if ($buildXml -match '</project>') {
            $buildXml = $buildXml -replace '</project>', "$testJunitTarget`n</project>"
            $buildXml | Set-Content "build.xml" -Force
            Log-Message "Added test-junit target to build.xml"
        }
    }
}

function Print-BuildXml {
    if (Test-Path "build.xml") {
        Write-Host "Contents of build.xml:"
        Get-Content "build.xml" | ForEach-Object { Write-Host $_ }
    } else {
        Write-Host "build.xml not found in the current directory."
    }
}

function Add-CombinatoricsLib-If-Missing {
    $buildXml = Get-Content "build.xml" -Raw
    
    if ($buildXml -notmatch "combinatoricslib\.jar") {
        # Add the property definition
        $propertyLine = '  <property name="combinatoricslib.jar" location="${lib.dir}/combinatoricslib-0.2.jar"/>'
        $buildXml = $buildXml -replace '(<property name="jruby\.jar".*?/>)', "`$1`n$propertyLine"
        
        # Add it to the compile classpaths
        $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{jruby\.jar\})"', '$1:${combinatoricslib.jar}"'
        $buildXml = $buildXml -replace '(classpath="\$\{antlr\.jar\}:\$\{junit\.jar\}:\$\{gsbase\.jar\})"', '$1:${combinatoricslib.jar}"'
        
        $buildXml | Set-Content "build.xml" -Force
        Log-Message "Added combinatoricslib dependency to build.xml"
    }
}

#########################################
# Build & Execution Functions #
#########################################

function Execute-Ant-Build {
    Log-Message "Executing ant build..."
    $ErrorActionPreference = 'Continue'
    $build_output = ant build 2>&1
    $ErrorActionPreference = 'Stop'
    $build_output | ForEach-Object { Log-Message $_ }
    return ($LASTEXITCODE -eq 0)
}

function Execute-ArchUnit-Test {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Executing ant test-junit for ArchUnit test..."
    $ErrorActionPreference = 'Continue'
    $all_output = ant test-junit -Dtest.case="org.tzi.use.architecture.AntCyclicDependenciesCoreTest" 2>&1  
    $test_output = $all_output | Where-Object {$_.GetType().Name -eq "String"}
    $ErrorActionPreference = 'Stop'
    $test_output | ForEach-Object { Log-Message "Test output: $_" }
    
    # Parse metrics directly from test output
    $cycleMetrics = Parse-CycleResults -OutputLines $test_output
    
    if ($cycleMetrics.Count -gt 0) {
        Log-Message "Successfully parsed cycle metrics from output"
        Record-Result -CommitHash $CommitHash -CycleCounts $cycleMetrics
        return $true
    } else {
        Log-Message "No cycle metrics found in test output"
        $failureMetrics = @{}
        $metrics = @(
            "all_modules",
            "analysis",
            "api",
            "config",
            "gen",
            "graph",
            "main", 
            "parser",
            "uml",
            "util"
        )
        foreach ($metric in $metrics) {
            $failureMetrics[$metric] = -1
        }
        Record-Result -CommitHash $CommitHash -CycleCounts $failureMetrics
        return $false
    }
}

function Cleanup {
    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
}

#########################################
# Setup starts here #
#########################################

# Initialize csv file with header
Initialize-Results-File

# Create & move to temp dir
New-Item -ItemType Directory -Force -Path $TEMP_DIR | Out-Null
Set-Location $TEMP_DIR
Log-Message "Created & moved to temporary directory: $TEMP_DIR"

# Clone USE repo into temp dir
Log-Message "Attempting to clone repository from $ORIGINAL_USE_DIR"
$gitOutput = & {
    $ErrorActionPreference = 'SilentlyContinue'
    git clone $ORIGINAL_USE_DIR . 2>&1
}
$gitOutput | ForEach-Object { Log-Message "Git output: $_" }
if ($LASTEXITCODE -ne 0) {
    Log-Message "Failed to clone repository. Exit code: $LASTEXITCODE"
    exit 1
}

# Determine which commits to process
if ($StartCommitHash -ne "") {
    # Verify that the start commit exists
    $commitExists = git cat-file -e "$StartCommitHash^{commit}" 2>$null
    if ($LASTEXITCODE -ne 0) {
        Log-Message "Start commit hash $StartCommitHash not found in repository. Exiting."
        exit 1
    }
    Log-Message "Found start commit $StartCommitHash"
}

# Download dependencies once
Setup-Dependencies

#########################################
# Store ArchUnit Test #
#########################################

git checkout -q master
$current_branch = git rev-parse --abbrev-ref HEAD
    
git checkout -q archunit_tests

if (-not (Test-Path $TEST_FILE_PATH) -or -not (Test-Path $PROPERTIES_FILE_PATH)) {
    Log-Message "Required files not found in archunit_tests branch. Exiting."
    exit 1
}

$test_file_content = Get-Content $TEST_FILE_PATH -Raw -Encoding UTF8
$properties_file_content = Get-Content $PROPERTIES_FILE_PATH -Raw -Encoding UTF8

git checkout -q $current_branch

#########################################
# Main Loop starts here #
#########################################

$CommitsToProcess = if ($StartCommitHash -eq "") {
    # Process all commits from the beginning
    git log --first-parent $current_branch --format="%H"
} elseif ($StartLoop.ToLower() -eq "n") {
    # Process only the specified commit
    @($StartCommitHash)
} else {
    # Process from the specified commit onwards
    git log --first-parent --format="%H" $StartCommitHash
}

$TOTAL_COMMITS = ($CommitsToProcess | Measure-Object).Count
$CURRENT_COMMIT = 1
$previousCycleCounts = @{}
try {
    foreach ($COMMIT in $CommitsToProcess) {
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"
        
        # Get previous commit hash
        $previousCommit = git log -1 --format="%H" "$COMMIT^" 2>$null
        if ($LASTEXITCODE -ne 0) {
            $previousCommit = $null
        }

        # Process commit if it's the first or has relevant changes
        if ($previousCommit -eq $null -or (Has-RelevantChanges -CommitHash $COMMIT -PreviousCommitHash $previousCommit)) {
            git checkout -q $COMMIT
            $is_ant = Test-Path (Join-Path $TEMP_DIR "build.xml")
            
            if ($is_ant) {
                Add-CombinatoricsLib-If-Missing
                Inject-ArchUnit-Test
                Inject-ArchUnit-Properties
                Remove-Id-Tags
                Update-Java-Version-And-Encoding
                Add-ArchUnit-Dependencies
                Update-Buildxml-For-ArchUnit-Dependencies
                Add-Test-Target-If-Missing
                Check-Buildxml-Classpath
                
                Check-And-Fix-Bag-File
                Check-And-Fix-DiagramView-File
                # dont know if necessary
                Check-And-Fix-ExtendedJTable-File
                $buildSuccess = Execute-Ant-Build
                
                if ($buildSuccess) {
                    Log-Message "Ant build successful."
                    Execute-ArchUnit-Test -CommitHash $COMMIT
                } else {
                    Log-Message "Ant build failed. Recording failure for this commit..."
                    $failureMetrics = @{}
                    $metrics = @(
                        "all_modules",
                        "analysis",
                        "api",
                        "config",
                        "gen",
                        "graph",
                        "main", 
                        "parser",
                        "uml",
                        "util"
                    )
                    foreach ($metric in $metrics) {
                        $failureMetrics[$metric] = -1
                    }
                    Record-Result -CommitHash $COMMIT -CycleCounts $failureMetrics
                }
            } else {
                Log-Message "No build.xml found. Skipping this commit..."
                $failureMetrics = @{}
                $metrics = @(
                    "all_modules",
                    "analysis",
                    "api",
                    "config",
                    "gen",
                    "graph",
                    "main", 
                    "parser",
                    "uml",
                    "util"
                )
                foreach ($metric in $metrics) {
                    $failureMetrics[$metric] = -1
                }
                Record-Result -CommitHash $COMMIT -CycleCounts $failureMetrics
            }
        } else {
            Log-Message "No relevant changes in commit $COMMIT. Using previous cycle counts."
            if ($previousCycleCounts.Count -gt 0) {
                Record-Result -CommitHash $COMMIT -CycleCounts $previousCycleCounts
            }
        }
        $CURRENT_COMMIT++
        Cleanup
    }
}
catch {
    Log-Message "Catch Block: An error occurred: $_"
    Log-Message "Stack Trace: $($_.ScriptStackTrace)"
}
finally {
    Set-Location $RESULTS_DIR
    if (Test-Path $TEMP_DIR) {
        Remove-Item -Recurse -Force $TEMP_DIR
        Log-Message "Cleaned up temporary directory $TEMP_DIR"
    }
    $dependencies_dir = Join-Path $RESULTS_DIR "dependencies"
    if (Test-Path $dependencies_dir) {
        Remove-Item -Recurse -Force $dependencies_dir
        Log-Message "Cleaned up dependencies directory"
    }
}