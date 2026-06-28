package model.leaderboard;

/**
 * Represents a single player's entry on the leaderboard
 */
public class LeaderboardEntry {
    private String username;
    private String nickname;
    private int maxChapter;
    private int maxLevel;
    private int minigamesCompleted;
    private int dailyQuestsCompleted;
    private int totalQuestsCompleted;
    private int highestScore;

    public String getUsername() {
        // TODO: Implementation
        return username;
    }

    public String getNickname() {
        // TODO: Implementation
        return nickname;
    }

    public int getMaxChapter() {
        // TODO: Implementation
        return maxChapter;
    }

    public int getMaxLevel() {
        // TODO: Implementation
        return maxLevel;
    }

    public int getMinigamesCompleted() {
        // TODO: Implementation
        return minigamesCompleted;
    }

    public int getDailyQuestsCompleted() {
        // TODO: Implementation
        return dailyQuestsCompleted;
    }

    public int getTotalQuestsCompleted() {
        // TODO: Implementation
        return totalQuestsCompleted;
    }

    public int getHighestScore() {
        // TODO: Implementation
        return highestScore;
    }

    /**
     * Update the max chapter and level
     */
    public void updateProgress(int chapter, int level) {
        // TODO: Implementation
    }

    /**
     * Increment minigames completed
     */
    public void addMinigameCompletion() {
        // TODO: Implementation
    }

    /**
     * Submit a new high score
     */
    public void submitScore(int score) {
        // TODO: Implementation
    }
}
