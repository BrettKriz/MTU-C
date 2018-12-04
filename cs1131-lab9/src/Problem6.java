import java.util.*;

/**
 * Lab 9
 * @author Brett Kriz
 */
public class Problem6 {
    public final polar UP = new polar(0,1);
    public final polar DOWN = new polar(0,-1);
    public final polar LEFT = new polar(-1,0);
    public final polar RIGHT = new polar(1,0);
    /**
    * Finds all simple paths from a start node to an end
    * node in the graph represented by the specified
    * adjacency matrix.
    *
    * @param graph the adjacency matrix representation of a
    * graph
    * @param start the starting node
    * @param end the ending node
    * @return a set of all the paths
    */
    public static Set <List <Integer >> paths(double [][] graph , int start , int end){
        if (graph.length < 2){
            throw new IllegalArgumentException("Graph is too small!");
        }
        int size = graph.length;
        Set<List <Integer >> ans = new HashSet<>();
        // Create paths with tangent nodes
        
        for(int x = 0; x < size; x++){
            for (int y = 0; y < size ; y++){
                polar cur = new polar(x,y);
                
                if (isNode(cur,start) || isNode(cur,end)){
                    // nvm, skip
                    continue;
                }else{
                    // new path
                    List<Integer> path = new ArrayList<>();
                    path.add(start);
                    while(true){
                        // route to cur
                    }
                }
                
            }
        }
        
        
        return ans; 
    }

    public static boolean isNode(polar a, int t){
        if(a.x == t || a.y == t){
            return true;
        }
        return false;
    }
    public static class polar{
        public int x;
        public int y;
        
        public polar(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
