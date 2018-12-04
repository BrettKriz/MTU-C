package AdjListGraph;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;

@jug.SuiteName("SmallAdjListTest")
public class SmallAdjListTest {

	private AdjListGraph TARGET = init();
	private AdjListGraph T = init();

	public AdjListGraph init() {
		return new AdjListGraph();
	}

	@Before
	public void setup() throws Throwable {
		TARGET = ( AdjListGraph<RoomCoordinate, Walkway> )( new Labyrinth( "SmallLabyrinth.txt" ).setupLabyrinth( "SmallLabyrinth.txt" ) );
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numVertices() equals 16")
	public void Test1() throws Throwable {
		
		org.junit.Assert.assertEquals("Verifying numVertices() equals 16", (Object)(16), (Object)(TARGET.numVertices()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numEdges() equals 15")
	public void Test2() throws Throwable {
		
		org.junit.Assert.assertEquals("Verifying numEdges() equals 15", (Object)(15), (Object)(TARGET.numEdges()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((0,0), (1,0)) equals false")
	public void Test3() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 0, 0 );
		RoomCoordinate c2 = new RoomCoordinate( 1, 0 );
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
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((0,0), (1,0)) equals false", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((2,2), (3,2)) equals true")
	public void Test4() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 2, 2 );
		RoomCoordinate c2 = new RoomCoordinate( 3, 2 );
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
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((2,2), (3,2)) equals true", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((3,2), (2,2)) equals true")
	public void Test5() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 3, 2 );
		RoomCoordinate c2 = new RoomCoordinate( 2, 2 );
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
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((3,2), (2,2)) equals true", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((3,3), (3,2)) equals false")
	public void Test6() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 3, 3 );
		RoomCoordinate c2 = new RoomCoordinate( 3, 2 );
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
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((3,3), (3,2)) equals false", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying edges() size equals 15")
	public void Test7() throws Throwable {
		int size = 0;
		Iterable<Edge<Walkway>> edges = TARGET.edges();
		for(Edge e : edges)
				size++;
		
		org.junit.Assert.assertEquals("Verifying edges() size equals 15", (Object)(15), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying vertices() size equals 16")
	public void Test8() throws Throwable {
		int size = 0;
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex v : vertices)
				size++;
		
		org.junit.Assert.assertEquals("Verifying vertices() size equals 16", (Object)(16), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 1, 1 )) size equals 3")
	public void Test9() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 1, 1 );
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
		else
			{

			Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
			for(Edge edge : edges)
				size++;

			if ( size != 3 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 1, 1 )) size equals 3", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 3, 3 )) size equals 1")
	public void Test10() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 3, 3 );
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

			if ( size != 1 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 3, 3 )) size equals 1", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 0, 2 )) size equals 2")
	public void Test11() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 0, 2 );
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

			if ( size != 2 )
				lPassed = false;
			}
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 0, 2 )) size equals 2", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying opposite of edge on (3,0) is (3,1)")
	public void Test12() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = null;
		RoomCoordinate c1 = new RoomCoordinate( 3, 0 );
		RoomCoordinate c2 = new RoomCoordinate( 3, 1 );
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
		
		org.junit.Assert.assertEquals("Verifying opposite of edge on (3,0) is (3,1)", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying endVertices({(1, 2), (1, 3)}) equals (1, 2), (1, 3)")
	public void Test13() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 1, 2 );
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
		else
			{
			

			Walkway w1 = new Walkway( "(1,2)(1,3)", 1 );
			Walkway w2 = new Walkway( "(1,3)(1,2)", 1 );
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
		
		org.junit.Assert.assertEquals("Verifying endVertices({(1, 2), (1, 3)}) equals (1, 2), (1, 3)", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying endVertices({(2, 2), (3, 2)}) equals (2, 2), (3, 2)")
	public void Test14() throws Throwable {
		Vertex<RoomCoordinate> v1 = null;
		Vertex<RoomCoordinate> v2 = null;
		RoomCoordinate c1 = new RoomCoordinate( 2, 2 );
		RoomCoordinate c2 = new RoomCoordinate( 3, 2 );
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
			

			Walkway w1 = new Walkway( "(2,2)(3,2)", 1 );
			Walkway w2 = new Walkway( "(3,2)(2,2)", 1 );
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
		
		org.junit.Assert.assertEquals("Verifying endVertices({(2, 2), (3, 2)}) equals (2, 2), (3, 2)", (Object)(true), (Object)(lPassed));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that when all vertices are removed, edge count is 0")
	public void Test15() throws Throwable {
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for ( Vertex<RoomCoordinate> v : vertices )
			{
				TARGET.removeVertex( v );
			}
		
		org.junit.Assert.assertEquals("Verifying that when all vertices are removed, edge count is 0", (Object)(0), (Object)(TARGET.numEdges()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that when all vertices are removed, no vertices remain in the graph")
	public void Test16() throws Throwable {
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for ( Vertex<RoomCoordinate> v : vertices )
                        {
                                TARGET.removeVertex( v );
                        }
		vertices = TARGET.vertices();
		int lCount = 0;
		for ( Vertex<RoomCoordinate> v : vertices )
			{
                        	lCount++;
			}
		
		org.junit.Assert.assertEquals("Verifying that when all vertices are removed, no vertices remain in the graph", (Object)(0), (Object)(lCount));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that when all edges are removed, vertex count is 16")
	public void Test17() throws Throwable {
		Iterable<Edge<Walkway>> edges = TARGET.edges();
		for ( Edge<Walkway> e : edges )
                        {
                                TARGET.removeEdge( e );
                        }
		
		org.junit.Assert.assertEquals("Verifying that when all edges are removed, vertex count is 16", (Object)(16), (Object)(TARGET.numVertices()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that when all edges are removed, no edges remain in the graph")
	public void Test18() throws Throwable {
		Iterable<Edge<Walkway>> edges = TARGET.edges();
		for ( Edge<Walkway> e : edges )
                        {
                                TARGET.removeEdge( e );
                        }
		edges = TARGET.edges();
		boolean lPassed = true;
		for ( Edge<Walkway> e : edges )
                        {
                                lPassed = false;
                        }
		
		org.junit.Assert.assertEquals("Verifying that when all edges are removed, no edges remain in the graph", (Object)(true), (Object)(lPassed));
	}

}
