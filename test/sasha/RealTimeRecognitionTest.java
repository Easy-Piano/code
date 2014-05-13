package test.sasha;
//Sasha
import recognition.analysis.MonoRecognizer;

/**
 * Created by 1 on 10.05.2014.
 */
public class RealTimeRecognitionTest implements Test {
    /*
        recognizes 100% of separate notes on my computer from my second computer dynamics, used virtualpiano.net
        strongly calibrated for mentioned computer, in order to get better result on yours, create files
        note36...note96 for your own computer and instrument pair
     */
    public static void main(String[] args){
        /*Notes.getInstance().showSpec(36);
        Notes.getInstance().showSpec(41);*/
        //Notes.getInstance().showSpec(60);
        //Notes.getInstance().showSpec(96);
        new MonoRecognizer().realTimeRecognize();//initialization is first, may take long time for the first launch!
        //and Notes.RealTimeCalibrate() was not tested too much
    }
}
