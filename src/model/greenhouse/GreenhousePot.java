package model.greenhouse;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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

    public GreenhousePot(int x, int y, boolean unlocked) {
        this.x = x;
        this.y = y;
        this.isUnlocked = unlocked;
        this.plantType = null;
        this.growthPhase = GrowthPhase.SEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void unlock() {
        isUnlocked = true;
    }

    public boolean isEmpty() {
        return plantType == null;
    }

    public String getPlantType() {
        return plantType;
    }

    public boolean isReady() {
        updateGrowth();
        return plantType != null && growthPhase == GrowthPhase.READY;
    }

    /**
     * Get time remaining until plant is ready
     */
    public long getTimeRemaining() {
        if (readyTime == null || isReady()) {
            return 0L;
        }
        return Math.max(0L, ChronoUnit.SECONDS.between(LocalTime.now(), readyTime));
    }

    public GrowthPhase getGrowthPhase() {
        updateGrowth();
        return growthPhase;
    }

    /**
     * Update the growth status based on elapsed time
     */
    public void updateGrowth() {
        if (plantType == null || readyTime == null) {
            return;
        }
        long remaining = ChronoUnit.SECONDS.between(LocalTime.now(), readyTime);
        if (remaining <= 0) {
            growthPhase = GrowthPhase.READY;
        } else if (remaining <= 2) {
            growthPhase = GrowthPhase.GROWING;
        } else {
            growthPhase = GrowthPhase.SPROUT;
        }
    }

    public boolean plant(String plantType) {
        if (!isUnlocked || !isEmpty()) {
            return false;
        }
        this.plantType = plantType;
        this.plantedTime = LocalTime.now();
        this.readyTime = plantedTime.plusSeconds(5);
        this.growthPhase = GrowthPhase.SEED;
        return true;
    }

    public void makeReady() {
        if (plantType != null) {
            readyTime = LocalTime.now();
            growthPhase = GrowthPhase.READY;
        }
    }

    public void restore(boolean unlocked, String plantType, long remainingSeconds) {
        this.isUnlocked = unlocked;
        if (plantType == null || plantType.isBlank()) {
            this.plantType = null;
            this.plantedTime = null;
            this.readyTime = null;
            this.growthPhase = GrowthPhase.SEED;
            return;
        }
        this.plantType = plantType;
        this.plantedTime = LocalTime.now();
        this.readyTime = LocalTime.now().plusSeconds(Math.max(0L, remainingSeconds));
        this.growthPhase = remainingSeconds <= 0 ? GrowthPhase.READY : GrowthPhase.SEED;
        updateGrowth();
    }

    public String collect() {
        if (!isReady()) {
            return null;
        }
        String harvestedPlant = plantType;
        plantType = null;
        plantedTime = null;
        readyTime = null;
        growthPhase = GrowthPhase.SEED;
        return harvestedPlant;
    }
}
