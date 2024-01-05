import java.util.Scanner;

public class VierGewinnt {
    Column[] columns;

    Bot bot;
    Difficulty difficulty = Difficulty.noBot;
    int rounds = 0;

    VierGewinnt() {
        columns = new Column[5];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new Column();
        }
    }

    VierGewinnt(int columns) {
        this(columns, columns);
    }

    VierGewinnt(int columns, int rows) {
        this.columns = new Column[columns];

        for (int i = 0; i < this.columns.length; i++) {
            this.columns[i] = new Column(rows);
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

        while (column < 1 || column > getColumns() || columnIsFull(column)) {
            System.out.println("Invalid column. [1-" + getColumns() + "]");
            column = sc.nextInt();
        }

        return column;
    }

    /// This Method adds a new Token to the current Column. The column is not zero based. So if you want to add a Token to the first column you have to pass 1 as the column parameter.
    public void addToken(int column, Token token) {
        rounds++;
        columns[column-1].addToken(token);
    }

    public boolean columnIsFull(int column) {
        return columns[column-1].isFull();
    }

    public GameStatus checkForWin() {
        GameStatus winner = GameStatus.onGoing;

        if (rounds == getColumns() * getRows()) {
            return GameStatus.draw;
        }

        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[i].rows.length; j++) {
                if (columns[i].rows[j] != Token.empty) {
                    if (j < columns[i].rows.length - 3) {
                        if (columns[i].rows[j] == columns[i].rows[j + 1] && columns[i].rows[j] == columns[i].rows[j + 2] && columns[i].rows[j] == columns[i].rows[j + 3]) {
                            winner = columns[i].rows[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j] && columns[i].rows[j] == columns[i + 2].rows[j] && columns[i].rows[j] == columns[i + 3].rows[j]) {
                            winner = columns[i].rows[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j < columns[i].rows.length - 3) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j + 1] && columns[i].rows[j] == columns[i + 2].rows[j + 2] && columns[i].rows[j] == columns[i + 3].rows[j + 3]) {
                            winner = columns[i].rows[j].toGameStatus();
                        }
                    }
                    if (i < columns.length - 3 && j > 2) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j - 1] && columns[i].rows[j] == columns[i + 2].rows[j - 2] && columns[i].rows[j] == columns[i + 3].rows[j - 3]) {
                            winner = columns[i].rows[j].toGameStatus();
                        }
                    }
                }
            }
        }
        return winner;
    }

    public int getRows() {
        return columns[0].rows.length;
    }

    public int getColumns() {
        return columns.length;
    }

    public String toString() {
        String playerOne = "O";
        String playerTwo = "X";
        String empty = " ";

        StringBuilder str = new StringBuilder();

        str.append(getBorder());
        str.append("\n");

        int rows = columns[0].rows.length;

        for (int i = 0; i < rows; i++) {
            str.append("|");
            for (Column column : columns) {
                switch (column.rows[rows - i - 1]) {
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
        VierGewinnt copy = new VierGewinnt(getColumns(), getRows(), difficulty);
        copy.rounds = rounds;
        for (int i = 0; i < columns.length; i++) {
            copy.columns[i] = columns[i].copy();
        }
        return copy;
    }
}
