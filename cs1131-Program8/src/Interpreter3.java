// Netbeans was having a fit
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

// you may not import additional files
// Map & Hash Map were approved in class :-)

/**
 * An interpreter for the Gloom programming language.
 *
 * @author Brett Kriz
 */
public class Interpreter3 {
        protected static Stack<Integer> stack;
        protected static Stack<Integer> retain;
        protected int index;
        private static boolean inComment = false;

        private static final Map<String, OP> operators = new HashMap<>();
        static{
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
        
	/**
	 * Constructs an interpreter!
	 */
	public Interpreter3() {
            this.stack = new Stack();
            this.retain = new Stack();
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
	 */
	public void evaluate(Scanner scanner) {
            // FIFO
            //scanner.useDelimiter("]");
            // ^^^^^^^^^^^^^^^
            inComment = false;
            List<OP> ops = new List();
            
            // "1 -42 2 -12" --> "[ 1 -42 2 -12 ]"
            // "42 12 +" --> "[ 54 ]"
		while(true){
                    boolean tokenIsInt = false;
                    if (!scanner.hasNext()){
                        break;
                    }
                    
                    if (scanner.hasNextInt()){  // INT
                        
                        if (!inComment){
                            stack.push(scanner.nextInt());
                        }
                    }else if (scanner.hasNext()){ // OP
                        String curstr = scanner.next();
                        OP curOP = getOPType(curstr);
                        
                        if (!inComment || curOP instanceof endComment){
                            curOP.operator();
                        }
                            
                    }

            }// END WHILE
	}

        private static OP getOPType(String op){
            OP curOP = null;
            // Get ops
            for(Map.Entry<String, OP> cur : operators.entrySet()){
                if (cur.getKey().equals(op)){
                    // OP FOUND
                    curOP = cur.getValue();
                    break;
                }
            }
            return curOP;
        }

            /*
            switch(op){
                case "+"    : 
                case "-"    : 
                case "*"    : 
                case "/"    : 
                case "%"    : 
                case "mod"  : 
                case ">"    : 
                case "eval" : 
                case "int?" : 
                case "list?": 
                case "dup"  : 
                case "over" : 
                case "drop" : 
                case "swap" : 
                case ">r"   : 
                case "r<"   : 
                default     : throw new IllegalArgumentException();
            */

        private Integer safeNextInt(Scanner in){
            Integer ans;
            
            if (in.hasNextInt()){
                ans = in.nextInt();
            }else{
                ans = null;
            }
            
            return ans;
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

        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //                  OPS CLASSES
        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        private static interface base_OP{
            
            public Integer operate(Integer lop, Integer rop);
            public Integer operate(Integer op);
            public void operator();
            //public Integer[] operate(Integer lop, Integer rop);
        }
        private static abstract class OP implements base_OP{
            
        }
        private static abstract class BinaryIntOP extends OP{
            public void operator(){
                Integer a = stack.pop();
                Integer b = stack.pop();
                stack.push(operate(b,a));
            }
            @Override
            public Integer operate(Integer op){
                throw new UnsupportedOperationException();
            }
        }
        private static abstract class UnaryIntOP extends OP{
            public void operator(){
                Integer a = stack.pop();
                stack.push(operate(a));
            }
            @Override
            public Integer operate(Integer lop, Integer rop){
                throw new UnsupportedOperationException();
            }
        }
        private static abstract class BooleanIntOP extends OP{
            public void operator(){
                Integer a = stack.pop();
                Integer b = stack.pop();
                stack.push(operate(a));
            }
        }
            
        private static abstract class ListOP extends OP{

           // public abstract void computeList(Scanner in);

            /*
            public void add(String arg){
                list.push(arg);
            }
            public String remove(){
                return list.pop();
            }
            // Spill method
            public Integer[] eval(){
                return null;
            }
            */
       }


        private static class endListOP extends ListOP{
            // Call for an eval to canel
            @Override
            public void operator(){
                // Shouldnt be here
            }
            @Override
            public Integer operate(Integer op){return null;}
            @Override
            public Integer operate(Integer l, Integer r){return null;}
        }
        private static class startListOP extends ListOP{
            private Stack<String> list = new Stack();
            private String contents = "";
            
            
            public void computeList(Scanner in){
                String all = "";
                int brackets = 1;
                
                while(in.hasNext()){
                    String cur = in.next();

                    if ("]".equals(cur)){
                        brackets--;
                        if (brackets <= 0){
                            break;
                        }
                    }
                    list.push(cur);
                }
            }
            public void operator(){}
            public Integer operate(Integer lop, Integer rop){return null;}
            public Integer operate(Integer op){return null;}
        }
        private static class endComment extends ListOP{
            //@Override
            public void operator(){
                inComment = false;
            }
            //@Override
            public Integer operate(Integer op){return null;}
            //@Override
            public Integer operate(Integer l, Integer r){return null;}
        }
        private static class startComment extends ListOP{
            //@Override
            public void operator(){
                inComment = true;
            }
            //@Override
            public Integer operate(Integer op){return null;}
            //@Override
            public Integer operate(Integer l, Integer r){return null;}
        }
        // ~~~~~~~~~~~~~~~~~~~~~EXTENDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
        private static class plusOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                return lop + rop;
            }
        }
        private static class minusOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                return lop - rop;
            }
        }
        private static class timesOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                return lop * rop;
            }
        }
        private static class divideOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                 return lop / rop;
            }
        }
        private static class minOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                return Math.min(lop,rop);
            }
        }
        private static class maxOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                return Math.max(lop, rop);
            }
        }
        private static class modOP extends BinaryIntOP{
            // True modulous
            @Override
            public Integer operate(Integer lop, Integer rop){
                Integer ans = ((lop % rop + rop) % rop);
                return ans;
            }
        } 
        private static class powOP extends BinaryIntOP{
            @Override
            public Integer operate(Integer lop, Integer rop){
                Double ans = Math.pow(lop.doubleValue(), rop.doubleValue());
                return ans.intValue();
            }
        }
        private static class overOP extends BinaryIntOP{
            @Override
            public void operator(){
                Integer a = stack.pop();
                Integer b = stack.pop();
                stack.push(b);
                stack.push(a);
                stack.push(b);
            }

            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class dupeOP extends UnaryIntOP{
            @Override
            public void operator(){
                Integer a = stack.pop();
                stack.push(a);
                stack.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class dropOP extends UnaryIntOP{
            @Override
            public void operator(){
                // Take the top off and do nothing lol;
                stack.pop();
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class swapOP extends BinaryIntOP{
            @Override
            public void operator(){
                Integer a = stack.pop();
                Integer b = stack.pop();
                stack.push(a);
                stack.push(b);
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class retainOP extends UnaryIntOP{
            @Override
            public void operator(){
                Integer a = stack.pop();
                retain.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class deretainOP extends UnaryIntOP{
            @Override
            public void operator(){
                Integer a = retain.pop();
                stack.push(a);
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        // BOOLEAN OPS
        private static class isGreaterOP extends BooleanIntOP{
            @Override
            public void operator(){
                int a = stack.pop();// ROP
                int b = stack.pop();// LOP
                if (b > a){
                    stack.push(-1);
                }else{
                    stack.push(0);
                }
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class isIntOP extends BooleanIntOP{
            @Override
            public void operator(){
                throw new UnsupportedOperationException();
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class isListOP extends BooleanIntOP{
            @Override
            public void operator(){
                throw new UnsupportedOperationException();
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        private static class evalOP extends BinaryIntOP{
            @Override
            public void operator(){
                // Grab a list and evaluate
            }
            @Override
            public Integer operate(Integer op) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Integer operate(Integer lop, Integer rop) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        
} // DONE
