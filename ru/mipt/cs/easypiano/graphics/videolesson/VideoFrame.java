package ru.mipt.cs.easypiano.graphics.videolesson;

import ru.mipt.cs.easypiano.graphics.videolesson.video.util.CustomGridBagConstraints;
import ru.mipt.cs.easypiano.graphics.videolesson.video.Canvas;

import javax.swing.*;
import java.awt.*;

//IVAN
public class VideoFrame extends JFrame {


    public static Canvas canvas;

    private JPanel createContentPanel(){
        canvas = new Canvas();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.add(canvas, new CustomGridBagConstraints(0, 0));

        return contentPanel;
    }

    public VideoFrame(String nameOfFile){
        String nameOfFile1 = nameOfFile;

        setTitle("Visual lesson");

        add(createContentPanel());
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();

        setVisible(false);
        setLocationRelativeTo(null);
    }


}
