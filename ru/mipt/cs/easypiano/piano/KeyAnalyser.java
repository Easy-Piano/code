package ru.mipt.cs.easypiano.piano;

//Dima

import java.util.Vector;

public class KeyAnalyser implements Runnable  {
    public int note;
    public Vector StartTimes;
    public Vector EndTimes;
    public PianoKey pkey;

    @Override
    public void run() {/*
        pkey.addKeyListener(new KeyListener() {
            //When any key is pressed and released then the
            //keyPressed and keyReleased methods are called respectively.
            //The keyTyped method is called when a valid character is typed.
            //The getKeyChar returns the character for the key used. If the key
            //is a modifier key (e.g., SHIFT, CTRL) or action key (e.g., DELETE, ENTER)
            //then the character will be a undefined symbol.
            //getKeyChar()
            @Override
            public void keyPressed(KeyEvent e){
            }
            @Override
            public void keyReleased(KeyEvent e)
            {

            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });*/
    }
}
