package model.entity.plant;

import model.entity.BoardEntity;
import model.entity.Attacker;
import model.entity.projectile.Projectile;

/**
 * Abstract base class for all plant types in the game.
 * Plants are defensive entities that protect against zombie hordes.
 * Each plant has unique abilities, costs, and recharge times.
 */
public abstract class Plant extends BoardEntity implements Attacker {
    protected String name;
    protected int sunCost;
    protected int rechargeTime;
    protected int timeSinceLastPlacement;
    protected int level;
    protected int attackDamage;
    protected int attackRange;
    protected int attackCooldown;
    protected int ticksSinceLastAttack;

    public String getName() {
        return name;
    }

    public int getSunCost() {
        return sunCost;
    }

    public int getRechargeTime() {
        return rechargeTime;
    }

    public int getTimeUntilReady() {
        return Math.max(0, rechargeTime - timeSinceLastPlacement);
    }

    /**
     * Check if this plant is ready to be placed
     */
    public boolean isReady() {
        return getTimeUntilReady() == 0;
    }

    public void resetPlacementCooldown() {
        timeSinceLastPlacement = 0;
    }

    public void makePlacementReady() {
        timeSinceLastPlacement = rechargeTime;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int getAttackDamage() {
        return attackDamage + level * 5;
    }

    @Override
    public int getAttackRange() {
        return attackRange;
    }

    @Override
    public boolean canAttack() {
        return ticksSinceLastAttack >= attackCooldown;
    }

    @Override
    public void attack() {
        ticksSinceLastAttack = 0;
    }

    public Projectile createProjectile() {
        attack();
        return new Projectile(x + 1, y, 1, getAttackDamage());
    }

    /**
     * Upgrade this plant to the next level
     */
    public void upgrade() {
        level++;
        maxHealth += 20;
        health = maxHealth;
    }

    /**
     * Feed this plant with plant food for bonus effect
     */
    public void feedPlantFood() {
        performAbility();
    }

    /**
     * Get the primary attack/ability of this plant
     */
    public abstract void performAbility();

    /**
     * Update plant state each tick
     */
    @Override
    public void update() {
        timeSinceLastPlacement++;
        ticksSinceLastAttack++;
    }
}
