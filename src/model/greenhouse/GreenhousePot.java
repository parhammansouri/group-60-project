package model.greenhouse;

import java.time.LocalTime;

/**
 * Represents a single pot in the greenhouse.
 * Pots can be locked/unlocked and contain growing plants.
 */
public class GreenhousePot {
    private int x;
    private int y;
    private boolean isUnlocked;
    private String plantType;
    private LocalTime plantedTime;
    private LocalTime readyTime;
    private GrowthPhase growthPhase;

    public int getX() {
        // TODO: Implementation
        return x;
    }

    public int getY() {
        // TODO: Implementation
        return y;
    }

    public boolean isUnlocked() {
        // TODO: Implementation
        return isUnlocked;
    }

    public boolean isEmpty() {
        // TODO: Implementation
        return plantType == null;
    }

    public String getPlantType() {
        // TODO: Implementation
        return plantType;
    }

    public boolean isReady() {
        // TODO: Implementation
        return plantType != null;
    }

    /**
     * Get time remaining until plant is ready
     */
    public long getTimeRemaining() {
        // TODO: Implementation
        return 0L;
    }

    public GrowthPhase getGrowthPhase() {
        // TODO: Implementation
        return growthPhase;
    }

    /**
     * Update the growth status based on elapsed time
     */
    public void updateGrowth() {
        // TODO: Implementation
    }
}

