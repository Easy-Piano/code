package ru.mipt.cs.easypiano.piano;

//Dima

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AuxiliaryPanel extends JPanel {

    private JButton startButton;

    public AuxiliaryPanel(final Piano piano, final KeyboardControl keyboardControl) {
        this.startButton = new JButton();

        startButton.addActionListener(new ActionEvent() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInputAnalyser analyser = new UserInputAnalyser(piano, 0, keyboardControl);
            }
        });
    }
}
