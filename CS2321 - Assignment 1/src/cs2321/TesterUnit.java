package cs2321;

import java.util.Iterator;
import net.datastructures.*;
import cs2321.*;


/**
 *
 * @author Brett Kriz
 */
public class TesterUnit {
        
    static public void main( String [] args )
    {   
        //This is a test driver for the LinkedSequence class.
        System.out.println("ARRAYSQUENCE TESTER");
                  ArraySequence<Integer> lLS;
        lLS = new ArraySequence<Integer>();
        
        
        
        checkExpectedSize( lLS, 0 );
        checkString( lLS,"[]" );
        if ( lLS.isEmpty() == false )
        {
            System.out.println( "<> <> FAILED: isEmpty did not return empty" );
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
            System.out.println( "<> <> FAILED: isEmpty returned empty" );
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
            System.out.println( "<> <> FAILED: First element was not 0" );
        }
        else
        {
            System.out.println( "PASSED: First element was 0" );
        }

        if ( lLS.getLast() != 5 )
        {
            System.out.println( "<> <> FAILED: Last element was not 5" );
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
            System.out.print( "<>  " + i );
        }
        System.out.println("\n ~~~~~~~~~~~~~ ITERATOR TEST ~~~~~~~~~~~~~~\n");
        
        for (Position p : lLS.positions()){
            
            if (p != null){
                System.out.println( "#"+p.toString()+" = "+p.element().toString());
            }
        }
        
        //*/
        
        System.out.println( "Functionality Tests Complete" );
        
        /* The following timing test was modified from code by
         * Chris Brown and Bill Siever.
         */
        // Measure some insertion times. Take repeated samples to find average
        // time.
        final int SAMPLES = 500; // Number of operations to test
        final int N = 100;
        /*
         * # Notice the use of polymorphism here. We're using a "Sequence" rather
         * than a "LinkedSequence". This isn't especially important here, but
         * could be handy if we want to create a separate "sequenceTest" method
         * that would run tests on different implementations of sequences.
         */
        
        Sequence<Integer>[] sequences   = new ArraySequence[SAMPLES];
        for ( int i = 0; i < SAMPLES; i++ )
        {
            sequences[i]                = new ArraySequence<Integer>();
            for (int x = 0; x < N; x++){
                // add 100 elements
                int c;
                //c = (int)Math.random()*550;
                c = x;
                sequences[i].addFirst( c );
            }
        }
        // Insert N elements into each sequence
        
        System.out.println( "On average, inserting the n-th item takes:" );
        System.out.println( "  n  \t time (ns)" );
        System.out.println( "--------------------------------------" );

        // This loop will print the average time taken to insert an element into
        // an empty sequence, followed by the average time taken to insert a second
        // element into a sequence, followed by the average time taken to insert a
        // third element, etc. Up to the average time taken to insert the Nth
        // element.
        
        // MODIFIED
        
        long startTime;
        long stopTime;
        Double[] times = new Double[SAMPLES];
        
        // TESTING LOOP
        // For 100 sequences..
        for ( int i = 0; i < SAMPLES; i++ )
        {
            startTime = System.nanoTime();
            // Do a push for each sample sequence
            // Check the time for the first 100 elements
            for ( int j = 0; j < N; j++ )
            { // .addLast( i );
                sequences[i].get( j ); /////////////////////////
            }
            
            
            stopTime = System.nanoTime();
            
            double delta = ( stopTime - startTime ) / ( double )N;
            times[i] = delta;
            
            System.out.printf( "%4d \t %9.5f %n", i + 1, delta );
        }

        double total = 0.0;
        
        for (Double d : times){
            // SUM IT
            total += d;
        }
        
        
        System.out.println();
        System.out.println("<> TESTS COMPLETED in " + total + " MS");
        total = total / ( double )SAMPLES;
        System.out.println();
        System.out.println("Sample #\t|\tAVG Time Taken in NS:");
       
        double stotal = total/1000000000;
        System.out.printf( "%4d \t\t|\t %9.5f %n", SAMPLES, total );
        System.out.println("Time taken in SECONDS:\n" + stotal);
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
    static public boolean checkElementAtIndex( ArraySequence<Integer> aSequence,
                                               int aIndex, int aExpectedValue )
    {
        boolean lSuccess = true;
        if ( aSequence.get( aIndex ) != aExpectedValue )
        {
            lSuccess = false;
            System.out.println( "<> <> FAILED: Expected value at index " + aIndex + " was " +
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
    static public boolean checkExpectedSize( ArraySequence<Integer> aSequence, int aExpectedSize )
    {
        boolean lSuccess = true;
        if ( aSequence.size() != aExpectedSize )
        {
            lSuccess = false;
            System.out.println( "<> <> FAILED: Expected size was " + aExpectedSize +
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
    static public boolean checkString( ArraySequence<Integer> aSequence, String aExpectedString )
    {
        boolean lSuccess = true;
        if ( aSequence.toString().compareTo( aExpectedString ) != 0 )
        {
            lSuccess = false;
            System.out.println( "<> <> FAILED: Expected list was " + aExpectedString +
                                " and actual list was " + aSequence.toString() );
        }
        else
        {
            System.out.println( "PASSED: Expected list was " + aExpectedString );
        }
        return lSuccess;
    }
}
