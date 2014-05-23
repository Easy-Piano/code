package ru.mipt.cs.easypiano.piano;

//Dima

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseControl extends Control {

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
        for (PianoKey key: piano.getPianoKeys()) {
            if (key.getColor())
                if (key.doesContainPoint(point))
                    return key.getIndex();
        }

        // black second
        for (PianoKey key: piano.getPianoKeys()) {
            if (!key.getColor())
                if (key.doesContainPoint(point))
                    return key.getIndex();
        }

        return KEY_NOT_FOUND;
    }

    @Override
    public int getNote(int keyCode) {
        return 0;
    }

    @Override
    public boolean isNote(int keyCode) {
        return false;
    }

    @Override
    public void pianoKeyPressed(int keyCode) {
        if (keyCode != KEY_NOT_FOUND)
            piano.setKeyState(keyCode,true);
    }

    @Override
    public void pianoKeyReleased(int keyCode) {
        if (keyCode != KEY_NOT_FOUND)
            piano.setKeyState(keyCode, false);
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
            piano.setKeyState(currentHovered, false);
        }
        currentHovered = index;
    }


    // Handles mouse press and release on piano keys.
    private class PianoMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            pianoKeyPressed(getHoveredKeyIndex(e.getPoint()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pianoKeyReleased(getHoveredKeyIndex(e.getPoint()));
        }
    }


}
