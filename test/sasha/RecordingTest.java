package test.sasha;
//SASHA
import graphics.visualisation.RecordDialog;

/**
 * Created by 1 on 04.05.2014.
 */
public class RecordingTest implements Test{
    /*
        tested recording ability
     */
    public static void main(String args[]){
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecordDialog().setVisible(true);
            }
        });*/
        new RecordDialog().setVisible(true);
    }
}
