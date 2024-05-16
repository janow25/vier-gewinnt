package backend;

public enum GameStatus {
    playerOneWon,
    playerTwoWon,
    draw,
    onGoing;

    public String toWinnerName() {
        return switch (this) {
            case playerOneWon -> "Player One";
            case playerTwoWon -> "Player Two";
            default -> null;
        };
    }
}
