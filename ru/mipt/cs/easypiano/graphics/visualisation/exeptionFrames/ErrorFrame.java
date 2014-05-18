package ru.mipt.cs.easypiano.graphics.visualisation.exeptionFrames;

import ru.mipt.cs.easypiano.graphics.visualisation.StartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class ErrorFrame extends JFrame{
    private JButton okButton;
    private JPanel errorPanel;

    public ErrorFrame(){
        super ("ERROR !");

        setContentPane(errorPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Закрыть окно");
            }
        });

        setVisible(true);

    }
}
