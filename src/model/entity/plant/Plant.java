package model.entity.plant;

import model.entity.BoardEntity;

/**
 * Abstract base class for all plant types in the game.
 * Plants are defensive entities that protect against zombie hordes.
 * Each plant has unique abilities, costs, and recharge times.
 */
public abstract class Plant extends BoardEntity {
    protected String name;
    protected int sunCost;
    protected int rechargeTime;
    protected int timeSinceLastPlacement;
    protected int level;

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

    public int getLevel() {
        return level;
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
    }
}
