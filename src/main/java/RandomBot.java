public class RandomBot implements Bot {

    private Token myToken;
    RandomBot() {
        myToken = Token.playerTwo;
    }

    RandomBot(Token token) {
        myToken = token;
    }
    public int makeMove(VierGewinnt vg) {
        int column;
        do {
            column = (int) (Math.random() * vg.getColumns()) + 1;
        } while (vg.columnIsFull(column));

        vg.addToken(column, myToken);
        return column;
    }
}
