package cs2321;

import java.util.Iterator;
import java.lang.Exception.*;
import net.datastructures.*;
import cs2321.*;

/**
 * A sequence that is implemented as an array.
 * If the capacity is exceeded the capacity will be doubled. 
 * 
 * YOUR COMPLEXITY INFO HERE
 * 
 * Course: CS2321 Section YOUR SECTION HERE
 * Assignment: #1
 * @author Brett Kriz
 */ 
/* Do not alter the definition of the class.
 * ie. Do not extend LinkedSequence.
 */
@SpaceComplexity("O(n)")
public class ArraySequence<E> implements Sequence<E>
{
    /*# Complete the implementation of all required methods.
     *  Be sure to update this file to include:
     *      
     *      Annotations (@TimeComplexity, @SpaceComplexity, @TimeComplexityAmmortized, @TimeComplexityExpected)
     *      that indicate the time and space complexities.
     *      
     *      Appropriate comments justifying time and space claims (TCJ and SCJ) 
     */
    protected int numElts = 0;            	// Number of elements in the list
    public final ArrayDNode<E> header, trailer;	// Special sentinels, not stored IN the array
    private ArrayDNode<E>[] elements = new ArrayDNode[1];
    
    @TimeComplexity("O(1)")
    public ArraySequence(){
        numElts = 0;
        header = new ArrayDNode<E>(false, null);	// create header
        trailer = new ArrayDNode<E>(true, header);	// create trailer
        header.setNext(trailer);	// make header and trailer point to each other
    }
    
    @TimeComplexity("O(1)")
    private void checkIndex(int i, int size) throws BoundaryViolationException {
        if ( (i < 0) || (i >= size) ) {
            throw new BoundaryViolationException( "Illegal index: " + i + " with a size of " + size  +" : R : " + this.elements.length );
        }
    }
    
    @Override  @TimeComplexity("O(n)") // @@@atIndex
    public Position<E> atIndex( int aIndex ) throws BoundaryViolationException
    {// Makeing sure this index is real
        try {
            checkIndex( aIndex , size() );
        } catch ( BoundaryViolationException e ) {
            //e.printStackTrace(); // Debuging
            throw e;
        }
        // Prevent missmatched indexs
        ArrayDNode<E> ans = this.elements[ aIndex ];
        
        if (ans == null){
            return ans; // DEBUG
        }
        
        ans.setIndex(aIndex);
        return ans;
    }
    
    /** Checks if position is valid for this list and converts it to
    *  DNode if it is valid; O(1) time */
     @TimeComplexity("O(1)")
    protected ArrayDNode<E> checkPosition(Position<E> p)
      throws InvalidPositionException {
       
      if (p == null)
        throw new InvalidPositionException
            ("Null position passed to NodeList");
      if (p == header)
        throw new InvalidPositionException
            ("The header node is not a valid position");
      if (p == trailer)
        throw new InvalidPositionException
            ("The trailer node is not a valid position");

      try {
        ArrayDNode<E> temp = (ArrayDNode<E>) p;
        //if ((temp.getPrev() == null) || (temp.getNext() == null))
                //throw new InvalidPositionException
                //("Position does not belong to a valid NodeList");
        return temp;
      } catch (ClassCastException e) {
        throw new InvalidPositionException
          ("Position is of wrong type for this list");
      }
    }
    
    @Override  @TimeComplexity("O(1)")
    public int indexOf( Position<E> aPosition ) throws InvalidPositionException
    {
        if (aPosition == null){
            throw new NullPointerException("Given NULL Position!");
        }
        // Get the Node for this Position
        ArrayDNode arg = checkPosition(aPosition);
        if (arg == null){
            throw new InvalidPositionException("No such Position!");
        }
        // Check that the index for this Node is valid.
        
       this.checkIndex( arg.getIndex() , size());

        // Now make sure it is correct.
       ArrayDNode<E> element = (ArrayDNode)this.elements[ arg.getIndex() ];
        if ( ((E)arg.element()).equals( ((E)element.element()) )  ) {
            return arg.getIndex();
        }else{
            throw new InvalidPositionException
        ("<!> Arrays mixed up or misslabeld! Looking for :" + 
                aPosition.element() + ": | Found different position :" 
                + arg.element());
        }
        // Position was not found
    }

    /** Returns whether the list is empty;  O(1) time  */
    @TimeComplexity("O(1)")
    public boolean isEmpty() { return (numElts == 0); }
    
    @TimeComplexity("O(1)")
    public int length()
    {
        return elements.length;
    }
    
    /** Returns the number of elements in the list;  O(1) time */
    @TimeComplexity("O(1)")
    public int size() { 
        return numElts; // @@@SIZE
    }

    @Override @TimeComplexity("O(n)") @TimeComplexityExpected("O(1)")
    public void add( int aIndex, E aElement ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex > this.length() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid for Array: " + aIndex );
        }
        if ( ( aIndex > this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid for Size" );
        }
        int n = this.size();
        // TCJ: The time complexity does not vary with input size,
        // thus the running time is constant.
        if ( aIndex == 0 )
        {
           this.addFirst( aElement );
        }
        else if ( aIndex == n - 1 )
        {
            this.addLast( aElement );
        }
        else
        {
            this.addBefore( this.atIndex( aIndex ), aElement ); // OFF BY ONE POSSIBLE
        }
    }
    
    @TimeComplexity("O(n)")
    public ArrayDNode[] repack(){
        this.elements = this.resize(elements, this.length());
        return this.elements;
    }
    
       /**
     * Resize the given array
     * 
     * @param elems
     * @param newsize
     * @return array of new size
     */
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    public ArrayDNode[] resize(ArrayDNode[] elems, int newsize){
        if(newsize < 0){
            throw new IllegalArgumentException();
        }
        
        ArrayDNode[] newelems = new ArrayDNode[newsize];
        int count = 0;
        
        if (elems == null){
            return newelems;
        }
        // Add elements back in
        for (ArrayDNode cur : this.elements){
            if (cur != null){
                ArrayDNode pre = null, post = null;
                
                if (count > 0){
                    pre = newelems[count-1];
                }else{
                    pre = this.header;
                }
                if (count < newsize-1){
                    post = newelems[count+1];
                }else{
                    post = this.trailer;
                }
                // Reform
                cur.setPrev(pre);
                cur.setNext(post);
                cur.setIndex(count);
                
                newelems[count] = cur;
                count++;
            }
        }
        
        this.numElts = count;
        this.elements = newelems;

        return newelems;
    }
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    public int findElem(E arg){

        // Backup search function
        for( int x = 0; x < size(); x++ ){
            ArrayDNode cur = this.elements[x];
            cur.setIndex(numElts);
            
            if ( ((E)cur.element()).equals(arg) ){
                // Found it
                return x;
            }
        }
        throw new Error("Element was not found! @findElem(E)");
    }
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    public int findElem( ArrayDNode<E> arg ) {
        // Sift
        if (arg == null){
            throw new IllegalArgumentException("Null arg");
        }

        int len = this.size();
        
        for (int x = 0; x < len; x++){
            // Might aswell affirm indexes too
            // Since this might be rag tag
            ArrayDNode<E> cur = this.elements[x];
            cur.setIndex(x);
            
            if ( x-1 > 0 ){
                cur.setPrev(this.elements[x-1]);
                this.elements[x-1].setNext(cur);
            }


            if ( arg.element().equals(cur.element())  ){
                return cur.getIndex();
            }
        }
        
        throw new Error("Element not contained"); // NoSuchElementException
    }
    
    @Override @TimeComplexity("O(1)")
    public E get( int aIndex ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid " + aIndex );
        }
        // TCJ: The atIndex function has a worst case cost of O(1) if the
        // contains n elements.  Thus, the time complexity of
        // this function is O(1) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1)
        return this.atIndex( aIndex ).element();
    }
    
    @TimeComplexity("O(1)")
    public E get( int aIndex, boolean force ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) && force == false )
        {
            throw new IndexOutOfBoundsException( "Index is invalid " + aIndex );
        }
        // TCJ: The atIndex function has a worst case cost of O(1) if the
        // contains n elements.  Thus, the time complexity of
        // this function is O(1) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1)
        return this.atIndex( aIndex ).element();
    }
    

    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    public E remove(int i) throws InvalidPositionException
    {   
        // DEBUGGER! REMOVE ME!

        Position<E> t = this.atIndex(i);
        
        if (i < 0 || i > this.size()){
            // MF
            System.out.println("[!] Oversized Index found: " + i + " VS " + i);
            
            throw new Error("TRACE ME!");
        }
        //*/
        
        this.elements[i] = null;

        shiftDown( i ); // O(N)
        //numElts--;

        return t.element();
    }
    
    /**Remove the given position from the list; O(1) time */
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    public E remove(Position<E> p) throws InvalidPositionException 
    { // INT
        if ((p instanceof ArrayDNode)){
            int index = (int)((ArrayDNode)p).getIndex();

            this.checkIndex(index, this.size());
            // Check 
            ArrayDNode<E> v = (ArrayDNode)p;
            
            ArrayDNode<E> vPrev = v.getPrev();
            ArrayDNode<E> vNext = v.getNext();
            // reconnect
            if (vPrev != null){
                vPrev.setNext(vNext);
            }
            if (vNext != null){
                vNext.setPrev(vPrev);
            }
            E vElem = v.element();
            // unlink the position from the list and make it invalid
            v.setNext(null);
            v.setPrev(null);
            
            
            this.elements[ v.getIndex() ] = null;
            
            shiftDown( v.getIndex() ); // O(N)
            //numElts--;
            return vElem;
        }else{
            throw new ClassCastException("Position was not an Array DNODE");
        }
    }
    
    public void adjustSize(int arg){
        // Alter the count remotly
        this.numElts += arg;
    }
    
    @TimeComplexity("O(1)")
    public void checkSize(int additive){
        if (this.size() + additive >= this.elements.length)
        {
            this.elements = this.resize(this.elements, (this.size() + additive)*2);
        }
    }
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
    private void shiftDown(int at){
        this.repack(); // Turns out, you dont need an Int
    }
    @TimeComplexity("O(n)") @TimeComplexityExpected("O(N)")
    private void insert(int at, ArrayDNode<E> item){
        // Shift the elements up
        
        at = Math.max(at,0);
        item.setIndex(at);
        
        checkSize( 1 );

        if (this.isEmpty()){
            this.elements[at] = item;
            numElts++;// ++++
            return;
        }
        
        // Shift it
        for (int i = this.size()-1; i >= at; i--) {
            int higher = i+1;
            // Nulls will be ignored.

                this.elements[higher] = this.elements[i];
            if (this.elements[higher] != null){
                this.elements[higher].setIndex(higher);
            }
        }
        
        numElts++;// ++++
        this.elements[at] = item;
    }
    //private ArrayDNode[] insert(ArrayDNode[] source, int at, ArrayDNode[] 
    

    @Override @TimeComplexity("O(1)")
    public E set( int aIndex, E aElement ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid" );
        }
        // TCJ: The atIndex function has a worst case cost of O(n) if the
        // linked list contains n elements.  Thus, the time complexity of
        // this function is O(n) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1).
        return this.set( this.atIndex( aIndex ), aElement );
    }

    
    /** Replace the element at the given position with the new element
      * and return the old element; O(1) time  */
    @TimeComplexity("O(1)")
    public E set(Position<E> p, E element)
        throws InvalidPositionException {
        // TCJ: The atIndex function has a worst case cost of O(n) if the
        // linked list contains n elements.  Thus, the time complexity of
        // this function is O(n) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1).
        ArrayDNode<E> v = checkPosition(p);
        E oldElt = v.element();
        v.setElement(element);
        return oldElt;
    }
    
    /** Insert the given element after the given position;
      * O(1) time  */
    @TimeComplexity("O(1)")
    public void addAfter(Position<E> p, E element) 
        throws InvalidPositionException {
        checkSize(1);
        ArrayDNode<E> v = checkPosition(p);
        ArrayDNode<E> newNode = new ArrayDNode<E>(v, v.getNext(), element, v.getIndex()+1);
        
        v.getNext().setPrev(newNode);
        v.setNext(newNode);
        insert( newNode.getIndex(), newNode );
    }

    /** Insert the given element before the given position;
      * O(1) time  */
    @TimeComplexity("O(1)")
    public void addBefore(Position<E> p, E element) 
        throws InvalidPositionException {
        checkSize(1);
        ArrayDNode<E> v = checkPosition(p);
        ArrayDNode<E> newNode = new ArrayDNode<E>(v.getPrev(), v, element, v.getIndex());
        
        v.getPrev().setNext(newNode);
        v.setPrev(newNode);
        insert( newNode.getIndex(), newNode );
    }

      /** Returns the first position in the list; O(1) time */
    @TimeComplexity("O(1)")
    public Position<E> first()throws EmptyListException 
    {
        if (isEmpty())
            throw new EmptyListException("List is empty");
        
        ArrayDNode<E> temp = this.elements[0];
        temp.setIndex(0);
        
        return temp;
    }

    //end#fragment first
    /** Returns the last position in the list; O(1) time
     * @return  */
    @Override @TimeComplexity("O(1)")
    public Position<E> last() throws EmptyListException {
        
      if (this.isEmpty())
            throw new EmptyListException("List is empty");
      
      this.elements[this.size()-1].setIndex(this.size()-1);
      return this.elements[this.size()-1];
    }

    /** Returns the position after the given one; O(1) time */
    @TimeComplexity("O(1)")
    public Position<E> next(Position<E> p)
        throws InvalidPositionException, BoundaryViolationException {
      ArrayDNode<E> v = checkPosition(p);
      ArrayDNode<E> next = v.getNext();
      if (next == trailer)
        throw new BoundaryViolationException
          ("Cannot advance past the end of the list");
      return next;
    }
    @TimeComplexity("O(1)")
    public Position<E> next(int index){
        return this.next( atIndex(index) );
    }

    /** Returns the position before the given one; O(1) time */
    @TimeComplexity("O(1)")
    public Position<E> prev(Position<E> p)
        throws InvalidPositionException, BoundaryViolationException {
      ArrayDNode<E> v = checkPosition(p);
      ArrayDNode<E> prev = v.getPrev();
      if (prev == header)
        throw new BoundaryViolationException
          ("Cannot advance past the beginning of the list");
      return prev;
    }

    /** Returns an iterator of all the elements in the list; O(1) time */
    @TimeComplexity("O(1)")
    public Iterator<E> iterator() { return new ElementIterator<E>(this); }

    /** Insert the given element at the beginning of the list, returning
      * the new position; O(N) time  */
    @TimeComplexity("O(n)")
    public void addFirst(E element) {
      checkSize(1);
      //numElts++;
      ArrayDNode<E> newNode = new ArrayDNode<E>(header, header.getNext(), element,0);

      ArrayDNode ahead = header.getNext();
      ahead.setPrev(newNode);
      header.setNext(newNode);
      insert( 0 , newNode);
    }

    /** Insert the given element at the end of the list, returning
      * the new position; O(1) time  */
    @TimeComplexity("O(n)") @TimeComplexityAmortized("O(n)")
    public void addLast(E element) {
        checkSize(1);
        int lastE = this.size();
        
        ArrayDNode<E> oldLast = trailer.getPrev();
        ArrayDNode<E> newNode = new ArrayDNode<E>(oldLast, trailer, element, lastE);
        // Recombine
        oldLast.setNext(newNode);
        trailer.setPrev(newNode);
        
        insert( lastE , newNode);// addin
    }
    @TimeComplexity("O(n)") @TimeComplexityAmortized("O(n)")
    public ArrayDNode<E> addLast2(E element) {
        checkSize(1);
        int lastE = this.size();
        
        ArrayDNode<E> oldLast = trailer.getPrev();
        ArrayDNode<E> newNode = new ArrayDNode<E>(oldLast, trailer, element, lastE);
        newNode.setIndex(lastE);
        // Recombine
        oldLast.setNext(newNode);
        trailer.setPrev(newNode);
        
        insert( lastE , newNode);//addin

        return newNode;
    }
    
 
    @Override @TimeComplexity("O(1)")
    public E getFirst() throws EmptyDequeException
    {
        return this.first().element();
    }

    @Override @TimeComplexity("O(n)")
    public E getLast() throws EmptyDequeException
    {
        return this.last().element();
    }

    @Override @TimeComplexity("O(n)")
    public E removeFirst() throws EmptyDequeException
    {
        if ( this.size() == 0 )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: Since the first position is available to us in
        // constant time and we can remove an element in constant
        // time, the time complexity is O(1)
        
        
        return this.remove( 0 );
    }
    @TimeComplexity("O(1)")
    public ArrayDNode<E> PositionToNode(Position<E> arg){
        // Quick converter
        if (arg instanceof ArrayDNode){
            return (ArrayDNode)arg;
        }else{
            throw new ClassCastException("Cannot Convert");
        }
    }
    
    @Override @TimeComplexity("O(1)")
    public E removeLast() throws EmptyDequeException
    {
        if ( this.isEmpty() )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: Since the last position is available to us in
        // constant time and we can remove an element in constant
        // time, the time complexity is O(1)
        
        return this.remove( this.last() );
    }

    /** Returns an iterable collection of all the nodes in the list. */
    @Override @TimeComplexity("O(1)")
    public Iterable<Position<E>> positions() 
    {     // create a list of posiitons
      return new SequenceIterable(this.elements);
    }
    /** Returns whether a position is the first one;  O(1) time */
    @TimeComplexity("O(1)")
    public boolean isFirst(Position<E> p)
      throws InvalidPositionException {  
      ArrayDNode<E> v = checkPosition(p);
      return this.elements.equals(v);
    }
    /** Returns whether a position is the last one;  O(1) time */
    @TimeComplexity("O(1)")
    public boolean isLast(Position<E> p)
        throws InvalidPositionException {  
      ArrayDNode<E> v = checkPosition(p);
      return this.elements[size()-1].equals(v);
    }
    /** Swap the elements of two give positions;  O(1) time */ 
    @TimeComplexity("O(1)")
    public void swapElements(Position<E> a, Position<E> b) 
        throws InvalidPositionException {
     
      ArrayDNode<E> pA = checkPosition(a);
      ArrayDNode<E> pB = checkPosition(b);
     

      E temp = pA.element();
      pA.setElement(pB.element());
      pB.setElement(temp);
    }
    /** Returns a textual representation of a given node list using for-each */
    @TimeComplexity("O(n)")
    public static <E> String forEachToString(PositionList<E> L) {
      String s = "[";
      int i = L.size();
      for (E elem: L) {
        s += elem; // implicit cast of the element to String
        i--;
        if (i > 0)
          s += ", "; // separate elements with a comma
      }
      s += "]";
      return s;
    }
    //begin#fragment toString
    /** Returns a textual representation of a given node list */
    @TimeComplexity("O(n)")
    public static <E> String toString(PositionList<E> l) {
      Iterator<E> it = l.iterator();
      String s = "[";
      while (it.hasNext()) {
        s += it.next();	// implicit cast of the next element to String
        if (it.hasNext())
          s += ", ";
        }
      s += "]";
      return s;
    }
    //end#fragment toString
    /** Returns a textual representation of the list */
    @Override @TimeComplexity("O(n)")
    public String toString() {
      // See above.
      return toString(this);
    }
}
