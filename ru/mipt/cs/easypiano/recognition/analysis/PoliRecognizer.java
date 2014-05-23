package ru.mipt.cs.easypiano.recognition.analysis;

import ru.mipt.cs.easypiano.graphics.visualisation.SignalVisualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.Recording;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.EmptyWindow;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.Window;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHamming;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHanna;
import ru.mipt.cs.easypiano.recognition.impartion.Note;
import ru.mipt.cs.easypiano.sound.MidiPlayer;
import ru.mipt.cs.easypiano.sound.MidiPlayerThread;

import javax.sound.midi.*;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by 1 on 20.05.2014.
 */
public class PoliRecognizer extends Recognizer {
    private final static double THRESHOLD=0.9d;
    private int STEP = 100;
    private int currentNote;
    private double sampleRate;
    private final int GATES=5; //noteNumber-GATES < X < noteNumber + Gates
    private int[] currentChord;
    private Vector notes;
    private Vector durations;
    private double minDuration;
    private Observer observer;
    public void RTrecognize(Vector vecNotes, Vector vecDurations, Vector vecStartTimes){
        int n=4;
        RTrecognize( vecNotes, vecDurations,vecStartTimes,n);
    }
    public void RTrecognize(Vector vecNotes, Vector vecDurations, Vector vecStartTimes,int n){//MONO!
        checkN(n);
        double[] signal;
        currentNote=-1;
        byte[][] bytes = new byte[n][2*FOURIER_N/n];
        Recording rec = new Recording();
        observer=new Note();
        int measurements=0;
        long totalMeasurements=0;
        AudioInputStream stream = rec.getStream();
        sampleRate = rec.getSampleRate();
        rec.mikeStart();
        for (int i=0; i<n; i++){
            fillBytes(bytes[i],stream);
        }
        int N=vecNotes.size();
        int k=0;
        int howmanynotes=0;
        int bufNote=0;
        int[] bufChord=null;
        while (k<N-1){
            for (int i=0; (i<n)&&(k<N-1); i++){
                //measurements+=FOURIER_N/n;
                totalMeasurements+=FOURIER_N/n;
                if ((totalMeasurements > (long) vecStartTimes.get(k))&&(totalMeasurements < (long) vecStartTimes.get(k+1))){
                    if ((totalMeasurements - (long) (int) vecDurations.get(k))>(long) vecStartTimes.get(k)){
                        howmanynotes=0;
                    }
                    else{
                        howmanynotes=1;
                    }
                }else{
                    if (totalMeasurements >= (long) vecStartTimes.get(k+1)){
                        k++;
                        if ((totalMeasurements - (long) vecDurations.get(k))<(long) vecStartTimes.get(k)){
                            howmanynotes=2;
                        }
                    }
                }
                signal = getSignal(i,n,bytes);
                /*if (getMaxAmplitudeNum(signal)<(FOURIER_N/4)){*/
                    window.smoothen(signal);
                    double[] spectrum = FFT.getSpectrum(signal);
                    switch(howmanynotes) {
                        case 2:
                            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                                bufChord = Notes.getInstance().dissectChord2(spectrum,sampleRate,((int) notes.get(k))-GATES,
                                        ((int) notes.get(k))+GATES)[0];
                            }else{
                                bufChord = null;
                            }
                        default:
                            bufChord = null;
                            if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                                System.out.println("hm2");
                                //bufNote = Notes.getInstance().dissectNote(spectrum,sampleRate);
                                bufNote = Notes.getInstance().dissectNote(spectrum,sampleRate,((int) (vecNotes.get(k)))-GATES,
                                        (int)(vecNotes.get(k))+GATES)[0];
                            }else{
                                bufNote = -1;
                            }
                    }
                if ((bufNote != currentNote)||(bufChord!=null)){
                    currentNote = bufNote;
                    notifyObservers();
                }
                fillBytes(bytes[i],stream);
            }
        }
    }
    public void getMidiVersion(String oldName, String newName) {
        WW = new WavWrapper(oldName);
        sampleRate = WW.getSampleRate();
        int size = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0, 0, size);
        size -= (FOURIER_N + 1);
        double[] signal = new double[FOURIER_N];
        notes = new Vector();
        durations = new Vector();
        minDuration = (1000d * ((double) STEP / sampleRate));
        for (int i = 0; i < size; i += STEP) {
            copySignal(wholeSignal, signal, i, FOURIER_N);
            if (getMaxAmplitudeNum(signal) < FOURIER_N / 4) {//not good idea actually
                window.smoothen(signal);
                double[] spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                    int a = (Notes.getInstance().dissectNote(spectrum, sampleRate, Notes.FIRST_NOTE, Notes.LAST_NOTE))[0];
                    notes.add(a);
                    durations.add(minDuration);
                } else {
                    notes.add(-1);
                    durations.add(minDuration);
                }
            } else {
                window.smoothen(signal);
                double[] spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                    notes.add((int) (notes.get(notes.size() - 1)));
                    durations.add(minDuration);
                } else {
                    notes.add(-1);
                    durations.add(minDuration);
                }
            }
        }
        condenseNotes(notes, durations);
        //MidiPlayer MP = new MidiPlayer();
        //sequence.createTrack();
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 10,2);
            //sequence.createTrack();
            Track track = sequence.getTracks()[1];
            int n = notes.size();
            int i = 0;
            long time = 0;
            while (i < n) {
                if ((int) notes.get(i) != -1){
                    ShortMessage msg = new ShortMessage();
                    msg.setMessage(ShortMessage.NOTE_ON, (int) notes.get(i), 80);
                    MidiEvent me = new MidiEvent(msg, time);
                    time += (long) durations.get(i);
                    track.add(me);
                    msg.setMessage(ShortMessage.NOTE_OFF, (int) notes.get(i), 80);
                    me = new MidiEvent(msg, time);
                    track.add(me);
                }else{
                    time+=(long) durations.get(i);
                }
                i++;
            }
            MidiSystem.write(sequence, MidiSystem.getMidiFileTypes(sequence)[0], new File(newName));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void notifyObservers(){
        if (currentChord!=null) {
            currentNote = currentChord[0]*(Notes.NOTES_QUANTITY+Notes.FIRST_NOTE)+currentChord[1];
            observer.update(this, currentNote);
        }
        observer.update(this,currentNote);
    }
    public PoliRecognizer(){
        super();
        currentChord = new int[2];
    }
    public PoliRecognizer(WindowHamming WH){
        super(WH);
        currentChord = new int[2];
    }
    public PoliRecognizer(WindowHanna WH){
        super(WH);
        currentChord = new int[2];
    }
    private void checkN(int n) {
        try {
            if ((Math.pow(2d, (double) n) > FOURIER_N) || (n < 1)) throw new Exception();
        } catch (Exception e) {
            System.out.println("wrong parameters");
            e.printStackTrace();
        }
    }
    public void pseudoParallelRecognitionChord3(int n){//n is number of parallels, n=2^m, 2^n<FOURIER_N
        checkN(n);
        double[] signal;
        currentNote=-1;
        byte[][] bytes = new byte[n][2*FOURIER_N/n];
        Recording rec = new Recording();
        observer=new Note();
        AudioInputStream stream = rec.getStream();
        sampleRate = rec.getSampleRate();
        rec.mikeStart();
        for (int i=0; i<n; i++){
            fillBytes(bytes[i],stream);
        }
        while (true) {
            for (int i=0; i<n; i++){
                signal = getSignal(i,n,bytes);
                //Vector v = convertSignal(signal); Doesn't work
                new Caser(signal,(getMaxAmplitudeNum(signal)<(FOURIER_N/4))){
                    protected void uniqueDeed(){
                        if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                            int[] chords = Notes.getInstance().dissectChord3(spectrum,sampleRate,
                                    Notes.FIRST_NOTE,Notes.LAST_NOTE,10);
                        }
                    }
                    protected void uniqueDeed2(){};
                };
                fillBytes(bytes[i],stream);
            }
        }
    }
    protected abstract class Caser {
        boolean a;
        double[] spectrum;
        double[] signal;
        double sampleRate;
        protected Caser(double[] ssignal, boolean a){
            signal=ssignal;
            this.a=a;
            dissectSpec();
            if (a){
                uniqueDeed();
            }else{
                uniqueDeed2();
            }
        }
        protected void dissectSpec(){
            window.smoothen(signal);
            spectrum=FFT.getSpectrum(signal);
        }
        protected abstract void uniqueDeed();
        protected abstract void uniqueDeed2();
    }
    private int getMaxAmplitudeNum(double[] signal){
        return getMaxAmplitudeNums(signal)[0];
    }
    private int[] getMaxAmplitudeNums(double[] signal){
        int[] a = new int[2];
        a[0] = getMaxNum(signal,0,signal.length/4);
        a[1] = getMaxNum(signal,signal.length*3/8,signal.length-1);
        return a;
    }
    private int mostMentioned(int[][] array){//determining chords
        double[] ar = new double[6];
        //System.out.println("mentions");
        for (int i=0; i<6; i++){
            ar[i]=mentions(array[i%3][i/3],array);
            //System.out.print(ar[i]+" ");
        }
        System.out.println();
        int t=(getMaxNums(ar)[0]);
        int a=(int)ar[t];
        if (a>3) return array[t%3][t/3];
        return 0;
    }
    private int mentions(int a, int[][] array){//3*2 size assumed
        int c=0;
        for (int i=0; i<6; i++){
            if (a==array[i%3][i/3]) c++;
        }
        return c;
    }
    public void lastHope(String fileName){
        WW = new WavWrapper(fileName);
        sampleRate = WW.getSampleRate();
        int size = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0, 0, size);
        size -= (FOURIER_N + 1);
        double[] signal = new double[FOURIER_N];
        notes = new Vector();
        durations = new Vector();
        minDuration = (1000d * ((double) STEP / sampleRate));
        for (int i = 0; i < size; i += STEP) {
            copySignal(wholeSignal, signal, i, FOURIER_N);
            if (getMaxAmplitudeNum(signal) < FOURIER_N / 4) {//not good idea actually
                window.smoothen(signal);
                double[] spectrum=FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                        int a = (Notes.getInstance().dissectNote(spectrum, sampleRate, Notes.FIRST_NOTE, Notes.LAST_NOTE))[0];
                        notes.add(a);
                        durations.add(minDuration);
                    } else {
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }else{
                    window.smoothen(signal);
                    double[] spectrum=FFT.getSpectrum(signal);
                    if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                        notes.add((int) (notes.get(notes.size() - 1)));
                        durations.add(minDuration);
                    } else {
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }
            }
            condenseNotes(notes, durations);
            MidiPlayer MP = new MidiPlayer();
            MP.playSound(0, notes, durations);
    }
    public void fromFileAmplitudeConsideration(String fileName) {//equals lastHope, but doesn't work
        WW = new WavWrapper(fileName);
        sampleRate = WW.getSampleRate();
        int size = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0, 0, size);
        size -= (FOURIER_N + 1);
        double[] signal = new double[FOURIER_N];
        notes = new Vector();
        durations = new Vector();
        minDuration = (1000d * ((double) STEP / sampleRate));
        for (int i = 0; i < size; i += STEP) {
            copySignal(wholeSignal, signal, i, FOURIER_N);
            new Caser(signal, (getMaxAmplitudeNum(signal) < FOURIER_N / 4)) {//not good idea actually
                protected void uniqueDeed() {
                    if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                        int a = (Notes.getInstance().dissectNote(spectrum, sampleRate, Notes.FIRST_NOTE, Notes.LAST_NOTE))[0];
                        notes.add(a);
                        durations.add(minDuration);
                    } else {
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }
                protected void uniqueDeed2() {
                    if (spectrum[getMaxNum(spectrum)] > THRESHOLD) {
                        notes.add((int) (notes.get(notes.size() - 1)));
                        durations.add(minDuration);
                    } else {
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }
            };
            condenseNotes(notes, durations);
            MidiPlayer MP = new MidiPlayer();
            MP.playSound(0, notes, durations);
        }
    }
    /*public void fromFileWholeConsideration(String fileName){
        WW=new WavWrapper(fileName);
        double sampleRate=WW.getSampleRate();
        int size = (int) WW.getSize();
        double[] wholeSignal = WW.getArray(0,0,size);
        size-=(FOURIER_N+1);
        double[] signal = new double[FOURIER_N];
        double[] spectrum;
        notes=new Vector();
        durations=new Vector();
        minDuration = (1000d*((double)STEP/sampleRate));
        for (int i=0; i<size; i+=STEP){
            copySignal(wholeSignal,signal,i,FOURIER_N);
            int[] ar = getMaxAmplitudeNums(signal);
            if (ar[0]>ar[1]){
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)]>THRESHOLD){
                    int a = Notes.getInstance().dissectNote(spectrum,sampleRate);
                    notes.add(a);
                    durations.add(minDuration);
                }else{
                    notes.add(-1);
                    durations.add(minDuration);
                }
            }else{
                if (ar[0]>0.5d*ar[1]){
                    window.smoothen(signal);
                    spectrum = FFT.getSpectrum(signal);
                    if (spectrum[getMaxNum(spectrum)]>THRESHOLD) {
                        currentChord = (Notes.getInstance().dissectChord2(spectrum,sampleRate,Notes.FIRST_NOTE,Notes.LAST_NOTE))[0];
                        if (currentChord[0]==(currentChord[1])){
                            notes.add(currentChord[0]);
                            durations.add(minDuration);
                        }else{
                            int cnote = (int) notes.get(notes.size()-1);
                            if (cnote==currentChord[0]){
                                notes.add(currentChord[0]);/////////////////NOT OBVIOUS!!!!
                                durations.add(minDuration);
                            }else{
                                if (cnote==currentChord[1]){
                                    notes.add(currentChord[1]);/////////////////NOT OBVIOUS!!!!
                                    durations.add(minDuration);
                                }else{//case cnote!=chords
                                    notes.add(cnote);
                                    durations.add(minDuration);
                                }
                            }
                        }
                    }else{
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }else{
                    window.smoothen(signal);
                    spectrum = FFT.getSpectrum(signal);
                    if (spectrum[getMaxNum(spectrum)]>THRESHOLD) {
                        notes.add((int) (notes.get(notes.size() - 1)));
                        durations.add(minDuration);
                    }else{
                        notes.add(-1);
                        durations.add(minDuration);
                    }
                }
            }
        }
        //second variant
        WW=new WavWrapper(fileName);
        notes=new Vector();
        durations=new Vector();
        for (int i=0; i<size; i+=STEP){
            copySignal(wholeSignal,signal,i,FOURIER_N);
            if (getMaxAmplitudeNum(signal)<(FOURIER_N/4)){//bad idea actually
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)]>THRESHOLD){
                    int a = (Notes.getInstance().dissectNote(spectrum,sampleRate));
                    notes.add(a);
                    durations.add(minDuration);
                }else{
                    notes.add(-1);
                    durations.add(minDuration);
                }
            }else{
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNum(spectrum)]>THRESHOLD) {
                    notes.add((int) (notes.get(notes.size() - 1)));
                    durations.add(minDuration);
                }else{
                    notes.add(-1);
                    durations.add(minDuration);
                }

            }
        }
        condenseNotes(notes,durations);
        condenseNotes(notes,durations);
        final MidiPlayer MP = new MidiPlayer();
        final MidiPlayer MP1 = new MidiPlayer();
        MidiPlayerThread MPT = new MidiPlayerThread(MP,notes,durations);
        MidiPlayerThread MPT1 = new MidiPlayerThread(MP1,notes,durations);
        MPT.start();
        MPT1.start();
    }*/

    private void condenseNotes(Vector notes, Vector durations){
        int i=1;
        while (i<notes.size()){
            if ((int)(notes.get(i))==(int)(notes.get(i-1))){
                condenseAndDelete(durations,notes,i);
                //no increment;
            }else{
                long buf = (long)((double)(durations.get(i-1)));
                durations.removeElementAt(i-1);
                durations.insertElementAt(buf,i-1);
                i++;
            }
        }
        long buf = (long)((double)(durations.get(i-1)));
        durations.removeElementAt(i-1);
        durations.insertElementAt(buf,i-1);
        int n = notes.size();
        for (int j=0; j<n; j++){
            System.out.println("note "+notes.get(j)+" duration "+durations.get(j));
        }
    }
    private void condenseAndDelete(Vector durations, Vector notes, int i){
        double buf=(double)(durations.get(i))+(double)(durations.get(i-1));
        durations.removeElementAt(i-1);
        durations.insertElementAt(buf,i-1);
        durations.removeElementAt(i);
        notes.removeElementAt(i);
    }
    private void copySignal(double[] array, double[] signal, int from, int howmany){
        int max=from+howmany;
        for (int i=from,j=0; i<max; i++,j++){
            signal[j]=array[i];
        }
    }
    public void pseudoParallelRecognition(int n){//n is number of parallels, n=2^m, 2^n<FOURIER_N
        checkN(n);
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
        while (true) {
            for (int i=0; i<n; i++){
                signal = getSignal(i,n,bytes);
                double mag = signal[getMaxNums(signal)[0]];
                window.smoothen(signal);
                spectrum = FFT.getSpectrum(signal);
                if (spectrum[getMaxNums(spectrum)[0]]>THRESHOLD) {
                    //currentNote = Notes.getInstance().dissectNote(spectrum, sampleRate);
                    //int[] array = Notes.getInstance().dissectNote(spectrum, sampleRate,36,96);
                    int[][] array = Notes.getInstance().dissectChord2(spectrum,sampleRate,36,96);
                    //currentNote = array[0];
                    /*for (int m=0; m<array.length; m++){
                        System.out.print("chord of "+array[m][0]+" and "+array[m][1]+" ");
                        if (m!=array.length-1) System.out.print("or ");
                    }
                    System.out.println();*/
                    int mynote=mostMentioned(array);
                    /*if (mynote==0){
                        System.out.println("chord of "+array[0][0]+" and "+array[0][1]);
                    }else{
                        System.out.println("note "+mynote);
                    }*/
                    /*for (int m=0; m<array.length; m++){
                        System.out.print(array[m]+" ");
                    }
                    System.out.println();*/
                    //notifyObservers();
                }
                fillBytes(bytes[i],stream);
            }
        }
    }
}
