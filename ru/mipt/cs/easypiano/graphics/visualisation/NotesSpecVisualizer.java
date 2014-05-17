package ru.mipt.cs.easypiano.graphics.visualisation;
//Sasha
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.mipt.cs.easypiano.recognition.analysis.Notes;

import javax.swing.*;

/**
 * Created by 1 on 07.05.2014.
 */
public class NotesSpecVisualizer implements Visualizer {
    public void visualize(double array[], long n){
        //here n is considered to be note number
        XYSeries seriesmag = new XYSeries("Note spectrum");
        for (int j=0; j<Notes.NOTES_QUANTITY;j++){
            seriesmag.add(j+Notes.FIRST_NOTE, array[j]);
        }
        XYDataset xyDataset = new XYSeriesCollection(seriesmag);
        JFreeChart chart = ChartFactory.createXYLineChart("y=relative amplitude", "harmonics", "magnitude",
                xyDataset, PlotOrientation.VERTICAL, true, true, true);
        JFrame frame = new JFrame("spectrum of note # "+(n));
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
