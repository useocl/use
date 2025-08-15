# JAVA_HOME will need to be set to Java 14 / 8
# Erster Ant Commit 4cdc2d1

param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

. "..\general_utilities.ps1"
. ".\ant_utilities.ps1"

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
$RESULTS_FILE = Join-Path $RESULTS_DIR "ant_layer_violations_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "ant_layer_test_log.txt"

$RELEVANT_PATHS = @(("src/main"), ("src/gui"))
$TEST_FILE_PATH = Join-Path $TEMP_DIR "use-gui\src\test\java\org\tzi\use\architecture\AntLayeredArchitectureTest.java"

$ARCHUNIT_JUNIT_PATH = ""
$ARCHUNIT_CORE_PATH = ""
$SLF4J_API_PATH = ""
$SLF4J_SIMPLE_PATH = ""

#########################################
# Functions start here #
#########################################

function Parse-Test-Results {
    param(
        [string]$TestOutput
    )

    $violation_count = -1
    
    foreach ($line in $test_output) {
        if ($line -match "Number of violations: (\d+)") {
            $violation_count = [int]$matches[1]
            Log-Message "Found violation count: $violation_count"
            break
        }
    }
    return $violation_count
}

#########################################
# Building & Testing Functions #
#########################################

function Run-ArchUnit-Test {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Running ant test-junit for ArchUnit test..."
    $test_output = ant test-junit -Dtest.case="org.tzi.use.architecture.AntLayeredArchitectureTest" 2>&1  

    $violation_count = Parse-Test-Results -TestOutput $test_output
    
    if ($violation_count -ge 0) {
        Record-Simple-Result -CommitHash $CommitHash -TestResult $violation_count -ResultsFile $RESULTS_FILE
    } else {
        Log-Message "Failed to extract violation count from test output"
        Record-Simple-Result -CommitHash $CommitHash -ResultsFile $RESULTS_FILE
    }
}

function Process-Commit {
    param(
        [string]$CommitHash
    )
    
    Add-CombinatoricsLib-If-Missing
    Inject-File -TempDir $TEMP_DIR -RelativePath "src\test\org\tzi\use\architecture" -FileName "AntLayeredArchitectureTest.java" -FileContent $test_file_content
    Remove-Id-Tags
    Update-Java-Version
    Add-Dependencies-To-Lib -TempDir $TEMP_DIR
    Update-Dependencies-In-Buildxml
    Add-Missing-Test-Target-To-Buildxml

    if ($LASTEXITCODE -ne 0) {
        Log-Message "Build.xml injection failed. Skipping this commit..."
        Record-Simple-Result -CommitHash $CommitHash -ResultsFile $RESULTS_FILE
        return $false
    }
    
    Check-And-Fix-Bag-File
    Check-And-Fix-DiagramView-File    
    Check-And-Fix-ExtendedJTable-File
    Run-ArchUnit-Test -CommitHash $CommitHash
    return $true
}

#########################################
# Setup starts here #
#########################################

# Initialize results file with headers
Initialize-Results-File -FilePath $RESULTS_FILE -Header "date,time,commit,violations"
# Create and move to use copy
Setup-Repo -TempDir $TEMP_DIR -OriginalUseDir $ORIGINAL_USE_DIR
# Download dependencies once
Setup-Dependencies-In-Shared-Dir -ResultsDir $RESULTS_DIR

#########################################
# Store ArchUnit Test #
#########################################

$test_file_content = Store-ArchTest -TestFilePath $TEST_FILE_PATH

#########################################
# Main Loop starts here #
#########################################

Verify-StartCommit -StartCommit $StartCommitHash

$CommitsToProcess = Determine-Commits-To-Process -StartCommitHash $StartCommitHash -StartLoop $StartLoop
$TOTAL_COMMITS = ($CommitsToProcess | Measure-Object).Count
$CURRENT_COMMIT = 1

try {
    foreach ($COMMIT in $CommitsToProcess) {
        Log-Message "[$CURRENT_COMMIT/$TOTAL_COMMITS] Processing commit: $COMMIT"
        
        $previousCommit = git log -1 --format="%H" "$COMMIT^" 2>$null

        $isFirstCommitInRepo = ($previousCommit -eq $null)
        $isStartCommit = ($StartCommitHash -ne "" -and $COMMIT -eq $StartCommitHash)
        $hasRelevantChanges = (Has-Relevant-Changes -CommitHash $COMMIT -PreviousCommitHash $previousCommit -RelevantPaths $RELEVANT_PATHS)

        if($isFirstCommitInRepo -or $isStartCommit -or $hasRelevantChanges) {
            git checkout -q $COMMIT
            $is_ant = Test-Path (Join-Path $TEMP_DIR "build.xml")   
            $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

            if($is_ant -and (-not $is_maven)) {
                $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT
                if (-not $commitProcessedSuccessfully) {
                    Log-Message "Build failed for commit $COMMIT"
                }            
            } else {
                Log-Message "No build.xml found. Skipping this commit..."
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
    $dependencies_dir = Join-Path $RESULTS_DIR "dependencies"
    Final-Cleanup -ResultsDir $RESULTS_DIR -TempDir $dependencies_dir
}