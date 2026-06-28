package model;

import model.entity.plant.Plant;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a user account in the game.
 * Stores all persistent user information including profile data,
 * game progress, currency, and account settings.
 */
public class User {
    private String username;
    private String nickname;
    private String passwordHash;
    private String email;
    private String gender;
    private Date createdAt;
    private Map<News, Boolean> newsList; // (news, seenStatus)
    private int coins;
    private int gems;
    private int difficultyLevel;
    private boolean stayLoggedIn;
    private String securityQuestion;
    private String securityAnswer;
    private int highestScore;
    private int maxChapter;
    private int maxLevel;
    private int minigamesCompleted;
    private int questsCompleted;

    private List<Plant> unlockedPlants;
    private List<String> unlockedPlantTypes;

    // TODO: Add collection of unlocked plants
    // TODO: Add collection of seen zombies
    // TODO: Add progress tracking data
    // TODO: Add quest completion tracking

    public User(String username, String password, String email, String nickname) {
        if (username == null || !username.matches("[A-Za-z0-9_]{3,30}")) {
            throw new IllegalArgumentException("username must be 3-30 letters, digits, or underscores");
        }
        this.username = username;
        setPasswordHash(hashPassword(password));
        setEmail(email);
        setNickname(nickname == null || nickname.isBlank() ? username : nickname);
        this.createdAt = new Date();
        this.newsList = new HashMap<>();
        this.coins = 100;
        this.gems = 3;
        this.difficultyLevel = 1;
        this.stayLoggedIn = false;
        this.unlockedPlants = new ArrayList<>();
        this.unlockedPlantTypes = new ArrayList<>();
        this.highestScore = 0;
        this.maxChapter = 1;
        this.maxLevel = 1;
        this.minigamesCompleted = 0;
        this.questsCompleted = 0;
        unlockPlant("basic");
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.length() < 3 || nickname.length() > 30) {
            throw new IllegalArgumentException("nickname must be 3-30 characters");
        }
        this.nickname = nickname;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("password cannot be empty");
        }
        this.passwordHash = passwordHash;
    }

    /**
     * Verify if the given password matches the stored hash
     */
    public boolean verifyPassword(String password) {
        try {
            return passwordHash.equals(hashPassword(password));
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("email format is invalid");
        }
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.isBlank()) {
            this.gender = "";
            return;
        }
        this.gender = gender.trim();
    }

    public Map<News, Boolean> getNewsList() {
        return newsList;
    }

    public boolean hasSeenNews(News news) {
        return Boolean.TRUE.equals(newsList.get(news));
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    public boolean removeCoins(int amount) {
        if (amount < 0 || coins < amount) {
            return false;
        }
        coins -= amount;
        return true;
    }

    public int getGems() {
        return gems;
    }

    public void addGems(int amount) {
        if (amount > 0) {
            gems += amount;
        }
    }

    public boolean removeGems(int amount) {
        if (amount < 0 || gems < amount) {
            return false;
        }
        gems -= amount;
        return true;
    }

    /**
     * Get the current difficulty level (1-5)
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Set the game difficulty level (1-5)
     */
    public void setDifficultyLevel(int level) {
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("difficulty level must be between 1 and 5");
        }
        difficultyLevel = level;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stay) {
        stayLoggedIn = stay;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public boolean verifySecurityAnswer(String answer) {
        return securityAnswer != null && securityAnswer.equalsIgnoreCase(answer == null ? "" : answer.trim());
    }

    /**
     * Set a new password through security question verification
     */
    public void resetPassword(String newPassword) {
        setPasswordHash(hashPassword(newPassword));
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        if (!verifyPassword(currentPassword)) {
            return false;
        }
        resetPassword(newPassword);
        return true;
    }

    public List<Plant> getUnlockedPlants() {
        return unlockedPlants;
    }

    public void unlockPlant() {
        // TODO: Implementation
    }

    public List<String> getUnlockedPlantTypes() {
        return unlockedPlantTypes;
    }

    public boolean hasUnlockedPlant(String plantType) {
        return unlockedPlantTypes.contains(normalizePlantType(plantType));
    }

    public boolean unlockPlant(String plantType) {
        String normalized = normalizePlantType(plantType);
        if (normalized.isBlank() || unlockedPlantTypes.contains(normalized)) {
            return false;
        }
        unlockedPlantTypes.add(normalized);
        return true;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void submitScore(int score) {
        highestScore = Math.max(highestScore, score);
    }

    public int getMaxChapter() {
        return maxChapter;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void updateProgress(int chapter, int level) {
        if (chapter > maxChapter || chapter == maxChapter && level > maxLevel) {
            maxChapter = chapter;
            maxLevel = level;
        }
    }

    public int getMinigamesCompleted() {
        return minigamesCompleted;
    }

    public void addMinigameCompletion() {
        minigamesCompleted++;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public void addQuestCompletion() {
        questsCompleted++;
    }

    public String toStorageRecord() {
        return String.join("\t",
                encode(username),
                encode(nickname),
                encode(passwordHash),
                encode(email),
                encode(gender),
                Long.toString(createdAt.getTime()),
                Integer.toString(coins),
                Integer.toString(gems),
                Integer.toString(difficultyLevel),
                Boolean.toString(stayLoggedIn),
                encode(String.join(",", unlockedPlantTypes)),
                Integer.toString(highestScore),
                Integer.toString(maxChapter),
                Integer.toString(maxLevel),
                Integer.toString(minigamesCompleted),
                Integer.toString(questsCompleted));
    }

    public static User fromStorageRecord(String record) {
        String[] fields = record.split("\t", -1);
        if (fields.length != 10 && fields.length != 11 && fields.length != 16) {
            throw new IllegalArgumentException("invalid user record");
        }

        User user = new User(decode(fields[0]), "temporary-password", decode(fields[3]), decode(fields[1]));
        user.setPasswordHash(decode(fields[2]));
        user.gender = decode(fields[4]);
        user.createdAt = new Date(Long.parseLong(fields[5]));
        user.coins = Integer.parseInt(fields[6]);
        user.gems = Integer.parseInt(fields[7]);
        user.setDifficultyLevel(Integer.parseInt(fields[8]));
        user.stayLoggedIn = Boolean.parseBoolean(fields[9]);
        user.unlockedPlantTypes.clear();
        if (fields.length == 11 && !decode(fields[10]).isBlank()) {
            for (String plantType : decode(fields[10]).split(",")) {
                user.unlockPlant(plantType);
            }
        } else {
            user.unlockPlant("basic");
        }
        if (fields.length == 16) {
            user.highestScore = Integer.parseInt(fields[11]);
            user.maxChapter = Integer.parseInt(fields[12]);
            user.maxLevel = Integer.parseInt(fields[13]);
            user.minigamesCompleted = Integer.parseInt(fields[14]);
            user.questsCompleted = Integer.parseInt(fields[15]);
        }
        return user;
    }

    public static String hashPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("password must be at least 4 characters");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte b : encoded) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }

    private static String encode(String value) {
        String safeValue = value == null ? "" : value;
        return Base64.getEncoder().encodeToString(safeValue.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private static String normalizePlantType(String plantType) {
        return plantType == null ? "" : plantType.trim().toLowerCase();
    }
}
