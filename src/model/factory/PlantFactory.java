package model.factory;

import model.entity.plant.Plant;
import model.entity.plant.BasicPlant;

/**
 * Simple factory for creating plants by type.
 */
public class PlantFactory {
    public static Plant create(String type) {
        if (type == null) return new BasicPlant();
        switch (type.toLowerCase()) {
            case "shooter":
                return new BasicPlant("Shooter", 50, 5);
            case "slow":
                return new BasicPlant("Slow Plant", 25, 3);
            case "basic":
            default:
                return new BasicPlant();
        }
    }
}
