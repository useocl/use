# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# First Maven Commit is 132ab9b
param(
    [int]$StartCommit = 0
)

$ErrorActionPreference = "Stop"
$ORIGINAL_USE_DIR = "C:\Users\alici\Uni\BA\use"
$RESULTS_DIR = "C:\Users\alici\Uni\BA"
$TEMP_DIR = Join-Path $RESULTS_DIR "USE_TEMP_$(Get-Random)"
$RESULTS_FILE = Join-Path $RESULTS_DIR "layer_violations_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "test_log.txt"
$RELEVANT_PATHS = @(
    ("use-gui/src/"),
    ("use-core/src/"),
    ("src/main")
)

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

function Ensure-Dependencies-For-Maven {
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

            $build = $pom.project.build
            if ($build -and $build.plugins) {
                $helper_plugin = $build.plugins.plugin | Where-Object { $_.artifactId -eq "build-helper-maven-plugin" }
                if ($helper_plugin) {
                    Log-Message "Deactivating build-helper-plugin in $full_path"
                    foreach ($execution in $helper_plugin.executions.execution) {
                        if ($execution.id -eq "add-test-source") {
                            $execution.phase = "none"
                        }
                    }
                }
            }

            $pom.Save($full_path)
        }
        else {
            Log-Message "pom.xml not found at $full_path"
        }
    }
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

function Fix-BagInterface {
    param (
        [System.IO.FileInfo]$BagFile
    )
    
    Log-Message "Fixing Bag interface..."
    $content = Get-Content $BagFile.FullName -Raw
    $content = $content -replace 'boolean contains\(T obj\)', 'boolean contains(Object obj)'
    $content = $content -replace 'boolean remove\(T o\)', 'boolean remove(Object o)'
    $content | Set-Content $BagFile.FullName
}

function Update-Java-Version {

    $pomFiles = @(
        "$TEMP_DIR\pom.xml",
        "$TEMP_DIR\use-core\pom.xml",
        "$TEMP_DIR\use-gui\pom.xml",
        "$TEMP_DIR\use-assembly\pom.xml"
    )

    foreach ($pomFile in $pomFiles) {
        if (Test-Path $pomFile) {
            $content = Get-Content $pomFile -Raw
            
            #$content = $content -replace '<maven\.compiler\.source>21</maven\.compiler\.source>', '<maven.compiler.source>18</maven.compiler.source>'
            #$content = $content -replace '<maven\.compiler\.target>21</maven\.compiler\.target>', '<maven.compiler.target>18</maven.compiler.target>'

            $content = $content -replace '<maven\.compiler\.source>15</maven\.compiler\.source>', '<maven.compiler.source>14</maven.compiler.source>'
            $content = $content -replace '<maven\.compiler\.target>15</maven\.compiler\.target>', '<maven.compiler.target>14</maven.compiler.target>'
            
            $content | Set-Content $pomFile
            
            Log-Message "Called method Update-Java-Version"
        }
        else {
            Log-Message "Warning: ${pomFile} not found."
        }
    }
}

function Prepare-Directory-For-Maven-Build-And-Test {
    # Add architecture directory if it doesn't exist
    $test_file_dir = Join-Path $TEMP_DIR "use-gui\src\test\java\org\tzi\use\architecture"
    if (-not (Test-Path $test_file_dir)) {
        New-Item -ItemType Directory -Force -Path $test_file_dir | Out-Null
        Log-Message "Created directory: $test_file_dir"
    }

    # Add CyclomaticComplexityTest to commit
    $test_file_path = Join-Path $test_file_dir "MavenLayeredArchitectureTest.java"
    $test_file_content | Out-File -FilePath $test_file_path -Encoding UTF8
    # maybe change URI to test_file_path
    Remove-BOM -FilePath "use-gui\src\test\java\org\tzi\use\architecture\MavenLayeredArchitectureTest.java"
    Log-Message "Successfully added test to commit $COMMIT"
}

function Run-ArchUnit-Test {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Running ArchUnit test..."
    $test_output = mvn -pl use-gui test "-Dtest=org.tzi.use.architecture.MavenLayeredArchitectureTest" -DfailIfNoTests=false -o 2>&1    
    Log-Message "Test Output: $test_output"

    # Extract the violation count from the output
    # The number appears alone on the line after the "Running org.tzi.use.architecture.MavenLayeredArchitectureTest" line
    $violation_count = -1
    $running_line_found = $false
    
    foreach ($line in $test_output) {
        if ($running_line_found) {
            # The next line should contain the violation count
            if ($line -match '^\s*(\d+)\s*$') {
                $violation_count = [int]$matches[1]
                break
            }
        }
        
        if ($line -match 'Running org\.tzi\.use\.architecture\.MavenLayeredArchitectureTest') {
            $running_line_found = $true
        }
    }
    
    if ($violation_count -ge 0) {
        Log-Message "Found violation count: $violation_count"
        Record-Result -CommitHash $CommitHash -ViolationCount $violation_count
    } else {
        Log-Message "Failed to extract violation count from test output"
        Record-Result -CommitHash $CommitHash -ViolationCount -1 # Use -1 to indicate error
    }
}

function Maven-Build-And-Test {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Found pom.xml. Attempting compilation with Maven..."
    
    # Build & install parent pom
    $parent_output = mvn -N clean install 2>&1

    if ($LASTEXITCODE -ne 0) {
        Log-Message "Parent pom installation failed:"
        $parent_output | ForEach-Object { Log-Message $_ }
        Record-Result -CommitHash $CommitHash -ViolationCount -2
        return $false
    }

    # Install core module to local repository (without tests) bc of snapshot errors
    Log-Message "Installing use-core module to local repo..."
    $core_install_output = mvn -pl use-core clean install -DskipTests 2>&1

    if ($LASTEXITCODE -ne 0) {
        Log-Message "Maven core module installation failed:"
        $core_install_output | ForEach-Object {Log-Message $_ }
        Record-Result -CommitHash $CommitHash -ViolationCount -2
        return $false
    }

    Log-Message "Core module installed successfully. Compiling project..."
    # $compile_output = mvn clean compile 2>&1
    
    # if ($LASTEXITCODE -ne 0) {
    #     Log-Message "Maven compilation failed. Error output:"
    #     $compile_output | ForEach-Object { Log-Message $_ }
    #     Record-Result -CommitHash $CommitHash -ViolationCount -2 # Use -2 to indicate build failure
    #     return $false
    # }
    
    # Log-Message "Maven compile successful."
    Run-ArchUnit-Test -CommitHash $CommitHash
    return $true
}

function Process-Commit {
    param(
        [string]$CommitHash
    )
    
    git checkout -q $CommitHash
    $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

    # Check for pom.xml
    if (-not $is_maven) {
        Log-Message "No pom.xml found. Skipping this commit because it's not Maven..."
        return $false
    }

    Update-Java-Version
    Ensure-Dependencies-For-Maven
    Prepare-Directory-For-Maven-Build-And-Test
    $mavenBuildSuccess = Maven-Build-And-Test -CommitHash $CommitHash

    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
    return $mavenBuildSuccess
}

# Main script execution
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
else {
    Log-Message "Repository cloned successfully."
}

git checkout -q master
$current_branch = git rev-parse --abbrev-ref HEAD

git checkout -q archunit_tests
$test_file_path = Join-Path $TEMP_DIR "use-gui\src\test\java\org\tzi\use\architecture\MavenLayeredArchitectureTest.java"

if (-not (Test-Path $test_file_path)) {
    Log-Message "Required file not found in archunit_tests branch. Exiting."
    exit 1
}

$test_file_content = Get-Content $test_file_path -Raw -Encoding UTF8

# back to main
git checkout -q $current_branch

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
            Log-Message "Build failed for commit $COMMIT"
        }
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