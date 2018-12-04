package cs2321;

import net.datastructures.*;
//import cs2321.*;


public class Logfile<K , V> implements Dictionary<K, V> { //ADT
    // extends Comparable<K>
    private LinkedSequence<Entry<K,V>> data;

    public Logfile(){
            // TODO Do necessary initialization here
        this.data = new LinkedSequence<Entry<K,V>>();
    }

    @TimeComplexity("o(n)")
    public Entry<K, V> find(K key) throws InvalidKeyException { // O(n)
        checkKey(key);

        for (Position<Entry<K,V>> cur : this.data.positions()){
            if (key.equals( cur.element().getKey() )){
                // We found the key!
                return cur.element();
            }
        }

        return null;
    }

    @TimeComplexity("o(n)")
    public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException { // O(n)
        checkKey(key);
        LinkedSequence<Entry<K,V>> ans = new LinkedSequence<Entry<K,V>>();

        for (Position<Entry<K,V>> cur : this.data.positions()){
            if (key.equals( cur.element().getKey() )){
                // We found the key!
                ans.addLast( cur.element() );
            }
        }

        return ans; // CHECK REQUIRED TYPE
    }

    @Override @TimeComplexity("o(n)")
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        // Insert at end
        checkKey(key);
        QEntry<K,V> ans = new QEntry<K,V>(key, value);

        this.data.addLast(ans); // o(n)?

        return ans;
    }
    
    public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
         checkEntry(e);
        /*# NOTE:
         * Since Logfile is implemented as a dictionary, according to the
         * specification in the textbook, this method should throw the
         * InvalidEntryException when an invalid entry is passed in.
         * Even though you may leave the return null in as the last line
         * of code, it should never be hit due to the exception being thrown.
         */
        for (Position<Entry<K,V>> cur : this.data.positions()){
            if ( e.getKey().equals( cur.element().getKey() ) && e.getValue().equals( cur.element().getValue() ) ){
                Entry<K, V> temp = cur.element();
                this.data.remove(cur);
                return temp;
            }
        }
        return null; // Should never hit?
    }
    
    public Iterable<Entry<K, V>> entries() {
        LinkedSequence<Entry<K,V>> lEntries = new LinkedSequence<>();
        for ( int lIndex = 0; lIndex < size(); lIndex++ )
        {   
            lEntries.addLast( data.atIndex( lIndex ).element() );
        }   
        return lEntries;
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }
    public int size() {
        return this.data.size();
    }
    
    public String toString(){
        String ans =  "{";
        
        for (Entry<K,V> c : this.entries()){
            ans += "["+c.getKey()+","+c.getValue()+"],";
                    
        }
        ans += "}";
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
