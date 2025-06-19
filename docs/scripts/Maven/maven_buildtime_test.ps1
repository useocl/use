# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

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
$RESULTS_FILE = Join-Path $RESULTS_DIR "build_time_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "test_log_maven_buildtime.txt"

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
        "date,time,commit,buildtime" | Out-File -FilePath $RESULTS_FILE
        Log-Message "Created results file with headers"
    }
}

# Record commit data and build time to CSV
function Record-Result {
    param(
        [string]$CommitHash,
        [int]$BuildTimeSec = -1
    )
    
    # Get commit date and time
    $commitDate = git show -s --format=%cd --date=format:"%Y-%m-%d" $CommitHash
    $commitTime = git show -s --format=%cd --date=format:"%H:%M:%S" $CommitHash
    
    # Format the result
    $resultLine = "$commitDate,$commitTime,$CommitHash,$BuildTimeSec"
    $resultLine | Out-File -FilePath $RESULTS_FILE -Append
    
    Log-Message "Recorded result: date=$commitDate, time=$commitTime, commit=$CommitHash, build_time=$BuildTimeSec s"
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

#########################################
# Dependency Functions #
#########################################

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
            
            $content = $content -replace '<maven\.compiler\.source>15</maven\.compiler\.source>', '<maven.compiler.source>14</maven.compiler.source>'
            $content = $content -replace '<maven\.compiler\.target>15</maven\.compiler\.target>', '<maven.compiler.target>14</maven.compiler.target>'
            
            $content | Set-Content $pomFile
            
            Log-Message "Called method Update-Java-Version for ${pomFile} "
        }
        else {
            Log-Message "Warning: ${pomFile} not found."
        }
    }
}

#########################################
# Build & Execution Functions #
#########################################

function Measure-Build-Time {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Measuring build time..."
    
    # Measure time it takes to build & test the project
    $startTime = Get-Date
    $buildOutput = mvn --batch-mode --update-snapshots verify
    $endTime = Get-Date
    $buildTime = $endTime - $startTime    
    # Convert to seconds
    $buildTimeSec = [int]$buildTime.TotalSeconds
    
    if ($LASTEXITCODE -eq 0) {
        Log-Message "Build completed successfully in $buildTimeSec s"
        Record-Result -CommitHash $CommitHash -BuildTimeSec $buildTimeSec
        return $true
    } else {
        return $false
    }
}

# function Maven-Build-And-Measure {
#     param(
#         [string]$CommitHash
#     )
    
#     Log-Message "Found pom.xml. Preparing for build time measurement..."
    
#     # Build & install parent pom
#     $parent_output = mvn -N clean install 2>&1

#     if ($LASTEXITCODE -ne 0) {
#         Log-Message "Parent pom installation failed:"
#         $parent_output | ForEach-Object { Log-Message $_ }
#         Record-Result -CommitHash $CommitHash -BuildTimeSec -2
#         return $false
#     }

#     # Install core module to local repository (without tests)
#     Log-Message "Installing use-core module to local repo..."
#     $core_install_output = mvn -pl use-core clean install -DskipTests 2>&1

#     if ($LASTEXITCODE -ne 0) {
#         Log-Message "Maven core module installation failed:"
#         $core_install_output | ForEach-Object {Log-Message $_ }
#         Record-Result -CommitHash $CommitHash -BuildTimeSec -2
#         return $false
#     }

#     Log-Message "Core module installed successfully. Measuring build time now..."
#     Measure-Build-Time -CommitHash $CommitHash
    
#     return $true
# }

function Process-Commit {
    param(
        [string]$CommitHash
    )
    
    git checkout -q $CommitHash
    $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

    if (-not $is_maven) {
        Log-Message "No pom.xml found. Skipping this commit because it's not Maven..."
        return $false
    }

    Update-Java-Version
    Ensure-Dependencies-For-Maven
    $mavenBuildSuccess = Measure-Build-Time -CommitHash $CommitHash

    # Clean up after processing
    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
    return $mavenBuildSuccess
}

#########################################
# Setup starts here #
#########################################

# Initialize results file with headers
Initialize-Results-File

# Create & move to temp directory
New-Item -ItemType Directory -Force -Path $TEMP_DIR | Out-Null
Set-Location $TEMP_DIR
Log-Message "Created & moved to temporary directory: $TEMP_DIR"


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

#########################################
# Main Loop starts here #
#########################################

# Verify that the start commit exists
if ($StartCommitHash -ne "") {
    $commitExists = git cat-file -e "$StartCommitHash^{commit}" 2>$null
    if ($LASTEXITCODE -ne 0) {
        Log-Message "Start commit hash $StartCommitHash not found in repository. Exiting."
        exit 1
    }
    Log-Message "Found start commit $StartCommitHash"
}

# Determine which commits to process
$CommitsToProcess = if ($StartCommitHash -eq "") {
    # Process all commits from the beginning
    git log --first-parent $current_branch --format="%H"
} elseif ($StartLoop.ToLower() -eq "n") {
    # Process only the specified commit
    @($StartCommitHash)
} else {
    # Process from the specified commit onwards
    git log --first-parent --format="%H" $StartCommitHash --reverse
}

$TOTAL_COMMITS = ($CommitsToProcess | Measure-Object).Count
$CURRENT_COMMIT = 1

try {
    foreach ($COMMIT in $CommitsToProcess) {
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"
        
        $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT
        if (-not $commitProcessedSuccessfully) {
            Log-Message "Build failed for commit $COMMIT"
            Record-Result -CommitHash $COMMIT -BuildTimeSec -1

        }
        $CURRENT_COMMIT++
    }
}
catch {
    Log-Message "Catch Block: An error occurred: $_"
    Log-Message "Stack Trace: $($_.ScriptStackTrace)"
}
finally {
    # Clean up
    Set-Location $RESULTS_DIR
    if (Test-Path $TEMP_DIR) {
        Remove-Item -Recurse -Force $TEMP_DIR
        Log-Message "Cleaned up temporary directory $TEMP_DIR"
    }
}