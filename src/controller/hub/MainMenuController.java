package controller.hub;

import model.App;
import model.enums.Menu;

public class MainMenuController {
    public void open(Menu menu) {
        App.setCurrentMenu(menu);
    }

    public void logout() {
        App.logout();
    }

    public void exit() {
        App.setCurrentMenu(Menu.Exit);
    }
}
