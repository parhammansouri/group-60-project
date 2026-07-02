$ErrorActionPreference = "Stop"

& "$PSScriptRoot\build.ps1"

if (Test-Path "data") {
    Remove-Item -LiteralPath "data" -Recurse -Force
}

$inputText = @"
register -u testuser -p pass123 pass123 -n TestUser -e test@example.com -g male
menu show current
menu logout
login -u testuser -p pass123 -stay-logged-in
menu enter profile
menu profile show-info
back
menu enter collection
menu collection show-plant -p shooter
buy seed_shooter
greenhouse
grow 1 1
boost 1 1
back
menu enter game
chapters
levels 1
start 1 2
show sun amount
plant plant -t shooter -l (1, 1)
tick 6
sun 2 7
end
back
menu enter network
play vasebreaker
quests
claim mini_start
back
menu enter news
news
read welcome
score
back
exit
"@

$secondInputText = @"
login
login -u testuser -p pass123
menu enter collection
greenhouse
harvest 1 1
back
menu enter network
quests
claim mini_start
back
exit
"@

$output = $inputText | java -cp out Main
$secondOutput = $secondInputText | java -cp out Main
$outputText = ($output + $secondOutput) -join [Environment]::NewLine
$outputText | Set-Content -Path "smoke-output.txt"

$required = @(
    "user registered successfully",
    "current menu: main",
    "logged in successfully",
    "username: testuser",
    "Shooter | sun=50",
    "purchase successful",
    "plant started growing",
    "plant boosted",
    "1. Ancient Egypt (unlocked)",
    "2. Regular Level",
    "game started: Ancient Egypt level 2",
    "plant placed",
    "sun collected",
    "minigame completed",
    "quest completed",
    "Welcome: The lawn defense campaign is now active.",
    "1. TestUser",
    "harvested",
    "quest already completed"
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
