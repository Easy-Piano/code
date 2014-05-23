package ru.mipt.cs.easypiano.piano;

//Dima

public class UserInputAnalyser implements Runnable {

    private KeyboardAdapterWithInput keyAdapter;
    private long worktime;
    private Piano piano;

    public UserInputAnalyser(long worktime, Piano piano) {
        this.piano = piano;
        this.worktime = worktime;
    }

    @Override
    public void run() {
        long startAnalyserTime = System.currentTimeMillis();
        keyAdapter = new KeyboardAdapterWithInput(piano);
        keyAdapter.setStartAnalyserTime(startAnalyserTime);
        //piano.removeKeyListener(piano.adapter);
        piano.addKeyListener(keyAdapter);
    }
}
