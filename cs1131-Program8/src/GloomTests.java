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
	private static int point = 0;
	private static int total = 0;

	// Interpret the expression and compare the result to the expected answer.
	private static void test(int points, String expression, String answer) {
		Interpreter interpreter = new Interpreter();
		
		String output;
		try {
			interpreter.evaluate(new Scanner(expression));
			output = interpreter.stack().toString();	
		}
		catch(RuntimeException exception) {
                        exception.printStackTrace();
			output = exception.getMessage();
		}
		
		total += points;
		if(answer.equals(output)) {
			System.out.printf("✓  \"%s\"  %n", expression);
			point += points;
		}
		else {
			System.out.printf("x  \"%s\"  ", expression);
			System.out.printf("(was \"%s\", should be \"%s\")%n", output, answer);
		}
	}

	/**
	 * Evaluates the suite of tests.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {
                /*
		System.out.printf("--- Part 1 Tests ---%n");
		test(1, "1 -42 2 -12", "[ 1 -42 2 -12 ]");
		test(1, "42 dup", "[ 42 42 ]");
		test(1, "12 42 over", "[ 12 42 12 ]");
		test(1, "42 drop", "[ ]");
		test(1, "12 42 swap", "[ 42 12 ]");
		System.out.println();
		test(1, "12 >r 42 r>", "[ 42 12 ]");
		test(1, "42 12 +", "[ 54 ]");
		test(1, "42 12 -", "[ 30 ]");
		test(1, "42 12 *", "[ 504 ]");
		test(1, "42 12 /", "[ 3 ]");
		test(1, "42 12 mod", "[ 6 ]");
		test(1, "42 12 >", "[ -1 ]");
		System.out.println();
		test(1, "42 int?", "[ -1 ]");
		test(1, "[ 42 ] int?", "[ 0 ]");
		test(1, "42 list?", "[ 0 ]");
		test(1, "[ 42 ] list?", "[ -1 ]");
		System.out.println();
		test(1, "[ 1 2 3 4 ] eval", "[ 1 2 3 4 ]");
		test(1, "[ [ 1 2 3 4 ] ] eval", "[ [ 1 2 3 4 ] ]");
		test(1, "[ 1 2 [ 3 4 ] ] eval", "[ 1 2 [ 3 4 ] ]");
		System.out.println();
		test(1, "( x y -- z )", "[ ]");
		test(1, "( ( x y -- z ) -- z )", "[ ]");
		test(1, "[ ( [ ] ) ( ) ]", "[ [ ] ]");
		System.out.println();
		test(1, "( nip ) 12 42 swap drop", "[ 42 ]");
		test(1, "( dupd ) 12 42 over swap", "[ 12 12 42 ]");
		test(1, "( swapd ) 12 42 10 >r swap r>", "[ 42 12 10 ]");
		test(1, "( rot ) 12 42 10 >r swap r> swap", "[ 42 10 12 ]");
		test(1, "( pick ) 12 42 10 >r over r> swap", "[ 12 42 10 12 ]");
		test(1, "( not ) -1 -1 * 1 -", "[ 0 ]");
		test(1, "( not )  0 -1 * 1 -" , "[ -1 ]");
		test(1, "( < ) 42 12 swap >", "[ 0 ]");
                //
		test(1, "( dip ) 1 2 [ 1 + ] swap >r eval r>", "[ 2 2 ]");
		test(1, "( keep ) 1 2 [ 1 + ] over [ eval ] swap >r eval r>", "[ 1 3 2 ]");
		System.out.println();
		test(1, "[ 1 2 3 [ + + ] eval [ eval ] ] eval", "[ 6 [ eval ] ]");
		test(1, "[ 1 + ] 1 over eval over eval over eval", "[ [ 1 + ] 4 ]");
		
		System.out.println();
		System.out.println();
		System.out.printf("--- Part 2 Tests [EASY!] ---%n");
		test(1, "3 1 [ 1 2 ] insert", "[ ]");
		test(1, "3 1 [ 1 2 ] dup >r insert r>", "[ [ 1 3 2 ] ]");
		test(1, "3 1 [ 1 2 ] dup copy >r insert r>", "[ [ 1 2 ] ]");
		test(1, "[ 1 2 ] 0 [ ] dup >r insert r>", "[ [ [ 1 2 ] ] ]");
		test(1, "[ 1 2 ] 1 [ 1 2 ] dup >r insert r>", "[ [ 1 [ 1 2 ] 2 ] ]");
		System.out.println();
		test(1, "5 1 [ 1 2 3 ] set", "[ ]");
		test(1, "5 1 [ 1 2 3 ] dup >r set r>", "[ [ 1 5 3 ] ]");
		test(1, "5 1 [ 1 2 3 ] dup copy >r set r>", "[ [ 1 2 3 ] ]");
		test(1, "[ 1 2 ] 0 [ 0 ] dup >r set r>", "[ [ [ 1 2 ] ] ]");
		test(1, "[ 1 2 ] 1 [ 1 2 3 ] dup >r set r>", "[ [ 1 [ 1 2 ] 3 ] ]");
		System.out.println();
		test(1, "0 [ 5 4 3 2 ] remove", "[ 5 ]");
		test(1, "0 [ 5 4 3 2 ] dup >r remove r>", "[ 5 [ 4 3 2 ] ]");
		test(1, "0 [ 5 4 3 2 ] dup copy >r remove r>", "[ 5 [ 5 4 3 2 ] ]");
		test(1, "2 [ 5 4 [ 3 2 ] ] dup >r remove r>", "[ [ 3 2 ] [ 5 4 ] ]");
		test(1, "0 1 2 [ 4 3 [ 2 [ 1 ] ] ] remove remove remove", "[ 1 ]");
		System.out.println();
		test(1, "3 [ 5 4 3 2 ] get", "[ 2 ]");
		test(1, "3 [ 5 4 3 2 ] dup >r get r>", "[ 2 [ 5 4 3 2 ] ]");
		test(1, "3 [ 5 4 3 2 ] dup copy >r get r>", "[ 2 [ 5 4 3 2 ] ]");
		test(1, "2 [ 5 4 [ 3 2 ] ] dup >r get r>", "[ [ 3 2 ] [ 5 4 [ 3 2 ] ] ]");
		test(1, "0 1 2 [ 4 3 [ 2 [ 1 ] ] ] get get get", "[ 1 ]");
		System.out.println();
		test(1, "[ 1 2 3 4 5 ] size", "[ 5 ]");
		test(1, "[ 1 2 3 ] [ 4 5 6 ] append", "[ [ 1 2 3 4 5 6 ] ]");
		test(1, "[ 1 2 3 ] [ 4 5 6 ] dup >r append drop r>", "[ [ 4 5 6 ] ]");
		test(1, "[ [ [ ] ] ] size", "[ 1 ]");
		test(1, "[ ] [ ] append", "[ [ ] ]");
		System.out.println(); 
		test(1, "[ a b c ] [ 42 ] ! a b c", "[ 42 42 42 ]");
                //*
		test(1, "[ a b c ] [ 42 ] ! [ b ] [ 12 ] ! a b c", "[ 42 12 42 ]");
                //*
		test(1, "[ a ] [ 42 ] ! [ b ] [ a ] ! [ a ] [ 12 ] ! b", "[ 12 ]");
		test(1, "[ a ] [ 42 ] ! [ b ] a 0 [ ] dup >r insert r> ! [ a ] [ 12 ] ! b", "[ 42 ]");
            // REDO
                        //*
		test(1, "[ a ] [ 0 ] ! [ inc-a ] [ [ a ] a 1 + 0 [ ] copy dup >r insert r> ! ] ! a inc-a a inc-a a", "[ 0 1 2 ]");
                
		System.out.println();
		test(1, "-1 [ 42 ] [ 12 ] if", "[ 42 ]");
		test(1, "0 [ 12 ] [ 42 ] if", "[ 42 ]");
		test(1, "42 dup 0 > [ 1 + ] [ 1 - ] if", "[ 43 ]");
                //*/
		test(1, "5 [ dup 1 > [ dup 1 - -1 ] [ 0 ] if ] loop", "[ 5 4 3 2 1 ]");
		test(1, "5 [ dup 1 > [ dup dup * swap 1 - -1 ] [ 0 ] if ] loop", "[ 25 16 9 4 1 ]");
		System.out.println();
		test(1, "[ inc ] ( x -- x++ ) [ 1 + ] ! 40 inc inc", "[ 42 ]");
		test(1, "[ dec ] ( x -- x-- ) [ 1 - ] ! 44 dec dec", "[ 42 ]");
		test(1, "[ pos? ] ( x -- t/f ) [ 0 > ] ! 42 pos? -1 pos? 0 pos?", "[ -1 0 0 ]");
		test(1, "[ neg? ] ( x -- t/f ) [ 0 swap > ] ! 42 neg? -1 neg? 0 neg?", "[ 0 -1 0 ]");
		test(1, "[ max ] ( a b -- max ) [ over over > [ ] [ swap ] if drop ] ! 5 8 max", "[ 8 ]");
		test(1, "[ min ] ( a b -- min ) [ over over > [ swap ] [ ] if drop ] ! 5 8 min", "[ 5 ]");
		test(1, "[ neg ] ( x -- -x ) [ 0 swap - ] ! 42 neg neg neg 12 neg neg -10 neg", "[ -42 12 10 ]");
		test(1, "[ pow ] ( x p -- x^p ) [ 1 swap [ dup >r 0 > [ over * r> 1 - -1 ] [ r> drop swap drop 0 ] if ] loop ] ! 2 4 pow", "[ 16 ]");
		test(1, "[ sqr ] ( x -- x^2 ) [ dup * ] ! 2 dup sqr dup sqr dup sqr", "[ 2 4 16 256 ]");
		test(1, "[ when ] [ [ ] if ] ! 5 0 > [ 42 ] when", "[ 42 ]");
		test(1, "[ unless ] [ [ ] swap if ] ! 5 0 > [ 42 ] unless", "[ ]");
		
		System.out.println();
		System.out.println();
		System.out.printf("--- Part 2 Tests [HARD!] ---%n");
		test(2, "[ fib ] ( f0 f1 -- f>15 ) [ [ dup 15 > [ 0 ] [ over over + 1 ] if ] loop ] ! 1 1 fib", "[ 1 1 2 3 5 8 13 21 ]");
		test(2, "[ fact ] ( i f -- f ) [ [ over 1 swap > [ swap drop 0 ] [ over * swap 1 - swap -1 ] if ] loop ] ! 5 1 fact", "[ 120 ]");
		test(2, "[ spill ] ( i l -- ... ) [ [ over 0 swap > [ drop drop 0 ] [ over over get swap >r swap 1 - r> -1 ] if ] loop ] ! 2 [ 42 12 10 ] spill", "[ 10 12 42 ]");
		test(2, "[ move ] ( l -- l ) [ [ ] [ over size 0 > [ over 0 swap remove over 0 swap insert -1 ] [ swap drop 0 ] if ] loop ] ! [ 42 12 10 ] move", "[ [ 10 12 42 ] ]");
		test(2, "[ yes ] ( k -- ... ) [ [ dup 0 > [ -1 swap 1 - -1 ] [ drop 0 ] if ] loop ] ! 5 yes", "[ -1 -1 -1 -1 -1 ]");
		System.out.println();
		test(2, "[ fib ] [ dup 1 swap > [ drop 1 ] [ dup 1 - fib swap 2 - fib + ] if ] ! 6 fib", "[ 21 ]");
		test(2, "[ fact ] [ over 1 swap > [ swap drop ] [ over * swap 1 - swap fact ] if ] ! 5 1 fact", "[ 120 ]");
		test(2, "[ spill ] [ over 0 swap > [ drop drop ] [ over over get swap >r swap 1 - r> spill ] if ] ! 2 [ 42 12 10 ] spill", "[ 10 12 42 ]");
		test(2, "[ move ] [ over size 0 > [ over 0 swap remove over 0 swap insert move ] [ swap drop ] if ] ! [ 42 12 10 ] [ ] move", "[ [ 10 12 42 ] ]");
		test(2, "[ yes ] [ dup 0 > [ -1 swap 1 - yes ] [ drop ] if ] ! 5 yes", "[ -1 -1 -1 -1 -1 ]");
		
		System.out.println();
		System.out.println();
		System.out.printf("- Grade: %2.0f%%%n", 100. * point / total);
	}
}