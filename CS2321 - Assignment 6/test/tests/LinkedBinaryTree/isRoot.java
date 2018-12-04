package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("isRoot Test")
public class isRoot {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("isRoot( addRoot( 10 ) ) == true")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("isRoot( addRoot( 10 ) ) == true", (Object)(true), (Object)(TARGET.isRoot( TARGET.addRoot( 10 ) )));
	}

	@org.junit.Test()
	@jug.TestName("isRoot( root() ) == true")
	public void Test2() throws Throwable {
		TARGET.addRoot( 10 );
		
		org.junit.Assert.assertEquals("isRoot( root() ) == true", (Object)(true), (Object)(TARGET.isRoot( TARGET.root() )));
	}

	@org.junit.Test()
	@jug.TestName("isRoot( insertLeft( addRoot( 10 ), 5 ) ) == false")
	public void Test3() throws Throwable {
		
		org.junit.Assert.assertEquals("isRoot( insertLeft( addRoot( 10 ), 5 ) ) == false", (Object)(false), (Object)(TARGET.isRoot( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("isRoot( insertRight( addRoot( 10 ), 5 ) ) == false")
	public void Test4() throws Throwable {
		
		org.junit.Assert.assertEquals("isRoot( insertRight( addRoot( 10 ), 5 ) ) == false", (Object)(false), (Object)(TARGET.isRoot( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ) )));
	}

}
