package ru.mipt.cs.easypiano.graphics.videolesson;

// Ivan

import ru.mipt.cs.easypiano.piano.Constants;
import ru.mipt.cs.easypiano.piano.Piano;
import ru.mipt.cs.easypiano.recognition.aggregation.frommidi.NotesExtractor;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Canvas extends JPanel implements Runnable{
    // Video
    public static final int CANVAS_MINI_HEIGHT = 400;
    private long lastTime = System.currentTimeMillis();
    private long delta;
    private Vector croissants;
    private int width;
    private int height;
    private String nameOfFile;
    public Canvas (String fileName, Piano piano){
        nameOfFile=fileName;
        //layouting
        nameOfFile=fileName;
        this.width = piano.getWidth();
        this.height = CANVAS_MINI_HEIGHT;
        setPreferredSize(new Dimension(width, height));
        setBackground(Constants.PIANO_BACKGROUND_COLOR);

        // Take all information about notes in midi file
        croissants = new Vector();
        NotesExtractor NE = new NotesExtractor(nameOfFile);
        Vector durations = NE.getDurations();
        Vector notes = NE.getNotes();
        Vector startTimes = NE.getStartTimes();
        int n = durations.size();
        VideoConstants.NUMBER = notes.size();
        for (int i=0; i<n; i++){
            croissants.add(new Croissant());
            ((Croissant)croissants.get(i)).setDuration((int)(long)durations.get(i));
            ((Croissant)croissants.get(i)).setNote((int)notes.get(i));
            ((Croissant)croissants.get(i)).setStartTick((int)(long) startTimes.get(i));
        }
        startMotion();
    }



    private void startMotion(){
        new Thread(this).start();
    }

    public void  paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2;
        g2 = (Graphics2D ) g;
        int n = croissants.size();
        for (int i=0; i<n; i++){
            ((Croissant)croissants.get(i)).draw(g2, delta);
        }
    }

    @Override
    public void run() {
        while (VideoConstants.NUMBER > 0){
            try {
                Thread.currentThread().sleep(VideoConstants.CANVAS_CROISSANT_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delta = System.currentTimeMillis() - lastTime;
            repaint();
        }
    }
}
