package ru.mipt.cs.easypiano.test.sasha;
//SASHA
import ru.mipt.cs.easypiano.sound.PlaySoundThread;

/**
 * Created by 1 on 04.05.2014.
 */
public class WavMusicTest implements Test{
    /*
    plays two compositions at a time
     */
    public static void main(String args[]){
        PlaySoundThread[] t =new PlaySoundThread[2];
        String s1=resoursePath;
        String s2=resoursePath;
        s1+="Chopin.wav";
        s2+="ritm02.wav";

        t[0]=new PlaySoundThread(s1);
        t[1]=new PlaySoundThread(s2);

        t[0].start();//runs in new thread
        try{
            Thread.currentThread().sleep(5000);
            t[1].start();
            t[0].join();//waits till t[0] finishes
        }catch(InterruptedException exc ){
        };
    }
}
