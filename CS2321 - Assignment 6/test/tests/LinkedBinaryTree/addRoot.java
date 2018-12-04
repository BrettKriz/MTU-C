package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("addRoot Test")
public class addRoot {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("addRoot( 10 ) returns a Position")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertThat("addRoot( 10 ) returns a Position", TARGET.addRoot( 10 ), org.hamcrest.CoreMatchers.instanceOf(Position.class));
	}

	@org.junit.Test()
	@jug.TestName("addRoot( 10 ).element() == 10")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("addRoot( 10 ).element() == 10", (Object)(10), (Object)(TARGET.addRoot( 10 ).element()));
	}

	@org.junit.Test()
	@jug.TestName("addRoot throws an exception if root exists")
	public void Test3() throws Throwable {
		TARGET.addRoot( 10 );
		
		{ boolean thrown = false;
			try {
				TARGET.addRoot( 50 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("addRoot throws an exception if root exists", t, org.hamcrest.CoreMatchers.instanceOf(Exception.class));
			}
			if(!thrown){
				org.junit.Assert.fail("addRoot throws an exception if root exists: Expected Throwable Exception");
			}
		}
	}

	@org.junit.Test()
	@jug.TestName("addRoot throws NonEmptyTreeException if root exists")
	public void Test4() throws Throwable {
		TARGET.addRoot( 10 );
		
		{ boolean thrown = false;
			try {
				TARGET.addRoot( 50 );
			} catch (Throwable t) {
				thrown = true;
				org.junit.Assert.assertThat("addRoot throws NonEmptyTreeException if root exists", t, org.hamcrest.CoreMatchers.instanceOf(NonEmptyTreeException.class));
			}
			if(!thrown){
				org.junit.Assert.fail("addRoot throws NonEmptyTreeException if root exists: Expected Throwable NonEmptyTreeException");
			}
		}
	}

}
