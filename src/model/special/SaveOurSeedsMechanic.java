package model.special;

import model.Level;

/** Minimal Save Our Seeds mechanic placeholder. */
public class SaveOurSeedsMechanic extends NoopMechanic {
    private int savedSeeds;

    public SaveOurSeedsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        savedSeeds = 0;
    }

    @Override
    public void onWaveEnd(int waveIndex) {
        super.onWaveEnd(waveIndex);
        savedSeeds += waveIndex;
    }

    @Override
    public String getKey() { return "save_our_seeds"; }
}
