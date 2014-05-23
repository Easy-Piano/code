package ru.mipt.cs.easypiano.recognition.analysis;
//SASHA
import ru.mipt.cs.easypiano.graphics.visualisation.FourierVisualizer;
import ru.mipt.cs.easypiano.graphics.visualisation.SignalVisualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.recognition.aggregation.frommidi.NotesExtractor;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.EmptyWindow;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.Window;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHamming;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHanna;
import ru.mipt.cs.easypiano.recognition.impartion.Note;
import ru.mipt.cs.easypiano.sound.MidiPlayer;
import ru.mipt.cs.easypiano.recognition.aggregation.Recording;
import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by 1 on 02.05.2014.
 */
/*
        69 - 76 including -- from synthesizer
        from 4 to -1
 */
public class MonoRecognizer extends Recognizer  {
    private static int SIZE=1000000;//will be changed in method according to sound file length
    private final static double THRESHOLD=0.6d;
    private final static double FILE_THRESHOLD=0.9d;
    private final static int OFFSET=0;
    private final static int STEP=1000;
    private final static int MIN_LENGTH_IN_minDuration=14;
    //private Window window;
    private int currentNote;
    public int strength;
    private Observer observer;
    public MonoRecognizer(){
        super();
    }
    public MonoRecognizer(WindowHamming WH){
        super(WH);
    }
    public MonoRecognizer(WindowHanna WH){
        super(WH);
    }
    public double[] setNuisance(){
        double[] nuisance = new double[Recognizer.FOURIER_N/2];
        String s = Notes.notePath+"noise"+".wav";
        WW=new WavWrapper(s);
        double sampleRate = WW.getSampleRate();
        SIZE = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0,0,SIZE);
        int n = SIZE-2*FOURIER_N;
        double[] signal = new double[FOURIER_N];
        int attempts=0;
        while (attempts<Notes.GAUGES){
            int i=(int) (Math.random()*(double) n)+FOURIER_N;
            for (int j=0; j<FOURIER_N; j++){
                signal[j]=wholeSignal[i+j];
            }
            window.smoothen(signal);
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
        return Notes.getInstance().spectrumToHarmonics(nuisance,sampleRate);
    }
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
            window.smoothen(signal);
            double[] spectrum = FFT.getSpectrum(signal);
            if (spectrum[getMaxNums(spectrum)[0]]>FILE_THRESHOLD) {
                /*if (mag<0.8) {
                    System.out.println("oops, note "+noteNumber);
                    continue;
                }*/
                //Notes.getInstance().updateRelaxationTime(noteNumber,time);
                Notes.getInstance().updateSpectrum(noteNumber, spectrum, sampleRate);
                attempts++;
            }
            /*int mn=getMaxNum(signal,0,signal.length-1);
            double max=signal[mn];
            Vector v;
            double time;
            v = convertSignal(signal);
            //double mag = signal[getMaxNums(signal)[0]];
            int j;
            if (mn<(FOURIER_N/4)) {//////////////////////now wraps taking note too
                int  count=0;
                System.out.println("hi");
                System.out.println("mn = "+mn);
                while((int)(v.get(count))!=mn){
                    System.out.println(v.get(count));
                    count++;
                }
                System.out.println("bye");
                count++;
                for ( j=count; j<v.size(); j++){
                    if (max>2*signal[(int) v.get(j)]){
                        break;
                    }
                }
                for (int k=0; k<signal.length; k++){
                    System.out.println(k+" "+signal[k]);
                }
                System.out.println("j= "+j);
                if (j==v.size()-1){
                    //System.out.println("window is too short");
                }else{
                    //System.out.println("hey! time is"+time);
                    System.out.println("hey! j-mn is "+((int)v.get(j)-mn));
                }
                time=1000d*((double)(j-mn))/sampleRate;
                //for (int i=mn; i<FOURIER_N-)

                }
            }*/
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
        long deltaTime = System.currentTimeMillis();
        long bufTime;
        while (true) {
            bufTime = System.currentTimeMillis();
            System.out.println(bufTime-deltaTime);//hereby we know that cycle lasts 1ms - 200ms
            deltaTime=bufTime;
            spectrum = analyzeSignal(stream,bytes,signal);
            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD){
                /*FourierVisualizer FV = new FourierVisualizer();
                FV.visualize(spectrum, (long) sampleRate);*/
                currentNote = Notes.getInstance().dissectNote(spectrum,sampleRate);
                notifyObservers();
            }
        }
    }
    public void pseudoParallelRecognition(int n){//n is number of parallels, n=2^m, 2^n<FOURIER_N
        try{
            if ((Math.pow(2d,(double)n)>FOURIER_N)||(n<1)) throw new Exception();
        } catch (Exception e) {
            System.out.println("wrong parameters");
            e.printStackTrace();
        }
        double[] signal;
        double[] spectrum;
        currentNote=-1;
        byte[][] bytes = new byte[n][2*FOURIER_N/n];
        Recording rec = new Recording();
        observer=new Note();
        AudioInputStream stream = rec.getStream();
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        for (int i=0; i<n; i++){
            fillBytes(bytes[i],stream);
        }
        //int k=0;
        //SignalVisualizer SV = new SignalVisualizer();
        while (true) {
            for (int i=0; i<n; i++){
                signal = getSignal(i,n,bytes);
                /*if (k<50){
                    SV.visualize(signal,44100);
                    k++;
                }*/
                if (getMaxNums(signal)[0]<0.3) continue;
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                    //currentNote = Notes.getInstance().dissectNote(spectrum, sampleRate);
                    int[] array = Notes.getInstance().dissectNote(spectrum, sampleRate,36,96);
                    currentNote = array[0];
                    for (int m=0; m<array.length; m++){
                        System.out.print(array[m]+" ");
                    }
                    System.out.println();
                    notifyObservers();
                }
               fillBytes(bytes[i],stream);
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
        window.smoothen(signal);
        //windowHanna(signal);//different strategies can be chosen!
        //windowHamming(signal);
        return FFT.getSpectrum(signal);
    }
    protected int[][] notesInit(int attempts,long sampleRate){
        int notes[][] = new int[attempts+1][3];
        int minDuration = (int) ((long)(STEP*1000)/sampleRate);
        for (int i=0; i<attempts+1; i++){
            notes[i][0]= minDuration; //how long to play
            notes[i][1]= -1;//to play or not to play, and note number if to play
            notes[i][2]= 80;//volume
        }
        return notes;
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
    public void listenAndPlay(int maxTimeSec){
        long maxTimeMiliSec = (long) maxTimeSec*1000;
        long startTime;
        long bufTime = System.currentTimeMillis();
        startTime = bufTime;
        long time;
        double[] signal = new double[FOURIER_N];
        double[] spectrum;
        currentNote=-1;
        int bufNote=-1;
        byte[] bytes = new byte[FOURIER_N/2];//////////////////////////////////////////changed to /2
        Recording rec = new Recording();
        Vector vecNotes = new Vector();
        Vector vecTime = new Vector();
        //observer=new Note();
        AudioInputStream stream = rec.getStream();
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        while ((System.currentTimeMillis()-startTime<maxTimeMiliSec)) {
            spectrum = analyzeSignal(stream,bytes,signal);
            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD){
                currentNote = Notes.getInstance().dissectNote(spectrum,sampleRate);
                //notifyObservers();
                if (bufNote!=currentNote){
                    bufNote=currentNote;
                    time = bufTime;
                    bufTime=System.currentTimeMillis();
                    System.out.println(bufTime-time);
                    vecTime.add(bufTime-time);
                    vecNotes.add(currentNote);
                }
            }else{
                currentNote = -1;
                if (bufNote!=currentNote){
                    bufNote=currentNote;
                    time = bufTime;
                    bufTime=System.currentTimeMillis();
                    System.out.println(bufTime-time);
                    vecTime.add(bufTime-time);
                    vecNotes.add(currentNote);
                }
            }
        }
        MidiPlayer MP = new MidiPlayer();
        for (int i=0; i<vecTime.size(); i++){
            System.out.println("time "+(long) (vecTime.get(i))+" note "+(int)(vecNotes.get(i)));
        }
        MP.playSound(0,vecNotes,vecTime);
    }
    public void pseudoParalleListenAndPlay(int maxTimeSec, int n){
        try{
            if ((Math.pow(2d,(double)n)>FOURIER_N)||(n<1)) throw new Exception();
        } catch (Exception e) {
            System.out.println("wrong parameters");
            e.printStackTrace();
        }
        long maxTimeMiliSec = (long) maxTimeSec*1000;
        long startTime;
        long bufTime = System.currentTimeMillis();
        startTime = bufTime;
        long time;
        double[] signal;
        double[] spectrum;
        currentNote=-1;
        int bufNote=-1;
        byte[][] bytes = new byte[n][2*FOURIER_N/n];
        Recording rec = new Recording();
        Vector vecNotes = new Vector();
        Vector vecTime = new Vector();
        AudioInputStream stream = rec.getStream();
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        for (int i=0; i<n; i++){
            fillBytes(bytes[i],stream);
        }
        while ((System.currentTimeMillis()-startTime<maxTimeMiliSec)) {
            for (int i=0; i<n; i++){
                signal = getSignal(i,n,bytes);
                if (getMaxNums(signal)[0]<0.8) continue;
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD){
                    currentNote = Notes.getInstance().dissectNote(spectrum,sampleRate,67,75)[0];
                    //currentNote = Notes.getInstance().dissectNote(spectrum,sampleRate);
                    if (bufNote!=currentNote){
                        bufNote=currentNote;
                        time = bufTime;
                        bufTime=System.currentTimeMillis();
                        System.out.println(bufTime-time);
                        vecTime.add(bufTime-time);
                        vecNotes.add(currentNote);
                    }
                }else{
                    currentNote = -1;
                    if (bufNote!=currentNote){
                        bufNote=currentNote;
                        time = bufTime;
                        bufTime=System.currentTimeMillis();
                        System.out.println(bufTime-time);
                        vecTime.add(bufTime-time);
                        vecNotes.add(currentNote);
                    }
                }
                fillBytes(bytes[i],stream);
            }
        }
        for (int i=0; i<vecTime.size(); i++){
            if (((long) vecTime.get(i))<35){
                long bt = (long) vecTime.get(i);
                vecNotes.removeElementAt(i);
                vecTime.removeElementAt(i);
                if (i>1) {
                    bt+=(long) vecTime.get(i-1);
                    vecTime.removeElementAt(i-1);
                    vecTime.insertElementAt(bt, i - 1);
                    i--;
                }
            }
        }
        MidiPlayer MP = new MidiPlayer();
        for (int i=0; i<vecTime.size(); i++){
            System.out.println("time "+(long) (vecTime.get(i))+" note "+(int)(vecNotes.get(i)));
        }
        MP.playSound(0,vecNotes,vecTime);
    }
    public void useOfAmplitude(){

    }
    public void listenAndCompare(String s, int n, double speed) {//in order not to spoil what is currently working
        NotesExtractor NE = new NotesExtractor(s);
        Vector notes = NE.getNotes();
        Vector startTimes1 = NE.getStartTimes();
        Vector durations = NE.getDurations();
        Vector startTimes = new Vector();
        int size = startTimes1.size();
        for (int i=0; i<size; i++){
            startTimes.add((long)(speed*(double)(long)startTimes1.get(i)));
        }
        try{
            if ((Math.pow(2d,(double)n)>FOURIER_N)||(n<1)) throw new Exception();
        } catch (Exception e) {
            System.out.println("wrong parameters");
            e.printStackTrace();
        }
        double[] signal;
        double[] spectrum;
        currentNote=-1;
        byte[][] bytes = new byte[n][2*FOURIER_N/n];
        Recording rec = new Recording();
        observer=new Note();
        AudioInputStream stream = rec.getStream();
        double sampleRate = rec.getSampleRate();
        rec.mikeStart();
        for (int i=0; i<n; i++){
            fillBytes(bytes[i],stream);
        }
        long iteration = 0;
        long maxIteration = (long) ((double) n*(sampleRate/(double) FOURIER_N)*(double)((long)startTimes.get(size-1)+(long)durations.get(size-1)));
        //int k=0;
        //SignalVisualizer SV = new SignalVisualizer();
        while (iteration<maxIteration) {
            for (int i=0; i<n; i++){
                signal = getSignal(i,n,bytes);
                /*if (k<50){
                    SV.visualize(signal,44100);
                    k++;
                }*/
                if (getMaxNums(signal)[0]<0.3) continue;
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                    //currentNote = Notes.getInstance().dissectNote(spectrum, sampleRate);
                    int[] array = Notes.getInstance().dissectNote(spectrum, sampleRate,36,96);
                    currentNote = array[0];
                    for (int m=0; m<array.length; m++){
                        System.out.print(array[m]+" ");
                    }
                    System.out.println();
                    notifyObservers();
                }
                fillBytes(bytes[i],stream);
            }
        }
    }
}
