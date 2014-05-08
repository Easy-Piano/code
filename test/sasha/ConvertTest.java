package test.sasha;
//SASHA

import recognition.analysis.MonoRecognizer;

/**
 * Created by 1 on 03.05.2014.
 */
public class ConvertTest implements Test {
    /*
        takes wav file and tries to recognize it, then plays what it got in MIDI format.
     */
    public static void main(String[] args){
        String s=resoursePath;//path from C:// to out actually, not src
        s+="upr_02.wav";
        //s+="1oct(2).wav";
        //s+="ritm02.wav";
        MonoRecognizer MR = new MonoRecognizer(s);
    }
}
