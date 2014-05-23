package ru.mipt.cs.easypiano.recognition.analysis;
//SASHA

import ru.mipt.cs.easypiano.graphics.visualisation.NotesSpecVisualizer;

import java.io.*;

/**
 * Created by 1 on 27.04.2014.
 */
public class Notes implements Serializable{//is also a Singleton
    //and trivial neuronet was implemented inside
    public final static String notePath = Notes.class.getClassLoader().getResource("//").getPath()+
            "ru\\mipt\\cs\\easypiano\\resourses\\notes\\";
    private final static String saveName = Notes.class.getClassLoader().getResource("//").getPath()+
            "ru\\mipt\\cs\\easypiano\\resourses\\" +"serialized.out";
    protected static boolean wasInitialized = false;
    public static double CMP_NOTE = 0.7;
    protected static volatile Notes instance;
    public static final int GAUGES=200;//how many experiments to carry out in order to calibrate from .wav file
    private double [] frequencies;//an array of NOTES_QUANTITY notes' frequencies
    public static final int NOTES_QUANTITY = 61;//as a virtual piano, for an acoustic piano it should be 88
    //public static final int HARMONICS_QUANTITY = 132; is redundant because depends on FOURIER_N and is not good when big
    //public static final int HARMONICS_QUANTITY = 88;
    //public static final int MIDI_NOTES_QUANTITY = 132;
    public static final int V_PIANO_OFFSET = 15;//distinction of virtual piano from acoustic piano
    public static final int MIDI_OFFSET = 21;//as a virtual piano, for an acoustic piano it should be 20
    public static final int FIRST_NOTE = V_PIANO_OFFSET+MIDI_OFFSET;
    public static final int LAST_NOTE = FIRST_NOTE+NOTES_QUANTITY-1;
    //private double[][] specArrays;//an arraay of whole average specrtums (size FOURIER_N/2 because is's symmetrcic)
    private double[] nuisance;
    private double[] relaxationTimes;
    private double[][] array;//an array of harmonic spectrums of NOTES_QUANTITY notes
    private double[][][] chords2;//chords2[i][j] is a normalized specturm of note i and note j together
    private double[][][][] chords3;
    //everywhere noteNumber is in MIDI notation
    protected void initialize() {//if was not initialized before
        frequencies=new double[NOTES_QUANTITY];//array of frequencies from FIRST_NOTE to LAST_NOTE
        array=new double[NOTES_QUANTITY][NOTES_QUANTITY];//x is note number, y is note number spectrum
        chords2 = new double[NOTES_QUANTITY][NOTES_QUANTITY][NOTES_QUANTITY];
        chords3 = new double[NOTES_QUANTITY][NOTES_QUANTITY][NOTES_QUANTITY][NOTES_QUANTITY];
        nuisance = new double[NOTES_QUANTITY];
        relaxationTimes = new double[NOTES_QUANTITY];
        //specArrays=new double[NOTES_QUANTITY][Recognizer.FOURIER_N/2];
        for (int i=0;i<NOTES_QUANTITY;i++){
            frequencies[i]=27.5*Math.pow(2d,(double)(i+V_PIANO_OFFSET) / 12d);
        }
        /*for (int i=0; i<NOTES_QUANTITY; i++){
            System.out.println("i= "+i+" fr[i]= "+frequencies[i]+ " note is "+ getNoteNumber(frequencies[i]));
        }*/
        MonoRecognizer MR = new MonoRecognizer();
        nuisance=MR.setNuisance();
        for (int i=0; i<NOTES_QUANTITY; i++) {
            MR.fillNotesSpectrum(i+FIRST_NOTE);
        }//can't be replaced into constructor

        //rationalizeArray(nuisance);
        for (int i=0; i<NOTES_QUANTITY; i++){
            for (int j=0; j<NOTES_QUANTITY; j++){
                for (int k=0; k<NOTES_QUANTITY; k++){
                    /*double buf = array[i][k]+array[j][k]-nuisance[k];
                    chords2[i][j][k]=(buf>0)?buf:0;*/
                    double buf = array[i][k]+array[j][k];
                    chords2[i][j][k]=buf;
                }
                rationalizeArray(chords2[i][j]);
            }
        }
        for (int i=0; i<NOTES_QUANTITY; i++){
            for (int j=0; j<NOTES_QUANTITY; j++){
                for (int k=0; k<NOTES_QUANTITY; k++){
                    for (int t=0; t<NOTES_QUANTITY; t++){
                        chords3[i][j][k][t]=array[i][t]+array[j][t]+array[k][t];
                    }
                    rationalizeArray(chords3[i][j][k]);
                }
            }
        }
        wasInitialized = true;
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateRelaxationTime(int noteNumber, double time){
        int a=noteNumber-FIRST_NOTE;
        System.out.println("just time is "+relaxationTimes);
        if (relaxationTimes[a]==0) {
            relaxationTimes[a]=time;
        }else{
            relaxationTimes[a]+=time;
            relaxationTimes[a]/=2;
        }
        System.out.println("rel time is "+relaxationTimes);

    }
    /*private void killNuisance(double[] array){
        int n=Recognizer.FOURIER_N/2;
        for (int i=0; i<n; i++){
            array[i]-=nuisance[i];
            if (array[i]<0) array[i]=0;
        }
    }*/
    public double[] getNoteSpec(int noteNumber){
        try {
            if ((noteNumber>MIDI_OFFSET+V_PIANO_OFFSET+NOTES_QUANTITY)||(noteNumber<MIDI_OFFSET+V_PIANO_OFFSET)) {
                throw new IndexOutOfBoundsException();
            }
            return array[noteNumber-MIDI_OFFSET-V_PIANO_OFFSET];
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }
    public static Notes getInstance() {
        if (instance == null) {
            instance = getInstanceFromFile();
        }
        return instance;
    }
    private Notes(){
    }
    private void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveName));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    public void realTimeCalibrate(int noteNumber){ //to calibrate note harmonic spectrum without recording .wav
        try{
            if ((noteNumber>LAST_NOTE)||(noteNumber<FIRST_NOTE)){
                throw new IndexOutOfBoundsException();
            }
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        specToNull(noteNumber);
        (new MonoRecognizer()).changeNoteSpectrum(noteNumber);
    };
    public void showSpec(int noteNumber){ //draws a graph of noteNumber harmonic spectrum
        try{
            if ((noteNumber>LAST_NOTE)||(noteNumber<FIRST_NOTE)){
                throw new IndexOutOfBoundsException();
            }
            NotesSpecVisualizer NSV = new NotesSpecVisualizer();
            NSV.visualize(array[noteNumber-FIRST_NOTE],noteNumber);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
    public void specToNull(int noteNumber) {
        try {
            if ((noteNumber > LAST_NOTE) || (noteNumber < FIRST_NOTE)){
                throw new IndexOutOfBoundsException();
            }
            for (int j = 0; j < NOTES_QUANTITY; j++) {
                array[noteNumber-FIRST_NOTE][j] = 0;
            }
            /*for (int j=0; j < Recognizer.FOURIER_N; j++){
                specArrays[noteNumber-FIRST_NOTE][j] = 0;
            }*/
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    private static Notes getInstanceFromFile(){
        try {
            if (wasInitialized == false) {
                if (!((new File(saveName)).exists())) {
                    Notes notes = new Notes();
                    instance = notes;
                    notes.initialize();
                    return notes;
                }
                Notes notes = (Notes) (new ObjectInputStream(new FileInputStream(saveName))).readObject();
                wasInitialized=true;//just in case
                return notes;
            }else {//just in case
                Notes notes = (Notes) (new ObjectInputStream(new FileInputStream(saveName))).readObject();
                return notes;
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private double dotProduct(double[] a1, double[] a2, int size){
        double s=0;
        for (int i=0; i<size; i++){
            s+=a1[i]*a2[i];
        }
        return s;
    }
    public int dissectNote(double[] spec, double sampleRate) {//returns most corresponding noteNumber in MIDI notation
        double[] linArray = spectrumToHarmonics(spec, sampleRate);
        double[] dotArray = new double[NOTES_QUANTITY];
        for (int i = 0; i < NOTES_QUANTITY; i++) {
            dotArray[i] = dotProduct(linArray, array[i], NOTES_QUANTITY);
        }
        return (findMaxNum(dotArray)+FIRST_NOTE);
    }
    public int[] dissectNote(double[] spec, double sampleRate,int first, int last){
        double[] linArray = spectrumToHarmonics(spec, sampleRate);
        double[] dotArray = new double[last-first+1];
        for (int i = 0; i <= last-first; i++) {
            dotArray[i] = dotProduct(linArray, array[i+first-FIRST_NOTE], NOTES_QUANTITY);
        }
        int[] array = Recognizer.getMaxNums(dotArray);
        for (int i=0; i<array.length; i++){
            array[i]+=first;
        }
        return array;
    }
    public int[] dissectChord3(double[] spec, double sampleRate, int first, int last, int howmany){
        double[] linArray = spectrumToHarmonics(spec, sampleRate);
        double[] dotArray = new double[(last-first+1)*(last-first+1)*(last-first+1)];
        for (int i = 0; i <= last-first; i++) {
            for (int j=0; j <= last-first; j++){
                for (int k=0; k<= last-first; k++){
                    dotArray[i*(last-first+1)*(last-first+1)+j*(last-first+1)+k] = dotProduct(
                            linArray, chords3[i+first-FIRST_NOTE][j+first-FIRST_NOTE][k+first-FIRST_NOTE],NOTES_QUANTITY);
                }
            }
        }
        int[] testArray = Recognizer.getMaxNums(dotArray,howmany);
        System.out.println("chords3 are ");
        for (int i=0; i<testArray.length; i++){
            int a=testArray[i]/((last-first+1)*(last-first+1))+first;
            int b=(testArray[i]/(last-first+1))%(last-first+1)+first;
            int c=testArray[i]%(last-first+1)+first;
            System.out.println(a+" and "+b+" and "+c);
        }
        return testArray;
    }
    public int[][] dissectChord2(double[] spec, double sampleRate, int first, int last){
        double[] linArray = spectrumToHarmonics(spec, sampleRate);
        double[] dotArray = new double[(last-first+1)*(last-first+1)];
        for (int i = 0; i <= last-first; i++) {
            for (int j=0; j <= last-first; j++){
                /*dotArray[i*(last-first+1)+j] = dotProduct(linArray, array[i+first-FIRST_NOTE], NOTES_QUANTITY)+
                dotProduct(linArray, array[j+first-FIRST_NOTE],NOTES_QUANTITY);*/
                dotArray[i*(last-first+1)+j] = dotProduct(linArray, chords2[i+first-FIRST_NOTE][j+first-FIRST_NOTE],NOTES_QUANTITY);
            }
        }
        int[] array = Recognizer.getMaxNums(dotArray);
        int[] testArray = Recognizer.getMaxNums(dotArray,8);
        System.out.println("chords2 are ");
        for (int i=0; i<testArray.length; i++){
            int a=testArray[i]/(last-first+1)+first;
            int b=testArray[i]%(last-first+1)+first;
            System.out.println(a+" and "+b);
        }
        //System.out.println("chords2 mags are "+dotArray[array[0]]+" and "+dotArray[array[1]]+" and "+dotArray[array[2]]);
        int[][] array2 = new int[array.length][2];
        for (int i=0; i<array.length; i++){
            array2[i][0]=(array[i]/(last-first+1)+first);
            array2[i][1]=(array[i]%(last-first+1)+first);
        }
        return array2;
    }
    private int findMaxNum(double[] a){
        int n=a.length;
        int m=0; double max=0;
        for (int i=0; i<n; i++){
            if (a[i]>max){
                max=a[i];
                m=i;
            }
        }
        return m;
    }
    public double[] spectrumToHarmonics(double[] spec, double sampleRate){
        //double minFreq = sampleRate/ (double) Recognizer.FOURIER_N;
        double minFreq = sampleRate / Recognizer.FOURIER_N;
        double freq;
        double[] linArray = new double[NOTES_QUANTITY];
        for (int j=FIRST_NOTE; j<=LAST_NOTE; j++){
            freq=getNoteFrequency(j);
            //if (freq>sampleRate/2) System.out.println("i say too big frequency");
            linArray[j-FIRST_NOTE]=getMagn(freq,spec,minFreq);
        }
        return linArray;
    }
    public void updateSpectrum(int noteNumber, double[] spec, double sampleRate){
        try {
            if ((noteNumber > LAST_NOTE) || (noteNumber < FIRST_NOTE)) {
                throw new IndexOutOfBoundsException();
            }
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        //double minFreq = sampleRate/ (double) Recognizer.FOURIER_N;
        double minFreq = sampleRate/ Recognizer.FOURIER_N;
        double freq;
        /*for (int j=0; j<Recognizer.FOURIER_N/2; j++){
            specArrays[noteNumber-FIRST_NOTE][j]+=spec[j];
        }*/
        for (int j=0; j < NOTES_QUANTITY; j++){
            freq=getNoteFrequency(j+FIRST_NOTE);
            //if (freq>minFreq*Recognizer.FOURIER_N/2) System.out.println("too big frequency");
            array[noteNumber-FIRST_NOTE][j]+=getMagn(freq,spec,minFreq);
        }
    }
    public void rationalizeSpectrum(int noteNumber){
        try {
            if ((noteNumber>LAST_NOTE)||(noteNumber<FIRST_NOTE)){
                throw new IndexOutOfBoundsException();
            }
            rationalizeArray(array[noteNumber-FIRST_NOTE]);
            //rationalizeArray(specArrays[noteNumber-FIRST_NOTE]);
            /*double  dp = dotProduct(specArrays[noteNumber-FIRST_NOTE],specArrays[noteNumber-FIRST_NOTE],Recognizer.FOURIER_N/2);
            System.out.println("dp = "+dp);*/
            System.out.println("note "+noteNumber+" spectrum received");
            save();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    private void rationalizeArray(double[] linearArray){
        double a = getSumSquares(linearArray);
        a=Math.sqrt(a);
        int n=linearArray.length;
        for (int j=0; j<n; j++){
            linearArray[j]/=a;
        }
    }
    private double getSumSquares(double[] linearArray){
        int n=linearArray.length;
        double s=0;
        for (int i=0; i<n; i++){
            s+=linearArray[i]*linearArray[i];
        }
        return s;
    }
    public int cmpNote(double[] spec,int noteNumber){
        if (dotProduct(array[noteNumber-FIRST_NOTE],spec,Recognizer.FOURIER_N/2)>(CMP_NOTE/2)*Math.sqrt(getSumSquares(spec))){
            return 1;
        }else{
            return 0;
        }
    }
    private double getMagn(double f, double[] spec, double minFreq){
        double l = getLeft(f,minFreq);
        double t = (spec[((int)l+1)]*(f-l*minFreq)+spec[(int)l]*((l+1d)*minFreq-f))/(minFreq);
        return t;
    }
    private double getLeft(double f,double minFreq){//return array, l[1] is closest to f left frequency, l[0] it's spec amplitude
        return (int)(f/minFreq);
    }
    public double getNoteFrequency(int noteNumber){
        try{
            if ((noteNumber>LAST_NOTE)||(noteNumber<FIRST_NOTE)){
                throw new IndexOutOfBoundsException();
            }
            return frequencies[noteNumber-FIRST_NOTE];
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return -1;
    }
    public int getNoteNumber(double f){
        return hashSearch(f);
    }
    private int binarySearch(double f, int l, int r){
        if ((r-l)==1) return l;
        int t=(r+l)/2;
        if (f<frequencies[t]){
            return binarySearch(f,l,t);
        }else {
            return binarySearch(f,t,r);
        }
    }
    private int hashSearch(double f){
        int r = (int) ((Math.log(f/27.5d))*12d/Math.log(2)+(double)-V_PIANO_OFFSET);
        if (r>=NOTES_QUANTITY-1){
            r=NOTES_QUANTITY-1;
        }else if (r<=0) {
                r=0;
              }else{
                    r=(((frequencies[r+1]-f)>(f-frequencies[r]))?r:(r+1));
              }
        return r+FIRST_NOTE;
    }
}
