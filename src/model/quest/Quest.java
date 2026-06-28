package model.quest;

import model.User;

/**
 * Represents a single quest/mission in the game.
 */
public class Quest {
    private String questId;
    private String title;
    private String description;
    private QuestType type;
    private QuestCondition condition;
    private boolean isCompleted;
    private int progress;

    public String getQuestId() {
        // TODO: Implementation
        return questId;
    }

    public String getTitle() {
        // TODO: Implementation
        return title;
    }

    public String getDescription() {
        // TODO: Implementation
        return description;
    }

    public QuestType getType() {
        // TODO: Implementation
        return type;
    }

    public QuestCondition getCondition() {
        // TODO: Implementation
        return condition;
    }

    public boolean isCompleted() {
        // TODO: Implementation
        return isCompleted;
    }

    public void markCompleted() {
        // TODO: Implementation
    }

    public int getProgress() {
        // TODO: Implementation
        return progress;
    }

    public void updateProgress(int amount) {
        // TODO: Implementation
    }

    public boolean areConditionsMet(User user) {
        // TODO: Implementation
        return false;
    }
}
