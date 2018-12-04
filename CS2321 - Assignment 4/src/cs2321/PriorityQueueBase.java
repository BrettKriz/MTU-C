package cs2321;
import java.util.*;
import net.datastructures.*;


/**
 * Template PriorityQueue
 * 
 * 
 * @author Brett Kriz
 */
@SpaceComplexity("O(n)")
abstract class PriorityQueueBase<K extends Comparable<K>, V > { // extends Comparable<K>
    protected ArraySequence< QEntry<K,V> > backing;
    
    @TimeComplexity("O(1)")
    public PriorityQueueBase(){
        this.backing = new ArraySequence< QEntry<K,V> >();
    }
    
    @TimeComplexity("O(?)")
    public abstract Entry<K,V> insert(K key, V value);
    
    @TimeComplexity("O(1)")
    public boolean isEmpty() {
            return backing.isEmpty();
    }
    
    @TimeComplexity("O(1)")
    public int size() {
            return backing.size();
    }
    
    @TimeComplexity("O(1)")
    public Iterator< QEntry<K,V> > iterator() {
            return backing.iterator();
    }
}
