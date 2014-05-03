package ru.mipt.cs.easypiano.test.sasha;

import ru.mipt.cs.easypiano.graphics.visualisation.FourierVisualizer;
import ru.mipt.cs.easypiano.graphics.visualisation.SignalVisualizer;
import ru.mipt.cs.easypiano.graphics.visualisation.Visualizer;
import ru.mipt.cs.easypiano.recognition.aggregation.WavWrapper;
import ru.mipt.cs.easypiano.recognition.analysis.FFT;

import java.io.File;

/**
 * Created by 1 on 03.05.2014.
 */
public class SpectrumTest implements Test{
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
