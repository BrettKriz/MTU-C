package cs2321;

import net.datastructures.*;
import cs2321.*;

/**
 * ArrayDNode - A super position
 * @author Brett Kriz
 */
@SpaceComplexity("O(n)")
public class ArrayDNode<E> implements Position<E> {
    private ArrayDNode<E> prev, next;	// References to the nodes before and after
    private E element;                  // Element stored in this position
    private int index = -5;
    public boolean isSentinal = false;
    public Boolean SentinalIndex;
    /** Constructor */
    @TimeComplexity("O(1)")
    public ArrayDNode(ArrayDNode<E> newPrev, ArrayDNode<E> newNext, E elem, int index) {
        if (index < 0){
            throw new Error("BAD INDEX");
        }
        this.prev = newPrev;
        this.next = newNext;
        this.element = elem;
        this.index = index;
     
    }
    @TimeComplexity("O(1)")
    public ArrayDNode(ArrayDNode<E> newPrev, ArrayDNode<E> newNext, E elem) {
      this.prev = newPrev;
      this.next = newNext;
      this.element = elem;
    }
    @TimeComplexity("O(1)")
    public ArrayDNode(boolean arg, ArrayDNode<E> related){
        //SENTINAL NODE
        // Not important enough for its own extra class
        this.isSentinal = true;
        this.SentinalIndex = arg;
        if (!arg){
            this.index = -1;
        }
        
        if (related != null){
            // We have a related node to attach,
            if (arg){ // if this is END
                this.prev = related;
            }else{ // if this is FRONT
                this.next = related;
            }
        }
        
    }
    
    @TimeComplexity("O(1)")// Method from interface Position
    public E element() throws InvalidPositionException {
      
      return element;
    }
    @TimeComplexity("O(1)") // Accessor methods
    public ArrayDNode<E> getNext() { return next; }
    @TimeComplexity("O(1)")
    public ArrayDNode<E> getPrev() { return prev; }
    @TimeComplexity("O(1)")
    public int getIndex() { return index; }
    // Update methods
    @TimeComplexity("O(1)")
    public void setAsSentinal(boolean f, boolean index){
        this.isSentinal = f;
        if (f){ // Index as in head or tail
            this.SentinalIndex = index;
        }else{
            this.SentinalIndex = null;
        }
        
    }
    @TimeComplexity("O(1)")
    public void setNext(ArrayDNode<E> newNext) { next = newNext; }
    @TimeComplexity("O(1)")
    public void setPrev(ArrayDNode<E> newPrev) { prev = newPrev; }
    @TimeComplexity("O(1)")
    public void setElement(E newElement) { element = newElement; }
    @TimeComplexity("O(1)")
    public void setIndex(int i) 
    { 
        if (i > -1)
          this.index = i;
        else
          throw new ArrayIndexOutOfBoundsException("Negative index not allowed!");
    }
    @Override @TimeComplexity("O(1)")
    public String toString(){ // FOR DEBUGING ONLY! MAY CHANGE TEXT RESULTS!
        return "" + this.element().toString() + " @ " + this.index;
    }
}
