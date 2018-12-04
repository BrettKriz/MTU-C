package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("isInternal Test")
public class isInternal {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("isInternal( parent( insertLeft( addRoot( 10 ), 5 ) ) ) == true")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("isInternal( parent( insertLeft( addRoot( 10 ), 5 ) ) ) == true", (Object)(true), (Object)(TARGET.isInternal( TARGET.parent( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) ) )));
	}

	@org.junit.Test()
	@jug.TestName("isInternal( parent( insertRight( addRoot( 10 ), 5 ) ) ) == true")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("isInternal( parent( insertRight( addRoot( 10 ), 5 ) ) ) == true", (Object)(true), (Object)(TARGET.isInternal( TARGET.parent( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ) ) )));
	}

}
