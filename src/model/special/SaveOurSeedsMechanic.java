package model.special;

import model.Level;

/** Minimal Save Our Seeds mechanic placeholder. */
public class SaveOurSeedsMechanic extends NoopMechanic {
    public SaveOurSeedsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., track seed pickups, special objectives
    }

    @Override
    public String getKey() { return "save_our_seeds"; }
}
