package view.auth;

import controller.auth.SignupMenuController;
import model.App;
import view.AppMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignupMenu implements AppMenu {
    private final SignupMenuController controller = new SignupMenuController();

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Signup menu: signup <username> <password> <email> <nickname> | login | exit");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+", 5);

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.trim().startsWith("register ")) {
            registerWithFlags(input.trim().split("\\s+"));
        } else if (parts.length == 5 && parts[0].equals("signup")) {
            System.out.println(controller.register(parts[1], parts[2], parts[3], parts[4]));
        } else if (input.trim().equals("login")) {
            controller.goToLogin();
        } else if (input.trim().equals("exit")) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }

    private void registerWithFlags(String[] parts) {
        Map<String, String> flags = parseFlags(parts);
        String password = flags.get("-p");
        String confirm = flags.get("-p-confirm");
        if (password == null || !password.equals(confirm)) {
            System.out.println("password confirmation does not match");
            return;
        }
        String result = controller.register(flags.get("-u"), password, flags.get("-e"), flags.get("-n"));
        if ("user registered successfully".equals(result) && App.getLoggedInUser() != null) {
            App.getLoggedInUser().setGender(flags.get("-g"));
            App.saveGameState();
        }
        System.out.println(result);
    }

    private Map<String, String> parseFlags(String[] parts) {
        Map<String, String> flags = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            if ("-p".equals(parts[i]) && i + 2 < parts.length) {
                flags.put("-p", parts[++i]);
                flags.put("-p-confirm", parts[++i]);
            } else if (parts[i].startsWith("-") && i + 1 < parts.length) {
                flags.put(parts[i], parts[++i]);
            }
        }
        return flags;
    }

}
