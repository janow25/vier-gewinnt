package backend.bot;

import backend.VierGewinnt;

import java.io.Serializable;

public interface Bot extends Serializable {
    int makeMove(VierGewinnt vierGewinnt);
}
