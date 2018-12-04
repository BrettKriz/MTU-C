import java.util.*;

/**
 * Lab 9
 * @author Brett Kriz
 */
public class Problem3 {
    /**
    * Determines if there is a path between two specified nodes
    * in a graph.
    *
    * @param graph the adjacency matrix representation of a
    * graph
    * @param node0 the starting node
    * @param node1 the ending node
    * @return true if a path exists between the specified nodes;
    * false otherwise
    */
    public static boolean pathExists(double [][] graph , int node0 , int node1){
        
        int size = graph.length;
        double[][] ans = newMatrix(size);
        
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size ; j++){
                for (int k = 0; k < size; k++){
                    ans[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }
        
        // check for infs
        Double t1 = graph[node0][node1];
        Double t2 = graph[node1][node0];
        Double t0 = Double.POSITIVE_INFINITY;
        
        if ( t0.equals(t1) || t0.equals(t2) ){
            return false;
        }
        
        return true;
    }
    public static double[][] newMatrix(int size){
        double[][] ans = new double[size][size];
        
        for(int x = 0; x < size; x++){
            for (int y = 0; y < size ; y++){
                ans[x][y] = Double.POSITIVE_INFINITY;
            }
        }
        return ans;
    }
}
