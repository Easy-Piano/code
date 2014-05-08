package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Musician {
    private MidiChannel[] channels = null;
    private Synthesizer synth = null;

    public Musician() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(1);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Musician.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Musician.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int note : notes) {
            channels[channel].noteOff(note);
        }
    }
}
