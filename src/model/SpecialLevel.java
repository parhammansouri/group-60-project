package model;

import model.enums.LevelType;
import model.special.SpecialMechanic;
import model.special.SpecialMechanicFactory;

/**
 * Wrapper for levels that have special mechanics. Composes an existing
 * `Level` and attaches a `SpecialMechanic` implementation determined from
 * the level's `LevelType`.
 */
public class SpecialLevel {
    private Level baseLevel;
    private SpecialMechanic mechanic;

    public SpecialLevel(Level baseLevel) {
        this.baseLevel = baseLevel;
        LevelType t = baseLevel.getType();
        this.mechanic = SpecialMechanicFactory.create(t, baseLevel);
    }

    public Level getBaseLevel() {
        return baseLevel;
    }

    public SpecialMechanic getMechanic() {
        return mechanic;
    }
}
