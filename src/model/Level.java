package model;

import model.enums.LevelType;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Level() {
        this(1, 1, LevelType.REGULAR, Arrays.asList(10, 18, 28), 100, 1, 0, 5, 9);
    }

    public Level(int levelNumber, int chapterNumber, LevelType type, List<Integer> waveZombieCosts,
                 int rewardCoins, int rewardGems, int rewardSeedPackets, int rows, int cols) {
        this.levelNumber = levelNumber;
        this.chapterNumber = chapterNumber;
        this.type = type == null ? LevelType.REGULAR : type;
        this.waveZombieCosts = new ArrayList<>(waveZombieCosts);
        this.numWaves = this.waveZombieCosts.size();
        this.rewardCoins = rewardCoins;
        this.rewardGems = rewardGems;
        this.rewardSeedPackets = rewardSeedPackets;
        this.isCompleted = false;
        this.isUnlocked = true;
        this.highScore = 0;
        this.rows = rows;
        this.cols = cols;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public LevelType getType() {
        return type;
    }

    public int getNumWaves() {
        return numWaves;
    }

    /**
     * Get the difficulty cost for a specific wave
     */
    public int getWaveCost(int waveNumber) {
        if (waveNumber < 1 || waveNumber > waveZombieCosts.size()) {
            return 0;
        }
        return waveZombieCosts.get(waveNumber - 1);
    }

    public List<Integer> getWaveCosts() {
        return waveZombieCosts;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

    public int getRewardGems() {
        return rewardGems;
    }

    public int getRewardSeedPackets() {
        return rewardSeedPackets;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Mark this level as completed
     */
    public void markCompleted() {
        isCompleted = true;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    /**
     * Unlock this level
     */
    public void unlock() {
        isUnlocked = true;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        highScore = Math.max(highScore, score);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
