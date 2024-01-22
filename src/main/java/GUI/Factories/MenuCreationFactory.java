package GUI.Factories;

import GUI.Connect4GUI;
import GUI.MyPanel;
import backend.Difficulty;

import javax.swing.*;
import java.awt.*;

public class MenuCreationFactory {
    public static void openPlayingDimensionEditScreen() {
        Connect4GUI.getInstance().getFrame().dispose();
        JFrame editScreen = new JFrame("Spielfeld bearbeiten");
        editScreen.setLayout(new BorderLayout());
        editScreen.setResizable(false);
        editScreen.setLocationRelativeTo(null);
        editScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //First Panel at the top
        JPanel topPanel = new MyPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(Box.createGlue());
        JLabel title = new JLabel("Spielfeld bearbeiten");
        topPanel.add(title);
        topPanel.add(Box.createGlue());
        topPanel.add(Box.createRigidArea(new Dimension(0,30)));
        editScreen.add(topPanel, BorderLayout.NORTH);

        //backend.Main Panel in the middle
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

        mainPanel.add(Box.createRigidArea(new Dimension(20,0)));

        JLabel selectRow = new JLabel("Wie viele Zeilen möchtest du haben?");
        selectRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        rowsPanel.add(selectRow);
        rowsPanel.add(Box.createRigidArea(new Dimension(0,5)));

        JLabel selectColumn = new JLabel("Wie viele Spalten möchtest du haben?");
        selectColumn.setAlignmentX(Component.CENTER_ALIGNMENT);
        columnsPanel.add(selectColumn);
        columnsPanel.add(Box.createRigidArea(new Dimension(0,5)));

        String[] options = new String[7];
        options[0] = "default";
        for (int i = 0; i <= 5; i++) {
            options[i + 1] = String.valueOf(i + 5);
        }
        JComboBox<String> dropDownRowOptions = new JComboBox<>(options);
        dropDownRowOptions.setMaximumSize(new Dimension(100,50));
        dropDownRowOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> dropDownColumnOptions = new JComboBox<>(options);
        dropDownColumnOptions.setMaximumSize(new Dimension(100,50));
        dropDownColumnOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel selectedRowOption = new JLabel(String.valueOf(Connect4GUI.getInstance().getPlayingRows()));
        selectedRowOption.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropDownRowOptions.addItemListener(e -> selectedRowOption.setText("Ausgewählt: " + dropDownRowOptions.getSelectedItem().toString()));
        rowsPanel.add(dropDownRowOptions);
        rowsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        rowsPanel.add(selectedRowOption);

        JLabel selectedColumnOption = new JLabel(String.valueOf(Connect4GUI.getInstance().getPlayingColumns()));
        selectedColumnOption.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropDownColumnOptions.addItemListener(e -> selectedColumnOption.setText("Ausgewählt: " + dropDownColumnOptions.getSelectedItem().toString()));
        columnsPanel.add(dropDownColumnOptions);
        columnsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        columnsPanel.add(selectedColumnOption);


        //Bottom panel for buttons
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.LINE_AXIS));
        confirmPanel.add(Box.createGlue());

        JButton confirm = new JButton("Bestätigen");
        confirm.setPreferredSize(new Dimension(150,30));
        confirm.addActionListener(e -> {
            if (selectedRowOption.getText().equals("Ausgewählt: default") && selectedColumnOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().setPlayingRows(Connect4GUI.getInstance().getDEFAULTROWS());
                Connect4GUI.getInstance().setPlayingColumns(Connect4GUI.getInstance().getDEFAULTCOLUMNS());


            } else if (selectedRowOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().setPlayingRows(Connect4GUI.getInstance().getDEFAULTROWS());
                Connect4GUI.getInstance().setPlayingColumns(Integer.parseInt(removeLetters(selectedColumnOption.getText())));
            } else if (selectedColumnOption.getText().equals("Ausgewählt: default")) {
                Connect4GUI.getInstance().setPlayingRows(Integer.parseInt(removeLetters(selectedRowOption.getText())));
                Connect4GUI.getInstance().setPlayingColumns(Connect4GUI.getInstance().getDEFAULTCOLUMNS());
            } else {
                Connect4GUI.getInstance().createNewBoard(Integer.parseInt(removeLetters(selectedRowOption.getText())), Integer.parseInt(removeLetters(selectedColumnOption.getText())), Difficulty.noBot);
            }
            editScreen.dispose();
        });
        confirmPanel.add(confirm);

        confirmPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        JButton cancel = new JButton("Abbrechen");
        cancel.setPreferredSize(new Dimension(150,30));
        cancel.addActionListener(e -> {
            Connect4GUI.getInstance().createGUI();
            editScreen.dispose();
        });
        confirmPanel.add(cancel);

        confirmPanel.add(Box.createGlue());

        editScreen.add(confirmPanel, BorderLayout.SOUTH);

        editScreen.pack();
        editScreen.setVisible(true);
    }

    private static String removeLetters(String s) {
        return s.replaceAll("\\D","");
    }
}
