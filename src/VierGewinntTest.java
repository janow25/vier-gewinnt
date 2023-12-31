import org.junit.Test;

import static org.junit.Assert.*;

public class VierGewinntTest {

    @Test
    public void TestIfAllFieldsAreEmpty() {
        VierGewinnt vg = new VierGewinnt();

        // Check if all fields are empty
        for (int i = 0; i < vg.columns.length; i++) {
            for (int j = 0; j < vg.columns[i].rows.length; j++) {
                assertEquals(Token.empty, vg.columns[i].rows[j]);
            }
        }
    }

    @Test
    public void addToken() {
        // Test Add Token Method
        VierGewinnt vg = new VierGewinnt();

        // Add Token to Column 1
        vg.addToken(1, Token.playerOne);

        // Add Token to Column 1
        vg.addToken(1, Token.playerTwo);

        //Check if Token is added
        assertEquals(Token.playerOne, vg.columns[1].rows[0]);
        assertEquals(Token.playerTwo, vg.columns[1].rows[1]);

        assertNotEquals(Token.playerTwo, vg.columns[1].rows[0]);
        assertNotEquals(Token.playerOne, vg.columns[1].rows[1]);
    }

    @Test
    public void checkForWinColumn() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt();

        // Check Win in Column
        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(Token.playerOne, vg.checkForWin());
    }

    @Test
    public void checkForWinRow() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt();

        // Check Win in Row
        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(Token.playerOne, vg.checkForWin());
    }

    @Test
    public void checkForWinDiagonal() {
        // Check Win in Diagonal
        VierGewinnt vg = new VierGewinnt();

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(Token.playerOne, vg.checkForWin());

        // Check diagonal win in other direction and for playerTwo
        vg = new VierGewinnt();

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(Token.empty, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerOne);
        assertEquals(Token.empty, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(Token.playerTwo, vg.checkForWin());
    }

    @Test
    public void testToStringEmptyField() {
        VierGewinnt vg = new VierGewinnt();

        String expected = """
                ___________
                | | | | | |
                | | | | | |
                | | | | | |
                | | | | | |
                | | | | | |
                ___________
                """;

        assertEquals(expected, vg.toString());
    }

    @Test
    public void testToStringRandomField() {
        VierGewinnt vg = new VierGewinnt();

        String expected = """
                ___________
                | | | | | |
                | | | | | |
                | | | | | |
                | | |O| |X|
                | | |X| |O|
                ___________
                """;

        vg.addToken(2, Token.playerTwo);
        vg.addToken(4, Token.playerOne);
        vg.addToken(4, Token.playerTwo);
        vg.addToken(2, Token.playerOne);

        assertEquals(expected, vg.toString());
    }
}