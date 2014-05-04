package ru.mipt.cs.easypiano.recognition.analysis;
//SASHA
import ru.mipt.cs.easypiano.graphics.visualisation.Visualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.sound.MidiPlayer;
import ru.mipt.cs.easypiano.sound.Notes;


/**
 * Created by 1 on 02.05.2014.
 */
public class MonoRecognizer extends Recognizer {
    private static int SIZE=1000000;//will be changed in method according to sound file length
    private final static double THRESHOLD=0.6;
    private final static int OFFSET=0;
    private final static int STEP=1000;
    private final static int MIN_LENGTH_IN_minDuration=14;
    private WavWrapper WW;
    public MonoRecognizer(String s){
        WW=new WavWrapper(s);
        long sampleRate=WW.getSampleRate();
        SIZE = (int) WW.getSize();
        double[] signal = WW.getArray(0,OFFSET,SIZE);
        int attempts=SIZE/STEP;
        int notes[][] = notesInit(attempts,sampleRate);
        fillNotesArray(signal,notes);
        condenseNotesArray(attempts,notes);
        sieveNoteArray(attempts,notes,sampleRate);
        printNoteSummary(attempts,notes);

        MidiPlayer midiPlayer = new MidiPlayer();
        midiPlayer.playSound(0,notes);
        midiPlayer.close();
    }
    private int[][] notesInit(int attempts,long sampleRate){
        int notes[][] = new int[attempts+1][3];
        int minDuration = (int) ((long)(STEP*1000)/sampleRate);
        for (int i=0; i<attempts+1; i++){
            //notes[i][0]=50;//how long to play the note
            notes[i][0]= minDuration; //how long to play
            notes[i][1]= -1;//to play or not to play, and note number if to play
            notes[i][2]= 80;//volume
        }
        return notes;
    }
    private int getMaxNum(double[] spectrum){
        double max=0;
        int maxnum=0;
        for (int j=0; j< Visualizer.N/2; j++){
            if (spectrum[j]>max){
                max=spectrum[j];
                maxnum=j;
            }
        }
        return maxnum;
    }
    private void fillNotesArray(double[] signal, int[][] notes){
        double[] spectrum;
        long sampleRate = WW.getSampleRate();
        for (int i=0,j=0; i<SIZE; i+=STEP,j++){
            spectrum=FFT.RealToReal(signal,i);
            if (spectrum==null) break;
            int maxnum=getMaxNum(spectrum);
            double max=spectrum[maxnum];
            double minfreq = (double) sampleRate/ (double) Visualizer.N;
            if (max>THRESHOLD){
                notes[j][1]= Notes.getNoteNumber((double) maxnum * minfreq);
                updateNote(j,notes,max);
            }
        }
    }
    private void updateNote(int j, int [][] notes, double max){//should be changed after no more print is needed
        if (j>0) {
            if (notes[j][1] != notes[j - 1][1]) {
                System.out.println("note number is " + notes[j][1] + " i= " + (j) + " max is " + max);
            }
        }
    }
    private void condenseNotesArray(int attempts, int[][] notes){
        for (int i=attempts; i>0; i--){
            if (notes[i][1]==notes[i-1][1]){
                notes[i-1][0]+=notes[i][0];
                notes[i][0]=0;
            }
        }
    }
    private void sieveNoteArray(int attempts, int[][] notes, long sampleRate){
        int minDuration = ((int) ((long)(STEP*1000)/sampleRate));
        for (int i=0; i<attempts; i++){
            if (notes[i][0] < (MIN_LENGTH_IN_minDuration*minDuration)){
                notes[i][1]=-1;
            }
        }
    }
    private void printNoteSummary(int attempts, int[][] notes){
        int sum=0;//to watch how much time something is playing
        for (int i=0; i<attempts; i++){
            if (notes[i][1]!=-1){
                System.out.println("note "+notes[i][1]+" length "+notes[i][0]);
                sum+=notes[i][0];
            }
        }
        System.out.println("sum = "+sum);
        System.out.println("time is "+WW.getTime());
    }
}
