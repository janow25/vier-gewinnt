package GUI;

import GUI.Factories.CalculationFactory;
import GUI.Factories.MenuFactory;
import backend.Difficulty;
import backend.GameStatus;
import backend.VierGewinnt;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Connect4GUI extends MouseInputAdapter implements ActionListener {
    private static Connect4GUI instance;
    private MyFrame frame;
    private MyPanel panel;
    private JPanel playingArea;
    private final int DEFAULTROWS = 6;
    private final int DEFAULTCOLUMNS = 7;
    private int playingRows = 6;
    private int playingColumns = 7;
    private Color[][] board = new Color[playingRows][playingColumns];
    private VierGewinnt viergewinnt = new VierGewinnt(DEFAULTCOLUMNS,DEFAULTROWS);
    private final java.util.List<Player> PLAYERS = new ArrayList<>();
    private boolean turnFinished = true;

    //Singleton
    public static Connect4GUI getInstance() {
        if(instance == null) {
            instance = new Connect4GUI();
        }
        return instance;
    }

    Connect4GUI() {}

    public void startGame() {
        MenuFactory.openStartScreen();
    }

    public void createGUI() {
        if (viergewinnt == null) {
            viergewinnt = new VierGewinnt(playingColumns, playingRows);
        }

        frame = new MyFrame();
        panel = new MyPanel();

        createPlayingField();

        createPlayers();

        createPointDisplay();

        createMenuBar();

        Timer timer = new Timer(10,this);
        timer.start();

        frame.pack();
        frame.setVisible(true);
    }

    public void loadGame() {
        viergewinnt = VierGewinnt.load();
        syncBoard();
        if (!turnFinished) setTurnFinished(true);
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

    public void createPlayers() {
        Player player1 = new Player("Spieler1", Color.RED);
        Player player2 = new Player("Spieler2", Color.YELLOW);
        PLAYERS.add(player1);
        PLAYERS.add(player2);
    }

    private void createPointDisplay() {
        JPanel points = new JPanel();
        points.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        points.setLayout(new BoxLayout(points, BoxLayout.LINE_AXIS));
        points.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(points, BorderLayout.SOUTH);

        try {
            PLAYERS.get(0);
            PLAYERS.get(1);
        } catch (Exception ignored) {
            createPlayers();
        }

        points.add(Box.createGlue());

        PLAYERS.get(0).getNAME().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(PLAYERS.get(0).getNAME());

        points.add(Box.createRigidArea(new Dimension(5,0)));

        PLAYERS.get(0).getSCORELABEL().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(PLAYERS.get(0).getSCORELABEL());

        points.add(Box.createRigidArea(new Dimension(20,0)));

        PLAYERS.get(1).getNAME().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(PLAYERS.get(1).getNAME());

        points.add(Box.createRigidArea(new Dimension(5,0)));

        PLAYERS.get(1).getSCORELABEL().setAlignmentY(Component.BOTTOM_ALIGNMENT);
        points.add(PLAYERS.get(1).getSCORELABEL());

        points.add(Box.createGlue());
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.NORTH);

        JMenu menu = new JMenu("Einstellungen");
        JMenuItem rows = new JMenuItem("Zeilen");
        JMenuItem columns = new JMenuItem("Spalten");
        JMenuItem bots = new JMenuItem("Bot");
        rows.addActionListener(e -> {
            MenuFactory.changePlayingRows();
            frame.dispose();
        });
        columns.addActionListener(e -> {
            MenuFactory.changePlayingColumns();
            frame.dispose();
        });
        bots.addActionListener(e -> {
            MenuFactory.changeBotEnemy();
            frame.dispose();
        });
        menu.add(rows);
        menu.add(columns);
        menu.add(bots);

        menuBar.add(menu);
    }

    private void dropToken(MouseEvent e) {
        int tokenWidth = CalculationFactory.calculateTokenWidth(playingColumns);

        //Checks if index out of Bounds
        if(e.getY()/CalculationFactory.calculateTokenHeight(playingRows) >= playingRows && e.getX()/tokenWidth >= playingColumns) return;

        //Checks if the top most Token in a specific column is already taken/not null. If true then the column is full -> return
        if (isColumnFull(e)) {
            setTurnFinished(true);
            return;
        }

        int column = e.getX()/tokenWidth;

        //Animation
        new Thread(() -> {
            for (int i = 0; i < playingRows && board[i][column] == null; i++) {
                try { Thread.sleep(50); } catch(Exception ignored) {}
                setTokenColor(i - 1, column, null);
                setTokenColor(i, column, getPlayerColor());
            }
            // Set Token in Backend
            viergewinnt.addToken(column + 1);

            if (checkIfGameIsOnGoing()) {
                if (viergewinnt.isBotTurn()) {
                    botDropToken(viergewinnt.getBot().makeMove(viergewinnt) - 1);
                } else {
                    viergewinnt.save();
                    syncBoard();
                    setTurnFinished(true);
                }
            } else {
                increaseScoreOfWinner();
                gameEnded();
            }
        }).start();
    }

    private void botDropToken(int index) {
        //Animation
        new Thread(() -> {
            // Set Token in Backend
            System.out.println(viergewinnt);

            for (int i = 0; i < playingRows && board[i][index] == null; i++) {
                try { Thread.sleep(50); } catch(Exception ignored) {}
                setTokenColor(i - 1, index, null);
                setTokenColor(i, index, getBotColor());
            }

            if (checkIfGameIsOnGoing()) {
                viergewinnt.save();
                syncBoard();
                setTurnFinished(true);
            } else {
                increaseScoreOfWinner();
                gameEnded();
            }
        }).start();
    }

    private boolean checkIfGameIsOnGoing() {
        return viergewinnt.getGameStatus() == GameStatus.onGoing;
    }

    private boolean isColumnFull(MouseEvent e) {
        return board[0][e.getX()/CalculationFactory.calculateTokenWidth(playingColumns)] != null;
    }

    private void increaseScoreOfWinner() {
        if (viergewinnt.getGameStatus() == GameStatus.playerOneWon) {
            PLAYERS.get(0).setSCORE(PLAYERS.get(0).getScore() + 1);
        } else if (viergewinnt.getGameStatus() == GameStatus.playerTwoWon) {
            PLAYERS.get(1).setSCORE(PLAYERS.get(1).getScore() + 1);
        }
    }

    private void gameEnded() {
        MenuFactory.openEndScreen(viergewinnt.getGameStatus());
    }

    public void createNewBoard(int rows, int columns, Difficulty difficulty) {
        setPlayingRows(rows);
        setPlayingColumns(columns);
        setTurnFinished(true);

        viergewinnt = new VierGewinnt(playingColumns, playingRows, difficulty);
        viergewinnt.save();

        syncBoard();
        createGUI();
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
        if (turnFinished && !isColumnFull(e)) {
            hoverToken(e);
        } else {
            panel.setHoverTokenX(-1);
        }
    }

    private void hoverToken(MouseEvent e) {
        panel.setHoverTokenX(
                (e.getX()/CalculationFactory.calculateTokenWidth(playingColumns) + 1) * CalculationFactory.calculateTokenWidth(playingColumns)
        );
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

    public void setBoard(int playingRows, int playingColumns) {
        board = new Color[playingRows][playingColumns];
    }

    public void syncBoard() {
        setPlayingColumns(viergewinnt.getNumberOfColumns());
        setPlayingRows(viergewinnt.getNumberOfRows());

        setBoard(playingRows, playingColumns);

        for (int i = 0; i < playingRows; i++) {
            for (int j = 0; j < playingColumns; j++) {
                board[i][j] = viergewinnt.getColor(j, playingRows-i-1);
            }
        }
    }

    /**
     * Should only be used from the drawBoard() Method in MyPanel Class
     * @param row Y Coordinate of the Mouse
     * @param column X Coordinate of the Mouse
     * @return the Color of the backend.Token at the row and column where the Mouse was clicked
     */
    public Color getTokenColor(int row, int column) {
        if (board[row][column] == null) {
            return Color.WHITE;
        }
        return board[row][column];
    }

    public void setTokenColor(int row, int column, Color color) {
        if (row >= 0 && column >= 0) {
            board[row][column] = color;
        }
    }

    public Color getPlayerColor() {
        return viergewinnt.getCurrentToken().toColor();
    }

    public Color getBotColor() {
        return viergewinnt.getCurrentToken().other().toColor();
    }

    public void setTurnFinished(boolean turnFinished) {
        this.turnFinished = turnFinished;
    }

    public MyFrame getFrame() {
        return frame;
    }

    public int getDEFAULTROWS() {
        return DEFAULTROWS;
    }

    public int getDEFAULTCOLUMNS() {
        return DEFAULTCOLUMNS;
    }

    public MyPanel getPanel() {
        return panel;
    }

    public VierGewinnt getViergewinnt() {
        return viergewinnt;
    }

    public java.util.List<Player> getPLAYERS() {
        return PLAYERS;
    }
}