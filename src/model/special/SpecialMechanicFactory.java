package model.special;

import model.Level;
import model.enums.LevelType;

/**
 * Factory that returns a `SpecialMechanic` implementation for a given
 * `LevelType`. Keeps implementations small and placeholder-like so core
 * game code doesn't need to change.
 */
public class SpecialMechanicFactory {
    public static SpecialMechanic create(LevelType type, Level level) {
        if (type == null) return new NoopMechanic(level);
        switch (type) {
            case CONVEYOR_BELT:
                return new ConveyorBeltMechanic(level);
            case LOCKED_PLANTS:
                return new LockedPlantsMechanic(level);
            case SAVE_OUR_SEEDS:
                return new SaveOurSeedsMechanic(level);
            case TIMED_WAR:
                return new TimedWarMechanic(level);
            case NIGHT_OPS:
                return new NightOpsMechanic(level);
            case DEAD_LINE:
                return new DeadLineMechanic(level);
            case LOVE_YOUR_PLANTS:
                return new LoveYourPlantsMechanic(level);
            case PLANT_WHAT_YOU_GET:
                return new PlantWhatYouGetMechanic(level);
            case BOSS:
                return new BossMechanic(level);
            case REGULAR:
            default:
                return new NoopMechanic(level);
        }
    }
}
