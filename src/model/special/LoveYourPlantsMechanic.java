package model.special;

import model.Level;

/** Minimal Love Your Plants mechanic placeholder. */
public class LoveYourPlantsMechanic extends NoopMechanic {
    private int healingPulseCount;

    public LoveYourPlantsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        healingPulseCount = 0;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (elapsedTicks % 8 == 0) {
            healingPulseCount++;
        }
    }

    @Override
    public String getKey() { return "love_your_plants"; }
}
