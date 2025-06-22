# Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# JAVA_HOME will need to be set to Java 14 / 8
param(
    [string]$StartCommitHash = "",
    [string]$StartLoop = "y",
    [string]$OriginalUseDir = "",
    [string]$ResultsDir = ""
)

. ".\general_utilities.ps1"
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

function Record-Result {
    param(
        [string]$CommitHash,
        [hashtable]$CycleCounts
    )
    
    $commitDate = git show -s --format=%cd --date=format:"%Y-%m-%d" $CommitHash
    $commitTime = git show -s --format=%cd --date=format:"%H:%M:%S" $CommitHash
    
    $csvLine = "$commitDate,$commitTime,$CommitHash"
    
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
# Dependency Functions #
#########################################

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

#########################################
# Build & Execution Functions #
#########################################

function Execute-ArchUnit-Test {
    param(
        [string]$CommitHash
    )
    
    Log-Message "Executing ant test-junit for ArchUnit test..."
    $test_output = ant test-junit -Dtest.case="org.tzi.use.architecture.AntCyclicDependenciesCoreTest" 2>&1  
    
    # Parse metrics directly from test output
    $cycleMetrics = Parse-CycleResults -OutputLines $test_output
    
    if ($cycleMetrics.Count -gt 0) {
        Log-Message "Successfully parsed cycle metrics from output"
        Record-Result -CommitHash $CommitHash -CycleCounts $cycleMetrics
        return
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
        return
    }
}

function Process-Commit {
    param(
        [string]$CommitHash
    )
    Add-CombinatoricsLib-If-Missing
    Inject-File -TempDir $TEMP_DIR -RelativePath "src\test\org\tzi\use\architecture" -FileName "AntCyclicDependenciesCoreTest.java" -FileContent $test_file_content
    Inject-File -TempDir $TEMP_DIR -RelativePath "src\test\resources" -FileName "archunit.properties" -FileContent $properties_file_content
    Remove-Id-Tags
    Update-Java-Version
    Add-Dependencies-To-Lib -TempDir $TEMP_DIR
    Update-Dependencies-In-Buildxml
    Add-Missing-Test-Target-To-Buildxml
    Check-Buildxml-Classpath
    Check-And-Fix-Bag-File
    Check-And-Fix-DiagramView-File
    Check-And-Fix-ExtendedJTable-File
    Execute-ArchUnit-Test -CommitHash $CommitHash
}

#########################################
# Setup starts here #
#########################################

# Initialize csv file with header
Initialize-Results-File -FilePath $RESULTS_FILE -Header "date,time,commit,all_modules,analysis,api,config,gen,graph,main,parser,uml,util"
# Create and move to use copy
Setup-Repo -TempDir $TEMP_DIR -OriginalUseDir $ORIGINAL_USE_DIR
# Download dependencies once
Setup-Dependencies-In-Shared-Dir -ResultsDir $RESULTS_DIR

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
            $is_ant = Test-Path (Join-Path $TEMP_DIR "build.xml")
            
            if ($is_ant) {
                Process-Commit -CommitHash $COMMIT
            } else {
                Log-Message "No build.xml found. Skipping this commit..."
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
    $dependencies_dir = Join-Path $RESULTS_DIR "dependencies"
    Final-Cleanup -ResultsDir $RESULTS_DIR -TempDir $dependencies_dir
}