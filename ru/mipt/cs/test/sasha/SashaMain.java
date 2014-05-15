package ru.mipt.cs.test.sasha;
//SASHA
import ru.mipt.cs.recognition.analysis.Notes;

/**
 * Created by 1 on 26.04.2014.
 */
public class SashaMain {//shows spectrum finally
    public static void main(String[] args){
        /*
        here I ru.mipt.cs.test on my computer, this file is for only me
         */
        String s=SashaMain.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\easypiano\\ru.mipt.cs.resourses\\";
        s+="notes\\note";
        String ext=".wav";
        for (int i=60;i>=0;i--){
            Renamer.rename(s+((Integer)i).toString()+ext,s+((Integer)(i+ Notes.MIDI_OFFSET+Notes.V_PIANO_OFFSET)).toString()+ext);
        }
    }
}
