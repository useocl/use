param(
    [int]$StartCommit = 0
)

#########################################
# Global variables start here #
#########################################

$ErrorActionPreference = "Stop"
$ORIGINAL_USE_DIR = "C:\Users\alici\Uni\BA\use"
$RESULTS_DIR = "C:\Users\alici\Uni\BA"
$TEMP_DIR = Join-Path $RESULTS_DIR "USE_TEMP_$(Get-Random)"
$RESULTS_FILE = Join-Path $RESULTS_DIR "ant_layer_violations_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "ant_layer_test_log.txt"
$RELEVANT_PATHS = @(("src/main"), ("src/gui"))
$TEST_FILE_PATH = Join-Path $TEMP_DIR "use-gui\src\test\java\org\tzi\use\architecture\AntLayeredArchitectureTest.java"
$ARCHUNIT_JUNIT_PATH = "C:\Users\alici\.m2\repository\com\tngtech\archunit\archunit-junit4\0.23.1\archunit-junit4-0.23.1.jar"
$ARCHUNIT_CORE_PATH = "C:\Users\alici\.m2\repository\com\tngtech\archunit\archunit\0.23.1\archunit-0.23.1.jar"
$SLF4J_API_PATH = "C:\Users\alici\.m2\repository\org\slf4j\slf4j-api\1.7.25\slf4j-api-1.7.25.jar"
$SLF4J_SIMPLE_PATH = "C:\Users\alici\.m2\repository\org\slf4j\slf4j-simple\1.7.25\slf4j-simple-1.7.25.jar"

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

# Initialize the results CSV with headers
function Initialize-Results-File {
    if (-not (Test-Path $RESULTS_FILE)) {
        "date,time,commit,violations" | Out-File -FilePath $RESULTS_FILE
        Log-Message "Created results file with headers"
    }
}

# Record commit data and violation count to CSV
function Record-Result {
    param(
        [string]$CommitHash,
        [int]$ViolationCount = -1
    )
    
    # Get commit date and time
    $commitDate = git show -s --format=%cd --date=format:"%Y-%m-%d" $CommitHash
    $commitTime = git show -s --format=%cd --date=format:"%H:%M:%S" $CommitHash
    
    # Format the result
    $resultLine = "$commitDate,$commitTime,$CommitHash,$ViolationCount"
    $resultLine | Out-File -FilePath $RESULTS_FILE -Append
    
    Log-Message "Recorded result: date=$commitDate, time=$commitTime, commit=$CommitHash, violations=$ViolationCount"
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
# Directory and Build.xml Functions #
#########################################

function Add-ArchUnit-Dependencies {
    $lib_dir = Join-Path $TEMP_DIR "lib"
    if (-not (Test-Path $lib_dir)) {
        New-Item -ItemType Directory -Force -Path $lib_dir | Out-Null
    }

    Copy-Item $ARCHUNIT_JUNIT_PATH (Join-Path $lib_dir "archunit-junit4-0.23.1.jar")
    Copy-Item $ARCHUNIT_CORE_PATH (Join-Path $lib_dir "archunit-0.23.1.jar")
    Copy-Item $SLF4J_API_PATH (Join-Path $lib_dir "slf4j-api-1.7.25.jar")
    Copy-Item $SLF4J_SIMPLE_PATH (Join-Path $lib_dir "slf4j-simple-1.7.25.jar")
    Log-Message "Copied ArchUnit & SLF4J dependencies from local Maven repository to lib directory"
}

function Inject-ArchUnit-Test {
    $test_dir = Join-Path $TEMP_DIR "src\test\org\tzi\use\architecture"
    if (-not (Test-Path $test_dir)) {
        New-Item -ItemType Directory -Force -Path $test_dir | Out-Null
        Log-Message "Created directory: $test_dir"
    }

    $new_test_file = Join-Path $test_dir "AntLayeredArchitectureTest.java"
    $test_file_content | Out-File -FilePath $new_test_file -Encoding UTF8
    Remove-BOM-From-New-Test-File -FilePath "src\test\org\tzi\use\architecture\AntLayeredArchitectureTest.java"
    if (Test-Path $new_test_file) {
        Log-Message "Successfully added test to commit $COMMIT"
    }
}

function Detect-Original-Java-Version {
    if (Test-Path "build.xml") {
        $buildXml = Get-Content "build.xml" -Raw
        
        # Check for source version pattern
        if ($buildXml -match 'source="(1\.)?(\d+)"') {
            $sourceVersion = $matches[2]
            Log-Message "Original Java source version detected: $sourceVersion"
        }
        elseif ($buildXml -match 'java\.sourceversion"\s+value="1\.(\d+)"') {
            $sourceVersion = $matches[1]
            Log-Message "Original Java source version detected: $sourceVersion"
        }
        elseif ($buildXml -match 'value="1\.(\d+)"') {
            $sourceVersion = $matches[1]
            Log-Message "Original Java version detected: $sourceVersion"
        }
        else {
            Log-Message "Could not detect Java version from build.xml"
        }
        
        # Also check if ANTLR version is specified
        if ($buildXml -match 'antlr-([\d\.]+)\.jar') {
            $antlrVersion = $matches[1]
            Log-Message "ANTLR version detected: $antlrVersion"
        }
    }
    else {
        Log-Message "build.xml not found, cannot detect Java version"
    }
}

function Update-Java-Version-And-Encoding {
    $buildXml = Get-Content "build.xml" -Raw

    # Update to Java 7 because 6 is not compatible with ArchUnit
    $buildXml = $buildXml -replace 'source="(1\.)?(\d+)"', 'source="7"'
    $buildXml = $buildXml -replace 'target="(1\.)?(\d+)"', 'target="7"'
    $buildXml = $buildXml -replace '(java\.targetversion"\s+value=")1\.?\d+(")', 'java.targetversion" value="7"'
    $buildXml = $buildXml -replace '(java\.sourceversion"\s+value=")1\.?\d+(")', 'java.sourceversion" value="7"'
    $buildXml = $buildXml -replace 'value="1\.[0-9]+"', 'value="7"'
    $buildXml = $buildXml -replace 'value="1\.[0-9]+\s*"', 'value="7"'
    
    $buildXml | Set-Content "build.xml"

    $content = Get-Content "build.xml" -Raw
    if ($content -match 'value="7"') {
        Log-Message "Successfully updated Java version to 7"
    } else {
        Log-Message "Warning: Java version update may not have worked"
    }
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
    
    # hier auch resources dir nötig oder vlt schädlich?
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
# Building & Testing Functions #
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
    $test_output = ant test-junit -Dtest.case="org.tzi.use.architecture.AntLayeredArchitectureTest" 2>&1  
    $ErrorActionPreference = 'Stop'
    
    # Log the output
    $test_output | ForEach-Object { Log-Message "Test output: $_" }

    # Extract violation count from output
    $violation_count = -1
    
    foreach ($line in $test_output) {
        if ($line -match "Number of violations: (\d+)") {
            $violation_count = [int]$matches[1]
            Log-Message "Found violation count: $violation_count"
            break
        }
    }
    
    if ($violation_count -ge 0) {
        Record-Result -CommitHash $CommitHash -ViolationCount $violation_count
    } else {
        Log-Message "Failed to extract violation count from test output"
        Record-Result -CommitHash $CommitHash -ViolationCount -1 # Use -1 to indicate error
    }
}

function Cleanup {
    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
}

function Process-Commit {
    param(
        [string]$CommitHash
    )
    
    git checkout -q $CommitHash
    
    $is_ant = Test-Path (Join-Path $TEMP_DIR "build.xml")
    if (-not $is_ant) {
        Log-Message "No build.xml found. Skipping this commit..."
        Record-Result -CommitHash $CommitHash -ViolationCount -2 # -2 indicates no build.xml
        return $false
    }
    
    Detect-Original-Java-Version
    Add-CombinatoricsLib-If-Missing
    Inject-ArchUnit-Test
    Remove-Id-Tags
    Update-Java-Version-And-Encoding
    Add-ArchUnit-Dependencies
    Update-Buildxml-For-ArchUnit-Dependencies
    Add-Test-Target-If-Missing
    
    if ($LASTEXITCODE -ne 0) {
        Log-Message "Build.xml injection failed. Skipping this commit..."
        Record-Result -CommitHash $CommitHash -ViolationCount -3 # -3 indicates build.xml injection failure
        return $false
    }
    
    Check-And-Fix-Bag-File
    Check-And-Fix-DiagramView-File
    Check-And-Fix-ExtendedJTable-File
    
    $buildSuccess = Execute-Ant-Build
    if (-not $buildSuccess) {
        Log-Message "Ant build failed. Skipping this commit..."
        Record-Result -CommitHash $CommitHash -ViolationCount -4 # -4 indicates build failure
        return $false
    }
    
    Log-Message "Ant build successful."
    Execute-ArchUnit-Test -CommitHash $CommitHash
    return $true
}

#########################################
# Setup starts here #
#########################################

New-Item -ItemType Directory -Force -Path $TEMP_DIR | Out-Null
Set-Location $TEMP_DIR

Log-Message "Created & moved to temporary directory: $TEMP_DIR"

# Initialize results file with headers
Initialize-Results-File

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

#########################################
# Store ArchUnit Test #
#########################################

git checkout -q master
$current_branch = git rev-parse --abbrev-ref HEAD
    
git checkout -q archunit_tests

if (-not (Test-Path $TEST_FILE_PATH)) {
    Log-Message "Required files not found in archunit_tests branch. Exiting."
    exit 1
}

$test_file_content = Get-Content $TEST_FILE_PATH -Raw -Encoding UTF8

git checkout -q $current_branch

#########################################
# Main Loop starts here #
#########################################

$COMMITS = git log --first-parent $current_branch --format="%H" --reverse
$TOTAL_COMMITS = ($COMMITS | Measure-Object -Line).Lines
$CURRENT_COMMIT = $StartCommit
$previousCommit = if ($StartCommit -gt 1) {
    ($COMMITS | Select-Object -Skip ($StartCommit - 1) -First 1)
} else {
    $null
}

try {
    foreach ($COMMIT in $COMMITS | Select-Object -Skip $StartCommit) {
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"
        
        $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT
        if (-not $commitProcessedSuccessfully) {
            Log-Message "Processing failed for commit $COMMIT"
        }
        
        Cleanup
        $previousCommit = $COMMIT
        $CURRENT_COMMIT++
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
}