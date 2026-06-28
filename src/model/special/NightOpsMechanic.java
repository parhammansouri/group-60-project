package model.special;

import model.Level;

/** Minimal Night Ops mechanic placeholder. */
public class NightOpsMechanic extends NoopMechanic {
    public NightOpsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., reduce visibility, modify spawn rules
    }

    @Override
    public String getKey() { return "night_ops"; }
}
