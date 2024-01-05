public class MediumBot implements Bot {

    private Token myToken;
    MediumBot() {
        myToken = Token.playerTwo;
    }

    MediumBot(Token token) {
        myToken = token;
    }
    public int makeMove(VierGewinnt vg) {
        // Check if there is a column where the bot can win
        for (int i = 1; i <= vg.getColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, myToken);
                if (tmpVg.checkForWin() == myToken.toGameStatus()) {
                    vg.addToken(i, myToken);
                    return i;
                }
            }
        }

        // Check if there is a column where the player can win
        for (int i = 1; i <= vg.getColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, myToken.other());
                if (tmpVg.checkForWin() == myToken.other().toGameStatus()) {
                    vg.addToken(i, myToken);
                    return i;
                }
            }
        }

        // If there is no column where the bot or the player can win, choose a random column
        int column;
        do {
            column = (int) (Math.random() * vg.getColumns()) + 1;
        } while (vg.columnIsFull(column));

        vg.addToken(column, myToken);

        return column;
    }
}
