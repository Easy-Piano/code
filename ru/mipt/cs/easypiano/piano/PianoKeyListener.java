package ru.mipt.cs.easypiano.piano;

//Dima
//A listener interface to listen to events on a piano key.

public interface PianoKeyListener {
	//Called when the specified piano key needs redraw.
	public void pianoKeyNeedsRedraw(PianoKey pianoKey);
}
