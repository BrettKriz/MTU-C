package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Add 2 Elements Remove 1 Element")
public class Add2Remove1 {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 1, 2; Remove 1; Verify 1 is not in tree")
	public void Test1() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		TARGET.remove( lEntry1 );
		
		org.junit.Assert.assertEquals("Insert 1, 2; Remove 1; Verify 1 is not in tree", (Object)(null), (Object)(TARGET.find( 1 )));
	}

	@org.junit.Test()
	@jug.TestName("Insert 1, 2; Remove 1; Verify 2 is in tree")
	public void Test2() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		TARGET.remove( lEntry1 );
		
		org.junit.Assert.assertEquals("Insert 1, 2; Remove 1; Verify 2 is in tree", (Object)(2), (Object)(TARGET.find( 2 ).getKey()));
	}

	@org.junit.Test()
	@jug.TestName("Insert 1, 2; Remove 2; Verify 1 is in tree")
	public void Test3() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
                
		TARGET.remove( lEntry2 );
                
		Entry<Integer, Integer> ans = (TARGET.find( 1 ));
                
		org.junit.Assert.assertEquals("Insert 1, 2; Remove 2; Verify 1 is in tree", (Object)(1), (Object)ans.getKey());
	}

	@org.junit.Test()
	@jug.TestName("Insert 1, 2; Remove 2; Verify 2 is not in tree")
	public void Test4() throws Throwable {
		Entry<Integer, Integer> lEntry1 = TARGET.insert( 1, 1 );
		Entry<Integer, Integer> lEntry2 = TARGET.insert( 2, 2 );
		TARGET.remove( lEntry2 );
		
		org.junit.Assert.assertEquals("Insert 1, 2; Remove 2; Verify 2 is not in tree", (Object)(null), (Object)(TARGET.find( 2 )));
	}

}
