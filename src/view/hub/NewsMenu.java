package view.hub;

import model.App;
import model.enums.Menu;
import model.leaderboard.Leaderboard;
import model.leaderboard.LeaderboardEntry;
import view.AppMenu;

import java.util.Scanner;

public class NewsMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Leaderboard: score | progress | back | exit");
        String input = scanner.nextLine().trim();
        if (input.equals("score")) {
            printLeaderboard(new Leaderboard().sortedByHighScore());
        } else if (input.equals("progress")) {
            printLeaderboard(new Leaderboard().sortedByProgress());
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void printLeaderboard(Iterable<LeaderboardEntry> entries) {
        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            System.out.println(rank + ". " + entry.getNickname()
                    + " score=" + entry.getHighestScore()
                    + " progress=" + entry.getMaxChapter() + "-" + entry.getMaxLevel());
            rank++;
        }
        if (rank == 1) {
            System.out.println("leaderboard is empty");
        }
    }
}
