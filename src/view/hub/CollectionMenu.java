package view.hub;

import model.App;
import model.User;
import model.entity.plant.Plant;
import model.entity.zombie.Zombie;
import model.enums.Menu;
import model.factory.PlantFactory;
import model.factory.ZombieFactory;
import model.greenhouse.Greenhouse;
import model.shop.Shop;
import model.shop.ShopItem;
import view.AppMenu;

import java.util.HashMap;
import java.util.Map;
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
        greenhouse.restore(user.getGreenhouseState());

        if (App.handleGlobalMenuCommand(input)) {
            return;
        }

        if (input.equals("plants") || input.equals("menu collection show-plants")
                || input.equals("menu collection show-all-plants")
                || input.equals("show all plants") || input.equals("show available plants")) {
            showPlants(user);
        } else if (input.equals("shop") || input.equals("enter shop") || input.equals("shop list")) {
            showShop(user);
        } else if (input.equals("shop daily")) {
            printItem(shop.getDailyDeal());
        } else if (input.equals("menu collection show-zombies")
                || input.equals("menu collection show-all-zombies")) {
            showZombies();
        } else if (input.startsWith("menu collection show-plant")) {
            showPlant(parseFlags(parts).get("-p"));
        } else if (input.startsWith("menu collection show-zombie")) {
            showZombie(parseFlags(parts).get("-z"));
        } else if (input.startsWith("menu collection purchase-plant")) {
            buyPlant(user, parseFlags(parts).get("-p"));
        } else if (input.startsWith("menu collection upgrade-plant")) {
            boostSelectedPlant(user, parseFlags(parts).get("-p"));
        } else if (input.startsWith("add plant")) {
            addSelectedPlant(user, parseFlags(parts).get("-t"));
        } else if (input.startsWith("remove plant")) {
            removeSelectedPlant(user, parseFlags(parts).get("-t"));
        } else if (input.startsWith("boost plant")) {
            boostSelectedPlant(user, parseFlags(parts).get("-t"));
        } else if (input.startsWith("shop buy")) {
            buyOfficial(user, parseFlags(parts));
        } else if (parts.length == 2 && parts[0].equals("buy")) {
            buy(user, parts[1]);
        } else if (input.equals("greenhouse")) {
            System.out.println(greenhouse.getStatus());
        } else if (parts.length == 3 && parts[0].equals("grow")) {
            grow(user, parts[1], parts[2]);
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
        System.out.println("selected plants: " + String.join(", ", user.getSelectedPlantTypes()));
        System.out.println("coins: " + user.getCoins() + " gems: " + user.getGems());
        showPlant(user, "basic");
        showPlant(user, "shooter");
        showPlant(user, "slow");
    }

    private void showZombies() {
        showZombie("basic");
        showZombie("fast");
        showZombie("tank");
    }

    private void showPlant(String type) {
        Plant plant = PlantFactory.create(type);
        System.out.println(plant.getName()
                + " | sun=" + plant.getSunCost()
                + " damage=" + plant.getAttackDamage()
                + " range=" + plant.getAttackRange());
    }

    private void showPlant(User user, String type) {
        Plant plant = PlantFactory.create(type);
        for (int level = 1; level < user.getPlantLevel(type); level++) {
            plant.upgrade();
        }
        System.out.println(plant.getName()
                + " | level=" + user.getPlantLevel(type)
                + " | sun=" + plant.getSunCost()
                + " damage=" + plant.getAttackDamage()
                + " range=" + plant.getAttackRange());
    }

    private void showZombie(String type) {
        Zombie zombie = ZombieFactory.create(type);
        System.out.println(zombie.getType()
                + " | speed=" + zombie.getSpeed()
                + " damage=" + zombie.getDamage()
                + " health=" + zombie.getHealth()
                + " cost=" + zombie.getCostToSpawn());
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
        buy(user, itemId, 1, null);
    }

    private void buy(User user, String itemId, int count, String plantType) {
        if (shop.purchaseItem(itemId, count, plantType, user)) {
            App.saveGameState();
            System.out.println("purchase successful");
        } else {
            System.out.println("purchase failed");
        }
        showPlants(user);
    }

    private void buyOfficial(User user, Map<String, String> flags) {
        try {
            int count = Integer.parseInt(flags.getOrDefault("-n", "1"));
            buy(user, flags.get("-i"), count, flags.get("-t"));
        } catch (NumberFormatException exception) {
            System.out.println("count must be a number");
        }
    }

    private void buyPlant(User user, String plantType) {
        String itemId = switch (plantType == null ? "" : plantType.toLowerCase()) {
            case "basic" -> "seed_basic";
            case "shooter" -> "seed_shooter";
            case "slow" -> "seed_slow";
            default -> "";
        };
        buy(user, itemId);
    }

    private void addSelectedPlant(User user, String plantType) {
        if (user.addSelectedPlant(plantType)) {
            App.saveGameState();
            System.out.println("plant added to loadout");
        } else {
            System.out.println("could not add plant to loadout");
        }
        System.out.println("selected plants: " + String.join(", ", user.getSelectedPlantTypes()));
    }

    private void removeSelectedPlant(User user, String plantType) {
        if (user.removeSelectedPlant(plantType)) {
            App.saveGameState();
            System.out.println("plant removed from loadout");
        } else {
            System.out.println("could not remove plant from loadout");
        }
        System.out.println("selected plants: " + String.join(", ", user.getSelectedPlantTypes()));
    }

    private void boostSelectedPlant(User user, String plantType) {
        if (!user.removeGems(1)) {
            System.out.println("not enough gems");
            return;
        }
        if (user.boostPlant(plantType)) {
            App.saveGameState();
            System.out.println("plant boosted to level " + user.getPlantLevel(plantType));
        } else {
            user.addGems(1);
            System.out.println("could not boost plant");
        }
    }

    private void grow(User user, String rowText, String colText) {
        try {
            int row = Integer.parseInt(rowText) - 1;
            int col = Integer.parseInt(colText) - 1;
            if (greenhouse.plantInPot(col, row)) {
                saveGreenhouse(user);
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
                saveGreenhouse(user);
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
                saveGreenhouse(user);
                System.out.println("harvested marigold for 50 coins");
            } else {
                user.unlockPlant("basic");
                saveGreenhouse(user);
                System.out.println("harvested seed packet");
            }
            System.out.println(greenhouse.getStatus());
        } catch (NumberFormatException exception) {
            System.out.println("row and col must be numbers");
        }
    }

    private void saveGreenhouse(User user) {
        user.setGreenhouseState(greenhouse.serialize());
        App.saveGameState();
    }

    private Map<String, String> parseFlags(String[] parts) {
        Map<String, String> flags = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("-") && i + 1 < parts.length) {
                flags.put(parts[i], parts[++i]);
            }
        }
        return flags;
    }
}
