package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.Croissant;
import ru.mipt.cs.easypiano.graphics.videolesson.resourse.LayoutConstant;
import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel{
    private Croissant c = new Croissant();
    private int width;
    private int height;
    private  boolean running = true;

    public Canvas (){


        //layouting
        this.width = LayoutConstant.CANVAS_MINI_WIDTH;
        this.height = LayoutConstant.CANVAS_MINI_HEIGHT;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);

        c.start();
    }

    public void  paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D ) g;
        // for(Rectangle f: Rectangle.moveL  ist) f.draw(g2);
        c.draw(g2);
    }
}
