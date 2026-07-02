package view.hub;

import model.App;
import model.enums.Menu;
import view.AppMenu;

import java.util.Scanner;

public class MainMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Main menu: menu <profile|settings|collection|news|game|network> | logout | exit");
        String input = scanner.nextLine().trim();

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("logout")) {
            App.logout();
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else if (input.equals("menu profile")) {
            App.setCurrentMenu(Menu.Profile);
        } else if (input.equals("menu settings")) {
            App.setCurrentMenu(Menu.Settings);
        } else if (input.equals("menu collection")) {
            App.setCurrentMenu(Menu.Collection);
        } else if (input.equals("menu news")) {
            App.setCurrentMenu(Menu.News);
        } else if (input.equals("menu game")) {
            App.setCurrentMenu(Menu.Game);
        } else if (input.equals("menu network")) {
            App.setCurrentMenu(Menu.Network);
        } else {
            System.out.println("invalid command");
        }
    }
}
