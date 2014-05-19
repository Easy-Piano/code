package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.resourse.LayoutConstant;
import javax.swing.*;

public class Canvas extends JPanel {

    private Thread repaintThread;

    public Canvas (){

        //layouting
        int width = LayoutConstant.canvasMinWidth;
        int height = LayoutConstant.canvasMinHeight;
        setSize(width, height);

    }




}
