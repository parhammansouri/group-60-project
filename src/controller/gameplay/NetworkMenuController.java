package controller.gameplay;

import model.App;
import model.User;

public class NetworkMenuController {
    public User currentUser() {
        return App.getLoggedInUser();
    }
}
