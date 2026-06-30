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

    public Armor(String type, int maxHealth) {
        this.type = type == null ? "armor" : type;
        this.maxHealth = Math.max(1, maxHealth);
        this.health = this.maxHealth;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Apply damage to this armor piece
     */
    public void takeDamage(int damage) {
        if (damage > 0) {
            health = Math.max(0, health - damage);
        }
    }

    /**
     * Check if this armor piece is destroyed
     */
    public boolean isDestroyed() {
        return health <= 0;
    }
}
