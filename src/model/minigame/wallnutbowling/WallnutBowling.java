package model.minigame.wallnutbowling;

import model.minigame.Minigame;
import model.minigame.MinigameType;

/**
 * Wallnut Bowling minigame: Roll nuts to knock down zombies
 */
public class WallnutBowling extends Minigame {
    private ConveyorBelt conveyorBelt;
    private int redLineX;
    private int zombiesKnocked;
    private int targetZombies;

    public WallnutBowling(int difficulty) {
        super(difficulty);
        this.type = MinigameType.WALLNUT_BOWLING;
        initializeBoard();
    }

    public boolean rollWallnut() {
        if (conveyorBelt.nextPlant() == null) {
            return false;
        }
        zombiesKnocked += 2;
        completed = checkWinCondition();
        return true;
    }

    @Override
    public void initializeBoard() {
        conveyorBelt = new ConveyorBelt();
        conveyorBelt.refill(3 + difficulty);
        redLineX = 1;
        zombiesKnocked = 0;
        targetZombies = 4 + difficulty;
    }

    @Override
    public boolean checkWinCondition() {
        return zombiesKnocked >= targetZombies;
    }

    @Override
    public boolean checkLoseCondition() {
        return failed;
    }

    @Override
    public void reset() {
        completed = false;
        failed = false;
        initializeBoard();
    }
}
