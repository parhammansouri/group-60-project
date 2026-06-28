package model.shop;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Manages the in-game shop where players can purchase items.
 * Contains permanent items and daily deals that refresh at midnight.
 */
public class Shop {
    private List<ShopItem> permanentItems;
    private ShopItem dailyDeal;
    private LocalDate dailyDealDate;
    private Map<String, Boolean> dailyPurchaseStatus;

    public Shop() {
        this.permanentItems = new ArrayList<>();
        permanentItems.add(new ShopItem("seed_basic", "Basic Plant Seed", ShopItemType.SEED_PACKET_SPECIFIC,
                25, 0, 1, 1, "basic"));
        permanentItems.add(new ShopItem("seed_shooter", "Shooter Seed", ShopItemType.SEED_PACKET_SPECIFIC,
                50, 0, 1, 1, "shooter"));
        permanentItems.add(new ShopItem("seed_slow", "Slow Plant Seed", ShopItemType.SEED_PACKET_SPECIFIC,
                35, 0, 1, 1, "slow"));
        permanentItems.add(new ShopItem("plant_food", "Plant Food", ShopItemType.PLANT_FOOD,
                40, 0, 1, 3, null));
        this.dailyPurchaseStatus = new HashMap<>();
        refreshDailyDeal();
    }

    public List<ShopItem> getPermanentItems() {
        return permanentItems;
    }

    public ShopItem getDailyDeal() {
        if (!LocalDate.now().equals(dailyDealDate)) {
            refreshDailyDeal();
        }
        return dailyDeal;
    }

    public boolean isDailyDealPurchased() {
        return Boolean.TRUE.equals(dailyPurchaseStatus.get(dailyDealDate.toString()));
    }

    public boolean purchaseItem(String itemId, int count, String plantType, User user) {
        ShopItem item = getItem(itemId);
        if (item == null || count <= 0) {
            return false;
        }
        if (item == dailyDeal && isDailyDealPurchased()) {
            return false;
        }
        int currentCount = item.getAssociatedPlant() != null && user.hasUnlockedPlant(item.getAssociatedPlant()) ? 1 : 0;
        if (!item.canPurchase(user, currentCount)) {
            return false;
        }
        if (!user.removeCoins(item.getCostCoins() * count) || !user.removeGems(item.getCostGems() * count)) {
            return false;
        }
        if (item.getAssociatedPlant() != null) {
            user.unlockPlant(item.getAssociatedPlant());
        }
        if (item == dailyDeal) {
            dailyPurchaseStatus.put(dailyDealDate.toString(), true);
        }
        return true;
    }

    /**
     * Refresh daily deal (called at midnight)
     */
    public void refreshDailyDeal() {
        dailyDealDate = LocalDate.now();
        dailyDeal = new ShopItem("daily_seed", "Daily Shooter Deal", ShopItemType.SEED_PACKET_SPECIFIC,
                30, 0, 1, 1, "shooter");
        dailyPurchaseStatus.putIfAbsent(dailyDealDate.toString(), false);
    }

    /**
     * Get shop item by ID
     */
    public ShopItem getItem(String itemId) {
        if (itemId == null) {
            return null;
        }
        if (getDailyDeal().getItemId().equals(itemId)) {
            return dailyDeal;
        }
        for (ShopItem item : permanentItems) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }
}
