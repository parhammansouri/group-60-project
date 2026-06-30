package model.special;

import model.Level;

/** Timed War countdown tracker. */
public class TimedWarMechanic extends NoopMechanic {
    private int remainingTicks;

    public TimedWarMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        remainingTicks = Math.max(60, level.getNumWaves() * 30);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (remainingTicks > 0) {
            remainingTicks--;
        }
    }

    @Override
    public String getKey() { return "timed_war"; }
}
