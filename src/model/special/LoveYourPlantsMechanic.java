package model.special;

import model.Level;

/** Minimal Love Your Plants mechanic placeholder. */
public class LoveYourPlantsMechanic extends NoopMechanic {
    public LoveYourPlantsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        // e.g., grant passive healing to plants
    }

    @Override
    public String getKey() { return "love_your_plants"; }
}
