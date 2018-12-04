package AdjMatrixGraph;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;

@jug.SuiteName("LargeAdjMatrixTest")
public class LargeAdjMatrixTest {

	private AdjMatrixGraph TARGET = init();
	private AdjMatrixGraph T = init();

	public AdjMatrixGraph init() {
		return new AdjMatrixGraph();
	}

	@Before
	public void setup() throws Throwable {
		TARGET = ( AdjMatrixGraph<RoomCoordinate, Walkway> )( new Labyrinth( "LargeLabyrinth.txt" ).setupLabyrinth( "LargeLabyrinth.txt" ) );
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numVertices() equals 196")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("Verifying numVertices() equals 196", (Object)(196), (Object)(TARGET.numVertices()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numEdges() equals 187")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("Verifying numEdges() equals 187", (Object)(187), (Object)(TARGET.numEdges()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((10,8), (11,8)) equals false")
	public void Test3() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 10, 8 );
		RoomCoordinate c2 = new RoomCoordinate( 11, 8 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else if ( TARGET.areAdjacent( v1, v2 ) )
				lPassed = false;
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((10,8), (11,8)) equals false", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((12,3), (12,4)) equals true")
	public void Test4() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 12, 3 );
		RoomCoordinate c2 = new RoomCoordinate( 12, 4 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else if ( !TARGET.areAdjacent( v1, v2 ) )
				lPassed = false;
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((12,3), (12,4)) equals true", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((2,3), (1,3)) equals true")
	public void Test5() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 2, 3 );
		RoomCoordinate c2 = new RoomCoordinate( 1, 3 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else if ( !TARGET.areAdjacent( v1, v2 ) )
				lPassed = false;
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((2,3), (1,3)) equals true", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((6,9), (6,10)) equals false")
	public void Test6() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 6, 9 );
		RoomCoordinate c2 = new RoomCoordinate( 6, 10 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else if ( TARGET.areAdjacent( v1, v2 ) )
				lPassed = false;
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((6,9), (6,10)) equals false", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying edges() size equals 187")
	public void Test7() throws Throwable {
		int size = 0;
		Iterable<Edge<Walkway>> edges = TARGET.edges();
		for(Edge e : edges)
				size++;
		
		org.junit.Assert.assertEquals("Verifying edges() size equals 187", (Object)(187), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying vertices() size equals 196")
	public void Test8() throws Throwable {
		int size = 0;
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex v : vertices)
				size++;
		
		org.junit.Assert.assertEquals("Verifying vertices() size equals 196", (Object)(196), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 2, 3 )) size equals 4")
	public void Test9() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 2, 3 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null )
				lPassed = false;
		else{
			Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
			for(Edge edge : edges)
				size++;

			if ( size != 4 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 2, 3 )) size equals 4", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 11, 13 )) size equals 0")
	public void Test10() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 11, 13 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null )
				lPassed = false;
		else{
			Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
			for(Edge edge : edges)
				size++;

			if ( size != 0 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 11, 13 )) size equals 0", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 7, 5 )) size equals 3")
	public void Test11() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 7, 5 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null )
				lPassed = false;
		else{
			Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
			for(Edge edge : edges)
				size++;

			if ( size != 3 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 7, 5 )) size equals 3", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying opposite of edge on (10,6) is (10,5)")
	public void Test12() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 10, 6 );
		RoomCoordinate c2 = new RoomCoordinate( 10, 5 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null )
				lPassed = false;
		else{
			Edge<Walkway> e1 = null;

			Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
			for(Edge edge : edges)
			{
				e1 = edge;
				size++;
			}

			if ( size != 1 )
				lPassed = false;

			Vertex<RoomCoordinate> v2 = TARGET.opposite( v1, e1 );
			if ( v2 == null )
				lPassed = false;
			else{			
			if ( v2.element().compareTo( c2 ) != 0 )
				lPassed = false;
			}}
		
		org.junit.Assert.assertEquals("Verifying opposite of edge on (10,6) is (10,5)", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying endVertices({(6, 7), (7, 7)}) equals (6, 7), (7, 7)")
	public void Test13() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 6, 7 );
		RoomCoordinate c2 = new RoomCoordinate( 7, 7 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else
			{
			

			Walkway w1 = new Walkway( "(6,7)(7,7)", 1 );
			Walkway w2 = new Walkway( "(7,7)(6,7)", 1 );
			Iterable<Edge<Walkway>> edges = TARGET.edges();
			Edge<Walkway> e1 = null;
			for(Edge<Walkway> e : edges)
			{
            			if ( e.element().getName().compareTo( w1.getName() ) == 0 )
            			{
                			e1 = e;
                			break;
            			}
            			else if ( e.element().getName().compareTo( w2.getName() ) == 0 )
				{
					e1 = e;
					break;
				}
			}

			if ( e1 == null )
				lPassed = false;
			else
			{
			
			Vertex[] vArray = TARGET.endVertices( e1 );
			if ( ( ( Vertex<RoomCoordinate> )vArray[0] ).element().compareTo( c1 ) == 0 )
			{
				if ( ( ( Vertex<RoomCoordinate> )vArray[1] ).element().compareTo( c2 ) == 0 )
				{
					;	
				}
				else
				{
					lPassed = false;
				}
			}
			else if ( ( ( Vertex<RoomCoordinate> )vArray[1] ).element().compareTo( c1 ) == 0 )
			{
				if ( ( ( Vertex<RoomCoordinate> )vArray[0] ).element().compareTo( c2 ) == 0 )
				{
					;	
				}
				else
				{
					lPassed = false;
				}
			}
			else
			{
				lPassed = false;
			}

			}
			}
		
		org.junit.Assert.assertEquals("Verifying endVertices({(6, 7), (7, 7)}) equals (6, 7), (7, 7)", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying endVertices({(9, 10), (10, 10)}) equals (9, 10), (10, 10)")
	public void Test14() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 9, 10 );
		RoomCoordinate c2 = new RoomCoordinate( 10, 10 );
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex<RoomCoordinate> v : vertices)
			{
				if ( v.element().compareTo( c1 ) == 0 )
				{
					v1 = v;
				}
				else if ( v.element().compareTo( c2 ) == 0 )
				{
					v2 = v;
				}
			}
		boolean lPassed = true;
		if ( v1 == null || v2 == null )
				lPassed = false;
		else
			{
			

			Walkway w1 = new Walkway( "(9,10)(10,10)", 1 );
			Walkway w2 = new Walkway( "(10,10)(9,10)", 1 );
			Iterable<Edge<Walkway>> edges = TARGET.edges();
			Edge<Walkway> e1 = null;
			for(Edge<Walkway> e : edges)
			{
            			if ( e.element().getName().compareTo( w1.getName() ) == 0 )
            			{
                			e1 = e;
                			break;
            			}
            			else if ( e.element().getName().compareTo( w2.getName() ) == 0 )
				{
					e1 = e;
					break;
				}
			}

			if ( e1 == null )
				lPassed = false;
			else
			{
			
			Vertex[] vArray = TARGET.endVertices( e1 );
			if ( ( ( Vertex<RoomCoordinate> )vArray[0] ).element().compareTo( c1 ) == 0 )
			{
				if ( ( ( Vertex<RoomCoordinate> )vArray[1] ).element().compareTo( c2 ) == 0 )
				{
					;	
				}
				else
				{
					lPassed = false;
				}
			}
			else if ( ( ( Vertex<RoomCoordinate> )vArray[1] ).element().compareTo( c1 ) == 0 )
			{
				if ( ( ( Vertex<RoomCoordinate> )vArray[0] ).element().compareTo( c2 ) == 0 )
				{
					;	
				}
				else
				{
					lPassed = false;
				}
			}
			else
			{
				lPassed = false;
			}

			}
			}
		
		org.junit.Assert.assertEquals("Verifying endVertices({(9, 10), (10, 10)}) equals (9, 10), (10, 10)", (Object)(true), (Object)(lPassed));
	}

}
