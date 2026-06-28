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
        // TODO: Implementation
        return name;
    }

    public int getSunCost() {
        // TODO: Implementation
        return sunCost;
    }

    public int getRechargeTime() {
        // TODO: Implementation
        return rechargeTime;
    }

    public int getTimeUntilReady() {
        // TODO: Implementation - Returns max(0, rechargeTime - timeSinceLastPlacement)
        return 0;
    }

    /**
     * Check if this plant is ready to be placed
     */
    public boolean isReady() {
        // TODO: Implementation
        return false;
    }

    public int getLevel() {
        // TODO: Implementation
        return level;
    }

    /**
     * Upgrade this plant to the next level
     */
    public void upgrade() {
        // TODO: Implementation - Requires coins and seed packets
    }

    /**
     * Feed this plant with plant food for bonus effect
     */
    public void feedPlantFood() {
        // TODO: Implementation - Activate plant's special ability
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
        // TODO: Implementation - Increment timer, trigger abilities
    }
}
