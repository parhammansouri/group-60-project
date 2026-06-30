package model;

import model.enums.ChapterType;
import model.enums.LevelType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a chapter/world in the adventure mode.
 * Each chapter has 4 levels and specific terrain/zombie types.
 * Examples: Ancient Egypt, Frozen Caves, Beach, Dark Ages
 */
public class Chapter {
    private String name;
    private int chapterNumber;
    private ChapterType type;
    private List<Level> levels;
    private boolean isUnlocked;

    public Chapter(int chapterNumber, ChapterType type, boolean unlocked) {
        this.chapterNumber = chapterNumber;
        this.type = type == null ? ChapterType.ANCIENT_EGYPT : type;
        this.name = this.type.getDisplayName();
        this.isUnlocked = unlocked;
        this.levels = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            LevelType levelType = i == 4 ? LevelType.BOSS : LevelType.REGULAR;
            levels.add(new Level(i, chapterNumber, levelType,
                    Arrays.asList(10 + i * 3, 18 + i * 4, 28 + i * 5),
                    75 + i * 25, i == 4 ? 2 : 1, i, 5, 9));
        }
    }

    public static Chapter createDefault() {
        return new Chapter(1, ChapterType.ANCIENT_EGYPT, true);
    }

    public String getName() {
        return name;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public ChapterType getType() {
        return type;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(int levelNumber) {
        for (Level level : levels) {
            if (level.getLevelNumber() == levelNumber) {
                return level;
            }
        }
        return null;
    }

    /**
     * Check if this chapter is unlocked for the user
     */
    public boolean isUnlocked() {
        return isUnlocked;
    }

    /**
     * Unlock this chapter
     */
    public void unlock() {
        isUnlocked = true;
    }
}
