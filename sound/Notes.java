package ru.mipt.cs.easypiano.sound;

/**
 * Created by 1 on 27.04.2014.
 */
public class Notes {//works fine
    private static double [] frequencies;
    private static final int NOTES_QUANTITY = 120;
    public Notes(){
        frequencies=new double[NOTES_QUANTITY];
        for (int i=0;i<NOTES_QUANTITY;i++){
            frequencies[i]=27.5*Math.pow(2,((double)(i-9)/(double)12));
        }
    }
    public static double getNoteFrequency(int n){
        return frequencies[n];
    }
    public static int getNoteNumber(double f){
        int n=binarySearch(f,0,NOTES_QUANTITY);
        if ((frequencies[n+1]-f)>(f-frequencies[n])){
            return n;
        } else return n+1;
    }
    private static int binarySearch(double f, int l, int r){
        if ((r-l)==1) return l;
        int t=(r+l)/2;
        if (f<frequencies[t]){
            return binarySearch(f,l,t);
        }else {
            return binarySearch(f,t,r);
        }
    }
}
