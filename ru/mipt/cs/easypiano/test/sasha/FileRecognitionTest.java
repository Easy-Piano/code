package ru.mipt.cs.easypiano.test.sasha;
//SASHA

import ru.mipt.cs.easypiano.recognition.analysis.MonoRecognizer;

/**
 * Created by 1 on 03.05.2014.
 */
public class FileRecognitionTest implements Test {//deprecated approach!
    /*
        takes wav file and tries to recognize it, then plays what it got in MIDI format.
     */
    public static void main(String[] args){
        String s=resoursePath;//path from C:// to out actually, not src
        //s+="upr_02.wav";
        //s+="1oct(2).wav";
        s+="ritm02.wav";
        MonoRecognizer MR = new MonoRecognizer();
        MR.recognizeFromFile(s);
    }
}
