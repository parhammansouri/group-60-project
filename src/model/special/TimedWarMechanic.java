package model.special;

import model.Level;

/** Minimal Timed War mechanic placeholder. */
public class TimedWarMechanic extends NoopMechanic {
    public TimedWarMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., start countdown timer
    }

    @Override
    public String getKey() { return "timed_war"; }
}
