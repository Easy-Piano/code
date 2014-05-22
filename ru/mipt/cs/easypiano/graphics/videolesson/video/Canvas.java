package ru.mipt.cs.easypiano.graphics.videolesson.video;

// Ivan

import ru.mipt.cs.easypiano.graphics.videolesson.Croissant;
import ru.mipt.cs.easypiano.recognition.aggregation.frommidi.NotesExtractor;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Canvas extends JPanel{
    private Vector croissants;//Croissant c = new Croissant();
    private int width;
    private int height;
    private  boolean running = true;
    private String nameOfFile;
    public Canvas (String fileName){
        nameOfFile=fileName;
        //layouting
        nameOfFile=fileName;
        this.width = VideoConstants.CANVAS_MINI_WIDTH;
        this.height = VideoConstants.CANVAS_MINI_HEIGHT;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);
        /*
        c.setDuration(200);
        c.setNote(37);
        c.setStartTick(0);
        c.start();
        */
        croissants = new Vector();
        NotesExtractor NE = new NotesExtractor(nameOfFile);
        Vector durations = NE.getDurations();
        Vector notes = NE.getNotes();
        Vector startTimes = NE.getStartTimes();
        int n = durations.size();
        for (int i=0; i<n; i++){
            croissants.add(new Croissant());
            ((Croissant)croissants.get(i)).setDuration((int)(long)durations.get(i));
            ((Croissant)croissants.get(i)).setNote((int)notes.get(i));
            ((Croissant)croissants.get(i)).setStartTick((int)(long) startTimes.get(i));
        }
        startMotion();
        /*for (int i=0; i<n; i++){
            System.out.println("note is "+notes.get(i));
        }*/
    }
    private void startMotion(){
        int n = croissants.size();
        for (int i=0; i<n; i++){
            ((Croissant)croissants.get(i)).start();
        }
    }

    public void  paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D ) g;
        int n = croissants.size();
        for (int i=0; i<n; i++){
            ((Croissant)croissants.get(i)).draw(g2);
        }
        //c.draw(g2);
    }
}
