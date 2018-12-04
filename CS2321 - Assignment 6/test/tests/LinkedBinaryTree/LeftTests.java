package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("insertLeft Test")
public class LeftTests {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Adding Left child to root; size() == 2")
	public void Test1() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertLeft( lPosition, 5 );
		
		org.junit.Assert.assertEquals("Adding Left child to root; size() == 2", (Object)(2), (Object)(TARGET.size()));
	}

	@org.junit.Test()
	@jug.TestName("hasLeft( addRoot( 10 ) ) is false")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("hasLeft( addRoot( 10 ) ) is false", (Object)(false), (Object)(TARGET.hasLeft( TARGET.addRoot( 10 ) )));
	}

	@org.junit.Test()
	@jug.TestName("insertLeft( addRoot( 10 ), 5 ) returns a Position")
	public void Test3() throws Throwable {
		
		org.junit.Assert.assertThat("insertLeft( addRoot( 10 ), 5 ) returns a Position", TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ), org.hamcrest.CoreMatchers.instanceOf(Position.class));
	}

	@org.junit.Test()
	@jug.TestName("hasLeft( insertLeft( addRoot( 10 ), 5 ) ) returns false")
	public void Test4() throws Throwable {
		
		org.junit.Assert.assertEquals("hasLeft( insertLeft( addRoot( 10 ), 5 ) ) returns false", (Object)(false), (Object)(TARGET.hasLeft( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("parent( insertLeft( addRoot( 10 ), 5 ) ) is root")
	public void Test5() throws Throwable {
		Position<Integer> lRoot = TARGET.addRoot( 10 );
		
		org.junit.Assert.assertEquals("parent( insertLeft( addRoot( 10 ), 5 ) ) is root", (Object)(lRoot), (Object)(TARGET.parent( TARGET.insertLeft( lRoot, 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("isRoot( parent( insertLeft( addRoot( 10 ), 5 ) ) ) == true")
	public void Test6() throws Throwable {
		
		org.junit.Assert.assertEquals("isRoot( parent( insertLeft( addRoot( 10 ), 5 ) ) ) == true", (Object)(true), (Object)(TARGET.isRoot( TARGET.parent( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) ) )));
	}

	@org.junit.Test()
	@jug.TestName("insertLeft( addRoot( 10 ), 5 ).element() == 5")
	public void Test7() throws Throwable {
		
		org.junit.Assert.assertEquals("insertLeft( addRoot( 10 ), 5 ).element() == 5", (Object)(5), (Object)(TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ).element()));
	}

	@org.junit.Test()
	@jug.TestName("Adding 2 elements; Child is left of Parent")
	public void Test8() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertLeft( lPosition, 5 );
		Position<Integer> lLeftChild = TARGET.left( lPosition );
		
		org.junit.Assert.assertEquals("Adding 2 elements; Child is left of Parent", (Object)(lLeftChild), (Object)(lChild));
	}

	@org.junit.Test()
	@jug.TestName("Adding 2 elements; Parent is parent of Child")
	public void Test9() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertLeft( lPosition, 5 );
		Position<Integer> lParent = TARGET.parent( lChild );
		
		org.junit.Assert.assertEquals("Adding 2 elements; Parent is parent of Child", (Object)(lPosition), (Object)(lParent));
	}

}
