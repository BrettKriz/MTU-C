package Server;

import Drawing.MainGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Xazaviar
 */
public class InputThread implements Runnable{

    private BufferedReader input = null;
    private PrintWriter output = null;
    private MainGUI mainGUI;
    
    /**
     * The default constructor (not in use)
     */
    public InputThread(){}   
    
    public InputThread(BufferedReader input, PrintWriter output){
        this.input = input;
        this.output = output;
        mainGUI = new MainGUI();
    }

    public MainGUI mainGUI(){
        return this.mainGUI;
    }
    
    public void run() {
        while(true){
            try {
                output.println(setupData(mainGUI.keyPresses())); //To server
                
                String prints = input.readLine();
//                System.out.println(prints);
            } catch (IOException ex) {
                System.out.println("Error getting output from server.");
                break;
            }
            sleep(30);
        }
        this.close();
    }
    
    public String setupData(boolean[] keys){
        String ret = "";
        for(int i = 0; i< keys.length; i++){
            if(keys[i]) ret += "1 ";
            else        ret += "0 ";
        }
        return ret;
    }
    
    public void close(){
        try{
            this.input.close();
        }catch(IOException e){
            throw new RuntimeException("Error closing.", e);
        }
        this.output.close();
        System.exit(0);
    }
    
    /**
     * Tells the thread to sleep for a set amount of time
     * @param dur 
     *          The duration the thread sleeps
     */
    public void sleep(int dur){
        try{
        Thread.sleep(dur);
        }catch(InterruptedException e){
            System.out.println("Room has been interrupted");
        }
    }
}
