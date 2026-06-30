package model.special;

import model.Level;

/** Save Our Seeds objective tracker. */
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
