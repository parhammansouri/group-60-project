package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import view.AppMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfileMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("Profile");
        System.out.println("username: " + user.getUsername());
        System.out.println("nickname: " + user.getNickname());
        System.out.println("email: " + user.getEmail());
        System.out.println("gender: " + user.getGender());
        System.out.println("difficulty: " + user.getDifficultyLevel());
        System.out.println("coins: " + user.getCoins());
        System.out.println("gems: " + user.getGems());
        System.out.println("highest score: " + user.getHighestScore());
        System.out.println("progress: chapter " + user.getMaxChapter() + " level " + user.getMaxLevel());
        System.out.println("back | exit");

        String input = scanner.nextLine().trim();
        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.startsWith("menu profile change-username")) {
            updateUsername(parseFlags(input.split("\\s+")).get("-u"));
        } else if (input.startsWith("menu profile change-nickname")) {
            updateNickname(user, parseFlags(input.split("\\s+")).get("-u"));
        } else if (input.startsWith("menu profile change-email")) {
            updateEmail(user, parseFlags(input.split("\\s+")).get("-e"));
        } else if (input.startsWith("menu profile change-password")) {
            Map<String, String> flags = parseFlags(input.split("\\s+"));
            updatePassword(user, flags.get("-o"), flags.get("-p"));
        } else if (input.equals("menu profile show-info")) {
            printProfile(user);
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void printProfile(User user) {
        System.out.println("username: " + user.getUsername());
        System.out.println("nickname: " + user.getNickname());
        System.out.println("email: " + user.getEmail());
        System.out.println("gender: " + user.getGender());
        System.out.println("difficulty: " + user.getDifficultyLevel());
        System.out.println("coins: " + user.getCoins());
        System.out.println("gems: " + user.getGems());
        System.out.println("highest score: " + user.getHighestScore());
        System.out.println("progress: chapter " + user.getMaxChapter() + " level " + user.getMaxLevel());
    }

    private void updateUsername(String username) {
        if (App.changeLoggedInUsername(username)) {
            System.out.println("username updated");
        } else {
            System.out.println("username is invalid or already exists");
        }
    }

    private void updateNickname(User user, String nickname) {
        try {
            user.setNickname(nickname);
            App.saveGameState();
            System.out.println("nickname updated");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void updateEmail(User user, String email) {
        try {
            user.setEmail(email);
            App.saveGameState();
            System.out.println("email updated");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void updatePassword(User user, String oldPassword, String newPassword) {
        try {
            if (user.changePassword(oldPassword, newPassword)) {
                App.saveGameState();
                System.out.println("password updated");
            } else {
                System.out.println("current password is incorrect");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Map<String, String> parseFlags(String[] parts) {
        Map<String, String> flags = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("-") && i + 1 < parts.length) {
                flags.put(parts[i], parts[++i]);
            }
        }
        return flags;
    }
}
