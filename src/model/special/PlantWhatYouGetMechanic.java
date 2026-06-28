package model.special;

import model.Level;

/** Minimal Plant What You Get mechanic placeholder. */
public class PlantWhatYouGetMechanic extends NoopMechanic {
    public PlantWhatYouGetMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., randomize available plant types on pickup
    }

    @Override
    public String getKey() { return "plant_what_you_get"; }
}
