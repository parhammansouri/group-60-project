package model.minigame.izombie;

import model.minigame.Minigame;
import model.minigame.MinigameType;

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
        this.type = MinigameType.I_ZOMBIE;
        reset();
    }

    public boolean placeZombie(String zombieType, int x, int y, int cost) {
        if (cost < 0 || sunAmount < cost || brainCount >= BRAINS_TO_WIN) {
            return false;
        }
        sunAmount -= cost;
        brainCount++;
        completed = checkWinCondition();
        return true;
    }

    @Override
    public void initializeBoard() {
        brainCount = 0;
    }

    @Override
    public boolean checkWinCondition() {
        return brainCount >= BRAINS_TO_WIN;
    }

    @Override
    public boolean checkLoseCondition() {
        return sunAmount <= 0 && brainCount < BRAINS_TO_WIN;
    }

    @Override
    public void reset() {
        sunAmount = INITIAL_SUN + difficulty * 25;
        completed = false;
        failed = false;
        initializeBoard();
    }
}
