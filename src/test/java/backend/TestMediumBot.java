import backend.Column;
import backend.Difficulty;
import backend.Token;
import backend.VierGewinnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestMediumBot {

    @Test
    public void TestBotMadeMove() {
        VierGewinnt vg = new VierGewinnt(Difficulty.medium);

        int column = vg.getBot().makeMove(vg);
        // Check if bot has made a move
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[column-1].getRows()[getLastFilledRow(vg.getColumns()[column-1])]);

        column = vg.getBot().makeMove(vg);
        // Check if bot has made another move
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[column-1].getRows()[getLastFilledRow(vg.getColumns()[column-1])]);

        column = vg.getBot().makeMove(vg);
        // Check if bot has made another move
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[column-1].getRows()[getLastFilledRow(vg.getColumns()[column-1])]);
    }

    @Test
    public void TestStopPlayerWin() {
        VierGewinnt vg = new VierGewinnt(Difficulty.medium);

        //Set up a situation where the player can win
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());

        //Check if the bot stops the player from winning
        vg.getBot().makeMove(vg);
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[0].getRows()[3]);
    }

    @Test
    public void TestWinFirst() {
        VierGewinnt vg = new VierGewinnt(Difficulty.medium);

        //Set up a situation where the player can win
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());

        //Set up a situation where the bot can win
        vg.addToken(2, VierGewinnt.getBotToken());
        vg.addToken(2, VierGewinnt.getBotToken());
        vg.addToken(2, VierGewinnt.getBotToken());

        //Check if the bot wins instead of stopping the player from winning
        vg.getBot().makeMove(vg);
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[1].getRows()[3]);

        //Check if the bot wins
        assertEquals(VierGewinnt.getBotToken().toGameStatus(), vg.checkForWin());

    }

    public int getLastFilledRow(Column column) {
        int lastFilledRow = 0;
        for (int i = 0; i < column.getRows().length; i++) {
            if (!column.getRows()[i].equals(Token.empty)) {
                lastFilledRow = i;
            }
        }
        return lastFilledRow;
    }
}