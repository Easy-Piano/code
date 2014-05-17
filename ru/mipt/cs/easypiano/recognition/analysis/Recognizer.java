package ru.mipt.cs.easypiano.recognition.analysis;
//SASHA

import java.util.Observable;

/**
 * Created by 1 on 02.05.2014.
 */
public class Recognizer extends Observable{
    public final static int FOURIER_N = 4096;
    //public final static int FOURIER_N = 8192;
    //public final static int FOURIER_N = 16384;
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
    protected int bytesToInt2(byte[] bytes, int offset){//should probably use isBigEndian()
        int a;
        a=(bytes[offset]<<8 & 0xFF00)+(bytes[offset+1] & 0xFF);
        if (bytes[offset]<0) {a=a|0xFFFF0000;}
        return a;
    }
}
