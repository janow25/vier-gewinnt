package GUI.Factories;

import GUI.Connect4GUI;
import backend.Difficulty;

import javax.swing.*;
import java.awt.*;

public class MenuCreationFactory {
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

        Connect4GUI.getInstance().getFrame().dispose();
        editScreen.pack();
        editScreen.setVisible(true);
    }

    private static String removeLetters(String s) {
        return s.replaceAll("\\D","");
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
