package model.sun;

/**
 * Manages the sun resource system in the game
 */
public class SunSystem {
    private int totalSunAmount;
    private int ticksSinceLastDrop;
    private Sun[] droppingSuns;

    public void addSunFromPlant(int x, int y, int amount) {
        // TODO: Implementation - Create collectible sun pellet
    }

    public void dropSun(int x, int y, SunType type) {
        // TODO: Implementation - Based on formula x = max(6 + 0.05t, 12)
    }

    public boolean collectSun(int x, int y) {
        // TODO: Implementation
        return false;
    }

    /**
     * Update sun drop timing
     */
    public void update(int currentTick) {
        // TODO: Implementation
    }

    public int getTotalSun() {
        // TODO: Implementation
        return totalSunAmount;
    }

    public boolean consumeSun(int amount) {
        // TODO: Implementation
        return false;
    }
}
