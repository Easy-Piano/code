package ru.mipt.cs.easypiano.graphics.videolesson;

import java.awt.*;
import ru.mipt.cs.easypiano.piano.Constants;

//IVAN
public class Croissant implements Runnable{
    private int note;
    private int startTick;
    private int duration;
    private int color=1;
    private int x=0;
    private int y=0;

    public void setDuration(int num){
        duration = num;
    }
    public void setNote (int num){
        note = num;
    }
    public void setStartTick (int num){
        startTick = num;
    }
    public int  getNote (){
        return note;
    }
    public int  getStartTick (){
        return startTick;
    }
    public int  getDuration (){
        return duration;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        if (color == 1){
            g.drawRoundRect(x, y, Constants.WHITE_KEY_WIDTH / 6, 30, 5, 5);
            g.fillRoundRect(x, y, Constants.WHITE_KEY_WIDTH / 6, 30, 5, 5);
        }
        else{
            g.drawRoundRect(x, y, Constants.BLACK_KEY_WIDTH ,30, 5, 5);
            g.fillRoundRect(x, y, Constants.BLACK_KEY_WIDTH ,30, 5, 5);
        }

    }

    public void start (){
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (y < 500){

            y++;
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VideoFrame.canvas.repaint();
        }
    }

}
