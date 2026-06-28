package model.special;

import model.Level;

/**
 * Interface for special-level mechanics. Implementations encapsulate the
 * behaviour specific to special levels (conveyor belt, locked plants, etc.)
 * Methods are intentionally minimal so existing codebase changes are small.
 */
public interface SpecialMechanic {
    /** Initialize mechanic for the given level (called once). */
    void init();

    /** Called every game tick while the level runs. */
    void onTick();

    /** Called when a wave starts. */
    void onWaveStart(int waveIndex);

    /** Called when a wave ends. */
    void onWaveEnd(int waveIndex);

    /** Short human-friendly key identifying the mechanic. */
    String getKey();
}
