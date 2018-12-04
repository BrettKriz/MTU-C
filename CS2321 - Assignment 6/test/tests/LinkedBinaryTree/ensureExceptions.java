package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Exceptions Test")
public class ensureExceptions {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Verifying root() throws EmptyTreeException")
	public void Test1() throws Throwable {
		
		{ boolean thrown = false;
			try {
				TARGET.root();
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("Verifying root() throws EmptyTreeException", t, org.hamcrest.CoreMatchers.instanceOf(EmptyTreeException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("Verifying root() throws EmptyTreeException: Expected Throwable EmptyTreeException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure insertLeft does not accept null positions")
	public void Test2() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.insertLeft( null, 5 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure insertLeft does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure insertLeft does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure insertRight does not accept null positions")
	public void Test3() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.insertRight( null, 5 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure insertRight does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure insertRight does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure hasLeft does not accept null positions")
	public void Test4() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.hasLeft( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure hasLeft does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure hasLeft does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure hasRight does not accept null positions")
	public void Test5() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.hasRight( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure hasRight does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure hasRight does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure left() does not accept null positions")
	public void Test6() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.left( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure left() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure left() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure right() does not accept null positions")
	public void Test7() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.right( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure right() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure right() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("Ensure left( addRoot( 1 ) ) throws BoundaryViolationException")
	public void Test8() throws Throwable {
		
		{ boolean thrown = false;
			try {
                                Position r = TARGET.addRoot( 1 );
				TARGET.left( r );
			} catch (Throwable t) {
                                t.printStackTrace();
				thrown = true;
				org.junit.Assert.assertThat("Ensure left( addRoot( 1 ) ) throws BoundaryViolationException", t, org.hamcrest.CoreMatchers.instanceOf(BoundaryViolationException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("Ensure left( addRoot( 1 ) ) throws BoundaryViolationException: Expected Throwable BoundaryViolationException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("Ensure right( addRoot( 1 ) ) throws BoundaryViolationException")
	public void Test9() throws Throwable {
		
		{ boolean thrown = false;
			try {
				TARGET.right( TARGET.addRoot( 1 ) );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("Ensure right( addRoot( 1 ) ) throws BoundaryViolationException", t, org.hamcrest.CoreMatchers.instanceOf(BoundaryViolationException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("Ensure right( addRoot( 1 ) ) throws BoundaryViolationException: Expected Throwable BoundaryViolationException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure remove does not allow null positions")
	public void Test10() throws Throwable {
		
		{ boolean thrown = false;
			try {
				TARGET.remove( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure remove does not allow null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure remove does not allow null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; AddRoot should throw NonEmptyTreeException")
	public void Test11() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.addRoot( 2 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; AddRoot should throw NonEmptyTreeException", t, org.hamcrest.CoreMatchers.instanceOf(NonEmptyTreeException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; AddRoot should throw NonEmptyTreeException: Expected Throwable NonEmptyTreeException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure children() does not accept null positions")
	public void Test12() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.children( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure children() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure children() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure isExternal() does not accept null positions")
	public void Test13() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.isExternal( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure isExternal() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure isExternal() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure isInternal() does not accept null positions")
	public void Test14() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.isInternal( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure isInternal() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure isInternal() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure isRoot() does not accept null positions")
	public void Test15() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.isRoot( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure isRoot() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure isRoot() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure parent() does not accept null positions")
	public void Test16() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.parent( null );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure parent() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure parent() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure parent( addRoot( 1 ) ) throws BoundaryViolationException")
	public void Test17() throws Throwable {
		
		{ boolean thrown = false;
			try {
				TARGET.parent( TARGET.addRoot( 1 ) );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure parent( addRoot( 1 ) ) throws BoundaryViolationException", t, org.hamcrest.CoreMatchers.instanceOf(BoundaryViolationException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure parent( addRoot( 1 ) ) throws BoundaryViolationException: Expected Throwable BoundaryViolationException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure replace() does not accept null positions")
	public void Test18() throws Throwable {
		TARGET.addRoot( 1 );
		
		{ boolean thrown = false;
			try {
				TARGET.replace( null, 5 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure replace() does not accept null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure replace() does not accept null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("AddRoot; Ensure attach does not allow null positions")
	public void Test19() throws Throwable {
		TARGET.addRoot( 1 );
		Position<Integer> lNullPosition = null;
		LinkedBinaryTree<Integer> T1 = new LinkedBinaryTree<Integer>();
		T1.addRoot( 1 );
		LinkedBinaryTree<Integer> T2 = new LinkedBinaryTree<Integer>();
		T2.addRoot( 2 );
		
		{ boolean thrown = false;
			try {
				TARGET.attach( lNullPosition, T1, T2 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("AddRoot; Ensure attach does not allow null positions", t, org.hamcrest.CoreMatchers.instanceOf(InvalidPositionException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("AddRoot; Ensure attach does not allow null positions: Expected Throwable InvalidPositionException");
			}
		}
	}

}
