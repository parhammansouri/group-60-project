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

        System.out.println("Settings: difficulty <1-5> | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (parts.length == 2 && parts[0].equals("difficulty")) {
            try {
                user.setDifficultyLevel(Integer.parseInt(parts[1]));
                App.saveGameState();
                System.out.println("difficulty updated");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }
}
