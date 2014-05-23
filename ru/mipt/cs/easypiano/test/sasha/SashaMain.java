package ru.mipt.cs.easypiano.test.sasha;
//SASHA

import ru.mipt.cs.easypiano.recognition.analysis.MonoRecognizer;
import ru.mipt.cs.easypiano.recognition.analysis.PoliRecognizer;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHanna;

import javax.sound.midi.*;
import java.io.File;

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
        MonoRecognizer MR = new MonoRecognizer(new WindowHanna());
        PoliRecognizer PR = new PoliRecognizer(new WindowHanna());
        //PR.lastHope(s+"godfather.wav");
        //NotesExtractor NE = new NotesExtractor(s+"jingle-bells.mid");
        //PR.RTrecognize(NE.getNotes(), NE.getDurations(), NE.getStartTimes());
        //PR.getMidiVersion(s+"godfather.wav",s+"godfather.mid");
        try {
            System.out.println("b");
            Sequence sequence = MidiSystem.getSequence(new File(s+"godfather.mid"));
            //Sequence sequence = MidiSystem.getSequence(new File(s+"jingle-bells.mid"));
            Synthesizer synth;
            MidiChannel[] channels;
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(1);//Acoustic Royal
            Thread.currentThread().sleep(5000);
            synth.close();
            System.out.println("a");
        } catch (Exception e) {
            System.err.println(e);
        }
        //PR.pseudoParallelRecognitionChord3(4);
        //PR.pseudoParallelRecognition(4);
        //PR.pseudoParallelRecognitionChord3(4);
        //PR.pseudoParallelRecognition(4,s+"godfather.wav");
        //PR.fromFileAmplitudeConsideration(4s+"mac_yiruma.wav");
        //PR.fromFileAmplitudeConsideration(s+"workitout.wav");
        //PR.fromFileAmplitudeConsideration(s+"godfather_fast.wav");
        //PR.fromFileAmplitudeConsideration(s+"godfather.wav");
        //PR.fromFileWholeConsideration(s+"jingle-bells.wav");
        /*PR.fromFileWholeConsideration(s+"godfather.wav");
        PR.fromFileWholeConsideration(s+"morrowind.wav");
        PR.fromFileWholeConsideration(s+"terminator.wav");
        PR.fromFileWholeConsideration(s+"workitout.wav");
        PR.fromFileWholeConsideration(s+"godfather.wav");
        PR.fromFileWholeConsideration(s+"morrowind.wav");*/
        /*Notes notes = Notes.getInstance();
        for (int i=69; i<77; i++){
            notes.specToNull(i);
            MR.fillNotesSpectrum(i);
        }*/
        //MR.realTimeRecognize();
        //MR.listenAndPlay(25);
        //MR.pseudoParallelRecognition(4);//looks like 2 is optimum =(
        //MR.pseudoParalleListenAndPlay(30,4);
        //s+="yiruma-river_flows_in_you.mid";
        s+="jingle-bells.mid";
        //VideoUtils.decodeFile(s);
        //NotesExtractor NE = new NotesExtractor(s);
    }
}
