package view.gameplay;

import model.App;
import model.Chapter;
import model.GameplaySession;
import model.Level;
import model.entity.zombie.Zombie;
import model.enums.Menu;
import model.factory.PlantFactory;
import model.factory.ZombieFactory;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameplayMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Gameplay: chapters | levels <chapter> | start [chapter] [level] | board");
        System.out.println("plant <basic|shooter|slow> <row> <col>");
        System.out.println("tick <count> | sun <row> <col> | pluck <row> <col> | end | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("chapters")) {
            printChapters();
        } else if (parts.length == 2 && parts[0].equals("levels")) {
            printLevels(parts[1]);
        } else if (input.equals("start game")) {
            start(new String[]{"start"});
        } else if (input.equals("start zombie waves")) {
            tick("1");
        } else if (parts[0].equals("start")) {
            start(parts);
        } else if (input.equals("board") || input.equals("show map")) {
            printBoard();
        } else if (input.equals("show sun amount")) {
            printSunAmount();
        } else if (input.equals("show plants status")) {
            printPlantStatus();
        } else if (input.equals("zombies info")) {
            printZombieInfo();
        } else if (input.startsWith("show tile status")) {
            printTileStatus(input);
        } else if (input.startsWith("plant plant")) {
            plantOfficial(input);
        } else if (parts.length == 4 && parts[0].equals("plant")) {
            plant(parts);
        } else if (input.startsWith("pluck plant")) {
            pluckOfficial(input);
        } else if (parts.length == 2 && parts[0].equals("tick")) {
            tick(parts[1]);
        } else if (parts.length == 3 && parts[0].equals("sun")) {
            collectSun(parts[1], parts[2]);
        } else if (input.startsWith("feed plant")) {
            feedPlant(input);
        } else if (parts.length == 3 && parts[0].equals("pluck")) {
            pluck(parts[1], parts[2]);
        } else if (input.startsWith("cheat add -n") && input.endsWith("suns")) {
            addSuns(parts);
        } else if (input.startsWith("cheat spawn-zombie")) {
            spawnZombie(input);
        } else if (input.equals("cheat add-plant-food")) {
            addPlantFood();
        } else if (input.equals("end")) {
            App.endSession();
            System.out.println("game ended");
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void printChapters() {
        for (Chapter chapter : App.getAdventureChapters()) {
            String status = chapter.isUnlocked() ? "unlocked" : "locked";
            System.out.printf("%d. %s (%s)%n", chapter.getChapterNumber(), chapter.getName(), status);
        }
    }

    private void printLevels(String chapterText) {
        try {
            Chapter chapter = App.getAdventureChapter(Integer.parseInt(chapterText));
            if (chapter == null) {
                System.out.println("chapter not found");
                return;
            }
            System.out.println(chapter.getName());
            for (Level level : chapter.getLevels()) {
                System.out.printf("%d. %s - waves: %d - reward: %d coins, %d gems%n",
                        level.getLevelNumber(),
                        level.getType().getDisplayName(),
                        level.getNumWaves(),
                        level.getRewardCoins(),
                        level.getRewardGems());
            }
        } catch (NumberFormatException exception) {
            System.out.println("chapter must be a number");
        }
    }

    private void start(String[] parts) {
        try {
            boolean started;
            if (parts.length == 1) {
                App.createNewSession(null, null, null);
                started = true;
            } else if (parts.length == 3) {
                started = App.createNewSession(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            } else {
                System.out.println("usage: start [chapter] [level]");
                return;
            }
            if (!started) {
                System.out.println("chapter or level not found");
                return;
            }
            GameplaySession session = App.getCurrentSession();
            System.out.printf("game started: %s level %d%n",
                    session.getChapter().getName(),
                    session.getLevel().getLevelNumber());
            System.out.println(session.getSessionState());
        } catch (NumberFormatException exception) {
            System.out.println("chapter and level must be numbers");
        }
    }

    private GameplaySession requireSession() {
        GameplaySession session = App.getCurrentSession();
        if (session == null) {
            System.out.println("no active game. use start first");
        }
        return session;
    }

    private void printBoard() {
        GameplaySession session = requireSession();
        if (session != null) {
            System.out.println(session.getSessionState());
        }
    }

    private void printSunAmount() {
        GameplaySession session = requireSession();
        if (session != null) {
            System.out.println("sun: " + session.getSunAmount());
        }
    }

    private void printPlantStatus() {
        GameplaySession session = requireSession();
        if (session != null) {
            for (var plant : session.getSelectedPlants()) {
                System.out.println(plant.getName()
                        + " | level=" + plant.getLevel()
                        + " ready=" + plant.isReady()
                        + " cost=" + plant.getSunCost());
            }
        }
    }

    private void printTileStatus(String input) {
        GameplaySession session = requireSession();
        int[] location = parseLocation(input);
        if (session == null || location == null) {
            System.out.println("location is invalid");
            return;
        }
        int x = location[0] - 1;
        int y = location[1] - 1;
        if (y < 0 || y >= session.getBoard().length || x < 0 || x >= session.getBoard()[y].length) {
            System.out.println("location is outside map");
            return;
        }
        var tile = session.getBoard()[y][x];
        String plant = tile.getPlant() == null ? "empty" : tile.getPlant().getName();
        System.out.println("tile " + location[0] + "," + location[1]
                + " plant=" + plant
                + " zombies=" + tile.getZombies().size());
    }

    private void printZombieInfo() {
        printZombie("basic");
        printZombie("fast");
        printZombie("tank");
    }

    private void printZombie(String type) {
        Zombie zombie = ZombieFactory.create(type);
        System.out.println(zombie.getType()
                + " | speed=" + zombie.getSpeed()
                + " damage=" + zombie.getDamage()
                + " health=" + zombie.getHealth()
                + " cost=" + zombie.getCostToSpawn()
                + " armor=" + zombie.getArmor().size());
    }

    private void plant(String[] parts) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        try {
            int row = Integer.parseInt(parts[2]) - 1;
            int col = Integer.parseInt(parts[3]) - 1;
            if (session.plantAt(PlantFactory.create(parts[1]), col, row)) {
                System.out.println("plant placed");
            } else {
                System.out.println("could not place plant");
            }
            System.out.println(session.getSessionState());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void plantOfficial(String input) {
        String[] parts = input.split("\\s+");
        String type = null;
        for (int i = 0; i < parts.length - 1; i++) {
            if ("-t".equals(parts[i])) {
                type = parts[i + 1];
            }
        }
        int[] location = parseLocation(input);
        if (type == null || location == null) {
            System.out.println("usage: plant plant -t <type> -l (<x>, <y>)");
            return;
        }
        plant(new String[]{"plant", type, Integer.toString(location[1]), Integer.toString(location[0])});
    }

    private void tick(String count) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        try {
            session.advanceTime(Integer.parseInt(count));
            System.out.println(session.getSessionState());
        } catch (NumberFormatException exception) {
            System.out.println("tick count must be a number");
        }
    }

    private void collectSun(String rowText, String colText) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            if (session.collectSun(col, row)) {
                System.out.println("sun collected");
            } else {
                System.out.println("could not collect sun");
            }
            System.out.println(session.getSessionState());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void pluck(String rowText, String colText) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            if (session.pluckAt(col, row)) {
                System.out.println("plant removed");
            } else {
                System.out.println("could not remove plant");
            }
            System.out.println(session.getSessionState());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void pluckOfficial(String input) {
        int[] location = parseLocation(input);
        if (location == null) {
            System.out.println("usage: pluck plant -l (<x>, <y>)");
            return;
        }
        pluck(Integer.toString(location[1]), Integer.toString(location[0]));
    }

    private void feedPlant(String input) {
        GameplaySession session = requireSession();
        int[] location = parseLocation(input);
        if (session == null || location == null) {
            System.out.println("usage: feed plant -l (<x>, <y>)");
            return;
        }
        if (session.usePlantFood(location[0] - 1, location[1] - 1)) {
            System.out.println("plant fed");
        } else {
            System.out.println("could not feed plant");
        }
        System.out.println(session.getSessionState());
    }

    private void addPlantFood() {
        GameplaySession session = requireSession();
        if (session != null) {
            session.addPlantFood(1);
            System.out.println("plant food added; you have " + session.getPlantFoodCount());
        }
    }

    private void addSuns(String[] parts) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        try {
            int amount = Integer.parseInt(parts[3]);
            session.addSun(amount);
            System.out.println("sun added; you have " + session.getSunAmount());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("usage: cheat add -n <count> suns");
        }
    }

    private void spawnZombie(String input) {
        GameplaySession session = requireSession();
        if (session == null) {
            return;
        }
        String[] parts = input.split("\\s+");
        String type = "basic";
        for (int i = 0; i < parts.length - 1; i++) {
            if ("-t".equals(parts[i])) {
                type = parts[i + 1];
            }
        }
        int[] location = parseLocation(input);
        if (location == null) {
            System.out.println("usage: cheat spawn-zombie -t <type> -l <x, y>");
            return;
        }
        if (session.spawnZombie(type, location[0] - 1, location[1] - 1)) {
            System.out.println("zombie spawned");
        } else {
            System.out.println("could not spawn zombie");
        }
        System.out.println(session.getSessionState());
    }

    private int[] parseLocation(String input) {
        Matcher matcher = Pattern.compile("-?\\d+").matcher(input);
        int[] values = new int[2];
        int count = 0;
        while (matcher.find() && count < 2) {
            values[count++] = Integer.parseInt(matcher.group());
        }
        return count == 2 ? values : null;
    }
}
