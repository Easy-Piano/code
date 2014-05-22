package ru.mipt.cs.easypiano.test.dima.piano;

//Dima

import ru.mipt.cs.easypiano.piano.CustomGridBagConstraints;
import ru.mipt.cs.easypiano.piano.Piano;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel createContentPanel() {
        Piano piano = new Piano();
        piano.createKeyboardMouseControl();
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.add(piano, new CustomGridBagConstraints(0, 0));
		
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
