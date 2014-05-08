package test.sasha;
//SASHA
import graphics.visualisation.FourierVisualizer;
import graphics.visualisation.SignalVisualizer;
import graphics.visualisation.Visualizer;
import recognition.aggregation.WavWrapper;
import recognition.analysis.FFT;

/**
 * Created by 1 on 03.05.2014.
 */
public class SpectrumTest implements Test{
    /*
       draws amplitude(time) graph for every channel, then draws spectrum of one frame for the first channel
     */
    public static void main(String[] args){
        String s=resoursePath;
        s+="upr_02.wav";
        WavWrapper WW=new WavWrapper(s);
        long sampleRate = WW.getSampleRate();

        SignalVisualizer SV=new SignalVisualizer();
        for (int i=0; i<WW.getNumChannels(); i++){
            SV.visualize(WW.getArray(i, OFFSET, SIZE),sampleRate);
        }
        double[] signal=WW.getArray(0,OFFSET_F, Visualizer.N);
        double[] spectrum= FFT.RealToReal(signal, 0);
        FourierVisualizer FV = new FourierVisualizer();
        FV.visualize(spectrum,sampleRate);
    }
}
