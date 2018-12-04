package cs2321;
import java.util.*;
import net.datastructures.*;

/**
 * QEntry
 * <p>
 * An type of Entry
 * 
 * @author Brett Kriz
 */
@SpaceComplexity("O(n)")
public class QEntry<K extends Comparable<K>, V > implements Entry<K,V>{
    private K Key;
    private V Value;
    public Integer index;
    
    @TimeComplexity("O(1)")
    public QEntry(K k, V v){
        this.Key = k;
        this.Value = v;
    }
    
    @TimeComplexity("O(1)")
    public QEntry(Entry<K,V> e){
        this.Key = e.getKey();
        this.Value = e.getValue();
    }
    
    @Override @TimeComplexity("O(1)")
    public K getKey() {
        return Key;
    }

    @Override @TimeComplexity("O(1)")
    public V getValue() {
        return Value;
    }
    
    @TimeComplexity("O(1)")
    public void setKey(K k){
        this.Key = k;
    }
    
    @TimeComplexity("O(1)")
    public void setValue(V v){
        this.Value = v;
    }
    
    @TimeComplexity("O(1)")
    public String toString(){ // MADE FOR DEBUGING ONLY! MAY AFFECT TEXT OUT
       return "[" + this.Key + "," + this.Value + "]@" + this.index;
    }
}