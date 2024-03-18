package GUI.Factories;

import backend.GameStatus;

public class MenuFactory {
    public static void openStartScreen() {
        MenuCreationFactory.openStartScreen();
    }

    public static void changePlayingRows() {
        MenuCreationFactory.openEditScreen();
    }

    public static void changePlayingColumns() {
        MenuCreationFactory.openEditScreen();
    }

    public static void changeBotEnemy() {
        MenuCreationFactory.openEditScreen();
    }

    public static void openEndScreen(GameStatus g) {
        MenuCreationFactory.openEndScreen(g);
    }
}
