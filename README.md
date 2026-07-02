# Group 60 Project

A command-line Java implementation inspired by Plants vs. Zombies 2 for the Advanced Programming course project.

## Features

- Account signup, login, logout, profile, and settings
- Persistent user data in `data/users.tsv`
- Persistent profile, progress, news, quest, and greenhouse state
- Password hashing with SHA-256
- Adventure gameplay loop with board display, plants, zombies, waves, sun drops, lawn mowers, scoring, and rewards
- Plant collection, shop purchases, and unlockable plants
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
chapters
levels <chapter>
start
start game
start <chapter> <level>
board
show map
show sun amount
plant <basic|shooter|slow> <row> <col>
plant plant -t <type> -l (<x>, <y>)
tick <count>
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
shop
shop list
shop daily
buy <itemId>
menu collection purchase-plant -p <plant_name>
greenhouse
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
menu enter profile
menu profile show-info
back
menu enter collection
menu collection show-plant -p shooter
buy seed_shooter
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
```

## Data Files

Runtime data is written under `data/`, which is ignored by Git.
