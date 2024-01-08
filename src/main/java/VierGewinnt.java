import java.io.*;
import java.util.Scanner;
import lombok.Getter;

public class VierGewinnt implements Serializable {
    //default id
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private Column[] columns;

    static Token botToken = Token.playerTwo;

    @Getter
    private Bot bot;

    @Getter
    private Difficulty difficulty = Difficulty.noBot;

    @Getter
    private int rounds = 0;

    VierGewinnt() {
        columns = new Column[5];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new Column();
        }

        difficulty = Difficulty.noBot;

        bot = null;
    }

    VierGewinnt(int columns) {
        this(columns, columns);

        difficulty = Difficulty.noBot;

        bot = null;
    }

    VierGewinnt(int columns, int rows) {
        this.columns = new Column[columns];

        for (int i = 0; i < this.columns.length; i++) {
            this.columns[i] = new Column(rows);
        }

        difficulty = Difficulty.noBot;

        bot = null;
    }

    VierGewinnt(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    VierGewinnt(int columns, Difficulty difficulty) {
        this(columns);
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    VierGewinnt(int columns, int rows, Difficulty difficulty) {
        this(columns, rows);
        this.difficulty = difficulty;

        switch (difficulty) {
            case easy -> bot = new RandomBot(botToken);
            case medium -> bot = new MediumBot(botToken);
            case hard -> bot = new HardBot(botToken);
            default -> bot = null;
        }
    }

    public void play() {
        while (checkForWin() == GameStatus.onGoing) {
            Token botToken = Token.playerTwo;

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

        while (column < 1 || column > getNumberOfColumns() || columnIsFull(column)) {
            System.out.println("Invalid column. [1-" + getNumberOfColumns() + "]");
            column = sc.nextInt();
        }

        return column;
    }

    /// This Method adds a new Token to the current Column. The column is not zero based. So if you want to add a Token to the first column you have to pass 1 as the column parameter.
    public void addToken(int column, Token token) {
        rounds++;
        columns[column-1].addToken(token);

        save("savegame.txt");
    }

    public boolean columnIsFull(int column) {
        return columns[column-1].isFull();
    }

    public GameStatus checkForWin() {
        GameStatus winner = GameStatus.onGoing;

        if (rounds == getNumberOfColumns() * getNumberOfRows()) {
            return GameStatus.draw;
        }

        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[i].getRows().length; j++) {
                if (columns[i].getRows()[j] != Token.empty) {
                    if (j < columns[i].getRows().length - 3) {
                        if (columns[i].getRows()[j] == columns[i].getRows()[j + 1] && columns[i].getRows()[j] == columns[i].getRows()[j + 2] && columns[i].getRows()[j] == columns[i].getRows()[j + 3]) {
                            winner = columns[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3) {
                        if (columns[i].getRows()[j] == columns[i + 1].getRows()[j] && columns[i].getRows()[j] == columns[i + 2].getRows()[j] && columns[i].getRows()[j] == columns[i + 3].getRows()[j]) {
                            winner = columns[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j < columns[i].getRows().length - 3) {
                        if (columns[i].getRows()[j] == columns[i + 1].getRows()[j + 1] && columns[i].getRows()[j] == columns[i + 2].getRows()[j + 2] && columns[i].getRows()[j] == columns[i + 3].getRows()[j + 3]) {
                            winner = columns[i].getRows()[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j > 2) {
                        if (columns[i].getRows()[j] == columns[i + 1].getRows()[j - 1] && columns[i].getRows()[j] == columns[i + 2].getRows()[j - 2] && columns[i].getRows()[j] == columns[i + 3].getRows()[j - 3]) {
                            winner = columns[i].getRows()[j].toGameStatus();
                        }
                    }
                }
            }
        }
        return winner;
    }

    public int getNumberOfRows() {
        return columns[0].getRows().length;
    }

    public int getNumberOfColumns() {
        return columns.length;
    }

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

    public String getBorder() {
        return "_".repeat(columns.length*2+1);
    }

    public VierGewinnt copy() {
        VierGewinnt copy = new VierGewinnt(getNumberOfColumns(), getNumberOfRows(), difficulty);
        copy.rounds = rounds;
        for (int i = 0; i < columns.length; i++) {
            copy.columns[i] = columns[i].copy();
        }
        return copy;
    }

    public void save(String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            System.out.println("Error while saving game");
            ex.printStackTrace();
        }
    }

    public static VierGewinnt load(String path) {
        try {
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            return (VierGewinnt) oi.readObject();

        } catch (Exception ex) {
            System.out.println("Error while loading game");
            ex.printStackTrace();
        }

        return null;
    }
}
