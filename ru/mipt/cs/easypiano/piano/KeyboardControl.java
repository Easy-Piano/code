package ru.mipt.cs.easypiano.piano;

//Dima

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardControl {

    public Map<Integer, Integer> getKeyMap() {
        return keyMap;
    }

    private Map<Integer, Integer> keyMap;
    private Piano piano;

    public static final int NOTE_0 = KeyEvent.VK_Z;
    public static final int NOTE_1 = KeyEvent.VK_S;
    public static final int NOTE_2 = KeyEvent.VK_X;
    public static final int NOTE_3 = KeyEvent.VK_D;
    public static final int NOTE_4 = KeyEvent.VK_C;
    public static final int NOTE_5 = KeyEvent.VK_V;
    public static final int NOTE_6 = KeyEvent.VK_G;
    public static final int NOTE_7 = KeyEvent.VK_B;
    public static final int NOTE_8 = KeyEvent.VK_H;
    public static final int NOTE_9 = KeyEvent.VK_N;
    public static final int NOTE_10 = KeyEvent.VK_J;
    public static final int NOTE_11 = KeyEvent.VK_M;
    public static final int NOTE_12 = KeyEvent.VK_COMMA;
    public static final int NOTE_13 = KeyEvent.VK_1;
    public static final int NOTE_14 = KeyEvent.VK_Q;
    public static final int NOTE_15 = KeyEvent.VK_2;
    public static final int NOTE_16 = KeyEvent.VK_W;
    public static final int NOTE_17 = KeyEvent.VK_E;
    public static final int NOTE_18 = KeyEvent.VK_4;
    public static final int NOTE_19 = KeyEvent.VK_R;
    public static final int NOTE_20 = KeyEvent.VK_5;
    public static final int NOTE_21 = KeyEvent.VK_T;
    public static final int NOTE_22 = KeyEvent.VK_6;
    public static final int NOTE_23 = KeyEvent.VK_Y;
    public static final int NOTE_24 = KeyEvent.VK_U;
    public static final int NOTE_25 = KeyEvent.VK_8;
    public static final int NOTE_26 = KeyEvent.VK_I;
    public static final int NOTE_27 = KeyEvent.VK_9;
    public static final int NOTE_28 = KeyEvent.VK_O;
    public static final int NOTE_29 = KeyEvent.VK_P;
    public static final int NOTE_30 = KeyEvent.VK_MINUS;
    public static final int NOTE_31 = KeyEvent.VK_OPEN_BRACKET;
    public static final int NOTE_32 = KeyEvent.VK_EQUALS;
    public static final int NOTE_33 = KeyEvent.VK_CLOSE_BRACKET;
    public static final int NOTE_34 = KeyEvent.VK_BACK_SPACE;
    public static final int NOTE_35 = KeyEvent.VK_BACK_SLASH;

    public KeyboardControl(Piano piano) {
        this.piano = piano;
        initKeyMap();
        this.piano.addKeyListener(new PianoViewKeyListener());
    }

    private void initKeyMap() {
        keyMap = new HashMap<Integer, Integer>();
        keyMap.put(NOTE_0, 12 * 0 + 0); // BASE_PITCH
        keyMap.put(NOTE_1, 12 * 0 + 1);
        keyMap.put(NOTE_2, 12 * 0 + 2);
        keyMap.put(NOTE_3, 12 * 0 + 3);
        keyMap.put(NOTE_4, 12 * 0 + 4);
        keyMap.put(NOTE_5, 12 * 0 + 5);
        keyMap.put(NOTE_6, 12 * 0 + 6);
        keyMap.put(NOTE_7, 12 * 0 + 7);
        keyMap.put(NOTE_8, 12 * 0 + 8);
        keyMap.put(NOTE_9, 12 * 0 + 9);
        keyMap.put(NOTE_10, 12 * 0 + 10);
        keyMap.put(NOTE_11, 12 * 0 + 11);
        keyMap.put(NOTE_12, 12 * 1 + 0);
        keyMap.put(NOTE_13, 12 * 1 + 1);
        keyMap.put(NOTE_14, 12 * 1 + 2);
        keyMap.put(NOTE_15, 12 * 1 + 3);
        keyMap.put(NOTE_16, 12 * 1 + 4);
        keyMap.put(NOTE_17, 12 * 1 + 5);
        keyMap.put(NOTE_18, 12 * 1 + 6);
        keyMap.put(NOTE_19, 12 * 1 + 7);
        keyMap.put(NOTE_20, 12 * 1 + 8);
        keyMap.put(NOTE_21, 12 * 1 + 9);
        keyMap.put(NOTE_22, 12 * 1 + 10);
        keyMap.put(NOTE_23, 12 * 1 + 11);
        keyMap.put(NOTE_24, 12 * 2 + 0);
        keyMap.put(NOTE_25, 12 * 2 + 1);
        keyMap.put(NOTE_26, 12 * 2 + 2);
        keyMap.put(NOTE_27, 12 * 2 + 3);
        keyMap.put(NOTE_28, 12 * 2 + 4);
        keyMap.put(NOTE_29, 12 * 2 + 5);
        keyMap.put(NOTE_30, 12 * 2 + 6);
        keyMap.put(NOTE_31, 12 * 2 + 7);
        keyMap.put(NOTE_32, 12 * 2 + 8);
        keyMap.put(NOTE_33, 12 * 2 + 9);
        keyMap.put(NOTE_34, 12 * 2 + 10);
        keyMap.put(NOTE_35, 12 * 2 + 11);
    }

    private void changeKeyMap(int i) {
        keyMap.put(NOTE_0, keyMap.get(NOTE_0) + i);
        keyMap.put(NOTE_1, keyMap.get(NOTE_1) + i);
        keyMap.put(NOTE_2, keyMap.get(NOTE_2) + i);
        keyMap.put(NOTE_3, keyMap.get(NOTE_3) + i);
        keyMap.put(NOTE_4, keyMap.get(NOTE_4) + i);
        keyMap.put(NOTE_5, keyMap.get(NOTE_5) + i);
        keyMap.put(NOTE_6, keyMap.get(NOTE_6) + i);
        keyMap.put(NOTE_7, keyMap.get(NOTE_7) + i);
        keyMap.put(NOTE_8, keyMap.get(NOTE_8) + i);
        keyMap.put(NOTE_9, keyMap.get(NOTE_9) + i);
        keyMap.put(NOTE_10, keyMap.get(NOTE_10) + i);
        keyMap.put(NOTE_11, keyMap.get(NOTE_11) + i);
        keyMap.put(NOTE_12, keyMap.get(NOTE_12) + i);
        keyMap.put(NOTE_13, keyMap.get(NOTE_13) + i);
        keyMap.put(NOTE_14, keyMap.get(NOTE_14) + i);
        keyMap.put(NOTE_15, keyMap.get(NOTE_15) + i);
        keyMap.put(NOTE_16, keyMap.get(NOTE_16) + i);
        keyMap.put(NOTE_17, keyMap.get(NOTE_17) + i);
        keyMap.put(NOTE_18, keyMap.get(NOTE_18) + i);
        keyMap.put(NOTE_19, keyMap.get(NOTE_19) + i);
        keyMap.put(NOTE_20, keyMap.get(NOTE_20) + i);
        keyMap.put(NOTE_21, keyMap.get(NOTE_21) + i);
        keyMap.put(NOTE_22, keyMap.get(NOTE_22) + i);
        keyMap.put(NOTE_23, keyMap.get(NOTE_23) + i);
        keyMap.put(NOTE_24, keyMap.get(NOTE_24) + i);
        keyMap.put(NOTE_25, keyMap.get(NOTE_25) + i);
        keyMap.put(NOTE_26, keyMap.get(NOTE_26) + i);
        keyMap.put(NOTE_27, keyMap.get(NOTE_27) + i);
        keyMap.put(NOTE_28, keyMap.get(NOTE_28) + i);
        keyMap.put(NOTE_29, keyMap.get(NOTE_29) + i);
        keyMap.put(NOTE_30, keyMap.get(NOTE_30) + i);
        keyMap.put(NOTE_31, keyMap.get(NOTE_31) + i);
        keyMap.put(NOTE_32, keyMap.get(NOTE_32) + i);
        keyMap.put(NOTE_33, keyMap.get(NOTE_33) + i);
        keyMap.put(NOTE_34, keyMap.get(NOTE_34) + i);
        keyMap.put(NOTE_35, keyMap.get(NOTE_35) + i);
    }

    // Increases the base pitch by one octave.
    public void incOctave() {
        if (keyMap.get(NOTE_0) >= 0 && keyMap.get(NOTE_35) <= Constants.NUM_KEYS_PER_OCTAVE*(Constants.NUM_OCTAVES - 1)) {
            changeKeyMap(Constants.NUM_KEYS_PER_OCTAVE);
        }
    }

    // Decreases the base pitch by one octave.
    public void decOctave() {
        if ( keyMap.get(NOTE_0) >= Constants.NUM_KEYS_PER_OCTAVE && keyMap.get(NOTE_35) <= Constants.NUM_KEYS_PER_OCTAVE*Constants.NUM_OCTAVES) {
            changeKeyMap(-Constants.NUM_KEYS_PER_OCTAVE);
        }
    }

    private class PianoViewKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == Constants.PEDAL_KEY) {
                piano.pedal.setState(true);
            } else if (keyCode == KeyEvent.VK_LEFT) { // instrument --
                MidiManager.getInstance().decSynthInstrument();
                piano.repaint();
            } else if (keyCode == KeyEvent.VK_RIGHT) { // instrument ++
                MidiManager.getInstance().incSynthInstrument();
                piano.repaint();
            } else if (keyCode == Constants.INC_OCTAVE) { // octave ++
                piano.reset();
                incOctave();
            } else if (keyCode == Constants.DEC_OCTAVE) { // octave --
                piano.reset();
                decOctave();
            } else if (keyCode == KeyEvent.VK_ENTER) { // reset
                piano.reset();
                piano.repaint();
            } else {
                if (keyMap.containsKey(keyCode))
                    piano.pianoKeys.get(keyMap.get(keyCode)).setState(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == Constants.PEDAL_KEY) {
                piano.pedal.setState(false);
            } else {
                if (keyMap.containsKey(keyCode)) piano.pianoKeys.get(keyMap.get(keyCode)).setState(false);
            }
        }
    }

}
