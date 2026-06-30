package model.quest;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class QuestBook {
    private final List<Quest> quests = new ArrayList<>();

    public QuestBook() {
        quests.add(new Quest("first_steps", "First Steps", "Collect 100 coins", QuestType.TUTORIAL,
                new QuestCondition(QuestConditionType.COLLECT_COINS, 100, null)));
        quests.add(new Quest("mini_start", "Mini Start", "Complete 1 minigame", QuestType.DAILY,
                new QuestCondition(QuestConditionType.COMPLETE_MINIGAME, 1, null)));
        quests.add(new Quest("shooter_owner", "Shooter Owner", "Unlock the shooter plant", QuestType.STORY,
                new QuestCondition(QuestConditionType.UNLOCK_PLANT, 1, "shooter")));
        quests.add(new Quest("zombie_slayer", "Zombie Slayer", "Defeat 5 zombies across sessions", QuestType.EPIC,
                new QuestCondition(QuestConditionType.KILL_ZOMBIES, 5, null)));
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public Quest getQuest(String questId) {
        for (Quest quest : quests) {
            if (quest.getQuestId().equals(questId)) {
                return quest;
            }
        }
        return null;
    }

    public String getStatus(User user) {
        StringBuilder builder = new StringBuilder();
        for (Quest quest : quests) {
            boolean ready = quest.areConditionsMet(user);
            builder.append(quest.getQuestId())
                    .append(" | ").append(quest.getTitle())
                    .append(" | ").append(quest.getProgress())
                    .append("/").append(quest.getCondition().getTargetValue())
                    .append(ready ? " | ready" : " | active")
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
