package controller.hub;

import model.News;
import model.leaderboard.Leaderboard;
import model.leaderboard.LeaderboardEntry;

import java.util.ArrayList;
import java.util.List;

public class NewsMenuController {
    private final List<News> newsItems = new ArrayList<>();

    public NewsMenuController() {
        newsItems.add(new News("welcome", "Welcome", "The lawn defense campaign is now active."));
        newsItems.add(new News("shop", "Shop Open", "Seed packets and plant food are available in collection."));
        newsItems.add(new News("minigames", "Minigames", "Vasebreaker, Bowling, and I Zombie are available."));
    }

    public List<News> getNewsItems() {
        return newsItems;
    }

    public News getNews(String id) {
        for (News news : newsItems) {
            if (news.getId().equals(id)) {
                return news;
            }
        }
        return null;
    }

    public List<LeaderboardEntry> leaderboardByScore() {
        return new Leaderboard().sortedByHighScore();
    }

    public List<LeaderboardEntry> leaderboardByProgress() {
        return new Leaderboard().sortedByProgress();
    }
}
