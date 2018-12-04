// Netbeans was havinga /*fit*/
// I ran out of time.. :/
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
// you may not import additional files
// Map & Hash Map were approved in class :-)

/**
 * An interpreter for the Gloom programming language.
 *
 * @author Brett Kriz
 */
public class Interpreter {
        protected final Stack<Object> stack;
        protected final Stack<Object> retain;
        
        
        private static final Boolean YES = true;
        private static final Boolean NO = false;

        private Boolean inComment;
        private int Commentcases = 0;
        private boolean go = true;
        
        private static final Map<String, OP> operators = new HashMap<>();
        private static final Map<String, String> valRefs = new HashMap<>();
        
        
        class ReturnEx extends Error{
            public ReturnEx() {}
            public ReturnEx(String msg){
               super(msg);
            }
        }

	/**
	 * Constructs an interpreter!
	 */
	public Interpreter() {
            this.stack = new Stack();
            this.retain = new Stack();
            //this.tbl = new List();
            this.inComment = false;
            
            setup();
	}
        public Interpreter(Stack<String> Retain){
            this.stack = new Stack();
            this.retain = new Stack();
            this.inComment = false;
        }
        
        private boolean arrayHasNull(Object[] tbl){
            boolean flag = false;
            
            for(Object cur : tbl){
                if (cur == null){
                    flag = true;
                    break;
                }
            }
            
            return flag;
        }
	
        /**
	 * Return's the interpreter's stack for use by the read-evaluate-print loop.
	 *
	 * @return the primary stack (not the retain stack)
	 */
	public Stack<?> stack() {
            return stack;
	}
        
	/**
	 * Evaluates the list of tokens given from an imported file or
	 * read-evaluate-print loop.
	 *
	 * @param scanner the tokenizer
         * @return St
	 */
	public Stack<?> evaluate(Scanner in) {
            String full = in.nextLine();
            Scanner scanner = new Scanner(full);// Now that I have the line
            
            if (scanner.hasNext()){
                inComment = false;
                List<Object> tbl = null;

                tbl = generateTbl(full); // Align input

                evaluateTbl(tbl);
                if (!tbl.isEmpty()){
                    // >:(
                    evaluateTbl(tbl);
                }
                System.out.println("Finished with array size: "+tbl.size());
            } else {
                return new Stack();
            }
            if (stack == null){
                    return new Stack();
                }else{
                    return stack;
                }
	}
        
        private List<Object> evaluateTbl(List<Object> tbl){
            // Using the state stack
            if (tbl.isEmpty()){
                return new List();
            }
            
            
            for(int x = 0; !tbl.isEmpty(); x++){
                
                int len = tbl.size();
                //Object[] oTbl = tbl.getArrayCopy();// for updating

                Object arg = tbl.get(x);// Better for placements
                tbl.remove(x);
                
                /*
                if (x == 9 && arg.toString().equals("b")){
                    Boolean k = true; // FOR DEBUG
                    try{
                        stack.top();
                        operators.size();
                    }catch (Exception e){
                        
                    }
                }
                */
                
                if ( arg instanceof String && getOPType(arg.toString()) != null ){
                    arg = getOPType(arg.toString());
                }// Catch new functions (which are strings)
                if (arg instanceof String){
                    // Could be a list, op or number
                    String arg2 = (String)arg;
                    
                    if (isList(arg2)){
                        // Compute for list
                        // durring eval, break open lists\
                        List<Object> tbl2 = generateTbl(arg2);
                        // Insert for list
                        tbl.set(x, tbl2);
                        x--; // Now that we've unpacked, do it agian
                    }else if (isInt(arg2)){
                        stack.push( (Integer.parseInt(arg2) ));
                    }else if (isOP(arg2)){
                        tbl.set(x, getOPType(arg2));
                        x--;
                    }else {
                        // Wut
                        System.out.println(arg2 + " is not recognized! WHY is it a string?");
                    }
                } else if ( arg instanceof evalOP ){
                    evalOP arg2 = (evalOP)arg;
                    List<Object> larg = arg2.canEval();
                    // Insert list into tbl for eval
                    if (larg != null){
                        // Eval possible
                        addListToList(larg, x, tbl);
                        //stack.add
                        
                        x--; // Now that we've unpacked, do it agian
                    }else{
                        // Give up and add to stack
                        // Going to add toString later
                        stack.push("eval");
                    }
                }else if ( arg instanceof GloomFuncOP){
                    // Just to eval
                    GloomFuncOP arg2 = (GloomFuncOP)arg;
                    Object func = arg2.getFunction();
                    
                    if (func != null){
                        if (func instanceof List){
                            List<Object> lfunc = (List)func;
                            if (!lfunc.isEmpty()){
                                addListToList(lfunc,x,tbl);
                            }
                        }else{
                            tbl.add(x, arg);
                        }
                    }
                    
                    x--;
                }else if ( arg instanceof Integer ){  // INT

                    stack.push( arg );
                }else if ( arg instanceof List ){
                    stack.push( arg );
                }else if (arg instanceof loopListOP){
                    // exicuites at least once
                    // Checks for bool flag after
                    // repeats op while flag is true
                    Object arg2 = stack.pop(); //list?
                    List<Object> body = new List();
                    Object[] backing = {};
                    Object[] bodytbl = {};

                    if (arg2 instanceof List){
                        List arg3 = (List)arg2;
                        int arglen = arg3.getArrayCopy().length;
                        backing = new Object[arglen];
                        bodytbl = new Object[arglen];
                        
                        System.arraycopy(arg3.getArrayCopy(), 0, backing, 0, arglen);
                        System.arraycopy(arg3.getArrayCopy(), 0, bodytbl, 0, arglen);
                        
                        boolean run = true;
                        int count = 0;
                        do{
                            body = new List();
                            //bodytbl = new Object[arglen];
                            
                            for (Object cur : bodytbl){
                                body.add(cur);
                            }
                            
                            Object raw = stack.pop();
                            stack.push(raw);
                            List<Object> list2 = new List(body);
                            
                            if (raw instanceof BooleanFlag ){
                                run = ((BooleanFlag)(raw)).flag;
                            }else if (raw instanceof Boolean){
                                run = (Boolean)raw;
                            }else if (raw instanceof Integer && ( ((Integer)raw).equals(0) || ((Integer)raw).equals(-1) )){
                                run = new BooleanFlag( ((Integer)raw) ).flag ;
                            }else{
                                list2.add(0,raw);
                            }
                            // "5 [ dup 1 > [ dup 1 - -1 ] [ 0 ] if ] loop", "[ 5 4 3 2 1 ]"

                            //Object cur = stack.pop();
                            
                            //list2.add(0,raw);
                            //stack.push(raw);

                            List<Object> result = evaluateTbl( list2 );// Should put bool onto stack

                            count++;
                            if (count >= 250){
                                // Somethings wrong...
                                break;
                            }
                        }while(run);

                    }
                }else if (arg instanceof ifOP){
                    ifOP arg2 = (ifOP)arg;
                    List<Object> result = arg2.evaluate();
                    addListToList(result,x,tbl);
                    x--;
                }else if ( arg instanceof OP ){ // OP
                    OP curOP = (OP)arg;
                    curOP.operator();
                }
                
            }// END FOR
            return tbl;
        }
        
        private List<Object> generateTbl(String full){
            if (full == null || full.isEmpty()){
                return new List<>();
            }
            
            Scanner scanner = new Scanner(full);
            List<Object> tbl = new List<>();
            int i = 0;
            go = true;
            inComment = false;
            Commentcases = 0;
            
            for(int x = 1; true; x++){
                //boolean tokenIsInt = false;
                if (!scanner.hasNext()){
                    break;
                }
                if (scanner.hasNextInt()){  // INT

                    if (!inComment){
                        tbl.add( ((Integer)(scanner.nextInt())) );
                        i++;
                    }
                }else if (scanner.hasNext()){ // OP
                    String curstr = scanner.next();
                    i++;

                    OP cur = getOPType(curstr);
                    if ("eval".equals(curstr)){
                        Boolean k = true; // for DEBUG! DELETE ME
                    }
                    if (!inComment || cur instanceof endComment){
                        if (cur instanceof endComment && Commentcases > 0){
                            Commentcases--;
                        }else{
                            if (cur instanceof ListOP){
                                cur = (ListOP)cur;
                                // Run it

                                if (cur instanceof startListOP){
                                    // INCEPTION
                                    startListOP cur2 = (startListOP)cur;
                                    List<Object> arg2 = new List<>();
                                    String segment = "";

                                    int brackets = 1;
                                    // NOW, count brackets until 0 or END
                                    while(brackets > 0){
                                        if (!scanner.hasNext()){
                                            break;
                                        }
                                        String listcur = scanner.next();
                                        i++;

                                        if ("[".equals(listcur)){
                                            brackets++;
                                        }else if ("]".equals(listcur)){
                                            brackets--;
                                        }

                                        if (brackets > 0){
                                            segment += listcur + " ";
                                        }

                                    }// END WHILE

                                    arg2 = generateTbl(segment);
                                    tbl.add(arg2);
                                }else if (cur instanceof endListOP){
                                    try{
                                        cur.operator(); 
                                    }catch (ReturnEx t){
                                        // for end list
                                        break;
                                    }
                                }else{
                                    // Let comments run their corse
                                    cur.operator();
                                }
                            //
                            }else if (cur != null){
                                tbl.add(cur);
                            }else{
                                tbl.add(curstr);
                            }
                        }
                    }else if(cur instanceof startComment){//END IF COMMENT
                        // Make sure 
                        Commentcases++;
                    }
                }//END IF (INT or OP)
            }// END WHILE

            return tbl;
        }// END
        
        private void addListToList(List<Object> addin, int index, List<Object> base){
            //base.remove(index);
            for (int y = 0; y < addin.size(); y++){
                // Should be adding without data loss
                base.add( index+y, addin.get(y) );
            }
        }
        private Integer safePopInt(){
            Integer ans = null;
            String ansSTR = "Sorry, Error";
            
            if (!stack.isEmpty()){
                Object o = stack.pop();
                try{
                    ans = (Integer)o;
                }catch (ClassCastException t){
                    // Not an INT
                }
                
            }
            return ans;
        }
        private String safePopStr(){
            String ansSTR = null;
            
            if (!stack.isEmpty()){
                Object o = stack.pop();
                if (o instanceof String){
                    ansSTR = o.toString();
                }
            }
            
            return ansSTR;
        }
        private Object safePop(){
            return stack.pop();
        }
        public void close(){
            // Shut down thread
            go = false;
        }
        
        private OP getOPType(String op){
            OP curOP = null;
            // Get ops
            for(Map.Entry<String, OP> cur : operators.entrySet()){
                if (cur.getKey().equals(op)){
                    // OP FOUND
                    if (op.equals("eval")){
                        //System.out.println("");
                    }
                    curOP = cur.getValue();
                    break;
                }
            }
            return curOP;
        }
        private String getOPStr(OP type){
            String ans = null;
            if (type == null){
                // -.-
                return null;
            }
            // Get ops
            for(Map.Entry<String, OP> cur : operators.entrySet()){
                if ( (cur.getValue().getClass()).equals(type.getClass()) ){
                    // OP FOUND
                    ans = cur.getKey();
                    break;
                }
            }
            return ans;
        }
        private boolean isOP(String arg){
            boolean ans = (getOPType(arg) != null);// for debuging
            return ans;
        }
        private boolean isList(String arg){
            boolean ans = (arg.contains("["));// for debuging
            return ans;
        }
        private boolean isList(Object arg){
            return ( arg instanceof List );
        }
        private boolean isInt(String arg){
            
            boolean ans = !(Pattern.matches("[a-zA-Z]+", arg));
            return ans;
        }
        private void safePush(Integer arg){
            safePush(arg.toString().replace("null", ""));
        }
        private void safePush(String arg){
            if (arg == null){
                return;
            }
            arg = arg.replace("null", "").replace("[ ]", "");
            if (!"".equals(arg)){
                stack.push(arg);
            }
        }
        private void safePush(Object arg){
            if (arg == null){
                return;
            }
            stack.push( arg.toString() );
        }

        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //                  OPS CLASSES
        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        private interface Base_OP{
            

            public void operator() throws ReturnEx;
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx; // not enough info
            public boolean canCompute(OP curOP, Object[] args) throws ReturnEx;
            public Object[] popArgs(int howmany) throws IllegalArgumentException;
            //public Integer[] operate(Integer lop, Integer rop);
            
        }
        private abstract class OP implements Base_OP{
            // Buffer class
            //@Override       //    Deepest left to shallowest right
            @Override
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{
                // push back everything and destroy list
                for(int x = 0; x < ops.length; x++){ // Order must be ensured
                    safePush(ops[x]);
                }
                safePush( getOPStr(curOP) );
                throw new ReturnEx("Lack of numbers, refunding & spilling OP");
            }
            public void refundOP(Object[] ops) throws ReturnEx{
                for(int x = 0; x < ops.length; x++){ // Order must be ensured
                    safePush(ops[x]);
                }
                throw new ReturnEx("Lack of numbers, refunding only numbers");
            }
            @Override
            public boolean canCompute(OP curOP, Object[] args) throws ReturnEx{
                if (arrayHasNull(args)){
                    refundOP(curOP, args);
                    return false;
                }// If its not up to snuff...
                return true;
            }
            @Override
            public String toString(){
                // Find our op
                String ans = "Unokwn OP";
                
                for(Map.Entry<String, OP> cur : operators.entrySet()){
                    if (this.getClass().equals(cur.getValue().getClass())){
                        ans = cur.getKey().trim();
                        break;
                    }
                }
                
                return ans;
            }
            @Override
            public Object[] popArgs(int howmany) throws IllegalArgumentException{
                
                if (howmany < 1){
                    return new Object[1];
                }
                List<Object> args = new List();
                for(int x = 0; x < howmany; x++){
                    Object cur = safePop();
                    if (cur != null){
                        args.add( cur );
                    }else{
                        throw new IllegalArgumentException("Null was poped from stack @ local "+x);
                    }
                }
                
                return args.getArrayCopy();
            }
            public void forfit(Object arg){
                forfit(arg,"Single Argument Type Missmatch (Non-specified)");
            }
            public void forfit(Object arg, String msg){
                Object[] a = {arg};
                forfit(a,msg);
            }
            public void forfit(Object[] args){
                forfit(args,"Argument Type Missmatch(s) (Non-specified)");
            }
            public void forfit(Object[] args, String msg){
                refundOP(args);
                // one of these may get removed, but untill then...
                //throw new IllegalArgumentException(msg);
            }
            
        }

        private abstract class GeneralOP    extends OP{

                
        }
        private class BooleanFlag           extends OP{
            private Boolean flag;
            
            public BooleanFlag(boolean flag){
                this.flag = flag;
            }
            public BooleanFlag(Integer arg){
                this.flag = (arg == -1);
            }
            public void setFlag(boolean flag){
                this.flag = flag;
            }
            public Boolean getFlag(){
                if (flag == null){
                    return false;
                }
                return flag;
            }
            @Override
            public void operator(){
                // N/A
            }
            @Override
            public String toString(){
                if(flag){
                    return "-1";
                }else{
                    return "0";
                }
            }
            
        }
        private abstract class LogicOP      extends OP{
            public abstract void operator();
                
            
        }
        private abstract class IntOP        extends OP{
            
            public abstract Integer operate(Integer op);
            public abstract Integer operate(Integer lop, Integer rop);
            @Override
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{
                super.refundOP(curOP, ops);
            } 
        }
        private abstract class BinaryIntOP  extends IntOP{
            @Override
            public void operator(){
                
                Object[] args = popArgs(2);
                if (args[0] instanceof Integer && args[1] instanceof Integer){
                    Integer a = (Integer)args[0];
                    Integer b = (Integer)args[1];
                    stack.push( operate(b,a) );
                }else{
                    forfit(args);
                }
                
            }

            @Override
            public Integer operate(Integer op){
                throw new UnsupportedOperationException();
            }
        }
        private abstract class UnaryIntOP   extends IntOP{
            @Override
            public void operator(){
                Object arg = stack.pop();
                if (arg instanceof Integer){
                    Integer a = (Integer)arg;
                    stack.push( operate(a) );
                }
            }
            @Override
            public Integer operate(Integer lop, Integer rop){
                throw new UnsupportedOperationException();
            }
        }
        private abstract class BooleanIntOP extends IntOP{
            @Override
            public void operator(){
                Integer a = (Integer)stack.pop();
                Integer b = (Integer)stack.pop();
                stack.push( operate(b,a) );
            }
                        @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
            
        }
        private abstract class ListOP       extends GeneralOP{
            // Nothing to see here
            @Override
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{
                super.refundOP(curOP, ops);
            } // Default functionality

        }
        // ~~~~~~~~~~~~~~~~~~~~~OP LIBRARAY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
        
        private class endListOP     extends ListOP{
            // Call for an eval to canel
            @Override
            public void operator() throws ReturnEx{
                throw new ReturnEx();
            }

        }
        private class startListOP   extends ListOP{
            private Stack<String> list = new Stack();
            private String contents = "";
            
            @Override
            public void operator(){
                //String listSTR = computeList().toString();
                //stack.push(listSTR);
            }

        }
        private class endComment    extends ListOP{
            @Override
            public void operator(){
                inComment = false;
            }
        }
        private class startComment  extends ListOP{
            @Override
            public void operator(){
                inComment = true;
            }
        }

        private class plusOP        extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to plusOP: "+lop+" "+rop);
                }

                return lop + rop;
            }
        }
        private class minusOP       extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to minusOP: "+lop+" "+rop);
                }
                 
                return lop - rop;
            }
        }
        private class timesOP       extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to timesOP: "+lop+" "+rop);
                }
                 
                return lop * rop;
            }
        }
        private class divideOP      extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to divideOP: "+lop+" "+rop);
                }
                
                return lop / rop;
            }
        }

        private class minOP         extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to minOP: "+lop+" "+rop);
                }
                 
                return Math.min(lop,rop);
            }
        }
        private class maxOP         extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to maxOP: "+lop+" "+rop);
                } 
                
                return Math.max(lop, rop);
            }
        }
        private class modOP         extends BinaryIntOP{
            // True modulous
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to modOP: "+lop+" "+rop);
                }
                Integer ans = ((lop % rop + rop) % rop);
                return ans;
            }
        }        
        private class powOP         extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to powOP: "+lop+" "+rop);
                }
                Double ans = Math.pow(lop.doubleValue(), rop.doubleValue());
                return ans.intValue();
            }
        }
        // General OPS
        private class addFuncOP     extends LogicOP{
            @Override
            public void operator(){
                // Grab a list and evaluate
                Boolean k = true; // for debug! DELETE ME!
                Object rop = stack.pop();// ROP (value)
                
                
                if (rop instanceof List){
                    List rop2 = (List)rop;
                    if (rop2.isEmpty()){
                        // -.-
                        rop = stack.pop();// Lets pray this isnt empty
                    }
                    /*else{
                        int x = 0;
                        for(Object cur : rop2.getArrayCopy()){
                            if (cur instanceof GloomFuncOP){
                                // NO! Your a string now
                                rop2.set(x, cur.toString());
                            }
                            x++;
                        }
                        rop = rop2;
                    }
                    //*/
                }
                
                Object lop = stack.pop();// LOP (many keys)
                List arg2 = null;
                
                if (lop instanceof List){
                    List keys           = (List)lop;
                    String keys2        = keys.getBody();
                    Scanner splitter    = new Scanner(keys2);
                    //List<Object> val2   = null;
                    
                    /*
                    if (rop instanceof List){
                        List val    = (List)rop;
                        val2        = generateTbl(val.getBody());
                    }else{
                        val2        = generateTbl(rop.toString());
                    }
                    */
                    // Lets do it
                    while( splitter.hasNext() ){
                        // Asign new functions via keys mapped to val
                        if (splitter.hasNextInt()){ 
                            splitter.nextInt();// Numbers not allowed
                        }else if(splitter.hasNext()){
                            // There we go, a var name;
                            String curstr = splitter.next();
                            OP keyOP = getOPType(curstr);
                            
                            if ((keyOP != null) && keyOP instanceof GloomFuncOP ){
                                // OVER RIDE FUNCTION
                                operators.remove(curstr);
                                GloomFuncOP ans = new GloomFuncOP(curstr, rop.toString());
                                operators.put(curstr , ans);
                            }else{
                                GloomFuncOP ans = new GloomFuncOP(curstr, rop.toString());
                                operators.put(curstr, ans);
                            }
                        }
                    }// END WHILE

                }else{
                    stack.push(lop); // LOP
                    stack.push(rop);
                    stack.push(this); // ~CHECK FOR correctness
                }
            }
        }
        private class ifOP          extends LogicOP{
            @Override
            public void operator(){
                throw new UnsupportedOperationException("Method will NOT be supported.");
            }
            public List<Object> evaluate(){
                Object[] args = popArgs(3);
                BooleanFlag bool = null;
                
                if (args[2] instanceof Integer){
                    Integer iarg = (Integer)args[2];
                    bool = new BooleanFlag(iarg);
                }else if (args[2] instanceof BooleanFlag){
                   bool = (BooleanFlag)args[2];
                }else{
                    forfit(args, "IF failure! Mismatched Boolean type!");
                }

                if (!canCompute(this, args)){
                    throw new IllegalArgumentException("Invalid input to ifOP!");
                }
                
                //NOW, lets compute
                List<Object> ans = new List<>();
                if (bool.getFlag()){
                    // Execute true list
                    ans.add(args[1]);
                }else{
                    ans.add(args[0]);
                }
                
                ans.add(new evalOP());
                return ans;
            }
        }
        
        private class GloomFuncOP   extends GeneralOP{
            //public List<Object> expr  = null;
            public String key   = null;
            private boolean isFuncView = false;
            
            
            public GloomFuncOP(String key, List<Object> expr){
                // Check for gloom func ops
                for(Object cur : expr.getArrayCopy()){
                    if (cur instanceof GloomFuncOP){
                        isFuncView = true;
                        break; // Just for views - 1AM
                    }
                }
                valRefs.put(key, expr.getBody());
                this.key = key;
            }
            
            public GloomFuncOP(String key, String expr){
                if (expr.length() == 1){
                    valRefs.put(key, fastList(expr).getBody());
                }else{
                    List<Object> tbl2 = generateTbl(expr);
                    valRefs.put(key, expr);
                }
                this.key = key;
            }
            
            
            public GloomFuncOP(String key,OP expr){
                // Lets see what that Object is..
                if (expr instanceof GloomFuncOP){
                    // *eye rub*
                    GloomFuncOP expr2 = (GloomFuncOP)expr;
                    GloomFuncOP2(key,expr2);
                    return;
                }
                this.key = key;
                valRefs.put(key, expr.toString());
            }
            private void GloomFuncOP2(String key,GloomFuncOP expr){
                // 4am, I dont understand why, but this fixes everything.
                this.key = key;
                valRefs.put(key, expr.toString());
            }
            public List<Object> fastList(Object whatever){
                // Just because so much greif.
                List<Object> t = new List<>();
                t.add(whatever);
                return t;
            }
            //*/
            @Override
            public void operator(){
                throw new UnsupportedOperationException("Method will NOT be supported.");
            }
            public Object getFunction(){
                // Here we pray for a pre-existing tbl
                for(Map.Entry cur : valRefs.entrySet()){
                    if (cur.getKey().equals(key)){
                        // Found our func, lets see what it is
                        List<Object> val = generateTbl(cur.getValue().toString());
                        val.add(new evalOP());
                        return val;
                    }
                }
                return new Error("What Even??");
            }
            @Override
            public String toString(){
                return key;
            }
        }
        private class evalOP        extends GeneralOP{
            @Override
            public void operator(){
                // Needs to use canEval
                throw new UnsupportedOperationException("Not supported yet, @@@EVALOP"); //Fix me
            }
            public List<Object> canEval(){ // Make self encasesd!
                // Grab a list and evaluate
                Object arg = stack.pop();
                List arg2 = null;
                
                if (arg instanceof List){
                    arg2 = (List)arg;
                    // Lets crack the list

                }else{
                    stack.push(arg);
                    stack.push(this.toString()); // 
                }
                return arg2;
            }
        }
        
        private class loopListOP    extends LogicOP{
            private List body;
            @Override
            public void operator(){
                throw new UnsupportedOperationException();
            }
        }
        private class sizeListOP    extends GeneralOP{
            @Override
            public void operator(){
                Object arg = stack.pop();
                if (arg instanceof List){
                    List arg2 = (List)arg;
                    Integer size = arg2.size();
                    
                    stack.push(size);   
                }else{
                    Integer z = 0;
                    stack.push(z);
                    throw new IllegalArgumentException("A list was not recieved @ sizeListOP");
                }
            }
        }
        private class getListOP     extends GeneralOP{
            @Override
            public void operator(){
                Object[] args = popArgs(2);
                
                if (args[1] instanceof Integer && args[0] instanceof List){
                    Integer i = (Integer)args[1];
                    List list = (List)args[0];
                    
                    
                    stack.push(list.get(i));
                }else{
                    forfit(args,"Lists were not recieved! @getListOP");
                }
                
            }
        }
        private class setListOP     extends GeneralOP{
            @Override
            public void operator(){
                Object[] args = popArgs(3);
                
                if (args[2] != null & args[1] instanceof Integer && args[0] instanceof List){
                    Integer i = (Integer)args[1];
                    List list = (List)args[0];
                    list.set(i, args[2]);
                    // I guess just dont push anything back?
                    
                    //stack.push(list.get(i));
                }else{
                    forfit(args);
                }
                
            }
        }
        private class removalListOP extends GeneralOP{
            @Override
            public void operator(){
                Object[] args = popArgs(2);
                
                if (args[1] instanceof Integer && args[0] instanceof List){
                    Integer i = (Integer)args[1];
                    List list = (List)args[0];
                    Object target = list.remove(i);
                    
                    stack.push(target);
                }else{
                    forfit(args);
                }
                
            }
        }
        private class insertListOP  extends GeneralOP{
            @Override
            public void operator(){
                Object[] args = popArgs(3);
                // 0 = list 1 = index 2 = arg/val
                
                if (args[2] != null && args[1] instanceof Integer && args[0] instanceof List){
                    Integer i = (Integer)args[1];
                    List list = (List)args[0];
                    list.add(i, args[2]);
                    // I guess just dont push anything back?
                    
                    //stack.push(list.get(i));
                }else{
                    String argSTR = "";
                    for(Object a : args){
                        argSTR += a.toString() + " ";
                    }
                    forfit(args);
                }
            }
        }
        private class copyListOP    extends GeneralOP{
            @Override
            public void operator(){
                Object arg = stack.pop();
                // Shallow copy
                if (arg instanceof List){
                    List<Object> list = ((List)arg).getShallowCopy();
                    //stack.push(list);
                    stack.push(list);
                }else{
                    this.forfit(arg);
                }
                
            }
        }
        private class appendListOP  extends GeneralOP{
            @Override
            public void operator(){
                // add list 2 onto the end of list 1
                Object[] args = popArgs(2);
                
                if (args[0] instanceof List && args[1] instanceof List){
                    List<Object> list1 = (List)args[1];
                    List<Object> list2 = (List)args[0];
                    
                    list1.addListElems(list2);
                    stack.push(list1);
                }else{
                    this.forfit(args);
                }
            }
        }
        
        private class overOP        extends GeneralOP{
            @Override
            public void operator(){
                Object a = stack.pop(); // rop
                Object b = stack.pop(); //lop
                Object[] args = {b,a};
                if (!canCompute(this, args)){
                    this.forfit(args);
                }
                
                stack.push(b); // LOP
                stack.push(a); // ROP
                stack.push(b); // LOP
            }
        }
        private class swapOP        extends GeneralOP{
            @Override
            public void operator(){
                Object a = safePop(); // ROP
                Object b = safePop(); // LOP
                
                Object[] args = {b,a};
                if (!canCompute(this, args)){
                    return;
                }
                
                stack.push(a); // L
                stack.push(b); // R
            }
            
        }
        private class dropOP        extends GeneralOP{
            @Override
            public void operator(){
                // Take the top off and do nothing lol;
                stack.pop();
            }
        }
        private class dupeOP        extends GeneralOP{
            @Override
            public void operator(){
                Object a = stack.pop();
                
                stack.push(a);
                stack.push(a);
            }
        }
        private class retainOP      extends GeneralOP{
            @Override
            public void operator(){
                Object a = safePop();
                
                retain.push(a);
            }
        }
        private class deretainOP    extends GeneralOP{
            @Override
            public void operator(){
                Object a = retain.pop();
                stack.push(a);
            }
        }
        // BOOLEAN OPS
        private class isGreaterOP   extends BooleanIntOP{
            @Override
            public void operator(){
                Integer a = safePopInt();// ROP
                Integer b = safePopInt();// LOP
                Integer[] args = {b,a};
                if (!canCompute(this, args)){
                    this.forfit(args);
                }
                
                
                BooleanFlag ans = new BooleanFlag(NO);
                if ((int)b > (int)a){
                    ans.setFlag(YES);
                }
                    stack.push(ans);
                
            }

        }
        private class isIntOP       extends BooleanIntOP{
            @Override
            public void operator(){
                
                Object arg = stack.pop();
                BooleanFlag ans = new BooleanFlag(NO);
                if (isInt(arg.toString()) && !(arg instanceof List)){
                    ans.setFlag(YES);
                }
                
                stack.push(ans);
            }

        }
        private class isListOP      extends BooleanIntOP{
            @Override
            public void operator(){
                // Take the list, insert bool
                Object arg = stack.pop();
                BooleanFlag ans = new BooleanFlag(NO);
                
                if (isList(arg.toString())){
                    ans.setFlag(YES);
                }
                
                stack.push(ans);
            }
        }

        //************************//
        private void setup(){
            operators.clear();
            operators.put( "+"      ,   new plusOP());
            operators.put( "-"      ,   new minusOP());
            operators.put( "*"      ,   new timesOP());
            operators.put( "/"      ,   new divideOP());
            operators.put( "mod"    ,   new modOP());
            operators.put( "min"    ,   new minOP());
            operators.put( "max"    ,   new maxOP());
            operators.put( "pow"    ,   new powOP()); // Right in the kisser!
            operators.put( ">"      ,   new isGreaterOP());
            operators.put( "eval"   ,   new evalOP());
            operators.put( "int?"   ,   new isIntOP());
            operators.put( "list?"  ,   new isListOP());
            operators.put( "dup"    ,   new dupeOP());
            operators.put( "over"   ,   new overOP());
            operators.put( "drop"   ,   new dropOP());
            operators.put( "swap"   ,   new swapOP());
            operators.put( ">r"     ,   new retainOP());
            operators.put( "r>"     ,   new deretainOP());
            operators.put( "["      ,   new startListOP() );
            operators.put( "]"      ,   new endListOP());
            operators.put( "("      ,   new startComment());
            operators.put( ")"      ,   new endComment());
            operators.put( "!"      ,   new addFuncOP());
            operators.put( "if"     ,   new ifOP());
            operators.put( "loop"   ,   new loopListOP()); // hard
            operators.put( "size"   ,   new sizeListOP());
            operators.put( "copy"   ,   new copyListOP());
            operators.put( "get"    ,   new getListOP());
            operators.put( "set"    ,   new setListOP());
            operators.put( "remove" ,   new removalListOP());
            operators.put( "insert" ,   new insertListOP());
            operators.put( "append" ,   new appendListOP());
            
            
            //operators.put( "")
        }
        
}