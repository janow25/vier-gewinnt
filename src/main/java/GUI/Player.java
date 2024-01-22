package GUI;

import javax.swing.*;
import java.awt.Color;

public class Player {
    private final JLabel NAME;
    private final Color COLOR;
    private final JLabel SCORE;

    Player(String name, Color color) {
        this.NAME = new JLabel(name);
        this.COLOR = color;
        this.SCORE = new JLabel(String.valueOf(0));
    }

    public JLabel getNAME() {
        return NAME;
    }

    public JLabel getSCORE() {
        return SCORE;
    }

    public void setSCORE(int SCORE) {
        this.SCORE.setText(Integer.toString(SCORE));
    }

    public Color getCOLOR() {
        return COLOR;
    }
}
