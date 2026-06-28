package model.special;

import model.Level;

/** Minimal conveyor-belt mechanic placeholder. */
public class ConveyorBeltMechanic extends NoopMechanic {
    public ConveyorBeltMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // In full implementation: populate belt, schedule supplies
    }

    @Override
    public String getKey() { return "conveyor_belt"; }
}
