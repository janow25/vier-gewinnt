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



