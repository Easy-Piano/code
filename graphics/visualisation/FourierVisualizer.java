package graphics.visualisation;
//SASHA
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import recognition.analysis.Notes;

import javax.swing.*;

/**
 * Created by 1 on 26.04.2014.
 */
public class FourierVisualizer implements Visualizer {
    private double minFrequency;
    public void visualize(double[] array,long sampleRate){//amplitude, not phase
        XYSeries seriesmag = new XYSeries("Spectrum magnitude");
        minFrequency=sampleRate/N;
        for (int j=0; j<N/2;j++){
            seriesmag.add(minFrequency*(double)j, array[j]);
        }
        XYDataset xyDataset = new XYSeriesCollection(seriesmag);
        JFreeChart chart = ChartFactory.createXYLineChart("y=signal spectrum", "frequency,Hz", "magnitude",
                xyDataset, PlotOrientation.VERTICAL, true, true, true);
        JFrame frame = new JFrame("spectrum try");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800,600);
        frame.setVisible(true);

        double max=0;
        int maxnum=0;
        for (int i=0; i<N/2; i++){
            if (array[i]>max) {max=array[i];maxnum=i;}
        }
        System.out.println("Min frequency is "+minFrequency+" Max is on frequency "+(maxnum*minFrequency)+"+-"+minFrequency/2+" Max amplitude is "+max);
        System.out.println("note number is "+ ((Notes) Notes.getInstance()).getNoteNumber(maxnum * minFrequency));
    }
}
