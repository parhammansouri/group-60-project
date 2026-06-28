package view.auth;

import controller.auth.SignupMenuController;
import view.AppMenu;

import java.util.Scanner;

public class SignupMenu implements AppMenu {
    private final SignupMenuController controller = new SignupMenuController();

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Signup menu: signup <username> <password> <email> <nickname> | login | exit");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+", 5);

        if (parts.length == 5 && parts[0].equals("signup")) {
            System.out.println(controller.register(parts[1], parts[2], parts[3], parts[4]));
        } else if (input.trim().equals("login")) {
            controller.goToLogin();
        } else if (input.trim().equals("exit")) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }

}
