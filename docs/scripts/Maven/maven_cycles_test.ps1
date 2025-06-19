# JAVA_HOME will need to be set to Java 21
# First Maven Commit is 132ab9b
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
$RESULTS_FILE = Join-Path $RESULTS_DIR "maven_cycles_test_results.csv"
$LOG_FILE = Join-Path $RESULTS_DIR "maven_cycles_test_log.txt"
$RELEVANT_PATHS = @(
    ("use-gui/src/"),
    ("use-core/src/")
)

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
        $header = "date,time,commit,all_modules_no_tests,all_modules_with_tests,analysis_no_tests,analysis_with_tests,api_no_tests,api_with_tests,config_no_tests,config_with_tests,gen_no_tests,gen_with_tests,graph_no_tests,graph_with_tests,main_no_tests,main_with_tests,parser_no_tests,parser_with_tests,uml_no_tests,uml_with_tests,util_no_tests,util_with_tests"
        $header | Out-File -FilePath $RESULTS_FILE -Encoding UTF8
        Log-Message "Created results file with header"
    }
}

# Records results to CSV in the new format
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

function Remove-BOM {
    param (
        [string]$FilePath
    )
    $full_path = Join-Path $TEMP_DIR $FilePath
    $content = [System.IO.File]::ReadAllBytes($full_path)
    if ($content.Length -ge 3 -and $content[0] -eq 0xEF -and $content[1] -eq 0xBB -and $content[2] -eq 0xBF) {
        $content = $content[3..$content.Length]
        [System.IO.File]::WriteAllBytes($full_path, $content)
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
# Dependency Functions #
#########################################

function Ensure-Dependencies-For-Maven {
    $pom_files = @("pom.xml", "use-core/pom.xml", "use-gui/pom.xml")
    
    foreach ($pom_file in $pom_files) {
        $full_path = Join-Path $TEMP_DIR $pom_file
        if (Test-Path $full_path) {
            Log-Message "Checking and adding dependencies in $full_path"
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
                $dependencies = $pom.project.dependencies
            }
            
            $archunit_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "archunit-junit5" }
            if (-not $archunit_present) {
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>com.tngtech.archunit</groupId><artifactId>archunit-junit5</artifactId><version>1.0.1</version>"
                $dependencies.AppendChild($new_dep)
            }

            $junit_jupiter_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "junit-jupiter" }
            if (-not $junit_jupiter_present) {
                $new_dep = $pom.CreateElement("dependency", $pom.DocumentElement.NamespaceURI)
                $new_dep.InnerXml = "<groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><version>5.8.2</version><scope>test</scope>"
                $dependencies.AppendChild($new_dep)
            }

            $slf4j_nop_present = $dependencies.dependency | Where-Object { $_.artifactId -eq "slf4j-nop" }
            if (-not $slf4j_nop_present) {
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

function Prepare-Directory-For-Maven-Build-And-Test {
    # Add architecture directory if it doesn't exist
    $test_file_dir = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture"
    if (-not (Test-Path $test_file_dir)) {
        New-Item -ItemType Directory -Force -Path $test_file_dir | Out-Null
        Log-Message "Created directory: $test_file_dir"
    }

    # Add archunit test to commit
    $test_file_path = Join-Path $test_file_dir "MavenCyclicDependenciesCoreTest.java"
    $test_file_content | Out-File -FilePath $test_file_path -Encoding UTF8
    Remove-BOM -FilePath "use-core\src\test\java\org\tzi\use\architecture\MavenCyclicDependenciesCoreTest.java"
    Log-Message "Added test to commit $COMMIT"

    # Add archunit.properties file
    $properties_file_path = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"
    $properties_file_content | Out-File -FilePath $properties_file_path -Encoding UTF8
    Remove-BOM -FilePath "use-core\src\test\resources\archunit.properties"
    Log-Message "Added archunit.properties file to commit $COMMIT"
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
    
    git checkout -q $CommitHash
    $is_maven = Test-Path (Join-Path $TEMP_DIR "pom.xml")

    if (-not $is_maven) {
        Log-Message "No pom.xml found. Skipping this commit..."
        return $false
    }

    Ensure-Dependencies-For-Maven
    Prepare-Directory-For-Maven-Build-And-Test
    $mavenBuildSuccess = Maven-Build-And-Test -CommitHash $CommitHash

    # Clean up after processing
    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
    return $mavenBuildSuccess
}


#########################################
# Setup starts here #
#########################################

# Initialize results file with header
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
# Comment in for debugging
#$gitOutput | ForEach-Object { Log-Message "Git output: $_" }

if ($LASTEXITCODE -ne 0) {
    Log-Message "Failed to clone repository. Exit code: $LASTEXITCODE"
    exit 1
}
else {
    Log-Message "Repository cloned successfully."
}

#########################################
# Store ArchUnit Test #
#########################################

git checkout -q master

$test_file_path = Join-Path $TEMP_DIR "use-core\src\test\java\org\tzi\use\architecture\MavenCyclicDependenciesCoreTest.java"
$properties_file_path = Join-Path $TEMP_DIR "use-core\src\test\resources\archunit.properties"

if (-not (Test-Path $test_file_path) -or -not (Test-Path $properties_file_path)) {
    Log-Message "Required files not found in master branch. Exiting."
    exit 1
}

$test_file_content = Get-Content $test_file_path -Raw -Encoding UTF8
$properties_file_content = Get-Content $properties_file_path -Raw -Encoding UTF8

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
    git log --first-parent --reverse --format="%H"
} elseif ($StartLoop.ToLower() -eq "n") {
    # Process only the specified commit
    @($StartCommitHash)
} else {
    # Process from the specified commit onwards
    git log --first-parent --reverse --format="%H" "$StartCommitHash^..HEAD"
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
        $isStartCommit = ($StartCommitHash -ne "" -and $COMMIT -eq $StartCommitHash)
        if ($previousCommit -eq $null -or $isStartCommit -or (Has-RelevantChanges -CommitHash $COMMIT -PreviousCommitHash $previousCommit)) {
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
            Log-Message "No relevant changes in commit $COMMIT. Using previous cycle counts."
            if ($previousCycleCounts.Count -gt 0) {
                Record-Result -CommitHash $COMMIT -CycleCounts $previousCycleCounts
            }
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