package model.special;

import model.Level;

/**
 * Default no-op mechanic for regular levels or unknown types.
 */
public class NoopMechanic implements SpecialMechanic {
    protected final Level level;
    protected boolean initialized;
    protected int elapsedTicks;
    protected int lastWaveStarted;
    protected int lastWaveEnded;

    public NoopMechanic(Level level) {
        this.level = level;
        this.initialized = false;
        this.elapsedTicks = 0;
        this.lastWaveStarted = 0;
        this.lastWaveEnded = 0;
    }

    @Override
    public void init() {
        initialized = true;
    }

    @Override
    public void onTick() {
        elapsedTicks++;
    }

    @Override
    public void onWaveStart(int waveIndex) {
        lastWaveStarted = waveIndex;
    }

    @Override
    public void onWaveEnd(int waveIndex) {
        lastWaveEnded = waveIndex;
    }

    @Override
    public String getKey() { return "noop"; }

    public boolean isInitialized() {
        return initialized;
    }

    public int getElapsedTicks() {
        return elapsedTicks;
    }
}
