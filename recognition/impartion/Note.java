package recognition.impartion;
//Sasha

import java.util.Observable;
import java.util.Observer;

/**
 * Created by 1 on 05.05.2014.
 */
public class Note implements Observer {//probably there will be only one observer
    public void update(Observable A, Object currentNote){
       stopAll();
       /*if (((MonoRecognizer) A).strength>0) System.out.println("current note is "+(int) currentNote);
        ((MonoRecognizer) A).strength=0;*/
        if ((int)currentNote!=-1) System.out.println("current note is "+(int) currentNote);
    }
    private void stopAll(){
        //
    };
}
