package ru.mipt.cs.easypiano.recognition.aggregation;
//SASHA

import ru.mipt.cs.easypiano.recognition.aggregation.wavhelp.WavFile;

import java.io.File;
import java.util.Vector;

/**
 * Created by 1 on 26.04.2014.
 */
public class WavWrapper {
    private final int BUFFER_SIZE=100;//Constant
    private String path;
    private int numChannels;
    private long sampleRate;//Hz
    private long time;
    private Vector arrays;
    private WavFile wavFile;
    public WavWrapper(String s){
        path=s;
        try
        {
            wavFile = WavFile.openWavFile(new File(path));
            numChannels = wavFile.getNumChannels();
            sampleRate = wavFile.getSampleRate();
            getArrays();
            wavFile.close();
            setTime(((double)sampleRate/1000d));
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    public double[] getArray(int number,int offset, int size){
        double[] A = new double[size];
        if ((number>=arrays.size())||((size+offset)>(((Vector) arrays.get(number)).size()))){
            System.out.println("ERROR");
        }else{
            int max=offset+size;
            int j=0;
            for (int i=offset;i<max;i++,j++){
                A[j]=(double)((Vector) arrays.get(number)).get(i);
            }
        }
        return A;
    }
    public void getArrays(){
        arrays = new Vector();
        for (int i=0; i<numChannels;i++){
            arrays.add(new Vector());
        }
        double buffer[];
        buffer = new double[BUFFER_SIZE * numChannels];
        int framesRead;

        try {
            do {
                framesRead = wavFile.readFrames(buffer, BUFFER_SIZE);//stores framesRead*numChannels to buffer
                for (int s = 0; s < framesRead * numChannels; s+=numChannels) {
                    for (int numChannel=0; numChannel<numChannels; numChannel++){
                        ((Vector)(arrays.get(numChannel))).add(buffer[s+numChannel]);
                    }
                }
            } while (framesRead != 0);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    private void setTime(double slotPerMilisec){//sets time in miliseconds
        time = (long)(((double) ((Vector) arrays.get(0)).size())/slotPerMilisec);
    }
    public long getSampleRate(){
        return sampleRate;
    }
    public int getNumChannels() {return numChannels;}
    public long getTime(){return time;}
    public long getSize(){return ((Vector) arrays.get(0)).size();}
}
