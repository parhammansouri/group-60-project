package view.gameplay;

import model.App;
import model.User;
import model.enums.Menu;
import model.minigame.Minigame;
import model.minigame.izombie.IZombie;
import model.minigame.vasebreaker.Vasebreaker;
import model.minigame.wallnutbowling.WallnutBowling;
import model.quest.Quest;
import model.quest.QuestBook;
import view.AppMenu;

import java.util.Scanner;

public class NetworkMenu implements AppMenu {
    private final QuestBook questBook = new QuestBook();

    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("Extras: quests | claim <questId> | minigames | play <vasebreaker|bowling|izombie>");
        System.out.println("back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("quests")) {
            System.out.print(questBook.getStatus(user));
        } else if (parts.length == 2 && parts[0].equals("claim")) {
            claimQuest(user, parts[1]);
        } else if (input.equals("minigames")) {
            System.out.println("available: vasebreaker, bowling, izombie");
        } else if (parts.length == 2 && parts[0].equals("play")) {
            playMinigame(user, parts[1]);
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void claimQuest(User user, String questId) {
        Quest quest = questBook.getQuest(questId);
        if (quest == null) {
            System.out.println("quest not found");
            return;
        }
        if (!quest.areConditionsMet(user)) {
            System.out.println("quest is not ready");
            return;
        }
        quest.markCompleted();
        user.addQuestCompletion();
        user.addCoins(25);
        App.saveGameState();
        System.out.println("quest completed: +" + 25 + " coins");
    }

    private void playMinigame(User user, String minigameName) {
        Minigame minigame = createMinigame(minigameName, user.getDifficultyLevel());
        if (minigame == null) {
            System.out.println("unknown minigame");
            return;
        }
        completeMinigame(minigame);
        if (minigame.isCompleted()) {
            user.addMinigameCompletion();
            user.addCoins(minigame.getReward());
            user.submitScore(user.getHighestScore() + minigame.getReward());
            App.saveGameState();
            System.out.println("minigame completed: " + minigame.getType()
                    + " reward=" + minigame.getReward());
        } else {
            System.out.println("minigame failed");
        }
    }

    private Minigame createMinigame(String name, int difficulty) {
        return switch (name.toLowerCase()) {
            case "vasebreaker" -> new Vasebreaker(difficulty);
            case "bowling", "wallnut", "wallnutbowling" -> new WallnutBowling(difficulty);
            case "izombie", "i_zombie" -> new IZombie(difficulty);
            default -> null;
        };
    }

    private void completeMinigame(Minigame minigame) {
        if (minigame instanceof Vasebreaker vasebreaker) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    vasebreaker.breakVase(x, y);
                }
            }
        } else if (minigame instanceof WallnutBowling wallnutBowling) {
            while (!wallnutBowling.isCompleted() && wallnutBowling.rollWallnut()) {
                // Roll until the simple win target is reached.
            }
        } else if (minigame instanceof IZombie iZombie) {
            while (!iZombie.isCompleted() && iZombie.placeZombie("basic", 0, 0, 25)) {
                // Place zombies until all brains are reached.
            }
        }
    }
}
