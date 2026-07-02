package model.shop;

import model.User;

/**
 * Represents a purchasable item in the shop.
 */
public class ShopItem {
    private String itemId;
    private String name;
    private ShopItemType type;
    private int costCoins;
    private int costGems;
    private int quantity;
    private int maxCapacity;
    private String associatedPlant;

    public ShopItem(String itemId, String name, ShopItemType type, int costCoins, int costGems,
                    int quantity, int maxCapacity, String associatedPlant) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.costCoins = costCoins;
        this.costGems = costGems;
        this.quantity = quantity;
        this.maxCapacity = maxCapacity;
        this.associatedPlant = associatedPlant;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public ShopItemType getType() {
        return type;
    }

    public int getCostCoins() {
        return costCoins;
    }

    public int getCostGems() {
        return costGems;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getAssociatedPlant() {
        return associatedPlant;
    }

    /**
     * Check if this item can be purchased right now
     */
    public boolean canPurchase(User user, int currentCount) {
        if (user == null || user.getCoins() < costCoins || user.getGems() < costGems) {
            return false;
        }
        return maxCapacity <= 0 || currentCount + quantity <= maxCapacity;
    }

    public boolean canPurchase(User user, int currentCount, int count) {
        if (count <= 0 || user == null || user.getCoins() < costCoins * count || user.getGems() < costGems * count) {
            return false;
        }
        return maxCapacity <= 0 || currentCount + quantity * count <= maxCapacity;
    }
}
