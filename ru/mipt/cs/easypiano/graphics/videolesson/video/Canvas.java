package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.resourse.LayoutConstant;
import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private Thread repaintThread;

    public Canvas (){

        //layouting
        int width = LayoutConstant.canvasMinWidth;
        int height = LayoutConstant.canvasMinHeight;
        setSize(width, height);

    }

    public void  paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D ) g;
        //for(Rectangle f: Rectangle.moveList) f.draw(g2);
        //p.draw(g2);

    }


}
