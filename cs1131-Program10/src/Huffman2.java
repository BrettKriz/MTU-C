import java.util.Scanner;
import java.io.*;

// REMOVE ME!
import TEST.PriorityQueue1;

/**
 * Huffman Encoding
 * <p>
 * 
 * 
 * @author Brett Kriz
 */
class Huffman2 {
    public static final int AZ_LENGTH = 26;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UNKNOWN = -1;
    public static double[] PROB_TBL = null;
    
    public Junction ROOT = null;
    public Junction ROOT_L = null;
    public Junction ROOT_R = null;
    
    public List<Leaf> Leafs;
    
    protected PriorityQueue<Node> Tree;
    
    /*
        Trees, down the left = 0, down the right 1
        put the chars into a minimum priority queue
        
        (In Example)
        Join the top 2 with node with a probability of the sum of its kids
        when removing, 1st is left, 2nd is right
    
        Reinsert the node to the priority queue,
        Snowballing the results
            
        Have no prefixes (where nodes connect)
        
        If values are equal, we get a balanced tree (nodes are balanced)
    */
    
    public static void main2(String[] args){
        // Yarg
        
        // Huffman Enc0der
        //      \/ Bits
        //BitOutputStream ---------This is also you! You're programmers, devise appropriate abstractions
        //      \/ Bytes
        //FIleOutputStream
        
        /*
            Huffman Decoder
                /\
            BitInputStream ---------
                /\Bytes
            FileInputStream
        
        */
        double[] ee = {};
        try{
            ee = probabilities("src/test2.txt");
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        
        /*
        System.out.println( );
        Huffman test1 = new Huffman(ee);
        */
        
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        Huffman2 we = new Huffman2(ee);

        
    }
    
   /**
    * Constructs the Huffman coding tree.
    *
    * @param the symbol probabilities (weights), length 26,
    * corresponding to the symbols A--Z.
    */
    public Huffman2(double[] probabilities){
        if (probabilities == null || probabilities.length < 1){
            throw new Error("Probabilities missing!");
        }
        if (PROB_TBL == null){
            //Noo....
            //System.out.println("probabilities() should be ran before constructing!");
        }
        // Make the tree

        final double[] PROBS = probabilities;
        //PriorityQueue<Node> test = buildTree(PROBS);
        Junction[] setup = generateTree(PROBS);
        
        if (true){
            return;
        }
        /*
        int juncs = 0;
        int leafs = 0;
        for(Node c : setup){
            if (c instanceof Leaf){
                leafs++;
            }else if (c instanceof Junction){
                ((Junction)c).updateProbability();
                juncs++;
            }else{
                throw new Error("NOT EXPECTED TYPE!~");
            }
        }
        */
        
        sortH2L(setup);
        
        System.out.println();// debug point
        
        
        Junction[] copy = setup;
        //System.arraycopy(setup, 0, copy, 0, setup.length);
        // Now assemble the nodes
        copy = constructTree(copy);
        
        System.out.println("DONE, results");
        System.out.println(copy);
    }
     public static void printCodes(Node tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof Leaf) {
            Leaf leaf = (Leaf)tree;
 
            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.Char + "\t" + leaf.probability + "\t" + prefix);
 
        } else if (tree instanceof Junction) {
            Junction node = (Junction)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.LChild, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            printCodes(node.RChild, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    public PriorityQueue<Node> buildTree(double[] charFreqs){
        PriorityQueue<Node> trees = new PriorityQueue<>();
        
        for (int x = 0; x < charFreqs.length; x++){
            if (x == 24){
                System.out.println();
            }
            if (charFreqs[x] > 0){
                char letter = (char)(x+65);
                Leaf temp = new Leaf(charFreqs[x], letter);
                try{
                    trees.enqueue(temp);
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                    System.out.println();
                }
            }
        }
                
        if (trees.size() < 1){
            throw new Error("Major failure!");
        }
        
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            Node a = trees.dequeue();
            Node b = trees.dequeue();
            Junction temp = new Junction(a, b);
            if (a != null){
                a.setParent(temp, LEFT);
            }
            if (b != null){
                b.setParent(temp, RIGHT);
            }
            temp.updateProbability();
            // put into new node and re-insert into queue
            trees.enqueue(temp);
        }
        return trees;
    }
    
    public Junction[] constructTree(Junction[] junc){
        if (junc == null || junc.length < 1){
            throw new Error("Nodes missing!");
        }
        
        sortL2H(junc);
        
        // Find similarities and branch it
        /*
            for(int x = 0; x < nodes.length-1;x+=2){
                // SORT LOW TO HIGH
                Node n1 = nodes[x];
                Node n2 = nodes[x+1];
                Junction temp = new Junction();
                if (n1.sum != n2.sum){
                    temp.LChild = min(n1,n2);
                    temp.RChild = max(n1,n2);
                }else{
                    temp.LChild = n1;
                    temp.RChild = n2;
                }
                // STORE TEMP
                // Junction has sum to use in similar loop struct
                // start making chunks
            }
        
        */
        
        // Assemble root structure
        ROOT = new Junction();
        ROOT.setRoot();
        ROOT_L = new Junction();
        ROOT_R = new Junction();
        ROOT.setLeftChild(ROOT_L);
        ROOT.setRightChild(ROOT_R);
        
        List<Junction> refined = new List<>();
        Junction[] nodes = junc;

        
        // Use loop to build from ground up
        // The L2H sort will ensure this
        // Once we reach a refinement of about 4,
        int reps = 0;
        int x = 0;
        while(refined.size > 5 || reps == 0){
            refined = new List<>();
            

            for(x = 0; x < nodes.length-1; x += 2) {
                Junction n1 = nodes[x];
                Junction n2 = nodes[x+1];
                Junction temp = new Junction();
                
                if (n1.sum() != n2.sum()){
                    Junction Max = max(n1,n2);
                    Junction Min = min(n1,n2);
                    // Prefered slanting
                    temp.setLeftChild(Min); // These methods set parents aswell
                    temp.setRightChild(Max);
                }else{
                    // Doesnt matter, just retain order
                    temp.setLeftChild(n1);
                    temp.setRightChild(n2);
                }

                refined.add(temp);
            }
            
            // Pack the refined list and repeat
            nodes = safeGetArrayCopy_Junction(refined);
            
            
            reps++; // For debuging
        }
        //backing.add(x, e);
        
        
        return safeGetArrayCopy_Junction(refined);
    }
    private Junction max(Junction n1, Junction n2){
        if (n1 == null || n2 == null){
            throw new IllegalArgumentException("A null Junction was given!");
        }
        Junction ans = null;
        // For airtight data reasons...
        n1.updateProbability();
        n2.updateProbability();
        int res = n1.compareTo(n2); // For debuging
        if (res > 0){
            // n1 greater
            return n1;
        }else{
            return n2;
        }
    }
    private Junction min(Junction n1, Junction n2){
        if (n1 == null || n2 == null){
            throw new IllegalArgumentException("A null Junction was given!");
        }
        Junction ans = null;
        // For airtight data reasons...
        n1.updateProbability();
        n2.updateProbability();
        int res = n1.compareTo(n2); // For debuging
        if (res < 0){
            // n1 greater
            return n1;
        }else{
            return n2;
        }
    }
    public Junction[] generateTree(double[] probs){
        if (probs == null || probs.length < 1){
            throw new Error("Probs missing!");
        }
        
        List<Junction> ans = new List<>();
        Set[] tbl = getSets(probs);
        sortH2L(tbl);
        boolean[] isExcluded = new boolean[AZ_LENGTH];
        Leafs = new List<>();
        /*
        ROOT = new Junction();
        Junction parent = ROOT; // --------
        //*/
        
        // Make the tree
        // Highest near root,
        Junction junc = null;
        for(int x = 0; x+1 < tbl.length; x++ ){
            Set a = tbl[x+0];
            isExcluded[a.index] = true;
            
            for (int y = x+1; y < tbl.length; y++){
                Set cur = tbl[y];
                if (cur.prob == a.prob && !isExcluded[y]){
                    // Match, clump it
                    Leaf L = new Leaf(a);
                    Leaf R = new Leaf(cur);
                    Leafs.add(L,R);
                    junc = new Junction( L,R );
                    ans.add(junc);
                }
            }
            if (junc == null){
                // Swing and a miss
                Leaf one = new Leaf(a);
                Leafs.add(one);
                junc = new Junction( one , null );
                ans.add(junc);
            }
        }
        return safeGetArrayCopy_Junction(ans);
    }
    private static Junction[]  safeGetArrayCopy_Junction(List choices){
        Object[] a = choices.getArrayObjectCopy();
        Junction[] b = new Junction[a.length];
        int bS = 0; // bSize
        // It literally wouldn't cast properly
        // And yes, i tired plenty of methods
        for(Object c : a){
            if(c instanceof Junction){
                Junction d = ((Junction)c);
                b[bS++] = d;
            }
        }
        return b;
    }
    private static Set[] getSets(double[] array){
        // 
        if (array == null || array.length < 1){
            throw new Error("Bad array sent to getSet");
        }
        Set[] tbl = new Set[array.length];
        for (int x = 0; x < array.length; x++){
            tbl[x] = new Set(x,array[x]);
        }
        
        return tbl;
    }
    public static <T extends Comparable> void sortH2L(T[] array) {
        if (array == null || array.length < 2){
            throw new Error("sortH2L was given a bad ARRAY! "+array);
            //return;
        }
        int len = array.length;
        for (int m = len; m >= 0; m--) {
            for (int i = 0; i < len - 1; i++) {
                int k = i + 1;
                // Sort HIGH to LOW
                if ( array[i].compareTo(array[k]) < 0 ) {
                    swap(i, k, array);
                }// Also stability
            }
        }
    }
    public static <T extends Comparable> void sortL2H(T[] array) {
        if (array == null || array.length < 2){
            throw new Error("sortH2L was given a bad ARRAY! "+array);
            //return;
        }
        int len = array.length;
        for (int m = len; m >= 0; m--) {
            for (int i = 0; i < len - 1; i++) {
                int k = i + 1;
                // Sort HIGH to LOW
                if ( array[i].compareTo(array[k]) > 0 ) {
                    swap(i, k, array);
                }// Also stability
            }
        }
    }
    private static <T extends Comparable> void swap(int x, int y, T[] array) {
        T temp;
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
    public Character findChar(String huffAddress){
        // Go down the tree for char
        if (ROOT == null){
            throw new Error("The ROOT is currently unknown! Check call order!");
        }
        if (huffAddress == null || huffAddress.isEmpty()){
            // No dice.
            throw new IllegalArgumentException("findChar was given a bad huffman address!");
        }

        // Do some looping
        Character ans = null;
        int len = huffAddress.length();
        Junction cur = ROOT;
        
        for(int x = 0; x < len; x++){
            // Read in 1 or 0
            if (x != len-1  && ans != null){
                throw new IllegalArgumentException("Address resulted in an inconsistancies! CurNode:"+cur+" , AnsNode:"+ans);
            }
            
            // COMPUTE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            char turn = huffAddress.charAt(x);
            if (turn != '1' && turn != '0'){
                // That shouldnt happen
                throw new IllegalArgumentException("Address @ findChar contained unexpected info!");
            }
            
            Node arg = null;
            if(turn == '1'){
                // Go right
                arg = cur.RChild;
            }else if(turn == '0'){
                // Go left
                arg = cur.LChild;
            }
            
            if (arg instanceof Junction){
                // keep it moving
                cur = ((Junction)arg);
            }else if (arg instanceof Leaf && x == len-1){
                // Find the char now
                ans = ((Leaf)arg).Char;
                //break;
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        }
        
        if (ans == null){
            throw new Error("Char not found!");
        }
        
        return ans;
    }
    public String findAddress(char arg){
        String ans = "";
        if (ROOT == null || Leafs == null){
            throw new Error("Tree has not been established! Check code for call order!");
        }
        if (arg >= 'A' && arg <= 'Z'){
            Leaf target = null;
            // Loop thru structure
            for(Leaf cur : Leafs){
                if (cur.Char.charValue() == arg){
                    target = cur;
                    break;
                }
            }
            
            if (target == null){
                throw new Error(arg+" was not found! Check Leafs[] or the arg as stated!");
            }
            
            Junction branch = target.getParent();
            ans = "" + target.getSide();
            while(branch != null && !branch.isRoot()){
                // Start going up to find the parent
                // ALSO keep track of address, add it in backward
                int turn = branch.getSide();
                ans = turn + ans;
                branch = branch.getParent();
            }
            
            
        }else{
            throw new Error(arg+" is not in the correct char range!");
        }
        
        
        return ans;
    }
    /**
    * Encodes the character stream given by the input filename
    * using this Huffman code, outputting the resulting bit stream
    * to the output filename.
    *
    * @throws IOException
    * @param input the input filename; the file is guaranteed to
    * be a character stream
    * @param output the output filename; the generated data should
    * be a bit stream
    */
    public void encode(String input , String output) throws IOException{
        String full = fileContents(input);
        //char[] full2 = full.toCharArray();
        double[] probs = probabilities(input);
        Huffman2 encoder = new Huffman2(probs);
        
        // Now encode based on our table
        for(int x = 0; x < full.length(); x++){
            char cur = full.charAt(x);
            if (cur >= 'A' && cur <= 'Z'){
                // Encodable
                // Get the adress of that char
                String address = findAddress(cur);
                
                
            }
            
        }
        
    }
    /**
    * Decodes the bit stream given by the input filename using
    * this Huffman code, outputting the resulting character stream
    * to the output filename.
    *
    * @throws IOException
    * @param input the input filename; the file is guaranteed to
    * be a bit stream
    * @param output the output filename; the generated data should
    * be a character stream
    */
    public void decode(String input , String output) throws IOException{
        String full = fileContents(input);
        
        
        // Now encode based on our table
        for(int x = 0; x < full.length(); x++){
            char cur = full.charAt(x);
            if (cur >= 'A' && cur <= 'Z'){
                // Encodable
                byte curB = (byte)cur;
                
                
            }
            
        }
        
    }
    public String fileContents(String file){
        File temp = new File(file);
        Scanner in = null;
        String full = "";
        
        try{
            in = new Scanner(temp);
        }catch (FileNotFoundException e){
            // 
        }
        
        while(in.hasNext()){
            full += in.nextLine() + "\n";
        }
        
        return full;
    }
    
    /**
    * Reads a plaintext format file and calculates the probabilities
    * for each character A--Z. 
    * @param input the input filename
    * @return the probabilities for each possible character
    */
    public static double[] probabilities(String input) throws IOException{
        File target = new File(input);
        if (!target.exists()){
            throw new IOException("404 FIle not found");
        }
        
        double[] probs = new double[AZ_LENGTH];
        Scanner in = new Scanner(target);
        String full = "";
        while(in.hasNext()){
            full += in.nextLine();
        }
        // Get it tight and right
        full.toUpperCase().trim();
        
        // Look through it
        for(int z = 0; z < full.length(); z++){
            char cur = full.charAt(z);
            // cur - 65
            if (cur >= 'A' && cur <= 'Z'){
                probs[cur-65]++;
            }
        }
        
        // Get it fresh
        PROB_TBL = probs;
        return probs;
    }
    
    abstract class Node implements Comparable<Node>{
        public double probability = 0.0;
        private Junction Parent = null;
        private int Side = -1; // With respect to parent
        public abstract double sum();
        @Override
        public int compareTo(Node o1){
            if (o1 == null || !(o1 instanceof Node) ){
                throw new IllegalArgumentException("Can't compare non-node object: "+o1);
            }
            
            Node other = (Node)o1;

            // Compare by sum
            double ours     = this.sum();
            double theirs   = other.sum();
            
            if (ours == theirs){
                return 0;
            }else if (ours > theirs){
                return 1; // THIS is greater
            }else{
                return -1; // THIS is lesser
            }
        }
        public void setSide(int side){
            if (side < -1 || side > 1){
                throw new IllegalArgumentException("Bad side #!");
            }
            this.Side = side;
        }
        public int getSide(){
            return this.Side;
        }
        public Junction getParent(){
            return this.Parent;
        }
        public void setParent(Junction parent, int side){
            setSide(side);
            this.Parent = parent;

        }
    }
    class Leaf extends Node{
        public Character Char = null;
        
        public Leaf(){}
        public Leaf(double prob, char Char){
            this.Char = Char;
            this.probability = prob;
        }
        public Leaf(Set set){
            this.Char = set.getChar();
            this.probability = set.prob;
        }
        @Override
        public double sum(){
            // Return sum
            return this.probability;
        }
        public int charToIndex(){
            return this.Char - 65;
        }
        @Override
        public String toString(){
            String ans = "" + Char + ":" + probability;
            return ans;
        }


        
    }
    class Junction extends Node{
        public Node LChild = null;
        public Node RChild = null;

        
        public Junction(){
            super();
        }
        
        public Junction(Node L, Node R){
            setChildren(L,R);
        }

        //private char findChar(String HA, )
        public void setLeftChild(Node L){
            this.LChild = L;
            L.setSide(LEFT);
            L.Parent = this;
        }
        public void setRightChild(Node R){
            this.RChild = R;
            R.setSide(RIGHT);
            R.Parent = this;
        }
        public void setRoot(){
            ROOT = this;
        }
        public void setRoot(Junction newRoot){
            ROOT = newRoot;
        }
        public boolean isRoot(){
            // is THIS root?
            return (this == ROOT);
        }
        public void setChildren(Node L, Node R){
            this.LChild = L;
            this.RChild = R;
            
            if (L != null){
                L.setSide(LEFT);
            }
            if (R != null){
                R.setSide(RIGHT);
            }
            updateProbability();
        }
        public double getLeafSum(){
            // Sum it up
            // Only a direct sum
            double sum = 0.0;
            if (LChild instanceof Leaf){
                sum += ((Leaf)LChild).probability;
            }
            if (RChild instanceof Leaf){
                sum += ((Leaf)RChild).probability;
            }
            return sum;
        }
        public void updateProbability(){
            // Convenience function
            this.probability = getLeafSum();
        }
        @Override
        public double sum(){
            // Different from getLeafSum()
            double ans = 0.0;
            
            if (LChild != null){
                ans += LChild.sum();
            }
            if (RChild != null){
                ans += RChild.sum();
            }
            
            return ans;
        }
        public int hasNode(Node arg){
            // 0 = LC, 1 = RC, -1 = false
            if (arg == null){
                throw new IllegalArgumentException("hasNode does not take a null arg!");
            }
            
            int ans = UNKNOWN;
            
            if (arg.equals(LChild)){
                ans = LEFT;
            }else if(arg.equals(RChild)){
                ans = RIGHT;
            }

            return ans;
        }
        @Override
        public String toString(){
            // For debuging
            updateProbability();
            String ans = "Junc["+ this.probability +"]("+LChild+" , "+RChild+")";
            return ans;
        }

    }
    static class Set implements Comparable{
        // DATA SET
        public int index;
        public double prob;
        
        public Set(){
            
        }
        public Set(int index, double prob){
            this.index = index;
            this.prob = prob;
            
        }
        public char getChar(){
            char ans = (char)(((int)this.index)+65);
            return ans;
        }
        @Override
        public String toString(){
            String ans = getChar()+"(";
            ans += prob + ")";
            return ans;
        }
        @Override
        public int compareTo(Object o1){
            Set other = (Set)o1;
            if (this.prob == other.prob){
                return 0;
            }else if (this.prob > other.prob){
                return 1;
            }else{
                return -1;
            }
        }
    }
} // EOF
