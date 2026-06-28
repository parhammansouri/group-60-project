package model.entity.zombie;

import model.entity.BoardEntity;
import java.util.List;

/**
 * Abstract base class for all zombie types in the game.
 * Zombies are offensive entities that move across the board trying to reach the
 * player's house.
 * Each zombie type has unique behaviors, armor types, and special abilities.
 */
public abstract class Zombie extends BoardEntity {
    protected String type;
    protected float speed;
    protected int damage;
    protected int costToSpawn;
    protected boolean isGlowing;
    protected List<Armor> armor;

    public String getType() {
        // TODO: Implementation
        return type;
    }

    public float getSpeed() {
        // TODO: Implementation
        return speed;
    }

    public int getDamage() {
        // TODO: Implementation
        return damage;
    }

    public int getCostToSpawn() {
        // TODO: Implementation
        return costToSpawn;
    }

    public boolean isGlowing() {
        // TODO: Implementation
        return isGlowing;
    }

    public List<Armor> getArmor() {
        // TODO: Implementation
        return armor;
    }

    /**
     * Remove armor from this zombie
     */
    public void removeArmor(Armor armor) {
        // TODO: Implementation
    }

    /**
     * Make this zombie move towards the player's house
     */
    public void moveForward() {
        // TODO: Implementation - Decrement x position
    }

    /**
     * Attack a plant at the given position
     */
    public void attackPlant() {
        // TODO: Implementation
    }

    /**
     * Get the special ability/behavior of this zombie type
     */
    public abstract void performSpecialAbility();

    /**
     * Update zombie state each tick
     */
    @Override
    public void update() {
        // TODO: Implementation - Move, attack, apply effects
    }
}
