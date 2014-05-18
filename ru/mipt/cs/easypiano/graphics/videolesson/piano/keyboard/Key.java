package ru.mipt.cs.easypiano.graphics.videolesson.piano.keyboard;

/**
 * Created by Asus on 18.05.14.
 */
public class Key {
    private boolean isOn = false;
    private String color;

    public String getColor() {
        return color;
    }
    public boolean getIsOn(){
        return isOn;
    }

    public void setColor(String newColor){
        color = newColor;
    }

    public void setOn(){
        isOn = true;
    }
    public void setOff(){
        isOn = false;
    }
}
