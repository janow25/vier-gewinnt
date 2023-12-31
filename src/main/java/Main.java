
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        VierGewinnt vg = new VierGewinnt();
        System.out.println(vg);

        vg.addToken(1, Token.playerTwo);
        System.out.println(vg);

        vg.addToken(1, Token.playerOne);
        System.out.println(vg);

        vg.addToken(1, Token.playerTwo);
        System.out.println(vg);

        vg.addToken(1, Token.playerOne);
        System.out.println(vg);
    }
}