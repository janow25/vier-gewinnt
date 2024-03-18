package backend.bot;

import backend.Token;
import lombok.Getter;

public abstract class BotBase implements Bot {
    @Getter
    private Token myToken;
    BotBase() {
        myToken = Token.playerTwo;
    }
    BotBase(Token token) {
        myToken = token;
    }
}
