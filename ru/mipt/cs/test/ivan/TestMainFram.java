package ru.mipt.cs.test.ivan;


import ru.mipt.cs.graphics.MainFrame;

import java.awt.*;

public class TestMainFram {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainFrame().setVisible(true);
                MainFrame frame = new MainFrame();
                frame.setResizable(false);
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                gd.setFullScreenWindow(frame);
                frame.setVisible(true);
            }
        });
    }

}
