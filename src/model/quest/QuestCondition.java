package model.quest;

import model.User;

/**
 * Represents conditions that must be met to complete a quest
 */
public class QuestCondition {
    private QuestConditionType type;
    private int targetValue;
    private String targetId;

    public QuestCondition(QuestConditionType type, int targetValue, String targetId) {
        this.type = type;
        this.targetValue = targetValue;
        this.targetId = targetId;
    }

    public QuestConditionType getType() {
        return type;
    }

    public int getTargetValue() {
        return targetValue;
    }

    /**
     * Get the target ID (e.g. level ID, plant ID)
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * Check if the condition is met for a user
     */
    public boolean isMet(User user) {
        return getProgress(user) >= targetValue;
    }

    /**
     * Get progress towards meeting this condition
     */
    public int getProgress(User user) {
        if (user == null) {
            return 0;
        }
        return switch (type) {
            case COLLECT_COINS -> user.getCoins();
            case COMPLETE_MINIGAME -> user.getMinigamesCompleted();
            case COMPLETE_LEVEL -> user.getMaxChapter() * 10 + user.getMaxLevel();
            case COMPLETE_LEVEL_WITH_SCORE -> user.getHighestScore();
            case UNLOCK_PLANT -> user.hasUnlockedPlant(targetId) ? targetValue : 0;
            case KILL_ZOMBIES, KILL_ZOMBIE_TYPE -> user.getHighestScore() / 10;
            case UNLOCK_ZOMBIE -> 0;
        };
    }
}
