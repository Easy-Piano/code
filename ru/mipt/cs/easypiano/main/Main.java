package ru.mipt.cs.easypiano.main;

import ru.mipt.cs.easypiano.graphics.visualisation.MainFrame;
import ru.mipt.cs.easypiano.piano.ImageManager;
import ru.mipt.cs.easypiano.piano.MidiManager;

public class Main {
    public static void main(String args[]) {

        ImageManager.initFactory();
        MidiManager.init();
        MainFrame myFrame = new MainFrame();

    }
}
