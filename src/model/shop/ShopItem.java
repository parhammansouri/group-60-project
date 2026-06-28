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

    public String getItemId() {
        // TODO: Implementation
        return itemId;
    }

    public String getName() {
        // TODO: Implementation
        return name;
    }

    public ShopItemType getType() {
        // TODO: Implementation
        return type;
    }

    public int getCostCoins() {
        // TODO: Implementation
        return costCoins;
    }

    public int getCostGems() {
        // TODO: Implementation
        return costGems;
    }

    public int getQuantity() {
        // TODO: Implementation
        return quantity;
    }

    public int getMaxCapacity() {
        // TODO: Implementation
        return maxCapacity;
    }

    public String getAssociatedPlant() {
        // TODO: Implementation
        return associatedPlant;
    }

    /**
     * Check if this item can be purchased right now
     */
    public boolean canPurchase(User user, int currentCount) {
        // TODO: Implementation - Check currency and capacity
        return false;
    }
}

