package ru.mipt.cs.easypiano.test.dima;

//Dima

import ru.mipt.cs.easypiano.piano.ImageManager;
import ru.mipt.cs.easypiano.piano.MidiManager;

import javax.swing.*;

public class VirtualPiano {

	public static void main(String[] args) {
		// use current platform's look and feel 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}

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
