package ru.mipt.cs.easypiano.sound;
//SASHA
/**
 * Created by 1 on 27.04.2014.
 */
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MidiPlayer {

    private MidiChannel[] channels = null;
    private Synthesizer synth = null;

    public MidiPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(1);//Acoustic Royal
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        synth.close();
    }

    public void playSound(int channel, int duration, int volume, int... notes) {
        for (int note : notes) {
            channels[channel].noteOn(note, volume);
        }
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int note : notes) {
            channels[channel].noteOff(note);
        }
    }
    public void playSound(int n,Vector notes,Vector times){
        int size = notes.size();
        for (int i=0; i<size; i++) {
            if (((long)(times.get(i)))!=-1){
                playSound(n,(int) (long) (times.get(i)) , 80, (int)(notes.get(i)));
            }else{
                try {
                    Thread.sleep((int)(long) (times.get(i)));
                } catch (InterruptedException ex) {
                    Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void playSound(Vector notes,Vector times){
        int size = notes.size();
        for (int i=0; i<size; i++) {
            if (((long)(times.get(i)))!=-1){
                playSound(0,(int) (long) (times.get(i)) , 80, (int)(notes.get(i)));
            }else{
                try {
                    Thread.sleep((int)(long) (times.get(i)));
                } catch (InterruptedException ex) {
                    Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void playSound(int channel, int[][] notes){
        for (int[] note : notes) {
            if (note[1] != -1) {
                playSound(0, note[0], note[2], note[1]);
            } else {
                try {
                    Thread.sleep(note[0]);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}


