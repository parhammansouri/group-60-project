package model.minigame.wallnutbowling;

import model.entity.plant.Plant;
import model.factory.PlantFactory;

/**
 * Represents the conveyor belt that supplies plants
 */
class ConveyorBelt {
    private int plantCount;
    private int ticksSinceLastPlant;
    private static final int PLANT_INTERVAL = 12 * 10; // 12 seconds

    public Plant nextPlant() {
        if (plantCount <= 0) {
            return null;
        }
        plantCount--;
        return PlantFactory.create("basic");
    }

    public void update() {
        ticksSinceLastPlant++;
        if (ticksSinceLastPlant >= PLANT_INTERVAL) {
            plantCount++;
            ticksSinceLastPlant = 0;
        }
    }

    public void refill(int count) {
        plantCount += Math.max(0, count);
    }
}
