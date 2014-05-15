package ru.mipt.cs.graphics.visualisation;
//SASHA

import ru.mipt.cs.recognition.aggregation.Recording;
import ru.mipt.cs.sound.WavSound;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by 1 on 18.04.2014.
 */
public class RecordDialog extends JFrame{
    private JButton recButton;
    private JButton playButton;
    private JButton reservedButton;
    private boolean playpressed;
    private boolean recpressed;
    private Recording rt;
    private File f;
    private WavSound snd;
    private boolean fileLinked;
    public RecordDialog() {
        rt=new Recording();
        f=rt.getNewFile();
        playpressed=false;
        recpressed=false;
        fileLinked=false;
        recButton = new JButton();
        playButton = new JButton();
        reservedButton = new JButton();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("audio ru.mipt.cs.recognition inception");
        recButton.setText("StartRec");
        playButton.setText("Play");
        reservedButton.setText("Reserved");
        recButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                recButtonActionPerformed(evt);
            }
        });
        playButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        reservedButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reservedButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(recButton)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(reservedButton)
                                        .addComponent(playButton)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reservedButton)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(recButton)
                                        .addComponent(playButton)
                        )
        );
        pack();
    }

    private void recButtonActionPerformed(ActionEvent evt){
        if (recpressed){
            rt.stopRecording();
            recButton.setText("StartRec");
            recpressed=false;
        }else{
            rt.startRecording();
            recButton.setText("StopRec");
            recpressed=true;
        }
    }
    private void playButtonActionPerformed(ActionEvent evt) {
        if (playpressed){
            playButton.setText("Play");
            snd.stop();
            playpressed=false;
        }else{
            if (!(fileLinked)){
                snd=new WavSound(f);
            }
            snd.play();
            playButton.setText("Stop");
            playpressed=true;
        }
    }
    private void reservedButtonActionPerformed(ActionEvent evt) {
    }
}
