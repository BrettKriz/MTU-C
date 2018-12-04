package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Empty AVL Tree")
public class sizeAndisEmptyAVL {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=60000)
	@jug.TestName("Empty tree: Size 0")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("Empty tree: Size 0", (Object)(0), (Object)(TARGET.size()));
	}

	@org.junit.Test()
	@jug.TestName("Empty tree: isEmpty is true")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("Empty tree: isEmpty is true", (Object)(true), (Object)(TARGET.isEmpty()));
	}

	@org.junit.Test()
	@jug.TestName("Adding one element: size is 1")
	public void Test3() throws Throwable {
		TARGET.insert( 10, 10 );
		
		org.junit.Assert.assertEquals("Adding one element: size is 1", (Object)(1), (Object)(TARGET.size()));
	}

	@org.junit.Test()
	@jug.TestName("Adding one element: isEmpty is false")
	public void Test4() throws Throwable {
		TARGET.insert( 10, 10 );
		
		org.junit.Assert.assertEquals("Adding one element: isEmpty is false", (Object)(false), (Object)(TARGET.isEmpty()));
	}

	@org.junit.Test()
	@jug.TestName("Adding 1 element and removing it has size 0")
	public void Test5() throws Throwable {
		Entry<Integer, Integer> lEntry = TARGET.insert( 20, 20 );
		TARGET.remove( lEntry );
		
		org.junit.Assert.assertEquals("Adding 1 element and removing it has size 0", (Object)(0), (Object)(TARGET.size()));
	}

	@org.junit.Test()
	@jug.TestName("Adding 1 element and removing it: isEmpty is true")
	public void Test6() throws Throwable {
		Entry<Integer, Integer> lEntry = TARGET.insert( 20, 20 );
		TARGET.remove( lEntry );
		
		org.junit.Assert.assertEquals("Adding 1 element and removing it: isEmpty is true", (Object)(true), (Object)(TARGET.isEmpty()));
	}

}
