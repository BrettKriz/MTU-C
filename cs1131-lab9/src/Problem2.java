import java.util.*;

/**
 * Lab 9
 * @author Brett Kriz
 */
public class Problem2 {
    /**
    * Determines if the specified adjacency matrix represents
    * a complete graph.
    *
    * @param graph the adjacency matrix representation of a
    * graph
    * @return true if the graph is complete; false otherwise
    */
    public static boolean isComplete(double [][] graph ){
        if (graph.length < 2){
            return true;
        }
        // All are connected?
        for(int x = 0; x < graph.length; x++){
            for (int y = 0; y < graph[x].length ; y++){
                
                if          (x == y && graph[x][y] == 0.0){
                    // Keep goin
                }else if    (x != y && graph[x][y] == 1.0){
                    
                }else{
                    return false;
                }// END IF
            }
        }
        return true;
    }
}
