package ru.mipt.cs.easypiano.graphics.videolesson.piano.keyboard;


import javax.swing.*;
import java.awt.*;

public class PanelPictureFrame extends JFrame {

       public PanelPictureFrame() {
           initComponents();
       }

       private void initComponents() {
            picturePanel1 = new PicturePanel();
            jPanel1 = new javax.swing.JPanel();
            picturePanel2 = new PicturePanel();


            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            picturePanel1.setLayout(new BorderLayout());
            picturePanel1.setImageFile(new java.io.File("G:\\image.jpg"));
            picturePanel2.setImageFile(new java.io.File("G:\\image.png"));
            jPanel1.setLayout(new java.awt.GridLayout());
            jPanel1.setOpaque(false);
            picturePanel1.add(jPanel1, java.awt.BorderLayout.NORTH);
            picturePanel2.add(jPanel1, BorderLayout.SOUTH);
            getContentPane().add(picturePanel1, java.awt.BorderLayout.CENTER);
            pack();
        }




        private javax.swing.JPanel jPanel1;
        private PicturePanel  picturePanel2;
        private PicturePanel picturePanel1;
 }

