package ru.mipt.cs.easypiano.piano;

// Dima
// Contains all the constants used in layouting, etc.
// All constants must be obtained through the methods.  The default
// implementation of the methods is just returning the value of private static
// constants.

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class Constants {
<<<<<<< HEAD
	// Video
    public static final int CANVAS_MINI_HEIGHT = 400;
    public static final int CANVAS_MINI_WIDTH = 1260;
    public static final int CANVAS_WHITE_KEY_WIDTH = 30;
    public static final int CANVAS_BLACK_KEY_WIDTH = 24;




    // piano
	public static final Color PIANO_BACKGROUND_COLOR = new Color(0.62352943f, 0.7019608f, 0.69411767f);

    public static final boolean BLACK_COLOR = true;
    public static final boolean WHITE_COLOR = false;
	
	public static final int WHITE_KEY_WIDTH = 63;
	public static final int WHITE_KEY_HEIGHT = 280;
	public static final int BLACK_KEY_WIDTH = 37;
	public static final int BLACK_KEY_HEIGHT = 173;
=======
	
	// piano
	public static final Color PIANO_BACKGROUND_COLOR = new Color(0.6f, 0.7f, 0.7f);

	public static final int WHITE_KEY_WIDTH = ImageManager.getInstance(ImageManager.WHITE_KEY_CENTRAL_UP).getWidth();
	public static final int WHITE_KEY_HEIGHT = ImageManager.getInstance(ImageManager.WHITE_KEY_CENTRAL_UP).getHeight();
	public static final int BLACK_KEY_WIDTH = ImageManager.getInstance(ImageManager.BLACK_KEY_UP).getWidth();
	public static final int BLACK_KEY_HEIGHT = ImageManager.getInstance(ImageManager.BLACK_KEY_UP).getHeight();
>>>>>>> b86198ecac09c40245af3b6b9b6e4b06a68f68a0
	public static final int KEY_LEFT_OFFSET = WHITE_KEY_WIDTH + 1;
	public static final int KEY_FRAME_HEIGHT = WHITE_KEY_HEIGHT + 2;
	
	public static final int PIANO_KEY_LEFT = 1;
	public static final int PIANO_KEY_TOP = 1;

	public static final int PEDAL_WIDTH = 48;
	public static final int PEDAL_HEIGHT = 36;
	public static final int PEDAL_PADDING = 4;
	public static final int PEDAL_AREA_HEIGHT = PEDAL_PADDING + PEDAL_HEIGHT;
	
	// instrument number
	public static final int INSTRUMENT_NUMBER_LEFT = 10;
	public static final int INSTRUMENT_NUMBER_PADDING = 25;
    public static final Color INSTRUMENT_NUMBER_COLOR = new Color(1.0f, 1.0f, 1.0f);
	public static final Font INSTRUMENT_NUMBER_FONT = new Font("Dialog", Font.PLAIN, 14);
	
	// keys
	public static final int PEDAL_KEY = KeyEvent.VK_SPACE;
    public static final int INC_OCTAVE = KeyEvent.VK_PAGE_UP;
    public static final int DEC_OCTAVE = KeyEvent.VK_PAGE_DOWN;
    public static final int NUM_KEYS_PER_OCTAVE = 12;

    // Default base pitch (lowest playable note)
    static final int DEFAULT_BASE_PITCH = 36;

    // general
    static final int NUM_OCTAVES = 6;
    static final int NUM_KEYS = NUM_KEYS_PER_OCTAVE* NUM_OCTAVES + 1;
}
