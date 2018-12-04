package Server;

import GameMechanics.Entity;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Xazaviar
 */
public class ConnectionThread implements Runnable{

    private final int serverPort = 6453;
    private final String ipAddress = "141.219.157.139";
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;
    
    private InputThread in;
    
    /**
     * The default constructor
     */
    public ConnectionThread(){
        while(true){
            try{
                System.out.println("Attempting to connect...");
                socket = new Socket(ipAddress,serverPort); //This is the connection to the server
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                
                in = new InputThread(input, output);
                new Thread(in).start();
                break;
            }catch(Exception e){
                System.out.println("Could not connect");
            }
        }
        
        System.out.println("Connected.");
    }   

    public void run() {
        while(true){
            try {
                String inn = input.readLine();
                Scanner scan = new Scanner(inn);
                ArrayList<Entity> data = new ArrayList<Entity>();
                int health = scan.nextInt();
                int gold = scan.nextInt();
                int x, y, s;
                String t;
                t = scan.next();
                x = scan.nextInt();
                y = scan.nextInt();
                s = scan.nextInt();
//                System.out.println("P: "+s);
                data.add(new Entity(t, x, y,s));
                while(scan.hasNext()){
                    t = scan.next();
                    x = scan.nextInt();
                    y = scan.nextInt();
                    s = scan.nextInt();
//                System.out.println("B: "+s);
                    data.add(new Entity(t, x, y, s));
                }
                
                in.mainGUI().HUDVars(health, gold);
                in.mainGUI().coords(data);
                in.mainGUI().display();
                
                
                
            } catch (IOException ex) {
                System.out.println("Error getting output from server.");
                break;
            } catch (java.lang.NullPointerException e){
                this.close();
            }
            sleep(5);
        }
        this.close();
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
