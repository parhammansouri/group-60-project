package model;

import model.enums.TileType;
import model.entity.plant.Plant;
import model.entity.zombie.Zombie;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single tile on the game board.
 * A tile can contain plants, zombies, obstacles, and various terrain types.
 * Each tile is immutable in terms of position but mutable in terms of content.
 */
public class Tile {
    private int x;
    private int y;
    private TileType type;
    private Plant plant;
    private final List<Zombie> zombies = new ArrayList<>();

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Check if this tile can accommodate a plant
     */
    public boolean canPlacePlant() {
        return type.isPlantable() && plant == null;
    }

    /**
     * Check if this tile is currently empty
     */
    public boolean isEmpty() {
        return plant == null && zombies.isEmpty();
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
        if (plant != null) {
            plant.setPosition(x, y);
        }
    }

    public void removePlant() {
        this.plant = null;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public void addZombie(Zombie zombie) {
        if (zombie != null && !zombies.contains(zombie)) {
            zombie.setPosition(x, y);
            zombies.add(zombie);
        }
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
    }
}
