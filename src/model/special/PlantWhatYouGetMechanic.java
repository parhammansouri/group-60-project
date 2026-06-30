package model.special;

import model.Level;

/** Minimal Plant What You Get mechanic placeholder. */
public class PlantWhatYouGetMechanic extends NoopMechanic {
    private int randomPlantGrants;

    public PlantWhatYouGetMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        randomPlantGrants = 1;
    }

    @Override
    public void onWaveStart(int waveIndex) {
        super.onWaveStart(waveIndex);
        randomPlantGrants++;
    }

    @Override
    public String getKey() { return "plant_what_you_get"; }
}
