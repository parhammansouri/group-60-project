package model.minigame.vasebreaker;

import model.minigame.Minigame;

/**
 * Vasebreaker minigame: Break vases to find plants or zombies
 */
public class Vasebreaker extends Minigame {
    private Vase[][] vases;
    private int vasesRemaining;

    public Vasebreaker(int difficulty) {
        super(difficulty);
        // TODO: Implementation
    }

    public Vase breakVase(int x, int y) {
        // TODO: Implementation
        return null;
    }

    @Override
    public void initializeBoard() {
        // TODO: Implementation - Populate vases with random contents
    }

    @Override
    public boolean checkWinCondition() {
        // TODO: Implementation - All vases broken, all zombies killed
        return false;
    }

    @Override
    public boolean checkLoseCondition() {
        // TODO: Implementation - Zombie reached end
        return false;
    }

    @Override
    public void reset() {
        // TODO: Implementation
    }
}

