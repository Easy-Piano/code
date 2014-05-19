package ru.mipt.cs.easypiano.test.dima;

//Dima

import ru.mipt.cs.easypiano.piano.ImageResource;
import ru.mipt.cs.easypiano.piano.MusicManager;

import javax.swing.*;

public class VirtualPiano {

	public static void main(String[] args) {
		// use current platform's look and feel 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}

		// init resources and systems
		ImageResource.initFactory();
		//MusicResource.initFactory();
		MusicManager.init();
		
		// intro message box
		/*JOptionPane.showMessageDialog(null, "Tips:\nSpacebar = pedal",
				"Ready?", JOptionPane.INFORMATION_MESSAGE);*/

		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
