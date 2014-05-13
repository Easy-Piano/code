package test.sasha;
//SASHA
import sound.MidiPlayer;

import javax.sound.midi.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 1 on 03.05.2014.
 */
public class MidiMusicTest implements Test{
    /*
       two ways of playing MIDI, just tested how it works
     */
    public static void main(String[] args){
        String s=resoursePath;
        s+="yiruma-river_flows_in_you.mid";
        //first option
        int notes[][] = {{470, 81}, {230, 80}, {470, 81}, {250, -1}, {230, 80}, {470, 81}, {230, 69}, {230, 76}, {470, 81}, {230, 69}, {470, 74}, {470, 73}, {470, 74}, {470, 76}, {470, 73}, {470, 71}, {970, -1}, {230, 69}, {230, 68}, {470, 69}, {730, -1}, {230, 64}, {230, 69}, {230, 71}, {470, 73}, {970, -1}, {230, 73}, {230, 74}, {470, 76}, {730, -1}, {230, 69}, {230, 74}, {230, 73}, {470, 71}, {1450, -1}, {470, 81}, {230, 80}, {470, 81}, {250, -1}, {230, 80}, {470, 81}, {230, 69}, {230, 76}, {470, 81}, {230, 69}, {470, 74}, {470, 73}, {470, 74}, {470, 76}, {470, 73}, {470, 71}, {970, -1}, {230, 69}, {230, 68}, {470, 69}, {730, -1}, {230, 64}, {230, 69}, {230, 71}, {470, 73}, {970, -1}, {230, 73}, {230, 74}, {470, 76}, {730, -1}, {230, 69}, {230, 74}, {230, 73}, {470, 71}, {250, -1}};
        MidiPlayer midiPlayer = new MidiPlayer();
        for (int[] note : notes) {
            if (note[1] != -1) {
                midiPlayer.playSound(0, note[0], 80, note[1]);
            } else {
                try {
                    Thread.sleep(note[0]);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SashaMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        midiPlayer.close();
        //second option
        try {
            Sequence sequence = MidiSystem.getSequence(new File(s));
            Synthesizer synth;
            MidiChannel[] channels;

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(1);//Acoustic Royal
            synth.close();
        } catch (Exception e) {
            System.err.println(e);
        }


    }
}
