package model.shop;

import model.User;

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
        // TODO: Implementation - Load permanent items and daily deal
    }

    public List<ShopItem> getPermanentItems() {
        // TODO: Implementation
        return permanentItems;
    }

    public ShopItem getDailyDeal() {
        // TODO: Implementation
        return dailyDeal;
    }

    public boolean isDailyDealPurchased() {
        // TODO: Implementation
        return false;
    }

    public boolean purchaseItem(String itemId, int count, String plantType, User user) {
        // TODO: Implementation - Check currency, limits, validity
        return false;
    }

    /**
     * Refresh daily deal (called at midnight)
     */
    public void refreshDailyDeal() {
        // TODO: Implementation - Select new random plant, reset purchase flag
    }

    /**
     * Get shop item by ID
     */
    public ShopItem getItem(String itemId) {
        // TODO: Implementation
        return null;
    }
}
