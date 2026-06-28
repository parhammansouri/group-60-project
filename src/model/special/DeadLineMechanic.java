package model.special;

import model.Level;

/** Minimal Dead Line mechanic placeholder. */
public class DeadLineMechanic extends NoopMechanic {
    public DeadLineMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., force narrow lanes, special hazards
    }

    @Override
    public String getKey() { return "dead_line"; }
}
