public enum Token {
    empty,
    playerOne,
    playerTwo;

    public GameStatus toGameStatus() {
        if (this == Token.playerOne) {
            return GameStatus.playerOneWon;
        } else if (this == Token.playerTwo) {
            return GameStatus.playerTwoWon;
        } else {
            return GameStatus.onGoing;
        }
    }

    public Token other() {
        if (this == Token.playerOne) {
            return Token.playerTwo;
        } else if (this == Token.playerTwo) {
            return Token.playerOne;
        } else {
            return Token.empty;
        }
    }
}
