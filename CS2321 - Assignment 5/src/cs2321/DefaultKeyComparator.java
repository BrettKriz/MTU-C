package cs2321;

import net.datastructures.*;

public class DefaultKeyComparator<K> implements java.util.Comparator<K> {
	
	/**
	 *  Uses the compareTo method to compare, or if not Comparable, throws an error. 
	 */
	@SuppressWarnings("unchecked")
	public int compare(K o1, K o2) {
		if(o1 instanceof Comparable){
			return ((Comparable<K>) o1).compareTo(o2);
		} else {
			throw new InvalidKeyException("A comparator must be provided for this type.");
		}
	}

}
