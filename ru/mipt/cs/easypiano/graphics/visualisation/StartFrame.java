package ru.mipt.cs.easypiano.graphics.visualisation;

import ru.mipt.cs.easypiano.graphics.videolesson.VideoFrame;
import ru.mipt.cs.easypiano.graphics.videolesson.video.extention.FileNameUtils;
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
    private String typeOfFile;

    public StartFrame(){

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
                    typeOfFile = FileNameUtils.getExtension(nameOfFile);
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameOfFile != null){
                    if (typeOfFile.equals("mid")){
                        VideoFrame videoFrame = new VideoFrame(nameOfFile);
                        videoFrame.setVisible(true);
                    }
                    else{
                        System.out.println(typeOfFile);
                        ErrorFrame errorFrame = new ErrorFrame("The extention of file is wrong");
                    }
                }
                else {

                    ErrorFrame errorFrame = new ErrorFrame("You did't choose file");
                }
            }
        });

        setVisible(true);
    }

    public String getNameOfFile (){
        return nameOfFile;
    }

}
