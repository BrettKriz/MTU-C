package cs2321;
import net.datastructures.*;
import cs2321.*;

/**
 * A test driver for the three PriorityQueues.
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author Chris Brown (cdbrown@mtu.edu)
 */
public class TestingPQ {
    /**
     * Simple test driver. For Assignment 5, it will need
     * to be changed to include the PQSort algorithm.
     * 
     * @param args unused
     */
    public static void main(String [] args) {
        MaxPriorityQueue<Integer,String> unordpq = new UnorderedPQ<Integer,String>();
        MinPriorityQueue<String, Integer> ordpq = new OrderedPQ<String, Integer>();
        MaxPriorityQueue<Double, Integer> heap = new Heap<Double, Integer>();
        CompleteTree<QEntry<Integer,String>> heap2;
        Entry e;

        /*
         * Unordered Priority Queue tests
         */

        //*# Remove the first '/' in this comment to comment out the unordered priority queue tests
        System.out.println("\n<> UNORDERED PQ TEST<>");
        unordpq.insert(16, "Bulbous Bouffant"); // 0
        unordpq.insert(6, "Gazebo");//1
        unordpq.insert(7, "Balooga");//2
        unordpq.insert(8, "Galoshes");//3
        unordpq.insert(6, "Eskimo");//4
        unordpq.insert(7, "Mukluks");//5
        unordpq.insert(9, "Macadamia");//6
        // 16,9,8,7,7,6,6

        heap2 = new CompleteTree<QEntry<Integer,String>>(unordpq);
        heap2.removeLast();
        heap2.removeLast();
        heap2.removeLast();
        heap2.removeLast();
        
        e = unordpq.max();
        System.out.println("\"max()\" - Key: " + e.getKey() + " | Size: " + unordpq.size() );
        int y = 0;
        while(!unordpq.isEmpty()){
            if (y == 5){
                //System.out.println("DEBUG HERE!");
            }
                e = unordpq.removeMax();
                System.out.println(">\"removeMax()\" - Key: " + e.getKey() + ", Value: " + e.getValue());
                y++;
        }
        //*# ending comment */

        /*
         * Ordered Priority Queue tests
         */

        //*# Remove the first '/' in this comment to comment out the ordered priority queue tests
        System.out.println("\n<> ORDERED PQ TEST <>");
        ordpq.insert("Bulbous Bouffant", 16);//0
        ordpq.insert("Gazebo", 6);//1
        ordpq.insert("Balooga", 7);//2
        ordpq.insert("Galoshes", 8);//3
        ordpq.insert("Eskimo", 6);//4
        ordpq.insert("Mukluks", 7); // 5
        ordpq.insert("Macadamia", 9);//6
        // {7,16,6,8,6,
    System.out.println( ordpq.size()  );

        e = ordpq.min();
        System.out.println("\"min()\" - Key: " + e.getKey() + " | Size: " +  ordpq.size() );
        int x = 0;
        while(!ordpq.isEmpty()){
                if (x == 5){
                    //System.out.println("DEBUG HERE!");
                }

                e = ordpq.removeMin();
                System.out.println(">\"removeMin()\" - Key: " + e.getKey() + ", Value: " + e.getValue());
                x++;
        }
        //*# ending comment */

        /*
         * Heap tests
         */

        //*# Remove the first '/' in this comment to comment out the heap tests
        System.out.println("\n<> HEAP TEST <>");
        heap.insert(321.2, 977);
        heap.insert(779.59817432, 624);
        heap.insert(818.728, 50);
        heap.insert(917.596352, 216);
        heap.insert(430.0, 547);
        heap.insert(197.6649, 38);
        heap.insert(50.598212865, 965);

        e = heap.max();
        System.out.println("\"max()\" - Key: " + e.getKey() + " | Size: " + heap.size() );

        while(!heap.isEmpty()){
                e = heap.removeMax();
                System.out.print(">\"removeMax()\" - Key: " + e.getKey() + ", Value: " + e.getValue() );
                if (e instanceof QEntry){
                    System.out.println(" " + ((QEntry)e).index);
                }else{
                    System.out.println("?");
                }
        }
        //*# ending comment */

    }	
} // End Main
 