package controller.hub;

import model.App;
import model.User;

import java.util.List;

public class CollectionMenuController {
    public List<String> unlockedPlantTypes() {
        User user = App.getLoggedInUser();
        return user == null ? List.of() : user.getUnlockedPlantTypes();
    }
}
