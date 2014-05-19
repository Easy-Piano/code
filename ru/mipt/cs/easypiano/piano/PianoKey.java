package ru.mipt.cs.easypiano.piano;

//Dima
//Encapsulates a single piano key which knows its pitch, image to draw, location, etc.

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PianoKey {

	public static final Color COLOR_NORMAL_WHITE = new Color(1.0f, 1.0f, 1.0f);
	public static final Color COLOR_DOWN_WHITE = new Color(1.0f, 1.0f, 0.5f); // light yellow
	public static final Color COLOR_NORMAL_BLACK = new Color(0.0f, 0.0f, 0.0f);
	public static final Color COLOR_DOWN_BLACK = new Color(0.5f, 0.5f, 0.2f); // dark yellow

	private Piano piano;
    private Point keyPos;
	private int index;
	private int type;
    private boolean isBlack;
	private Rectangle bounds;
	private boolean isDown;

	// listener list
	private List<PianoKeyListener> listeners;

	public PianoKey(Piano piano, int index) {
		this.piano = piano;
		this.index = index;
		this.type = calculateType(index);
        this.isBlack = isChromatic(index);
		this.bounds = null;
		this.isDown = false; // initially, index key is down (pressed)
		this.listeners = new ArrayList<PianoKeyListener>();
	}

    public Image getImage(final int type) {
        switch ( type ) {
            case 0: return ImageResource.getImage( (isDown) ? ImageResource.BLACK_KEY_DOWN : ImageResource.BLACK_KEY_UP);
            case 1: return ImageResource.getImage( (isDown) ? ImageResource.WHITE_KEY_CENTRAL_DOWN : ImageResource.WHITE_KEY_CENTRAL_UP);
            case 2: return ImageResource.getImage( (isDown) ? ImageResource.WHITE_KEY_RIGHT_DOWN : ImageResource.WHITE_KEY_RIGHT_UP);
            default: return ImageResource.getImage( (isDown) ? ImageResource.WHITE_KEY_LEFT_DOWN : ImageResource.WHITE_KEY_LEFT_UP);
        }
    }
	
    // Returns whether the drawing bounds of this piano key contains the point.
	public boolean doesContainPoint(Point point) {
		return bounds.contains(point);
	}

    // Returns whether the specified pitch is chromatic.  Chromatic (Black keys) notes: C#, D#, F#, Ab, Bb.
    private static int calculateType(int pitch) {
        int i = pitch % Constants.OCTAVE_PITCH_DELTA;
        int res = 0;
        if (i == 1 || i == 3 || i == 6 || i == 8 || i == 10) res = 0;
        if (i == 2 || i == 7 || i == 9) res = 1;
        if (i == 4 || i == 11) res = 2;
        if (i == 0 || i == 5) res = 3;
        return res;
    }

    public static boolean isChromatic(int pitch) {
        int basePitch = pitch % Constants.OCTAVE_PITCH_DELTA;
        return (
                basePitch == 1 ||
                        basePitch == 3 ||
                        basePitch == 6 ||
                        basePitch == 8 ||
                        basePitch == 10
        );
    }

    public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean getColor() {
		return isBlack;
	}
	
	public boolean getState() {
		return isDown; 
	}
	
	public int getPitch() {
		return index + piano.getBasePitch();
	}

    public Point getKeyPos() {
        return keyPos;
    }
	
	public int getIndex() {
		return index;
	}

    // Returns the fill color of the key (for drawing purpose).
    public Color getFillColor() {
        return (isBlack) ?
                ((isDown) ? PianoKey.COLOR_DOWN_BLACK : PianoKey.COLOR_NORMAL_BLACK)
                :
                ((isDown) ? PianoKey.COLOR_DOWN_WHITE : PianoKey.COLOR_NORMAL_WHITE);
    }
	
    //Setters

	public void setState(boolean isDown) {
		if (!this.isDown && isDown) {
			MusicManager.getInstance().playNote(getPitch());
		} else if (this.isDown && !isDown) {
			// TODO: What happens if the octave changes before note stopped?
			MusicManager.getInstance().stopNote(getPitch());
		}
			
		this.isDown = isDown;
		fireNeedsRedraw(); // even though getState doesn't change
	}


    public void setKeyPos(Point keyPos) {
        this.keyPos = keyPos;
    }

	public void setBounds(Rectangle rectangle) {
		this.bounds = rectangle;
	}
	
    //Listeners

	public void addListener(PianoKeyListener listener) {
		listeners.add(listener);
	}

	public void removeListener(PianoKeyListener listener) {
		listeners.remove(listener);
	}

	private void fireNeedsRedraw() {
		for (PianoKeyListener listener: listeners)
			listener.pianoKeyNeedsRedraw(this);
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
