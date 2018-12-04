package Main;

import Server.ConnectionThread;
import java.util.Scanner;
import java.lang.String;
import java.lang.Throwable;
import java.lang.Exception;
import java.io.*;
import java.lang.Character;
import java.util.Arrays;
/**
 *
 * @author Xazaviar
 */
public class Main {

    public static void main(String[] args){
        ConnectionThread connect = new ConnectionThread();
        new Thread(connect).start();
    } 
}
