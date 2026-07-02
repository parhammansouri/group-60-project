$ErrorActionPreference = "Stop"

& "$PSScriptRoot\build.ps1"

if (Test-Path "data") {
    Remove-Item -LiteralPath "data" -Recurse -Force
}

$inputText = @"
signup testuser pass123 test@example.com TestUser
menu collection
buy seed_shooter
back
menu game
start
plant shooter 1 1
tick 6
sun 2 7
end
back
menu network
play vasebreaker
quests
claim mini_start
back
menu news
news
read welcome
score
back
exit
"@

$output = $inputText | java -cp out Main
$outputText = $output -join [Environment]::NewLine
$outputText | Set-Content -Path "smoke-output.txt"

$required = @(
    "user registered successfully",
    "purchase successful",
    "game started",
    "sun collected",
    "minigame completed",
    "quest completed",
    "Welcome: The lawn defense campaign is now active.",
    "1. TestUser"
)

foreach ($line in $required) {
    if ($outputText -notmatch [regex]::Escape($line)) {
        Write-Host $outputText
        throw "Smoke test failed: missing '$line'"
    }
}

Remove-Item -LiteralPath "smoke-output.txt" -ErrorAction SilentlyContinue
Remove-Item -LiteralPath "data" -Recurse -Force -ErrorAction SilentlyContinue

Write-Host "Smoke test passed"
