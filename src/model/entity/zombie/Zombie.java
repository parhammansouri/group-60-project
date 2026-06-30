package model.entity.zombie;

import model.entity.BoardEntity;
import java.util.Iterator;
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
        return type;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getCostToSpawn() {
        return costToSpawn;
    }

    public boolean isGlowing() {
        return isGlowing;
    }

    public List<Armor> getArmor() {
        return armor;
    }

    /**
     * Remove armor from this zombie
     */
    public void removeArmor(Armor armor) {
        if (this.armor != null) {
            this.armor.remove(armor);
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }
        int remainingDamage = damage;
        if (armor != null) {
            Iterator<Armor> iterator = armor.iterator();
            while (iterator.hasNext() && remainingDamage > 0) {
                Armor piece = iterator.next();
                int before = piece.getHealth();
                piece.takeDamage(remainingDamage);
                remainingDamage = Math.max(0, remainingDamage - before);
                if (piece.isDestroyed()) {
                    iterator.remove();
                }
            }
        }
        super.takeDamage(remainingDamage);
    }

    /**
     * Make this zombie move towards the player's house
     */
    public void moveForward() {
        x -= Math.max(1, Math.round(speed));
    }

    /**
     * Attack a plant at the given position
     */
    public void attackPlant() {
        // Board-level combat is handled by GameplaySession.
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
        moveForward();
    }
}
