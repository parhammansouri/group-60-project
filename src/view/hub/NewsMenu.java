package view.hub;

import controller.hub.NewsMenuController;
import model.App;
import model.News;
import model.User;
import model.enums.Menu;
import model.leaderboard.LeaderboardEntry;
import view.AppMenu;

import java.util.Scanner;

public class NewsMenu implements AppMenu {
    private final NewsMenuController controller = new NewsMenuController();

    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("News: news | read <id> | score | progress | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");
        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("news")) {
            printNews(user);
        } else if (parts.length == 2 && parts[0].equals("read")) {
            readNews(user, parts[1]);
        } else if (input.equals("score")) {
            printLeaderboard(controller.leaderboardByScore());
        } else if (input.equals("progress")) {
            printLeaderboard(controller.leaderboardByProgress());
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void printNews(User user) {
        for (News news : controller.getNewsItems()) {
            String status = user.hasSeenNews(news) ? "read" : "new";
            System.out.println(news.getId() + " | " + news.getTitle() + " | " + status);
        }
    }

    private void readNews(User user, String id) {
        News news = controller.getNews(id);
        if (news == null) {
            System.out.println("news not found");
            return;
        }
        user.markNewsSeen(news);
        App.saveGameState();
        System.out.println(news.getTitle() + ": " + news.getMessage());
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
