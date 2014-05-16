package ru.mipt.cs.sound;
//SASHA
/**
 * Created by 1 on 04.04.14.
 */
public class PlaySoundThread extends Thread{
    private String name;
    public PlaySoundThread(String n){
        name=n;
    }
    public void run(){
        WavSound.playSound(name).join();
    }
}
