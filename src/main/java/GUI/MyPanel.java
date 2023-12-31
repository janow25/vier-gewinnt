package GUI;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private int tH = Connect4GUI.getTokenHeight();
    private int tW = Connect4GUI.getTokenWidth();

     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
     }

     public void drawBoard(Graphics g) {
         ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         Graphics2D g2d = (Graphics2D) g;
         for (int i = tW; i <= Connect4GUI.getPanelWidth() - tW; i += tW) {
             g2d.setColor(Color.BLACK);
             g2d.drawLine(i, tH, i, tH * (Connect4GUI.getPlayingRows() + 1));
             if(i < Connect4GUI.getPanelWidth() - 2 * tW) {
                 for (int j = tH; j <= Connect4GUI.getPanelHeight() - 2 * tH; j += tH) {
                     g2d.setColor(Color.BLACK);
                     g2d.drawOval(i + 2, j, tW - 4, tH - 2);
                 }
             }
         }
         g2d.drawLine(tW, tH * (Connect4GUI.getPlayingRows() + 1), tW * (Connect4GUI.getPlayingColumns() + 1), tH * (Connect4GUI.getPlayingRows() + 1));
     }
}
