# Group 60 Project

A command-line Java implementation inspired by Plants vs. Zombies 2 for the Advanced Programming course project.

## Features

- Account signup, login, logout, profile, and settings
- Persistent user data in `data/users.tsv`
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
login
exit
```

Login:

```text
login <username> <password>
signup
exit
```

Main menu:

```text
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
start
board
plant <basic|shooter|slow> <row> <col>
tick <count>
sun <row> <col>
pluck <row> <col>
end
back
```

Collection, shop, and greenhouse:

```text
plants
shop
buy <itemId>
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
```

## Data Files

Runtime data is written under `data/`, which is ignored by Git.
