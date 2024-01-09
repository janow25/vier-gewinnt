import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VierGewinntTest {

    @Test
    public void TestIfAllFieldsAreEmpty() {
        VierGewinnt vg = new VierGewinnt();

        assertEquals(vg.getRows(), 5);
        assertEquals(vg.getColumnsArrays(), 5);

        // Check if all fields are empty
        for (int i = 0; i < vg.columnsArrays.length; i++) {
            for (int j = 0; j < vg.columnsArrays[i].getRows().length; j++) {
                assertEquals(Token.empty, vg.columnsArrays[i].getRows()[j]);
            }
        }
    }

    @Test
    public void TestIfAllFieldsAreEmpty10x8() {
        VierGewinnt vg = new VierGewinnt(10, 8);

        assertEquals(vg.getRows(), 8);
        assertEquals(vg.getColumnsArrays(), 10);

        // Check if all fields are empty
        for (int i = 0; i < vg.getColumnsArrays(); i++) {
            for (int j = 0; j < vg.getRows(); j++) {
                assertEquals(Token.empty, vg.columnsArrays[i].getRows()[j]);
            }
        }
    }

    @Test
    public void testCopy() {
        VierGewinnt vg = new VierGewinnt();
        vg.addToken(1, Token.playerOne);
        vg.addToken(1, Token.playerTwo);

        VierGewinnt copy = vg.copy();
        copy.addToken(1, Token.playerOne);

        assertNotEquals(vg.toString(), copy.toString());
        assertEquals(vg.columnsArrays[0].getRows()[0], copy.columnsArrays[0].getRows()[0]);
        assertEquals(vg.columnsArrays[0].getRows()[1], copy.columnsArrays[0].getRows()[1]);

        assertNotEquals(vg.columnsArrays[0].getRows()[2], copy.columnsArrays[0].getRows()[2]);
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
        assertEquals(Token.playerOne, vg.columnsArrays[0].getRows()[0]);
        assertEquals(Token.playerTwo, vg.columnsArrays[0].getRows()[1]);
    }

    @Test
    public void checkForWinDraw3x3() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(3);

        // Check Draw
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.draw, vg.checkForWin());
    }

    @Test
    public void checkForWinColumn() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt();

        // Check Win in Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinRow() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt();

        // Check Win in Row
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinDiagonal() {
        // Check Win in Diagonal
        VierGewinnt vg = new VierGewinnt();

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());

        // Check diagonal win in other direction and for playerTwo
        vg = new VierGewinnt();

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.playerTwoWon, vg.checkForWin());
    }

    @Test
    public void checkForWinColumn10x10() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10);

        // Check Win in Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinRow10x10() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10);

        // Check Win in Row
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinDiagonal10x10() {
        // Check Win in Diagonal
        VierGewinnt vg = new VierGewinnt(10);

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());

        // Check diagonal win in other direction and for playerTwo
        vg = new VierGewinnt(10);

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.playerTwoWon, vg.checkForWin());
    }

    @Test
    public void checkForWinColumn10x5() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10, 5);

        // Check Win in Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinRow10x5() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10, 5);

        // Check Win in Row
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());
    }

    @Test
    public void checkForWinDiagonal10x5() {
        // Check Win in Diagonal
        VierGewinnt vg = new VierGewinnt(10, 5);

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(4, Token.playerOne);
        assertEquals(GameStatus.playerOneWon, vg.checkForWin());

        // Check diagonal win in other direction and for playerTwo
        vg = new VierGewinnt(10, 5);

        // First Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.playerTwoWon, vg.checkForWin());
    }

    @Test
    public void testToStringEmptyField6x3() {
        VierGewinnt vg = new VierGewinnt(6, 3);

        String expected = """
                _____________
                | | | | | | |
                | | | | | | |
                | | | | | | |
                _____________
                """;

        assertEquals(expected, vg.toString());
    }

    @Test
    public void testToStringEmptyField10x30() {
        VierGewinnt vg = new VierGewinnt(10, 30);

        String expected = """
                _____________________
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                | | | | | | | | | | |
                _____________________
                """;

        assertEquals(expected, vg.toString());
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

        vg.addToken(3, Token.playerTwo);
        vg.addToken(5, Token.playerOne);
        vg.addToken(5, Token.playerTwo);
        vg.addToken(3, Token.playerOne);

        assertEquals(expected, vg.toString());
    }
}