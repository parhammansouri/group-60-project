package model.special;

import model.Level;

/** Minimal Boss mechanic placeholder. */
public class BossMechanic extends NoopMechanic {
    private int bossHealth;

    public BossMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        bossHealth = 500 + level.getLevelNumber() * 100;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (bossHealth > 0 && elapsedTicks % 10 == 0) {
            bossHealth = Math.max(0, bossHealth - 25);
        }
    }

    @Override
    public String getKey() { return "boss"; }
}
