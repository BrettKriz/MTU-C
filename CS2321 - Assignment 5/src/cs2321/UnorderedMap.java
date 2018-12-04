/**
 * 
 */
package cs2321;

import net.datastructures.Map;

/**
 *  This class combines the Logfile with the MapFrontEnd to create an unordered
 *  map class.  All methods are already defined through inheritance.
 *  No additional code is necessary.
 * @author cdbrown
 */
public class UnorderedMap<K, V> extends MapFrontEnd<K, V> implements Map<K,V> {
	
	public UnorderedMap(){
		super(new Logfile<K,V>());
	}
}
