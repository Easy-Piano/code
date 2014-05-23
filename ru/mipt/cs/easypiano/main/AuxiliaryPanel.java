package ru.mipt.cs.easypiano.main;

//Dima

import ru.mipt.cs.easypiano.piano.Piano;

import javax.swing.*;

public class AuxiliaryPanel extends JPanel {

    private JButton startButton;
    private Piano piano;

    @Override
    public void repaint() {
        super.repaint();
    }

    public AuxiliaryPanel(Piano p) {
        this.startButton = new JButton("S T A R T");
        add(startButton);
        this.piano = p;
        this.piano.requestFocusInWindow();
        /*
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               UserInputAnalyser analyser = new UserInputAnalyser(100000, piano);
                System.out.println("new thread");
                new Thread(analyser).start();
                //analyser.run();
            }
        });*/
    }
}
