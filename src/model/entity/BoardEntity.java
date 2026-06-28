package model.entity;

/**
 * Abstract base class for all living entities that reside on the game board.
 * Both plants and zombies are BoardEntity instances.
 */
public abstract class BoardEntity {
    protected int x;
    protected int y;
    protected int health;
    protected int maxHealth;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Apply damage to this entity
     */
    public void takeDamage(int damage) {
        if (damage > 0) {
            health = Math.max(0, health - damage);
        }
    }

    /**
     * Check if this entity is still alive
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Called each game tick
     */
    public abstract void update();
}
