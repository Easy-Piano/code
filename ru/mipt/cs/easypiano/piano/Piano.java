package ru.mipt.cs.easypiano.piano;

// Dima
// The piano panel contains clickable piano keys and pedal.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piano extends JPanel {

	private static final int NUM_OCTAVES = 3;
	private static final int NUM_KEYS = Constants.OCTAVE_PITCH_DELTA * Piano.NUM_OCTAVES + 1;
	private static final int NUM_KEYS_PER_OCTAVE = 12;
	private static final int KEY_NOT_FOUND = -1;

	// Default base pitch (lowest playable C)
	private static final int DEFAULT_BASE_PITCH = 48;
	private static final int MIN_BASE_PITCH = 0;
	private static final int MAX_BASE_PITCH = 216;

	private List<PianoKey> pianoKeys;
	private Pedal pedal;

	// of the Piano JPanel
	private int width;

    private int currentHovered = KEY_NOT_FOUND;
	
	private int basePitch = DEFAULT_BASE_PITCH;
	
	private Map<Integer, Integer> keyMap;

	public Piano() {
		createKeys();		
		createPedal();
		initKeyMap();
		
		// width already assigned in createKeys()
        int height = Constants.KEY_FRAME_HEIGHT + Constants.PEDAL_PADDING +
                Constants.PEDAL_HEIGHT;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true); // to be able to read key
		
		// listener interfaces
		addMouseMotionListener(new PianoMouseMotionListener());
		addMouseListener(new PianoMouseListener());
		addKeyListener(new PianoViewKeyListener());
	}

	private void initKeyMap() {
		keyMap = new HashMap<Integer, Integer>();
		keyMap.put(KeyEvent.VK_Z, 12 * 0 + 0); // C4
		keyMap.put(KeyEvent.VK_S, 12 * 0 + 1);
		keyMap.put(KeyEvent.VK_X, 12 * 0 + 2);
		keyMap.put(KeyEvent.VK_D, 12 * 0 + 3);
		keyMap.put(KeyEvent.VK_C, 12 * 0 + 4);
		keyMap.put(KeyEvent.VK_V, 12 * 0 + 5);
		keyMap.put(KeyEvent.VK_G, 12 * 0 + 6);
		keyMap.put(KeyEvent.VK_B, 12 * 0 + 7);    
		keyMap.put(KeyEvent.VK_H, 12 * 0 + 8);
		keyMap.put(KeyEvent.VK_N, 12 * 0 + 9);
		keyMap.put(KeyEvent.VK_J, 12 * 0 + 10);
		keyMap.put(KeyEvent.VK_M, 12 * 0 + 11);
		keyMap.put(KeyEvent.VK_Q, 12 * 1 + 0); // C5
		keyMap.put(KeyEvent.VK_2, 12 * 1 + 1);
		keyMap.put(KeyEvent.VK_W, 12 * 1 + 2);
		keyMap.put(KeyEvent.VK_3, 12 * 1 + 3);
		keyMap.put(KeyEvent.VK_E, 12 * 1 + 4);
		keyMap.put(KeyEvent.VK_R, 12 * 1 + 5);
		keyMap.put(KeyEvent.VK_5, 12 * 1 + 6);
		keyMap.put(KeyEvent.VK_T, 12 * 1 + 7);
		keyMap.put(KeyEvent.VK_6, 12 * 1 + 8);
		keyMap.put(KeyEvent.VK_Y, 12 * 1 + 9);
		keyMap.put(KeyEvent.VK_7, 12 * 1 + 10);
		keyMap.put(KeyEvent.VK_U, 12 * 1 + 11);
		keyMap.put(KeyEvent.VK_I, 12 * 2 + 0); // C6
		keyMap.put(KeyEvent.VK_9, 12 * 2 + 1);
		keyMap.put(KeyEvent.VK_O, 12 * 2 + 2);
		keyMap.put(KeyEvent.VK_0, 12 * 2 + 3);
		keyMap.put(KeyEvent.VK_P, 12 * 2 + 4);
		keyMap.put(KeyEvent.VK_OPEN_BRACKET, 12 * 2 + 5);
		keyMap.put(KeyEvent.VK_EQUALS, 12 * 2 + 6);
		keyMap.put(KeyEvent.VK_CLOSE_BRACKET, 12 * 2 + 7);
		keyMap.put(KeyEvent.VK_BACK_SPACE, 12 * 2 + 8);
		keyMap.put(KeyEvent.VK_BACK_SLASH, 12 * 2 + 9);
	}
	

	// Create the piano keys, initializes them, registers listeners. Also populates width.
	private void createKeys() {
        pianoKeys = new ArrayList<PianoKey>();

        // the left coordinate of the next white key
        int pianoKeyNextLeftCoordinate = Constants.PIANO_KEY_LEFT;

        // creates keys and position them correctly
        for (int i = 0; i < Piano.NUM_KEYS; i++) {
            PianoKey pianoKey = new PianoKey(this, i);

            if (pianoKey.getColor()) {
                pianoKey.setKeyPos(new Point(pianoKeyNextLeftCoordinate - Constants.BLACK_KEY_WIDTH / 2, Constants.PIANO_KEY_TOP));
                pianoKey.setBounds(new Rectangle(
                        pianoKeyNextLeftCoordinate - Constants.BLACK_KEY_WIDTH / 2,
                        Constants.PIANO_KEY_TOP,
                        Constants.BLACK_KEY_WIDTH,
                        Constants.BLACK_KEY_HEIGHT));
            } else {
                pianoKey.setKeyPos(new Point(pianoKeyNextLeftCoordinate, Constants.PIANO_KEY_TOP));
                pianoKey.setBounds(new Rectangle(
                        pianoKeyNextLeftCoordinate,
                        Constants.PIANO_KEY_TOP,
                        Constants.WHITE_KEY_WIDTH,
                        Constants.WHITE_KEY_HEIGHT));
                pianoKeyNextLeftCoordinate += Constants.KEY_LEFT_OFFSET;
            }

            // register listener
            pianoKey.addListener(new PianoPianoKeyListener());
            pianoKeys.add(pianoKey);
        }
        width = pianoKeyNextLeftCoordinate;
    }

	// Create and initializes the pedal and register a listener. Populates pedalPos.
	private void createPedal() {
		pedal = new Pedal();
		pedal.addListener(new PianoPedalListener());
		// we can assume that Piano width is already determined
		pedal.setPedalPos(new Point((width - Constants.PEDAL_WIDTH) / 2, Constants.KEY_FRAME_HEIGHT + Constants.PEDAL_PADDING));
	}

    // Stops all currently playing notes (through set down)
	public void reset() {
		pedal.setState(false);
		for (PianoKey pianoKey: pianoKeys) {
			pianoKey.setState(false);
		}
	}
	
	// Paints whole piano JPanel
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintKeys(g);
		paintPedalArea(g);
	}

	@Override
	public void repaint() {
		//Utilities.showMessage("Piano repaint()");
		super.repaint();
	}
	
	// Paints all keys with its frames
	private void paintKeys(Graphics g) {		
		// paint key frames
		for (int i = 0; i < Piano.NUM_KEYS; i++) {
            g.drawImage(pianoKeys.get(i).getImage(pianoKeys.get(i).getType()),
                   pianoKeys.get(i).getKeyPos().x, pianoKeys.get(i).getKeyPos().y, null);

			/*g.setColor(PianoKey.COLOR_KEY_FRAME);
			g.drawRect(Constants.KEY_FRAME_LEFT + i * Constants.KEY_LEFT_OFFSET,
					   Constants.KEY_FRAME_TOP, Constants.KEY_FRAME_WIDTH - 1,
					   Constants.KEY_FRAME_HEIGHT - 1);
            g.drawImage(pianoKeys.get(i).getImage(pianoKeys.get(i).getColor()),
                    pianoKeys.get(i).getKeyPos().x, pianoKeys.get(i).getKeyPos().y, null);
		}

		// black first
		for (PianoKey key: pianoKeys) {
			if (!key.getColor()) {
				g.setColor(key.getFillColor());
				Rectangle bounds = key.getBounds();
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
		}
		
		// white second
		for (PianoKey key: pianoKeys) {
			if (key.getColor()) {
				g.setColor(key.getFillColor());
				Rectangle bounds = key.getBounds();
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}*/
		}
	}
	
    // Paints the pedal area (the area below piano keys).
	private void paintPedalArea(Graphics g) {
		// background
		g.setColor(Constants.PIANO_BACKGROUND_COLOR);
		g.fillRect(0, Constants.KEY_FRAME_HEIGHT, width, Constants.PEDAL_AREA_HEIGHT);
		
		// pedal
		g.drawImage(pedal.getImage(), pedal.getPedalPos().x, pedal.getPedalPos().y, null);
		
		// instrument number and octave number
		g.setFont(Constants.INSTRUMENT_NUMBER_FONT);
		g.setColor(Constants.INSTRUMENT_NUMBER_COLOR);
		g.drawString(MusicManager.getInstance().getSynthInstrument() + ". " +
				MusicManager.getInstance().getInstrumentName(),
				Constants.INSTRUMENT_NUMBER_LEFT,
				Constants.KEY_FRAME_HEIGHT + Constants.INSTRUMENT_NUMBER_PADDING
        );
		
		g.drawString("C" + getBasePitch() / 12,
                width - Constants.WHITE_KEY_WIDTH / 2,
				Constants.KEY_FRAME_HEIGHT + Constants.INSTRUMENT_NUMBER_PADDING);
	}
	

	// Returns the key number at the supplied point.
	// Returns KEY_NOT_FOUND if the point is not inside any key.
	public int getHoveredKeyIndex(Point point) {
		// white first
		for (PianoKey key: pianoKeys) {
			if (key.getColor())
				if (key.doesContainPoint(point))
					return key.getIndex();
		}

		// black second
		for (PianoKey key: pianoKeys) {
			if (!key.getColor())
				if (key.doesContainPoint(point))
					return key.getIndex();
		}

		return Piano.KEY_NOT_FOUND;
	}

	// Returns the actual width of the Piano.  We do not use the default
	// getWidth in JComponent because before the component is drawn, getWidth
	// will return 0.
	public int getPianoWidth() {
		return width;
	}
	
    // Returns the base pitch number of the Piano.  Used by the PianoKeys.
	public int getBasePitch() {
		return basePitch;
	}
	
    // Setters //

	// Increases the base pitch by one octave.
	public void incOctave() {
		if (basePitch < MAX_BASE_PITCH) {
			basePitch += NUM_KEYS_PER_OCTAVE;
		}
	}

	// Decreases the base pitch by one octave.
	public void decOctave() {
		if (basePitch > MIN_BASE_PITCH) {
			basePitch -= NUM_KEYS_PER_OCTAVE;
		}
	}

	// Sets the current key number hovered to a new number.
	private void setCurrentHovered(int index) {
		if (currentHovered != index && currentHovered != Piano.KEY_NOT_FOUND) {
			// force release the previous hovered key
			pianoKeys.get(currentHovered).setState(false);
		}
		
		currentHovered = index;
	}
	
	// Listener's interfaces //

	// Handles mouse motion. Detects whenever the mouse pointer goes out of a piano key.
	private class PianoMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			setCurrentHovered(getHoveredKeyIndex(e.getPoint()));
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}
	}

	// Handles mouse press and release on piano keys.
	private class PianoMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int hovered = getHoveredKeyIndex(e.getPoint());
			if (hovered != Piano.KEY_NOT_FOUND)
				pianoKeys.get(hovered).setState(true);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			int hovered = getHoveredKeyIndex(e.getPoint());
			if (hovered != Piano.KEY_NOT_FOUND)
				pianoKeys.get(hovered).setState(false);
		}
	}

	private class PianoViewKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if (keyCode == Constants.PEDAL_KEY) {
				pedal.setState(true);
			} else if (keyCode == KeyEvent.VK_LEFT) { // instrument --
				MusicManager.getInstance().decSynthInstrument();
				repaint();
			} else if (keyCode == KeyEvent.VK_RIGHT) { // instrument ++
				MusicManager.getInstance().incSynthInstrument();
				repaint();
			} else if (keyCode == KeyEvent.VK_PAGE_UP) { // octave ++
				reset();
				incOctave();
				repaint();
			} else if (keyCode == KeyEvent.VK_PAGE_DOWN) { // octave --
				reset();
				decOctave();
				repaint();
			} else if (keyCode == KeyEvent.VK_ENTER) { // reset
				reset();
				repaint();
			} else {
				if (keyMap.containsKey(keyCode))
					pianoKeys.get(keyMap.get(keyCode)).setState(true);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == Constants.PEDAL_KEY) {
				pedal.setState(false);
			} else {
				if (keyMap.containsKey(keyCode)) pianoKeys.get(keyMap.get(keyCode)).setState(false);
			}
		}
	}

	// Responds to piano key's notification to redraw.
	private class PianoPianoKeyListener implements PianoKeyListener {
		public void pianoKeyNeedsRedraw(PianoKey pianoKey) {
			repaint();
		}
	}

	// Responds to pedal's notification to redraw.
	private class PianoPedalListener implements PedalListener {
		public void pedalNeedsRedraw(Pedal pedal) {
			repaint();
		}
	}
	
}
