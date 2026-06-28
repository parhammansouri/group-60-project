package model.special;

import model.Level;

/** Minimal locked-plants mechanic placeholder. */
public class LockedPlantsMechanic extends NoopMechanic {
    public LockedPlantsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // In full implementation: mark certain plants as unavailable
    }

    @Override
    public String getKey() { return "locked_plants"; }
}
