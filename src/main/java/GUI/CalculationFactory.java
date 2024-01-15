package GUI;

import java.awt.*;

public class CalculationFactory {
    public static int calculateScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width - 400;
    }

    public static int calculateScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height - 300;
    }

    public static Dimension calculatePanelDimension() {
        return new Dimension(calculateScreenWidth(), calculateScreenHeight());
    }

    public static int calculateTokenWidth(int playingColumns) {
        return calculateScreenWidth() / (1 + playingColumns + 1);
    }

    public static int calculateTokenHeight(int playingRows) {
        return calculateScreenHeight() / (1 + playingRows + 1);
    }
}
