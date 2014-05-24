package ru.mipt.cs.easypiano.piano;

// Dima
// The piano panel contains clickable piano keys and pedal.

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Piano extends JPanel {

    protected List<PianoKey> pianoKeys;
	private Pedal pedal;

    public List<Control> getControlList() {
        return controlList;
    }

    protected List<Control> controlList;
    //public KeyAdapter adapter;

    public int getWidth() {
        return width;
    }

    private int width;
	private int basePitch = Constants.DEFAULT_BASE_PITCH;
	
	public Piano() {
        this.controlList = new ArrayList<Control>();
		createKeys();		
		createPedal();
        //this.adapter = new KeyboardAdapter(this);
		// width already assigned in createKeys()
        int height = Constants.KEY_FRAME_HEIGHT + Constants.PEDAL_PADDING +
                Constants.PEDAL_HEIGHT;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
        //this.addKeyListener(adapter);
	}

	// Create the piano keys, initializes them, registers listeners. Also populates width.
	private void createKeys() {
        pianoKeys = new ArrayList<PianoKey>();

        // the left coordinate of the next white key
        int pianoKeyNextLeftCoordinate = Constants.PIANO_KEY_LEFT;

        // creates keys and position them correctly
        for (int i = 0; i < Constants.NUM_KEYS; i++) {
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

    public void setKeyState(int keyNum, boolean state){
        pianoKeys.get(keyNum).setState(state);
    }

    public void setPedalState(boolean state){
        pedal.setState(state);
    }
	
	// Paints whole piano JPanel
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

        // paint key frames
        /*for (int i = 0; i < Constants.NUM_KEYS; i++) {
            if(!pianoKeys.get(i).getColor()) g.drawImage(resizeImage(pianoKeys.get(i).getImage(pianoKeys.get(i).getType()),Constants.WHITE_KEY_WIDTH, Constants.WHITE_KEY_HEIGHT),
                    pianoKeys.get(i).getKeyPos().x, pianoKeys.get(i).getKeyPos().y, null);

            if(pianoKeys.get(i).getColor()) g.drawImage(resizeImage(pianoKeys.get(i).getImage(pianoKeys.get(i).getType()),Constants.BLACK_KEY_WIDTH, Constants.BLACK_KEY_HEIGHT),
                    pianoKeys.get(i).getKeyPos().x, pianoKeys.get(i).getKeyPos().y, null);
        }*/

        for (int i = 0; i < Constants.NUM_KEYS; i++) {
        g.drawImage(pianoKeys.get(i).getImage(pianoKeys.get(i).getType()),
                    pianoKeys.get(i).getKeyPos().x, pianoKeys.get(i).getKeyPos().y, null);
        }

        // Below code paints the pedal area (the area below piano keys).
        g.setColor(Constants.PIANO_BACKGROUND_COLOR);
        g.fillRect(0, Constants.KEY_FRAME_HEIGHT, width, Constants.PEDAL_AREA_HEIGHT);

        // pedal
        g.drawImage(pedal.getImage(), pedal.getPedalPos().x, pedal.getPedalPos().y, null);

        // instrument number and octave number
        g.setFont(Constants.INSTRUMENT_NUMBER_FONT);
        g.setColor(Constants.INSTRUMENT_NUMBER_COLOR);
        g.drawString(MidiManager.getInstance().getSynthInstrument() + ". " +
                        MidiManager.getInstance().getInstrumentName(),
                Constants.INSTRUMENT_NUMBER_LEFT,
                Constants.KEY_FRAME_HEIGHT + Constants.INSTRUMENT_NUMBER_PADDING
        );
	}

	@Override
	public void repaint() {
		super.repaint();
	}

    public void addControl(Control c){
        this.controlList.add(c);
    }

    public List<PianoKey> getPianoKeys() {
        return this.pianoKeys;
    }

	public int getBasePitch() {
		return basePitch;
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

    private BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();

        return bufferedImage;
    }
}
