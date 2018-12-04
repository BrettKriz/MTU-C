package cs2321;

import net.datastructures.*;


/**
 * Adjacency List Graph
 * <p>
 * Optimized with HashMaps!
 * 
 * @author cdbrown
 * @author Brett Kriz
 */
@SpaceComplexity("o(n+m)")
public class AdjListGraph<V, E> implements Graph<V, E> {
    /*
    SCJ: The graph needs to create 2 extra blocks of memory
        with respective sizes n and m for vertices and edges
    */
        
    private boolean isDirected = false;
    private LinkedSequence<Vertex<V>> vertices = new LinkedSequence<>();
    private LinkedSequence<Edge<E>> edges = new LinkedSequence<>();


    public AdjListGraph() {
        /*# If you create an Adjacency List Directed Graph, remove the following
         * exception and complete the constructor appropriately.
         * DO NOT submit more than one working tree-based Map */

    }
    public AdjListGraph(boolean directed){
        this.isDirected = directed;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#areAdjacent(net.datastructures.Vertex, net.datastructures.Vertex)
     */
    @TimeComplexity("o( min(deg(v),deg(w)) )") @TimeComplexityExpected("o(1)")
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v)
                                           throws InvalidPositionException {
        /*
        TCJ: get has a cost of o(min(deg(u),deg(u))),
            HOWEVER because of optimization it is expected to run
            at o(1)
            (getEdge TCJ)
        */
        Object flag = this.getEdge(u, v); // o(1) expected
        return (flag != null);
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#edges()
     */
    @TimeComplexity("o(1)")
    public Iterable<Edge<E>> edges() {
        /*
        TCJ: edges is know and stored, so constant time
        */
        return edges;
    }

    public int outDegree(Vertex<V> v){
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v){
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    public int inDegree(Vertex<V> v){
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    public Iterable<Edge<E>> incomingEdges(Vertex<V> v){
        InnerVertex<V> origin = validate(v);
        return origin.getIncoming().values();
    }

    @TimeComplexity("o(min(deg(u),deg(u)))") @TimeComplexityExpected("o(1)")
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v){
        /*
        TCJ: get has a cost of o(min(deg(u),deg(u))),
            HOWEVER because of optimization it is expected to run
            at o(1)
        */
        // Edge from u to v
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v); // o(1) expected
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
     */
    @TimeComplexity("o(1)")
    public Vertex[] endVertices(Edge<E> e) throws InvalidPositionException {
        /*
        TCJ: The size of the end verticies is know and stored
            so constant time
        */
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#incidentEdges(net.datastructures.Vertex)
     */
    @TimeComplexity("o(deg(v))")
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v)
                                               throws InvalidPositionException {
        /*
        TCJ: The code will run at o(deg(v)) because out.values() has
            to create an iterable set of Edges only from a list of size
            deg(v)
        */
        InnerVertex<V> origin = validate(v);
        HashMap<Vertex<V>,Edge<E>> out = origin.getOutgoing(); // o(1)

        return out.values(); // o(deg(v))
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)")
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
                                               throws InvalidPositionException {
        /*
        TCJ: The code below has an amortized cost of o(1).
            Put isnt expected to slow the code
        */
        
        if (getEdge(u,v) == null){
            InnerEdge<E> e = new InnerEdge<>(u,v,o);
            edges.addLast(e); // o(1) amortized
            e.setPosition(edges.last());
            // Check the vertexs
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            
            // The code below supportes directed option
            origin.getOutgoing().put(v,e); // o(n) but o(1) amortized
            dest.getIncoming().put(u,e);
            return e;
        }else{
            throw new IllegalArgumentException("Edge from u to v exists"); // @@@
        }
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#insertVertex(java.lang.Object)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)")
    public Vertex<V> insertVertex(V o) {
        /*
        TCJ: The code below has an amortized cost of o(1),
        */
        
        InnerVertex<V> v = new InnerVertex<V>(o, this.isDirected);
        vertices.addLast(v); // o(1) Amortized
        v.setPosition( vertices.last() );
        return v;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#numEdges()
     */
    public int numEdges() {
        return this.edges.size();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#numVertices()
     */
    public int numVertices() {
        return this.vertices.size();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
                                            throws InvalidPositionException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();

        if (endpoints[0].equals(v)){
            return endpoints[1];
        }else if (endpoints[1].equals(v)){
            return endpoints[0];
        }else{
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)") @TimeComplexityExpected("o(1)")
    public E removeEdge(Edge<E> e) throws InvalidPositionException { // o(1)
        InnerEdge<E> edge = validate(e);
        
        /*
        TCJ: The code below is expected to run at o(1),
                with an amortized cost of o(1)
        */

        // Nab the 2 vertexes
        InnerVertex<V> A = edge.endpoints[0];
        InnerVertex<V> B = edge.endpoints[1];

        if (A != null){
            A.incoming.remove(B); // o(1) amortized
        }
        if (B != null){
            B.incoming.remove(A); // o(1) amortized
        }

        return edges.remove(edge.pos).element(); // o(1) amortized
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
     */
    @TimeComplexity("o(deg(v))")
    public V removeVertex(Vertex<V> v) throws InvalidPositionException {
            InnerVertex<V> vert = validate(v);

            /*
            TCJ: The loop below will run for the number of degrees v has,
                therefore it runs at o(deg(v))
            */
            
            for (Edge<E> cur : this.incidentEdges(v)){ // o(deg(v))
                this.removeEdge(cur);
            }

            return this.vertices.remove(vert.pos).element(); // o(1) amortized
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#replace(net.datastructures.Edge, java.lang.Object)
     */
    public E replace(Edge<E> p, E o) throws InvalidPositionException {
        InnerEdge<E> arg = validate(p);
        
        for (Edge<E> cur : this.edges){
            if (p.equals(cur)){
                InnerEdge<E> target = validate(cur);
                return target.setElement(o);
            }
        }
        throw new InvalidPositionException("P was not found!");
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#replace(net.datastructures.Vertex, java.lang.Object)
     */
    public V replace(Vertex<V> p, V o) throws InvalidPositionException {
        InnerVertex<V> arg = validate(p);
        
        for (Vertex<V> cur : this.vertices){
            if (p.equals(cur)){
                InnerVertex<V> target = validate(cur);
                return target.setElement(o);
            }
        }
        throw new InvalidPositionException("P was not found!");
    }

    public InnerVertex<V> findV(V o) throws InvalidPositionException,IllegalArgumentException {
        // o(v)
        DefaultComparator vs = new DefaultComparator();
        if (o == null){
            throw new IllegalArgumentException("NULL ARG!");
        }

        for (Vertex<V> cur : this.vertices){
            int flag = ((Comparable<V>)cur.element()).compareTo(o);
            if ( flag == 0 ){
                return (InnerVertex)cur;
            }
        }
        
        throw new InvalidPositionException("P was not found!");
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.Graph#vertices()
     */
    @TimeComplexity("o(1)")
    public Iterable<Vertex<V>> vertices() {
        // TCJ vertices is known and stored, so constant time
        return vertices;
    }

    public InnerEdge<E> validate(Edge<E> e) throws InvalidPositionException{
        if (e instanceof InnerEdge){
            return (InnerEdge<E>) e;
        }
        throw new InvalidPositionException("Bad Edge Type! "+e.toString());
    }
    public InnerVertex<V> validate(Vertex<V> v) throws InvalidPositionException{
        if (v instanceof InnerVertex){
            return (InnerVertex<V>) v;
        }
        throw new InvalidPositionException("Bad Edge Type! "+v.toString());
    }
    
    @SpaceComplexity("o(1)")
    class InnerEdge<E> extends HashMap<Object,Object> implements Edge<E> {
        private E element;
        private Position<Edge<E>> pos;
        private InnerVertex<V>[] endpoints;

        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem){
            this.element = elem;
            //this.decore = new HashMap<>();
            endpoints = (InnerVertex<V>[]) new InnerVertex[]{(InnerVertex<V>)u,(InnerVertex<V>)v};

        }
        
        public E getElement(){
            return element;
        }
        
        public E setElement(E e){
            E temp = this.element;
            this.element = e;
            return temp;
        }
        
        @TimeComplexity("o(1)")
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
        
        public String toString(){
            String ans = "-<";
                    
            ans += element.toString()+" "+this.buckets.toString();
            ans += ">-";
            return ans;
        }
    }
    
    @SpaceComplexity("o(n)")
    class InnerVertex<V> extends HashMap<Object,Object> implements Vertex<V> {
        public static final int DEFAULT_SIZE = 640;
        private V element;
        private Position<Vertex<V>> pos;
        protected HashMap<Vertex<V>, Edge<E>> outgoing, incoming; // Only 1 used

        public InnerVertex(V elem){
            this(elem,false);
        }
        
        public InnerVertex(V elem, boolean isDirected){
            super();
            this.element = elem;
            outgoing = new HashMap<Vertex<V>, Edge<E>>(DEFAULT_SIZE);
            

            if (isDirected){
                incoming = new HashMap<Vertex<V>, Edge<E>>(DEFAULT_SIZE);
            }else{
                incoming = outgoing; // If undirected alias outgoing map
            }
        }

        @Override
        public V element() {
            return this.element;
        }
        
        public V setElement(V v){
            V temp = this.element;
            this.element = v;
            return temp;
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
        
        public Edge<E> addOutgoing(InnerVertex<V> v, InnerEdge<E> e){
            outgoing.put(v, e);
            return e;
        }
        
        public Edge<E> addIncoming(InnerVertex<V> v, InnerEdge<E> e){
            incoming.put(v, e);
            return e;
        }
        public String toString(){
            String ans = "|";
                    
            ans += element.toString();//  +" & "+super.toString();
            ans += "|";
            return ans;
        }
    }
}
