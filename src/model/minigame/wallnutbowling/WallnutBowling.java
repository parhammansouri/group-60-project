package model.minigame.wallnutbowling;

import model.minigame.Minigame;

/**
 * Wallnut Bowling minigame: Roll nuts to knock down zombies
 */
public class WallnutBowling extends Minigame {
    private ConveyorBelt conveyorBelt;
    private int redLineX;

    public WallnutBowling(int difficulty) {
        super(difficulty);
        // TODO: Implementation
    }

    @Override
    public void initializeBoard() {
        // TODO: Implementation - Set up conveyor belt and bowling nuts
    }

    @Override
    public boolean checkWinCondition() {
        // TODO: Implementation
        return false;
    }

    @Override
    public boolean checkLoseCondition() {
        // TODO: Implementation
        return false;
    }

    @Override
    public void reset() {
        // TODO: Implementation
    }
}

