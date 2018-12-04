import java.util.*;

/**
 * Lab 9
 * @author Brett Kriz
 */
public class Problem1 {
    /**
    * Generates an adjacency matrix from the corresponding Map
    * description of a graph. The Map is guaranteed to be
    * symmetric, and so the adjacency matrix should also be
    * symmetric.
    * <p>
    * If the Map is empty, the adjacency matrix should have size
    * 0, 0.
    *
    * @param graph a graph represented as a Map
    * @return the adjacency matrix representation of the specified
    * graph
    */
    public static double[][] construct(Map <Integer , List <Integer >> graph){
        double[][] ans = {{}};
        if (graph.isEmpty()){
            return ans;
        }
        int size = graph.size();
        ans = newMatrix(size);
        
        for(Map.Entry cur : graph.entrySet()){
            // Hook me up
            List<Integer> vals = (List<Integer>)cur.getValue();
            if( !vals.isEmpty() ){
                //System.out.println();
                for(Integer v : vals){
                    // ADD EM IN OUR MATRIX
                    Integer k = (Integer)cur.getKey();
                    
                    if (!v.equals(k)){
                        // [][]
                        ans[k][v] = 1.0;
                    }else{
                        ans[k][v] = 0.0;
                    }
                }
            }
        }
        
        
        return ans;
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
    public static void main(String[] ar){
        //construct();
    }
}
