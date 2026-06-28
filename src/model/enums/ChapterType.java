package model.enums;

/**
 * Enumeration of all chapter types with their specific characteristics.
 */
public enum ChapterType {
    ANCIENT_EGYPT("Ancient Egypt"),
    FROZEN_CAVES("Frozen Caves"),
    BIG_WAVE_BEACH("Big Wave Beach"),
    DARK_AGES("Dark Ages");

    private String displayName;

    ChapterType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        // TODO: Implementation
        return displayName;
    }
}
