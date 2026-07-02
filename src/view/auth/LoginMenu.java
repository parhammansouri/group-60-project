package view.auth;

import controller.auth.LoginMenuController;
import view.AppMenu;

import java.util.Scanner;

public class LoginMenu implements AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Login menu: login <username> <password> | signup | exit");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

        if (model.App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (parts.length == 3 && parts[0].equals("login")) {
            System.out.println(controller.login(parts[1], parts[2]));
        } else if (input.trim().equals("signup")) {
            controller.goToSignup();
        } else if (input.trim().equals("exit")) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }

}
