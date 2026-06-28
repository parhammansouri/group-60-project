package view.hub;

import model.App;
import model.User;
import model.enums.Menu;
import model.greenhouse.Greenhouse;
import model.shop.Shop;
import model.shop.ShopItem;
import view.AppMenu;

import java.util.Scanner;

public class CollectionMenu implements AppMenu {
    private final Shop shop = new Shop();
    private final Greenhouse greenhouse = new Greenhouse();

    @Override
    public void handle(Scanner scanner) {
        User user = App.getLoggedInUser();
        if (user == null) {
            App.setCurrentMenu(Menu.Login);
            return;
        }

        System.out.println("Collection: plants | shop | buy <itemId> | greenhouse");
        System.out.println("grow <row> <col> | boost <row> <col> | harvest <row> <col> | back | exit");
        String input = scanner.nextLine().trim();
        String[] parts = input.split("\\s+");

        if (input.equals("plants")) {
            showPlants(user);
        } else if (input.equals("shop")) {
            showShop(user);
        } else if (parts.length == 2 && parts[0].equals("buy")) {
            buy(user, parts[1]);
        } else if (input.equals("greenhouse")) {
            System.out.println(greenhouse.getStatus());
        } else if (parts.length == 3 && parts[0].equals("grow")) {
            grow(parts[1], parts[2]);
        } else if (parts.length == 3 && parts[0].equals("boost")) {
            boost(user, parts[1], parts[2]);
        } else if (parts.length == 3 && parts[0].equals("harvest")) {
            harvest(user, parts[1], parts[2]);
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

    private void grow(String rowText, String colText) {
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            if (greenhouse.plantInPot(col, row)) {
                System.out.println("plant started growing");
            } else {
                System.out.println("could not grow plant there");
            }
            System.out.println(greenhouse.getStatus());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void boost(User user, String rowText, String colText) {
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            if (greenhouse.growInstant(col, row, user.getGems()) && user.removeGems(1)) {
                App.saveGameState();
                System.out.println("plant boosted");
            } else {
                System.out.println("could not boost plant");
            }
            System.out.println(greenhouse.getStatus());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void harvest(User user, String rowText, String colText) {
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            Integer reward = greenhouse.collectPlant(col, row);
            if (reward == null) {
                System.out.println("nothing ready to harvest");
            } else if (reward == 50) {
                user.addCoins(reward);
                App.saveGameState();
                System.out.println("harvested marigold for 50 coins");
            } else {
                user.unlockPlant("basic");
                App.saveGameState();
                System.out.println("harvested seed packet");
            }
            System.out.println(greenhouse.getStatus());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }
}
