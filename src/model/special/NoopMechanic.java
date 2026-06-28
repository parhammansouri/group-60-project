package model.special;

import model.Level;

/**
 * Default no-op mechanic for regular levels or unknown types.
 */
public class NoopMechanic implements SpecialMechanic {
    protected final Level level;

    public NoopMechanic(Level level) {
        this.level = level;
    }

    @Override
    public void init() {}

    @Override
    public void onTick() {}

    @Override
    public void onWaveStart(int waveIndex) {}

    @Override
    public void onWaveEnd(int waveIndex) {}

    @Override
    public String getKey() { return "noop"; }
}
