param(
    [string]$OutputDirectory = "out"
)

$ErrorActionPreference = "Stop"

if (Test-Path $OutputDirectory) {
    Remove-Item -LiteralPath $OutputDirectory -Recurse -Force
}

Get-ChildItem -Path "src" -Recurse -Filter "*.java" |
    ForEach-Object { $_.FullName } |
    Set-Content -Path "sources.txt"

javac -d $OutputDirectory "@sources.txt"
Remove-Item -LiteralPath "sources.txt" -ErrorAction SilentlyContinue

Write-Host "Build completed in $OutputDirectory"
