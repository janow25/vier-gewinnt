package GUI;

import java.awt.Color;

public class DroppedToken {
    private int row;
    private final int column;
    private final Color color;

    public DroppedToken(int row, int column, Color color) {
        this.row = row;
        this.column = column;
        this.color = color;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Color getColor() {
        return color;
    }
}
