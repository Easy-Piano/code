package ru.mipt.cs.easypiano.test.dima;

//Dima

import ru.mipt.cs.easypiano.piano.ImageManager;
import ru.mipt.cs.easypiano.piano.MidiManager;

public class VirtualPiano {

	public static void main(String[] args) {
		// init resources and systems
		ImageManager.initFactory();
		//MusicResource.initFactory();
		MidiManager.init();
		// intro message box
		/*JOptionPane.showMessageDialog(null, "Tips:\nSpacebar = pedal",
				"Ready?", JOptionPane.INFORMATION_MESSAGE);*/
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
