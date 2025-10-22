# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# First Maven Commit is 132ab9b
param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

. ".\general_utilities.ps1"
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
    Join-Path (Get-Location) "../../.."}

# Validate that the original use directory exists
if (-not (Test-Path $ORIGINAL_USE_DIR)) {
    Log-Message "Error: Original USE directory not found at: $ORIGINAL_USE_DIR"
    Log-Message "Please specify the correct path using -OriginalUseDir parameter"
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
$LOG_FILE = Join-Path $RESULTS_DIR "test_log_build_time_history.txt"

#########################################
# Functions start here #
#########################################

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
        Record-Simple-Result -CommitHash $CommitHash -TestResult $buildTimeSec -ResultsFile $RESULTS_FILE
        return $true
    } else {
        return $false
    }
}

function Process-Commit {
    param(
        [string]$CommitHash
    )

    Update-Java-Version-For-Maven
    Ensure-Dependencies-For-Maven -TempDir $TEMP_DIR
    $mavenBuildSuccess = Measure-Build-Time -CommitHash $CommitHash
    Cleanup-Commit
    
    return $mavenBuildSuccess
}

#########################################
# Setup starts here #
#########################################

# Initialize results file with headers
Initialize-Results-File -FilePath $RESULTS_DIR -Header "date,time,commit,buildtime"

Setup-Repo -TempDir $TEMP_DIR -OriginalUseDir $ORIGINAL_USE_DIR
git checkout -q master

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
        
        git checkout -q $COMMIT
        $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

        if($is_maven) {
            $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT
            if (-not $commitProcessedSuccessfully) {
                Log-Message "Build failed for commit $COMMIT"
                Record-Simple-Result -CommitHash $COMMIT -TestResult -1 -ResultsFile $RESULTS_FILE
            }
        } else {
            Log-Message "No pom.xml found. Skipping this commit..."
        }
        $CURRENT_COMMIT++
    }
}
catch {
    Log-Message "Catch Block: An error occurred: $_"
    Log-Message "Stack Trace: $($_.ScriptStackTrace)"
}
finally {
    Final-Cleanup -ResultsDir $RESULTS_DIR -TempDir $TEMP_DIR
}