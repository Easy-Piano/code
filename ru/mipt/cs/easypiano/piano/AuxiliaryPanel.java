package ru.mipt.cs.easypiano.piano;

//Dima

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuxiliaryPanel extends JPanel {

    private JButton startButton;
    private Piano piano;

    @Override
    public void repaint() {
        super.repaint();
    }

    public AuxiliaryPanel(Piano p) {
        this.startButton = new JButton("S T A R T");
        this.piano = p;
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInputAnalyser analyser = new UserInputAnalyser(100000, piano);
                System.out.println("new thread");
                new Thread(analyser).start();
                //analyser.run();
            }
        });
    }
}
