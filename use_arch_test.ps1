# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
$ErrorActionPreference = "Stop"

$ORIGINAL_USE_DIR = "C:\Users\alici\Uni\BA\use"
$RESULTS_DIR = "C:\Users\alici\Uni\BA"
$TEMP_DIR = Join-Path $RESULTS_DIR "USE_TEMP_$(Get-Random)"
$RESULTS_FILE = Join-Path $RESULTS_DIR "cyclomatic_complexity_results.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "test_log.txt"

function Log-Message {
    param([string]$message)
    Write-Host $message
    $message | Out-File -FilePath $LOG_FILE -Append
}

function Remove-BOM {
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

function Ensure-Dependencies {
    $pom_files = @("pom.xml", "use-core/pom.xml", "use-gui/pom.xml")
    
    foreach ($pom_file in $pom_files) {
        $full_path = Join-Path $TEMP_DIR $pom_file
        if (Test-Path $full_path) {
            Log-Message "Checking dependencies in $full_path"
            $pom = [xml](Get-Content $full_path)

            if ($null -eq $pom.project) {
                Log-Message "Project node not found in $full_path"
                continue
            }

            if ($null -eq $pom.project.dependencies) {
                Log-Message "No dependencies in pom"
                $dependencies = $pom.CreateElement("dependencies", $pom.DocumentElement.NamespaceURI)
                $pom.project.AppendChild($dependencies)
            } else {
                Log-Message "Dependencies already in pom"
                $dependencies = $pom.project.dependencies
            }
            
            $archunit_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "archunit-junit5" }
            if (-not $archunit_present) {
                Log-Message "Adding ArchUnit dependency to $full_path"
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>com.tngtech.archunit</groupId><artifactId>archunit-junit5</artifactId><version>1.0.1</version>"
                $dependencies.AppendChild($new_dep)
            }

            $junit_jupiter_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "junit-jupiter" }
            if (-not $junit_jupiter_present) {
                Log-Message "Adding JUnit 5 dependency to $full_path"
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><version>5.8.2</version><scope>test</scope>"
                $dependencies.AppendChild($new_dep)
            }

            $slf4j_nop_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "slf4j-nop" }
            if (-not $slf4j_nop_present) {
                Log-Message "Adding SLF4J NOP binding to $full_path"
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>org.slf4j</groupId><artifactId>slf4j-nop</artifactId><version>1.7.32</version><scope>test</scope>"
                $dependencies.AppendChild($new_dep)
            }

            $pom.Save($full_path)
        }
        else {
            Log-Message "pom.xml not found at $full_path"
        }
    }
}

# Saves CycleCount in csv file
function Record-Result {
    param(
        [int]$CycleCount
    )
    $CycleCount | Out-File -FilePath $RESULTS_FILE -Append
    Log-Message "Recorded result Cycles $CycleCount"
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

function Update-Buildxml-For-Ant {
    Log-Message "Updating build.xml to use Java 8 & ISO-8859-1..."
    $buildXml = Get-Content "build.xml" -Raw
    $buildXml = $buildXml -replace 'source="(1\.)?(\d+)"', 'source="1.8"'
    $buildXml = $buildXml -replace 'target="(1\.)?(\d+)"', 'target="1.8"'
    $buildXml = $buildXml -replace '(java\.targetversion"\s+value=")1\.?\d+(")', 'java.targetversion" value="1.8"'
    $buildXml = $buildXml -replace '(java\.sourceversion"\s+value=")1\.?\d+(")', 'java.sourceversion" value="1.8"'
    $buildXml = $buildXml -replace '<javac', '<javac encoding="ISO-8859-1"'
    $buildXml | Set-Content "build.xml"
}

function Fix-BagInterface {
    param (
        [System.IO.FileInfo]$BagFile
    )
    
    Log-Message "Fixing Bag interface in $($BagFile.FullName) ..."
    $content = Get-Content $BagFile.FullName -Raw

    $content = $content -replace 'boolean contains\(T obj\)', 'boolean contains(Object obj)'
    $content = $content -replace 'boolean remove\(T o\)', 'boolean remove(Object o)'
    
    # Add @Override annotations to methods that are in Collection
    $content = $content -replace '(\s*)(boolean contains\(Object obj\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(boolean remove\(Object o\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(boolean add\(T o\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(void clear\(\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(boolean equals\(Object o\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(int hashCode\(\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(int size\(\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(boolean isEmpty\(\);)', '$1@Override$1$2'
    $content = $content -replace '(\s*)(Iterator<T> iterator\(\);)', '$1@Override$1$2'
    
    $content | Set-Content $BagFile.FullName
}

function Skip-Class-Usage-Errors {
    param (
        [string]$ClassName
    )
    Log-Message "Checking for $ClassName class..."
    $unimplementedClass = Get-ChildItem -Recurse -Filter *.java |
                             Select-String -Pattern "class\s+$ClassName" -List
    $unimplementedClassUsage = Get-ChildItem -Recurse -Filter *.java |
                             Select-String -Pattern "$ClassName" -List
    Log-Message "$ClassName count: $($unimplementedClass.Count) Usage count: $($unimplementedClassUsage.Count)"
    if ($unimplementedClass.Count -eq 0 -and $unimplementedClassUsage.Count -gt 0) {
        Log-Message "$ClassName is used but not defined in this commit. Skipping..."
        return $true
    }
    return $false
}

function Build-Ant-Project {
    Update-Buildxml-For-Ant
    if (Skip-Class-Usage-Errors "ExpCollectNested") { return }
    if (Skip-Class-Usage-Errors "VoidType") { return }
    if (Skip-Class-Usage-Errors "GSignature") { return }

    Get-ChildItem -Recurse -Filter *.java | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        if ($content -match '\$Id\$') {
            $content = $content -replace '\$Id\$', ''
            $content | Set-Content $_.FullName
        }
    }

    Log-Message "Executing ant build"
    $ErrorActionPreference = 'Continue'
    $build_output = ant build 2>&1
    $ErrorActionPreference = 'Stop'
    $build_output | ForEach-Object { Log-Message $_ }

    if ($build_output -match "package org.tzi.use.runtime.shell.impl does not exist") {
        Log-Message "!!!!! PluginShellCmdProxy imported from wrong package. Skipping..."
        return
    }

    if ($LASTEXITCODE -ne 0) {
        $bagErrorCount = ($build_output | Select-String -Pattern "error: name clash: (contains|remove)\(T\) in Bag and (contains|remove)\(Object\) in Collection have the same erasure" -AllMatches).Matches.Count
        if ($bagErrorCount -gt 0) {
            # refaktorisieren!
            $bagFile = Get-ChildItem -Recurse -Filter "Bag.java" | Select-Object -First 1
            if ($bagFile) {
                Fix-BagInterface($bagFile)
                #Log-Message "Contents of $($bagFile.FullName) after fix:"
                #Get-Content $bagFile.FullName | ForEach-Object { Log-Message $_ }
                Log-Message "Rebuilding after fixing Bag interface..."
                $ErrorActionPreference = 'Continue'
                $new_build_output = ant build 2>&1
                $ErrorActionPreference = 'Stop'
                $new_build_output | ForEach-Object { Log-Message $_ }
                if ($LASTEXITCODE -ne 0) {
                    Log-Message "Build still failed after fixing Bag interface. Exit code: $LASTEXITCODE"
                    exit 1
                } else {
                    Log-Message "Build successful after fixing Bag interface"
                }
            } else {
                Log-Message "Couldn't fix error, because Bag.java file was not found. Exiting..."
                exit 1
            }
        } else {
            Log-Message "Ant build failed. Exit code: $LASTEXITCODE"
            exit 1
        }
    } else {
        Log-Message "Ant build successful."
    }
}

function Prepare-Directory-For-Maven-Build-And-Test {
    # Add architecture directory if it doesn't exist
    $test_file_dir = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture"
    if (-not (Test-Path $test_file_dir)) {
        New-Item -ItemType Directory -Force -Path $test_file_dir | Out-Null
        Log-Message "Created directory: $test_file_dir"
    }

    # Add CyclomaticComplexityTest to commit
    $test_file_path = Join-Path $test_file_dir "CyclomaticComplexityTest.java"
    $test_file_content | Out-File -FilePath $test_file_path -Encoding UTF8
    # maybe change URI to test_file_path
    Remove-BOM -FilePath "use-core\src\test\java\org\tzi\use\architecture\CyclomaticComplexityTest.java"
    Log-Message "Successfully added test to commit $COMMIT"

    # Add archunit.properties file
    $properties_file_path = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"
    $properties_file_content | Out-File -FilePath $properties_file_path -Encoding UTF8
    Remove-BOM -FilePath "use-core\src\test\resources\archunit.properties"
    Log-Message "Added archunit.properties file to commit $COMMIT"
}

function Maven-Build-And-Test {
    Log-Message "Found pom.xml. Attempting compilation with Maven..."
    $compile_output = mvn clean compile 2>&1
    if ($LASTEXITCODE -ne 0) {
        Log-Message "Maven compilation failed. Error output:"
        $compile_output | ForEach-Object { Log-Message $_ }
        Record-Result -CycleCount $previousCycleCount
        git reset -q --hard
        git clean -qfd
        git checkout -q $current_branch
        Log-Message "Switched back to original branch: $current_branch"
        continue
    }
	Log-Message "Maven compile successful."
    Log-Message "Running ArchUnit test..."
    $test_output = mvn -pl use-core test "-Dtest=org.tzi.use.architecture.CyclomaticComplexityTest" -DfailIfNoTests=false 2>&1    
    $cyclomatic_results_file = Join-Path $TEMP_DIR "use-core\cyclomatic_complexity_results.csv"
    if (Test-Path $cyclomatic_results_file) {
        $cycle_count = Get-Content $cyclomatic_results_file | Select-Object -First 1
        $previousCycleCount = $cycle_count
        Record-Result -CycleCount $cycle_count
    } else {
        Log-Message "Cyclomatic results file not found"
        Record-Result -CycleCount -1
    }
}

New-Item -ItemType Directory -Force -Path $TEMP_DIR | Out-Null
Set-Location $TEMP_DIR
Log-Message "Created & moved to temporary directory: $TEMP_DIR"


# Log-Message
# git clone $ORIGINAL_USE_DIR . 2>&1 | ForEach-Object { Log-Message "Git output: $_" }
# if ($LASTEXITCODE -ne 0) {
# Log-Message "Failed to clone repo. Exit code: $LASTEXITCODE"
# exit 1}
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
else {
    Log-Message "Repository cloned successfully."
}

# evtl use-gui?
$RELEVANT_PATHS = @(
    ("use-core/src/"),
    ("src/main")
)

git checkout -q master
$current_branch = git rev-parse --abbrev-ref HEAD

git checkout -q archunit_tests
$test_file_path = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture\CyclomaticComplexityTest.java"
$properties_file_path = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"

if (-not (Test-Path $test_file_path) -or -not (Test-Path $properties_file_path)) {
    Log-Message "Required files not found in archunit_tests branch. Exiting."
    exit 1
}

$test_file_content = Get-Content $test_file_path -Raw -Encoding UTF8
$properties_file_content = Get-Content $properties_file_path -Raw -Encoding UTF8

# back to main
git checkout -q $current_branch

$COMMITS = git log --first-parent $current_branch --reverse --format="%H"
$TOTAL_COMMITS = ($COMMITS | Measure-Object -Line).Lines
$CURRENT_COMMIT = 0
$previousCommit = $null
$previousCycleCount = -1

try {
    foreach ($COMMIT in $COMMITS) {
        $CURRENT_COMMIT++
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"

        if ($previousCommit -eq $null -or (Has-RelevantChanges -CommitHash $COMMIT -PreviousCommitHash $previousCommit)) {
            git checkout -q $COMMIT

            $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")
            $is_ant = Test-Path (Join-Path $TEMP_DIR "build.xml")

            # Check for pom.xml
            if ($is_maven) {
                Ensure-Dependencies
                Prepare-Directory-For-Maven-Build_And-Test
                Maven-Build-And-Test	            
	        } elseif ($is_ant) {
                Build-Ant-Project
            } else {
                Log-Message "No pom.xml or build.xml found. Skipping this commit"
                continue
            }

            git reset -q --hard
            git clean -qfd
            Log-Message "#####################################################################################"
        } else {
            Log-Message "No relevant changes in commit $COMMIT. Using previous result."
            Record-Result -CycleCount $previousCycleCount
        }

        $previousCommit = $COMMIT
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