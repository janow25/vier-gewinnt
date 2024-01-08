package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Connect4 implements ActionListener, MouseListener, MouseMotionListener {
    private static Connect4 instance;

    private static MyFrame frame;
    private static MyPanel panel;
    private static final int panelWidth = 1300;
    private static final int panelHeight = 1000;
    private static int playingRows = 6;
    private static int playingColumns = 7;
    private static int tokenWidth = panelWidth / (1 + playingColumns + 1);
    private static int tokenHeight = panelHeight / (1 + playingRows + 1);
    private static boolean playerWon = false;

    private Connect4() {
        frame = new MyFrame();
        panel = new MyPanel();



        createPlayingField();

        createPointDisplay();

        createMenuBar();

        frame.pack();
        frame.setVisible(true);
    }

    //Singleton
    public static Connect4 getInstance() {
        if(instance == null) {
            instance = new Connect4();
        }
        return instance;
    }

    public static void createPlayingField() {
        panel.setPreferredSize(new Dimension(getPanelWidth(), getPanelHeight()));
        panel.setLayout(new BorderLayout());



        frame.add(panel, BorderLayout.CENTER);
    }

    public static void createPointDisplay() {
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

    public static void createMenuBar() {
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
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static int getPanelWidth() {
        return panelWidth;
    }

    public static int getPanelHeight() {
        return panelHeight;
    }

    public static int getPlayingRows() {
        return playingRows;
    }

    public static int getPlayingColumns() {
        return playingColumns;
    }

    public static int getTokenWidth() {
        return tokenWidth;
    }

    public static int getTokenHeight() {
        return tokenHeight;
    }

    public static boolean hasPlayerWon() {
        return playerWon;
    }
}