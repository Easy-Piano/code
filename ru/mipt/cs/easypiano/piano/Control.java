package ru.mipt.cs.easypiano.piano;

//Dima

public abstract class Control {
    protected Piano piano;

    public abstract int getNote(int keyCode);

    public abstract boolean isNote(int keyCode);

    public abstract void pianoKeyPressed(int keyCode);

    public abstract void pianoKeyReleased(int keyCode);
}
