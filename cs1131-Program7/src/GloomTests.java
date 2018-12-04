import java.util.Scanner;

/**
 * A simple test suite fixture which evaluates a variety of expressions and
 * compares the resulting stack with the expected stack.
 * <p>
 * CHANGES TO THIS FILE WILL NOT BE RETAINED.
 *
 * @author Jason Hiebel
 */
class GloomTests {
    public static Scanner in = new Scanner(System.in);
    public static final boolean DEBUG = false;
    
	// Interpret the expression and compare the result to the expected answer.
	private static void test(String expression, String answer) {
		Interpreter interpreter = new Interpreter();
		
		String output;
		try {
			interpreter.evaluate(new Scanner(expression));
			output = interpreter.stack().toString();	
		}
		catch(RuntimeException exception) {
			output = exception.getMessage();
		}
		
		if(answer.equals(output)) {
			System.out.printf("✓  \"%s\"  %n", expression);
		}
		else {
			System.out.printf("x  \"%s\"  ", expression);
			System.out.printf("(was \"%s\", should be \"%s\")%n", output, answer);
		}
                if (DEBUG){
                System.out.print("\n Waiting for input!");
                in.next();
                }
	}

	/**
	 * Evaluates the suite of tests.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {
                //
		test("1 -42 2 -12", "[ 1 -42 2 -12 ]");
		test("42 dup", "[ 42 42 ]");
		test("12 42 over", "[ 12 42 12 ]");
		test("42 drop", "[ ]");
		test("12 42 swap", "[ 42 12 ]");
		test("12 >r 42 r>", "[ 42 12 ]");
		test("42 12 +", "[ 54 ]");
		test("42 12 -", "[ 30 ]");
		test("42 12 *", "[ 504 ]");
		test("42 12 /", "[ 3 ]");
		test("42 12 mod", "[ 6 ]");
		test("42 12 >", "[ -1 ]");
		test("[ 1 2 3 4 ] eval", "[ 1 2 3 4 ]");
		test("( x y -- z )", "[ ]");
		test("( nip ) 12 42 swap drop", "[ 42 ]");
		test("( dupd ) 12 42 over swap", "[ 12 12 42 ]");
		test("( swapd ) 12 42 10 >r swap r>", "[ 42 12 10 ]");
		test("( rot ) 12 42 10 >r swap r> swap", "[ 42 10 12 ]");
		test("( pick ) 12 42 10 >r over r> swap", "[ 12 42 10 12 ]");
		test("( not ) -1 -1 * 1 -", "[ 0 ]");
		test("( not ) 0 -1 * 1 -" , "[ -1 ]");
		test("( < ) 42 12 swap >", "[ 0 ]");
                
		test("( dip ) 1 2 [ 1 + ] swap >r eval r>", "[ 2 2 ]");
		test("( keep ) 1 2 [ 1 + ] over [ eval ] swap >r eval r>", "[ 1 3 2 ]");
                //*/
		test("[ 1 2 3 [ + + ] eval [ eval ] ] eval", "[ 6 [ eval ] ]");
	}
}