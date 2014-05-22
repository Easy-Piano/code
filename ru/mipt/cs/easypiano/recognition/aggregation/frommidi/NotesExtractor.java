package ru.mipt.cs.easypiano.recognition.aggregation.frommidi;
//Sasha + Ivan
import ru.mipt.cs.easypiano.recognition.analysis.Notes;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class NotesExtractor {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    private Vector vecNotes;
    private Vector vecStartTimes;//time when note starts, in milliseconds.
    private Vector vecDurations;//duration of note
    private double ticksPerSecond;
    private NoteInfo ni;
    public NotesExtractor(String fName){
        vecNotes = new Vector();
        vecStartTimes = new Vector();
        vecDurations = new Vector();
        ni = new NoteInfo();
        decodeFile(fName);
        show();
    }
    public class NoteInfo{
        /*
        example of usage class in main
        NoteInfo NI = (new NotesExtractor("path_to_file.mid")).getNoteInfo();
        Vector startTimes = NI.getSTvec(myNoteNumber);
        Vector endTimes = NI.getETvec(myNoteNumber);
         */
        protected Vector[] startTime;
        protected Vector[] endTime;
        protected NoteInfo(){
            startTime = new Vector[Notes.NOTES_QUANTITY];
            endTime = new Vector[Notes.NOTES_QUANTITY];
            int n = vecNotes.size();
            for (int i=0; i<n; i++){
                startTime[(int) vecNotes.get(i)].add(vecStartTimes.get(i));
                endTime[(int) vecNotes.get(i)].add((long)vecStartTimes.get(i)+(long)vecDurations.get(i));
            }
        }
        public Vector getSTvec(int noteNumber){
            if ((noteNumber < 0)||(noteNumber > Notes.NOTES_QUANTITY-1)) throw new IndexOutOfBoundsException();
            return startTime[noteNumber];
        }
        public Vector getETvec(int noteNumber){
            if ((noteNumber < Notes.FIRST_NOTE)||(noteNumber > Notes.LAST_NOTE)) throw new IndexOutOfBoundsException();
            return endTime[noteNumber];
        }
    }
    public NoteInfo getNoteInfo() {
        return ni;
    }
    public void show(){
        int n=vecNotes.size();
            /*int n1=vecDurations.size();
            int n2=vecStartTimes.size();
            System.out.println("note size is "+n+" duration size is "+n1+" startTime size is "+n2);*/
        for (int i=0; i<n; i++){
            System.out.println("note: "+vecNotes.get(i)+" duration: "+vecDurations.get(i)+" startTime: "+
                    vecStartTimes.get(i));
        }
    }
    public Vector getNotes(){
        return vecNotes;
    }
    public Vector getStartTimes(){
        return vecStartTimes;
    }
    public Vector getDurations(){
        return vecDurations;
    }
    private void decodeFile (String fName) {
        Sequence sequence = null;
        try {
            sequence = MidiSystem.getSequence(new File(fName));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Track track = sequence.getTracks()[1];

        ticksPerSecond = ((double) (1000 * sequence.getTickLength()) / (double) (sequence.getMicrosecondLength()));
        System.out.println(ticksPerSecond + " ticks per second");
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            //System.out.print("@" + event.getTick() + " ");
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                //System.out.print("Channel: " + sm.getChannel() + " ");
                if (sm.getCommand() == NOTE_ON && sm.getData2() != 0) {
                    int key = sm.getData1();
                    vecNotes.add(key);
                    vecStartTimes.add((long) ((double) event.getTick() / ticksPerSecond));
                    //int velocity = sm.getData2();
                } else if ((sm.getCommand() == NOTE_OFF) || (sm.getCommand() == NOTE_ON && sm.getData2() == 0)) {
                    int key = sm.getData1();
                    vecDurations.add((long) ((double) event.getTick() / ticksPerSecond) - (long) vecStartTimes.get(findLastMention(key)));
                }
            }
        }
    }
    private int findLastMention(int key){
        int n=vecNotes.size()-1;
        while (((int)(vecNotes.get(n)))!=key){
            n--;
        }
        return n;
    }
}
