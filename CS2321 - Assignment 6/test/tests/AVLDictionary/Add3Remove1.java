package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Add 3 Elements Remove 1 Element")
public class Add3Remove1 {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 1, 2, 3; Remove 1; Verify only 2 and 3 are in tree")
	public void Test1() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		Entry<Integer, Integer> lEntry3 = TARGET.insert( 3, 3 );
		TARGET.remove( lEntry1 );
		boolean lPass =
				( TARGET.find( 1 ) == null ) &&
                                ( TARGET.find( 2 ).getKey() == 2 ) &&
                                ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 1, 2, 3; Remove 1; Verify only 2 and 3 are in tree", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 1, 3, 2; Remove 2; Verify only 1 and 3 are in tree")
	public void Test2() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry3 = TARGET.insert( 3, 3 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		TARGET.remove( lEntry2 );
                
                Object ans = TARGET.find( 2 );
		boolean lPass =
				( ans == null ) &&
                                ( TARGET.find( 1 ).getKey() == 1 ) &&
                                ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 1, 3, 2; Remove 2; Verify only 1 and 3 are in tree", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 3, 1, 2; Remove 3; Verify only 1 and 2 are in tree")
	public void Test3() throws Throwable {
		Entry<Integer, Integer> lEntry3 = TARGET.insert( 3, 3 );
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		TARGET.remove( lEntry3 );
		boolean lPass =
				( TARGET.find( 3 ) == null ) &&
                                ( TARGET.find( 1 ).getKey() == 1 ) &&
                                ( TARGET.find( 2 ).getKey() == 2 );
		
		org.junit.Assert.assertEquals("Insert 3, 1, 2; Remove 3; Verify only 1 and 2 are in tree", (Object)(true), (Object)(lPass));
	}

}
