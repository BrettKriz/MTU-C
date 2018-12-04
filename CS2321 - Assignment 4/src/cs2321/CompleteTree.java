package cs2321;

import java.util.Iterator;

import net.datastructures.BinaryTree;
import net.datastructures.BoundaryViolationException;
import net.datastructures.EmptyTreeException;
import net.datastructures.InvalidPositionException;
import net.datastructures.Position;

/*# 
 * Note that this is NOT where you maintain ordering of the values.
 * E is not comparable, and therefore should not be attempted to be compared.
 * 
 * Be sure to include Complexity Annotations and TCJ/SCJ comments.
 */
@SpaceComplexity("O(n)")
public class CompleteTree<E> implements BinaryTree<E> {
        ArraySequence<E> backing;
	/*# If you choose to add additional constructors to this
    	 *  class, BE SURE that you make them public!
	 */
	@TimeComplexity("O(1)")
	public CompleteTree(){
	    this.backing = new ArraySequence<E>();
	}
        
	@TimeComplexity("O(1)")
        public CompleteTree(MaxPriorityQueue pq){
            this.backing = (((UnorderedPQ)pq).backing);
        }
        
	/**
	 * Adds an element to the tree in the "last" position.
	 * This is the lowest level, justified to the left
	 * 
	 * @param e the element to be added to the tree
	 * @return the position of the newly added element
	 * @throws InvalidPositionException
	 */
	/*# Since this is a complete tree, this is the only place one should add to */
        @TimeComplexity("O(1)") @TimeComplexityAmortized("O(1)")
	public Position<E> addLast(E e) {
            if (e == null){
                throw new InvalidPositionException();
            }

            return this.backing.addLast2(e);
	}
        @TimeComplexity("O(1)") @TimeComplexityAmortized("O(1)")
        public ArrayDNode<E> addLast2(E e){
            return this.backing.addLast2(e);
        }
        @TimeComplexity("O(1)")
        public Position<E> last(){
            return this.backing.last();
        }
	
	/**
	 * Removes an element from the "last" position of the tree.
	 * This is the lowest level, rightmost element.
	 * 
	 * @return
	 * @throws InvalidPositionException
	 */
	/*# Since this is a complete tree, this is the only place one should remove from */
        @TimeComplexity("O(1)")
	public E removeLast() throws EmptyTreeException {
            if ( this.backing.isEmpty() ){
                throw new EmptyTreeException("@removeLast()");
            }
            // Lets hope this cast works
            return (E)this.backing.removeLast();
	}

	/**
	 * Swaps the values contained in these two positions.
	 * 
	 * @param e the element to be added to the tree
	 * @return the position of the newly added element
	 * @throws InvalidPositionException
	 */
	/*# Since this is a complete tree, this is the only place one should add to */
        @TimeComplexity("O(1)")
	public void swapElements(Position<E> p1, Position<E> p2) {
            // This isn't where order is maintained...
            // So lets mess with the order!
            
            this.backing.swapElements(p1, p2);
	}

	/* (non-Javadoc)
	 * @see net.datastructures.BinaryTree#hasLeft(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
            int at = this.backing.indexOf(v);
            int arg = (2*at)+1;
            if ( arg >= 0 && arg < size() ){
                return true;
            }
            return false;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.BinaryTree#hasRight(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
	    int at = this.backing.indexOf(v);
            int arg = (2*at)+2;
            if ( arg >= 0 && arg < size() ){
                return true;
            }
            return false;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.BinaryTree#left(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public Position<E> left(Position<E> v) throws InvalidPositionException,
                                                    BoundaryViolationException {
            int at = this.backing.indexOf(v);
            Position<E> ans = null;
            
            try{ // Attempt to grab the index!
                ans = this.backing.atIndex((2*at)+1);
            }catch (BoundaryViolationException e){
                //throw e;
                return ans;
            }
            return ans;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.BinaryTree#right(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public Position<E> right(Position<E> v) throws InvalidPositionException,
        BoundaryViolationException {
	    int at = this.backing.indexOf(v);
            Position<E> ans = null;
            
            try{ // Attempt to grab the index!
                ans = this.backing.atIndex((2*at)+2);
            }catch (BoundaryViolationException e){
                //throw e;
                return ans;
            }
            
            return ans;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#children(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public Iterable<Position<E>> children(Position<E> v)
        throws InvalidPositionException {
            //int at = this.backing.findElem((ArrayDNode)v);
            //QEntry target = (QEntry)this.backing.get(at);
            int n = 0;
            Position<E> L = left(v), R = right(v);

            // Check results
            if (L != null){
                n++;
            }
            if (R != null){
                n++;
            }

            n = 0;
            Position<E>[] tbl = new Position[n];

            if (L != null){
                tbl[n++] = L;
            }
            if (R != null){
                tbl[n++] = R;
            }

            Iterable<Position<E>> ans = new SequenceIterable<>(tbl);
            return ans;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#isEmpty()
	 */
        @TimeComplexity("O(1)")
	public boolean isEmpty() {
            return this.backing.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#isExternal(net.datastructures.Position)
	 */
        @Override @TimeComplexity("O(1)")
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
            return ( !this.hasLeft(v) && !this.hasRight(v) );
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#isInternal(net.datastructures.Position)
	 */
        @Override @TimeComplexity("O(1)")
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
                // If it has ANY kids, its internal
            return ( this.hasLeft(v) || this.hasRight(v) );
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#isRoot(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		// Root is ALWAYS [0]
		return this.backing.isFirst(v);
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#iterator()
	 */
        @TimeComplexity("O(1)")
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return this.backing.iterator();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#parent(net.datastructures.Position)
	 */
        @TimeComplexity("O(1)")
	public Position<E> parent(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
            if (v == null){
                throw new NullPointerException();
            }

            Position<E> parent = null;
            int x;
            
            // Work with position indexs
            if (v instanceof ArrayDNode){
                ArrayDNode<E> v2 = (ArrayDNode)v;
                x = v2.getIndex();
                // Look for parent or fail
                try{
                    int path = (x-1)/2; // INT
                    parent = this.backing.atIndex( path );
                    ((ArrayDNode<E>)parent).setIndex(path);
                } catch (BoundaryViolationException e){
                    e.printStackTrace();
                    
                    //throw new BoundaryViolationException( e. )
                }
            }
            return parent;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#positions()
	 */
        @TimeComplexity("O(1)")
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return this.backing.positions();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#replace(net.datastructures.Position, java.lang.Object)
	 */
        @TimeComplexity("O(1)")
	public E replace(Position<E> v, E e) throws InvalidPositionException {
            // Replace element of V
            
            E ans = v.element();
            ArrayDNode temp = (ArrayDNode)v;
            temp.setElement(e);
            v = temp;
            
            return ans;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#root()
	 */
        @TimeComplexity("O(1)")
	public Position<E> root() throws EmptyTreeException {
		// TODO Auto-generated method stub
            if (this.backing.isEmpty()){
                throw new EmptyTreeException("empty @ root()");
            }
            return this.backing.first();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Tree#size()
	 */
        @TimeComplexity("O(1)")
	public int size() {
            return this.backing.size();
	}

}
