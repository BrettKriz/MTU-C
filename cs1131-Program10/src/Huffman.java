

import java.io.*;
import java.util.PriorityQueue; // I cant get my queue to run fully
import java.util.Scanner;

 
abstract class Node implements Comparable<Node> {
    public final double frequency; // the frequency of this tree
    public Node(double freq) { frequency = freq; }
 
    // compares on the frequency
    @Override
    public int compareTo(Node tree) {
        return ((int)frequency - (int)tree.frequency);
    }
}
 
class Leaf extends Node {
    public final char value; // the character this leaf represents
 
    public Leaf(double freq, char val) {
        super(freq);
        value = val;
    }
}
 
class Junction extends Node {
    public final Node left, right; // subtrees
 
    public Junction(Node l, Node r) {
        
        super(l.frequency + r.frequency);
        
        this.left = l;
        this.right = r;
    }
}
 
public class Huffman {
    public static int AZ_LENGTH = 26;
    public LeafAddress[] LA = new LeafAddress[AZ_LENGTH*5];
    public int LASize = 0;
    public Junction ROOT;
    //buildTree
    
    
    /**
    * Constructs the Huffman coding tree.
    *
    * @param the symbol probabilities (weights), length 26,
    * corresponding to the symbols A--Z.
    */
    public Huffman(double[] probabilities){
        if (probabilities == null || probabilities.length < 1){
            throw new IllegalArgumentException("Probabilities missing!");
        }
        generateTree(probabilities);
    }
    // input is an array of frequencies, indexed by character code
    public Node generateTree(double[] charFreqs) {
        PriorityQueue1<Node> trees;
        trees = new PriorityQueue1<>();

        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++){
            char charAT = (char)((int)'A' + i);
            trees.offer( new Leaf(charFreqs[i], charAT) );
        }
        
        // Make sure we can play ball
        if (trees.size() < 1){
            throw new Error("Failed to generate tree! Tree size was too small!");
        }
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            Node a = trees.poll();
            Node b = trees.poll();
 
            // put into new node and re-insert into queue
            trees.offer(new Junction(a, b));
        }
        // Do some work before we go
        Node temp = trees.poll();
        ROOT = (Junction)temp;
        generateAddresses(ROOT,new StringBuffer());
        
        return ROOT;
    }
    public static String fileContents(String file){
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
    public void generateAddresses(Node tree, StringBuffer prefix) {
        if (tree == null){
            throw new NullPointerException("Tree was null!");
        }
        if (tree instanceof Leaf) {
            Leaf leaf = (Leaf)tree;

            LA[LASize++] = new LeafAddress(leaf, prefix.toString());
         } else if (tree instanceof Junction) {
            Junction node = (Junction)tree;
 
            // Left
            prefix.append('0');
            Node ltemp = node.left;
            if (ltemp != null){
                generateAddresses(ltemp, prefix);
            }
            prefix.deleteCharAt(prefix.length()-1);
 
            // Right
            prefix.append('1');
            Node rtemp = node.right;
            if (rtemp != null){
                generateAddresses(rtemp, prefix);
            }
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    
    private String getAddress(char target){

        for(LeafAddress cur : LA){
            if (cur != null && cur.leaf.value == target){
                // Found it!
                return cur.Address;
            }
        }
        // Hope not
        return null;
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
        // We can only hope the probabilities are synched
        
        File fin = new File(input);
        File fout = new File(output);
        BitInputStream bin = new BitInputStream(new BufferedInputStream(new FileInputStream(fin)));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(fout));
        
        // Now decode based on our table
        // Which is hard.
        while(true){
            char letter;
            Junction cur = ROOT;

            while(true){
                Node next;
                int bit = bin.read();
                
                // Check the kids
                if (bit == 0){
                    next = cur.left;
                }else if (bit == 1){
                    next = cur.right;
                }else if (bit == -1){ // WERE DONE HERE
                    out.close();
                    bin.close();
                    return;
                }else{
                    throw new IOException("Bad digit read! Neither 0 or 1!");
                }
                
                // Check if we have a leaf yet
                if (next instanceof Leaf){
                    letter =((Leaf)next).value;
                    break;
                }else if (next instanceof Junction){
                    cur = (Junction)next;
                }else{
                    throw new IOException("Bad child! Check address or Tree!");
                }
            }// END WHILE
            
            out.write(letter);
        }// END WHILE
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
        generateTree(probabilities(input));
        String full = fileContents(input);
        File fout = new File(output);
        BitOutputStream bout = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(fout)));

        System.out.println(output);
        // Now encode based on our table
        for(int x = 0; x < full.length(); x++){
            char cur = full.charAt(x);
            
            if (cur >= 'A' && cur <= 'Z'){
                // Encodable
                // Get the adress of that char
                String addressSTR = getAddress(cur);
                char[] address = addressSTR.toCharArray();
                System.out.print(addressSTR+" ");
                // Write value as 8 bits
                for (int z = 0; z < address.length; z++){
                    int n = (int)(address[z] - '0');
                    bout.write(n);
                }
            }
        } // END FOR
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        bout.close();
    }
    
    /**
    * Reads a plaintext format file and calculates the probabilities
    * for each character A--Z.
    *
    * @param input the input filename
    * @return the probabilities for each possible character
    */
    public static double[] probabilities(String full) throws IOException{
        if (full == null || full.isEmpty()){
            throw new IOException();
        }
        
        // I do plan to put them into upper just cause
        full = fileContents(full).toUpperCase();
        
        double[] freqs = new double[AZ_LENGTH];
        // Read each character and record the frequencies
        for (char c : full.toCharArray()){
            if (c >= 'A' && c <= 'Z'){
                freqs[c-'A']++;
            }
        }
        
        return freqs;
    }
    public static void main(String[] args) {
        // Test it
        double[] idk = new double[AZ_LENGTH];
        idk[3] = 5.5;
        idk[7] = 1.5;
        Huffman tester = new Huffman(idk);
        tester.test();
        System.out.println("DONE! Check results");
    }
    public void test(){
        String test1 = "src/test1.txt";
        String test2 = "src/test2.txt";
        String f1 = "src/test1_HUFF.txt";
        String f2 = "src/test2_HUFF.txt";
        String f3 = "src/test1_BACK.txt";
        
        try{
            encode(test2,f2);
            System.out.println(f2);
            //---------------
            encode(test1,f1);
            System.out.println(f1);
            decode(f1,f3);
            System.out.println(f3);
        }catch (IOException e){
            // :(
            e.printStackTrace();
        }
    }
    class LeafAddress{
        // Convenience Class
        public Leaf leaf;
        public String Address;
        
        public LeafAddress(Leaf leaf, String Address){
            this.leaf = leaf;
            this.Address = Address;
        }
    }
}