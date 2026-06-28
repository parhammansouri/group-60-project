package model;

import model.entity.zombie.Zombie;
import java.util.List;

public class Wave {
    private final int waveNumber;
    private int elapsedTicks;
    private int spawnedZombies;
    private int waveProgressPercentage;

    public Wave(int waveNumber) {
        this.waveNumber = waveNumber;
        // TODO: Implementation
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public int getWaveCost() {
        // TODO: Implementation
        return 0;
    }

    /**
     * Spawn zombies for the current wave
     */
    public List<Zombie> spawnWave() {
        // TODO: Implementation - Spawn zombies based on wave cost
        return null;
    }

    /**
     * Update wave progress
     */
    public void update() {
        // TODO: Implementation
    }
}
