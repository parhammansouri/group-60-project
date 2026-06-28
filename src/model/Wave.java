package model;

import model.entity.zombie.Zombie;
import model.factory.ZombieFactory;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    private final int waveNumber;
    private final int waveCost;
    private int elapsedTicks;
    private int spawnedZombies;
    private int waveProgressPercentage;
    private boolean spawned;

    public Wave(int waveNumber) {
        this(waveNumber, 10 + waveNumber * 5);
    }

    public Wave(int waveNumber, int waveCost) {
        this.waveNumber = waveNumber;
        this.waveCost = waveCost;
        this.elapsedTicks = 0;
        this.spawnedZombies = 0;
        this.waveProgressPercentage = 0;
        this.spawned = false;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public int getWaveCost() {
        return waveCost;
    }

    /**
     * Spawn zombies for the current wave
     */
    public List<Zombie> spawnWave() {
        if (spawned) {
            return new ArrayList<>();
        }
        List<Zombie> zombies = new ArrayList<>();
        int remainingCost = waveCost;
        while (remainingCost > 0) {
            Zombie zombie = remainingCost >= 20 ? ZombieFactory.create("tank") : ZombieFactory.create("basic");
            zombies.add(zombie);
            remainingCost -= zombie.getCostToSpawn();
        }
        spawnedZombies += zombies.size();
        spawned = true;
        return zombies;
    }

    /**
     * Update wave progress
     */
    public void update() {
        elapsedTicks++;
        waveProgressPercentage = Math.min(100, elapsedTicks * 10);
    }
}
