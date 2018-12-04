package net.datastructures;

/** Interface for the priority queue ADT */
/** Code copied from the original PriorityQueue<K,V> interface */
/** @author Dustin Larson */
public interface MaxPriorityQueue<K, V> extends PriorityQueue<K, V>
{
	/** Returns but does not remove an entry with maximum key. */
	public Entry<K, V> max() throws EmptyPriorityQueueException;

	/** Removes and returns an entry with maximum key. */
	public Entry<K, V> removeMax() throws EmptyPriorityQueueException;
}
