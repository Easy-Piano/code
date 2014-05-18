package ru.mipt.cs.easypiano.graphics.videolesson;

import javax.swing.*;

//IVAN
public class VideoFrame extends JFrame {
    public VideoFrame(){ initComponents(); }

    private void initComponents(){
        setTitle("Video EP");
        pack();
        setSize(800, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }


}
