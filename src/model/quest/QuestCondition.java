package model.quest;

import model.User;

/**
 * Represents conditions that must be met to complete a quest
 */
public class QuestCondition {
    private QuestConditionType type;
    private int targetValue;
    private String targetId;

    public QuestConditionType getType() {
        // TODO: Implementation
        return type;
    }

    public int getTargetValue() {
        // TODO: Implementation
        return targetValue;
    }

    /**
     * Get the target ID (e.g. level ID, plant ID)
     */
    public String getTargetId() {
        // TODO: Implementation
        return targetId;
    }

    /**
     * Check if the condition is met for a user
     */
    public boolean isMet(User user) {
        // TODO: Implementation
        return false;
    }

    /**
     * Get progress towards meeting this condition
     */
    public int getProgress(User user) {
        // TODO: Implementation
        return 0;
    }
}

