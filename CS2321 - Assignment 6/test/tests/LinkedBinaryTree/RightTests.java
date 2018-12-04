package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("insertRight Test")
public class RightTests {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Adding Right child to root; size() == 2")
	public void Test1() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertRight( lPosition, 5 );
		
		org.junit.Assert.assertEquals("Adding Right child to root; size() == 2", (Object)(2), (Object)(TARGET.size()));
	}

	@org.junit.Test()
	@jug.TestName("hasRight( addRoot( 10 ) ) is false")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("hasRight( addRoot( 10 ) ) is false", (Object)(false), (Object)(TARGET.hasRight( TARGET.addRoot( 10 ) )));
	}

	@org.junit.Test()
	@jug.TestName("insertRight( addRoot( 10 ), 5 ) returns a Position")
	public void Test3() throws Throwable {
		
		org.junit.Assert.assertThat("insertRight( addRoot( 10 ), 5 ) returns a Position", TARGET.insertRight( TARGET.addRoot( 10 ), 5 ), org.hamcrest.CoreMatchers.instanceOf(Position.class));
	}

	@org.junit.Test()
	@jug.TestName("hasRight( insertRight( addRoot( 10 ), 5 ) ) returns false")
	public void Test4() throws Throwable {
		
		org.junit.Assert.assertEquals("hasRight( insertRight( addRoot( 10 ), 5 ) ) returns false", (Object)(false), (Object)(TARGET.hasRight( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("parent( insertRight( addRoot( 10 ), 5 ) ) is root")
	public void Test5() throws Throwable {
		Position<Integer> lRoot = TARGET.addRoot( 10 );
		
		org.junit.Assert.assertEquals("parent( insertRight( addRoot( 10 ), 5 ) ) is root", (Object)(lRoot), (Object)(TARGET.parent( TARGET.insertRight( lRoot, 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("isRoot( parent( insertRight( addRoot( 10 ), 5 ) ) ) == true")
	public void Test6() throws Throwable {
		
		org.junit.Assert.assertEquals("isRoot( parent( insertRight( addRoot( 10 ), 5 ) ) ) == true", (Object)(true), (Object)(TARGET.isRoot( TARGET.parent( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ) ) )));
	}

	@org.junit.Test()
	@jug.TestName("insertRight( addRoot( 10 ), 5 ).element() == 5")
	public void Test7() throws Throwable {
		
		org.junit.Assert.assertEquals("insertRight( addRoot( 10 ), 5 ).element() == 5", (Object)(5), (Object)(TARGET.insertRight( TARGET.addRoot( 10 ), 5 ).element()));
	}

	@org.junit.Test()
	@jug.TestName("Adding 2 elements; Child is right of Parent")
	public void Test8() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertRight( lPosition, 5 );
		Position<Integer> lRightChild = TARGET.right( lPosition );
		
		org.junit.Assert.assertEquals("Adding 2 elements; Child is right of Parent", (Object)(lRightChild), (Object)(lChild));
	}

	@org.junit.Test()
	@jug.TestName("Adding 2 elements; Parent is parent of Child")
	public void Test9() throws Throwable {
		Position<Integer> lPosition = TARGET.addRoot( 10 );
		Position<Integer> lChild = TARGET.insertRight( lPosition, 5 );
		Position<Integer> lParent = TARGET.parent( lChild );
		
		org.junit.Assert.assertEquals("Adding 2 elements; Parent is parent of Child", (Object)(lPosition), (Object)(lParent));
	}

}
