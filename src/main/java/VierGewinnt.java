import GUI.Connect4GUI;

import java.util.Scanner;

public class VierGewinnt {
    Column[] columnsArrays;
    private int rows;
    private int columns;

    Bot bot;
    Difficulty difficulty = Difficulty.noBot;
    int rounds = 0;

    VierGewinnt() {
        this.rows = 6;
        this.columns = 7;
        this.columnsArrays = new Column[columns];

        for (int i = 0; i < columnsArrays.length; i++) {
            columnsArrays[i] = new Column(rows);
        }
    }

    VierGewinnt(int columns) {
        this(columns, columns);
    }

    VierGewinnt(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.columnsArrays = new Column[columns];

        for (int i = 0; i < this.columnsArrays.length; i++) {
            this.columnsArrays[i] = new Column(rows);
        }
    }

    VierGewinnt(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;
    }

    VierGewinnt(int columns, Difficulty difficulty) {
        this(columns);
        this.difficulty = difficulty;
    }

    VierGewinnt(int columns, int rows, Difficulty difficulty) {
        this(columns, rows);
        this.difficulty = difficulty;
    }

    public void play() {
        while (checkForWin() == GameStatus.onGoing) {
            Token botToken = Token.playerTwo;

            switch (difficulty) {
                case easy -> bot = new RandomBot(botToken);
                case medium -> bot = new MediumBot(botToken);
                case hard -> bot = new HardBot(botToken);
                default -> bot = null;
            }

            // Use toString() to print the game board
            System.out.println(this);

            Token token = getCurrentToken();

            if (difficulty == Difficulty.noBot) {
                addToken(getPlayerInput(token), token);
            } else {
                if (token != botToken) {
                    addToken(getPlayerInput(token), token);
                } else {
                    bot.makeMove(this);
                }
            }
        }
        System.out.println(this);
        System.out.println(checkForWin() + " wins!");
    }

    public Token getCurrentToken() {
        return rounds % 2 == 0 ? Token.playerOne : Token.playerTwo;
    }

    private int getPlayerInput(Token token) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Player " + token + " enter column: ");
        int column = sc.nextInt();

        while (column < 1 || column > getColumnsArrays() || columnIsFull(column)) {
            System.out.println("Invalid column. [1-" + getColumnsArrays() + "]");
            column = sc.nextInt();
        }

        return column;
    }

    /// This Method adds a new Token to the current Column. The column is not zero based. So if you want to add a Token to the first column you have to pass 1 as the column parameter.
    public void addToken(int column, Token token) {
        rounds++;
        columnsArrays[column-1].addToken(token);
    }

    public boolean columnIsFull(int column) {
        return columnsArrays[column-1].isFull();
    }

    public GameStatus checkForWin() {
        GameStatus winner = GameStatus.onGoing;

        if (rounds == getColumns() * getRows()) {
            return GameStatus.draw;
        }

        for (int i = 0; i < columnsArrays.length; i++) {
            for (int j = 0; j < columnsArrays[i].getRows().length; j++) {
                if (columnsArrays[i].getRows()[j] != Token.empty) {
                    if (j < columnsArrays[i].getRows().length - 3) {
                        if (columnsArrays[i].getRows()[j] == columnsArrays[i].getRows()[j + 1] && columnsArrays[i].getRows()[j] == columnsArrays[i].getRows()[j + 2] && columnsArrays[i].getRows()[j] == columnsArrays[i].getRows()[j + 3]) {
                            winner = columnsArrays[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columnsArrays.length - 3) {
                        if (columnsArrays[i].getRows()[j] == columnsArrays[i + 1].getRows()[j] && columnsArrays[i].getRows()[j] == columnsArrays[i + 2].getRows()[j] && columnsArrays[i].getRows()[j] == columnsArrays[i + 3].getRows()[j]) {
                            winner = columnsArrays[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columnsArrays.length - 3 && j < columnsArrays[i].getRows().length - 3) {
                        if (columnsArrays[i].getRows()[j] == columnsArrays[i + 1].getRows()[j + 1] && columnsArrays[i].getRows()[j] == columnsArrays[i + 2].getRows()[j + 2] && columnsArrays[i].getRows()[j] == columnsArrays[i + 3].getRows()[j + 3]) {
                            winner = columnsArrays[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columnsArrays.length - 3 && j > 2) {
                        if (columnsArrays[i].getRows()[j] == columnsArrays[i + 1].getRows()[j - 1] && columnsArrays[i].getRows()[j] == columnsArrays[i + 2].getRows()[j - 2] && columnsArrays[i].getRows()[j] == columnsArrays[i + 3].getRows()[j - 3]) {
                            winner = columnsArrays[i].getRows()[j].toGameStatus();
                        }
                    }
                }
            }
        }
        return winner;
    }

    public int getColumnsArrays() {
        return columnsArrays.length;
    }

    public String toString() {
        String playerOne = "O";
        String playerTwo = "X";
        String empty = " ";

        StringBuilder str = new StringBuilder();

        str.append(getBorder());
        str.append("\n");

        int rows = columnsArrays[0].getRows().length;

        for (int i = 0; i < rows; i++) {
            str.append("|");
            for (Column column : columnsArrays) {
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

    public String getBorder() {
        return "_".repeat(columnsArrays.length*2+1);
    }

    public VierGewinnt copy() {
        VierGewinnt copy = new VierGewinnt(getColumnsArrays(), getRows(), difficulty);
        copy.rounds = rounds;
        for (int i = 0; i < columnsArrays.length; i++) {
            copy.columnsArrays[i] = columnsArrays[i].copy();
        }
        return copy;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
