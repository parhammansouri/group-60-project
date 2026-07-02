package view.auth;

import controller.auth.LoginMenuController;
import model.App;
import view.AppMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginMenu implements AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void handle(Scanner scanner) {
        System.out.println("Login menu: login <username> <password> | signup | exit");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.trim().startsWith("login -")) {
            loginWithFlags(parts);
        } else if (parts.length == 3 && parts[0].equals("login")) {
            System.out.println(controller.login(parts[1], parts[2]));
        } else if (input.trim().equals("signup")) {
            controller.goToSignup();
        } else if (input.trim().equals("exit")) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }

    private void loginWithFlags(String[] parts) {
        Map<String, String> flags = parseFlags(parts);
        String result = controller.login(flags.get("-u"), flags.get("-p"));
        if ("logged in successfully".equals(result)
                && App.getLoggedInUser() != null
                && flags.containsKey("-stay-logged-in")) {
            App.getLoggedInUser().setStayLoggedIn(true);
            App.saveGameState();
        }
        System.out.println(result);
    }

    private Map<String, String> parseFlags(String[] parts) {
        Map<String, String> flags = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            if ("-stay-logged-in".equals(parts[i])) {
                flags.put(parts[i], "true");
            } else if (parts[i].startsWith("-") && i + 1 < parts.length) {
                flags.put(parts[i], parts[++i]);
            }
        }
        return flags;
    }

}
