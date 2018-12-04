package cs2321;

import net.datastructures.*;

/**
 * @author cdbrown 
 *
 */
public class AdjMatrixGraph<V, E> implements Graph<V, E> {

	public AdjMatrixGraph() {
	    /*# If you create an Adjacency Matrix Directed Graph, remove the following
	     * exception and complete the constructor appropriately.
	     * DO NOT submit more than one working tree-based Map */
		throw new RuntimeException();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#areAdjacent(net.datastructures.Vertex, net.datastructures.Vertex)
	 */
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v)
			throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 */
	public Iterable<Edge<E>> edges() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 */
	public Vertex[] endVertices(Edge<E> e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#incidentEdges(net.datastructures.Vertex)
	 */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v)
			throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 */
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 */
	public Vertex<V> insertVertex(V o) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 */
	public int numEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 */
	public int numVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
	public E removeEdge(Edge<E> e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 */
	public V removeVertex(Vertex<V> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#replace(net.datastructures.Edge, java.lang.Object)
	 */
	public E replace(Edge<E> p, E o) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#replace(net.datastructures.Vertex, java.lang.Object)
	 */
	public V replace(Vertex<V> p, V o) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 */
	public Iterable<Vertex<V>> vertices() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
