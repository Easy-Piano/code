package ru.mipt.cs.easypiano.piano;

//Dima

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class UserInputAnalyser implements Runnable  {

    private Vector[] startTimes;
    private Vector[] endTimes;
    private Piano piano;
    private long startAnalyserTime;
    private long workingTime;
    private Control control;

    public UserInputAnalyser(Piano piano, long workingTime, Control control) {
        this.piano = piano;
        this.workingTime = workingTime;
        this.control = control;
    }

    private class PianoExerciseInputKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (control.isNote(keyCode))
                startTimes[control.getNote(keyCode)].add(System.currentTimeMillis()-startAnalyserTime);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (control.isNote(keyCode)) {
                endTimes[control.getNote(keyCode)].add(System.currentTimeMillis()-startAnalyserTime);
            }
        }
    }

    @Override
    public void run() {
        startAnalyserTime = System.currentTimeMillis();
        piano.addKeyListener(new PianoExerciseInputKeyListener());
    }

    public Vector[] getStartTimes() {
        return startTimes;
    }

    public Vector[] getEndTimes() {
        return endTimes;
    }
}
