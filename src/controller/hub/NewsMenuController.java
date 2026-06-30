package controller.hub;

import model.leaderboard.Leaderboard;
import model.leaderboard.LeaderboardEntry;

import java.util.List;

public class NewsMenuController {
    public List<LeaderboardEntry> leaderboardByScore() {
        return new Leaderboard().sortedByHighScore();
    }

    public List<LeaderboardEntry> leaderboardByProgress() {
        return new Leaderboard().sortedByProgress();
    }
}
