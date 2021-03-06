package ru.mipt.cs.easypiano.main;

//Dima

import ru.mipt.cs.easypiano.piano.*;
import ru.mipt.cs.easypiano.recognition.aggregation.fromcpp.Client;

import javax.swing.*;
import java.awt.*;

public class ExerciseFrame extends JFrame {

    private static String nameOfFile;
    private AuxiliaryPanel auxiliaryPanel;
    protected Piano piano;
    private ru.mipt.cs.easypiano.graphics.videolesson.Canvas canvas;


    private JPanel createContentPanel() {
        GridBagConstraints format = new GridBagConstraints();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());

        this.piano = new Piano();
        Control mouseControl = new MouseControl(piano);
        Control keyboardControl = new KeyboardControl(piano);
        Control clientControl = new ClientControl(piano, (KeyboardControl)keyboardControl);
        piano.addControl(mouseControl);
        piano.addControl(keyboardControl);
        piano.addControl(clientControl);
        format.gridx = 0;
        format.gridy = 1;
        contentPanel.add(piano, format);
        piano.requestFocusInWindow();

        this.canvas = new ru.mipt.cs.easypiano.graphics.videolesson.Canvas(nameOfFile, piano);
        format.gridx = 0;
        format.gridy = 0;
        contentPanel.add(canvas, format);
        /*
        this.auxiliaryPanel = new AuxiliaryPanel(piano);
        format.gridx = 1;
        format.gridy = 0;
        format.gridheight = 2;
        contentPanel.add(auxiliaryPanel, format);
        */
		
		return contentPanel;
	}

	public ExerciseFrame(String nameOfFile) {
        super("Easy Piano");
        this.nameOfFile = nameOfFile;
		add(createContentPanel());
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
    public ExerciseFrame(String nameOfFile, int n){
        super("Easy Piano");
        this.nameOfFile = nameOfFile;
        add(createContentPanel());
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        (new Thread(){
            public void run(){
                Client client = new Client(piano.getControlList().get(2));
            }
        }).start();
    }
}
