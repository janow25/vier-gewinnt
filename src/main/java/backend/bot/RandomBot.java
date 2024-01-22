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
        do {
            column = (int) (Math.random() * vg.getNumberOfColumns()) + 1;
        } while (vg.columnIsFull(column));

        vg.addToken(column, super.getMyToken());
        return column;
    }
}
