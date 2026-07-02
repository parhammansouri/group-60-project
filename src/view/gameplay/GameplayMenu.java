package view.gameplay;

import model.App;
import model.Chapter;
import model.GameplaySession;
import model.Level;
import model.enums.Menu;
import model.factory.PlantFactory;
import view.AppMenu;

import java.util.Scanner;

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
        } else if (parts[0].equals("start")) {
            start(parts);
        } else if (input.equals("board")) {
            printBoard();
        } else if (parts.length == 4 && parts[0].equals("plant")) {
            plant(parts);
        } else if (parts.length == 2 && parts[0].equals("tick")) {
            tick(parts[1]);
        } else if (parts.length == 3 && parts[0].equals("sun")) {
            collectSun(parts[1], parts[2]);
        } else if (parts.length == 3 && parts[0].equals("pluck")) {
            pluck(parts[1], parts[2]);
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
}
