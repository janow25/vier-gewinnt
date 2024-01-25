package backend.bot;

import backend.Column;
import backend.Difficulty;
import backend.Token;
import backend.VierGewinnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestRandomBot {

    @Test
    public void TestBotMadeMove() {
        VierGewinnt vg = new VierGewinnt(Difficulty.easy);

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


    /// Help method that return the top token in a column
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