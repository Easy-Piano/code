package ru.mipt.cs.easypiano.piano;

//Dima
//Encapsulates the model of the pedal, that knows its state and image to draw.

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pedal {

	private boolean isDown;
	private List<PedalListener> listeners;
    private Point pedalPos;

	public Pedal() {
		isDown = false;
		this.listeners = new ArrayList<PedalListener>();
	}

    // Getters //

	public boolean getState() {
		return isDown;
	}
	
	// Returns Image for drawing
	public Image getImage() {
		return ImageResource.getImage((isDown) ? ImageResource.PEDAL_DOWN : ImageResource.PEDAL_UP);
	}

    public Point getPedalPos() {
        return pedalPos;
    }
	
    // Setters

    public void setPedalPos(Point pedalPos) {
        this.pedalPos = pedalPos;
    }

	public void setState(boolean isDown) {
		if (!this.isDown && isDown) {
			MusicManager.getInstance().pedalDown();
		} else if (this.isDown && !isDown) {
			MusicManager.getInstance().pedalUp();
		}

		this.isDown = isDown;
		fireNeedsRedraw(); // even though getState doesn't change
	}

	// Listners

	public void addListener(PedalListener listener) {
		listeners.add(listener);
	}

	public void removeListener(PedalListener listener) {
		listeners.remove(listener);
	}

	private void fireNeedsRedraw() {
		for (PedalListener listener: listeners)
			listener.pedalNeedsRedraw(this);
	}
}
