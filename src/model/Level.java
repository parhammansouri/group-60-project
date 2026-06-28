package model;

import model.enums.LevelType;
import java.util.List;

/**
 * Represents a single level/stage in a chapter.
 * Contains wave configurations, reward information, and level-specific
 * mechanics.
 */
public class Level {
    private int levelNumber;
    private int chapterNumber;
    private LevelType type;
    private int numWaves;
    private List<Integer> waveZombieCosts;
    private int rewardCoins;
    private int rewardGems;
    private int rewardSeedPackets;
    private boolean isCompleted;
    private boolean isUnlocked;
    private int highScore;
    private int rows;
    private int cols;

    public int getLevelNumber() {
        // TODO: Implementation
        return levelNumber;
    }

    public int getChapterNumber() {
        // TODO: Implementation
        return chapterNumber;
    }

    public LevelType getType() {
        // TODO: Implementation
        return type;
    }

    public int getNumWaves() {
        // TODO: Implementation
        return numWaves;
    }

    /**
     * Get the difficulty cost for a specific wave
     */
    public int getWaveCost(int waveNumber) {
        // TODO: Implementation
        return 0;
    }

    public List<Integer> getWaveCosts() {
        // TODO: Implementation
        return waveZombieCosts;
    }

    public int getRewardCoins() {
        // TODO: Implementation
        return rewardCoins;
    }

    public int getRewardGems() {
        // TODO: Implementation
        return rewardGems;
    }

    public int getRewardSeedPackets() {
        // TODO: Implementation
        return rewardSeedPackets;
    }

    public boolean isCompleted() {
        // TODO: Implementation
        return isCompleted;
    }

    /**
     * Mark this level as completed
     */
    public void markCompleted() {
        // TODO: Implementation
    }

    public boolean isUnlocked() {
        // TODO: Implementation
        return isUnlocked;
    }

    /**
     * Unlock this level
     */
    public void unlock() {
        // TODO: Implementation
    }

    public int getHighScore() {
        // TODO: Implementation
        return highScore;
    }

    public void setHighScore(int score) {
        // TODO: Implementation
    }

    public int getRows() {
        // TODO: Implementation
        return rows;
    }

    public int getCols() {
        // TODO: Implementation
        return cols;
    }
}
