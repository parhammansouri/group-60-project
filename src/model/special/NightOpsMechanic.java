package model.special;

import model.Level;

/** Night Ops visibility tracker. */
public class NightOpsMechanic extends NoopMechanic {
    private int visibilityRange;

    public NightOpsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        visibilityRange = Math.max(3, level.getCols() / 2);
    }

    @Override
    public String getKey() { return "night_ops"; }
}
