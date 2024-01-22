package backend;

import GUI.Connect4GUI;

public class Main {
    public static void main(String[] args) {
        //backend.VierGewinnt vg = new backend.VierGewinnt(backend.Difficulty.medium);
        //vg.play();
        Connect4GUI.getInstance().createGUI();
    }
}