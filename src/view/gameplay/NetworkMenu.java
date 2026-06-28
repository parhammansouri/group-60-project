package view.gameplay;

import model.App;
import model.enums.Menu;
import view.AppMenu;

import java.util.Scanner;

public class NetworkMenu implements AppMenu {
    @Override
    public void handle(Scanner scanner) {
        System.out.println("Network is not implemented yet. back | exit");
        String input = scanner.nextLine().trim();
        if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }

    }
}
