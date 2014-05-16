package ru.mipt.cs.recognition.analysis;
//SASHA
import ru.mipt.cs.recognition.aggregation.Recording;
import ru.mipt.cs.recognition.aggregation.WavWrapper;
import ru.mipt.cs.recognition.impartion.Note;
import ru.mipt.cs.sound.MidiPlayer;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.util.Observer;

/**
 * Created by 1 on 02.05.2014.
 */
public class MonoRecognizer extends Recognizer {
    private static int SIZE=1000000;//will be changed in method according to ru.mipt.cs.sound file length
    private final static double THRESHOLD=0.7d;
    private final static double FILE_THRESHOLD=0.8d;
    private final static int OFFSET=0;
    private final static int STEP=1000;
    private final static int MIN_LENGTH_IN_minDuration=14;
    private WavWrapper WW;
    private int currentNote;
    public int strength;
    private Observer observer;
    public MonoRecognizer(){
    }
    /*public double[] setNuisance(){
        double[] nuisance = new double[Recognizer.FOURIER_N/2];
        String s = Notes.notePath+"noise"+".wav";
        WW=new WavWrapper(s);
        double sampleRate = WW.getSampleRate();
        SIZE = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0,0,SIZE);
        int n = SIZE-FOURIER_N;
        double[] signal = new double[FOURIER_N];
        int attempts=0;
        while (attempts<Notes.GAUGES){
            int i=(int) (Math.random()*(double) n);
            for (int j=0; j<FOURIER_N; j++){
                signal[j]=wholeSignal[i+j];
            }
            windowHanna(signal);
            double[] spectrum = FFT.getSpectrum(signal);
            if (spectrum[getMaxNums(spectrum)[0]]>0.01d) {
                System.out.println("attempts#"+attempts);
                for (int j=0; j<FOURIER_N/2; j++){
                    nuisance[j]+=spectrum[j];
                }
                attempts++;
            }
        }
        for (int j=0; j<FOURIER_N/2; j++){
            nuisance[j]/=Notes.GAUGES;
        }
        return nuisance;
    }*/
    public void fillNotesSpectrum(int noteNumber){//is called from Notes initialize()
        String s = Notes.notePath+"note"+((Integer)noteNumber).toString()+".wav";
        WW=new WavWrapper(s);
        double sampleRate = WW.getSampleRate();
        SIZE = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0,0,SIZE);
        int n = SIZE-FOURIER_N;
        double[] signal = new double[FOURIER_N];
        int attempts=0;
        while (attempts<Notes.GAUGES){
            int i=(int) (Math.random()*(double) n);
            for (int j=0; j<FOURIER_N; j++){
                signal[j]=wholeSignal[i+j];
            }
            windowHanna(signal);
            double[] spectrum = FFT.getSpectrum(signal);
            if (spectrum[getMaxNums(spectrum)[0]]>FILE_THRESHOLD) {
                Notes.getInstance().updateSpectrum(noteNumber, spectrum, sampleRate);
                attempts++;
            }
        }
        Notes.getInstance().rationalizeSpectrum(noteNumber);
    }
    public void realTimeRecognize(){//from microphone, real-time; yet only separate notes; and never stops
        double[] signal = new double[FOURIER_N];
        double[] spectrum;
        currentNote=-1;
        byte[] bytes = new byte[FOURIER_N/2];//////////////////////////////////////////changed to /2
        Recording rec = new Recording();
        observer=new Note();
        AudioInputStream stream = rec.getStream();
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        //strength=0;
        while (true) {
            spectrum = analyzeSignal(stream,bytes,signal);
            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD){
                /*FourierVisualizer FV = new FourierVisualizer();
                FV.visualize(spectrum, (long) sampleRate);*/
                currentNote = Notes.getInstance().dissectNote(spectrum,sampleRate);
                notifyObservers();
            }
        }
    }
    public void recognizeFromFile(String fileName){//from .wav file
        //deprecated
        WW=new WavWrapper(fileName);
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
    public void changeNoteSpectrum(int noteNumber){ //is called from Notes realTimeCalibrate()
        double[] signal = new double[FOURIER_N];
        byte[] bytes = new byte[FOURIER_N/2];
        Recording rec = new Recording();
        AudioInputStream stream = rec.getStream();//rec started
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        strength=0;
        remindPreparation(noteNumber);
        collectRealTimeSpectrum(noteNumber,stream,bytes,signal,sampleRate);
        rec.stopRecording();
        System.out.println("you may stop");
        someSleep(2000);
    }
    private void collectRealTimeSpectrum(int noteNumber, AudioInputStream stream, byte[] bytes, double[] signal, double sampleRate){
        int j=0;
        while (j<Notes.GAUGES) {
            double[] spectrum = analyzeSignal(stream,bytes,signal);
            currentNote = analyseNote(spectrum, sampleRate);
            notifyObservers();
            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                Notes.getInstance().updateSpectrum(noteNumber, spectrum, sampleRate);
                if (j%5 == 0){
                    System.out.println("now sleeping");
                    someSleep(1500);
                }
                j++;
            }
        }
        Notes.getInstance().rationalizeSpectrum(noteNumber);
    }
    private void remindPreparation(int noteNumber){
        if (noteNumber==-1) {
            System.out.println("Listening to nuisance!");
        }else{
            System.out.println("Listening to note #"+noteNumber);
        }
        someSleep(3000);
    }
    private double[] analyzeSignal(AudioInputStream stream ,byte[] bytes,double[] signal) {
        getSignal(stream,bytes,signal);
        windowHanna(signal);//different strategies can be chosen!
        //windowHamming(signal);
        return FFT.getSpectrum(signal);
    }
    private void getSignal(AudioInputStream stream ,byte[] bytes,double[] signal) {
        try {
            for (int i = 0; i < 4; i++) {
                stream.read(bytes);
                bytesToIntArray(bytes, signal, i * FOURIER_N/ 4,FOURIER_N / 4);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void windowHamming(double[] signal){
        int n=FOURIER_N;
        double ppi=2*Math.PI;
        for (int i=0; i<n; i++){
            signal[i]*=(0.53836d-0.46164d*Math.cos(ppi*(double)i/(double)(n-1)));
        }
    }
    private void windowHanna(double[] signal){
        int n=FOURIER_N;
        double ppi=2*Math.PI;
        for (int i=0; i<n; i++){
            signal[i]*=(1d-(Math.cos(ppi*(double)i/(double)(n-1))))/2d;
        }
    }
    private int analyseNote(double[] spec, double sampleRate){//doesn't have correct implementation yet
        int[] maxnums = getMaxNums(spec);
        int note;
        if (spec[maxnums[2]]>THRESHOLD){
            //note = ((Notes)Notes.getInstance()).getNoteNumber(sampleRate * (double) maxnums[0] / (double) FOURIER_N);
            note = Notes.getInstance().getNoteNumber(sampleRate * (double) getLeast3(maxnums) / (double) FOURIER_N);
            System.out.println("maxnums are: "+maxnums[0]+" "+maxnums[1]+" "+maxnums[2]);
        }else{
            note = -1;
        }
        return note;
    }
    private int getLeast3(int [] maxnums){//adjusting purposes
        int a;
        a = Math.min(maxnums[0],maxnums[1]);
        a = Math.min(maxnums[2],a);
        if ((double) a*1.7 < (double) maxnums[0]) return a;
        else return maxnums[0];
    }
    public void notifyObservers(){
        observer.update(this,currentNote);
    }
    private int[][] notesInit(int attempts,long sampleRate){
        int notes[][] = new int[attempts+1][3];
        int minDuration = (int) ((long)(STEP*1000)/sampleRate);
        for (int i=0; i<attempts+1; i++){
            notes[i][0]= minDuration; //how long to play
            notes[i][1]= -1;//to play or not to play, and note number if to play
            notes[i][2]= 80;//volume
        }
        return notes;
    }
    private void fillNotesArray(double[] signal, int[][] notes){
        double[] spectrum;
        long sampleRate = WW.getSampleRate();
        double minfreq = (double) sampleRate/ (double) FOURIER_N;
        for (int i=0,j=0; i<SIZE; i+=STEP,j++){
            spectrum=FFT.RealToReal(signal,i);
            if (spectrum==null) break;
            int maxnum=getMaxNum(spectrum);
            double max=spectrum[maxnum];
            if (max>THRESHOLD){
                notes[j][1]=  Notes.getInstance().getNoteNumber((double) maxnum * minfreq);
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
