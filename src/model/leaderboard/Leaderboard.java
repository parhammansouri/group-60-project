package model.leaderboard;

import model.User;

import java.util.List;

/**
 * Manages the global leaderboard showing all players' progress and achievements
 */
public class Leaderboard {
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        // TODO: Implementation - Load all user entries
    }

    public List<LeaderboardEntry> getEntries() {
        // TODO: Implementation
        return entries;
    }

    /**
     * Get leaderboard sorted by max chapter progress
     */
    public List<LeaderboardEntry> sortedByProgress() {
        // TODO: Implementation
        return null;
    }

    /**
     * Get leaderboard sorted by minigame completions
     */
    public List<LeaderboardEntry> sortedByMinigames() {
        // TODO: Implementation
        return null;
    }

    /**
     * Get leaderboard sorted by quests completed
     */
    public List<LeaderboardEntry> sortedByQuests() {
        // TODO: Implementation
        return null;
    }

    /**
     * Get leaderboard sorted by highest score
     */
    public List<LeaderboardEntry> sortedByHighScore() {
        // TODO: Implementation
        return null;
    }

    /**
     * Get a specific player's entry
     */
    public LeaderboardEntry getPlayerEntry(User user) {
        // TODO: Implementation
        return null;
    }

    /**
     * Update a player's leaderboard entry
     */
    public void updateEntry(User user, LeaderboardEntry entry) {
        // TODO: Implementation
    }

    /**
     * Add a new player to the leaderboard
     */
    public void addEntry(User user) {
        // TODO: Implementation
    }
}
