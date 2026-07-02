package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import model.leaderboard.Leaderboard;
import model.leaderboard.LeaderboardEntry;
import view.AppMenu;

import java.util.Scanner;

public class MainMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Main menu: menu <profile|settings|collection|news|game|network> | logout | exit");
        String input = scanner.nextLine().trim();

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("logout")) {
            App.logout();
        } else if (input.equals("menu coin-wallet")) {
            printWallet("coins");
        } else if (input.equals("menu gem-wallet")) {
            printWallet("diamonds");
        } else if (input.equals("menu leaderboard")) {
            printLeaderboard();
        } else if (input.equals("menu travel-log")) {
            printTravelLog();
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else if (input.equals("menu profile")) {
            App.setCurrentMenu(Menu.Profile);
        } else if (input.equals("menu settings")) {
            App.setCurrentMenu(Menu.Settings);
        } else if (input.equals("menu collection")) {
            App.setCurrentMenu(Menu.Collection);
        } else if (input.equals("menu news")) {
            App.setCurrentMenu(Menu.News);
        } else if (input.equals("menu game")) {
            App.setCurrentMenu(Menu.Game);
        } else if (input.equals("menu network")) {
            App.setCurrentMenu(Menu.Network);
        } else {
            System.out.println("invalid command");
        }
    }

    private void printWallet(String type) {
        User user = App.getLoggedInUser();
        if (user == null) {
            System.out.println("login required");
            return;
        }
        if ("coins".equals(type)) {
            System.out.println("coins: " + user.getCoins());
        } else {
            System.out.println("diamonds: " + user.getGems());
        }
    }

    private void printLeaderboard() {
        int rank = 1;
        for (LeaderboardEntry entry : new Leaderboard().sortedByHighScore()) {
            System.out.println(rank + ". " + entry.getNickname()
                    + " score=" + entry.getHighestScore()
                    + " progress=" + entry.getMaxChapter() + "-" + entry.getMaxLevel());
            rank++;
        }
        if (rank == 1) {
            System.out.println("leaderboard is empty");
        }
    }

    private void printTravelLog() {
        User user = App.getLoggedInUser();
        if (user == null) {
            System.out.println("login required");
            return;
        }
        System.out.println("travel log");
        System.out.println("progress: chapter " + user.getMaxChapter() + " level " + user.getMaxLevel());
        System.out.println("minigames completed: " + user.getMinigamesCompleted());
        System.out.println("quests completed: " + user.getQuestsCompleted());
        System.out.println("highest score: " + user.getHighestScore());
    }
}
