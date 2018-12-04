package cs2321;

import java.util.Iterator;

import net.datastructures.BinaryTree;
import net.datastructures.BoundaryViolationException;
import net.datastructures.EmptyTreeException;
import net.datastructures.InvalidPositionException;
import net.datastructures.LinkTree;
import net.datastructures.NonEmptyTreeException;
import net.datastructures.Position;

import cs2321.DNode;
/**
 * PNODE
 * 
 * An impossible to cast DNode -,-
 * 
 * @author Brett Kriz
 */
public class PNode_ORIGINAL<E extends Object> extends DNode<E> {
    //public static DNode<Object> DEBUG_NODE = new DNode<>();
    public PNode_ORIGINAL<E> parent = null;
    public PNode_ORIGINAL<E> left = null;
    public PNode_ORIGINAL<E> right = null;
    protected int index = -5;
    protected int key   = 0;
    protected int height = 0;
    
    private DNode<E> prev, next; // Needed for LEGACY
    
    public PNode_ORIGINAL(PNode_ORIGINAL<E> parent, E elem){
        super(null,null,elem);
        this.parent = parent;
        
        this.setKey(elem);
    }

    
    // Accessor methods
  public PNode_ORIGINAL<E> getNext() { return (PNode_ORIGINAL<E>)super.getNext(); }
  public PNode_ORIGINAL<E> getPrev() { return (PNode_ORIGINAL<E>)super.getPrev(); }
  // Update methods
  public void setNext(DNode<E> newNext) { next = (PNode_ORIGINAL<E>)newNext; }
  public void setPrev(DNode<E> newPrev) { prev = (PNode_ORIGINAL<E>)newPrev; }
  
    public int setHeight(int arg){
        // Calculate this somehow
        this.height = arg;
        //throw new UnsupportedOperationException("BUILD ME!");
        return this.height;
    }
    public int height(){
        return this.height;
    }
    
    public int key(){
        return key; // @@@ Unused function
    }
    public void setKey(E e){
        if (e != null){ // Must account for leaves
            key = e.hashCode();
        }
    }
    public static int key(Position<?> arg){
        //return arg.key; // for tests
        return arg.element().hashCode();
    }
    
    
    public int getIndex(){
        return index;
    }
    
    public void setIndex(int arg){
        if (arg < 0){
            throw new IllegalArgumentException("Index too low!");
        }
        this.index = arg;
    }
    
    public boolean isExternal(){
        return this.right == null && this.left == null;
    }
    
    public boolean isInternal(){
        return !this.isExternal();
    }
    //super.setElement(elem);
    public void setElement(E newElement){
        super.setElement(newElement);
        
        this.key = newElement.hashCode();
    }
    
    /*
    public E element(){
        // DNode is far too worried about prev and next
        if (this.prev == null && this.next == null){
            // 
        }
        
        return super.element();
    }
    //*/
    
    public String toString(){
        String ans = ""; // Mostly for debuging
        
        ans += "[" + this.key() + "]";
        
        return ans;
    }
}
/*
public class DebugNode<E> extends PNode<E>{
    
}
}


*/