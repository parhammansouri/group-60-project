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
menu coin-wallet
menu gem-wallet
menu cheat add 20 coin
menu cheat add 2 diamond
menu enter settings
menu settings change-difficulty -l 3
back
menu enter profile
menu profile show-info
back
menu enter collection
menu collection show-plant -p shooter
buy seed_shooter
shop buy -i seed_slow -n 1
add plant -t shooter
remove plant -t basic
boost plant -t shooter
show greenhouse
plant pot at (1, 1)
boost 1 1
back
menu enter game
chapters
levels 1
start 1 4
show sun amount
cheat add -n 25 suns
zombies info
cheat spawn-zombie -t tank -l 9, 1
cheat spawn-zombie -t glowing -l 5, 1
plant plant -t shooter -l (1, 1)
plant plant -t shooter -l (2, 1)
cheat remove-cooldown
plant plant -t shooter -l (2, 1)
start zombie waves
tick 5
sun 2 7
end
back
menu enter network
play vasebreaker
quests
claim mini_start
back
menu enter news
menu news show-unread
read welcome
menu news show-all
score
back
menu travel-log
menu leaderboard
exit
"@

$secondInputText = @"
login
login -u testuser -p pass123
menu enter collection
plants
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
    "coins: 100",
    "diamonds: 3",
    "coins added: 120",
    "diamonds added: 5",
    "difficulty updated",
    "username: testuser",
    "Shooter | sun=50",
    "purchase successful",
    "unlocked plants: basic, shooter, slow",
    "plant added to loadout",
    "plant removed from loadout",
    "plant boosted to level 2",
    "selected plants: shooter",
    "plant started growing",
    "plant boosted",
    "1. Ancient Egypt (unlocked)",
    "2. Regular Level",
    "4. Boss Level",
    "game started: Ancient Egypt level 4",
    "mechanic=boss",
    "sun added; you have 175",
    "Tank Zombie",
    "Glowing Zombie",
    "zombie spawned",
    "The glowing zombie dropped a plant food",
    "plant placed",
    "could not place plant",
    "plant cooldowns removed",
    "sun collected",
    "minigame completed",
    "quest completed",
    "Welcome: The lawn defense campaign is now active.",
    "1. TestUser",
    "travel log",
    "minigames completed: 1",
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
