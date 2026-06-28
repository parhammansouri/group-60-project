package model.greenhouse;

import java.util.Map;

/**
 * Manages the player's greenhouse where plants can be grown to gain rewards.
 * Contains 20 plant pots arranged in 4 rows and 5 columns.
 * Pots can be unlocked with coins and grow plants over time.
 */
public class Greenhouse {
    private static final int ROWS = 4;
    private static final int COLS = 5;
    private static final int TOTAL_POTS = 20;

    private GreenhousePot[][] pots;
    private Map<Integer, Integer> unlockedPots; // potId -> true if unlocked

    public Greenhouse() {
        // TODO: Implementation - Initialize 20 pots, first 5 unlocked
    }

    public GreenhousePot getPot(int x, int y) {
        // TODO: Implementation
        return null;
    }

    public boolean isPotUnlocked(int x, int y) {
        // TODO: Implementation
        return false;
    }

    /**
     * Unlock a pot with coins
     */
    public boolean unlockPot(int x, int y, int coinsAvailable) {
        // TODO: Implementation - Costs 2000 coins
        return false;
    }

    /**
     * Plant a random plant in a pot
     */
    public boolean plantInPot(int x, int y) {
        // TODO: Implementation - 50% chance marigold, 50% random unlocked plant
        return false;
    }

    /**
     * Collect a fully grown plant from a pot
     */
    public Integer collectPlant(int x, int y) {
        // TODO: Implementation - Returns coins or seed packet ID
        return null;
    }

    /**
     * Grow a pot instantly for gems
     */
    public boolean growInstant(int x, int y, int gemsAvailable) {
        // TODO: Implementation - Costs 1 gem per hour remaining
        return false;
    }

    /**
     * Update greenhouse state (called periodically based on system time)
     */
    public void update() {
        // TODO: Implementation - Grow plants based on elapsed time
    }

    /**
     * Get overall greenhouse status for display
     */
    public String getStatus() {
        // TODO: Implementation
        return "";
    }
}
