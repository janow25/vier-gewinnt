package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Connect4GUI implements ActionListener, MouseListener, MouseMotionListener {
    private static Connect4GUI instance;

    private MyFrame frame;
    private MyPanel panel;
    private final int panelWidth = 1300;
    private final int panelHeight = 1000;
    private int playingRows = 6;
    private int playingColumns = 7;
    private int tokenWidth = panelWidth / (1 + playingColumns + 1);
    private int tokenHeight = panelHeight / (1 + playingRows + 1);
    private boolean playerWon = false;

    //Singleton
    public static Connect4GUI getInstance() {
        if(instance == null) {
            instance = new Connect4GUI();
        }
        return instance;
    }

    public void createGUI() {
        frame = new MyFrame();
        panel = new MyPanel();

        createPlayingField();

        createPointDisplay();

        createMenuBar();

        frame.pack();
        frame.setVisible(true);
    }

    public void createPlayingField() {
        panel.setPreferredSize(new Dimension(getPanelWidth(), getPanelHeight()));
        panel.setLayout(new BorderLayout());



        frame.add(panel, BorderLayout.CENTER);
    }

    public void createPointDisplay() {
        JPanel points = new JPanel();
        points.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        points.setLayout(new BoxLayout(points, BoxLayout.LINE_AXIS));
        points.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(points, BorderLayout.SOUTH);

        points.add(Box.createGlue());

        JLabel player = new JLabel("Player: 0");
        player.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(player);

        points.add(Box.createRigidArea(new Dimension(20,0)));

        JLabel cpu = new JLabel("CPU: 0");
        cpu.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(cpu);

        points.add(Box.createGlue());
    }

    public void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.NORTH);

        JMenu menu1 = new JMenu("Bearbeiten");
        JMenuItem item1 = new JMenuItem("Name");
        menu1.add(item1);

        JMenu menu2 = new JMenu("Profil");
        JMenu menu3 = new JMenu("Hilfe");

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getPlayingRows() {
        return playingRows;
    }

    public void setPlayingRows(int playingRows) {
        this.playingRows = playingRows;
        setTokenHeight(this.playingRows);
    }

    public int getPlayingColumns() {
        return playingColumns;
    }

    public void setPlayingColumns(int playingColumns) {
        this.playingColumns = playingColumns;
        setTokenWidth(this.playingColumns);
    }

    public int getTokenWidth() {
        return tokenWidth;
    }

    private void setTokenWidth(int playingColumns) {
        this.tokenWidth = this.panelWidth / (1 + playingColumns + 1);
    }

    public int getTokenHeight() {
        return tokenHeight;
    }

    private void setTokenHeight(int playingRows) {
        this.tokenHeight = this.panelHeight / (1 + playingRows + 1);
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }
}