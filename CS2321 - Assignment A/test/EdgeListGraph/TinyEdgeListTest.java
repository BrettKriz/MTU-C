package EdgeListGraph;

import org.junit.*;
import jug.*;
import cs2321.*;
import net.datastructures.*;

@jug.SuiteName("Very small Edge List")
public class TinyEdgeListTest {

	private EdgeListGraph TARGET = init();
	private EdgeListGraph T = init();

	public EdgeListGraph init() {
		return new EdgeListGraph();
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numVertices() equals 2")
	public void Test1() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )( new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" ) );
		
		org.junit.Assert.assertEquals("Verifying numVertices() equals 2", (Object)(2), (Object)(TARGET.numVertices()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying numEdges() equals 1")
	public void Test2() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		
		org.junit.Assert.assertEquals("Verifying numEdges() equals 1", (Object)(1), (Object)(TARGET.numEdges()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((0,0), (1,0)) equals true")
	public void Test3() throws Throwable {
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 0, 0 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 1, 0 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		TARGET.insertEdge( v1, v2, lWalkway );
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((0,0), (1,0)) equals true", (Object)(true), (Object)(TARGET.areAdjacent(v1, v2)));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying areAdjacent((1,0), (0,0)) equals true")
	public void Test4() throws Throwable {
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 0, 0 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 1, 0 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		TARGET.insertEdge( v1, v2, lWalkway );
		
		org.junit.Assert.assertEquals("Verifying areAdjacent((1,0), (0,0)) equals true", (Object)(true), (Object)(TARGET.areAdjacent(v2, v1)));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying endVertices({(0, 0), (1, 0)}) equals (0, 0), (1, 0)")
	public void Test5() throws Throwable {
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 0, 0 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 1, 0 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> lEdge = TARGET.insertEdge( v1, v2, lWalkway );
		Vertex[] vertices = TARGET.endVertices( lEdge );
		Vertex [] values = {v1, v2}
		;
		boolean [] checked = new boolean[values.length];
		for(Vertex v : vertices) {
				for(int j=0; j<values.length; j++) {
					if(values[j].equals(v)) {
						checked[j]=true;
					}
				}
			}
		for(int j=0; j<checked.length; j++)//;
		
		org.junit.Assert.assertEquals("Verifying endVertices({(0, 0), (1, 0)}) equals (0, 0), (1, 0)", (Object)(true), (Object)(checked[j]));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying insertVertex((1,1)), numVertices() equals 3")
	public void Test6() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		
		org.junit.Assert.assertEquals("Verifying insertVertex((1,1)), numVertices() equals 3", (Object)(3), (Object)(TARGET.numVertices()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying v = insertVertex((1,1)), v.element().toString() equals \"(1, 1)\"")
	public void Test7() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		Vertex<RoomCoordinate> v = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		
		org.junit.Assert.assertEquals("Verifying v = insertVertex((1,1)), v.element().toString() equals \"(1, 1)\"", (Object)("(1,1)"), (Object)(v.element().toString()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying insertEdge((1,1),(0,1)), numEdges() equals 2")
	public void Test8() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		
		org.junit.Assert.assertEquals("Verifying insertEdge((1,1),(0,1)), numEdges() equals 2", (Object)(2), (Object)(TARGET.numEdges()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying insertEdge(( 1, 1 ), ( 0, 1 ), (1,1)(0,1)); areAdjacent((1, 1), (0, 1)) equals true")
	public void Test9() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		
		org.junit.Assert.assertEquals("Verifying insertEdge(( 1, 1 ), ( 0, 1 ), (1,1)(0,1)); areAdjacent((1, 1), (0, 1)) equals true", (Object)(true), (Object)(TARGET.areAdjacent( v1, v2 )));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying e = insertEdge(( 1, 1 ), ( 0, 1 ), (1,1)(0,1)), e.element().getName() equals (1, 1)(0, 1)")
	public void Test10() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		
		org.junit.Assert.assertEquals("Verifying e = insertEdge(( 1, 1 ), ( 0, 1 ), (1,1)(0,1)), e.element().getName() equals (1, 1)(0, 1)", (Object)("(1,1)(0,1)"), (Object)(e.element().getName()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying incidentEdges(( 1, 1 )) size equals 1")
	public void Test11() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		Iterable<Edge<Walkway>> edges = TARGET.incidentEdges(v1);
		for(Edge edge : edges)
				size++;
		
		org.junit.Assert.assertEquals("Verifying incidentEdges(( 1, 1 )) size equals 1", (Object)(1), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying edges() size equals 1")
	public void Test12() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		int size = 0;
		Iterable<Edge<Walkway>> edges = TARGET.edges();
		for(Edge e : edges)
				size++;
		
		org.junit.Assert.assertEquals("Verifying edges() size equals 1", (Object)(1), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying vertices() size equals 2")
	public void Test13() throws Throwable {
		TARGET = ( EdgeListGraph<RoomCoordinate, Walkway> )new Labyrinth( "TinyLabyrinth.txt" ).setupLabyrinth( "TinyLabyrinth.txt" );
		int size = 0;
		Iterable<Vertex<RoomCoordinate>> vertices = TARGET.vertices();
		for(Vertex v : vertices)
				size++;
		
		org.junit.Assert.assertEquals("Verifying vertices() size equals 2", (Object)(2), (Object)(size));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that removing edge (0,0)(1,0) makes those vertices not adjacent")
	public void Test14() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		TARGET.removeEdge( e );
		
		org.junit.Assert.assertEquals("Verifying that removing edge (0,0)(1,0) makes those vertices not adjacent", (Object)(false), (Object)(TARGET.areAdjacent( v1, v2 )));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that removing edge (0,0)(1,0) returns the proper element")
	public void Test15() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		lWalkway = ( Walkway )TARGET.removeEdge( e );
		
		org.junit.Assert.assertEquals("Verifying that removing edge (0,0)(1,0) returns the proper element", (Object)("(1,1)(0,1)"), (Object)(lWalkway.getName()));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that removing vertex (0,1) returns the proper element")
	public void Test16() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		RoomCoordinate lCoordinate = ( RoomCoordinate )TARGET.removeVertex( v2 );
		
		org.junit.Assert.assertEquals("Verifying that removing vertex (0,1) returns the proper element", (Object)(0), (Object)(lCoordinate.compareTo( new RoomCoordinate( 0, 1 ) )));
	}

	@org.junit.Test(timeout=15000)
	@jug.TestName("Verifying that removing vertex (0,1) removes the incident edge")
	public void Test17() throws Throwable {
		int size = 0;
		Vertex<RoomCoordinate> v1 = TARGET.insertVertex( new RoomCoordinate( 1, 1 ) );
		Vertex<RoomCoordinate> v2 = TARGET.insertVertex( new RoomCoordinate( 0, 1 ) );
		Walkway lWalkway = new Walkway( v1.element().toString() + v2.element().toString(), 1 );
		Edge<Walkway> e = TARGET.insertEdge( v1, v2, lWalkway );
		RoomCoordinate lCoordinate = ( RoomCoordinate )TARGET.removeVertex( v2 );
		
		org.junit.Assert.assertEquals("Verifying that removing vertex (0,1) removes the incident edge", (Object)(0), (Object)(TARGET.numEdges()));
	}

}
