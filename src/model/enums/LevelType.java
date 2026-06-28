package model.enums;

/**
 * Enumeration of special level types in the game.
 * Regular levels are the default progression levels.
 * Special levels have unique mechanics and challenges.
 */
public enum LevelType {
    REGULAR("Regular Level"),
    CONVEYOR_BELT("Conveyor Belt Level"),
    LOCKED_PLANTS("Locked Plants Level"),
    SAVE_OUR_SEEDS("Save Our Seeds"),
    TIMED_WAR("Timed War"),
    NIGHT_OPS("Night Ops"),
    DEAD_LINE("Dead Line"),
    LOVE_YOUR_PLANTS("Love Your Plants"),
    PLANT_WHAT_YOU_GET("Plant What You Get"),
    BOSS("Boss Level");

    private String displayName;

    LevelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        // TODO: Implementation
        return displayName;
    }

    public boolean isSpecial() {
        // TODO: Implementation
        return this != REGULAR;
    }
}
