package backend.bot;

import backend.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHardBot {

    @Test
    public void TestFull() {
        VierGewinnt vg = new VierGewinnt(7, 6, Difficulty.hard);

        // Fill the board as a draw
        for (int i = 1; i < vg.getNumberOfColumns()/2+1; i++) {
            while (!vg.columnIsFull(i)) {
                vg.addToken(i, Token.playerOne);
                vg.addToken(i, Token.playerTwo);
            }
        }

        for (int i = vg.getNumberOfColumns()/2+2; i < vg.getNumberOfColumns()+1; i++) {
            while (!vg.columnIsFull(i)) {
                vg.addToken(i, Token.playerOne);
                vg.addToken(i, Token.playerTwo);
            }
        }

        Bot otherBot = new HardBot(VierGewinnt.getBotToken().other());

        vg.getBot().makeMove(vg);
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[vg.getNumberOfColumns()/2].getRows()[getLastFilledRow(vg.getColumns()[vg.getNumberOfColumns()/2])]);

        otherBot.makeMove(vg);
        assertEquals(VierGewinnt.getBotToken().other(), vg.getColumns()[vg.getNumberOfColumns()/2].getRows()[getLastFilledRow(vg.getColumns()[vg.getNumberOfColumns()/2])]);

        System.out.println(vg);

        System.out.println(vg.getRounds());
    }

    @Test
    public void TestAgainstItSelf() {
        VierGewinnt vg = new VierGewinnt(7, 6);

        while (vg.checkForWin() == GameStatus.onGoing) {
            HardBot bot = new HardBot(vg.getCurrentToken());
            bot.makeMove(vg);
            System.out.println(vg.getCurrentToken().toGameStatus().toWinnerName() + bot.evaluateBoard(vg));
        }

        System.out.println(vg);

        System.out.println(vg.checkForWin());

        System.out.println(vg.getRounds());
    }

    @Test
    public void TestBotMadeMove() {
        VierGewinnt vg = new VierGewinnt(Difficulty.hard);

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
        VierGewinnt vg = new VierGewinnt(Difficulty.hard);

        // Set up a situation where the player can win
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());

        // Check if the bot stops the player from winning
        vg.getBot().makeMove(vg);
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[0].getRows()[3]);
    }

    @Test
    public void TestWinFirst() {
        VierGewinnt vg = new VierGewinnt(Difficulty.hard);

        // Set up a situation where the player can win
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());
        vg.addToken(1, VierGewinnt.getBotToken().other());

        // Set up a situation where the bot can win
        vg.addToken(2, VierGewinnt.getBotToken());
        vg.addToken(2, VierGewinnt.getBotToken());
        vg.addToken(2, VierGewinnt.getBotToken());

        // Check if the bot wins instead of stopping the player from winning
        vg.getBot().makeMove(vg);
        assertEquals(VierGewinnt.getBotToken(), vg.getColumns()[1].getRows()[3]);

        // Check if the bot wins
        assertEquals(VierGewinnt.getBotToken().toGameStatus(), vg.checkForWin());
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