package controller.hub;

import model.App;
import model.User;

public class SettingsMenuController {
    public String updateDifficulty(int level) {
        User user = App.getLoggedInUser();
        if (user == null) {
            return "no user logged in";
        }
        try {
            user.setDifficultyLevel(level);
            App.saveGameState();
            return "difficulty updated";
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }
}
