package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import view.AppMenu;

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

        if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }
}
