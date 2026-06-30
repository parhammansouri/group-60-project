package model.minigame.vasebreaker;

import model.minigame.Minigame;
import model.minigame.MinigameType;

/**
 * Vasebreaker minigame: Break vases to find plants or zombies
 */
public class Vasebreaker extends Minigame {
    private Vase[][] vases;
    private int vasesRemaining;

    public Vasebreaker(int difficulty) {
        super(difficulty);
        this.type = MinigameType.VASEBREAKER;
        initializeBoard();
    }

    public Vase breakVase(int x, int y) {
        if (y < 0 || y >= vases.length || x < 0 || x >= vases[y].length || vases[y][x].isBroken()) {
            return null;
        }
        Vase vase = vases[y][x];
        vase.breakOpen();
        vasesRemaining--;
        completed = checkWinCondition();
        return vase;
    }

    @Override
    public void initializeBoard() {
        vases = new Vase[3][3];
        vasesRemaining = 9;
        for (int y = 0; y < vases.length; y++) {
            for (int x = 0; x < vases[y].length; x++) {
                String type = x == y ? "plant" : "normal";
                vases[y][x] = new Vase(type, type);
            }
        }
    }

    @Override
    public boolean checkWinCondition() {
        return vasesRemaining == 0;
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
