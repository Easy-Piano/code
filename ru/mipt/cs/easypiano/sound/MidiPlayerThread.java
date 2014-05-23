package ru.mipt.cs.easypiano.sound;

import java.util.Vector;

/**
 * Created by 1 on 23.05.2014.
 */
public class MidiPlayerThread extends Thread {
    private MidiPlayer MP;
    private Vector vecNotes;
    private Vector vecDurations;
    public MidiPlayerThread(MidiPlayer MP,Vector vnotes, Vector vecdurations){
        this.MP=MP;
        vecNotes=vnotes;
        vecDurations=vecdurations;
    }
    public void run(){
        MP.playSound(0,vecNotes,vecDurations);
    }
}
