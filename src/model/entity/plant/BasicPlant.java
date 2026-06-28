package model.entity.plant;

/**
 * Minimal concrete plant implementation used by the factory.
 */
public class BasicPlant extends Plant {
    public BasicPlant() {
        this("Basic Plant", 25, 5);
    }

    public BasicPlant(String name, int sunCost, int rechargeTime) {
        this.name = name;
        this.sunCost = sunCost;
        this.rechargeTime = rechargeTime;
        this.timeSinceLastPlacement = rechargeTime;
        this.level = 1;
    }

    @Override
    public void performAbility() {
        // Minimal placeholder ability
    }

    @Override
    public void update() {
        // Increment timer so ready state can change
        this.timeSinceLastPlacement++;
    }
}
