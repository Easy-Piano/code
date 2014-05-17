package ru.mipt.cs.easypiano.graphics.visualisation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame{
    private JButton startButton;
    private JPanel rootPanel;
    private JButton statisticsButton;
    private JButton graphicsButton;
    private JButton aboutButton;

    public MainFrame(){
        super("Easy Piano");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartFrame startFrame = new StartFrame();
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        graphicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        aboutButton.addActionListener(new ActionListener() {
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