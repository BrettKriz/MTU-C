package cs2321;

import net.datastructures.*;
/**
 * A PriorityQueue based on an Unordered Sequence. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author Brett Kriz
 */
@SpaceComplexity("O(n)")
public class Heap<K extends Comparable<K>,V> extends PriorityQueueBase<K,V> implements MaxPriorityQueue<K,V>  {
	UnorderedPQ<K,V> backing;
	CompleteTree<Entry<K,V>> tree;
	
        @TimeComplexity("O(1)")
	public Heap() {
            backing = new UnorderedPQ<K,V>();
            tree = new CompleteTree< Entry<K,V> >();
            
            
	}
	
	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param p a position from the tree, containing an entry into the heap
	 */
	/*# This should be done immediately after adding a value to the tree */
        @TimeComplexity("O(Log N)")
	private void bubbleUp(Position<Entry<K,V>> p){
            // Find parents
            if ( tree.isRoot(p) ){
                // Not enough nodes
                return;
            }
            // TCJ: The loop only runs for the height of the tree,
            // so the cost is always o(Log n)
            
            Position<Entry<K,V>> parent = tree.parent(p);
            if (swapCheckG(p, parent)){
                tree.swapElements(p, parent);
                bubbleUp(parent);
            }
            
	}
        
        /**
	 * The entry should be bubbled down to its appropriate position 
	 * @param p
	 */
	/*# This should be done immediately after replacing the value of the root of the tree */
        @TimeComplexity("O(Log N)")
	private void bubbleDown(Position<Entry<K,V>> p){
            // Find parents
            Position<Entry<K,V>> L,R;
            boolean go1 = true, go2 = true;
            
            // TCJ: The loop only runs the height of the tree
            // so it is always o(Log n)
            
            L = tree.left(p);
            R = tree.right(p);
            
            if (L == null && R == null){
                return;
            }
            
            go1 = swapCheckL(p, L);
            go2 = swapCheckL(p, R);

            if (go1 && go2){
                if (swapCheckL(R,L)){
                    // Swap Left
                    this.tree.swapElements(L, p);
                    bubbleDown(L);
                }else{
                    // Swap Right
                    this.tree.swapElements(R, p);
                    bubbleDown(R);
                }
            }else if (go1){
                this.tree.swapElements(L, p);
                bubbleDown(L);
            }else if (go2){
                this.tree.swapElements(R, p);
                bubbleDown(R);
            }// else were gone
	}
        
        @TimeComplexity("O(1)")
        private boolean swapCheckG(Position<Entry<K,V>> C, Position<Entry<K,V>> P){
            // Quick check
            if (C == null){
                throw new NullPointerException();
            }else if (P == null){
                return false;
            }
            
            return (C.element().getKey().compareTo( P.element().getKey() ) > 0);
        }
        
        @TimeComplexity("O(1)")
	private boolean swapCheckL(Position<Entry<K,V>> C, Position<Entry<K,V>> P){
            // Quick check
            if ( C == null ){
                throw new NullPointerException();
            }else if( P == null ){
                return false;
            }
            
            return (C.element().getKey().compareTo( P.element().getKey() ) < 0);
        }

        @TimeComplexity("O(Log N)")
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
            // put in last, move to correct position
            QEntry<K,V> t = new QEntry<>(key,value);
            int i = this.size();
            // Add and track
            ArrayDNode t2 = this.tree.addLast2(t);
            t2.setIndex( i );
            t.index = i;
            
            // TCJ: The cost of everything about this comment is o(1)
            // but when we call bubble up, it costs o(Log n)
            
           // System.out.println(this.size()); // DEBUG
            // Put into place
            this.bubbleUp( this.tree.last() );

             return t;
	}

        @TimeComplexity("O(1)")
	public boolean isEmpty() {
            return this.tree.isEmpty();
	}

        @TimeComplexity("O(1)")
	public Entry<K,V> max() throws EmptyPriorityQueueException {
            // TCJ: to find max, we look at the top element, so o(1)
            return this.tree.root().element();
	}
        
        @TimeComplexity("O(Log n)")
	public Entry<K,V> removeMax() throws EmptyPriorityQueueException {
            // TCJ: the removal happens at 
            //this.removeFirst();
            Position<Entry<K,V>> root =  this.tree.root();
            Position<Entry<K,V>> last = this.tree.last();
            this.tree.swapElements( root, last);

            QEntry<K,V> ans = (QEntry)this.tree.removeLast();
            
            if (!this.isEmpty()){
                this.bubbleDown(this.tree.root());
            }
            return ans;
	}
        
        @TimeComplexity("O(1)")
	public int size() {
            return this.tree.size();
	}

}
