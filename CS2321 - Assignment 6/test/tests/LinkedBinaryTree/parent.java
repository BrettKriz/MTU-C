package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("parent Test")
public class parent {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("parent( insertLeft( insertLeft( addRoot( 10 ), 5 ), 1 ) ).element() == 5")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("parent( insertLeft( insertLeft( addRoot( 10 ), 5 ), 1 ) ).element() == 5", (Object)(5), (Object)(TARGET.parent( TARGET.insertLeft( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ), 1 ) ).element()));
	}

	@org.junit.Test()
	@jug.TestName("parent( insertLeft( insertRight( addRoot( 10 ), 5 ), 1 ) ).element() == 5")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("parent( insertLeft( insertRight( addRoot( 10 ), 5 ), 1 ) ).element() == 5", (Object)(5), (Object)(TARGET.parent( TARGET.insertLeft( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ), 1 ) ).element()));
	}

	@org.junit.Test()
	@jug.TestName("parent( insertRight( insertLeft( addRoot( 10 ), 5 ), 1 ) ).element() == 5")
	public void Test3() throws Throwable {
		
		org.junit.Assert.assertEquals("parent( insertRight( insertLeft( addRoot( 10 ), 5 ), 1 ) ).element() == 5", (Object)(5), (Object)(TARGET.parent( TARGET.insertRight( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ), 1 ) ).element()));
	}

	@org.junit.Test()
	@jug.TestName("parent( insertRight( insertRight( addRoot( 10 ), 5 ), 1 ) ).element() == 5")
	public void Test4() throws Throwable {
		
		org.junit.Assert.assertEquals("parent( insertRight( insertRight( addRoot( 10 ), 5 ), 1 ) ).element() == 5", (Object)(5), (Object)(TARGET.parent( TARGET.insertRight( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ), 1 ) ).element()));
	}

}
