/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SSAL;
import java.util.*;
/**
 *
 * @author Brett Kriz
 */
public class BFSearch<E> implements Search<E> {
    public List<E> search(Domain<E> domain){
        Map<E, E> visited = new HashMap<E, E>();
        Queue<E> frontier = new LinkedList<E>();
        
        E start = domain.start();
        frontier.add(start);
        visited.put(start,null);
        
        while(!frontier.isEmpty()){
            E current = frontier.remove();
            
            if(domain.isGoal(current)){
                List<E> path = new ArrayList<E>();
                
                E node = current;
                while(node != null){
                    path.add(0, node);
                    node = visited.get(node);
                }
                
                for(E next : domain.expand(current) ){
                    if(!visited.containsKey(next)){
                        frontier.add(next);
                        visited.put(next, current);
                    }
                }
            } // Not sure where return goes in scopes
        }
        
        
        
        return null;
    }
}
    
