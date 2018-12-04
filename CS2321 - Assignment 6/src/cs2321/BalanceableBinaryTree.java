package cs2321;

import java.util.Iterator;
import net.datastructures.*;

/**
 * Balanceable Binary Tree
 * 
 * Thanks book!
 * 
 * @author Brett Kriz
 */
public class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>> {
    // Aux = height
    // BSTNode = pnode
    public int getAux(PNode<Entry<K,V>> p){
        return p.getHeight();
    }
    public void setAux(Position<Entry<K,V>> p, int value){
        validate(p).setHeight(value);
    }
    
    protected PNode<Entry<K,V>> createNode(Entry<K,V> e, PNode<Entry<K,V>> parent, PNode<Entry<K,V>> left, PNode<Entry<K,V>> right){
        return new PNode<>(e,parent,left,right);
    }
    
    private void relink(Position<Entry<K,V>> parent, Position<Entry<K,V>> child, boolean makeLeftChild){
        PNode<Entry<K,V>> p = validate(parent), c = validate(child);
        
        c.parent = p;
        if (makeLeftChild){
            p.left = c;
        }else{
            p.right = c;
        }
    }
    
    public void rotate(Position<Entry<K,V>> p){
        PNode<Entry<K,V>> x = validate(p);
        PNode<Entry<K,V>> y = x.getParent(); // we assume this exists
        PNode<Entry<K,V>> z = y.getParent(); // Grandparent possibly null
        
        if (z == null){
            root = x;
            x.setParent(null);
        }else{
            relink(z,x,y == z.getLeft());
        }
        // Now rotate x and y, including transfer of middle subtree
        if (x.equals(y.getLeft())){
            relink(y, x.getRight(), true);
            relink(x, y, false);
        }else{
            relink(y, x.getLeft(), false);
            relink(x, y, true);
        }
    }
    
    public Position<Entry<K,V>> restructure(Position<Entry<K,V>> arg){
        PNode<Entry<K,V>> x = validate(arg);
        PNode<Entry<K,V>> y = x.parent;
        PNode<Entry<K,V>> z = y.parent;
        
        if ((x == y.right) == (y == z.right)){//matching alignments
            rotate(y);  // single rotation
            return y;   // y is new subtree root
        } else { // Opposite alignments
            rotate(x);  // double rotation
            rotate(x);
            return x;   // x is new subtree root
        }
    }
    
    // ~~~~~~~~~~~~ HELPER FUNCTIONS ~~~~~~~~~~~~~~~~
            
    public PNode<Entry<K,V>> treeSearch(Position<Entry<K,V>> arg, K key) throws InvalidPositionException, InvalidKeyException {
        checkKey(key);
        PNode<Entry<K,V>> v = validate(arg);
        DefaultComparator<K> vs = new DefaultComparator<K>();
        int x = 1;
        
        while ( v != null && v.element() != null && v.isInternal() ){

            int comp = -6;
            try{
                comp = vs.compare( v.element().getKey() , key );
            }catch (NullPointerException e){
                System.out.println(">>>>>"+x);
                return v;
            }
            //                          A                 B
            // A>B = 1(L) ; A<B = -1(R)
            if (comp > 0){
                if (v.left == null){
                    v = (PNode<Entry<K,V>>)this.insertLeft(v, null);
                    break;
                }
                v = v.left;
            }else if (comp < 0){
                if (v.right == null){
                    v = (PNode < Entry<K, V>>)this.insertRight(v, null);
                    break;
                }
                v = v.right;
            }else{
                break;
            }
            x++;
        }
        System.out.println("=======================================");
        
        return v;
    }


    public PNode<Entry<K,V>> treeInsert(K key, V value) throws InvalidKeyException {
        // Duplicates allowed
        checkKey(key);
        QEntry<K,V> entry = new QEntry<K,V>(key,value);
        checkEntry(entry);
        
        PNode<Entry<K,V>> w;

        if (this.isEmpty()){
            // FIRST ENTRY!
            w = (PNode<Entry<K,V>>) this.addRoot(entry);
            // Make children!
        }else{
            w = treeSearch(root(), key); // @@@ DEBUG ME!

            K key_debug = w.element().getKey();
            int comp = new DefaultComparator().compare(key, key_debug);

            if (comp < 0){
                w = (PNode<Entry<K,V>>) this.insertLeft(w, entry);
            }else {
                w = (PNode<Entry<K,V>>) this.insertRight(w, entry);
            }
        }
        return w;
    }

    public QEntry<K,V> checkEntry(Entry<K,V> e) throws InvalidEntryException {
        if (!(e instanceof QEntry) || e.getKey() == null){
            // No good
            throw new InvalidKeyException("Entry isnt QEntry or key is null");
        }

        return (QEntry<K,V>) e;
    }
    
    public K checkKey(K k) throws InvalidKeyException {
        if (k == null){
            throw new InvalidKeyException("Key is null");
        }
        return k;
    }
    
    public int compKeys(K k1,K k2){
        DefaultComparator<K> vs = new DefaultComparator();
        return vs.compare( k1, k2 );
    }
	
}
