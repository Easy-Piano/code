package ru.mipt.cs.easypiano.piano;

//Dima

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseControl {

    private static final int KEY_NOT_FOUND = -1;
    private int currentHovered = KEY_NOT_FOUND;
    private Piano piano;

    public MouseControl(Piano piano) {
        this.piano = piano;
        this.piano.addMouseMotionListener(new PianoMouseMotionListener());
        this.piano.addMouseListener(new PianoMouseListener());
    }

    // Returns the key number at the supplied point.
    // Returns KEY_NOT_FOUND if the point is not inside any key.
    public int getHoveredKeyIndex(Point point) {
        // white first
        for (PianoKey key: piano.pianoKeys) {
            if (key.getColor())
                if (key.doesContainPoint(point))
                    return key.getIndex();
        }

        // black second
        for (PianoKey key: piano.pianoKeys) {
            if (!key.getColor())
                if (key.doesContainPoint(point))
                    return key.getIndex();
        }

        return KEY_NOT_FOUND;
    }

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

    // Sets the current key number hovered to a new number.
    private void setCurrentHovered(int index) {
        if (currentHovered != index && currentHovered != KEY_NOT_FOUND) {
            // force release the previous hovered key
            piano.pianoKeys.get(currentHovered).setState(false);
        }
        currentHovered = index;
    }


    // Handles mouse press and release on piano keys.
    private class PianoMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int hovered = getHoveredKeyIndex(e.getPoint());
            if (hovered != KEY_NOT_FOUND)
                piano.pianoKeys.get(hovered).setState(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int hovered = getHoveredKeyIndex(e.getPoint());
            if (hovered != KEY_NOT_FOUND)
                piano.pianoKeys.get(hovered).setState(false);
        }
    }
}
