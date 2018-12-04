package cs2321;

import net.datastructures.*;
import java.util.Scanner;

/**
 *
 * @author Brett Kriz
 */
public class Homework3 {
    // Expected Time Cost o(n+N)
    public void mostFreq(String arg){
        //repeated read a word from the file and update the time it appears
        //while read word from file
        HashMap<String,Integer> map = new HashMap();
        QEntry<String,Integer> best = null;
        Scanner in = new Scanner(arg);
        /*
        * check the current count for the word,
        * if the word has not appeared, then insert the new word into the map
        * otherwise, increase the count by 1
        */
        while(in.hasNext()){
            String cur = in.next().toLowerCase();
            Integer count = map.get(cur);
            //look through all entries in the map to find the most frequent word
            if (best == null){
                // Grab one..  simplifies 1 item cases
                best = new QEntry(cur,1);
            }
            
            if (count == null){
                // Add it
                map.put(cur, 1);
                
            }else{
                map.put(cur, count+1);
                
                if ((int)(count)+1 > (int)(best.getValue()) ){ // INT CAST COMP
                    best = new QEntry(cur,count+1); 
                }
            }
            
        }
        //print the word and number of times it appear.
        for ( Entry<String,Integer> cur : map.entrySet() ){
            // PRINT
            System.out.println();
        }
    }
    
    
}
