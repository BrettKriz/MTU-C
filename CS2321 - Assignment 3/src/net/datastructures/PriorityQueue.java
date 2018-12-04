package net.datastructures;

//begin#fragment PriorityQueue
/** Interface for the priority queue ADT */
public interface PriorityQueue2<K,V> {
  /** Returns the number of items in the priority queue. */
  public int size();
  /** Returns whether the priority queue is empty. */
  public boolean isEmpty();
  /** Inserts a key-value pair and return the entry created. */
  public Entry<K,V> insert(K key, V value) throws InvalidKeyException;
}
////end#fragment PriorityQueue
