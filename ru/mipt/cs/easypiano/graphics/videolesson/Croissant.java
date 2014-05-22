package ru.mipt.cs.easypiano.graphics.videolesson;

import java.awt.*;

import ru.mipt.cs.easypiano.graphics.videolesson.video.VideoConstants;

//IVAN
public class Croissant implements Runnable{
    private int note;
    private int startTick;
    private int duration;
    // color = 1 - "WHITE"
    // color = 2 - "BLACK"
    private int color;
    private int x;
    private int y;

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

    private void findOutColor (){
        switch (note){
            case 36:case 38:case 40:case 41:case 43:case 45:case 47:color = 1; break;
            case 48:case 50:case 52:case 53:case 55:case 57:case 59:color = 1; break;
            case 60:case 62:case 64:case 65:case 67:case 69:case 71:color = 1; break;
            case 72:case 74:case 76:case 77:case 79:case 81:case 83:color = 1; break;
            case 84:case 86:case 88:case 89:case 91:case 93:case 95:color = 1; break;
            case 96:case 98:case 100:case 101:case 103:case 105:case 107:color = 1; break;
            case 37:case 39:case 42:case 44:case 46: color =0; break;
            case 49:case 51:case 54:case 56:case 58: color =0; break;
            case 61:case 63:case 66:case 68:case 70: color =0; break;
            case 73:case 75:case 78:case 80:case 82: color =0; break;
            case 85:case 87:case 90:case 92:case 94: color =0; break;
            case 97:case 99:case 102:case 104:case 106: color =0; break;

        }

    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        if (color == 1){
            g.drawRoundRect(x, y, VideoConstants.CANVAS_WHITE_KEY_WIDTH, 100, 10, 10);
            g.fillRoundRect(x, y, VideoConstants.CANVAS_WHITE_KEY_WIDTH, 100, 10, 10);
        }
        else{
            g.drawRoundRect(x, y, VideoConstants.CANVAS_BLACK_KEY_WIDTH ,60, 10, 10);
            g.fillRoundRect(x, y, VideoConstants.CANVAS_BLACK_KEY_WIDTH ,60, 10, 10);
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
