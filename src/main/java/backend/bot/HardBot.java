package backend.bot;

import backend.GameStatus;
import backend.Token;
import backend.VierGewinnt;

import java.util.Arrays;

public class HardBot extends BotBase {

    public HardBot() {
        super();
    }

    public HardBot(Token token) {
        super(token);
    }

    public int evaluateBoard(VierGewinnt vg) {
        int score = 0;

        GameStatus winner = vg.checkForWin();
        if (winner == super.getMyToken().toGameStatus()) {
            return 200;
        }
        else if (winner == super.getMyToken().other().toGameStatus()) {
            return -100;
        }

        // Score center column
        int centerCount = 0;
        for (int i = 0; i < vg.getNumberOfRows(); i++) {
            if (vg.getColumns(vg.getNumberOfColumns() / 2).getRows(i) == super.getMyToken()) {
                centerCount++;
            }
        }
        score += centerCount * 3;

        // Score Horizontal
        for (int i = 0; i < vg.getNumberOfRows(); i++) {
            for (int j = 0; j < vg.getNumberOfColumns() - 3; j++) {
                Token[] window = new Token[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = vg.getColumns(j + k).getRows(i);
                }
                score += evaluateWindow(window);
            }
        }

        // Score Vertical
        for (int i = 0; i < vg.getNumberOfRows() - 3; i++) {
            for (int j = 0; j < vg.getNumberOfColumns(); j++) {
                Token[] window = new Token[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = vg.getColumns(j).getRows(i + k);
                }
                score += evaluateWindow(window);
            }
        }

        // Score Diagonal
        for (int i = 0; i < vg.getNumberOfRows() - 3; i++) {
            for (int j = 0; j < vg.getNumberOfColumns() - 3; j++) {
                Token[] window = new Token[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = vg.getColumns(j + k).getRows(i + k);
                }
                score += evaluateWindow(window);
            }
        }

        for (int i = 0; i < vg.getNumberOfRows() - 3; i++) {
            for (int j = 0; j < vg.getNumberOfColumns() - 3; j++) {
                Token[] window = new Token[4];
                for (int k = 0; k < 4; k++) {
                    window[k] = vg.getColumns(j + 3 - k).getRows(i + k);
                }
                score += evaluateWindow(window);
            }
        }

        return score;
    }

    public int evaluateWindow(Token[] window) {
        int score = 0;
        int myTokens = 0;
        int enemyTokens = 0;
        int emptyTokens = 0;
        for (Token token : window) {
            if (token == super.getMyToken()) {
                myTokens++;
            } else if (token == super.getMyToken().other()) {
                enemyTokens++;
            } else {
                emptyTokens++;
            }
        }
        if (myTokens == 4) {
            score += 200;
        } else if (myTokens == 3 && emptyTokens == 1) {
            score += 5;
        } else if (myTokens == 2 && emptyTokens == 2) {
            score += 2;
        }

        if (enemyTokens == 4) {
            score -= 100;
        }
        else if (enemyTokens == 3 && emptyTokens == 1) {
            score -= 5;
        }
        else if (enemyTokens == 2 && emptyTokens == 2) {
            score -= 2;
        }

        return score;
    }

    public int minMax(VierGewinnt vg, int depth, boolean isMaximizing) {

        // Check if the game is over or if the depth is 0
        if (depth == 0 || vg.checkForWin() != GameStatus.onGoing) {
            return evaluateBoard(vg);
        }

        if (isMaximizing) {
            // Check for the best move for the bot
            int bestScore = Integer.MIN_VALUE;
            for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
                if (!vg.columnIsFull(i)) {
                    VierGewinnt tmpVg = vg.copy();
                    tmpVg.addToken(i, super.getMyToken());

                    int score = minMax(tmpVg, depth - 1, false);

                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            // Check for the best move for the player
            int bestScore = Integer.MAX_VALUE;
            for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
                if (!vg.columnIsFull(i)) {
                    VierGewinnt tmpVg = vg.copy();
                    tmpVg.addToken(i, super.getMyToken().other());

                    int score = minMax(tmpVg, depth - 1, true);

                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public int makeMove(VierGewinnt vg) {
        //System.out.println(evaluateBoard(vg));

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


        int column = 1;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 1; i <= vg.getNumberOfColumns(); i++) {
            if (!vg.columnIsFull(i)) {
                VierGewinnt tmpVg = vg.copy();
                tmpVg.addToken(i, super.getMyToken());

                int score = minMax(tmpVg, 5, false);

                if (score > bestScore) {
                    bestScore = score;
                    column = i;
                }
            }
        }
        if (vg.columnIsFull(column)) {
            System.out.println("ERROR: Column is full" + column);
        }

        System.out.println();

        vg.addToken(column, super.getMyToken());

        return column;
    }
}
