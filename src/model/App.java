package model;

import model.entity.plant.Plant;
import model.enums.Menu;
import model.factory.PlantFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central game state manager.
 * Manages the current user session, menu state, and overall game session.
 * Implements a singleton pattern to ensure only one game instance exists.
 */
public class App {
    private static final Path DATA_DIRECTORY = Path.of("data");
    private static final Path USERS_FILE = DATA_DIRECTORY.resolve("users.tsv");
    private static User loggedInUser = null;
    private static Menu currentMenu = Menu.Signup;
    private static GameplaySession currentSession = null;
    private static final Map<String, User> users = new HashMap<>();

    public static User getLoggedInUser() {
        // TODO: Implementation
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static boolean isUserLoggedIn() {
        // TODO: Implementation
        return loggedInUser != null;
    }

    public static void logout() {
        loggedInUser = null;
        currentMenu = Menu.Login;
    }

    public static Menu getCurrentMenu() {
        // TODO: Implementation
        return currentMenu;
    }

    public static void setCurrentMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("menu cannot be null");
        }
        currentMenu = menu;
    }

    public static boolean usernameExists(String username) {
        return users.containsKey(normalizeUsername(username));
    }

    public static void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        users.put(normalizeUsername(user.getUsername()), user);
        saveGameState();
    }

    public static User findUser(String username) {
        return users.get(normalizeUsername(username));
    }

    public static Collection<User> getUsers() {
        return users.values();
    }

    private static String normalizeUsername(String username) {
        return username == null ? "" : username.trim().toLowerCase();
    }

    public static GameplaySession getCurrentSession() {
        return currentSession;
    }

    public static void createNewSession(Chapter chapter, Level level, List<Plant> selectedPlants) {
        Chapter activeChapter = chapter == null ? Chapter.createDefault() : chapter;
        Level activeLevel = level == null ? activeChapter.getLevel(1) : level;
        List<Plant> plants = selectedPlants == null || selectedPlants.isEmpty()
                ? List.of(PlantFactory.create("basic"), PlantFactory.create("shooter"), PlantFactory.create("slow"))
                : selectedPlants;
        currentSession = new GameplaySession(activeChapter, activeLevel, plants);
    }

    /**
     * End the current game session
     */
    public static void endSession() {
        if (loggedInUser != null && currentSession != null) {
            loggedInUser.addCoins(currentSession.getCoins());
            loggedInUser.addGems(currentSession.getGems());
            loggedInUser.submitScore(currentSession.getPlayerScore());
            if (currentSession.hasWon()) {
                loggedInUser.addCoins(currentSession.getLevel().getRewardCoins());
                loggedInUser.addGems(currentSession.getLevel().getRewardGems());
                loggedInUser.updateProgress(currentSession.getLevel().getChapterNumber(),
                        currentSession.getLevel().getLevelNumber());
            }
            saveGameState();
        }
        currentSession = null;
    }

    /**
     * Save all game data to persistent storage
     */
    public static void saveGameState() {
        try {
            Files.createDirectories(DATA_DIRECTORY);
            List<String> records = new ArrayList<>();
            for (User user : users.values()) {
                records.add(user.toStorageRecord());
            }
            Files.write(USERS_FILE, records);
        } catch (IOException exception) {
            throw new IllegalStateException("could not save game state", exception);
        }
    }

    /**
     * Load all game data from persistent storage
     */
    public static void loadGameState() {
        users.clear();
        if (!Files.exists(USERS_FILE)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(USERS_FILE)) {
                if (line.isBlank()) {
                    continue;
                }
                User user = User.fromStorageRecord(line);
                users.put(normalizeUsername(user.getUsername()), user);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("could not load game state", exception);
        }
    }
}
