package tests.LinkedBinaryTree;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("children Test")
public class children {

	private LinkedBinaryTree<Integer> TARGET = init();
	private LinkedBinaryTree<Integer> T = init();

	public LinkedBinaryTree<Integer> init() {
		return new LinkedBinaryTree<Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Children of root 10 are: 5 and 8")
	public void Test1() throws Throwable {
		TARGET.addRoot( 10 );
		TARGET.insertLeft( TARGET.root(), 5 );
		TARGET.insertRight( TARGET.root(), 8 );
		Iterator<Position<Integer>> lPositions = TARGET.children( TARGET.root() ).iterator();
		boolean lLeftFound = false;
		boolean lRightFound = false;
		boolean lPassed = false;
		while ( lPositions.hasNext() )
			{
				Position<Integer> lNextPosition = lPositions.next();
				if ( lNextPosition.element() == 5 )
				{
					lLeftFound = true;
				}
				else if ( lNextPosition.element() == 8 )
				{
					lRightFound = true;
				}
			}
		lPassed = lLeftFound && lRightFound;
		
		org.junit.Assert.assertEquals("Children of root 10 are: 5 and 8", (Object)(true), (Object)(lPassed));
	}

}
