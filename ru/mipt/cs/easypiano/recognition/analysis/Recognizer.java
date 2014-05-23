package ru.mipt.cs.easypiano.recognition.analysis;
//SASHA

import ru.mipt.cs.easypiano.graphics.visualisation.Visualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.EmptyWindow;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.Window;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHamming;
import ru.mipt.cs.easypiano.recognition.analysis.strategies.WindowHanna;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Vector;

/**
 * Created by 1 on 02.05.2014.
 */
public class Recognizer extends Observable{
    //public final static int FOURIER_N = 2048;
    //public final static int FOURIER_N = 4096;
    protected Window window;
    protected WavWrapper WW;
    public final static int FOURIER_N = 8192;
    //public final static int FOURIER_N = 16384;
    public Recognizer(){
        window = new EmptyWindow();
    }
    public Recognizer(WindowHamming WH){
        window = WH;
    }
    public Recognizer(WindowHanna WH){
        window = WH;
    }
    protected int getMaxNum(double[] spectrum){
        double max=0;
        int maxnum=0;
        int n=FOURIER_N/2;
        for (int j=0; j< n; j++){
            if (spectrum[j]>max){
                max=spectrum[j];
                maxnum=j;
            }
        }
        return maxnum;
    }
    /*protected double getAverageAmplitude(double[] signal, int atIndex,int noteNumber, double sampleRate){
        double periodSec = 1d/Notes.getInstance().getNoteFrequency(noteNumber);
        int period = (int) (sampleRate*periodSec);
        if ((atIndex+3*period)>signal.length){
            System.out.println("not enough data in array");
        }
        double a = getMax(signal ,atIndex, period*2);

    }*/
    protected Vector convertSignal(double[] signal){
        Vector v = new Vector();
        int n=signal.length;
        int l;
        int r;
        int t=0;
        while(signal[t]<0) t++;
        l=t;
        for (int i=t+1; i<n-1; i++){
            if (signal[i]>0){
                if (signal[i-1]<0){
                    l=i-1;
                }
                if (signal[i+1]<0){
                    r=i+1;
                    v.add(getMaxNum(signal, l, r));
                }
            }
        }
        /*for (int i=0; i<v.size(); i++){
            System.out.println(v.get(i));
        }*/
        return v;
    }
    protected int getMaxNum(double[] array, int from, int to){
        double max=array[from];
        int mn=from;
        for (int i=from; i<=to; i++){
            if (array[i]>max){
                max=array[i];
                mn=i;
            }
        }
        return mn;
    }
    protected double getMax(double[] array,int from, int to){
        double max=array[from];
        for (int i=from; i<to; i++){
            if (array[i]>max){
                max=array[i];
            }
        }
        return max;
    }

    public static int[] getMaxNums(double[] spectrum){//should be improved obviously
        int [] m = new int[3]; m[0]=0; m[1]=0; m[2]=0;
        double [] mm = new double[3]; mm[0]=0; mm[1]=0; mm[2]=0;
        int n=spectrum.length;
        if (n==FOURIER_N) n=n/2;
        for (int i=0; i<n; i++){//should be generalized
            if (spectrum[i]>mm[0]){
                mm[2]=mm[1];
                m[2]=m[1];
                mm[1]=mm[0];
                m[1]=m[0];
                mm[0]=spectrum[i];
                m[0]=i;
            }else if(spectrum[i]>mm[1]){
                mm[2]=mm[1];
                m[2]=m[1];
                mm[1]=spectrum[i];
                m[1]=i;
            }else if(spectrum[i]>mm[2]){
                mm[2]=spectrum[i];
                m[2]=i;
            }
        }
        return m;
    }
    public static int[] getMaxNums(double[] array,int howmany){
        int n=array.length;
        int[] maxNums = new int[howmany];
        double[] maxes = new double[howmany];
        int i=0;
        int j=0;
            while (i<n){
                if (maxes[j]<array[i]){
                    pull(maxes,maxNums,j);
                    maxes[j]=array[i];
                    maxNums[j]=i;
                    i++;
                }else j++;
                if (j==howmany) {
                    j=0;
                    i++;
                }
            }
        return maxNums;
    }
    private static void pull(double[] m, int[] a, int index){
        int n = m.length;
        for (int i=n-1; i>index; i--){
            a[i]=a[i-1];
            m[i]=m[i-1];
        }
    }
    protected void someSleep(long ms){
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void bytesToIntArray(byte[] bytes, double[] doubles, int offset, int number){
        int max=offset+number;
        for (int i=offset,j=0;i<max;i++,j+=2){
            doubles[i]=((double)bytesToInt2(bytes,j)/32768d);// rationalized
        }
    }
    protected int bytesToInt2(byte[] bytes, int offset){
        int a;
        a=(bytes[offset]<<8 & 0xFF00)+(bytes[offset+1] & 0xFF);
        if (bytes[offset]<0) {a=a|0xFFFF0000;}
        return a;
    }
    protected void fillBytes(byte[] bytes,AudioInputStream stream){
        try {
            stream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected double[] getSignal(int i, int n, byte[][] bytes){
        double[] signal=new double[FOURIER_N];
        for (int j=i,l=0; l<n; j++, l++){
            bytesToIntArray(bytes[((i+j)%n)],signal,l*FOURIER_N/n,FOURIER_N/n);
        }
        return signal;
    }
    protected void getSignal(AudioInputStream stream ,byte[] bytes,double[] signal) {
        try {
            for (int i = 0; i < 4; i++) {
                stream.read(bytes);
                bytesToIntArray(bytes, signal, i * FOURIER_N/ 4,FOURIER_N / 4);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
