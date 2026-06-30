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

    public Quest(String questId, String title, String description, QuestType type, QuestCondition condition) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.condition = condition;
        this.isCompleted = false;
        this.progress = 0;
    }

    public String getQuestId() {
        return questId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public QuestType getType() {
        return type;
    }

    public QuestCondition getCondition() {
        return condition;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markCompleted() {
        isCompleted = true;
        progress = condition.getTargetValue();
    }

    public int getProgress() {
        return progress;
    }

    public void updateProgress(int amount) {
        if (amount > 0) {
            progress = Math.min(condition.getTargetValue(), progress + amount);
        }
    }

    public boolean areConditionsMet(User user) {
        progress = condition.getProgress(user);
        return condition.isMet(user);
    }
}
