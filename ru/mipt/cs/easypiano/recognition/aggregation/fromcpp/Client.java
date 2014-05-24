package ru.mipt.cs.easypiano.recognition.aggregation.fromcpp;

import ru.mipt.cs.easypiano.piano.Control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by 1 on 23.05.2014.
 */

public class Client {
    private Control clientControl;
    public Client(Control clientControl){
        this.clientControl=clientControl;
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() throws IOException {

        System.out.println("Welcome to Client side");

        Socket fromserver = null;

        /*if (args.length==0) {
            System.out.println("use: client hostname");
            System.exit(-1);
        }*/

        //System.out.println("Connecting to... "+args[0]);
        //System.out.println("Connecting to..."+InetAddress.getLocalHost());
        //int portNumber = 4444;
        int portNumber = 27015;
        fromserver = new Socket(/*InetAddress.getLocalHost()*/"192.168.1.8",portNumber);
        //fromserver = new Socket(args[0],portNumber);
        BufferedReader in  = new
                BufferedReader(new
                InputStreamReader(fromserver.getInputStream()));
        PrintWriter out = new
                PrintWriter(fromserver.getOutputStream(),true);
        BufferedReader inu = new
                BufferedReader(new InputStreamReader(System.in));
        System.out.println("connected!");
        /*String fuser,fserver;

        while ((fuser = inu.readLine())!=null) {
            out.println(fuser);
            fserver = in.readLine();
            System.out.println(fserver);
            if (fuser.equalsIgnoreCase("close")) break;
            if (fuser.equalsIgnoreCase("exit")) break;
        }*/
        /*char[] t = new char[3];
        t[0]=1; t[1]=1; t[2]=1;
        out.write(t);
        for (int i=0; i<515; i++){
            out.append(t[0]);
            System.out.println("done i= "+i);
        }*/
        char[] buf = new char[3];
        int got;
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()-startTime)<50000){//50 seconds
            //System.out.println("oo");
            //got = in.read(buf,0,3);
            got = in.read(buf);
            //System.out.println("got = "+got);
            switch(got){
                case 3:
                    int noteNum=((int) buf[0])-36;
                    int onOrOff=(int) buf[1];
                    int instrumentNum = (int) buf[2];
                    sendSignal(noteNum,onOrOff,instrumentNum);
            }
        }
        /*try{

        }catch(InterruptedException e){
            out.write(0);
        }*/

        out.close();
        in.close();
        inu.close();
        fromserver.close();
    }
    private void sendSignal(int noteNum, int onOrOff, int instrument){
        //yet is empty
        clientControl.pianoKeyPressed(noteNum);
        //System.out.println("note: "+noteNum+" onOrOff: "+onOrOff+" instrument: "+instrument);
    }
}

