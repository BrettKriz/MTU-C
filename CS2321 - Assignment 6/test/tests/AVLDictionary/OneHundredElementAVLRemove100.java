package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Add 100 Elements Remove 100 Elements")
public class OneHundredElementAVLRemove100 {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 1, 2, ..., 100; Remove 1, 2, ..., 100; Verify correct elements in tree using find()")
	public void Test1() throws Throwable {
		for ( int lIndex = 1; lIndex <= 100; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		for ( int lIndex = 1; lIndex <= 100; lIndex++ )
			{
				TARGET.remove( TARGET.find( lIndex ) );
			}
		boolean lPass = true;
		for ( int lIndex = 1; lIndex <= 100; lIndex++ )
			{
				lPass = lPass && ( TARGET.find( lIndex ) == null );
			}
		
		org.junit.Assert.assertEquals("Insert 1, 2, ..., 100; Remove 1, 2, ..., 100; Verify correct elements in tree using find()", (Object)(true), (Object)(lPass));
	}

}
