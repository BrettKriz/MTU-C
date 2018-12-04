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
public class Main {
    public static void main(String[] args){
        
    }
    private enum Thing { Wolf, Sheep, Cabbage, Farmer; }
    
    private class State{
        Set<Thing> lSet = new HashSet<Thing>();
        Set<Thing> rSet = new HashSet<Thing>();
        
        public State(){
            lSet.add(Thing.Wolf);
            lSet.add(Thing.Sheep);
            lSet.add(Thing.Cabbage);
            lSet.add(Thing.Farmer);
        }
    }
    
    public interface Domain<E>{
        
    }
    public class StateDomain implements Domain<State>{
        public State start(){
            return new State();
        }
        public boolean isGoal(State e){
            return (e.lSet.isEmpty() && e.rSet.size() == 4);
        }
        public Set<State> expand(State e){
            Set<State> expansions = new HashSet<State>()
            if (e.lSet.contains((Thing.Farmer))){
                
            }else{
                
            }
        }
    }
    public E start();
    public boolean isGoal(E e);
    public Set<E> expand(E e);
}
