package cs2321;

import net.datastructures.*;
import java.util.Comparator;

public class HashMap<K, V> implements Map<K, V> { 

    protected UnorderedMap<K,V>[] buckets; // // Hash Table?table
    private int size = 0;

    protected final int mDefaultHashSize = 1021;

    /**
     * Constructor that takes a hash size
     * @param hashsize The number of buckets to initialize
     *                 in the HashMap
     */
    public HashMap(int hashsize){
            // TODO: Be sure to initialize the bucket array
            //       using the hashsize given as the number of buckets
        this.buckets = new UnorderedMap[ hashsize ];
        this.setup();
    }

    public HashMap(){
            // TODO: Be sure to initialize the bucket array
            //       using the default hash size provided.
        this.buckets = new UnorderedMap[ this.mDefaultHashSize ];
        this.setup();
    }

    private void setup(){
        for (int x = 0; x < buckets.length; x++){
            // No more null
            buckets[x] = new UnorderedMap<K,V>();
        }
    }

    @TimeComplexity("o(n)") @TimeComplexityExpected("o(1)")
    public V get(K key) throws InvalidKeyException {
        checkKey(key);
        int hc = this.hash(key);// Wrote For Debuging
        UnorderedMap bin = this.buckets[ hc ];
        // Look for key in map
        V ans = (V)bin.get(key);

        return ans;
    }
    
    @TimeComplexity("o(n)") @TimeComplexityExpected("o(1)") @TimeComplexityAmortized("o(1)")
    public V put(K key, V value) throws InvalidKeyException {
        // Rotate through to find
        checkKey(key);
        checkSize(); // o(n)
        
        V ans = null;
        int h = hash(key);
        
        // Check for collisions
        UnorderedMap<K,V> bin = this.buckets[h];
        
        if (bin == null){
            bin = new UnorderedMap<K,V>(); // Took me forever to remember
            this.buckets[h] = bin;         // buckets start null...
        }else{
            // Collision?
        }
        
        ans = bin.put(key, value); // o(n)
        
        if (ans == null){
            this.size++;
        }
        return ans;
    }

    @TimeComplexity("o(n)") @TimeComplexityExpected("o(1)")
    public V remove(K key) throws InvalidKeyException {
        V ans = this.buckets[hash(key)].remove(key);
        if (ans != null){
            this.size--;
        }
        return ans;
    }
    
    public void clear(){
        this.buckets = new UnorderedMap[ this.mDefaultHashSize ];
        this.size = 0;
        this.setup();
    }

    
    // Iterables
    public Iterable<Entry<K, V>> entrySet() { // o(n^2)_
        LinkedSequence<Entry<K,V>> ans = new LinkedSequence<>();
        for ( Map<K,V> cur : this.buckets )
        {   
            if (!cur.isEmpty()){
                // Add the contents
                for ( Entry<K,V> cur2 : cur.entrySet()){ // NEEDS TESTING!
                    ans.addLast(cur2);
                }
            }
        }   
        return ans;
    }
    
    public Iterable<K> keySet() {
        LinkedSequence<K> ans = new LinkedSequence<>();
        for ( Map<K,V> cur : this.buckets )
        {
            if (!cur.isEmpty()){
                // Add the contents
                for ( Entry<K,V> cur2 : cur.entrySet()){ // NEEDS TESTING!
                    ans.addLast(cur2.getKey());
                }
            }
        }
        return ans;
    }
    
    @TimeComplexity("o(n)")
    public Iterable<V> values() {
        
        LinkedSequence<V> vals = new LinkedSequence<>();
        for ( Entry<K,V> cur : this.entrySet() )
        {   
            vals.addLast( cur.getValue() );
        }   
        return vals;
    }

    // Calculations
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return (size() == 0);
    }
    
    
    public int hash(K k){
        // HASH FUNCTUIONs
        int y = k.hashCode();
        int ans, c = this.buckets.length-1;
        int MAD = Math.abs(7*y+1);

        ans = ( MAD % c + c)%c;
        
        // Look for collisions..?
        
        
        return ans;
    }

    @Override
    public int hashCode() {
        // Auto generated
        return super.hashCode();
    }
    public void checkSize(){
        if (size() == buckets.length){
            // Resize array
            UnorderedMap<K,V>[] newdata ;
            newdata = new UnorderedMap[ Math.max( 2*size(), 2 ) ];
            
            for (int i = 0; i < size(); i++){
                newdata[i] = this.buckets[i];
            }
            this.buckets = newdata;
        }
    }
    
    public String toString(){
        // Mostly for debuging
        String ans = "";
        
        // Give it raw
        for(int x = 0; x < this.buckets.length; x++){
            UnorderedMap cur = this.buckets[x];
            
            if (cur != null && !cur.isEmpty()){
                // Okay good lets work with it
                ans += "@"+x+" = {";
                int y = 0;
                for (Entry<K,V> c2 : (Iterable<Entry<K,V>>)cur.entrySet()){
                    ans += c2.toString();
                    if (y != 0 && y <= cur.size()-2){
                        ans += ",";
                    }
                    y++;
                }
                ans += "}\n";
                
            }
        }
        
        return ans;
    }
    
    public void checkKey(K arg) throws InvalidKeyException{
        // Conveinence function
        if (arg == null){
            throw new InvalidKeyException("Key is Null!");
        }
    }
    
    public void checkEntry(Entry<K,V> e) throws InvalidEntryException{
        // Conveinence function
        if (e.getKey() == null ){
            throw new InvalidEntryException("Key is BAD!");
        }
        // Check standards @@@
        if (e.getValue() == null){
            throw new InvalidEntryException("Value is Null!");
        }
    }

}
