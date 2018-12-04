// Netbeans was having a /*fit*/
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.NoSuchElementException;
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
        
        private static final String YES = "-1";
        private static final String NO = "0";

        private Boolean inComment;
        private boolean go = true;
        
        private static final Map<String, OP> operators = new HashMap<>();
        
        
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
            int len = tbl.size();
            
            for(int x = 0; !tbl.isEmpty(); x++){
                //Object[] oTbl = tbl.getArrayCopy();// for updating
                Object arg = tbl.get(x);// Better for placements
                tbl.remove(x);
                
                if (arg instanceof deretainOP || x == 7){
                    Boolean k = true; // FOR DEBUG
                }

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
                    List larg = arg2.canEval();
                    // Insert list into tbl for eval
                    if (larg != null){
                        // Eval possible
                        addListToList(larg, x, tbl);
                        
                        x--; // Now that we've unpacked, do it agian
                    }else{
                        // Give up and add to stack
                        // Going to add toString later
                        stack.push("eval");
                    }

                } else if ( arg instanceof Integer ){  // INT

                    stack.push( arg );
                } else if ( arg instanceof List ){
                    stack.push( arg );
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
                        Boolean k = true;
                    }
                    if (!inComment || cur instanceof endComment){

                        if (curstr != null && (cur instanceof ListOP) ){
                            cur = (ListOP)cur;
                            // Run it
                            
                            if (cur instanceof startListOP && x > 1){
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
                        }else{
                            tbl.add(cur);
                        }
                        
                    }//END IF COMMENT
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
            boolean ans = (!arg.contains("[") && !arg.contains(" "));// for debuging
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
        private interface base_OP{
            
            public Integer operate(Integer lop, Integer rop);
            public Integer operate(Integer op);
            public void operator() throws ReturnEx;
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx; // not enough info
            public boolean canCompute(OP curOP, Object[] args) throws ReturnEx;
            //public Integer[] operate(Integer lop, Integer rop);
        }
        private abstract class OP implements base_OP{
            // Buffer class
            //@Override       //    Deepest left to shallowest right
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{
                // push back everything and destroy list
                for(int x = 0; x < ops.length; x++){ // Order must be ensured
                    safePush(ops[x]);
                }
                safePush( getOPStr(curOP) );
                throw new ReturnEx("Lack of numbers, refunding & spilling OP");
            }
            //@Override
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
            
        }

        private abstract class BinaryIntOP extends OP{
            @Override
            public void operator(){
                Integer a = (Integer)stack.pop();
                Integer b = (Integer)stack.pop();
                Object[] t = {b,a};
                if (!canCompute(this, t)){
                    return;
                }
                stack.push( operate(b,a) );
            }
            @Override
            public Integer operate(Integer op){
                throw new UnsupportedOperationException();
            }
            @Override
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{
                super.refundOP(curOP, ops);
            } 
        }
        private abstract class UnaryIntOP extends OP{
            @Override
            public void operator(){
                Integer a = (Integer)stack.pop();
                stack.push( operate(a) );
            }
            @Override
            public Integer operate(Integer lop, Integer rop){
                throw new UnsupportedOperationException();
            }
        }
        private abstract class BooleanIntOP extends OP{
            @Override
            public void operator(){
                Integer a = (Integer)stack.pop();
                Integer b = (Integer)stack.pop();
                stack.push( operate(a) );
            }
            
        }
        // @@@LISTOPS
        private abstract class ListOP extends OP{
            // Nothing to see here
            @Override
            public Integer operate(Integer op){return null;}
            @Override
            public Integer operate(Integer l, Integer r){return null;}
            @Override
            public void refundOP(OP curOP, Object[] ops) throws ReturnEx{} 

        }

        private class endListOP extends ListOP{
            // Call for an eval to canel
            @Override
            public void operator() throws ReturnEx{
                throw new ReturnEx();
            }

        }
        private class startListOP extends ListOP{
            private Stack<String> list = new Stack();
            private String contents = "";
            
            //@Override
            public void operator(){
                //String listSTR = computeList().toString();
                //stack.push(listSTR);
            }

        }
        private class endComment extends ListOP{
            @Override
            public void operator(){
                inComment = false;
            }
            @Override
            public Integer operate(Integer op){return null;}
            @Override
            public Integer operate(Integer l, Integer r){return null;}
        }
        private class startComment extends ListOP{
            @Override
            public void operator(){
                inComment = true;
            }
            @Override
            public Integer operate(Integer op){return null;}
            @Override
            public Integer operate(Integer l, Integer r){return null;}
        }
        // ~~~~~~~~~~~~~~~~~~~~~EXTENDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
        private class plusOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};

                return lop + rop;
            }
        }
        private class minusOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                return lop - rop;
            }
        }
        private class timesOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                return lop * rop;
            }
        }
        private class divideOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                 return lop / rop;
            }
        }
        private class minOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                return Math.min(lop,rop);
            }
        }
        private class maxOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                return Math.max(lop, rop);
            }
        }
        private class modOP extends BinaryIntOP{
            // True modulous
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                Integer ans = ((lop % rop + rop) % rop);
                return ans;
            }
        }        
        private class powOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer[] args = {lop,rop};
                 
                Double ans = Math.pow(lop.doubleValue(), rop.doubleValue());
                return ans.intValue();
            }
        }
        private class overOP extends BinaryIntOP{
            @Override
            public void operator(){
                Object a = stack.pop();
                Object b = stack.pop();
                Object[] args = {b,a};
                if (!canCompute(this, args)){
                    return; // Jeez...
                }
                
                stack.push(b);
                stack.push(a);
                stack.push(b);
            }

            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private class dupeOP extends UnaryIntOP{
            @Override
            public void operator(){
                Object a = stack.pop();
                
                stack.push(a);
                stack.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private class dropOP extends UnaryIntOP{
            @Override
            public void operator(){
                // Take the top off and do nothing lol;
                stack.pop();
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private class swapOP extends BinaryIntOP{
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
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private class retainOP extends UnaryIntOP{
            @Override
            public void operator(){
                Object a = safePop();
                
                retain.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private class deretainOP extends UnaryIntOP{
            @Override
            public void operator(){
                Object a = retain.pop();
                stack.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        // BOOLEAN OPS
        private class isGreaterOP extends BooleanIntOP{
            @Override
            public void operator(){
                Integer a = safePopInt();// ROP
                Integer b = safePopInt();// LOP
                Integer[] args = {b,a};
                if (!canCompute(this, args)){
                    return;
                }
                
                if ((int)b > (int)a){
                    stack.push(YES);
                }else{
                    stack.push(NO);
                }
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        private class isIntOP extends BooleanIntOP{
            @Override
            public void operator(){
                
                String arg = safePopStr();
                String ans = NO;
                if (isInt(arg)){
                    ans = YES; 
                }
                
                stack.push(ans);
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
        private class isListOP extends BooleanIntOP{
            @Override
            public void operator(){
                // Take the list, insert bool
                String arg = safePopStr();
                String ans = NO;
                if (isList(arg)){
                    ans = YES;
                }
                
                stack.push(ans);
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
        // @@@EVAL
        private class evalOP extends BinaryIntOP{
            @Override
            public void operator(){
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
            public List<Object> canEval(){
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
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //Fix me
            }
        }
        private void setup(){
            operators.put( "+"    , new plusOP());
            operators.put( "-"    , new minusOP());
            operators.put( "*"    , new timesOP());
            operators.put( "/"    , new divideOP());
            operators.put( "mod"  , new modOP());
            operators.put( ">"    , new isGreaterOP());
            operators.put( "eval" , new evalOP());
            operators.put( "int?" , new isIntOP());
            operators.put( "list?", new isListOP());
            operators.put( "dup"  , new dupeOP());
            operators.put( "over" , new overOP());
            operators.put( "drop" , new dropOP());
            operators.put( "swap" , new swapOP());
            operators.put( ">r"   , new retainOP());
            operators.put( "r>"   , new deretainOP());
            operators.put( "["    , new startListOP() );
            operators.put( "]"    , new endListOP());
            operators.put( "("    , new startComment());
            operators.put( ")"    , new endComment());
        }
        
}