package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import model.shop.Shop;
import model.shop.ShopItem;
import view.AppMenu;

import java.util.Scanner;

public class CollectionMenu implements AppMenu {
    private final Shop shop = new Shop();

    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("Collection: plants | shop | buy <itemId> | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (input.equals("plants")) {
            showPlants(user);
        } else if (input.equals("shop")) {
            showShop(user);
        } else if (parts.length == 2 && parts[0].equals("buy")) {
            buy(user, parts[1]);
        } else if (input.equals("back")) {
            App.setCurrentMenu(Menu.Main);
        } else if (input.equals("exit")) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            System.out.println("invalid command");
        }
    }

    private void showPlants(User user) {
        System.out.println("unlocked plants: " + String.join(", ", user.getUnlockedPlantTypes()));
        System.out.println("coins: " + user.getCoins() + " gems: " + user.getGems());
    }

    private void showShop(User user) {
        System.out.println("coins: " + user.getCoins() + " gems: " + user.getGems());
        for (ShopItem item : shop.getPermanentItems()) {
            printItem(item);
        }
        System.out.print("daily: ");
        printItem(shop.getDailyDeal());
    }

    private void printItem(ShopItem item) {
        System.out.println(item.getItemId() + " | " + item.getName()
                + " | coins=" + item.getCostCoins()
                + " gems=" + item.getCostGems()
                + " plant=" + item.getAssociatedPlant());
    }

    private void buy(User user, String itemId) {
        if (shop.purchaseItem(itemId, 1, null, user)) {
            App.saveGameState();
            System.out.println("purchase successful");
        } else {
            System.out.println("purchase failed");
        }
        showPlants(user);
    }
}
