# Group 60 Project

A command-line Java implementation inspired by Plants vs. Zombies 2 for the Advanced Programming course project.

## Features

- Account signup, login, logout, profile, and settings
- Persistent user data in `data/users.tsv`
- Persistent profile, progress, news, quest, and greenhouse state
- Password hashing with SHA-256
- Adventure gameplay loop with board display, plants, zombies, waves, sun drops, lawn mowers, scoring, and rewards
- Zombie drops for coins, diamonds, pots, and glowing-zombie plant food
- Plant collection, shop purchases, unlockable plants, and persistent loadouts
- Greenhouse with pots, growing, boosting, and harvesting
- Quests, minigames, and leaderboard
- Chapter, level, special-level mechanic, armor, sun, and wave models

## Requirements

- Java JDK 17 or newer
- PowerShell or any shell that can run `javac` and `java`

## Build

From the repository root:

```powershell
powershell -ExecutionPolicy Bypass -File ./scripts/build.ps1
```

## Run

```powershell
java -cp out Main
```

## Main Commands

Signup:

```text
signup <username> <password> <email> <nickname>
register -u <username> -p <password> <password_confirm> -n <nickname> -e <email> -g <gender>
login
exit
```

Login:

```text
login <username> <password>
login -u <username> -p <password> -stay-logged-in
signup
exit
```

Main menu:

```text
menu show current
menu enter <menu_name>
menu exit
menu logout
menu coin-wallet
menu gem-wallet
menu cheat add <n> <coin|diamond>
menu leaderboard
menu travel-log
menu profile
menu settings
menu collection
menu news
menu game
menu network
logout
exit
```

Gameplay:

```text
menu settings change-difficulty -l <difficulty_level>
chapters
levels <chapter>
start
start game
start <chapter> <level>
board
show map
show sun amount
cheat add -n <count> suns
cheat remove-cooldown
zombies info
cheat spawn-zombie -t <basic|fast|tank|weak|glowing> -l <x, y>
plant <basic|shooter|slow> <row> <col>
plant plant -t <type> -l (<x>, <y>)
tick <count>
start zombie waves
sun <row> <col>
pluck <row> <col>
pluck plant -l (<x>, <y>)
feed plant -l (<x>, <y>)
cheat add-plant-food
show plants status
show tile status -l (<x>, <y>)
end
back
```

Collection, shop, and greenhouse:

```text
plants
menu collection show-plants
menu collection show-plant -p <plant_name>
menu collection show-zombies
menu collection upgrade-plant -p <plant_name>
shop
shop list
shop daily
buy <itemId>
shop buy -i <item_id> -n <count> [-t <plant_type>]
menu collection purchase-plant -p <plant_name>
add plant -t <type>
remove plant -t <type>
boost plant -t <type>
greenhouse
show greenhouse
plant pot at (<x>, <y>)
unlock pot at (<x>, <y>)
grow <row> <col>
boost <row> <col>
harvest <row> <col>
back
```

Quests and minigames:

```text
quests
claim <questId>
minigames
play <vasebreaker|bowling|izombie>
back
```

News and leaderboard:

```text
news
menu news show-unread
menu news show-all
read <id>
score
progress
back
```

## Smoke Test

Run the automated smoke test:

```powershell
powershell -ExecutionPolicy Bypass -File ./scripts/smoke-test.ps1
```

The same flow is also checked by GitHub Actions on push and pull request.

Manual smoke-test input:

```text
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
```

## Data Files

Runtime data is written under `data/`, which is ignored by Git.
