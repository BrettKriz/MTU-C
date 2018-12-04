package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Add 1 Element Remove 1 Element")
public class Add1Remove1 {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=1000000000)
	@jug.TestName("Insert 1; Remove 1; Verify 1 is not in tree")
	public void Test1() throws Throwable {
		Entry<Integer, Integer> lEntry = TARGET.insert( 1, 1 );
		TARGET.remove( lEntry );
		Object ans = (Object)(TARGET.find( 1 ));
		org.junit.Assert.assertEquals("Insert 1; Remove 1; Verify 1 is not in tree", (Object)(null), ans);
	}

}
