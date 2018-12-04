package cs2321;

import net.datastructures.*;

/**
 * Inner Vertex
 * @author Brett Kriz
 * @author The Book
 * @param <V>
 */
private class InnerVertex<V> extends HashMap<Comparable, Comparable> implements Vertex<V> {
    public final boolean isDirected;
    private V element;
    private Position<Vertex<V>> pos;
    private HashMap<Vertex<V>, Edge<E>> outgoing, incoming;
    
    public InnerVertex(V elem){
        this(elem,false);
    }
    public InnerVertex(V elem, boolean isDirected){
        this.element = elem;
        this.outgoing = new HashMap<>();
        this.isDirected = isDirected;
        
        if (isDirected){
            incoming = new HashMap<>();
        }else{
            incoming = outgoing; // If undirected alias outgoing map
        }
    }

    @Override
    public V element() {
        return this.element;
    }

    public void setPosition(Position<Vertex<V>> p){
        this.pos = p;
    }
    
    public Position<Vertex<V>> getPosition(){
        return this.pos;
    }
    
    public HashMap<Vertex<V>, Edge<E>> getOutgoing() {
        return outgoing;
    }
    
    public HashMap<Vertex<V>, Edge<E>> getIncoming(){
        return incoming;
    }

}
