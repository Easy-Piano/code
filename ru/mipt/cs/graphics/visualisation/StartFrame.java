package ru.mipt.cs.graphics.visualisation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Asus on 16.05.14.
 */
public class StartFrame extends JFrame{
    private JPanel startPanels;
    private JButton chooseFileButton;
    private JButton startButton;

    public StartFrame(){
        super ("Video Exercise");

        setContentPane(startPanels);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
