package controller.auth;

import model.App;
import model.User;
import model.enums.Menu;

public class LoginMenuController {
    public String login(String username, String password) {
        User user = App.findUser(username);
        if (user == null || !user.verifyPassword(password)) {
            return "invalid username or password";
        }
        App.setLoggedInUser(user);
        App.setCurrentMenu(Menu.Main);
        return "logged in successfully";
    }

    public void goToSignup() {
        App.setCurrentMenu(Menu.Signup);
    }

    public void exit() {
        App.setCurrentMenu(Menu.Exit);
    }
}
