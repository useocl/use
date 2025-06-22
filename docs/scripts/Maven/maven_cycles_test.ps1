# JAVA_HOME will need to be set to Java 21
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
$RESULTS_FILE = Join-Path $RESULTS_DIR "maven_cycles_history.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "maven_cycles_test_log.txt"

$RELEVANT_PATHS = @(
    ("use-gui/src/"),
    ("use-core/src/")
)
$TEST_FILE_PATH = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture\MavenCyclicDependenciesCoreTest.java"
$PROPERTIES_FILE_PATH = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"

#########################################
# Functions start here #
#########################################

#########################################
# Util Functions #
#########################################

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
        "all_modules_no_tests", "all_modules_with_tests",
        "analysis_no_tests", "analysis_with_tests",
        "api_no_tests", "api_with_tests",
        "config_no_tests", "config_with_tests",
        "gen_no_tests", "gen_with_tests",
        "graph_no_tests", "graph_with_tests",
        "main_no_tests", "main_with_tests", 
        "parser_no_tests", "parser_with_tests",
        "uml_no_tests", "uml_with_tests",
        "util_no_tests", "util_with_tests"
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

function Parse-CycleResults {
    param(
        [string[]]$OutputLines
    )
    
    $metrics = @{}
    
    # Regex patterns to extract cycle counts from stdout
    $patternNoTests = "Number of cycles in (org\.tzi\.use(?:\.\w+)?) without tests: (\d+)"
    $patternWithTests = "Number of cycles in (org\.tzi\.use(?:\.\w+)?) with tests: (\d+)"
    $patternCoreNoTests = "Cycles in core module without tests : (\d+)"
    $patternCoreWithTests = "Cycles in core module with tests : (\d+)"
    
    foreach ($line in $OutputLines) {
        # Match for core module counts (all_modules)
        if ($line -match $patternCoreNoTests) {
            $metrics["all_modules_no_tests"] = [int]$matches[1]
        }
        elseif ($line -match $patternCoreWithTests) {
            $metrics["all_modules_with_tests"] = [int]$matches[1]
        }
        # Match for specific package counts
        elseif ($line -match $patternNoTests) {
            $package = $matches[1] -replace "org\.tzi\.use\.", ""
            # Handle root package
            if ($package -eq "org.tzi.use") {
                $package = "all_modules"
            }
            $metrics["$($package)_no_tests"] = [int]$matches[2]
        }
        elseif ($line -match $patternWithTests) {
            $package = $matches[1] -replace "org\.tzi\.use\.", ""
            # Handle root package
            if ($package -eq "org.tzi.use") {
                $package = "all_modules"
            }
            $metrics["$($package)_with_tests"] = [int]$matches[2]
        }
    }
    return $metrics
}

#########################################
# Build & Execution Functions #
#########################################

function Run-ArchUnit-Test {
    Log-Message "Running ArchUnit test..."
    $test_output = mvn -pl use-core test "-Dtest=org.tzi.use.architecture.MavenCyclicDependenciesCoreTest#count_cycles_in_core_without_tests" -DfailIfNoTests=false 2>&1    
    
    # Parse metrics directly from test output
    $cycleMetrics = Parse-CycleResults -OutputLines $test_output
    
    if ($cycleMetrics.Count -gt 0) {
        Log-Message "Successfully parsed cycle metrics from output"
        return $cycleMetrics
    } else {
        Log-Message "No cycle metrics found in test output"
        return @{}
    }
}

function Maven-Build-And-Test {
    param(
        [string]$CommitHash
    )
    
    $compile_output = mvn clean compile 2>&1
    if ($LASTEXITCODE -ne 0) {
        Log-Message "Maven compilation failed. Error output:"
        $compile_output | ForEach-Object { Log-Message $_ }
        return $false
    }
    
    $cycleMetrics = Run-ArchUnit-Test
    
    if ($cycleMetrics.Count -gt 0) {
        Record-Result -CommitHash $CommitHash -CycleCounts $cycleMetrics
        return $true
    } else {
        return $false
    }
}

function Process-Commit {
    param(
        [string]$CommitHash,
        [hashtable]$PreviousCycleCounts
    )

    Update-Java-Version-For-Maven
    Ensure-Dependencies-For-Maven -TempDir $TEMP_DIR
    Prepare-Directory-For-Maven-Build-And-Test -TempDir $TEMP_DIR -ArchTestDir "use-core\src\test\java\org\tzi\use\architecture" -ArchTestName "MavenCyclicDependenciesCoreTest.java" -ArchTestContent $test_file_content -PropertiesPath "use-core\src\test\resources\archunit.properties" -PropertiesContent $properties_file_content
    $mavenBuildSuccess = Maven-Build-And-Test -CommitHash $CommitHash

    return $mavenBuildSuccess
}


#########################################
# Setup starts here #
#########################################

# Initialize results file with header
Initialize-Results-File -FilePath $RESULTS_FILE -Header "date,time,commit,all_modules_no_tests,all_modules_with_tests,analysis_no_tests,analysis_with_tests,api_no_tests,api_with_tests,config_no_tests,config_with_tests,gen_no_tests,gen_with_tests,graph_no_tests,graph_with_tests,main_no_tests,main_with_tests,parser_no_tests,parser_with_tests,uml_no_tests,uml_with_tests,util_no_tests,util_with_tests"
# Create and move to use copy
Setup-Repo -TempDir $TEMP_DIR -OriginalUseDir $ORIGINAL_USE_DIR

#########################################
# Store ArchUnit Test #
#########################################

$test_file_content = Store-ArchTest -TestFilePath $TEST_FILE_PATH
$properties_file_content = Store-ArchTest -TestFilePath $PROPERTIES_FILE_PATH

#########################################
# Main Loop starts here #
#########################################

Verify-StartCommit -StartCommit $StartCommitHash

# Determine which commits to process
$CommitsToProcess = Determine-Commits-To-Process -StartCommitHash $StartCommitHash -StartLoop $StartLoop

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

        $isFirstCommitInRepo = ($previousCommit -eq $null)
        $isStartCommit = ($StartCommitHash -ne "" -and $COMMIT -eq $StartCommitHash)
        $hasRelevantChanges = (Has-Relevant-Changes -CommitHash $COMMIT -PreviousCommitHash $previousCommit -RelevantPaths $RELEVANT_PATHS)

        if ($isFirstCommitInRepo -or $isStartCommit -or $hasRelevantChanges) {
            git checkout -q $COMMIT
            $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

            if ($is_maven) {
                $commitProcessedSuccessfully = Process-Commit -CommitHash $COMMIT -PreviousCycleCounts $previousCycleCounts
                if (-not $commitProcessedSuccessfully) {
                    $failureMetrics = @{}
                    $metrics = @(
                        "all_modules_no_tests", "all_modules_with_tests",
                        "analysis_no_tests", "analysis_with_tests",
                        "api_no_tests", "api_with_tests",
                        "config_no_tests", "config_with_tests",
                        "gen_no_tests", "gen_with_tests",
                        "graph_no_tests", "graph_with_tests",
                        "main_no_tests", "main_with_tests", 
                        "parser_no_tests", "parser_with_tests",
                        "uml_no_tests", "uml_with_tests",
                        "util_no_tests", "util_with_tests"
                    )
                    foreach ($metric in $metrics) {
                        $failureMetrics[$metric] = -1
                    }
                    Record-Result -CommitHash $COMMIT -CycleCounts $failureMetrics
                }
            } else {
                Log-Message "No pom.xml found. Skipping this commit..."
            }
        } else {
            Log-Message "No relevant changes in commit $COMMIT. Using previous cycle counts."
            if ($previousCycleCounts.Count -gt 0) {
                Record-Result -CommitHash $COMMIT -CycleCounts $previousCycleCounts
            }
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