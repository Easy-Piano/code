package ru.mipt.cs.easypiano.piano;

//Dima
//Encapsulates a MIDI music.  Contains a factory getter method to get a initialized music resource.
//Implements factory pattern.

import ru.mipt.cs.easypiano.utils.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.IOException;

public class MusicResource {
	// factory
	private static MusicResource[] musicResources;
	
	private String fileName;
	private Sequence sequence;

    private MusicResource(String fileName) {
        this.fileName = fileName;

        // populates the sequence for quick retrieval / playing later
        try {
            this.sequence = MidiSystem.getSequence(Utilities.getResourceURL(fileName));

        } catch (IOException e) {
            ErrorHandler.display("File " + fileName + " is missing");
            // recovery
            sequence = null;

        } catch (InvalidMidiDataException e) {
            ErrorHandler.display("File " + fileName + " is corrupted");
            // recovery
            sequence = null;
        }
    }

    // Initializes the factory.
	public static void initFactory() {
		musicResources = new MusicResource[] {
				//new MusicResource("ru/mipt/cs/easypiano/piano/resources/music/background.mid")
		};
	}

	// Returns the MusicResource corresponding to the specified id.
	public static MusicResource getInstance(int id) {
		// TODO: assumed no array index error
		return MusicResource.musicResources[id];		
	}
	
	// Returns the sequence of the MusicResource corresponding to the specified id.
    // If id is invalid, returns null.
	public static Sequence getSequence(int id) {
        MusicResource mr = MusicResource.getInstance(id);
        if (mr == null) {
            return null;
        } else {
            return mr.getSequence();
        }
    }

	public Sequence getSequence() {
		return sequence;
	}

	public String getFileName() {
		return fileName;
	}

    /*
	public void play() {
		MusicManager.getInstance().play(sequence);
	}
	*/
}
