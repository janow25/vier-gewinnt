import java.io.Serializable;

public interface Bot extends Serializable {
    int makeMove(VierGewinnt vierGewinnt);
}
