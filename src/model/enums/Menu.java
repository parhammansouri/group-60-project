package model.enums;

import view.AppMenu;
import view.auth.*;
import view.gameplay.*;
import view.hub.*;

import java.util.Scanner;

public enum Menu {
    Login(new LoginMenu()),
    Signup(new SignupMenu()),
    Game(new GameplayMenu()),
    Network(new NetworkMenu()),
    Collection(new CollectionMenu()),
    Main(new MainMenu()),
    News(new NewsMenu()),
    Profile(new ProfileMenu()),
    Settings(new SettingsMenu()),
    Exit(null);

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void handle(Scanner scanner) {
        if (this.menu == null) {
            return;
        }
        this.menu.handle(scanner);
    }
}
