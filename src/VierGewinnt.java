public class VierGewinnt {
    Column[] columns;

    VierGewinnt() {
        columns = new Column[]{new Column(), new Column(), new Column(), new Column(), new Column()};
    }
    public void addToken(int column, Token token) {
        columns[column].addToken(token);
    }

    public String toString() {
        String playerOne = "O";
        String playerTwo = "X";
        String empty = " ";

        StringBuilder str = new StringBuilder();

        str.append("___________\n");

        for (int i = 0; i < columns[0].rows.length; i++) {
            str.append("|");
            for (int j = 0; j < columns.length; j++) {
                switch (columns[j].rows[columns.length - i - 1]) {
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
