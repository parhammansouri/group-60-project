package model.special;

import model.Level;

/** Dead Line lane-limit tracker. */
public class DeadLineMechanic extends NoopMechanic {
    private int deadlineColumn;

    public DeadLineMechanic(Level level) { super(level); }

    @Override
    public void init() {
        super.init();
        deadlineColumn = Math.max(1, level.getCols() / 3);
    }

    @Override
    public String getKey() { return "dead_line"; }
}
