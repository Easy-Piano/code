package ru.mipt.cs.easypiano.test.dima;

//Dima

import ru.mipt.cs.easypiano.piano.AuxiliaryPanel;
import ru.mipt.cs.easypiano.piano.Control;
import ru.mipt.cs.easypiano.piano.KeyboardControl;
import ru.mipt.cs.easypiano.piano.Piano;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel createContentPanel() {
        GridBagConstraints format = new GridBagConstraints();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());

        Piano piano = new Piano();
        Control keyboardControl= new KeyboardControl(piano);
        Control mouseControl = new KeyboardControl(piano);
        piano.addControl(keyboardControl);
        piano.addControl(mouseControl);
        format.gridx = 0;
        format.gridy = 1;
		contentPanel.add(piano, format);

        AuxiliaryPanel auxiliaryPanel = new AuxiliaryPanel(piano);
        format.gridx = 1;
        format.gridy = 0;
        format.gridheight = 2;
        contentPanel.add(auxiliaryPanel, format);
		
		return contentPanel;
	}

	public MainFrame() {
		super("Easy Piano");
		add(createContentPanel());
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}
}
