package ru.mipt.cs.easypiano.recognition.analysis.strategies;

/**
 * Created by 1 on 17.05.2014.
 */
public class WindowHamming implements Window{
    public void smoothen(double[] array){
        int n=array.length;
        double ppi=2*Math.PI;
        for (int i=0; i<n; i++){
            array[i]*=(0.53836d-0.46164d*Math.cos(ppi*(double)i/(double)(n-1)));
        }
    }
}
