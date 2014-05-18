package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.resourse.LayoutConstant;
import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private int width;
    private int height;

    private Thread repaintThread;

    public Canvas (){

        //layouting
        this.width = LayoutConstant.canvasMinWidth;
        this.height = LayoutConstant.canvasMinHeight;
        setSize(width, height);

    }




}
