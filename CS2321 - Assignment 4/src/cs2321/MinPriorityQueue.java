package cs2321;
import net.datastructures.*;

/** This class was created in order to distinguish from the MaxPriorityQueue
 *  and was not part of the original net.datastructures package.
 *  
 *  The min() and removeMin() were removed from the original PriorityQueue interface
 *  and put here in order that MaxPriorityQueue and MinPriorityQueue could both extend
 *  PriorityQueue. 
 *  
 *  @author Dustin Larson
 */
@SpaceComplexity("O(n)")
public interface MinPriorityQueue<K, V> extends PriorityQueue<K, V>
{   
    /** Returns but does not remove an entry with minimum key. */
    @TimeComplexity("O(?)")
    public Entry<K,V> min() throws EmptyPriorityQueueException;

    /** Removes and returns an entry with minimum key. */
    @TimeComplexity("O(?)")
    public Entry<K,V> removeMin() throws EmptyPriorityQueueException;

    @TimeComplexity("O(?)")
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException;

    @TimeComplexity("O(?)")
    public boolean isEmpty();

    @TimeComplexity("O(?)")
    public int size();
}
