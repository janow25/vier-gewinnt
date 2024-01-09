public class Column {
    private Token[] rows;

    Column() {
        this.rows = new Token[6];

        java.util.Arrays.fill(this.rows, Token.empty);
    }

    Column(int rows) {
        this.rows = new Token[rows];

        java.util.Arrays.fill(this.rows, Token.empty);
    }

    /// This Method adds a new Token to the current Column.
    /// The Method uses a While loop to search for the First row which is Empty
    public void addToken(Token token) {
        int empty = 0;

        while(!rows[empty].equals(Token.empty) && empty < rows.length-1) {
            empty++;
        }

        rows[empty] = token;
    }

    /// This Method checks if the current Column is full
    public boolean isFull() {
        for (Token token : rows) {
            if (token.equals(Token.empty)) {
                return false;
            }
        }
        return true;
    }

    public Column copy() {
        Column copy = new Column(rows.length);
        for (int i = 0; i < rows.length; i++) {
            copy.rows[i] = rows[i];
        }
        return copy;
    }

    public Token[] getRows() {
        return rows;
    }
}
