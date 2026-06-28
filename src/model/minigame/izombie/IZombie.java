package model.minigame.izombie;

import model.minigame.Minigame;

/**
 * I Zombie minigame: Play as zombies attacking plants
 */
public class IZombie extends Minigame {
    private int sunAmount;
    private int brainCount;
    private static final int INITIAL_SUN = 150;
    private static final int BRAINS_TO_WIN = 5;

    public IZombie(int difficulty) {
        super(difficulty);
        // TODO: Implementation
    }

    public boolean placeZombie(String zombieType, int x, int y, int cost) {
        // TODO: Implementation
        return false;
    }

    @Override
    public void initializeBoard() {
        // TODO: Implementation - Initialize with random plants
    }

    @Override
    public boolean checkWinCondition() {
        // TODO: Implementation - All brains eaten (5)
        return false;
    }

    @Override
    public boolean checkLoseCondition() {
        // TODO: Implementation - All zombies dead and no sun left
        return false;
    }

    @Override
    public void reset() {
        // TODO: Implementation
    }
}
