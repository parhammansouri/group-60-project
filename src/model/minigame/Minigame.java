package model.minigame;

import model.GameplaySession;

/**
 * Abstract base class for all minigames in the system.
 * Each minigame is a variant of the main game with unique rules and challenges.
 */
public abstract class Minigame extends GameplaySession {
    protected int difficulty;
    protected int reward;
    protected MinigameType type;
    protected boolean completed;
    protected boolean failed;

    public Minigame(int difficulty) {
        super();
        this.difficulty = Math.max(1, difficulty);
        this.reward = this.difficulty * 25;
        this.completed = false;
        this.failed = false;
    }

    public MinigameType getType() {
        return type;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getReward() {
        return reward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isFailed() {
        return failed;
    }

    /**
     * Initialize the minigame board with special rules
     */
    public abstract void initializeBoard();

    /**
     * Check if minigame win condition is met
     */
    public abstract boolean checkWinCondition();

    /**
     * Check if minigame lose condition is met
     */
    public abstract boolean checkLoseCondition();

    /**
     * Reset the minigame to initial state
     */
    public abstract void reset();
}
