package ru.mipt.cs.easypiano.test.sasha;
//SASHA

import ru.mipt.cs.easypiano.recognition.analysis.MonoRecognizer;

/**
 * Created by 1 on 26.04.2014.
 */
public class SashaMain {//shows spectrum finally
    public static void main(String[] args){
        /*
        here I test on my computer, this file is for only me
         */
        /*String s=SashaMain.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\easypiano\\resourses\\";
        s+="notes\\note";
        String ext=".wav";
        for (int i=60;i>=0;i--){
            Renamer.rename(s+((Integer)i).toString()+ext,s+((Integer)(i+ Notes.MIDI_OFFSET+Notes.V_PIANO_OFFSET)).toString()+ext);
        }*/
        String s=SashaMain.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\easypiano\\resourses\\";
        /*String ext=".wav";
        String s1=s+"notes\\synthnote";
        String s2=s+"notes\\note";
        for (int i=69; i<77; i++){
            Renamer.rename(s1+((Integer)i).toString()+ext,s2+((Integer)i).toString()+ext);
        }*/
        MonoRecognizer MR = new MonoRecognizer();
        /*Notes notes = Notes.getInstance();
        for (int i=69; i<77; i++){
            notes.specToNull(i);
            MR.fillNotesSpectrum(i);
        }*/
        //MR.realTimeRecognize();
        MR.listenAndPlay(25);
        //s+="yiruma-river_flows_in_you.mid";
        //VideoUtils.decodeFile(s);
        //NotesExtractor NE = new NotesExtractor(s);
    }
}
