import backend.GameStatus;
import backend.Token;
import backend.VierGewinnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VierGewinntTest {

    @Test
    public void TestIfAllFieldsAreEmpty() {
        VierGewinnt vg = new VierGewinnt();

        assertEquals(vg.getNumberOfRows(), 5);
        assertEquals(vg.getNumberOfColumns(), 5);

        // Check if all fields are empty
        for (int i = 0; i < vg.getNumberOfColumns(); i++) {
            for (int j = 0; j < vg.getNumberOfRows(); j++) {
                assertEquals(Token.empty, vg.getColumns()[i].getRows()[j]);
            }
        }
    }

    @Test
    public void TestIfAllFieldsAreEmpty10x8() {
        VierGewinnt vg = new VierGewinnt(10, 8);

        assertEquals(vg.getNumberOfRows(), 8);
        assertEquals(vg.getNumberOfColumns(), 10);

        // Check if all fields are empty
        for (int i = 0; i < vg.getNumberOfColumns(); i++) {
            for (int j = 0; j < vg.getNumberOfRows(); j++) {
                assertEquals(Token.empty, vg.getColumns()[i].getRows()[j]);
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
        assertEquals(vg.getColumns()[0].getRows()[0], copy.getColumns()[0].getRows()[0]);
        assertEquals(vg.getColumns()[0].getRows()[1], copy.getColumns()[0].getRows()[1]);

        assertNotEquals(vg.getColumns()[0].getRows()[2], copy.getColumns()[0].getRows()[2]);
    }

    @Test
    public void addToken() {
        // Test Add backend.Token Method
        VierGewinnt vg = new VierGewinnt();

        // Add backend.Token to backend.Column 1
        vg.addToken(1, Token.playerOne);

        // Add backend.Token to backend.Column 1
        vg.addToken(1, Token.playerTwo);

        //Check if backend.Token is added
        assertEquals(Token.playerOne, vg.getColumns()[0].getRows()[0]);
        assertEquals(Token.playerTwo, vg.getColumns()[0].getRows()[1]);
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

        // Check Win in backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth backend.Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.playerTwoWon, vg.checkForWin());
    }

    @Test
    public void checkForWinColumn10x10() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10);

        // Check Win in backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth backend.Column
        vg.addToken(4, Token.playerTwo);
        assertEquals(GameStatus.playerTwoWon, vg.checkForWin());
    }

    @Test
    public void checkForWinColumn10x5() {
        // Test Check for Win Method
        VierGewinnt vg = new VierGewinnt(10, 5);

        // Check Win in backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Fourth backend.Column
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

        // First backend.Column
        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(1, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Second backend.Column
        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(2, Token.playerTwo);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        // Third backend.Column
        vg.addToken(3, Token.playerOne);
        assertEquals(GameStatus.onGoing, vg.checkForWin());

        vg.addToken(3, Token.playerTwo);

        // Fourth backend.Column
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