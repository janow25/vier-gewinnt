package backend.bot;

import backend.Token;
import backend.VierGewinnt;

public class HardBot extends BotBase {

    public HardBot() {
        super();
    }

    public HardBot(Token token) {
        super(token);
    }

    public int GetColumnWithFastestWin(VierGewinnt vg, int depth) {
        int bestScore = 0;
        int column = 0;

        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                int score = CheckForFastestWin(tmpVg, i, depth);
                if (score > bestScore) {
                    bestScore = score;
                    column = i;
                }
            }
        }

        return column;
    }

    public int CheckForFastestWin(VierGewinnt vg, int column, int depth) {
        VierGewinnt tmpVg = vg.copy();
        tmpVg.addToken(column, super.getMyToken());

        if (tmpVg.checkForWin() == super.getMyToken().toGameStatus()) {
            System.out.println(tmpVg);
            System.out.println("Winning move found at depth " + depth + " in column " + column + "!");
            return depth;
        }

        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                if (depth > 0) {
                    return CheckForFastestWin(tmpVg, i, depth - 1);
                }
            }
        }

        return 0;
    }
    public int makeMove(VierGewinnt vg) {
        // Check if there is a column where the bot can win
        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, super.getMyToken());
                if (tmpVg.checkForWin() == super.getMyToken().toGameStatus()) {
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
                    vg.addToken(i, super.getMyToken());
                    return i;
                }
            }
        }

        // If there is no column where the bot or the player can win, choose the column with the fastest win
        int column = GetColumnWithFastestWin(vg, 5);

        vg.addToken(column, super.getMyToken());

        return column;
    }
}
