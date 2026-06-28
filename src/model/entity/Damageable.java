package model.entity;

/**
 * Interface for entities that can be damaged
 */
public interface Damageable {
    /**
     * Apply damage to this entity
     */
    void takeDamage(int damage);

    /**
     * Get current health
     */
    int getHealth();

    /**
     * Check if entity is still alive
     */
    boolean isAlive();
}
