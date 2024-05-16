package GUI;

import GUI.Factories.CalculationFactory;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private int tH = CalculationFactory.calculateTokenHeight(Connect4GUI.getInstance().getPlayingRows());
    private int tW = CalculationFactory.calculateTokenWidth(Connect4GUI.getInstance().getPlayingColumns());
    private final int TOKENPADDING = 1;
    /**
     * The x coordinate of the hovering Token. Will be -1 if the hovering Token should not be drawn
     */
    private int hoverTokenX;

     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
     }

     public void drawBoard(Graphics g) {
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         Graphics2D g2d = (Graphics2D) g;

         int row = 0; int column = 0;
         for (int i = tW; i <= CalculationFactory.calculateScreenWidth() - tW; i += tW) {
             g2d.setColor(Color.BLACK);
             g2d.drawLine(i, tH, i, tH * (Connect4GUI.getInstance().getPlayingRows() + 1));
             if(i <= CalculationFactory.calculateScreenWidth() - 2 * tW) {
                 row = 0;
                 for (int j = tH; j <= CalculationFactory.calculateScreenHeight() - 2 * tH; j += tH) {
                     g2d.setColor(Connect4GUI.getInstance().getTokenColor(row, column));
                     g2d.fillOval(i + TOKENPADDING, j, tW - (2 * TOKENPADDING), tH - TOKENPADDING);
                     g2d.setColor(Color.BLACK);
                     g2d.drawOval(i + TOKENPADDING, j, tW - (2 * TOKENPADDING), tH - TOKENPADDING);
                     row++;
                 }
             }
             column++;
         }
         g2d.setColor(Color.BLACK);
         g2d.drawLine(tW, tH * (Connect4GUI.getInstance().getPlayingRows() + 1), tW * (Connect4GUI.getInstance().getPlayingColumns() + 1), tH * (Connect4GUI.getInstance().getPlayingRows() + 1));
         if (hoverTokenX > 0) {
             drawHoverToken(g2d);
         }
     }

     private void drawHoverToken(Graphics g) {
         g.setColor(Connect4GUI.getInstance().getPlayerColor());
         g.fillOval(hoverTokenX + TOKENPADDING,0,tW - (2 * TOKENPADDING),tH - TOKENPADDING);
         g.setColor(Color.BLACK);
         g.drawOval(hoverTokenX + TOKENPADDING,0,tW - (2 * TOKENPADDING),tH - TOKENPADDING);
     }

    public void setHoverTokenX(int hoverTokenX) {
        this.hoverTokenX = hoverTokenX;
    }
}
