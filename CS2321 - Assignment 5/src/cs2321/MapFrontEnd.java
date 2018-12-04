/**
 * 
 */
package cs2321;

import net.datastructures.*;

/**
 * This code is a complete front end Map for a dictionary, d.
 * 
 * @author cdbrown
 *
 */
public class MapFrontEnd<K, V> implements Map<K, V> {

	Dictionary<K,V> d;

	public MapFrontEnd(Dictionary<K,V> d){
		this.d = d;
		
		for(Entry<K,V> e: d.entries()){
			for(Entry<K,V> x: d.findAll(e.getKey())){
				if(x != e){
					throw new InvalidEntryException("Cannot have a Map with multiple, identically Keyed entries");
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.datastructures.Map#entries()
	 */
	public Iterable<Entry<K, V>> entrySet() {
		return d.entries();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#get(java.lang.Object)
	 */
	public V get(K key) throws InvalidKeyException {
		Entry<K, V> lEntry = d.find( key );
		if ( lEntry != null )
		{
			return d.find(key).getValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return d.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#keys()
	 */
	public Iterable<K> keySet() {
		Sequence<K> ret = new LinkedSequence<K>();
		for(Entry<K,V> e: d.entries()){
			ret.addLast(e.getKey());
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) throws InvalidKeyException {
		Entry<K,V> e = d.find(key);
		if(e != null){
			d.remove(e);
			d.insert(key, value);
			return e.getValue(); 
		} else {
			d.insert(key, value);
			return null; 
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#remove(java.lang.Object)
	 */
	public V remove(K key) throws InvalidKeyException {
		Entry<K,V> e = d.find(key);
		if(e != null){
			d.remove(e);
			return e.getValue(); 
		} else {
			return null; 
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#size()
	 */
	public int size() {
		return d.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Map#values()
	 */
	public Iterable<V> values() {
		Sequence<V> ret = new LinkedSequence<V>();
		for(Entry<K,V> e: d.entries()){
			ret.addLast(e.getValue());
		}
		return ret;
	}

}
