package model.entity.zombie;

import java.util.ArrayList;

/**
 * Minimal concrete zombie implementation used by the factory.
 * Keeps behavior simple so it fits into the current codebase without
 * requiring changes elsewhere.
 */
public class BasicZombie extends Zombie {
    public BasicZombie() {
        this("Basic Zombie", 1.0f, 2, 5);
    }

    public BasicZombie(String type, float speed, int damage, int costToSpawn) {
        this.type = type;
        this.speed = speed;
        this.damage = damage;
        this.costToSpawn = costToSpawn;
        this.isGlowing = false;
        this.armor = new ArrayList<>();
        this.maxHealth = Math.max(40, costToSpawn * 10);
        this.health = maxHealth;
    }

    @Override
    public void performSpecialAbility() {
        // Minimal placeholder: no special ability for basic zombie
    }

    @Override
    public void update() {
        // Minimal update: move forward by speed (actual board logic lives elsewhere)
        moveForward();
    }

    @Override
    public void moveForward() {
        super.moveForward();
    }
}
