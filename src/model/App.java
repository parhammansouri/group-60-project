package model;

import model.entity.plant.Plant;
import model.enums.Menu;
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
        // TODO: Implementation
        return currentSession;
    }

    public static void createNewSession(Chapter chapter, Level level, List<Plant> selectedPlants) {
        // TODO: Implementation - Initialize board, waves, etc.
    }

    /**
     * End the current game session
     */
    public static void endSession() {
        // TODO: Implementation - Save progress, calculate rewards
        currentSession = null;
    }

    /**
     * Save all game data to persistent storage
     */
    public static void saveGameState() {
        // TODO: Implementation - Serialize user data and progress
    }

    /**
     * Load all game data from persistent storage
     */
    public static void loadGameState() {
        // TODO: Implementation - Deserialize user data and progress
    }
}
