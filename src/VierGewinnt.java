public class VierGewinnt {
    Column[] columns;

    VierGewinnt() {
        columns = new Column[]{new Column(), new Column(), new Column(), new Column(), new Column()};
    }
    public void addToken(int column, Token token) {
        columns[column].addToken(token);
    }

    public Token checkForWin() {
        Token winner = Token.empty;
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[i].rows.length; j++) {
                if (columns[i].rows[j] != Token.empty) {
                    if (j < columns[i].rows.length - 3) {
                        if (columns[i].rows[j] == columns[i].rows[j + 1] && columns[i].rows[j] == columns[i].rows[j + 2] && columns[i].rows[j] == columns[i].rows[j + 3]) {
                            winner = columns[i].rows[j];
                        }
                    }
                    if (i < columns.length - 3) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j] && columns[i].rows[j] == columns[i + 2].rows[j] && columns[i].rows[j] == columns[i + 3].rows[j]) {
                            winner = columns[i].rows[j];
                        }
                    }
                    if (i < columns.length - 3 && j < columns[i].rows.length - 3) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j + 1] && columns[i].rows[j] == columns[i + 2].rows[j + 2] && columns[i].rows[j] == columns[i + 3].rows[j + 3]) {
                            winner = columns[i].rows[j];
                        }
                    }
                    if (i < columns.length - 3 && j > 2) {
                        if (columns[i].rows[j] == columns[i + 1].rows[j - 1] && columns[i].rows[j] == columns[i + 2].rows[j - 2] && columns[i].rows[j] == columns[i + 3].rows[j - 3]) {
                            winner = columns[i].rows[j];
                        }
                    }
                }
            }
        }
        return winner;
    }

    public String toString() {
        String playerOne = "O";
        String playerTwo = "X";
        String empty = " ";

        StringBuilder str = new StringBuilder();

        str.append("___________\n");

        for (int i = 0; i < columns[0].rows.length; i++) {
            str.append("|");
            for (Column column : columns) {
                switch (column.rows[columns.length - i - 1]) {
                    case empty -> str.append(empty);
                    case playerOne -> str.append(playerOne);
                    case playerTwo -> str.append(playerTwo);
                }

                str.append("|");
            }
            str.append("\n");
        }

        str.append("___________\n");


        return str.toString();
    }
}
