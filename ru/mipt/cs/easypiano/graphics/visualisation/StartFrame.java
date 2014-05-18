package ru.mipt.cs.easypiano.graphics.visualisation;

import ru.mipt.cs.easypiano.graphics.videolesson.VideoFrame;
import ru.mipt.cs.easypiano.graphics.visualisation.exeptionFrames.ErrorFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Asus on 16.05.14.
 */
public class StartFrame extends JFrame{
    private JPanel startPanels;
    private JButton chooseFileButton;
    private JButton startButton;
    private String nameOfFile;

    public StartFrame(){
        super ("Video Exercise");

        setContentPane(startPanels);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        chooseFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showDialog(null, "Выбрать файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    nameOfFile = file.getAbsolutePath();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameOfFile != null){
                    VideoFrame videoFrame = new VideoFrame();
                    videoFrame.setVisible(true);
                }
                else {
                    ErrorFrame errorFrame = new ErrorFrame();
                }
            }
        });

        setVisible(true);
    }

    public String getNameOfFile (){
        return nameOfFile;
    }
}
