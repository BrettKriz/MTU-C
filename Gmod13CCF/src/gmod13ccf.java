/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Gmod 13 Code Change Finder
 * @author Brett Kriz
 */

import java.util.Scanner;
import java.text.NumberFormat;
import java.lang.Math; //right below class statement
import java.util.Random;
import java.util.Vector;
import java.util.Arrays;
import javax.swing.*; // For prompts and shit
import java.io.File;


public class gmod13ccf {
    static  Random r = new Random();  //Calls constructor
    static Scanner scan = new Scanner(System.in); //Setup keyboard input
    static String FileDir = null;
    static JPanel Pane = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Garry is such an asshole...");
        init();
        startScan();
        
        JOptionPane.showConfirmDialog(null, "We done here?");
    }
    
    public static void init(){
        Print("Initializing program...");
        FileDir = "C:";
        Pane = new JPanel();
        paneInit(Pane);
        
        Print("Done initializing...");
    }
    
    public static void paneInit(JPanel lol){
        if (lol == null) {Print(">> PANE NOT VALID");
        return;}
        
        lol.setSize(30, 50);
        lol.setEnabled(true);
        
    }
           
    public static void startScan(){
        // start reading in files
        askDirectory(0);
    }
    
    public static void askDirectory(int count){
        // Ask the user for directory to search from
        Print(">>> Input Directory: ");
        FileDir = JOptionPane.showInputDialog("Input file path to look from:");
        
        if (FileDir.toLowerCase().contains("<accountname>")){
            if (count < 2)
            JOptionPane.showMessageDialog(null, "You dipshit! Fill out your ACCOUNT NAME!");
            // lol
            askDirectory(count+1);
        }
        cleanDirectoryName();
        Print(FileDir);
        
    }
    
    public static void cleanDirectoryName(){
        // Backslash bullshit
        FileDir = FileDir.replaceAll("\n", "/n");
        FileDir = FileDir.replaceAll("\t", "/t");
        //FileDir = FileDir.replaceAll("\\", "/");
        FileDir = FileDir.replaceAll("\b", "/b");
        FileDir = FileDir.replaceAll("\r", "/r");
        FileDir = FileDir.replaceAll("\f", "/f");
        FileDir = FileDir.replaceAll("\'", "/'");
        FileDir = FileDir.replaceAll("\"", "/\"");
        
        
        
    }
    public static void Print(String arg){
        System.out.println("Done initializing...");
    }
}
