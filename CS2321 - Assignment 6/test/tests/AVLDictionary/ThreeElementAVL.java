package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Three Element AVL Tree")
public class ThreeElementAVL {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 1; Insert 2; Insert 3")
	public void Test1() throws Throwable {
		TARGET.insert( 1, 1 );
		TARGET.insert( 2, 2 );
		TARGET.insert( 3, 3 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 1; Insert 2; Insert 3", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 1; Insert 3; Insert 2")
	public void Test2() throws Throwable {
		TARGET.insert( 1, 1 );
		TARGET.insert( 3, 3 );
		TARGET.insert( 2, 2 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 1; Insert 3; Insert 2", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 2; Insert 1; Insert 3")
	public void Test3() throws Throwable {
		TARGET.insert( 2, 2 );
		TARGET.insert( 1, 1 );
		TARGET.insert( 3, 3 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 2; Insert 1; Insert 3", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 2; Insert 3; Insert 1")
	public void Test4() throws Throwable {
		TARGET.insert( 2, 2 );
		TARGET.insert( 3, 3 );
		TARGET.insert( 1, 1 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 2; Insert 3; Insert 1", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 3; Insert 2; Insert 1")
	public void Test5() throws Throwable {
		TARGET.insert( 3, 3 );
		TARGET.insert( 2, 2 );
		TARGET.insert( 1, 1 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 3; Insert 2; Insert 1", (Object)(true), (Object)(lPass));
	}

	@org.junit.Test()
	@jug.TestName("Insert 3; Insert 1; Insert 2")
	public void Test6() throws Throwable {
		TARGET.insert( 3, 3 );
		TARGET.insert( 1, 1 );
		TARGET.insert( 2, 2 );
		boolean lPass = true;
		lPass = lPass && ( TARGET.find( 1 ).getKey() == 1 );
		lPass = lPass && ( TARGET.find( 2 ).getKey() == 2 );
		lPass = lPass && ( TARGET.find( 3 ).getKey() == 3 );
		
		org.junit.Assert.assertEquals("Insert 3; Insert 1; Insert 2", (Object)(true), (Object)(lPass));
	}

}
