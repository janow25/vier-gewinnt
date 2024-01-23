package backend;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

import backend.bot.Bot;
import backend.bot.HardBot;
import backend.bot.MediumBot;
import backend.bot.RandomBot;
import lombok.Getter;
import lombok.Setter;

public class VierGewinnt implements Serializable {
    //default id
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private Column[] columns;

    @Getter
    static Token botToken = Token.playerTwo;

    @Getter
    private Bot bot;

    @Getter
    private Difficulty difficulty;

    @Getter @Setter
    private int rounds = 0;

    @Getter
    private GameStatus gameStatus = GameStatus.onGoing;

    @Getter
    private static String saveGamePath = "./savegame.bin";

    public VierGewinnt() {
        columns = new Column[5];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new Column();
        }

        difficulty = Difficulty.noBot;

        bot = null;
    }

    public VierGewinnt(int columns) {
        this(columns, columns);
    }

    public VierGewinnt(int columns, int rows) {
        this.columns = new Column[columns];

        for (int i = 0; i < this.columns.length; i++) {
            this.columns[i] = new Column(rows);
        }

        difficulty = Difficulty.noBot;

        bot = null;
    }

    public VierGewinnt(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    public VierGewinnt(int columns, Difficulty difficulty) {
        this(columns);
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    public VierGewinnt(int columns, int rows, Difficulty difficulty) {
        this(columns, rows);
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    /// This Method can be used to play the game in Console
    public void play() {
        while (checkForWin() == GameStatus.onGoing) {
            Token botToken = Token.playerTwo;

            System.out.println(this);

            Token token = getCurrentToken();

            if (difficulty == Difficulty.noBot) {
                addToken(getPlayerInput(token), token);
            } else {
                // Check if it is the players or bots turn
                if (token != botToken) {
                    addToken(getPlayerInput(token), token);
                } else {
                    bot.makeMove(this);
                }
            }
        }

        // Print the game board one last time and print the winner
        System.out.println(this);

        if (checkForWin() == GameStatus.draw) {
            System.out.println("Draw!");
        }
        else {
            System.out.println(checkForWin().toWinnerName() + " wins!");
        }
    }

    /// This Method can be used to get the current Token
    public Token getCurrentToken() {
        return rounds % 2 == 0 ? Token.playerOne : Token.playerTwo;
    }

    /// This Method can be used to check if it is the bots turn
    /// This Method returns only true if the bot is enabled
    public Boolean isBotTurn() {
        return getCurrentToken() == botToken && difficulty != Difficulty.noBot;
    }

    /// This Method can be used to get a valid column from the player
    private int getPlayerInput(Token token) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Player " + token + " enter column: ");
        int column = sc.nextInt();

        while (column < 1 || column > getNumberOfColumns() || columnIsFull(column)) {
            System.out.println("Invalid column. [1-" + getNumberOfColumns() + "]");
            column = sc.nextInt();
        }

        return column;
    }

    /// This Method adds the current Token to column inserted
    public void addToken(int column) {
        addToken(column, getCurrentToken());
        save();
    }

    /// This Method adds a new Token to the Column. The column is not a zero based Index.
    /// So if you want to add a Token to the first column you have to pass 1 as the column parameter.
    /// The Method also adjusts the rounds and checks if the game is over
    public void addToken(int column, Token token) {
        rounds++;
        getColumns(column-1).addToken(token);

        gameStatus = checkForWin();
    }

    /// This Method checks if the column is full
    public boolean columnIsFull(int column) {
        return getColumns(column-1).isFull();
    }

    /// This Method checks if the game is over and returns the winner/draw
    public GameStatus checkForWin() {
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < getColumns(i).getRows().length; j++) {
                if (getColumns(i).getRows()[j] != Token.empty) {
                    if (j < getColumns(i).getRows().length - 3) {
                        if (getColumns(i).getRows()[j] == getColumns(i).getRows()[j + 1] && getColumns(i).getRows()[j] == getColumns(i).getRows()[j + 2] && getColumns(i).getRows()[j] == getColumns(i).getRows()[j + 3]) {
                            return getColumns(i).getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3) {
                        if (getColumns(i).getRows()[j] == columns[i + 1].getRows()[j] && getColumns(i).getRows()[j] == columns[i + 2].getRows()[j] && getColumns(i).getRows()[j] == columns[i + 3].getRows()[j]) {
                            return getColumns(i).getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j < getColumns(i).getRows().length - 3) {
                        if (getColumns(i).getRows()[j] == columns[i + 1].getRows()[j + 1] && getColumns(i).getRows()[j] == columns[i + 2].getRows()[j + 2] && getColumns(i).getRows()[j] == columns[i + 3].getRows()[j + 3]) {
                            return getColumns(i).getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j > 2) {
                        if (getColumns(i).getRows()[j] == columns[i + 1].getRows()[j - 1] && getColumns(i).getRows()[j] == columns[i + 2].getRows()[j - 2] && getColumns(i).getRows()[j] == columns[i + 3].getRows()[j - 3]) {
                            return getColumns(i).getRows()[j].toGameStatus();
                        }
                    }
                }
            }
        }

        // Check for draw
        if (rounds == getNumberOfColumns() * getNumberOfRows()) {
            return GameStatus.draw;
        }

        return GameStatus.onGoing;
    }

    /// This Method returns the number of rows
    public int getNumberOfRows() {
        return getColumns(0).getRows().length;
    }

    /// This Method returns the number of columns
    public int getNumberOfColumns() {
        return getColumns().length;
    }

    /// This Method returns the current game board as a String
    public String toString() {
        String playerOne = "O";
        String playerTwo = "X";
        String empty = " ";

        StringBuilder str = new StringBuilder();

        str.append(getBorder());
        str.append("\n");

        int rows = columns[0].getRows().length;

        for (int i = 0; i < rows; i++) {
            str.append("|");
            for (Column column : columns) {
                switch (column.getRows()[rows - i - 1]) {
                    case empty -> str.append(empty);
                    case playerOne -> str.append(playerOne);
                    case playerTwo -> str.append(playerTwo);
                }

                str.append("|");
            }
            str.append("\n");
        }

        str.append(getBorder());
        str.append("\n");


        return str.toString();
    }

    /// This Method returns the border of the game board
    /// for example: ___________
    public String getBorder() {
        return "_".repeat(columns.length*2+1);
    }

    /// This Method returns a copy of the current game board
    public VierGewinnt copy() {
        VierGewinnt copy = new VierGewinnt(getNumberOfColumns(), getNumberOfRows(), difficulty);
        copy.rounds = rounds;
        for (int i = 0; i < columns.length; i++) {
            copy.columns[i] = getColumns(i).copy();
        }
        return copy;
    }

    /// This Method saves the current game board to the saveGamePath
    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(saveGamePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            System.out.println("Error while saving game");
            ex.printStackTrace();
        }
    }

    /// This Method loads the game board from the saveGamePath
    public static VierGewinnt load() {
        if (!new File(saveGamePath).exists()) {
            return null;
        }

        try {
            FileInputStream fi = new FileInputStream(new File(saveGamePath));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            return (VierGewinnt) oi.readObject();

        } catch (Exception ex) {
            System.out.println("Error while loading game");
            ex.printStackTrace();
        }

        return null;
    }

    /// This Method returns the column at the given index
    public Column getColumns(int index) {
        return columns[index];
    }

    /// This Method returns the color of the token at the given position
    public Color getColor(int column, int row) {
        return columns[column].getRows()[row].toColor();
    }
}
