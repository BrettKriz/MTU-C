/**
 * 
 */
package cs2321;

import java.util.Comparator;
import net.datastructures.*;

/**
 * AVL Dictionary 
 * @author Brett Kriz
 * @version 2
 */
public class AVLDictionary<K, V> implements Dictionary<K, V> {
    BalanceableBinaryTree<K,V> tree;
    Comparator<K> vs = new DefaultComparator();
    
    /**
     * 
     */
    public AVLDictionary() {
        this.tree = new BalanceableBinaryTree<>();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#get(java.lang.Object)
     */
    @TimeComplexity("o(Lg n)") @TimeComplexityAmortized("o(Lg n)") @TimeComplexityExpected("o(Lg n)")
    public Entry<K,V> find(K key) throws InvalidKeyException { // o(lg n)
        // Binary Search
        if (tree.isEmpty()){
            // No dice.
            return null;
        }
        tree.checkKey(key);
        
        PNode<Entry<K,V>> at = (PNode<Entry<K,V>>)tree.root();
        PNode<Entry<K,V>> ans = null;
        ans = tree.treeSearch(at, key);
        
        
        
        // Start the search...
        return ans.element();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#isEmpty()
     */
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    public QEntry<K,V> checkEntry(K k, V v) throws InvalidEntryException { 
        return this.tree.checkEntry( new QEntry<K,V>(k,v) );
    }

    protected PNode<Entry<K,V>> tallerChild (Position<Entry<K,V>> arg) 
                                               throws InvalidPositionException {
        PNode<Entry<K,V>> p = tree.validate(arg);
        DefaultComparator<Integer> vs = new DefaultComparator();
        int comp = vs.compare(height(p.left), height(p.right)); // Deal with null cases?
        // Find which ones taller
        switch (comp){
            case 1  :   return p.left;
            case -1 :   return p.right;
            case 0  :   // See below
                if (tree.isRoot(p)){
                    return p.left; // Choice is irrelevant
                }
                if (p.equals( p.parent.left )){
                    return p.left;
                }else{
                    return p.right;
                }
            default :   throw new Error("Switch Error @tallerChild");
        }
        
    }
    
    public Integer height (Position<Entry<K,V>> arg) 
                                               throws InvalidPositionException {
        PNode<Entry<K,V>> v = tree.checkPos(arg);
        return (Integer)tree.getAux( v );
    }
    
    public void recomputeHeight (Position<Entry<K,V>> arg) 
                                               throws InvalidPositionException {
        PNode<Entry<K,V>> p = tree.validate(arg);
        tree.setAux( p, 1 + Math.max(height(p.left), height(p.right)) );
    }
    
    public boolean isBalanced (Position<Entry<K,V>> arg) 
                                               throws InvalidPositionException {
        PNode<Entry<K,V>> p = tree.validate(arg);
        
        // Protect against null cases
        int lh = 0;
        int rh = 0;
        if (p.left != null){
            lh = height( p.left );
        }
        if (p.right != null){
            rh = height( p.right );
        }
        
        
        return ( Math.abs( lh - rh ) <= 1 ); 
    }

    public void setHeight(Position<Entry<K,V>> arg) 
                                              throws InvalidPositionException  {
        PNode<Entry<K,V>> z = tree.checkPos(arg);

        if (z.isExternal()){
            z.height = 0;
        }else{
            int lh = 0;
            int rh = 0;
            
            if (z.left != null){
                lh = z.left.height;
            }
            if (z.right != null){
                rh = z.right.height;
            }
            
            z.height = Math.max(lh, rh)+1;
        }    
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override @TimeComplexity("o(Lg n)") @TimeComplexityAmortized("o(Lg n)") @TimeComplexityExpected("o(Lg n)")
    public Entry<K,V> insert(K key, V value) throws InvalidKeyException { // o(lg n)
        tree.checkKey(key);
        PNode<Entry<K,V>> w = tree.treeInsert(key, value); // o(?)
        Entry<K,V> e = w.element();

        if (size() > 1){
            this.rebalance(w); // o(?)
        }
        
        return e;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#remove(java.lang.Object)
     */
    @TimeComplexity("o(lg n)") @TimeComplexityAmortized("o(lg n)") @TimeComplexityExpected("o(lg n)")
    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException { // o(lg n)
        // k is qunique - MAD
        QEntry<K,V> k = tree.checkEntry(e);
        PNode<Entry<K,V>> n = tree.treeSearch(tree.root(), k.getKey()); 
        // 

        //
        if ( n.isExternal() ){
            //return null;
           return tree.remove(n);
        }
        //

        QEntry<K,V> ans = (QEntry)tree.BSTRemove(n);
        if (!this.isEmpty()){
            rebalance(  n.parent );
        }
        return ans;
    }
    //
    
    protected void rebalanceInsert(Position<Entry<K,V>> arg) throws InvalidPositionException{
        this.rebalance(arg);
    }
    protected void rebalanceDelete(Position<Entry<K,V>> arg) throws InvalidPositionException{
        if (!(arg instanceof PNode)) {
            // Then, no.
            throw new InvalidPositionException("Type Error!");
        }
        PNode<Entry<K,V>> p = tree.checkPos(arg);
        
        if (!tree.isRoot(p)){
            rebalance(p.parent);
        }
        
    }
    
    public void rebalance(Position<Entry<K,V>> arg) throws InvalidPositionException{
        // for insert z is the internal node containing the new entry
        // for delete z is the parent of the deleted node
        if (arg == null){
            return;
        }
        PNode<Entry<K,V>> z = tree.validate(arg);
        int newheight;
        int oldheight;

        while ( z != null ) {
            oldheight = z.height;
            setHeight(z);

            if (!isBalanced(z)){
                z = (PNode<Entry<K,V>>)tree.restructure(z);

                setHeight(z.left);
                setHeight(z.right);
                setHeight(z);
            }

            newheight = z.getHeight();

            if (newheight == oldheight){
                break;
            }
            z = z.parent;
        }
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#size()
     */
    public int size() {
        return tree.size();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Map#values()
     */
    public Iterable<V> values() {
        throw new UnsupportedOperationException("WIP@@@");
    }
    
    @TimeComplexity("o(n)") @TimeComplexityAmortized("o(n)") @TimeComplexityExpected("o(n)")
    public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
        throw new UnsupportedOperationException("PROJECT IS INCOMPLETE! - TIME LIMIT HIT");
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
    @TimeComplexity("o(n)") @TimeComplexityAmortized("o(n)") @TimeComplexityExpected("o(n)")
    public Iterable<Entry<K,V>> getRange(K start, K stop) {
        /*
        throw new UnsupportedOperationException("PROJECT IS INCOMPLETE! - TIME LIMIT HIT");
        LinkedSequence<Entry<K,V>> tbl = new LinkedSequence<>();
        LinkedList<Entry<K,V>> ans = new LinkedSequence<>();
        if (this.isEmpty()){
            return ans;
        }
        // Recur all the way
        getRange(tree.root,tbl,start,stop);
        
        for (Position<Entry<K,V>> cur : ans.positions()){
            ans.addLast(cur.);
        }
        */
        return null;
    }
    private void getRange(PNode<Entry<K,V>> at,LinkedSequence<Entry<K,V>> tbl, K start, K stop){
        if (at == null){
            return;
        }
        
        if (at.isExternal()){
            if (at.left != null && inKs(at.left,start,stop)){
                tbl.addLast(at.left.element());
            }
            if (at.right != null && inKs(at.right,start,stop)){
                tbl.addLast(at.right.element());
            }
        }else{
            // Recur on kids
            this.getRange(at.left, tbl, start, stop);
            this.getRange(at.right, tbl, start, stop);
        }
    }
    
    public boolean inKs(PNode<Entry<K,V>> arg, K start, K stop){
        K item = arg.element().getKey();
        
        int l = tree.compKeys(item,start); // 1
        int h = tree.compKeys(stop,item); // 1
        
        return (h >= 0 && l >= 0);
    }
    

    /* (non-Javadoc)
     * @see net.datastructures.Map#entries()
     */
    public Iterable<Entry<K, V>> entries() {
        LinkedSequence<Entry<K,V>> ans = new LinkedSequence<>();
        
        //this.tree.minimum(null);
        
        throw new UnsupportedOperationException("WIP@@@");
    }
   // public void snapshot

    /* (non-Javadoc)
     * @see net.datastructures.Map#keys()
     */
    public Iterable<K> keys() {
        throw new UnsupportedOperationException("WIP@@@");
    }
    
}



    /*
    public PNode<Entry<K,V>> restructure(Position<Entry<K,V>> arg) 
                                           throws InvalidPositionException {
        // Children are balanced but Z/ARG is not
        PNode<Entry<K,V>> z = tree.checkPos(arg);
        PNode<Entry<K,V>> p = z.parent;
        // Rotation variables
        PNode<Entry<K,V>> x,y, a,b,c, t0,t1,t2,t3;

        if ( z.left.height() > z.right.height() ){
            y = z.left;
        }else{
            y = z.right;
        }

        // @@@ Check this!!!!
        if ( y.left.height() > y.right.height() ){
            x = y.left;
        }else{
            x = y.right;
        }


        // Set x to be y's higher child
        if ( y.equals(z.right) && x.equals(y.right)) { // Find out where X is defined...
            a = z;
            b = y;
            c = x;

            t0 = z.left;
            t1 = y.left;
            t2 = x.left;
            t3 = x.right;

        } else {
            // Recombine them... My Wrist!!
            a = z;
            b = y; // @@@ check order!!!!!!!!
            c = x;

            // Row1
            b.left = a;
            b.right = c;

            a.parent = b;
            c.parent = b;
            // Row2
            a.left = t0;
            a.right = t1;

            t0.parent = a;
            t1.parent = a;
            // Row3
            c.left = t2;
            c.right = t3;

            t2.parent = c;
            t3.parent = c;
            // Row4
            b.parent = p;

            if (p == null){
                tree.root = b; // @@@ CHECK FOR ACCURACY!!!!
            } else if ( z.equals( p.left ) ){
                p.left = b;
            } else {
                p.right = b;
            }
        }
        return b;
    }
    //*/