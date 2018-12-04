package cs2321;

import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
import net.datastructures.*;

import cs2321.AdjListGraph.InnerEdge;
import cs2321.AdjListGraph.InnerVertex;

/* CS2321 Project: The Labyrinth
 * 
 * 
 * Part A: Use the main method in this class for testing purposes.
 *         Do NOT change the setupLabyrinth function, except for
 *         uncommenting the graph implementation that you have chosen.
 *         
 * Part B: Implement the dfsPath, bfsPath, shortestPath, and
 *         totalPathDistance functions below.
 *         
 * Part C: Implement LabyrinthPanel and LabyrinthFrame
 */
public class Labyrinth
{
    public static final int WALL = 1;
    public static final String PARSE_CHARACTER = ",";

    //public static final String EXPLORED = "explored";
    //public static final String UNEXPLORED = "unexplored";
    //public static final String BACK = "back";
    public static final String LABEL = "label";
    public static final String DIST = "distance";
    public static final String SRCv = "source vertex";

    public static final Double INF = Double.POSITIVE_INFINITY;

    public enum Label{
        UNEXOPLORED,
        EXOPLORED,
        BACK,
    }

    private Graph<RoomCoordinate, Walkway> mGraph;
    private int mWidth = -1;
    private int mHeight = -1;

    public static void main( String[] aArguments )
    {
        Labyrinth lab = new Labyrinth( "SmallLabyrinth.txt" );


    }

    public Labyrinth( String aFileName )
    {
        mGraph = setupLabyrinth( aFileName );

        // TODO: Add other necessary code to constructor
    }

    /*
     * Width of the labyrinth (# of squares, not pixels)
     */
    public int getWidth()
    {
        return mWidth;
    }

    /*
     * Height of the labyrinth (# of squares, not pixels)
     */
    public int getHeight()
    {
        return mHeight;
    }

    public Graph<RoomCoordinate, Walkway> setupLabyrinth( String aFileName )
    {
        Scanner lFile = null;
        try
        {
            lFile = new Scanner( new File( aFileName ) );
            lFile.useDelimiter( ",\n" );
        }
        catch ( FileNotFoundException eException )
        {
            System.out.println( eException.getMessage() );
            eException.printStackTrace();
            System.exit( -1 );
        }

        /*
         * TODO: Project Part A: Pick your implementation and uncomment
         */
        //# Graph<RoomCoordinate, Walkway> lGraph = new EdgeListGraph<RoomCoordinate, Walkway>();
        Graph<RoomCoordinate, Walkway> lGraph = new AdjListGraph<RoomCoordinate, Walkway>();
        //# Graph<RoomCoordinate, Walkway> lGraph = new AdjMatrixGraph<RoomCoordinate, Walkway>();

        try
        {
            int lXSize = 0;
            int lYSize = 0;
            if ( lFile.hasNext() )
            {
                String[] lDimensions = lFile.nextLine().split( PARSE_CHARACTER );
                lXSize = Integer.parseInt( lDimensions[0] );
                lYSize = Integer.parseInt( lDimensions[1] );
            }

            mWidth = lXSize;
            mHeight = lYSize;

            /* Create all the room coordinates */
            Vertex<?>[][] lVertices = new Vertex<?>[lXSize][lYSize];
            for ( int lYIndex = 0; lYIndex < lYSize; lYIndex++ )
            {
                for ( int lXIndex = 0; lXIndex < lXSize; lXIndex++ )
                {
                    RoomCoordinate lNextRoomCoordinate = new RoomCoordinate(
                            lXIndex, lYIndex );
                    Vertex<RoomCoordinate> lNextRoom = lGraph
                            .insertVertex( lNextRoomCoordinate );
                    lVertices[lXIndex][lYIndex] = lNextRoom;
                }
            }

            for ( int lYIndex = 0; lYIndex < lYSize; lYIndex++ )
            {
                String[] lWalls = lFile.nextLine().split( PARSE_CHARACTER );
                for ( int lXIndex = 0; lXIndex < lXSize; lXIndex++ )
                {
                    if ( Integer.parseInt( lWalls[lXIndex] ) != WALL )
                    {
                        Vertex<RoomCoordinate> lVertex1 = ( Vertex<RoomCoordinate> )lVertices[lXIndex][lYIndex];
                        Vertex<RoomCoordinate> lVertex2 = ( Vertex<RoomCoordinate> )lVertices[lXIndex][lYIndex - 1];

                        Walkway lNewWalkway = new Walkway( lVertex1.element().toString() +
                                           lVertex2.element().toString(),
                                           Integer.parseInt( lWalls[lXIndex] ) );
                        lGraph.insertEdge( lVertex1, lVertex2, lNewWalkway );
                    }
                }
            }

            for ( int lYIndex = 0; lYIndex < lYSize; lYIndex++ )
            {
                String[] lWalls = lFile.nextLine().split( PARSE_CHARACTER );
                for ( int lXIndex = 0; lXIndex < lXSize; lXIndex++ )
                {
                    if ( Integer.parseInt( lWalls[lXIndex] ) != WALL )
                    {
                        Vertex<RoomCoordinate> lVertex1 = ( Vertex<RoomCoordinate> )lVertices[lXIndex][lYIndex];
                        Vertex<RoomCoordinate> lVertex2 = ( Vertex<RoomCoordinate> )lVertices[lXIndex - 1][lYIndex];

                        Walkway lNewWalkway = new Walkway( lVertex1.element().toString() +
                                           lVertex2.element().toString(),
                                           Integer.parseInt( lWalls[lXIndex] ) );
                        lGraph.insertEdge( lVertex1, lVertex2, lNewWalkway );
                    }
                }
            }
        }
        catch ( Exception eException )
        {
            System.out.println( eException.getMessage() );
            eException.printStackTrace();
            System.exit( -1 );
        }

        return lGraph;
    }

    /* START PROJECT PART B */

    /* Project Part B:
     * Complete the dfsPath function by implementing a Depth First Search algorithm
     * to find a path from start to end.
     * RETURN VALUES:
     *    + If there is NO path, do NOT return null.  Return an empty sequence.
     *    + If there is a path, return the sequence of edges traversed in order
     *      to get from the start to the finish locations.
     */
    @TimeComplexity("o(V+E)") @SpaceComplexity("O(1)")
    public Sequence<Edge<Walkway>> dfsPath( RoomCoordinate start, RoomCoordinate end )
    { // o(n^2+m)
        
        /* TCJ: The loop found in the overloaded function
            Can run through all V vertices and E edges only once.
        
            SCJ: o(1) nothing new is made for this method
        */
        this.clearSearchVars();

        LinkedSequence<Vertex<RoomCoordinate>> verts = (LinkedSequence)this.mGraph.vertices();
        LinkedSequence<Edge<Walkway>> s = new LinkedSequence(); // A sequence

        // Baby step the variables so casting doesn't break
        AdjListGraph<RoomCoordinate,Walkway> temp = ((AdjListGraph) (this.mGraph));
        Vertex<RoomCoordinate> v = temp.findV(start);
        Vertex<RoomCoordinate> z = temp.findV(end);

        if (z == null){
            // Thats a problem
            throw new NullPointerException("End cant be null");
        }
        if ( v.equals(z) ){
            // Found it.
            return s;
        }

        
        this.dfsPath(v, z);
        //this.DFS(v, z);
        
        return this.makePath(v, z);
    }
    
    public void dfsPath(Vertex<RoomCoordinate> v, Vertex<RoomCoordinate> z){
        // Overloaded function
        Vertex<RoomCoordinate> at = v;

        v.put(LABEL, Label.EXOPLORED);

        for (Object cur : this.getPolar(at)){
            if (cur == null){
                continue;
            }

            InnerEdge e = (InnerEdge)cur;

            if ( isUnexplored(e) ){
                Vertex<RoomCoordinate> w = this.mGraph.opposite(at, e);

                if ( isUnexplored(w) ){
                    e.put(LABEL, Label.EXOPLORED);
                    w.put(SRCv, at);
                    w.put(LABEL, Label.EXOPLORED);

                    if ( z.equals(w)  ){
                        // Found it, we dont need to go further here.
                        return;
                    }

                    this.dfsPath(w, z); // ADD BOOL & BREAK?
                    //at = w;
                    //break;
                }else{
                    e.put(LABEL, Label.BACK);
                }
            }
        }
    }
    
    public InnerEdge[] getPolar(Vertex<RoomCoordinate> around){
        AdjListGraph.InnerEdge[] ans = new AdjListGraph.InnerEdge[4];
        final int y = around.element().getY();
        final int x = around.element().getX();
        // For convenience 
        AdjListGraph<RoomCoordinate,Walkway> temp = ((AdjListGraph) (this.mGraph));
        HashMap<AdjListGraph.InnerVertex, AdjListGraph.InnerEdge> tbl = ((AdjListGraph.InnerVertex)around).getOutgoing();
        
        // Check outgoing
        int c = 0;
        for (Vertex<RoomCoordinate> cur : tbl.keySet()){
            // find polar neighbors
            if (c > 4){
                // Not very polar...
                throw new IllegalArgumentException("Non-polar vertex! # > 4");
            }
            RoomCoordinate curPos = ((RoomCoordinate)cur.element());
            
            // Crunch ints, made final for failsafe
            final int cy = curPos.getY();
            final int cx = curPos.getX();
            final int dy = y - cy; // y>cy = 1 = N
            final int dx = x - cx; // x>cx = 1 = W
            int index = -1;
            
            if ( (Math.abs(dy) >= 1 && Math.abs(dx) >= 1) 
                || (Math.abs(dy) == 0  && Math.abs(dx) == 0)
                || (Math.abs(dy) > 1 || Math.abs(dx) > 1) ){
                // Somethings wrong
                throw new IllegalArgumentException("Neighbor has strange polar coords: "+dx+","+dy);
            }
            // N E S W
            if (dx == 0){ // North or South
                if (dy == 1){
                    index = 0; // N
                }else{
                    index = 2;
                }
            }else if (dy == 0){ // West or East
                if (dx == 1){
                    index = 3; // W
                }else{
                    index = 1;
                }
            }
                
            ans[index] = (AdjListGraph.InnerEdge)temp.getEdge(around, cur);    
            c++; // 
        }

        return ans;
    }
    
    public void clearSearchVars(){
        // clean o(n)
        for (Vertex<RoomCoordinate> v : this.mGraph.vertices()){
            InnerVertex cur = (InnerVertex)v;
            cur.clear();
            cur.put(LABEL, Label.UNEXOPLORED);

        }
        for (Edge<Walkway> e : this.mGraph.edges()){
            InnerEdge cur = (InnerEdge) e;
            //if ( ((Walkway)cur.element()).getDistance() != WALL ){
                cur.put(LABEL, Label.UNEXOPLORED);
            //}else{
                // Walls, DONT GET MADE!!!! Scraped @@@
                //cur.put(LABEL, Label.EXOPLORED);
            //}
        }
    }

    public boolean isUnexplored(Map arg){
        Label flag = (Label)arg.get(LABEL);
        if (flag == null){
            arg.put(LABEL, Label.UNEXOPLORED);
        }
        return (flag == Label.UNEXOPLORED);
    }
    
    public boolean isExplored(Map arg){
        return !isUnexplored(arg);
    }
    
    public boolean isBack(Map arg){
        Label flag = (Label)arg.get(LABEL);
        if (flag == null){
            arg.put(LABEL, Label.UNEXOPLORED);
        }
        return (flag == Label.BACK);
    }
    
    public boolean isNear(Vertex<RoomCoordinate> Av, Vertex<RoomCoordinate>Bv){
        RoomCoordinate A = Av.element();
        RoomCoordinate B = Bv.element();
        int Ax = A.getX(), Ay = A.getY();
        int Bx = B.getX(), By = B.getY();
        int Dx = Math.abs(Ax-Bx);
        int Dy = Math.abs(Ay-By);
        
        // Check by compairision of value and literal value
        return (Dy < 2 && Dx <2) && (Dy != Dx);
    }
        

    public Sequence<Edge<Walkway>> makePath(Vertex<RoomCoordinate> start, Vertex<RoomCoordinate> end){ // o(n)
        AdjListGraph<RoomCoordinate,Walkway> temp = ((AdjListGraph)(this.mGraph));
        LinkedSequence<Edge<Walkway>> s = new LinkedSequence();
        LinkedSequence<Edge<Walkway>> ans = new LinkedSequence();
        InnerVertex at = ((InnerVertex) end );

        // Now start tracing
        InnerVertex cur = null;
        while( at != null ){
            cur = (InnerVertex)at.get(SRCv);
            if (cur == null){
                // Found end. is it right?
                if (start.equals(at)){
                    // It is!
                    break;
                }else{
                    // Not
                    return new LinkedSequence();
                }
            }
            
            // Track it
            Edge<Walkway> e = temp.getEdge(cur, at);
            s.addLast(e);
            // Setup for next
            at = (AdjListGraph.InnerVertex)cur;
            
        }
        // Now reverse
        while(!s.isEmpty()){
            ans.addLast( s.removeLast() );
        }

        return ans;
    }
    
    /* Project Part B:
     * Complete the bfsPath function by implementing a Breadth First Search algorithm
     * to find a path from start to end.
     * RETURN VALUES:
     *    + If there is NO path, do NOT return null.  Return an empty sequence.
     *    + If there is a path, return the sequence of edges traversed in order
     *      to get from the start to the finish locations.
     */
    @TimeComplexity("o(V+E)") @SpaceComplexity("o(V)")
    public Sequence<Edge<Walkway>> bfsPath( RoomCoordinate start, RoomCoordinate end )
    {
        /* TCJ: The overloaded method can run at most V times thru all verticies plus
            E times through all the edges only ONCE.
        
        SCJ: Level uses V extra space.
        
        */
        this.clearSearchVars();

        // Get points, create vars
        AdjListGraph<RoomCoordinate,Walkway> temp = ((AdjListGraph)(this.mGraph));       
        
        InnerVertex v = (InnerVertex)temp.findV(start);
        InnerVertex z = (InnerVertex)temp.findV(end);

        this.bfsPath(v, z); // Real code body
        // Use LABELS to trace
        return this.makePath(v, z);
    }

    private void bfsPath( Vertex<RoomCoordinate> arg,
                                                    Vertex<RoomCoordinate> goal){
        PositionList<Vertex<RoomCoordinate>> level = new NodePositionList<>();
        
        arg.put(LABEL, Label.EXOPLORED);
        level.addLast(arg);

        while(!level.isEmpty()){
            NodePositionList<Vertex<RoomCoordinate>> nextLevel = new NodePositionList<>();
            // Sift the breadth
            for (Vertex<RoomCoordinate> u2 : level){
                // Strange casting issues for AdjListGraph.InnerVertex here
                AdjListGraph.InnerVertex u = (AdjListGraph.InnerVertex)u2;

                for ( Object e2 : this.getPolar(u2) ) { // Check outgoing
                    Edge<Walkway> e = (Edge)e2;
                    if ( e2 == null || this.isExplored(e) ){
                        // Already checked, back, or null
                        continue;
                    } // Removes EXPLORED CASE

                    Vertex<RoomCoordinate> w = this.mGraph.opposite(u, e);
                    if ( this.isUnexplored(w) ){
                        w.put(LABEL, Label.EXOPLORED);
                        e.put(LABEL, Label.EXOPLORED);
                        w.put(SRCv, u);         // Connect to parent
                        
                        nextLevel.addLast(w); // v will be further considered in next pass
                        
                        if (w.equals(goal)){
                            // Done
                            return;
                        }
                    }else{
                        e.put(LABEL, Label.BACK);
                    }
                }
            } // END for LEVEL
            level = nextLevel;  // relabel 'next' level to become the current
        }
    }

    /* Project Part B:
     * Complete the shortestPath function by implementing Dijkstra's Algorithm
     * to find a path from start to end.
     * RETURN VALUES:
     *    + If there is NO path, do NOT return null.  Return an empty sequence.
     *    + If there is a path, return the sequence of edges traversed in order
     *      to get from the start to the finish locations.
     */
    @TimeComplexity("o(E Lg(V))") @SpaceComplexity("o(V+p)")
    public Sequence<Edge<Walkway>> shortestPath( RoomCoordinate start, RoomCoordinate end )
    {
        // #TODO: Complete and correct shortestPath()
        /* TCJ: The loop found in the overloaded function
            Can run up to V times, then runs Deg(V) times
            aswell.
        
            SCJ: There are M verticies in Q and P (path amount) of vertexes in
                cloud
        */
        this.clearSearchVars();
        AdjListGraph<RoomCoordinate,Walkway> ALG = (AdjListGraph)this.mGraph;
        final Vertex<RoomCoordinate> v = ALG.findV(start);
        final Vertex<RoomCoordinate> z = ALG.findV(end);

        // Get paths
        HashMap<Vertex<RoomCoordinate>, Double> vLenMap = this.Dijkstra(v, z);
        
        Double v1 = vLenMap.get(v);
        Double z1 = vLenMap.get(z);

        return this.makePath(v, z);
    }

    public HashMap<Vertex<RoomCoordinate>, Double> Dijkstra(final Vertex<RoomCoordinate> src, final Vertex<RoomCoordinate> end){
        // d.get(v) is upper bound on distance from src to v
        OrderedPQ<Double,Vertex> Q = new OrderedPQ();
        HashMap<Vertex<RoomCoordinate>,Double> cloud = new HashMap();
        HashMap<Vertex<RoomCoordinate>,Entry<Integer,Vertex<RoomCoordinate>>> pqTokens;
        AdjListGraph<RoomCoordinate, Walkway> temp = (AdjListGraph)this.mGraph;
        pqTokens = new HashMap();
        String TAG = "QEntry Distance";

        // Compile distances and add them to Q
        for ( Vertex u : this.mGraph.vertices() ){
            // add distances
            if ( src.equals(u) ){
                // Make start
                u.put(DIST, (Double)0.0); 
            }else{
                u.put(DIST, INF);
            }
            // pqTokens.put(v,pq.insert(d.get(v),v));
            QEntry var = (QEntry) Q.insert( (Double)u.get(DIST), u ); // ++++++
            u.put( TAG, var );
            pqTokens.put(u, var);
            //              Entry<K,V>
        }

        InnerVertex past = null;
        double pastLen  =0;
        // Add reachable verticies
        while ( !Q.isEmpty() ){
            // for each

            Entry<Double,Vertex> curEntry = Q.removeMin() ;// the shortest dist to v
            InnerVertex u = (InnerVertex) curEntry.getValue();
            Double key =    (Double) u.get(DIST);

            if ( past == null || 
                        (this.isNear(u, past)&& temp.getEdge(u, past) != null) ){
                u.put(SRCv, past);
            }

            u.put(LABEL,Label.EXOPLORED);
            cloud.put(u, key);
            pqTokens.remove(u);

            // Expand outward and find possible goals
            for (Object cur : u.getOutgoing().values()){ // outgoing edges!
                if (cur == null){
                    continue;
                }// For polar
                Edge<Walkway> e = (Edge)cur;
                e.put(LABEL,Label.EXOPLORED);
                
                Vertex<RoomCoordinate> w = this.mGraph.opposite(u, e);
                Double newdist = (Double)u.get(DIST) + e.element().getDistance();
                w.put(LABEL, Label.EXOPLORED);

                if ( cloud.get(w) == null ){
                    // RELAX the edge e
                    if (newdist < (Double)w.get(DIST)){ // Find faster edge
                        // Remove then re-insert
                        QEntry<Double, Vertex> at = ((QEntry) w.get(TAG));
                        Q.backing.remove(Q.backing.findElem(at));
                        at.setKey(newdist);
                        // REINSERT
                        Q.insert(at);
                        w.put(DIST, newdist);
                        w.put(SRCv, u);
                        
                    }
                }
            }
            past = u;
        }
        return cloud;
    }

    /* Project Part B:
     * Complete the totalPathDistance function, which calculates how far the given
     * path traverses.
     */
    public static double totalPathDistance( Sequence<Edge<Walkway>> path )
    {
        //# TODO: Complete totalPathDistance function
        double ans = 0.0;
        for (Edge<Walkway> cur : path){
            ans += cur.element().getDistance();
        }

        return ans;
    }

    /* END PROJECT PART B */

}
