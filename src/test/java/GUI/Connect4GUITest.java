package GUI;

import backend.VierGewinnt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4GUITest {

    private static final String SAVE_GAME_PATH = "./testSaveGame.bin";
    private VierGewinnt game;

    @BeforeEach
    public void setUp() {
        // Initialisiere das Spiel für den Test
        game = new VierGewinnt();
    }

    @AfterEach
    public void tearDown() {
        // Lösche die Testdatei nach jedem Test
        File testSaveFile = new File(SAVE_GAME_PATH);
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
        game.save();

        // Überprüfe, ob die Datei erstellt wurde
        File saveFile = new File(SAVE_GAME_PATH);
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

    private static final int DEFAULT_ROWS = 6;
    private static final int DEFAULT_COLUMNS = 7;

        @Test
        public void getInstance() {
            Connect4GUI instance1 = Connect4GUI.getInstance();
            Connect4GUI instance2 = Connect4GUI.getInstance();

            // Check if both instances are not null and the same
            assertNotNull(instance1);
            assertNotNull(instance2);
            assertSame(instance1, instance2);
        }

        @Test
        public void createGUI() {
            Connect4GUI instance = Connect4GUI.getInstance();
            instance.createGUI();
            assertNotNull(instance.getFrame());
            assertNotNull(instance.getPanel());
        }

        @Test
        void createNewBoard() {
        }

        @Test
        void hasPlayerWon() {
            Connect4GUI instance = Connect4GUI.getInstance();

            assertFalse(instance.hasPlayerWon());
            instance.setPlayerWon(true);
            assertTrue(instance.hasPlayerWon());
            instance.setPlayerWon(false);
            assertFalse(instance.hasPlayerWon());
        }

        @Test
        void setPlayerWon() {
        }
    }



