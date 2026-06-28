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

    public Minigame(int difficulty) {
        // TODO: Implementation - Set up game board with minigame rules
    }

    public MinigameType getType() {
        // TODO: Implementation
        return type;
    }

    public int getDifficulty() {
        // TODO: Implementation
        return difficulty;
    }

    public int getReward() {
        // TODO: Implementation
        return reward;
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

