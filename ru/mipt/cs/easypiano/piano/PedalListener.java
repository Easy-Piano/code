package ru.mipt.cs.easypiano.piano;

//Dima
//A listener interface to listen to events on a pedal.

public interface PedalListener {
	//Called when the pedal needs redraw.
	public void pedalNeedsRedraw(Pedal pedal);
}
