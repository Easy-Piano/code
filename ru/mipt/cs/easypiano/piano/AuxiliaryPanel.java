package ru.mipt.cs.easypiano.piano;

//Dima

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuxiliaryPanel extends JPanel {

    private JButton startButton;

    public AuxiliaryPanel(final Piano piano) {
        this.startButton = new JButton();

        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInputAnalyser analyser;
                analyser = new UserInputAnalyser(piano, 0, piano.controlList.get(0));
            }
        });
    }
}
