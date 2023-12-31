package main.java;

public class Column {
    public Token[] rows = new Token[5];

    Column() {
        rows[0] = Token.empty;
        rows[1] = Token.empty;
        rows[2] = Token.empty;
        rows[3] = Token.empty;
        rows[4] = Token.empty;
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
}
