package controller.hub;

import model.App;
import model.User;

public class ProfileMenuController {
    public User currentUser() {
        return App.getLoggedInUser();
    }
}
