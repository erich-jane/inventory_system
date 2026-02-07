<#
PowerShell helper to create inventory.db from db/inventory.sql
Usage:
  - Run in project root (or double click in Explorer):
      .\scripts\make_inventory_db.ps1
  - To automatically remove the SQL script after creating the DB add -RemoveSql switch:
      .\scripts\make_inventory_db.ps1 -RemoveSql

This script will try, in order:
  1) Run the Python script at scripts\create_inventory_db.py if Python is available (recommended)
  2) Use sqlite3.exe on PATH (if available) to execute the SQL script
  3) If neither is found, prints instructions and exits
#>

param(
    [switch]$RemoveSql
)

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path | Split-Path -Parent
$sqlFile = Join-Path $projectRoot "db\inventory.sql"
$dbFile = Join-Path $projectRoot "inventory.db"

if(-not (Test-Path $sqlFile)){
    Write-Error "SQL file not found: $sqlFile`nMake sure db\inventory.sql exists in the project."; exit 1
}

Write-Host "Creating SQLite DB at: $dbFile" -ForegroundColor Cyan

# Try Python first
$pythonCmds = @('py -3', 'python', 'python3')
$ran = $false
foreach($cmd in $pythonCmds){
    try{
        $ver = & $cmd -V 2>&1
        if($LASTEXITCODE -eq 0){
            Write-Host "Found Python: $cmd -> running create_inventory_db.py" -ForegroundColor Green
            Push-Location $projectRoot
            & $cmd .\scripts\create_inventory_db.py
            Pop-Location
            $ran = $true; break
        }
    } catch { }
}

if(-not $ran){
    # Try sqlite3 in PATH
    try{
        $s = Get-Command sqlite3 -ErrorAction Stop
        Write-Host "Found sqlite3 at: $($s.Path) -> executing SQL" -ForegroundColor Green
        Push-Location $projectRoot
        # Use cmd.exe redirection because PowerShell handles < differently
        $sqlitePath = $s.Path
        cmd.exe /c `""$sqlitePath" `"$dbFile`" < `"$sqlFile`"`"
        Pop-Location
        $ran = $true
    } catch {
        # Not found
    }
}

if(-not $ran){
    Write-Host "Could not find Python or sqlite3 on PATH." -ForegroundColor Yellow
    Write-Host "Options:" -ForegroundColor Yellow
    Write-Host "  1) Install Python (https://python.org) and run: py -3 .\scripts\create_inventory_db.py" -ForegroundColor White
    Write-Host "  2) Download sqlite3 tools (https://www.sqlite.org/download.html), put sqlite3.exe on PATH or into the project, then run:" -ForegroundColor White
    Write-Host "       sqlite3 inventory.db < db\inventory.sql" -ForegroundColor White
    exit 1
}

if(Test-Path $dbFile){
    Write-Host "Database created: $dbFile" -ForegroundColor Green
    if($RemoveSql){
        Write-Host "Removing SQL file: $sqlFile" -ForegroundColor Yellow
        Remove-Item $sqlFile -Force
        Write-Host "SQL file removed." -ForegroundColor Green
    } else {
        Write-Host "SQL file retained. To remove it automatically use -RemoveSql." -ForegroundColor Cyan
    }
} else {
    Write-Error "Database file was not created. Check output above for errors."; exit 1
}
