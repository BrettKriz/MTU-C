package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("replace Test")
public class replace {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Replacing data in root returns old data == 10")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("Replacing data in root returns old data == 10", (Object)(10), (Object)(TARGET.replace( TARGET.addRoot( 10 ), 2 )));
	}

	@org.junit.Test()
	@jug.TestName("Replacing data stored in root to contain 20")
	public void Test2() throws Throwable {
		TARGET.replace( TARGET.addRoot( 10 ), 20 );
		
		org.junit.Assert.assertEquals("Replacing data stored in root to contain 20", (Object)(20), (Object)(TARGET.root().element()));
	}

}
