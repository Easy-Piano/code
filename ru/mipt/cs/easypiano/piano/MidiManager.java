package ru.mipt.cs.easypiano.piano;

// Dima
// A class that handles MIDI music playing. This class takes care of the
// Sequencer, provides functions to play and stop music, and to play a single
// note on a special channel.
// This class will take control of the default MIDI Sequencer and Synthesizer
// while the program is running.
// Any MIDI sequence played must not occupy the last channel (highest numbered)
// since that channel is used for synthesizing.
// Implements singleton pattern.

import ru.mipt.cs.easypiano.utils.*;

import javax.sound.midi.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MidiManager {

	private static final String instrumentFileName = "ru/mipt/cs/easypiano/resources/instruments.txt";
	
	// single instance
	private static MidiManager midiManager;
	private static final int NUM_INSTRUMENT = 128;
	
	// default values for synthesizer
	private static final int SYNTH_NOTE_VELOCITY = 120;
	private static final int SYNTH_INSTRUMENT = 0; // acoustic grand piano

	// pedal
	private static final int PEDAL_ID = 64;
	private static final int PEDAL_ON = 127;
	private static final int PEDAL_OFF = 0;
	
	private Sequencer sequencer;
	private Synthesizer synthesizer;
	private MidiChannel synthChannel;
	private int synthInstrument;
	
	private static List<String> instrumentNames;
	
	public static MidiManager getInstance() {
		if (midiManager == null)
			midiManager = new MidiManager();
		return midiManager;
	}

	public static void init() {
		midiManager = new MidiManager();
		initInstrumentNames();
	}

	private static void initInstrumentNames() {
		instrumentNames = new ArrayList<String>();
		try {
			URL url = Utilities.getResourceURL(MidiManager.instrumentFileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// Read line by line. Empty line or null line indicates an end
			while (true) {
				String line = in.readLine();
				if (line == null || line.trim().isEmpty()) {
					break;
				} else {					
					instrumentNames.add(line);
				}
			}
			in.close();

		} catch (IOException e) {
			ErrorHandler.display("Cannot read MIDI instrument names");
		}

		if (instrumentNames.size() != MidiManager.NUM_INSTRUMENT) {
			ErrorHandler.display("Wrong MIDI instrument names data");
		}
		
		// TODO: workaround. Append empty string until size is at least 128
		while (instrumentNames.size() < MidiManager.NUM_INSTRUMENT)
			instrumentNames.add(""); // default: empty string

	}

    // Returns the name of the instrument with specific id.
	public static String getInstrumentName(int id) {
		return MidiManager.instrumentNames.get(id);
	}

	private MidiManager() {
		try {
			// init sequencer
            // TODO: Error if sequencer device is not supported. Inform user.
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// init synthesizer
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			
			// get channel for synthesizing: the highest numbered channel.  sets it up
			MidiChannel[] channels = synthesizer.getChannels();
			synthChannel = channels[channels.length - 1];
			setSynthInstrument(MidiManager.SYNTH_INSTRUMENT);
			
		} catch (MidiUnavailableException e) {
			ErrorHandler.display("Cannot play MIDI music");
			sequencer = null; //sequencer can be null.
		}
		
	}

    // Close all
	@Override
	protected void finalize() throws Throwable {
		if (synthesizer != null)
			synthesizer.close();
		if (sequencer != null)
			sequencer.close();
		super.finalize();
	}

    // Plays a single note with a default instrument in the synthesizer channel.
	public void playNote(int pitch) {
		synthChannel.noteOn(pitch, MidiManager.SYNTH_NOTE_VELOCITY);
	}

    //Stop a single note with a default instrument in the synthesizer channel.
	public void stopNote(int pitch) {
		synthChannel.noteOff(pitch, 127);
	}

    // Pedal on in synthesizer.
	public void pedalDown() {
		synthChannel.controlChange(MidiManager.PEDAL_ID, MidiManager.PEDAL_ON);
	}

    // Pedal off in synthesizer.
	public void pedalUp() {
		synthChannel.controlChange(MidiManager.PEDAL_ID, MidiManager.PEDAL_OFF);
	}
	
    // Sets a new instrument for the synthesizer and change the instrument for real.
	public void setSynthInstrument(int synthInstrument) {
		// TODO: error checking
		this.synthInstrument = synthInstrument;
		synthChannel.programChange(synthInstrument);
	}

	public void decSynthInstrument() {
		if (synthInstrument > 0) {
			setSynthInstrument(synthInstrument - 1);
		} else {
			setSynthInstrument(MidiManager.NUM_INSTRUMENT - 1);
		}
	}
	
	public void incSynthInstrument() {
		if (synthInstrument < MidiManager.NUM_INSTRUMENT - 1) {
			setSynthInstrument(synthInstrument + 1);
		} else {
			setSynthInstrument(0);
		}
	}
	
	public int getSynthInstrument() {
		return synthInstrument;
	}

	public String getInstrumentName() {
		return MidiManager.getInstrumentName(synthInstrument);
	}
}
