# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# First Maven Commit is 132ab9b
param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

. "..\general_utilities.ps1"
. ".\maven_utilities.ps1"

#########################################
# Global variables start here #
#########################################

$ErrorActionPreference = "Stop"
# Set paths based on parameters or use defaults
$ORIGINAL_USE_DIR = if ($OriginalUseDir -ne "") { 
    $OriginalUseDir 
} else { 
    # Scripts are in docs/scripts/maven, go 3 levels up to repo root
    Join-Path (Get-Location) "../../.."
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
$RESULTS_FILE = Join-Path $RESULTS_DIR "maven_layers_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "mave_layers_test_log.txt"

$RELEVANT_PATHS = @(
    ("use-gui/src/"),
    ("use-core/src/")
)
$TEST_FILE_PATH = Join-Path $TEMP_DIR "use-gui\src\test\java\org\tzi\use\architecture\MavenLayeredArchitectureTest.java"


#########################################
# Functions start here #
#########################################

#########################################
# Dependency Functions #
#########################################

function Parse-Test-Results {
    param(
        [string[]]$TestOutput
    )

    # Violation count appears alone on the line after the "Running org.tzi.use.architecture.MavenLayeredArchitectureTest" line
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
    return $violation_count
}

#########################################
# Build & Execution Functions #
#########################################

function Run-ArchUnit-Test {
    param(
        [string]$CommitHash
    )
    Log-Message "Running ArchUnit test..."
    $test_output = mvn -pl use-gui test "-Dtest=org.tzi.use.architecture.MavenLayeredArchitectureTest" -DfailIfNoTests=false -o 2>&1    

    # Parse metrics directly from test output
    $violation_count = Parse-Test-Results -TestOutput $test_output

    if ($violation_count -ge 0) {
        Log-Message "Successfully parsed violation count from test output"
        Record-Simple-Result -CommitHash $CommitHash -TestResult $violation_count -ResultsFile $RESULTS_FILE
    } else {
        Log-Message "Failed to extract violation count from test output"
        Record-Simple-Result -CommitHash $CommitHash -TestResult -1 -ResultsFile $RESULTS_FILE
    }
}

function Maven-Build-And-Test {
    param(
        [string]$CommitHash
    )
        
    # Build & install parent pom
    $parent_output = mvn -N clean install 2>&1

    if ($LASTEXITCODE -ne 0) {
        Log-Message "Parent pom installation failed:"
        $parent_output | ForEach-Object { Log-Message $_ }
        Record-Simple-Result -CommitHash $CommitHash -TestResult -1 -ResultsFile $RESULTS_FILE
        return $false
    }

    # Install core module to local repository (without tests) bc of snapshot errors
    $core_install_output = mvn -pl use-core clean install -DskipTests 2>&1

    if ($LASTEXITCODE -ne 0) {
        Log-Message "Maven core module installation failed:"
        Record-Simple-Result -CommitHash $CommitHash -TestResult -1 -ResultsFile $RESULTS_FILE
        return $false
    }
    Log-Message "Core module installed successfully. Compiling project..."

    Run-ArchUnit-Test -CommitHash $CommitHash
    return $true
}

function Process-Commit {
    param(
        [string]$CommitHash
    )

    Update-Java-Version-For-Maven
    Ensure-Dependencies-For-Maven -TempDir $TEMP_DIR
    Prepare-Directory-For-Maven-Build-And-Test -TempDir $TEMP_DIR -ArchTestDir "use-gui\src\test\java\org\tzi\use\architecture" -ArchTestName "MavenLayeredArchitectureTest.java" -ArchTestContent $test_file_content    
    $mavenBuildSuccess = Maven-Build-And-Test -CommitHash $CommitHash

    return $mavenBuildSuccess
}

#########################################
# Setup starts here #
#########################################

# Initialize results file with header
Initialize-Results-File -FilePath $RESULTS_FILE -Header "date,time,commit,violations"
# Create and move to use copy
Setup-Repo -TempDir $TEMP_DIR -OriginalUseDir $ORIGINAL_USE_DIR

#########################################
# Store ArchUnit Test #
#########################################

$test_file_content = Store-ArchTest -TestFilePath $TEST_FILE_PATH

#########################################
# Main Loop starts here #
#########################################

Verify-StartCommit -StartCommit $StartCommitHash

# Determine which commits to process
$CommitsToProcess = Determine-Commits-To-Process -StartCommitHash $StartCommitHash -StartLoop $StartLoop

$TOTAL_COMMITS = ($CommitsToProcess | Measure-Object).Count
$CURRENT_COMMIT = 1

try {
    foreach ($COMMIT in $CommitsToProcess) {
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"
        
        $previousCommit = git log -1 --format="%H" "$COMMIT^" 2>$null
        if ($LASTEXITCODE -ne 0) {
            $previousCommit = $null
        }

        $isFirstCommitInRepo = ($previousCommit -eq $null)
        $isStartCommit = ($StartCommitHash -ne "" -and $COMMIT -eq $StartCommitHash)
        $hasRelevantChanges = (Has-Relevant-Changes -CommitHash $COMMIT -PreviousCommitHash $previousCommit -RelevantPaths $RELEVANT_PATHS)

        if ($isFirstCommitInRepo -or $isStartCommit -or $hasRelevantChanges) {
            git checkout -q $COMMIT
            $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

            if($is_maven){
                $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT
                if (-not $commitProcessedSuccessfully) {
                    Log-Message "Build failed for commit $COMMIT"
                }
            } else {
                Log-Message "No pom.xml found. Skipping this commit..."
            }
        } else {
            Log-Message "No relevant changes in commit $COMMIT. Skipping..."
        }
        $CURRENT_COMMIT++
        Cleanup-Commit
    }
}
catch {
    Log-Message "Catch Block: An error occurred: $_"
    Log-Message "Stack Trace: $($_.ScriptStackTrace)"
}
finally {
    Final-Cleanup -ResultsDir $RESULTS_DIR -TempDir $TEMP_DIR
}