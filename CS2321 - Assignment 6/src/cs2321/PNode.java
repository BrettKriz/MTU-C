package cs2321;

import java.util.Iterator;

import net.datastructures.*;

import cs2321.DNode;
/**
 * PNODE
 * 
 * An impossible to cast DNode -,-
 * 
 * @author Brett Kriz
 */
public class PNode<E extends Object> implements Position<E> {
    //public static DNode<Object> DEBUG_NODE = new DNode<>();
    private E elem;
    public PNode<E> parent = null;
    public PNode<E> left = null;
    public PNode<E> right = null;
    
    protected int height = 0;

    public PNode(E e, PNode<E> parent, PNode<E> left, PNode<E> right){
        elem = e;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public PNode<E> getParent(){ return this.parent; }
    public PNode<E> getLeft(){ return left; }
    public PNode<E> getRight() { return right; }
    public E element() { return elem; }
    public E getElement() {return this.element(); }

    public void setParent(PNode<E> x) { parent = x;}
    public void setLeft(PNode<E> x) { left = x; }
    public void setRight(PNode<E> x) { right = x; }
    public E setElement(E e) {
        E temp = this.elem;
        this.elem = e;
        return temp;
    }
    
    
    public int key(){
        return this.element().hashCode(); // @@@ Unused function
    }
    
    public boolean isExternal(){
        // DEBUGING @@@
        boolean b1 = (this.right == null);
        boolean b2 = (this.left == null);
        
        return b1 && b2;
    }
    
    public boolean isInternal(){
        return !this.isExternal();
    }
    
    public int getHeight(){
        return height;
    }
    public void setHeight(int h){
        this.height = h;
    }
    
    public String toString(){
        String ans = "";
        
        ans += "[" + this.element().toString() + "]";
        
        return ans;
    }

}
