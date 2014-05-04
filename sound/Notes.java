package ru.mipt.cs.easypiano.sound;
//SASHA
/**
 * Created by 1 on 27.04.2014.
 */
public class Notes {
    private static double [] frequencies;
    private static final int NOTES_QUANTITY = 120;
    private static boolean wasInitialized=false;
    private Notes(){
        frequencies=new double[NOTES_QUANTITY];
        for (int i=0;i<NOTES_QUANTITY;i++){
            frequencies[i]=27.5d*Math.pow(2d,((double)(i-9)/12d));
        }
        wasInitialized=true;
    }
    public static double getNoteFrequency(int n){
        if (!wasInitialized){
            Notes notes = new Notes();
        }
        return frequencies[n];
    }
    public static int getNoteNumber(double f){
        if (!wasInitialized){
            Notes notes = new Notes();
        }
        /*int n=binarySearch(f,0,NOTES_QUANTITY);
        if ((frequencies[n+1]-f)>(f-frequencies[n])){
            return n;
        } else return n+1;*/
        return hashSearch(f);
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
    private static int hashSearch(double f){
        int r = (int) ((Math.log(f/27.5d))*12d/Math.log(2)+9d);
        if (r>=NOTES_QUANTITY-1){
            r=NOTES_QUANTITY-1;
        }else if (r<=0) {
                r=0;
              }else{
                    r=(((frequencies[r+1]-f)>(f-frequencies[r]))?r:(r+1));
              }
        return r;
    }
}
