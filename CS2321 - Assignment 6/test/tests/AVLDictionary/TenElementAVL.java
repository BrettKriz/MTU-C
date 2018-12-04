package tests.AVLDictionary;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;
import java.util.Random;
import java.util.Iterator;

@jug.SuiteName("Ten Element AVL Tree")
public class TenElementAVL {

	private AVLDictionary<Integer, Integer> TARGET = init();
	private AVLDictionary<Integer, Integer> T = init();

	public AVLDictionary<Integer, Integer> init() {
		return new AVLDictionary<Integer, Integer>();
	}

	@org.junit.Test(timeout=10000)
	@jug.TestName("Insert 1, 2, ..., 10")
	public void Test1() throws Throwable {
		for ( int lIndex = 1; lIndex <= 10; lIndex++ )
			{
				TARGET.insert( lIndex, lIndex );
			}
		boolean lPass = true;
		for ( int lIndex = 1; lIndex <= 10; lIndex++ )
			{
				lPass = lPass && ( TARGET.find( lIndex ).getKey() == lIndex );
			}
		
		org.junit.Assert.assertEquals("Insert 1, 2, ..., 10", (Object)(true), (Object)(lPass));
	}

}
