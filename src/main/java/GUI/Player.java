package GUI;

import java.awt.Color;

public class Player {
    private static Player instance;

    private String name;
    private int score;
    private Color color;

    //Singleton
    public static Player getInstance() {
        if(instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void createPlayer(String name, int score, Color color) {
        this.name = name;
        this.score = score;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }
}
