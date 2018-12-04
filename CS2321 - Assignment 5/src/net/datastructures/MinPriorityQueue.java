package net.datastructures;

/** This class was created in order to distinguish from the MaxPriorityQueue
 *  and was not part of the original net.datastructures package.
 *  
 *  The min() and removeMin() were removed from the original PriorityQueue interface
 *  and put here in order that MaxPriorityQueue and MinPriorityQueue could both extend
 *  PriorityQueue.
 *  
 *  @author Dustin Larson
 */
public interface MinPriorityQueue<K, V> extends PriorityQueue<K, V>
{
	  /** Returns but does not remove an entry with minimum key. */
	  public Entry<K,V> min() throws EmptyPriorityQueueException;

	  /** Removes and returns an entry with minimum key. */
	  public Entry<K,V> removeMin() throws EmptyPriorityQueueException;

}
