package model.special;

import model.Level;

/** Minimal conveyor-belt mechanic placeholder. */
public class ConveyorBeltMechanic extends NoopMechanic {
    private int queuedPlants;

    public ConveyorBeltMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        queuedPlants = 3;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (elapsedTicks % 12 == 0) {
            queuedPlants++;
        }
    }

    @Override
    public String getKey() { return "conveyor_belt"; }
}
