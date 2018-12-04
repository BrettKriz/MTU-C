import java.util.Scanner;
import java.io.*;

/**
 * Program 8 - Decision Tree
 * <p>
 * Where do you wanna go eat?  <p>
 * Why not decide with a tree structure?
 * 
 * @author Brett Kriz
 */
public class DecisionTree { // For specifically eating out
    public static final String DEC = "?";
    public static final String LEAF = "!";
    public static int atLine = 0;
    
    private static ChoiceNode root;
    private static String[] FULL_SCRIPT = {};
    
    enum Patrons    { NONE , SOME , FULL };
    enum WaitTime   { EXCESS , LONG , SHORT , NONE };
    enum Affirm     { NO , YES };

   /**
    * Constructs a decision tree for its specification.
    *
    * @param filename a decision tree specification filename
    * @return a decision tree
    */
    public static DecisionTree      construct(String filename){
        // Compile file into Tree
        File test1 = new File( filename );
        Scanner in = new Scanner( "Not a valid input" );
        List<String> full = new List<>();
        DecisionTree DT = new DecisionTree();
        
        try {
            in = new Scanner( test1 );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        // Fill full and fullSTR
        while(in.hasNextLine()){
            full.add( in.nextLine().trim() );
        }
        out(full);
        //System.arraycopy(full, 0, FULL_SCRIPT, 0, full.size());
        FULL_SCRIPT = safeGetArrayCopySTR(full);

        // Needs to be a way to know when all decisions are met
        // Since whitespace cant be relied on...
        Node[] result = startGenerate(); // Stored for Debug & Anchoring
        return DT;
    }
    
    /**
    * Evaluates the decision to be made given the datapoint given.
    *
    * @param datapoint a set of enumerated values
    * @return the boolean decision given by the decision tree
    */
    public boolean evaluate(Enum <?>[] datapoint){
        if (datapoint == null || datapoint.length <= 0){
            return false; // Not even valid
        }

        // Lets get this show on the road then...
        // We need to find the end leaf
        // From root
        ChoiceNode cur = root;
        int steps = 0; // IE Steps taken through the datapoint "path"
        int x;
        
        // For each level, check enums
        for(x = 0; x < cur.choices.length; x++){ // EnumResultSet ERS : cur.choices
            if (steps == 2){
                out(); // breakpoint for debug
            }
            if (steps >= datapoint.length){
                // That... shouldn't happen.
                throw new Error("Datapoint wasn't long enough to reach a conclusion @ Evaluate!");
            }
            
            //     Yes, its like this for debuging
            EnumResultSet ERS = cur.choices[x];
            Enum curEV = ERS.enumVal;
            Enum path = datapoint[steps];
            boolean b1 = curEV.equals(path);
            //         and YES, because NONE occured in 2 enums
            boolean b2 = curEV.name().equals(path.name());
            
            if ( b1 || b2 ){
                // Expand or quit
                Node res = ERS.result;

                if (res instanceof ChoiceNode){
                    ChoiceNode res2 = (ChoiceNode)res; // For Debug
                    // Get the next Choice Node
                    cur = res2;
                    steps++;
                    x = -1; // Because of reguardless incrementation
                }else{
                    // Leaf, done!
                    return res.flag;
                }
            }else if (steps == 100){
                // Something is wrong?
                out("steps has become VERY large @ EVALUATE!\nInvestigate for possible problems!");
            } // END IF
            
        }
        return false; // I guess, No.
    } // END METHOD
    // ~~~~~ HELPER FUNCTIONS BELOW ~~~~~ //
    private static Node[]           startGenerate(){
        Node[] ans = generateNodeTbl();
        // NOW, to construct node relationships
        ans = constructTree(ans);
        // Results stored for debuging and ease
        return ans;
    }
    private static Node[]           constructTree(Node[] nodes){
        if (nodes == null || nodes.length < 1){
            // No dice.
            throw new Error("@constructTree was given imappropriate nodes!");
        }
        Stack<ChoiceNode> stack = new Stack<>();
        atLine = 0;
        
        for ( Node cur : nodes ){
            // Use a stack to hook up nodes
            ChoiceNode top = stack.safeTop();
            
            if (top == null && atLine != 0){
                throw new Error("Stack Underflow @ " + atLine);
            }else if ( atLine != 0 && !hasMissingResults(top) ){
                
                do{ // Drop all full items on the stack
                    stack.pop();
                }while(!hasMissingResults(stack.top()));
                // Make sure we dont underflow
                if (stack.safeTop() != null){
                    top = stack.safeTop();
                }else{
                    throw new Error("Stack Underflow after pop @ " + atLine);
                }
            }else if (top == null && atLine == 0){
                // We're at the start, so push the root, and continue
                stack.push(root);
                atLine++;
                continue;
            } // END IF BLOCK

            // Main computation here
            if (cur instanceof ChoiceNode){
                ChoiceNode cur2 = (ChoiceNode)cur;
                
                // We have to also add choice nodes to current nodes
                for(int pos = 0; pos < top.choices.length; pos++){
                    if (top.choices[pos].result == null && cur2.choices.length > 0){
                        // VALID blank result: Set Parent, Add and Push
                        cur2.parent = top;
                        top.choices[pos].result = cur2;
                        stack.push(cur2);
                        break;
                    }
                }
            }else{ // LEAFS
                for(int pos = 0; pos < top.choices.length; pos++){
                    if (top.choices[pos].result == null){
                        // VALID blank result: Add only
                        top.choices[pos].result = cur;
                        break;
                    }
                }
            } // END IF BLOCK
            
            if (atLine != 0 && cur instanceof ChoiceNode ){
                ChoiceNode tester = (ChoiceNode)cur;
                if (tester.parent == null){
                    // HOW ABOUT NO!!!!
                    throw new Error("IMPOSSIBLE PARENTLESS CHILD!! @ " + atLine + "\tSee: "+tester);
                }
            }
            atLine++;
        } // END FOR
        return nodes;
    }
    private static Node[]           generateNodeTbl(){
        int size = 0;
        Node[] ans = new Node[FULL_SCRIPT.length];
        
        for(atLine = 0; atLine < FULL_SCRIPT.length; atLine++){
            // Tokenize and store low data Nodes
            // We'll worry about connecting them later
            String cur = FULL_SCRIPT[atLine].trim();
            int i1 = cur.indexOf(DEC);
            int i2 = cur.indexOf(LEAF);
            
            if (!cur.isEmpty()){
                // Figure the node type
                Node curNode;
                
                if (i1 != -1 && i2 != -1){
                    // NO!
                    throw new Error("Boolean abnormality, BOTH are true @ "+ (atLine) +": "+ cur);
                }else if (i1 > -1){ // Is ChoiceNode
                    
                    ChoiceNode result = generateChoiceNode(cur); // for debug
                    ans[size++] = result;
                    if (size == 1){
                        root = result;
                    }
                }else if (i2 > -1){ // is (boolean)Node
                    
                    ans[size++] = generateNode(cur);
                }else{
                    throw new Error("Line abnormality @ "+ (atLine) +": "+ cur);
                }
            } // END IF
        } // END FOR
        return ans;
    }
    private static Node             generateNode(String cur){
        return generateNode(cur, cur.indexOf(LEAF));
    }
    // "exporting non-public type through public API" Strange warning...
    public static Node              generateNode(String cur, int i2){
                // LEAF NODE !
                String cur2 = cur.substring( i2+1 ).trim(); // Reduce cur
                Scanner split = new Scanner( cur2 ); 
                String arg = "";
                boolean flag = false;
                
                if (split.hasNext()){ // Make sure we have a target
                    // Just incase there is extra peices on the line
                    arg = split.next().toLowerCase();
                }
                // Split to read node
                
                if ("true".equals(arg)){
                    flag = true;
                }
                
                return new Node(flag);
    }
    public static ChoiceNode        generateChoiceNode(String cur){
        return generateChoiceNode(cur, cur.indexOf(DEC));
    }
    public static ChoiceNode        generateChoiceNode(String cur, int i1){
                String cur2 = cur.substring( i1+1 ).trim(); // Reduce cur
                Scanner split = new Scanner( cur2 );
                Integer i = -1;
                ChoiceNode curN;

                // NOW, lets hope for an INT
                if (split.hasNextInt()){
                    i = split.nextInt();
                }else{
                    throw new Error("INDEX NOT FOUND! See String:>:"+cur);
                }

                curN = new ChoiceNode(i, null);
                List<EnumResultSet> choices = new List<>();
                
                // Add many EnumResultSets
                for(int enumCount = 0; split.hasNext(); enumCount++ ){
                    // For each ENUM, grab branches or leafs
                    String arg = split.next().toUpperCase().trim();
                    EnumResultSet ERSet = null; //new EnumResultSet();
                    Enum val = null;
                    
                    // Check for enums, all of them
                    try{
                       val = Affirm.valueOf(arg);
                       
                       ERSet = new EnumResultSet(val, null);
                       choices.add(ERSet);
                       
                       continue; // No need to keep going
                    }catch (IllegalArgumentException e){}

                    
                    try{
                        val = WaitTime.valueOf(arg);
                        ERSet = new EnumResultSet(val, null);
                        choices.add(ERSet);
                        
                        continue; // No need to keep going
                    }catch (IllegalArgumentException e){}
                    
                    try{
                        val = Patrons.valueOf(arg);
                        ERSet = new EnumResultSet(val, null);
                        choices.add(ERSet);
                        
                        continue; // No need to keep going
                    }catch (IllegalArgumentException e){}
                    
                    throw new Error("ENUM NOT FOUND! Enum argument: " + arg);
                } // END ENUM CYCLE

                EnumResultSet[] ERS1 = safeGetArrayCopy(choices);
                curN.setEnumResultSet( ERS1 ); // Entering it is vital
                
                // Now all we need to do is connect the nodes
                return curN;
    }
    private static boolean          hasMissingResults(ChoiceNode arg){
        // Convenience function
        // Its important to note, it was writen in this way for debuging 
        // However its only remaining usage is a negation; Its form will not be changed
        if (arg == null){
            throw new Error("hasMissingResults was given a null arg!");
        }else if (arg.choices.length < 1){
            throw new Error("hasMissingResults was given an arg without enough choices! See: " + arg.toString());
        }
        
        for(EnumResultSet cur : arg.choices){
            if (cur.result == null){
                return true;
            }
        }
        return false;
    }
    private static EnumResultSet[]  safeGetArrayCopy(List choices){
        Object[] a = choices.getArrayObjectCopy();
        EnumResultSet[] b = new EnumResultSet[a.length];
        int bS = 0; // bSize
        // It literally wouldn't cast properly
        // And yes, i tired plenty of methods
        for(Object c : a){
            if(c instanceof EnumResultSet){
                EnumResultSet d = ((EnumResultSet)c);
                b[bS++] = d;
            }
        }
        return b;
    }
    private static String[]         safeGetArrayCopySTR(List choices){
        Object[] a = choices.getArrayObjectCopy();
        String[] b = new String[a.length];
        int bS = 0; // bSize
        // It literally wouldn't cast properly
        // And yes, i tired plenty of methods
        for(Object c : a){
            if(c instanceof String){
                String d = ((String)c);
                b[bS++] = d;
            }
        }
        return b;
    }
    // ~~~~~ DEBUG FUNCTIONS BELOW ~~~~~ //
    public static void out(Object... args){} // Nullified but not removed
    public static void main(String[] args){
        //enum Patrons { NONE , SOME , FULL };
        //enum WaitTime { EXCESS , LONG , SHORT , NONE };
        //enum Affirm { NO , YES };
        String srcPath = ""; // for Debuging file paths
        
        DecisionTree decision = DecisionTree.construct( srcPath + "restaurant.dt" );
        Enum<?>[] a = {
            Patrons.FULL , // patrons
            WaitTime.LONG , // wait time
            Affirm.YES , // alternate
            Affirm.NO , // reservation
            Affirm.YES , // bar
            Affirm.YES , // week night
            Affirm.NO , // hungry
            Affirm.YES // raining
        };
        Enum<?>[] b = {
            Patrons.FULL , // patrons
            WaitTime.LONG , // wait time
            Affirm.NO , // alternate
            Affirm.NO , // reservation
            Affirm.YES , // bar
            Affirm.YES // week night
        };
        boolean flag = decision.evaluate(a);
        // TRUE
        out("FLAG: "+flag);
    } 
} // END DT CLASS

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
//          NODE CLASSES
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
class Node{
    public Boolean flag = null;

    public Node(boolean flag){
        this.flag = flag;
    }
    public Node(){}
}

class ChoiceNode extends Node{
    public final int index; // Serial ID
    public EnumResultSet[] choices;
    public ChoiceNode parent = null;
    public int size = 0;

    public ChoiceNode(int index, ChoiceNode parent, EnumResultSet[] choices  ){
        this.index = setIndex(index);
        this.choices = choices;
        this.parent = parent;
        countSet();
    }
    public ChoiceNode(int index, ChoiceNode parent){
        this.index = setIndex(index);
        this.parent = parent;
    }
    public ChoiceNode(int index){
        this.index = setIndex(index);
    }
    
    private int setIndex(int index){
        // Convenience checker
        if (index < 0){
            throw new Error("Bad Index! Less than 0! " + index);
        }
        return index;
    }
    public EnumResultSet[] getSet(){
        return choices;
    }
    private void countSet(){
        // Convenience size checker
        this.size = 0;
        for (int x = 0; x < this.choices.length; x++){
            if (this.choices[x] != null){
                this.size++;
            }
        }
    }
    public void setEnumResultSet(EnumResultSet[] choices){
        this.choices = choices;
        countSet();
    }
    public void setEnumResultSetAt(int i, EnumResultSet choice){
        if (choice == null){
            throw new Error("Null choice @ setEnumResultSetAt!");
        }
        if (i < 0 || i >= this.choices.length){
            throw new Error("Seting failure: Bad index! "+i);
        }
        this.choices[i] = choice;
        countSet();
    }
    public void addEnumResultSet(EnumResultSet set){
        // Tack on an ERS to the end
        if (set == null){
            return;
        }else if (choices == null){
            this.choices = new EnumResultSet[10];
        }else if (size+1 >= choices.length){
            this.choices = resize(choices, 2*size);
        }

        this.choices[size++] = set;
        countSet();
    }
    private EnumResultSet[] resize(EnumResultSet[] elems, int newsize){
        if(newsize < 0){
            throw new IllegalArgumentException("New size is less than 0 @ChoiceNode.resize");
        }
        EnumResultSet[] newelems = (EnumResultSet[])(new Object[newsize]);
        if (elems == null){
            return newelems;
        }
        // Add elements back in
        System.arraycopy(elems, 0, newelems, 0, Math.min(elems.length, newsize));

        return newelems;
    }
    @Override
    public String toString(){
         // Done for debuging purposes mostly...
        String ans = ""+index+"[ ";
        String body = "";
        
        for(int x = 0; x < choices.length; x++){
            if (choices[x] != null){
                body += choices[x].enumVal.name() + " ";

            }
        } // END FOR
        ans += body.trim() + " ]";
        
        return ans;
    }
}

class EnumResultSet{
    // Convenience Object
    public final Enum enumVal;
    public Node result = null;

    public EnumResultSet(Enum e, Node result){
        this.enumVal = e;
        this.result = result;
    }
}