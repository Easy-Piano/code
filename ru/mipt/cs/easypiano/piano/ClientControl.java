package ru.mipt.cs.easypiano.piano;

//Dima

public class ClientControl extends Control {

    private Piano piano;
    private KeyboardControl keyboardControl;

    public ClientControl(Piano piano, KeyboardControl keyboardControl) {
        this.piano = piano;
        this.keyboardControl = keyboardControl;
    }

    @Override
    public int getNote(int keyCode) {
        //return this.keyboardControl.getNote(keyCode);
        return keyCode;
    }

    @Override
    public boolean isNote(int keyCode) {
        return false;
    }

    @Override
    public void pianoKeyPressed(int keyCode) {
        piano.setKeyState(getNote(keyCode) ,true);
        Thread t = new KeyReleaseThread(keyCode);
        t.start();
    }

    @Override
    public void pianoKeyReleased(int keyCode) {
        piano.setKeyState(getNote(keyCode) ,false);
    }

    private class KeyReleaseThread extends Thread {
        private int keyCode;

        private KeyReleaseThread(int keyCode) {
            this.keyCode = keyCode;
        }

        public void run(){
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        piano.setKeyState(getNote(keyCode) ,false);
        }
    }
}
