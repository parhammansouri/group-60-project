package controller.auth;

import model.App;
import model.User;
import model.enums.Menu;

public class SignupMenuController {
    public String register(String username, String password, String email, String nickname) {
        if (App.usernameExists(username)) {
            return "username already exists";
        }
        try {
            User user = new User(username, password, email, nickname);
            App.addUser(user);
            App.setLoggedInUser(user);
            App.setCurrentMenu(Menu.Main);
            return "user registered successfully";
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    public void goToLogin() {
        App.setCurrentMenu(Menu.Login);
    }

    public void exit() {
        App.setCurrentMenu(Menu.Exit);
    }
}
