package model.leaderboard;

import model.App;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the global leaderboard showing all players' progress and achievements
 */
public class Leaderboard {
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        entries = new ArrayList<>();
        for (User user : App.getUsers()) {
            addEntry(user);
        }
    }

    public List<LeaderboardEntry> getEntries() {
        return entries;
    }

    /**
     * Get leaderboard sorted by max chapter progress
     */
    public List<LeaderboardEntry> sortedByProgress() {
        return entries.stream()
                .sorted(Comparator.comparingInt(LeaderboardEntry::getMaxChapter).reversed()
                        .thenComparing(Comparator.comparingInt(LeaderboardEntry::getMaxLevel).reversed()))
                .toList();
    }

    /**
     * Get leaderboard sorted by minigame completions
     */
    public List<LeaderboardEntry> sortedByMinigames() {
        return entries.stream()
                .sorted(Comparator.comparingInt(LeaderboardEntry::getMinigamesCompleted).reversed())
                .toList();
    }

    /**
     * Get leaderboard sorted by quests completed
     */
    public List<LeaderboardEntry> sortedByQuests() {
        return entries.stream()
                .sorted(Comparator.comparingInt(LeaderboardEntry::getTotalQuestsCompleted).reversed())
                .toList();
    }

    /**
     * Get leaderboard sorted by highest score
     */
    public List<LeaderboardEntry> sortedByHighScore() {
        return entries.stream()
                .sorted(Comparator.comparingInt(LeaderboardEntry::getHighestScore).reversed())
                .toList();
    }

    /**
     * Get a specific player's entry
     */
    public LeaderboardEntry getPlayerEntry(User user) {
        if (user == null) {
            return null;
        }
        for (LeaderboardEntry entry : entries) {
            if (entry.getUsername().equals(user.getUsername())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Update a player's leaderboard entry
     */
    public void updateEntry(User user, LeaderboardEntry entry) {
        LeaderboardEntry oldEntry = getPlayerEntry(user);
        if (oldEntry != null) {
            entries.remove(oldEntry);
        }
        entries.add(entry);
    }

    /**
     * Add a new player to the leaderboard
     */
    public void addEntry(User user) {
        if (getPlayerEntry(user) != null) {
            return;
        }
        entries.add(new LeaderboardEntry(user.getUsername(), user.getNickname(), user.getMaxChapter(),
                user.getMaxLevel(), user.getMinigamesCompleted(), 0, user.getQuestsCompleted(),
                user.getHighestScore()));
    }
}
