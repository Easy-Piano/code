package test.sasha;
//Sasha
import java.io.File;

/**
 * Created by 1 on 10.05.2014.
 */
public class Renamer {
    public static void rename(String old,String superseder){
        File file = new File(old);
        File file2 = new File(superseder);
        boolean success = file.renameTo(file2);
        if (!success) {
            System.out.println("can't rename "+old+" to "+superseder);
        }
    }
}
