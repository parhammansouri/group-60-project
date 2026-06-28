package model.entity.zombie;

/**
 * Represents armor pieces that can be worn by zombies.
 * Different armor types have different health values.
 * Certain plants can remove specific armor types.
 */
public class Armor {
    private String type;
    private int health;
    private int maxHealth;

    public String getType() {
        // TODO: Implementation
        return type;
    }

    public int getHealth() {
        // TODO: Implementation
        return health;
    }

    public int getMaxHealth() {
        // TODO: Implementation
        return maxHealth;
    }

    /**
     * Apply damage to this armor piece
     */
    public void takeDamage(int damage) {
        // TODO: Implementation
    }

    /**
     * Check if this armor piece is destroyed
     */
    public boolean isDestroyed() {
        // TODO: Implementation
        return health <= 0;
    }
}
