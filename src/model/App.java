package model;

import model.entity.plant.Plant;
import model.enums.ChapterType;
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
    private static final List<Chapter> adventureChapters = List.of(
            new Chapter(1, ChapterType.ANCIENT_EGYPT, true),
            new Chapter(2, ChapterType.FROZEN_CAVES, true),
            new Chapter(3, ChapterType.BIG_WAVE_BEACH, true),
            new Chapter(4, ChapterType.DARK_AGES, true));

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public static void logout() {
        loggedInUser = null;
        currentMenu = Menu.Login;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("menu cannot be null");
        }
        currentMenu = menu;
    }

    public static boolean handleGlobalMenuCommand(String input) {
        String normalized = input == null ? "" : input.trim().toLowerCase();
        if (normalized.equals("menu show current")) {
            System.out.println("current menu: " + currentMenu.name().toLowerCase());
            return true;
        }
        if (normalized.equals("menu logout")) {
            logout();
            System.out.println("logged out");
            return true;
        }
        if (normalized.equals("menu exit")) {
            exitCurrentMenu();
            return true;
        }
        if (normalized.startsWith("menu enter ")) {
            enterMenu(normalized.substring("menu enter ".length()).trim());
            return true;
        }
        return false;
    }

    private static void exitCurrentMenu() {
        switch (currentMenu) {
            case Signup, Exit -> setCurrentMenu(Menu.Exit);
            case Login -> setCurrentMenu(Menu.Signup);
            case Main -> System.out.println("use menu logout to leave main menu");
            case Collection -> setCurrentMenu(Menu.Game);
            default -> setCurrentMenu(Menu.Main);
        }
    }

    private static void enterMenu(String target) {
        Menu menu = resolveMenuTarget(target);
        if (menu == null) {
            System.out.println("menu not found");
            return;
        }
        if (requiresLogin(menu) && !isUserLoggedIn()) {
            System.out.println("login required");
            return;
        }
        setCurrentMenu(menu);
    }

    private static Menu resolveMenuTarget(String target) {
        String name = target.split("\\s+")[0].replace("-", "");
        return switch (name) {
            case "signup", "register" -> Menu.Signup;
            case "login" -> Menu.Login;
            case "main" -> Menu.Main;
            case "profile", "coinwallet", "gemwallet" -> Menu.Profile;
            case "settings", "setting" -> Menu.Settings;
            case "collection", "greenhouse", "shop" -> Menu.Collection;
            case "news", "leaderboard" -> Menu.News;
            case "game", "chapter" -> Menu.Game;
            case "network", "travellog", "quest", "quests", "minigame", "minigames" -> Menu.Network;
            default -> null;
        };
    }

    private static boolean requiresLogin(Menu menu) {
        return menu != Menu.Signup && menu != Menu.Login && menu != Menu.Exit;
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

    public static List<Chapter> getAdventureChapters() {
        return adventureChapters;
    }

    public static Chapter getAdventureChapter(int chapterNumber) {
        for (Chapter chapter : adventureChapters) {
            if (chapter.getChapterNumber() == chapterNumber) {
                return chapter;
            }
        }
        return null;
    }

    public static boolean createNewSession(int chapterNumber, int levelNumber) {
        Chapter chapter = getAdventureChapter(chapterNumber);
        if (chapter == null) {
            return false;
        }
        Level level = chapter.getLevel(levelNumber);
        if (level == null) {
            return false;
        }
        createNewSession(chapter, level, null);
        return true;
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
