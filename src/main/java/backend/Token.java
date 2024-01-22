package backend;

import java.awt.*;

public enum Token {
    empty,
    playerOne,
    playerTwo;

    public GameStatus toGameStatus() {
        return switch (this) {
            case playerOne -> GameStatus.playerOneWon;
            case playerTwo -> GameStatus.playerTwoWon;
            default -> GameStatus.onGoing;
        };
    }

    public Token other() {
        return switch (this) {
            case playerOne -> Token.playerTwo;
            case playerTwo -> Token.playerOne;
            default -> Token.empty;
        };
    }

    public Color toColor() {
        return switch (this) {
            case playerOne -> Color.red;
            case playerTwo -> Color.yellow;
            default -> null;
        };
    }
}
