import java.util.*;

/**
 * Lab 9
 * @author Brett Kriz
 */
public class Problem4 {
    /**
    * Determines if the specified adjacency matrix represents
    * a cycle graph.
    *
    * @param graph the adjacency matrix representation of a
    * graph
    * @return true if the graph is cycle graph; false otherwise
    */
    public static boolean isCycle(double [][] graph){
        if (graph.length < 3){
           // no love
            return false;
        }
        // Interconnected
        int size = graph.length;
        Map <Integer , List <Integer >> ans = new HashMap<>();
        
        for(int x = 0; x < size; x++){
            for (int y = 0; y < size ; y++){
                if (graph[x][y] == 1.0){
                    if (ans.containsKey(x)){
                        ans.get(x).add(y);
                    }else{
                        List<Integer> a = new ArrayList<>();
                        a.add(y);
                        ans.put(x,a);
                    }
                }
            }
        }
        // NOW CHECK
        for(Map.Entry cur : ans.entrySet()){
            List<Integer> w = (List<Integer>)cur.getValue();
            if (w.size() >= 2){
                // GREAT
            }else{
                return false;
            }
        }
        return true;
    }
}
