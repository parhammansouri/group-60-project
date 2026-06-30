package model.special;

import model.Level;

/** Locked-plants restriction tracker. */
public class LockedPlantsMechanic extends NoopMechanic {
    private boolean locked;

    public LockedPlantsMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        locked = true;
    }

    @Override
    public String getKey() { return "locked_plants"; }
}
