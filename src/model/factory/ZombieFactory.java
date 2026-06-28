package model.factory;

import model.entity.zombie.Zombie;
import model.entity.zombie.BasicZombie;

/**
 * Simple factory for creating zombies by type.
 * Adds a single entry point so future types can be registered centrally.
 */
public class ZombieFactory {
    public static Zombie create(String type) {
        if (type == null) return new BasicZombie();
        switch (type.toLowerCase()) {
            case "fast":
                return new BasicZombie("Fast Zombie", 2.0f, 3, 8);
            case "tank":
                return new BasicZombie("Tank Zombie", 0.6f, 8, 20);
            case "basic":
            default:
                return new BasicZombie();
        }
    }
}
