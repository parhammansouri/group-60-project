package model.quest;

/**
 * Types of quests in the game
 */
public enum QuestType {
    STORY("Story"),
    EPIC("Epic"),
    DAILY("Daily"),
    TUTORIAL("Tutorial");

    private String displayName;

    QuestType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
