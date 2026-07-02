package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import view.AppMenu;

import java.util.Scanner;

public class SettingsMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("Settings: nickname <name> | email <address> | password <old> <new>");
        System.out.println("gender <value> | difficulty <1-5> | stay logged in <on|off> | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+", 3);

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (parts.length == 2 && parts[0].equals("difficulty")) {
            try {
                user.setDifficultyLevel(Integer.parseInt(parts[1]));
                App.saveGameState();
                System.out.println("difficulty updated");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else if (parts.length == 2 && parts[0].equals("nickname")) {
            updateNickname(user, parts[1]);
        } else if (parts.length == 2 && parts[0].equals("email")) {
            updateEmail(user, parts[1]);
        } else if (parts.length == 2 && parts[0].equals("gender")) {
            user.setGender(parts[1]);
            App.saveGameState();
            System.out.println("gender updated");
        } else if (parts.length == 3 && parts[0].equals("password")) {
            updatePassword(user, parts[1], parts[2]);
        } else if (input.equals("stay logged in on")) {
            user.setStayLoggedIn(true);
            App.saveGameState();
            System.out.println("stay logged in enabled");
        } else if (input.equals("stay logged in off")) {
            user.setStayLoggedIn(false);
            App.saveGameState();
            System.out.println("stay logged in disabled");
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
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
}
