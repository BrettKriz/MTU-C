package cs2321;

import java.util.Iterator;
import net.datastructures.*;
import cs2321.*;

/**
 * BENCHED ArraySequence
 * 
 * WARNING: File is NOT for use or submission! <p>
 * DELETE THIS FILE IF FOUND <p>
 * I WILL NOT CLAIM OWNERSHIP for this file or its contents <p>
 * 
 * @author Nobody
 */
public class ArraySequence2<E> extends NodePositionList<E> implements Sequence<E>{
      /** Array storing the Positions holding the elements of the Sequence. */
  private PNode<E>[] arrA;

  /** Number of elements stored in the index list. */
  private int size;

  /** Creates the indexed list with initial capacity 16. */
  @SuppressWarnings("unchecked")
  public ArraySequence2() {
    // Initialize the fields.
    arrA = (PNode<E>[])new Object[1];
    size = 0;
  }
  
  @Override
    public Iterable<Position<E>> positions() {
            // TODO Auto-generated method stub
            return super.positions();
    }

  /**
   * Returns the number of elements in the indexed list.
   * 
   * @return Number of elements in the Sequence.
   */
  public int size() {
    return size;
  }

  /**
   * Returns whether the Sequence is empty.
   * 
   * @return True if there are no elements in the Sequence; false otherwise.
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns the element at rank r, without removing it.
   * 
   * @param r Rank for which we wish to return the element
   * @return Element that is stored at rank <code>i</code>
   * @throws IndexOutOfBoundsException Exception thrown if the rank is below 0 or greater than or
   *           equal to the size of the index list (e.g., there is no element at the rank).
   */
  public E get(int r) throws IndexOutOfBoundsException {
    checkIndex(r, size());
    return arrA[r].element();
  }

  /**
   * Replaces the element at rank r with e, returning the previous element at i.
   * 
   * @param r Rank for which we wish to change the element stored
   * @param e Element which will now be stored at rank <code>i</code>
   * @return Element that was previously stored at rank <code>i</code>
   * @throws IndexOutOfBoundsException Exception thrown if the rank is below 0 or greater than or
   *           equal to the size of the index list (e.g., there is no element at the rank).
   */
  public E set(int r, E e) throws IndexOutOfBoundsException {
    checkIndex(r, size());
    E temp = arrA[r].element();
    arrA[r] = new PNode<E>(r, e);
    return temp;
  }

  /**
   * Inserts an element <code>e</code> at rank <code>r</code> by shifting all elements at that
   * rank and higher to make space.
   * 
   * @param r Rank at which we should insert the element
   * @param e Element to add to the index list.
   * @throws IndexOutOfBoundsException Exception thrown if the rank is below 0 or greater than the
   *           size of the index list.
   */
  @SuppressWarnings("unchecked")
  public void add(int r, E e) throws IndexOutOfBoundsException {
    checkIndex(r, size() + 1);
    if (size == arrA.length) { // an overflow
      PNode<E>[] arrB = (PNode<E>[])new Object[arrA.length * 2];
      for (int i = 0; i < size; i++) {
        arrB[i] = arrA[i];
      }
      arrA = arrB;
    }
    // Shift the elements up
    for (int i = size - 1; i >= r; i--) {
      arrA[i + 1] = arrA[i];
      arrA[i + 1].setRank(i + 1);
    }
    arrA[r] = new PNode<E>(r, e);
    size++;
  }

  /**
   * Removes and returns the element at rank r, shifting the elements after this.
   * 
   * @param r Rank for which we wish to remove and return the element
   * @return Element that was previously stored at rank <code>r</code>
   * @throws IndexOutOfBoundsException Exception thrown if the rank is below 0 or greater than or
   *           equal to the size of the index list (e.g., there is no element at the rank).
   */
  public E remove(int r) throws IndexOutOfBoundsException {
    checkIndex(r, size());
    E temp = arrA[r].element();
    for (int i = r; i < size - 1; i++) {
      // shift elements down
      arrA[i] = arrA[i + 1];
      arrA[i].setIndex(i);
    }
    size--;
    return temp;
  }

  /** Checks whether the given index is in the range [0, n - 1]. */
  private void checkIndex(int r, int n) throws IndexOutOfBoundsException {
    if ((r < 0) || (r >= n)) {
      throw new IndexOutOfBoundsException("Illegal index: " + r);
    }
  }

  /**
   * Checks if position is valid for this list and converts it to PNode if it is valid.
   * 
   * @param p Position which we are checking to if it is valid
   * @return The Position <code>p</code> typecast as a DPNode.
   * @throws InvalidPositionException Exception thrown if the Position could not occur in this
   *           Sequence.
   */
  protected ArrayDNode<E> checkPosition(ArrayDNode<E> p) throws InvalidPositionException {
    if (p == null) {
      throw new InvalidPositionException("Null position passed to NodeList");
    }
    if (!(p instanceof ArrayDNode)) {
      throw new InvalidPositionException("Position does not belong to this list.");
    }
    return (ArrayDNode<E>)p;
  }

  /*
   * (non-Javadoc)
   * 
   * @see Sequence#atIndex(int)
   */
  public Position<E> atIndex(int r) throws BoundaryViolationException {
    try {
      checkIndex(r, size());
    } catch (IndexOutOfBoundsException e) {
      throw new BoundaryViolationException(e.getMessage());
    }
    return arrA[r];
  }

  /*
   * (non-Javadoc)
   * 
   * @see Sequence#indexOf(Position)
   */
  public int indexOf(Position<E> p) throws InvalidPositionException {
    // Get the PNode for this Position
    ArrayDNode<E> v = checkPosition(p);
    // Check that the rank for this PNode is valid.
    checkIndex(v.getIndex(), size());
    // Now make sure it is correct.
    if (arrA[v.getIndex()].equals(p)) {
      return v.getIndex();
    }
    // Position was not found, throw the Exception
    throw new InvalidPositionException("No such Position!");
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#addFirst(java.lang.Object)
   */
  public void addFirst(E element) {
    add(0, element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#addLast(java.lang.Object)
   */
  public void addLast(E element) {
    add(size(), element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#getFirst()
   */
  public E getFirst() throws EmptyDequeException {
    try {
      return get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyDequeException(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#getLast()
   */
  public E getLast() throws EmptyDequeException {
    try {
      return get(size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyDequeException(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#removeFirst()
   */
  public E removeFirst() throws EmptyDequeException {
    try {
      return remove(0);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyDequeException(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see Deque#removeLast()
   */
  public E removeLast() throws EmptyDequeException {
    try {
      return remove(size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyDequeException(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#addAfter(Position, java.lang.Object)
   */
  public void addAfter(Position<E> p, E e) throws InvalidPositionException {
    int r = indexOf(p);
    add(r + 1, e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#addBefore(Position, java.lang.Object)
   */
  public void addBefore(Position<E> p, E e) throws InvalidPositionException {
    int r = indexOf(p);
    add(r, e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#first()
   */
  public Position<E> first() throws BoundaryViolationException {
    return atIndex(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#last()
   */
  public Position<E> last() throws BoundaryViolationException {
    return atIndex(size() - 1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#next(Position)
   */
  public Position<E> next(Position<E> p) throws InvalidPositionException,
                                        BoundaryViolationException {
    int r = indexOf(p);
    if (r == size()) {
      throw new BoundaryViolationException("Cannot go beyond last Position!");
    }
    return arrA[r + 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#prev(Position)
   */
  public Position<E> prev(Position<E> p) throws InvalidPositionException,
                                        BoundaryViolationException {
    int r = indexOf(p);
    if (r == 0) {
      throw new BoundaryViolationException("Cannot go before first Position!");
    }
    return arrA[r + 1];
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#remove(Position)
   */
  public E remove(Position<E> p) throws InvalidPositionException {
    int r = indexOf(p);
    return remove(r);
  }

  /*
   * (non-Javadoc)
   * 
   * @see PositionList#set(Position, java.lang.Object)
   */
  public E set(Position<E> p, E e) throws InvalidPositionException {
    int r = indexOf(p);
    return set(r, e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<E> iterator() {
    return super.iterator();
  }
}

class PNode<E> implements Position{
    protected E element;
    protected int position;
    
    public PNode(){
        
    }
    public PNode(int index, E element){
        this.element = element;
        this.position = index;
    }
    
    @Override
    public E element(){
        return element;
    }
    public int getIndex(){
        return this.position;
    }
    public void setIndex(int i){
        if (i >= 0){
            this.position = i;
        }else{
            throw new Error("Negative Index used!");
        }
    }
}