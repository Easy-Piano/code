package ru.mipt.cs.easypiano.graphics.visualisation.exeptionFrames;

import ru.mipt.cs.easypiano.graphics.visualisation.StartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class ErrorFrame extends JFrame{
    private JButton okButton;
    private JPanel errorPanel;
    private JLabel Label;

    public ErrorFrame(String text){
        super ("ERROR !");

        Label.setText(text);
        setContentPane(errorPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }
}
