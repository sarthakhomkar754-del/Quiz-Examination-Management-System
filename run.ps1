# PowerShell compilation and run script
Write-Host "[1/2] Compiling Quiz & Examination Management System..." -ForegroundColor Cyan
if (!(Test-Path -Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

$sources = (Get-ChildItem -Path "src" -Recurse -Filter *.java).FullName
javac -d bin $sources

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Compilation failed." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[2/2] Launching System..." -ForegroundColor Green
java -cp bin main.Main
