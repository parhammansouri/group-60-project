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

    public LeaderboardEntry(String username, String nickname, int maxChapter, int maxLevel,
                            int minigamesCompleted, int dailyQuestsCompleted,
                            int totalQuestsCompleted, int highestScore) {
        this.username = username;
        this.nickname = nickname;
        this.maxChapter = maxChapter;
        this.maxLevel = maxLevel;
        this.minigamesCompleted = minigamesCompleted;
        this.dailyQuestsCompleted = dailyQuestsCompleted;
        this.totalQuestsCompleted = totalQuestsCompleted;
        this.highestScore = highestScore;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMaxChapter() {
        return maxChapter;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinigamesCompleted() {
        return minigamesCompleted;
    }

    public int getDailyQuestsCompleted() {
        return dailyQuestsCompleted;
    }

    public int getTotalQuestsCompleted() {
        return totalQuestsCompleted;
    }

    public int getHighestScore() {
        return highestScore;
    }

    /**
     * Update the max chapter and level
     */
    public void updateProgress(int chapter, int level) {
        if (chapter > maxChapter || chapter == maxChapter && level > maxLevel) {
            maxChapter = chapter;
            maxLevel = level;
        }
    }

    /**
     * Increment minigames completed
     */
    public void addMinigameCompletion() {
        minigamesCompleted++;
    }

    /**
     * Submit a new high score
     */
    public void submitScore(int score) {
        highestScore = Math.max(highestScore, score);
    }
}
