package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.Croissant;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel{
    private Croissant c = new Croissant();
    private int width;
    private int height;
    private  boolean running = true;

    public Canvas (){


        //layouting
        this.width = VideoConstants.CANVAS_MINI_WIDTH;
        this.height = VideoConstants.CANVAS_MINI_HEIGHT;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);

        c.start();
    }

    public void  paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D ) g;
        c.draw(g2);
    }
}
