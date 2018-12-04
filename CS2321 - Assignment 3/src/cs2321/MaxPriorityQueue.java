package cs2321;
import net.datastructures.*;

/** Interface for the priority queue ADT */
/** Code copied from the original PriorityQueue<K,V> interface */
/** @author Dustin Larson */
@SpaceComplexity("O(n)")
public interface MaxPriorityQueue<K, V> extends PriorityQueue<K, V>
{
    /** Returns but does not remove an entry with maximum key. */
    @TimeComplexity("O(?)")
    public Entry<K, V> max() throws EmptyPriorityQueueException;

    /** Removes and returns an entry with maximum key. */
    @TimeComplexity("O(?)")
    public Entry<K, V> removeMax() throws EmptyPriorityQueueException;
    
    @TimeComplexity("O(?)")
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException;
    
    @TimeComplexity("O(?)")
    public boolean isEmpty(); // Had to add these, otherwise test case would cry

    @TimeComplexity("O(?)")
    public int size();
}