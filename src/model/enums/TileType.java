package model.enums;

/**
 * Represents all possible terrain types for tiles on the game board.
 * Different chapters have different terrain types with unique mechanics.
 */
public enum TileType {
    // Ancient Egypt
    GRASS,
    GRAVESTONE,

    // Frozen Caves
    SLIPPERY_UP,
    SLIPPERY_DOWN,
    FROZEN,

    // Big Wave Beach
    WATER,
    LOW_BEACH,

    // Dark Ages
    NECROMANCY;

    /**
     * Check if this tile type can have plants placed on it normally
     */
    public boolean isPlantable() {
        return this == GRASS || this == LOW_BEACH;
    }

    /**
     * Check if this tile type can have zombies walk on it
     */
    public boolean isWalkable() {
        return this != WATER;
    }
}
