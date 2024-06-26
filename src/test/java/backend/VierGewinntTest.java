package backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VierGewinntTest {
    @AfterEach
    public void tearDown() {
        // Lösche die Testdatei nach jedem Test
        File testSaveFile = new File(VierGewinnt.getSaveGamePath());
        try {
            Files.deleteIfExists(testSaveFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Überprüfe, ob die Datei gelöscht wurde
        assertFalse(Files.exists(testSaveFile.toPath()), "Die Testdatei wurde nicht erfolgreich gelöscht.");
    }

    @Test
    public void testSaveAndLoad() {
        // Führe einige Aktionen im Spiel aus (hier ein Beispiel)
        // game.doSomeActions();

        // Speichere das Spiel
        VierGewinnt vg = new VierGewinnt();
        vg.save();

        // Überprüfe, ob die Datei erstellt wurde
        File saveFile = new File(VierGewinnt.getSaveGamePath());
        assertTrue(Files.exists(saveFile.toPath()), "Die Datei wurde nicht erstellt.");
        // Lade das gespeicherte Spiel
        VierGewinnt loadedGame = VierGewinnt.load();

        // Überprüfe, ob das geladene Spiel nicht null ist
        assertNotNull(loadedGame);
    }

    @Test
    public void testLoadNonExistentFile() {
        // Überprüfe, ob das Spiel null ist, wenn die Datei nicht existiert
        assertNull(VierGewinnt.load());
    }

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

        // Add some tokens to board
        vg.addToken(1, Token.playerOne);
        vg.addToken(1, Token.playerTwo);

        // Create a copy of the Board and check if boards are the same
        VierGewinnt copy = vg.copy();
        assertEquals(vg.toString(), copy.toString());

        // Add a token to the copy
        copy.addToken(1, Token.playerOne);

        // Check if boards are not the same
        assertNotEquals(vg.toString(), copy.toString());
        assertNotEquals(vg.getColumns()[0].getRows()[2], copy.getColumns()[0].getRows()[2]);
    }

    @Test
    public void addToken() {
        // Test Add Token Method
        VierGewinnt vg = new VierGewinnt();

        // Add Token to Column 1
        vg.addToken(1, Token.playerOne);

        // Add Token to Column 1
        vg.addToken(1, Token.playerTwo);

        // Check if Token is added
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