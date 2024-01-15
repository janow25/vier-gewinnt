package GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class Connect4GUI extends MouseInputAdapter implements ActionListener {
    private static Connect4GUI instance;

    private MyFrame frame;
    private MyPanel panel;
    private JPanel playingArea;
    private int playingRows = 6;
    private int playingColumns = 7;
    private final Color[][] board = new Color[playingRows][playingColumns];

    //:TODO change simple variables to Player objects
    private final Color player1 = Color.RED;
    private final Color player2 = Color.YELLOW;


    private boolean playersTurn = false;
    private boolean playerWon = false;
    private boolean turnFinished = true;

    //Singleton
    public static Connect4GUI getInstance() {
        if(instance == null) {
            instance = new Connect4GUI();
        }
        return instance;
    }

    private Connect4GUI() {}

    public void createGUI() {
        frame = new MyFrame();
        panel = new MyPanel();

        createPlayingField();

        createPointDisplay();

        createMenuBar();

        Timer timer = new Timer(10,this);
        timer.start();

        frame.pack();
        frame.setVisible(true);
    }

    private void createPlayingField() {
        panel.setPreferredSize(CalculationFactory.calculatePanelDimension());
        panel.setLayout(null);

        playingArea = new JPanel();
        playingArea.setBounds(
                CalculationFactory.calculateTokenWidth(playingColumns),
                CalculationFactory.calculateTokenHeight(playingRows),
                CalculationFactory.calculateTokenWidth(playingColumns) * playingColumns,
                CalculationFactory.calculateTokenHeight(playingRows) * playingRows);
        playingArea.setOpaque(false);
        playingArea.addMouseListener(this);
        playingArea.addMouseMotionListener(this);

        panel.add(playingArea);
        panel.revalidate();
        frame.add(panel);
    }

    //:TODO change it so the point display uses the Player classes instead of Strings and numbers
    private void createPointDisplay() {
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

    //:TODO add further Menu elements and give them functionalities
    private void createMenuBar() {
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

    private void dropToken(MouseEvent e) {
        int tokenWidth = CalculationFactory.calculateTokenWidth(playingColumns);

        //Checks if index out of Bounds
        if(e.getY()/CalculationFactory.calculateTokenHeight(playingRows) >= playingRows && e.getX()/tokenWidth >= playingColumns) return;

        //Checks if the top most Token in a specific column is already taken/not null. If true then the column is full -> return
        if (board[0][e.getX()/tokenWidth] != null) {
            setTurnFinished(true);
            return;
        }

        new Thread(() -> {
            for (int i = 0; i < playingRows && board[i][e.getX()/tokenWidth] == null; i++) {
                try { Thread.sleep(60); } catch(Exception ignored) {}
                setTokenColor(i - 1,e.getX()/tokenWidth, null);
                setTokenColor(i,e.getX()/tokenWidth, getPlayerColor(playersTurn));
            }
            nextPlayer(playersTurn);
            setTurnFinished(true);
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (turnFinished) {
            setTurnFinished(false);
            dropToken(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        playingArea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        hoverToken(e);
    }

    private void hoverToken(MouseEvent e) {
        //:TODO create the method so a visible Token in the correct Player color hovers over a column where the mouse currently is
    }

    public int getPlayingRows() {
        return playingRows;
    }

    public void setPlayingRows(int playingRows) {
        this.playingRows = playingRows;
    }

    public int getPlayingColumns() {
        return playingColumns;
    }

    public void setPlayingColumns(int playingColumns) {
        this.playingColumns = playingColumns;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public void nextPlayer(boolean playersTurn) {
        this.playersTurn = !playersTurn;
    }

    public Color[][] getBoard() {
        return board;
    }

    /**
     * Should only be used from the drawBoard() Method in MyPanel Class
     * @param row Y Coordinate of the Mouse
     * @param column X Coordinate of the Mouse
     * @return the Color of the Token at the row and column where the Mouse was clicked
     */
    public Color getTokenColor(int row, int column) {
        if (board[row/CalculationFactory.calculateTokenHeight(playingRows) - 1][column/CalculationFactory.calculateTokenWidth(playingColumns) - 1] == null) {
            return Color.WHITE;
        } else {
            return board[row/CalculationFactory.calculateTokenHeight(playingRows) - 1][column/CalculationFactory.calculateTokenWidth(playingColumns) - 1];
        }
    }

    public void setTokenColor(int row, int column, Color color) {
        if (row >= 0 && column >= 0) {
            board[row][column] = color;
        }
    }

    //:TODO make it so the method returns the color of the instance of the right Player(class) instance
    public Color getPlayerColor(boolean playersTurn) {
        if(playersTurn){
            return player1;
        }
        return player2;
    }

    public void setTurnFinished(boolean turnFinished) {
        this.turnFinished = turnFinished;
    }
}