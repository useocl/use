# Load with . ".\general_utilities.ps1"

function Log-Message {
    param([string]$message)
    Write-Host $message
    $message | Out-File -FilePath $LOG_FILE -Append
}

# Initialize CSV file with header for results
function Initialize-Results-File {
    param(
        [string]$FilePath,
        [string]$Header
    )
    
    if (-not (Test-Path $FilePath)) {
        $Header | Out-File -FilePath $FilePath -Encoding UTF8
        Log-Message "Created results file with header: $FilePath"
    }
}

function Record-Simple-Result {
    param(
        [string]$CommitHash,
        [int]$TestResult = -1,
        [string]$ResultsFile
    )
    
    # Get commit date and time
    $commitDate = git show -s --format=%cd --date=format:"%Y-%m-%d" $CommitHash
    $commitTime = git show -s --format=%cd --date=format:"%H:%M:%S" $CommitHash
    
    # Format the result
    $resultLine = "$commitDate,$commitTime,$CommitHash,$TestResult"
    $resultLine | Out-File -FilePath $ResultsFile -Append
}


function Remove-BOM {
    param(
        [string]$FilePath
    )
    $full_path = Join-Path $TEMP_DIR $FilePath
    $content = [System.IO.File]::ReadAllBytes($full_path)
    if ($content.Length -ge 3 -and $content[0] -eq 0xEF -and $content[1] -eq 0xBB -and $content[2] -eq 0xBF) {
        $content = $content[3..$content.Length]
        [System.IO.File]::WriteAllBytes($full_path, $content)
    }
}

# Compares two commit hashes and checks whether there are changes to the source code
function Has-Relevant-Changes {
    param(
        [string]$CommitHash,
        [string]$PreviousCommitHash,
        [string[]]$RelevantPaths
    )
    $changedFiles = git diff --name-only $PreviousCommitHash $CommitHash

    foreach ($file in $changedFiles) {
        foreach ($path in $RelevantPaths) {
            if ($file.StartsWith($path) -and $file.EndsWith(".java")) {
                return $true
            }
        }
    }
    return $false
}

function Cleanup-Commit {
    git reset -q --hard
    git clean -qfd
    Log-Message "#####################################################################################"
}

function Setup-Repo {
    param(
        [string]$TempDir,
        [string]$OriginalUseDir
    )
    # Create & move to temp directory
    New-Item -ItemType Directory -Force -Path $TempDir | Out-Null
    Set-Location $TempDir
    Log-Message "Created & moved to temporary directory: $TempDir"

    Log-Message "Attempting to clone repository from $OriginalUseDir"
    $gitOutput = & {
        $ErrorActionPreference = 'SilentlyContinue'
        git clone $OriginalUseDir . 2>&1
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
}

function Store-ArchTest {
    param(
        $TestFilePath
    )
    # In case we're not on master already
    git checkout -q master

    if (-not (Test-Path $TestFilePath)) {
        Log-Message "Required file $TestFilePath not found in master branch. Exiting."
        exit 1
    }

    $test_file_content = Get-Content $TestFilePath -Raw -Encoding UTF8
    return $test_file_content
}

function Verify-StartCommit {
    param(
        $StartCommit
    )
    # Verify that the start commit exists
    if ($StartCommitHash -ne "") {
        $commitExists = git cat-file -e "$StartCommitHash^{commit}" 2>$null
        if ($LASTEXITCODE -ne 0) {
            Log-Message "Start commit hash $StartCommitHash not found in repository. Exiting."
            exit 1
        }
        Log-Message "Found start commit $StartCommitHash"
    }
}

function Determine-Commits-To-Process {
    param(
        $StartCommitHash,
        $StartLoop
    )
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
    return $CommitsToProcess
}

function Final-Cleanup {
    param(
        $ResultsDir,
        $TempDir
    )
    Set-Location $ResultsDir
    if (Test-Path $TempDir) {
        Remove-Item -Recurse -Force $TempDir
        Log-Message "Cleaned up directory $TempDir"
    }
}

# Creates new architecture test dir, new ArchTest file and copies pre-stored test content into that file
# Alternatively same for properties file
function Inject-File {
    param(
        [string]$TempDir,
        [string]$RelativePath,
        [string]$FileName,
        [string]$FileContent
    )
    
    $target_dir = Join-Path $TempDir $RelativePath
    if (-not (Test-Path $target_dir)) {
        New-Item -ItemType Directory -Force -Path $target_dir | Out-Null
        Log-Message "Created directory: $target_dir"
    }

    $target_file = Join-Path $target_dir $FileName
    $utf8NoBomEncoding = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($target_file, $FileContent, $utf8NoBomEncoding)
    
    if (Test-Path $target_file) {
        Log-Message "Successfully added $FileName to commit"
    }
}