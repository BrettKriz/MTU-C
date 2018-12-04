package cs2321;

import net.datastructures.*;

import java.util.Comparator;
import static cs2321.A5_Toolbox.*; // An added conceinence file

public class LookupTable<K, V> implements Dictionary<K, V> {
    
    private int size = 0;
    private QEntry<K,V>[] data; // ORDERED
    private Comparator<K> comparator;

    public LookupTable(){
            comparator = new DefaultKeyComparator<K>();
            this.data = new QEntry[1];
            // TODO Add necessary initialization
    }

    /**
     * 
     * @param comparator - on construction, set comparator
     */
    public LookupTable(Comparator<K> comparator) {
            this.comparator = comparator;
            this.data = new QEntry[1];
    }

    /**
     * @param comparator the comparator to set
     */
    public void setComparator(Comparator<K> comparator) {
            this.comparator = comparator;
    }

    public Iterable<Entry<K, V>> entries() {
        
        LinkedSequence<Entry<K,V>> lEntries = new LinkedSequence<>();
        for ( Entry<K,V> cur : this.data )
        {   
            lEntries.addLast( cur );
        }   
        return lEntries;
        //
    }

    @TimeComplexity("o(Lg(n))")
    public Entry<K,V> find(K key) throws InvalidKeyException { // o(log n)
        // TODO Auto-generated method stub
        // Use Binary Search for Log N
        checkKey(key);
        
        int i = this.binarySearch(data, key); // o(log n)
        
        if (i == -1){
            return null;
        }else{
            this.data[i].index = i;
            return this.data[i];
        }
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public Iterable<K> keySet() {
        LinkedSequence<K> ans = new LinkedSequence<K>();

        for (Entry<K,V> cur : this.data){
            ans.addLast( cur.getKey() );
        }

        return ans; // CHECK REQUIRED TYPE
    }

    @TimeComplexity("o(n)") @TimeComplexityAmortized("o(n)")
    public Entry<K,V> insert(K key, V value) throws InvalidKeyException {// O(n)
        QEntry<K,V> newdata = new QEntry<K,V>(key,value);
        // Checks
        checkEntry(newdata);
        checkSize(); // RESIZE! o(n)
        int i = size()-1;
        
        // Sort
        while(i >= 0 
                && comparator.compare( data[i].getKey(), key ) > 0){
            this.data[i+1] = this.data[i];
            this.data[i+1].index = i+1;
            i--;
        }
        
        newdata.index = i+1;
        this.data[i+1] = newdata;
        this.size++;
        
        return newdata;
    }
    
    @TimeComplexity("o(n))")
    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {// O(n)
        checkEntry(e);
        
        for (Entry<K,V> cur : this.data){
            // Find the exact Entry
            if (e.getKey().equals(cur.getKey()) && e.getValue().equals(cur.getValue())){
                // Found
                Entry<K,V> t = cur;
                cur = null;
                this.size--;
                return t;
            }
        }

        return null;
    }

    public int size() {
        return this.size;
    }

    public Iterable<V> values() { // O(N)
        LinkedSequence<V> ans = new LinkedSequence<V>();
        
        for (Entry<K,V> cur : this.data){
            ans.addLast(cur.getValue());
        }
        
        return ans;
    }

    public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException // O(N)
    {
        /*# Remember: If no matches are found, do NOT return null.
         *  Return an empty Iterable */
        // Sorted, so this shouldn't be hard
       checkKey(key);
       LinkedSequence<Entry<K,V>> ans = new LinkedSequence<Entry<K,V>>();

       for (Entry<K,V> cur : this.data){
           if (cur != null &&  key.equals(cur.getKey())){
               ans.addLast(cur);
           } // After not encoutering the same key, we can break for efficency
       }

       return ans;
    }
	
    /**
     * Return an iterable collection of all Entries with keys within
     * the given range (inclusive).
     *
     * @param start Starting key value
     * @param stop Ending key value
     * @return Iterable collection of matching entries. If no matches
     *         are found the returned Iterable is empty.
     */
    public Iterable<Entry<K,V>> getRange(K start, K stop) { // o(n)
    	/*# Remember: If no matches are found, do NOT return null.
    	 *  Return an empty Iterable */
        LinkedSequence<Entry<K,V>> ans = new LinkedSequence<Entry<K,V>>();
        boolean going = false;
        
        for (Entry<K,V> cur : this.data){
            if (going){
                ans.addLast(cur);
                if (stop.equals( cur.getKey() )){
                    going = false;
                    // INCLUSIVE!
                    break;
                }
            }else if (!going && start.equals( cur.getKey() )){
                // We need to start recording
                going = true;
                ans.addLast(cur);
            }
        }
        
    	return ans;
    }
    
    @TimeComplexity("o(Lg(n))")
    public int binarySearch(Entry<K,V>[] A, K k){
         // Convienece method
        return this.binarySearch(A, k, 0, size()-1);
    }
    @TimeComplexity("o(Lg(n))")
    public int binarySearch(Entry<K,V>[] A, K k, int L, int R){ // O(Log n)
        
        if (L <= R){
            int mid = (L+R)/2; // Allow for casting
            
            if ( A[mid].getKey().equals(k) ){
                return mid;
            }else if ( comparator.compare( A[mid].getKey() , k ) < 0 ){
                return this.binarySearch(A, k, mid+1, R);
            }else{
                return this.binarySearch(A, k, L, mid-1);
   
             }
        }
    
        return -1;
    }
    
    public void checkSize(){
        if (size() == data.length){
            // Resize array
            QEntry<K,V>[] newdata ;
            newdata = new QEntry[ Math.max( 2*size(), 2 ) ];
            
            for (int i = 0; i < size(); i++){
                newdata[i] = this.data[i];
            }
            this.data = newdata;
        }
    }
}
