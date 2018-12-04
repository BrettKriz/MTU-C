package B_Tests;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;

@jug.SuiteName("DijkstraTest")
public class DijkstraTest {

	private Labyrinth TARGET = init();
	private Labyrinth T = init();

	public Labyrinth init() {

		return new Labyrinth( "WeightedTinyLabyrinth.txt" );
	
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedSmallLabyrinth.txt: shortestPath( (0,3), (3,1) ) returns correct path")
	public void Test1() throws Throwable {
		TARGET = new Labyrinth( "WeightedSmallLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(0,3)(0,2)",2),
        new Walkway("(1,2)(0,2)",2),
        new Walkway("(1,2)(1,1)",2),
        new Walkway("(1,1)(1,0)",2),
        new Walkway("(2,0)(1,0)",2),
        new Walkway("(3,0)(2,0)",2),
        new Walkway("(3,1)(3,0)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(0,3), new RoomCoordinate(3,1) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedSmallLabyrinth.txt: shortestPath( (0,3), (3,1) ) returns correct path", (Object)(7), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedSmallLabyrinth.txt: shortestPath( (1,1), (2,2) ) returns correct path")
	public void Test2() throws Throwable {
		TARGET = new Labyrinth( "WeightedSmallLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(1,2)(1,1)",2),
        new Walkway("(1,3)(1,2)",5),
        new Walkway("(2,3)(1,3)",2),
        new Walkway("(2,3)(2,2)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(1,1), new RoomCoordinate(2,2) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedSmallLabyrinth.txt: shortestPath( (1,1), (2,2) ) returns correct path", (Object)(4), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=150000000)
	@jug.TestName("WeightedSmallLabyrinth.txt: shortestPath( (2,2), (1,3) ) returns correct path")
	public void Test3() throws Throwable {
		TARGET = new Labyrinth( "WeightedSmallLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(2,3)(2,2)",2),
        new Walkway("(2,3)(1,3)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(2,2), new RoomCoordinate(1,3) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedSmallLabyrinth.txt: shortestPath( (2,2), (1,3) ) returns correct path", (Object)(2), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedSmallLabyrinth.txt: shortestPath( (3,3), (1,0) ) returns correct path")
	public void Test4() throws Throwable {
		TARGET = new Labyrinth( "WeightedSmallLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(3,3)(2,3)",2),
        new Walkway("(2,3)(1,3)",2),
        new Walkway("(1,3)(1,2)",5),
        new Walkway("(1,2)(1,1)",2),
        new Walkway("(1,1)(1,0)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(3,3), new RoomCoordinate(1,0) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedSmallLabyrinth.txt: shortestPath( (3,3), (1,0) ) returns correct path", (Object)(5), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=1500000000)
	@jug.TestName("WeightedSmallLabyrinth.txt: shortestPath( (2,3), (0,2) ) returns correct path")
	public void Test5() throws Throwable {
		TARGET = new Labyrinth( "WeightedSmallLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(2,3)(1,3)",2),
        new Walkway("(1,3)(1,2)",5),
        new Walkway("(1,2)(0,2)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(2,3), new RoomCoordinate(0,2) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedSmallLabyrinth.txt: shortestPath( (2,3), (0,2) ) returns correct path", (Object)(3), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=1500000000)
	@jug.TestName("WeightedMediumLabyrinth.txt: shortestPath( (6,3), (5,6) ) returns correct path")
	public void Test6() throws Throwable {
		TARGET = new Labyrinth( "WeightedMediumLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(6,3)(5,3)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,5)(5,4)",2),
        new Walkway("(5,6)(5,5)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(6,3), new RoomCoordinate(5,6) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedMediumLabyrinth.txt: shortestPath( (6,3), (5,6) ) returns correct path", (Object)(4), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedMediumLabyrinth.txt: shortestPath( (2,1), (3,4) ) returns correct path")
	public void Test7() throws Throwable {
		TARGET = new Labyrinth( "WeightedMediumLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(2,1)(1,1)",2),
        new Walkway("(1,1)(0,1)",2),
        new Walkway("(0,2)(0,1)",2),
        new Walkway("(1,2)(0,2)",2),
        new Walkway("(1,3)(1,2)",2),
        new Walkway("(2,3)(1,3)",2),
        new Walkway("(2,4)(2,3)",2),
        new Walkway("(3,4)(2,4)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(2,1), new RoomCoordinate(3,4) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedMediumLabyrinth.txt: shortestPath( (2,1), (3,4) ) returns correct path", (Object)(8), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=1500000000)
	@jug.TestName("WeightedMediumLabyrinth.txt: shortestPath( (4,2), (1,3) ) returns correct path")
	public void Test8() throws Throwable {
		TARGET = new Labyrinth( "WeightedMediumLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(5,2)(4,2)",2),
        new Walkway("(5,3)(5,2)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(2,4)(2,3)",2),
        new Walkway("(2,3)(1,3)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(4,2), new RoomCoordinate(1,3) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedMediumLabyrinth.txt: shortestPath( (4,2), (1,3) ) returns correct path", (Object)(8), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedMediumLabyrinth.txt: shortestPath( (0,6), (6,0) ) returns correct path")
	public void Test9() throws Throwable {
		TARGET = new Labyrinth( "WeightedMediumLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(0,6)(0,5)",2),
        new Walkway("(1,5)(0,5)",2),
        new Walkway("(1,6)(1,5)",2),
        new Walkway("(2,6)(1,6)",2),
        new Walkway("(2,6)(2,5)",2),
        new Walkway("(2,5)(2,4)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,3)(5,2)",2),
        new Walkway("(6,2)(5,2)",2),
        new Walkway("(6,2)(6,1)",2),
        new Walkway("(6,1)(6,0)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(0,6), new RoomCoordinate(6,0) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedMediumLabyrinth.txt: shortestPath( (0,6), (6,0) ) returns correct path", (Object)(14), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedMediumLabyrinth.txt: shortestPath( (5,0), (2,2) ) returns correct path")
	public void Test10() throws Throwable {
		TARGET = new Labyrinth( "WeightedMediumLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(5,1)(5,0)",2),
        new Walkway("(5,2)(5,1)",2),
        new Walkway("(5,3)(5,2)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(2,4)(2,3)",2),
        new Walkway("(2,3)(2,2)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(5,0), new RoomCoordinate(2,2) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedMediumLabyrinth.txt: shortestPath( (5,0), (2,2) ) returns correct path", (Object)(9), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedLargeLabyrinth.txt: shortestPath( (12,6), (9,3) ) returns correct path")
	public void Test11() throws Throwable {
		TARGET = new Labyrinth( "WeightedLargeLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(12,6)(12,5)",2),
        new Walkway("(12,5)(12,4)",2),
        new Walkway("(12,4)(11,4)",2),
        new Walkway("(11,4)(10,4)",2),
        new Walkway("(10,4)(9,4)",2),
        new Walkway("(9,4)(9,3)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(12,6), new RoomCoordinate(9,3) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedLargeLabyrinth.txt: shortestPath( (12,6), (9,3) ) returns correct path", (Object)(6), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedLargeLabyrinth.txt: shortestPath( (11,1), (1,4) ) returns correct path")
	public void Test12() throws Throwable {
		TARGET = new Labyrinth( "WeightedLargeLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(12,1)(11,1)",2),
        new Walkway("(12,2)(12,1)",2),
        new Walkway("(12,3)(12,2)",2),
        new Walkway("(12,4)(12,3)",2),
        new Walkway("(12,4)(11,4)",2),
        new Walkway("(11,4)(10,4)",2),
        new Walkway("(10,4)(9,4)",2),
        new Walkway("(9,5)(9,4)",2),
        new Walkway("(9,6)(9,5)",2),
        new Walkway("(9,6)(8,6)",2),
        new Walkway("(8,6)(8,5)",2),
        new Walkway("(8,5)(7,5)",2),
        new Walkway("(7,5)(7,4)",2),
        new Walkway("(7,4)(6,4)",2),
        new Walkway("(6,4)(6,3)",2),
        new Walkway("(6,3)(5,3)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(2,4)(1,4)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(11,1), new RoomCoordinate(1,4) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedLargeLabyrinth.txt: shortestPath( (11,1), (1,4) ) returns correct path", (Object)(21), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedLargeLabyrinth.txt: shortestPath( (4,9), (5,3) ) returns correct path")
	public void Test13() throws Throwable {
		TARGET = new Labyrinth( "WeightedLargeLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(5,9)(4,9)",2),
        new Walkway("(5,9)(5,8)",2),
        new Walkway("(5,8)(5,7)",2),
        new Walkway("(5,7)(4,7)",2),
        new Walkway("(4,7)(3,7)",2),
        new Walkway("(3,7)(3,6)",2),
        new Walkway("(3,6)(3,5)",2),
        new Walkway("(4,5)(3,5)",2),
        new Walkway("(5,5)(4,5)",2),
        new Walkway("(5,5)(5,4)",2),
        new Walkway("(5,4)(5,3)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(4,9), new RoomCoordinate(5,3) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedLargeLabyrinth.txt: shortestPath( (4,9), (5,3) ) returns correct path", (Object)(11), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedLargeLabyrinth.txt: shortestPath( (0,2), (9,9) ) returns correct path")
	public void Test14() throws Throwable {
		TARGET = new Labyrinth( "WeightedLargeLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(1,2)(0,2)",2),
        new Walkway("(1,3)(1,2)",2),
        new Walkway("(2,3)(1,3)",2),
        new Walkway("(2,4)(2,3)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(6,3)(5,3)",2),
        new Walkway("(6,4)(6,3)",2),
        new Walkway("(7,4)(6,4)",2),
        new Walkway("(7,5)(7,4)",2),
        new Walkway("(8,5)(7,5)",2),
        new Walkway("(8,6)(8,5)",2),
        new Walkway("(8,7)(8,6)",2),
        new Walkway("(9,7)(8,7)",2),
        new Walkway("(9,8)(9,7)",2),
        new Walkway("(9,8)(8,8)",2),
        new Walkway("(8,8)(7,8)",2),
        new Walkway("(7,9)(7,8)",2),
        new Walkway("(8,9)(7,9)",2),
        new Walkway("(8,10)(8,9)",2),
        new Walkway("(9,10)(8,10)",2),
        new Walkway("(9,10)(9,9)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(0,2), new RoomCoordinate(9,9) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedLargeLabyrinth.txt: shortestPath( (0,2), (9,9) ) returns correct path", (Object)(24), (Object)(lCorrectEdges));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("WeightedLargeLabyrinth.txt: shortestPath( (13,0), (2,2) ) returns correct path")
	public void Test15() throws Throwable {
		TARGET = new Labyrinth( "WeightedLargeLabyrinth.txt" );
		Walkway[] lWalkways = 
    {
        new Walkway("(13,1)(13,0)",2),
        new Walkway("(13,2)(13,1)",2),
        new Walkway("(13,2)(12,2)",2),
        new Walkway("(12,3)(12,2)",2),
        new Walkway("(12,4)(12,3)",2),
        new Walkway("(12,4)(11,4)",2),
        new Walkway("(11,4)(10,4)",2),
        new Walkway("(10,4)(9,4)",2),
        new Walkway("(9,5)(9,4)",2),
        new Walkway("(9,6)(9,5)",2),
        new Walkway("(9,6)(8,6)",2),
        new Walkway("(8,6)(8,5)",2),
        new Walkway("(8,5)(7,5)",2),
        new Walkway("(7,5)(7,4)",2),
        new Walkway("(7,4)(6,4)",2),
        new Walkway("(6,4)(6,3)",2),
        new Walkway("(6,3)(5,3)",2),
        new Walkway("(5,4)(5,3)",2),
        new Walkway("(5,4)(4,4)",2),
        new Walkway("(4,4)(3,4)",2),
        new Walkway("(3,4)(2,4)",2),
        new Walkway("(2,4)(2,3)",2),
        new Walkway("(2,3)(2,2)",2),

    }
		;
		Sequence<Edge<Walkway>> lPath = TARGET.shortestPath( new RoomCoordinate(13,0), new RoomCoordinate(2,2) );
		Edge<Walkway> lEdge = null;
		int lCorrectEdges = 0;
		int lCurrentEdge = 0;
		while ( !( lPath.isEmpty() ) )
    {
        lEdge = lPath.removeFirst();
        String lEdgeName = lEdge.element().getName();
        String lVertexName1 = lEdgeName.substring(0,lEdgeName.indexOf( ")" ) + 1);
        String lVertexName2 = lEdgeName.substring(lEdgeName.indexOf( ")" ) + 1 );
        
        String lPossibleEdge1 = lVertexName1 + lVertexName2;
        String lPossibleEdge2 = lVertexName2 + lVertexName1;
    
        String lActualEdgeString = lWalkways[lCurrentEdge].getName();
    
        if ( lActualEdgeString.equals( lPossibleEdge1 ) ||
             lActualEdgeString.equals( lPossibleEdge2 ) )
        {
            lCorrectEdges++;
        }
        
        lCurrentEdge++;
    }
		
		org.junit.Assert.assertEquals("WeightedLargeLabyrinth.txt: shortestPath( (13,0), (2,2) ) returns correct path", (Object)(23), (Object)(lCorrectEdges));
	}

}
