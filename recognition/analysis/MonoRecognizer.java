package ru.mipt.cs.easypiano.recognition.analysis;

import ru.mipt.cs.easypiano.graphics.visualisation.Visualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.sound.MidiPlayer;
import ru.mipt.cs.easypiano.sound.Notes;
import ru.mipt.cs.easypiano.test.sasha.SashaMain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 1 on 02.05.2014.
 */
public class MonoRecognizer extends Recognizer {
    private final static int SIZE=500000;
    private final static int OFFSET=0;
    private final static int STEP=1000;
    public MonoRecognizer(String s){
        WavWrapper WW=new WavWrapper(s);
        long sampleRate=WW.getSampleRate();
        //convert to MIDI //may have no overlapping yet
        double[] signal = WW.getArray(0,OFFSET,SIZE);
        double[] spectrum;
        int attempts=SIZE/STEP;
        int notes[][] = new int[attempts+1][3];
        for (int i=0; i<attempts+1; i++){
            notes[i][0]=100;//how long to play the note
            notes[i][1]=-1;//to play or not to play
            notes[i][2]=80;//volume
        }
        for (int i=0; i<SIZE; i+=STEP){
            spectrum=FFT.RealToReal(signal,i);
            if (spectrum==null) break;
            double max=0;
            int maxnum=0;
            for (int j=0; j< Visualizer.N/2; j++){
                if (spectrum[j]>max){
                    max=spectrum[j];
                    maxnum=j;
                }
            }
            if (max>0.3){
                notes[i/STEP][1]= Notes.getNoteNumber(maxnum * sampleRate / Visualizer.N);
                System.out.println("note number is "+notes[i/STEP][1]+" i= "+(i/STEP)+" max is "+max);
                /*notes[i/2000][2]=(int) (80*max);
                if (notes[i/2000][2]>110) notes[i/2000][2]=110;*/
            }else{System.out.println("i= "+(i/STEP)+" max is "+max);}
            //else it is default, default is -1
        }
        for (int i=SIZE/STEP; i>0; i--){
            if (notes[i][1]==notes[i-1][1]){
                notes[i-1][0]+=notes[i][0];
                notes[i][0]=0;
            }
        }
        MidiPlayer midiPlayer = new MidiPlayer();
        for (int[] note : notes) {
            if (note[1] != -1) {
                midiPlayer.playSound(0, note[0], note[2], note[1]);
            } else {
                try {
                    Thread.sleep(note[0]);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SashaMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        midiPlayer.close();//after close nothing wants to work, close it only in the end
    }
}
