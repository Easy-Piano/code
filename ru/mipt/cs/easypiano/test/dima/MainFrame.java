package ru.mipt.cs.easypiano.test.dima;

//Dima

import ru.mipt.cs.easypiano.piano.AuxiliaryPanel;
import ru.mipt.cs.easypiano.piano.Piano;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel createContentPanel() {
        GridBagConstraints format = new GridBagConstraints();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());

        Piano piano = new Piano();
        piano.createKeyboardMouseControl();
        format.gridx = 0;
        format.gridy = 1;
		contentPanel.add(piano, format);

        AuxiliaryPanel auxiliaryPanel = new AuxiliaryPanel(piano, piano.keyboardControl);
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
