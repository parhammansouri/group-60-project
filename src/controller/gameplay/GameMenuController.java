package controller.gameplay;

import model.App;

public class GameMenuController {
    public void startDefaultGame() {
        App.createNewSession(null, null, null);
    }

    public void endGame() {
        App.endSession();
    }
}
