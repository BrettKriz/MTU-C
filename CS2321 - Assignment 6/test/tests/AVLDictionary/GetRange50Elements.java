package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Add 50 Elements; Test GetRange")
public class GetRange50Elements {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 50, 51, ..., 100; Test getRange( 0, 25 )")
	public void Test1() throws Throwable {
		for ( int lIndex = 50; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		Iterator<Entry<Integer, Integer>> lIterator = 
				TARGET.getRange( 0, 25 ).iterator();
		if ( lIterator.hasNext() )
			{
				lPass = false;
			}
		
		org.junit.Assert.assertEquals("Insert 50, 51, ..., 100; Test getRange( 0, 25 )", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 50, 51, ..., 100; Test getRange( 25, 75 )")
	public void Test2() throws Throwable {
		for ( int lIndex = 50; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		int lKeyRangeStart = 25;
		int lKeyRangeEnd = 75;
		Iterator<Entry<Integer, Integer>> lIterator = 
				TARGET.getRange( lKeyRangeStart, lKeyRangeEnd ).iterator();
		Integer lKeySum = 0;
		while ( lIterator.hasNext() )
			{
				Integer lKey = lIterator.next().getKey();
				if ( ( lKey < lKeyRangeStart ) || ( lKey > lKeyRangeEnd ) )
				{
					lPass = false;
					break;
				}
				lKeySum += lKey;
			}
		if ( lKeySum != 1625 )
			{
				lPass = false;
			}
		
		org.junit.Assert.assertEquals("Insert 50, 51, ..., 100; Test getRange( 25, 75 )", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 50, 51, ..., 100; Test getRange( 50, 100 )")
	public void Test3() throws Throwable {
		for ( int lIndex = 50; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		int lKeyRangeStart = 50;
		int lKeyRangeEnd = 100;
		Iterator<Entry<Integer, Integer>> lIterator = 
				TARGET.getRange( lKeyRangeStart, lKeyRangeEnd ).iterator();
		Integer lKeySum = 0;
		while ( lIterator.hasNext() )
			{
				Integer lKey = lIterator.next().getKey();
				if ( ( lKey < lKeyRangeStart ) || ( lKey > lKeyRangeEnd ) )
				{
					lPass = false;
					break;
				}
				lKeySum += lKey;
			}
		if ( lKeySum != 3825 )
			{
				lPass = false;
			}
		
		org.junit.Assert.assertEquals("Insert 50, 51, ..., 100; Test getRange( 50, 100 )", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 50, 51, ..., 100; Test getRange( 75, 125 )")
	public void Test4() throws Throwable {
		for ( int lIndex = 50; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		int lKeyRangeStart = 75;
		int lKeyRangeEnd = 125;
		Iterator<Entry<Integer, Integer>> lIterator = 
				TARGET.getRange( lKeyRangeStart, lKeyRangeEnd ).iterator();
		Integer lKeySum = 0;
		while ( lIterator.hasNext() )
			{
				Integer lKey = lIterator.next().getKey();
				if ( ( lKey < lKeyRangeStart ) || ( lKey > lKeyRangeEnd ) )
				{
					lPass = false;
					break;
				}
				lKeySum += lKey;
			}
		if ( lKeySum != 2275 )
			{
				lPass = false;
			}
		
		org.junit.Assert.assertEquals("Insert 50, 51, ..., 100; Test getRange( 75, 125 )", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 50, 51, ..., 100; Test getRange( 125, 150 )")
	public void Test5() throws Throwable {
		for ( int lIndex = 50; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		int lKeyRangeStart = 125;
		int lKeyRangeEnd = 150;
		Iterator<Entry<Integer, Integer>> lIterator = 
				TARGET.getRange( lKeyRangeStart, lKeyRangeEnd ).iterator();
		if ( lIterator.hasNext() )
			{
				lPass = false;
			}
		
		org.junit.Assert.assertEquals("Insert 50, 51, ..., 100; Test getRange( 125, 150 )", (Object)(true), (Object)(lPass));
	}

}
