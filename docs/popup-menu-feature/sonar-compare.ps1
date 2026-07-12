<#
.SYNOPSIS
    Dumps SonarQube metrics for the popup-menu refactor, comparing the
    pre-refactor project (use-before) against the refactored project (use),
    both whole-project and for each file touched by the refactor.

.DESCRIPTION
    Queries the SonarQube Web API (api/measures/component) for two projects
    and writes a wide-format CSV (one row per item, before/after/delta columns
    per metric). Also prints the table to the console.

    Reproducibility check for the bachelor thesis case study. The numbers this
    produces are exactly what the SonarQube UI shows for each project.

.PARAMETER Token
    A SonarQube user/global token with read access to both projects.

.PARAMETER HostUrl
    Base URL of the local SonarQube server. Default: http://localhost:9000

.PARAMETER OutFile
    Path of the CSV to write. Default: sonar-comparison.csv next to this script.

.EXAMPLE
    ./sonar-compare.ps1 -Token sqa_xxx
    ./sonar-compare.ps1 -Token sqa_xxx -OutFile C:\thesis\appendix\metrics.csv
#>

[CmdletBinding()]
param(
    [string]$Token   = "sqa_226fdf4364de24b73ffa6a46733098a2a708a8d7",
    [string]$HostUrl = "http://localhost:9000",
    [string]$OutFile = "",
    [string]$BeforeKey = "use-before",   # project key of the pre-refactor scan
    [string]$AfterKey  = "use"           # project key of the refactored scan
)

$ErrorActionPreference = "Stop"

# Resolve default output path (avoids $PSScriptRoot being empty in some hosts).
if ([string]::IsNullOrEmpty($OutFile)) {
    $base = $PSScriptRoot
    if ([string]::IsNullOrEmpty($base)) { $base = Split-Path -Parent $MyInvocation.MyCommand.Path }
    if ([string]::IsNullOrEmpty($base)) { $base = (Get-Location).Path }
    $OutFile = Join-Path $base "sonar-comparison.csv"
}

# --- auth header (SonarQube uses token-as-username, empty password) ---
$authHeader = @{
    Authorization = "Basic " + [Convert]::ToBase64String(
        [Text.Encoding]::ASCII.GetBytes("$($Token):"))
}

# Metrics to report, in display order.
$metrics = @(
    "ncloc",
    "complexity",              # cyclomatic
    "cognitive_complexity",
    "code_smells",
    "duplicated_lines_density",
    "sqale_index"              # technical debt, in minutes
)
$metricKeys = $metrics -join ","

# Files touched by the refactor, as paths relative to the use-gui module root.
# The two new files have no "before" state (they show blank / delta = full value).
$files = @(
    "src/main/java/org/tzi/use/gui/views/diagrams/classdiagram/ClassDiagram.java",
    "src/main/java/org/tzi/use/gui/views/diagrams/objectdiagram/NewObjectDiagram.java",
    "src/main/java/org/tzi/use/gui/views/diagrams/DiagramView.java",
    "src/main/java/org/tzi/use/gui/views/diagrams/behavior/communicationdiagram/CommunicationDiagram.java",
    "src/main/java/org/tzi/use/gui/views/diagrams/statemachine/StateMachineDiagram.java",
    "src/main/java/org/tzi/use/gui/views/selection/classselection/ClassSelection.java",
    "src/main/java/org/tzi/use/gui/views/selection/objectselection/ObjectSelection.java",
    "src/main/java/org/tzi/use/gui/views/diagrams/PopupMenuBuilder.java",
    "src/main/java/org/tzi/use/gui/views/selection/ISelectionMenuProvider.java"
)

# Query api/measures/component for one component key; return a hashtable metric->value.
function Get-Measures([string]$componentKey) {
    $url = "$HostUrl/api/measures/component?component=$([uri]::EscapeDataString($componentKey))&metricKeys=$metricKeys"
    $result = @{}
    try {
        $resp = Invoke-RestMethod -Uri $url -Headers $authHeader
        foreach ($m in $resp.component.measures) { $result[$m.metric] = $m.value }
    } catch {
        # Component not analysed (e.g. a new file has no "before"): leave empty.
    }
    return $result
}

# Build one output row (before/after/delta per metric) for a labelled item.
function New-Row([string]$scope, [string]$item, [string]$beforeKey, [string]$afterKey) {
    $before = Get-Measures $beforeKey
    $after  = Get-Measures $afterKey
    $row = [ordered]@{ Scope = $scope; Item = $item }
    foreach ($m in $metrics) {
        $b = $before[$m]; $a = $after[$m]
        $delta = ""
        if ($b -ne $null -and $a -ne $null) {
            $delta = [math]::Round(([double]$a - [double]$b), 2)
        }
        $row["$($m)_before"] = $b
        $row["$($m)_after"]  = $a
        $row["$($m)_delta"]  = $delta
    }
    return [pscustomobject]$row
}

Write-Host "Querying SonarQube at $HostUrl ..." -ForegroundColor Cyan
Write-Host "  before project: $BeforeKey" -ForegroundColor DarkGray
Write-Host "  after  project: $AfterKey"  -ForegroundColor DarkGray
Write-Host ""

$rows = New-Object System.Collections.Generic.List[object]

# Whole-project row.
$rows.Add( (New-Row "PROJECT" "(whole project)" $BeforeKey $AfterKey) )

# Per-file rows. Component key format: <projectKey>:<module>/<path>
foreach ($f in $files) {
    $name = Split-Path $f -Leaf
    $rows.Add( (New-Row "FILE" $name "$($BeforeKey):use-gui/$f" "$($AfterKey):use-gui/$f") )
}

# --- console summary: the headline complexity & smells deltas ---
$rows |
    Select-Object Item,
        @{n="cyclomatic (B/A/d)"; e={ "$($_.complexity_before)/$($_.complexity_after)/$($_.complexity_delta)" }},
        @{n="cognitive (B/A/d)";  e={ "$($_.cognitive_complexity_before)/$($_.cognitive_complexity_after)/$($_.cognitive_complexity_delta)" }},
        @{n="smells (B/A/d)";     e={ "$($_.code_smells_before)/$($_.code_smells_after)/$($_.code_smells_delta)" }} |
    Format-Table -AutoSize

# --- full CSV for the thesis appendix ---
$rows | Export-Csv -Path $OutFile -NoTypeInformation -Encoding UTF8
Write-Host "Full metric table written to: $OutFile" -ForegroundColor Green
