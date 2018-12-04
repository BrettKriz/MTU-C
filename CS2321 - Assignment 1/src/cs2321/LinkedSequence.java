package cs2321;

import net.datastructures.*;

/**
 * A linked list representation of a sequence.
 * 
 * A linked list of nodes is used for storage: O(n) space complexity for n items
 * 
 * The following functions are O(n) time complexity:
 *     atIndex; indexOf; get; remove( int aIndex ); set( int aIndex, E aElement )
 *     
 * The following functions are O(1) time complexity:
 *     getFirst; getLast; removeFirst; removeLast; add; size; isEmpty;
 *     first; last; prev; next; addBefore; addAfter; addFirst; addLast;
 *     remove( Position<E> p ); set( Position<E> p, E element ); iterator
 *     
 * Note: Iterators and toString create objects with O(n) space.
 * 
 * Course: CS2321 Section ALL
 * Assignment: #1
 * 
 * @author Dustin Larson (dflarson@mtu.edu)
 *
 * @param <E> The data type that is stored in the LinkedSequence nodes
 */
@SpaceComplexity ("O(n)")
public class LinkedSequence<E> extends NodePositionList<E> implements Sequence<E> 
{
    /**
     * Returns the position containing the element at the given index.
     * 
     * @param aIndex The index of the element to retrieve
     * 
     * @return The position containing the element at the given index.
     * 
     * @throws BoundaryViolationException
     * 
     * @see net.datastructures.Sequence#atIndex(int)
     */
    @TimeComplexity("O(n)")
    public Position<E> atIndex( int aIndex ) throws BoundaryViolationException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) )
        {
            throw new BoundaryViolationException( "Index is invalid" );
        }
        int lCount = aIndex;
        DNode<E> lCurrentNode = this.header.getNext();
        // TCJ: the loop will execute n times in the worst case that there
        // are n items and we are moving to the last index in the linked
        // list, so the cost is O(n)
        // Note that the best case is when the index is 0, which means
        // that the loop will not execute, and thus the best case time
        // complexity is O(1).
        while ( lCount > 0 )
        {
            lCurrentNode = lCurrentNode.getNext();
            lCount--;
        }
        return lCurrentNode;
    }

    /**
     * Returns the index of the element stored at the given position.
     * 
     * @param aPosition The position of the element to get the index of
     * 
     * @return The index of the element at the given position
     * 
     * @throws InvalidPositionException
     * 
     * @see net.datastructures.Sequence#indexOf(net.datastructures.Position)
     */
    @TimeComplexity("O(n)")
    public int indexOf( Position<E> aPosition ) throws InvalidPositionException
    {
        int lIndex = 0;
        DNode<E> lCurrentNode = this.header.getNext();
        // TCJ: the loop will execute n times in the worst case that there
        // are n items and we have to traverse all the way to the last index
        // in the list, so the cost is O(n)
        while ( ( lCurrentNode != aPosition ) &&
                ( lCurrentNode != this.trailer ) )
        {
            lCurrentNode = lCurrentNode.getNext();
            lIndex++;
        }
        
        if ( lCurrentNode == this.trailer )
        {
            throw new InvalidPositionException( "Position is invalid for this sequence" );
        }
        return lIndex;
    }

    /**
     * Returns the first element; an exception is thrown if deque is empty.
     * 
     * @return The first element in the deque
     * 
     * @throws EmptyDequeException if the deque is empty
     * 
     * @see net.datastructures.Deque#getFirst()
     */
    @TimeComplexity("O(1)")
    public E getFirst() throws EmptyDequeException
    {
        if ( this.size() == 0 )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: The time complexity does not vary with input size,
        // thus the running time is constant.
        return first().element();
    }

    /**
     * Returns the last element; an exception is thrown if deque is empty.
     * 
     * @return The last element in the deque
     * 
     * @throws EmptyDequeException if the deque is empty
     * 
     * @see net.datastructures.Deque#getLast()
     */
    @TimeComplexity("O(1)")
    public E getLast() throws EmptyDequeException
    {
        if ( this.size() == 0 )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: The time complexity does not vary with input size,
        // thus the running time is constant.
        return last().element();
    }

    /**
     * Removes the first element; an exception is thrown if deque is empty.
     * 
     * @return The first element, which was just removed from the deque.
     * 
     * @throws EmptyDequeException if the deque is empty
     * 
     * @see net.datastructures.Deque#removeFirst()
     */
    @TimeComplexity("O(1)")
    public E removeFirst() throws EmptyDequeException
    {
        if ( this.size() == 0 )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: Since the first position is available to us in
        // constant time and we can remove an element in constant
        // time, the time complexity is O(1)
        return this.remove( this.first() );
    }

    /**
     * Removes the last element; an exception is thrown if deque is empty.
     * 
     * @return The last element, which was just removed from the deque.
     * 
     * @throws EmptyDequeException if the deque is empty
     * 
     * @see net.datastructures.Deque#removeLast()
     */
    @TimeComplexity("O(1)")
    public E removeLast() throws EmptyDequeException
    {
        if ( this.size() == 0 )
        {
            throw new EmptyDequeException( "Sequence is empty" );
        }
        // TCJ: Since the last position is available to us in
        // constant time and we can remove an element in constant
        // time, the time complexity is O(1)
        return this.remove( this.last() );
    }

    /**
     * Inserts an element aElement to be at index aIndex, shifting all elements after this
     * 
     * @param aIndex The index to insert the element at
     * @param aElement The element to insert at the given index
     * 
     * @throws IndexOutOfBoundsException if the given index is invalid
     * 
     * @see net.datastructures.IndexList#add(int, java.lang.Object)
     */
    @TimeComplexity("O(n)")
    public void add( int aIndex, E aElement ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex > this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid" );
        }
        // TCJ: The time complexity does not vary with input size,
        // thus the running time is constant.
        if ( aIndex == 0 )
        {
           this.addFirst( aElement );
        }
        else if ( aIndex == this.size() - 1 )
        {
            this.addLast( aElement );
        }
        else
        {
            this.addBefore( this.atIndex( aIndex ), aElement );
        }
    }

    /**
     * Returns the element at index aIndex without removing it
     * 
     * @param aIndex The index to get the element from
     * 
     * @return The element at the given index
     * 
     * @throws IndexOutOfBoundsException if the given index is invalid
     * 
     * @see net.datastructures.IndexList#get(int)
     */
    @TimeComplexity("O(n)")
    public E get( int aIndex ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid" );
        }
        // TCJ: The atIndex function has a worst case cost of O(n) if the
        // linked list contains n elements.  Thus, the time complexity of
        // this function is O(n) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1)
        return this.atIndex( aIndex ).element();
    }

    /**
     * Removes and returns the element at index aIndex, shifting the elements after this.
     * 
     * @param aIndex The index to remove the element at
     * 
     * @return The element removed from the list
     * 
     * @throws IndexOutOfBoundsException if the given index is invalid
     * 
     * @see net.datastructures.IndexList#remove(int)
     */
    @TimeComplexity("O(n)")
    public E remove( int aIndex ) throws IndexOutOfBoundsException
    {
        if ( ( aIndex < 0 ) || ( aIndex >= this.size() ) )
        {
            throw new IndexOutOfBoundsException( "Index is invalid" );
        }
        // TCJ: The atIndex function has a worst case cost of O(n) if the
        // linked list contains n elements.  Thus, the time complexity of
        // this function is O(n) in the worst case.  Note that, in the best
        // case, the index given is 0, which makes the best case time
        // complexity constant time, O(1)
        return this.remove( this.atIndex( aIndex ) );
    }

    /**
     * Replaces the element at index aIndex with aElement, returning the previous element at aIndex.
     * 
     * @param aIndex The index to replace the element at
     * @param aElement The element to replace the element at the given index
     * 
     * @return The element that was replaced at the given index
     * 
     * @throws IndexOutOfBoundsException if the given index is invalid
     * 
     * @see net.datastructures.IndexList#set(int, java.lang.Object)
     */
    @TimeComplexity("O(n)")
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
    
    /**
     * The main function is used as a test driver to test the LinkedSequence.
     * 
     * @param args arguments to the program.  They are not currently used.
     */
    @TimeComplexity("O(?)")
    @SpaceComplexity("O(?)")
    static public void main( String [] args )
    {
        //This is a test driver for the LinkedSequence class.
        LinkedSequence<Integer> lLS = new LinkedSequence<Integer>();
        checkExpectedSize( lLS, 0 );
        checkString( lLS,"[]" );
        if ( lLS.isEmpty() == false )
        {
            System.out.println( "FAILED: isEmpty did not return empty" );
        }
        else
        {
            System.out.println( "PASSED: isEmpty returned empty" );
        }
        
        lLS.addFirst( 1 );
        checkExpectedSize( lLS, 1 );
        checkString( lLS,"[1]" );
        if ( lLS.isEmpty() == true )
        {
            System.out.println( "FAILED: isEmpty returned empty" );
        }
        else
        {
            System.out.println( "PASSED: isEmpty did not return empty" );
        }

        lLS.removeFirst();
        checkExpectedSize( lLS, 0 );
        checkString( lLS,"[]" );

        lLS.add( 0, 2 );
        checkExpectedSize( lLS, 1 );
        checkString( lLS,"[2]" );
        
        lLS.remove( 0 );
        checkExpectedSize( lLS, 0 );
        checkString( lLS,"[]" );
        
        lLS.addLast( 3 );
        checkExpectedSize( lLS, 1 );
        checkString( lLS,"[3]" );
        
        lLS.removeLast();
        checkExpectedSize( lLS, 0 );
        checkString( lLS,"[]" );
        
        lLS.addFirst( 1 );
        lLS.addAfter( lLS.first(), 2 );
        lLS.addAfter( lLS.last(), 5 );
        lLS.addBefore( lLS.first(), 0 );
        lLS.addBefore( lLS.last(), 4 );
        lLS.addAfter( lLS.atIndex( 2 ), 3 );
        checkExpectedSize( lLS, 6 );
        checkString( lLS, "[0, 1, 2, 3, 4, 5]" );
        
        for ( int lIndex = 0; lIndex < 6; lIndex++ )
        {
            checkElementAtIndex( lLS, lIndex, lIndex );
        }
        
        if ( lLS.getFirst() != 0 )
        {
            System.out.println( "FAILED: First element was not 0" );
        }
        else
        {
            System.out.println( "PASSED: First element was 0" );
        }

        if ( lLS.getLast() != 5 )
        {
            System.out.println( "FAILED: Last element was not 5" );
        }
        else
        {
            System.out.println( "PASSED: Last element was 5" );
        }
        
        lLS.removeFirst();
        lLS.removeLast();
        checkExpectedSize( lLS, 4 );
        checkString( lLS, "[1, 2, 3, 4]" );
 
        lLS.add( 0, 0 );
        lLS.remove( 1 );
        lLS.set( 2, 5 );
        checkExpectedSize( lLS, 4 );
        checkString( lLS, "[0, 2, 5, 4]" );
        
        lLS.remove( lLS.prev( lLS.last() ) );
        lLS.addBefore( lLS.next( lLS.first() ), 7 );
        checkExpectedSize( lLS, 4 );
        checkString( lLS, "[0, 7, 2, 4]" );
        
        lLS.set( lLS.next( lLS.first() ), 1 );
        lLS.set( lLS.last(), 3 );
        checkExpectedSize( lLS, 4 );
        checkString( lLS, "[0, 1, 2, 3]" );
        
        for ( Integer i : lLS )
        {
            System.out.print( "  " + i );
        }
        System.out.println();
        System.out.println( "Functionality Tests Complete" );
        
        /* The following timing test was modified from code by
         * Chris Brown and Bill Siever.
         */
        // Measure some insertion times. Take repeated samples to find average
        // time.
        final int SAMPLES = 10000; // Number of operations to test
        /*
         * # Notice the use of polymorphism here. We're using a "Sequence" rather
         * than a "LinkedSequence". This isn't especially important here, but
         * could be handy if we want to create a separate "sequenceTest" method
         * that would run tests on different implementations of sequences.
         */
        Sequence<Integer>[] sequences = new LinkedSequence[SAMPLES];
        for ( int i = 0; i < SAMPLES; i++ )
        {
            sequences[i] = new LinkedSequence<Integer>();
        }
        // Insert N elements into each sequence
        final int N = 100;
        System.out.println( "On average, inserting the n-th item takes:" );
        System.out.println( "  n  | time (ns)" );
        System.out.println( "----------------" );

        // This loop will print the average time taken to insert an element into
        // an empty sequence, followed by the average time taken to insert a second
        // element into a sequence, followed by the average time taken to insert a
        // third element, etc. Up to the average time taken to insert the Nth
        // element.
        for ( int i = 0; i < N; i++ )
        {
            long startTime = System.nanoTime();
            // Do a push for each sample sequence
            for ( int j = 0; j < SAMPLES; j++ )
            {
                sequences[j].addLast( i );
            }
            long stopTime = System.nanoTime();
            System.out.printf( "%4d , %9.5f %n", i + 1, ( stopTime - startTime )
                    / ( double )SAMPLES );
        }

    }
    
    /**
     * Used to see if the expected element is at the specified index
     * 
     * @param aSequence the LinkedSequence of integers to check
     * @param aIndex the index to check for the expected value
     * @param aExpectedValue the value to check for
     * 
     * @return True if the expected value is found; False otherwise 
     */
    @TimeComplexity("O(?)")
    @SpaceComplexity("O(?)")
    static public boolean checkElementAtIndex( LinkedSequence<Integer> aSequence,
                                               int aIndex, int aExpectedValue )
    {
        boolean lSuccess = true;
        if ( aSequence.get( aIndex ) != aExpectedValue )
        {
            lSuccess = false;
            System.out.println( "FAILED: Expected value at index " + aIndex + " was " +
                                aExpectedValue + " and actual value was " +
                                aSequence.get( aIndex ) );
        }
        else
        {
            System.out.println( "PASSED: Expected value at index " + aIndex +
                                " was " + aSequence.get( aIndex ) );
        }
        return lSuccess;
    }
    
    /**
     * Used to see if the size of the LinkedSequence is as expected.
     * 
     * @param aSequence the LinkedSequence to check
     * @param aExpectedSize the expected number of elements currently contained in the sequence
     * 
     * @return True if the current size of the LinkedSequence matches the expected size;
     *         False otherwise
     */
    @TimeComplexity("O(?)")
    @SpaceComplexity("O(?)")
    static public boolean checkExpectedSize( LinkedSequence<Integer> aSequence, int aExpectedSize )
    {
        boolean lSuccess = true;
        if ( aSequence.size() != aExpectedSize )
        {
            lSuccess = false;
            System.out.println( "FAILED: Expected size was " + aExpectedSize +
                                " and actual size was " + aSequence.size() );
        }
        else
        {
            System.out.println( "PASSED: Expected size was " + aExpectedSize );
        }
        return lSuccess;
    }
    
    /**
     * Used to see if the string output of the LinkedSequence is as expected.
     * 
     * @param aSequence the LinkedSequence to check
     * @param aExpectedSize the expected string output for the sequence
     * 
     * @return True if the current string output matches the expected string output;
     *         False otherwise
     */
    @TimeComplexity("O(?)")
    @SpaceComplexity("O(?)")
    static public boolean checkString( LinkedSequence<Integer> aSequence, String aExpectedString )
    {
        boolean lSuccess = true;
        if ( aSequence.toString().compareTo( aExpectedString ) != 0 )
        {
            lSuccess = false;
            System.out.println( "FAILED: Expected list was " + aExpectedString +
                                " and actual list was " + aSequence.toString() );
        }
        else
        {
            System.out.println( "PASSED: Expected list was " + aExpectedString );
        }
        return lSuccess;
    }
}
