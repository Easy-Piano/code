package test.sasha;
//SASHA
import recognition.analysis.Notes;

/**
 * Created by 1 on 26.04.2014.
 */
public class SashaMain {//shows spectrum finally
    public static void main(String[] args){
        /*
        here I test on my computer, this file is for only me
         */
        String s=SashaMain.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\easypiano\\resourses\\";
        s+="notes\\note";
        String ext=".wav";
        for (int i=60;i>=0;i--){
            Renamer.rename(s+((Integer)i).toString()+ext,s+((Integer)(i+ Notes.MIDI_OFFSET+Notes.V_PIANO_OFFSET)).toString()+ext);
        }
    }
}
