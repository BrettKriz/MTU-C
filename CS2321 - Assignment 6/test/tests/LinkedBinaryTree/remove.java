package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("remove Test")
public class remove {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Removing node containing 5 returns 5")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("Removing node containing 5 returns 5", (Object)(5), (Object)(TARGET.remove( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) )));
	}

	@org.junit.Test()
	@jug.TestName("remove( insertLeft( addRoot( 10 ), 5 ) ); hasLeft( root() ) is false")
	public void Test2() throws Throwable {
		TARGET.remove( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ) );
		
		org.junit.Assert.assertEquals("remove( insertLeft( addRoot( 10 ), 5 ) ); hasLeft( root() ) is false", (Object)(false), (Object)(TARGET.hasLeft( TARGET.root() )));
	}

	@org.junit.Test()
	@jug.TestName("remove( insertRight( addRoot( 10 ), 5 ) ); hasRight( root() ) is false")
	public void Test3() throws Throwable {
		TARGET.remove( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ) );
		
		org.junit.Assert.assertEquals("remove( insertRight( addRoot( 10 ), 5 ) ); hasRight( root() ) is false", (Object)(false), (Object)(TARGET.hasRight( TARGET.root() )));
	}

	@org.junit.Test()
	@jug.TestName("remove( parent( insertLeft( insertLeft( addRoot( 10 ), 5 ), 2 ) ) ); root's left child contains 2")
	public void Test4() throws Throwable {
		TARGET.remove( TARGET.parent( TARGET.insertLeft( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ), 2 ) ) );
		
		org.junit.Assert.assertEquals("remove( parent( insertLeft( insertLeft( addRoot( 10 ), 5 ), 2 ) ) ); root's left child contains 2", (Object)(2), (Object)(TARGET.left( TARGET.root() ).element()));
	}

	@org.junit.Test()
	@jug.TestName("remove( parent( insertLeft( insertRight( addRoot( 10 ), 5 ), 2 ) ) ); root's right child contains 2")
	public void Test5() throws Throwable {
		TARGET.remove( TARGET.parent( TARGET.insertLeft( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ), 2 ) ) );
		
		org.junit.Assert.assertEquals("remove( parent( insertLeft( insertRight( addRoot( 10 ), 5 ), 2 ) ) ); root's right child contains 2", (Object)(2), (Object)(TARGET.right( TARGET.root() ).element()));
	}

	@org.junit.Test()
	@jug.TestName("remove( parent( insertRight( insertLeft( addRoot( 10 ), 5 ), 2 ) ) ); root's left child contains 2")
	public void Test6() throws Throwable {
		TARGET.remove( TARGET.parent( TARGET.insertRight( TARGET.insertLeft( TARGET.addRoot( 10 ), 5 ), 2 ) ) );
		
		org.junit.Assert.assertEquals("remove( parent( insertRight( insertLeft( addRoot( 10 ), 5 ), 2 ) ) ); root's left child contains 2", (Object)(2), (Object)(TARGET.left( TARGET.root() ).element()));
	}

	@org.junit.Test()
	@jug.TestName("remove( parent( insertRight( insertRight( addRoot( 10 ), 5 ), 2 ) ) ); root's right child contains 2")
	public void Test7() throws Throwable {
		TARGET.remove( TARGET.parent( TARGET.insertRight( TARGET.insertRight( TARGET.addRoot( 10 ), 5 ), 2 ) ) );
		
		org.junit.Assert.assertEquals("remove( parent( insertRight( insertRight( addRoot( 10 ), 5 ), 2 ) ) ); root's right child contains 2", (Object)(2), (Object)(TARGET.right( TARGET.root() ).element()));
	}

}
