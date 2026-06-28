package model.special;

import model.Level;

/** Minimal Boss mechanic placeholder. */
public class BossMechanic extends NoopMechanic {
    public BossMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., spawn boss entity, change win conditions
    }

    @Override
    public String getKey() { return "boss"; }
}
