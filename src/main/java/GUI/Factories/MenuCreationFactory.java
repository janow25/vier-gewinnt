package GUI.Factories;

import GUI.Connect4GUI;
import GUI.Player;
import backend.Difficulty;
import backend.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuCreationFactory {
    public static void openStartScreen() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JLabel welcome = new JLabel("4 Gewinnt");
        JLabel creatorsNote = new JLabel("Ein Projekt von Janne, Philipp und Robin");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        creatorsNote.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        //Buttons
        JButton newGame = new JButton("Neues Spiel");
        JButton loadGame = new JButton("Spiel laden");
        JButton settings = new JButton("Einstellungen");
        newGame.setAlignmentY(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentY(Component.CENTER_ALIGNMENT);
        settings.setAlignmentY(Component.CENTER_ALIGNMENT);

        newGame.addActionListener(e -> {
            if (Connect4GUI.getInstance().getPLAYERS().size() != 0) {
                resetPlayerScore();
            }
            Connect4GUI.getInstance().createNewBoard(
                    Connect4GUI.getInstance().getDEFAULTROWS(),
                    Connect4GUI.getInstance().getDEFAULTCOLUMNS(),
                    Difficulty.noBot
            );
            frame.dispose();
        });

        loadGame.addActionListener(e -> {
            Connect4GUI.getInstance().loadGame();
            Connect4GUI.getInstance().createGUI();
            frame.dispose();
        });
        if (!new File("./savegame.bin").exists()) {
            loadGame.setEnabled(false);
        }

        settings.addActionListener(e -> {
            openEditScreen();
            frame.dispose();
        });

        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(newGame);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(loadGame);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(settings);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,40)));

        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(welcome);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(creatorsNote);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void openEditScreen() {
        JFrame editScreen = new JFrame("Einstellungen");
        editScreen.setLayout(new BorderLayout());
        editScreen.setResizable(false);
        editScreen.setLocationRelativeTo(null);
        editScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //First Panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(Box.createGlue());
        JLabel title = new JLabel("Einstellungen");
        topPanel.add(title);
        topPanel.add(Box.createGlue());
        topPanel.add(Box.createRigidArea(new Dimension(0,30)));
        editScreen.add(topPanel, BorderLayout.NORTH);

        //Main Panel in the middle managing the smaller Panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        editScreen.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(Box.createRigidArea(new Dimension(20,0)));

        JPanel rowsPanel = new JPanel();
        rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(rowsPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(50,0)));

        JPanel columnsPanel = new JPanel();
        columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(columnsPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(50,0)));

        JPanel botsPanel = new JPanel();
        botsPanel.setLayout(new BoxLayout(botsPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(botsPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(20,0)));

        //Preparing for dropdown menus (JComboBox)
        String[] rowColumnOptions = new String[7];
        rowColumnOptions[0] = "default";
        for (int i = 0; i <= 5; i++) { rowColumnOptions[i + 1] = String.valueOf(i + 5); }
        String[] botOptions = new String[4];
        botOptions[0] = "Kein Bot";
        botOptions[1] = "Einfach";
        botOptions[2] = "Mittel";
        botOptions[3] = "Schwierig";

        //Row Panel Elements
        JLabel selectRow = new JLabel("Wie viele Zeilen möchtest du haben?");
        selectRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> dropDownRowOptions = new JComboBox<>(rowColumnOptions);
        dropDownRowOptions.setMaximumSize(new Dimension(100,50));
        dropDownRowOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel selectedRowOption = new JLabel(String.valueOf(Connect4GUI.getInstance().getPlayingRows()));
        selectedRowOption.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropDownRowOptions.addItemListener(e -> selectedRowOption.setText("Ausgewählt: " + dropDownRowOptions.getSelectedItem().toString()));

        //Column Panel Elements
        JLabel selectColumn = new JLabel("Wie viele Spalten möchtest du haben?");
        selectColumn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> dropDownColumnOptions = new JComboBox<>(rowColumnOptions);
        dropDownColumnOptions.setMaximumSize(new Dimension(100,50));
        dropDownColumnOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel selectedColumnOption = new JLabel(String.valueOf(Connect4GUI.getInstance().getPlayingColumns()));
        selectedColumnOption.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropDownColumnOptions.addItemListener(e -> selectedColumnOption.setText("Ausgewählt: " + dropDownColumnOptions.getSelectedItem().toString()));

        //Bots Panel Elements
        JLabel selectBot = new JLabel("Wähle den Bot Gegner aus.");
        selectBot.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> dropDownBotOptions = new JComboBox<>(botOptions);
        dropDownBotOptions.setMaximumSize(new Dimension(100,50));
        dropDownBotOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel selectedBotOption = new JLabel(convertDifficultyToString());
        selectedBotOption.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropDownBotOptions.addItemListener(e -> selectedBotOption.setText("Ausgewählt: " + dropDownBotOptions.getSelectedItem()));

        //Adding every element to the rows panel
        rowsPanel.add(selectRow);
        rowsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        rowsPanel.add(dropDownRowOptions);
        rowsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        rowsPanel.add(selectedRowOption);

        //Adding every element to the columns panel
        columnsPanel.add(selectColumn);
        columnsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        columnsPanel.add(dropDownColumnOptions);
        columnsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        columnsPanel.add(selectedColumnOption);

        //Adding every element to the bots panel
        botsPanel.add(selectBot);
        botsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        botsPanel.add(dropDownBotOptions);
        botsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        botsPanel.add(selectedBotOption);

        //Bottom panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createGlue());

        JButton confirm = new JButton("Bestätigen");
        confirm.setPreferredSize(new Dimension(150,30));
        confirm.addActionListener(e -> {
            resetPlayerScore();
            if (selectedRowOption.getText().equals("Ausgewählt: default") && selectedBotOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().createNewBoard(
                        Connect4GUI.getInstance().getDEFAULTROWS(),
                        Connect4GUI.getInstance().getDEFAULTCOLUMNS(),
                        convertStringToDifficulty(selectedBotOption.getText()));
            } else if (selectedRowOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().createNewBoard(
                        Connect4GUI.getInstance().getDEFAULTROWS(),
                        Integer.parseInt(removeLetters(selectedColumnOption.getText())),
                        convertStringToDifficulty(selectedBotOption.getText()));
            } else if (selectedBotOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().createNewBoard(
                        Integer.parseInt(removeLetters(selectedRowOption.getText())),
                        Connect4GUI.getInstance().getDEFAULTCOLUMNS(),
                        convertStringToDifficulty(selectedBotOption.getText()));
            } else {
                Connect4GUI.getInstance().createNewBoard(
                        Integer.parseInt(removeLetters(selectedRowOption.getText())),
                        Integer.parseInt(removeLetters(selectedColumnOption.getText())),
                        convertStringToDifficulty(selectedBotOption.getText()));
            }
            editScreen.dispose();
        });
        buttonPanel.add(confirm);

        buttonPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        JButton cancel = new JButton("Abbrechen");
        cancel.setPreferredSize(new Dimension(150,30));
        cancel.addActionListener(e -> {
            editScreen.dispose();
            Connect4GUI.getInstance().createGUI();
        });
        buttonPanel.add(cancel);

        buttonPanel.add(Box.createGlue());

        editScreen.add(buttonPanel, BorderLayout.SOUTH);

        editScreen.pack();
        editScreen.setVisible(true);
    }

    public static void openEndScreen(GameStatus g) {
        Player playerOne = Connect4GUI.getInstance().getPLAYERS().get(0);
        Player playerTwo = Connect4GUI.getInstance().getPLAYERS().get(1);

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        //Configure Labels
        JLabel winText = new JLabel(
                getWinningText(g)
                + " Aktueller Punktestand:"
        );
        JLabel points = new JLabel(
                playerOne.getNAME().getText() + ": " + playerOne.getScore() + " "
                + playerTwo.getNAME().getText()  + ": " + playerTwo.getScore()
        );
        JLabel continuePlaying = new JLabel(
                "Was möchtest du als nächstes tun?"
        );
        winText.setAlignmentX(Component.CENTER_ALIGNMENT);
        points.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuePlaying.setAlignmentX(Component.CENTER_ALIGNMENT);


        //Configure button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        JButton continueButton = new JButton("Weiterspielen");
        JButton restart = new JButton("Neustart");
        JButton finish = new JButton("Beenden");
        continueButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        restart.setAlignmentY(Component.CENTER_ALIGNMENT);
        finish.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(continueButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(restart);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));
        buttonPanel.add(finish);
        buttonPanel.add(Box.createRigidArea(new Dimension(15,0)));

        //adding the actionlisteners to Buttons
        continueButton.addActionListener(e -> {
            Connect4GUI.getInstance().createNewBoard(
                    Connect4GUI.getInstance().getPlayingRows(),
                    Connect4GUI.getInstance().getPlayingColumns(),
                    Connect4GUI.getInstance().getViergewinnt().getDifficulty()
            );
            frame.dispose();
        });
        restart.addActionListener(e -> {
            resetPlayerScore();
            Connect4GUI.getInstance().createNewBoard(
                    Connect4GUI.getInstance().getPlayingRows(),
                    Connect4GUI.getInstance().getPlayingColumns(),
                    Connect4GUI.getInstance().getViergewinnt().getDifficulty()
            );

            frame.dispose();
        });
        finish.addActionListener(e -> {
            //:Todo add functionality that deletes the saveGame if the X of the Window gets pressed instead of finish button
            new File ("./savegame.bin").delete();
            openStartScreen();
            frame.dispose();
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(winText);
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        mainPanel.add(points);
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        mainPanel.add(continuePlaying);
        mainPanel.add(Box.createRigidArea(new Dimension(0,30)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));

        frame.add(mainPanel, BorderLayout.CENTER);
        Connect4GUI.getInstance().getFrame().dispose();
        frame.pack();
        frame.setVisible(true);
    }

    private static String getWinningText(GameStatus g) {
        switch (g) {
            case playerOneWon -> {
                return Connect4GUI.getInstance().getPLAYERS().get(0).getNAME().getText()
                        + " hat gewonnen!";
            }
            case playerTwoWon -> {
                return Connect4GUI.getInstance().getPLAYERS().get(1).getNAME().getText()
                        + " hat gewonnen!";
            }
            default -> {
                return "Ein Unentschieden!";
            }
        }
    }

    private static String removeLetters(String s) {
        return s.replaceAll("\\D","");
    }

    private static void resetPlayerScore() {
        Connect4GUI.getInstance().getPLAYERS().get(0).setSCORE(0);
        Connect4GUI.getInstance().getPLAYERS().get(1).setSCORE(0);
    }

    private static String convertDifficultyToString() {
        switch (Connect4GUI.getInstance().getViergewinnt().getDifficulty()) {
            case easy -> {return "Einfach";}
            case medium -> {return "Mittel";}
            case hard -> {return "Schwierig";}
            default -> {return "Kein Bot";}
        }
    }

    private static Difficulty convertStringToDifficulty(String s) {
        switch (s) {
            case "Ausgewählt: Einfach" -> {return Difficulty.easy;}
            case "Ausgewählt: Mittel" -> {return Difficulty.medium;}
            case "Ausgewählt: Schwierig" -> {return Difficulty.hard;}
            default -> {return Difficulty.noBot;}
        }
    }
}
