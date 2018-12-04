package cs2321;

import java.util.Iterator;
import net.datastructures.*;
import cs2321.*;


/**
 *
 * @author Brett Kriz
 */
public class SequenceIterable<E> implements Iterable<E>{
    private E[] array;
    
    @TimeComplexity("O(1)")
    public SequenceIterable(E[] array){
        this.array = array;
    }
    
    @TimeComplexity("O(1)")
    public Iterator<E> iterator(){
        return new SequenceIterator<E>(array);
    }
    
}
class SequenceIterator<E> implements Iterator<E> {
    private E[] array;
    private int next;
    @TimeComplexity("O(1)")
    public SequenceIterator(E[] array){
        this.array = array;
        this.next = 0;
    }
    @Override @TimeComplexity("O(1)")
    public boolean hasNext(){
        return next < array.length;
    }
    @Override @TimeComplexity("O(1)")
    public E next(){
        return array[next++];
    }
    @Override @TimeComplexity("O(?)")
    public void remove(){
        throw new UnsupportedOperationException("No.");
    }
    
    
    
}
