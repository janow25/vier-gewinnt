package backend.bot;

import backend.Token;
import backend.VierGewinnt;

public class MediumBot extends BotBase {

    public MediumBot() {
        super();
    }

    public MediumBot(Token token) {
        super(token);
    }
    public int makeMove(VierGewinnt vg) {

        // Check if there is a column where the bot can win
        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, super.getMyToken());
                if (tmpVg.checkForWin() == super.getMyToken().toGameStatus()) {
                    // Add a token to the column
                    vg.addToken(i, super.getMyToken());
                    return i;
                }
            }
        }

        // Check if there is a column where the player can win
        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, super.getMyToken().other());
                if (tmpVg.checkForWin() == super.getMyToken().other().toGameStatus()) {
                    // Add a token to the column
                    vg.addToken(i, super.getMyToken());
                    return i;
                }
            }
        }

        // If there is no column where the bot or the player can win, choose a random column
        int column;
        do {
            column = (int) (Math.random() * vg.getNumberOfColumns()) + 1;
        } while (vg.columnIsFull(column));

        // Add a token to the column
        vg.addToken(column, super.getMyToken());

        return column;
    }
}
