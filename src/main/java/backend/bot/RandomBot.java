package backend.bot;

import backend.Token;
import backend.VierGewinnt;

public class RandomBot extends BotBase {

    public RandomBot() {
        super();
    }

    public RandomBot(Token token) {
        super(token);
    }
    public int makeMove(VierGewinnt vg) {
        int column;

        // Find a random column that is not full
        do {
            column = (int) (Math.random() * vg.getNumberOfColumns()) + 1;
        } while (vg.columnIsFull(column));

        // Add a token to the column
        vg.addToken(column, super.getMyToken());
        return column;
    }
}
