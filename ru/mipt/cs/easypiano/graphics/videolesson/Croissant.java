package ru.mipt.cs.easypiano.graphics.videolesson;

import java.awt.*;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import ru.mipt.cs.easypiano.graphics.videolesson.video.*;
import ru.mipt.cs.easypiano.graphics.videolesson.video.Canvas;

//IVAN
public class Croissant{
    private int note;
    private int startTick;
    private int duration;
    private int sizeY=0;
    private int sizeYMax;
    // color = 1 - "WHITE"
    // color = 0 - "BLACK"
    private  boolean color;
    private int flag1 = 1;
    private int flag2 = 1;
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
    private void  findOutX(){
        int rest, whole;
        rest = (note -36)%12;
        whole = (note - 36)/12;
        if (color){
            x=((whole)*7)*VideoConstants.CANVAS_WHITE_KEY_WIDTH;
            switch (rest){
                case 0: x+=0; break;
                case 2: x+=VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
                case 4: x+=2*VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
                case 5: x+=3*VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
                case 7: x+=4*VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
                case 9: x+=5*VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
                case 11: x+=6*VideoConstants.CANVAS_WHITE_KEY_WIDTH; break;
            }
        }
        else{
            switch (rest){
                case 1: x=(whole*7+1)*VideoConstants.CANVAS_WHITE_KEY_WIDTH - VideoConstants.CANVAS_BLACK_KEY_WIDTH/2; break;
                case 3: x=(whole*7+2)*VideoConstants.CANVAS_WHITE_KEY_WIDTH - VideoConstants.CANVAS_BLACK_KEY_WIDTH/2; break;
                case 6: x=(whole*7+4)*VideoConstants.CANVAS_WHITE_KEY_WIDTH - VideoConstants.CANVAS_BLACK_KEY_WIDTH/2; break;
                case 8: x=(whole*7+5)*VideoConstants.CANVAS_WHITE_KEY_WIDTH - VideoConstants.CANVAS_BLACK_KEY_WIDTH/2; break;
                case 10: x=(whole*7+6)*VideoConstants.CANVAS_WHITE_KEY_WIDTH - VideoConstants.CANVAS_BLACK_KEY_WIDTH/2; break;
            }
        }

    }

    private void findOutSizeYMax(){
        sizeYMax = duration/VideoConstants.CANVAS_CROISSANT_SPEED;
    }

    private void findOutColor (){
        switch (note){
            case 36:case 38:case 40:case 41:case 43:case 45:case 47:color = true; break;
            case 48:case 50:case 52:case 53:case 55:case 57:case 59:color = true; break;
            case 60:case 62:case 64:case 65:case 67:case 69:case 71:color = true; break;
            case 72:case 74:case 76:case 77:case 79:case 81:case 83:color = true; break;
            case 84:case 86:case 88:case 89:case 91:case 93:case 95:color = true; break;
            case 96:case 98:case 100:case 101:case 103:case 105:case 107:color = true; break;
            case 37:case 39:case 42:case 44:case 46: color = false; break;
            case 49:case 51:case 54:case 56:case 58: color = false; break;
            case 61:case 63:case 66:case 68:case 70: color = false; break;
            case 73:case 75:case 78:case 80:case 82: color = false; break;
            case 85:case 87:case 90:case 92:case 94: color = false; break;
            case 97:case 99:case 102:case 104:case 106: color = false; break;
        }
    }

    public void draw(Graphics g, long delta){
        if (flag1 == 1){
            start();
            flag1 = 0;
        }
        if (y < (VideoConstants.CANVAS_MINI_HEIGHT + duration/VideoConstants.CANVAS_CROISSANT_SPEED) && (delta >= startTick) ) {
            if (sizeY < sizeYMax){
                sizeY++;
                y--;
            }
            y++;
        }
        else{
            if (delta > startTick && (flag2 == 1)) {
                VideoConstants.NUMBER --;
                flag2 =0 ;
            }
        }
        if (color){
            g.setColor(Color.BLUE);
            g.drawRoundRect(x, y, VideoConstants.CANVAS_WHITE_KEY_WIDTH, sizeY, 10, 10);
            g.setColor(Color.WHITE);
            g.fillRoundRect(x, y, VideoConstants.CANVAS_WHITE_KEY_WIDTH, sizeY, 10, 10);
        }
        else{
            g.setColor(Color.BLUE);
            g.drawRoundRect(x, y, VideoConstants.CANVAS_BLACK_KEY_WIDTH ,sizeY, 10, 10);
            g.setColor(Color.BLACK);
            g.fillRoundRect(x, y, VideoConstants.CANVAS_BLACK_KEY_WIDTH ,sizeY, 10, 10);
        }

    }

    public void start (){
        findOutColor();
        findOutX();
        findOutSizeYMax();
    }
}
