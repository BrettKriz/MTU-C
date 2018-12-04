package cs2321;

import net.datastructures.*;


/**
 * INNER EDGE
 * @author Brett Kriz
 * @author The Book
 */
private class InnerEdge<E> extends HashMap<String, Boolean> implements Edge<E> {
    private E element;
    private Position<Edge<E>> pos;
    private InnerVertex<V>[] endpoints;
    
    public InnerEdge(Vertex<V> u, Vertex<V> v, E elem){
        this.element = elem;
        endpoints = (InnerVertex<V>[]) new Vertex[]{u,v};
        
    }
    public E getElement(){
        return element;
    }
    public Vertex<V>[] getEndpoints(){
        return endpoints;
    }
    public void setPosition(Position<Edge<E>> p){
        this.pos = p;
    }
    public Position<Edge<E>> getPosition(){
        return pos;
    }

    @Override
    public E element() {
        return this.getElement();
    }
    
}
