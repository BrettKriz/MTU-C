import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * A simple read-evaluate-print loop for the Gloom programming language.
 * <p>
 * CHANGES TO THIS FILE WILL NOT BE RETAINED.
 *
 * @author Jason Hiebel
 */
public class Gloom {

	/**
	 * Starts a read-evaluate-print loop which reads a Gloom expression and
	 * calls the interpreter for evaluation. Arguments passed to this program
	 * will be tokenized and evaluated by the interpreter in the order they
	 * are given.
	 *
	 * @param args files to evaluate
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Interpreter3 interpreter = new Interpreter3();
		
		// import and evaluate argument files
		for(String arg : args) {
			System.out.printf("Importing %s%n", arg);
			interpreter.evaluate(new Scanner(new File(arg)));
		}
		
		// start the repl
		Scanner scanner = new Scanner(System.in);
		while(true) {
			try {
				System.out.print("> ");
				if(!scanner.hasNextLine()) { break; }
				String line = scanner.nextLine();
				
				interpreter.evaluate(new Scanner(line));
			}
			catch(RuntimeException exception) {
				exception.printStackTrace();
			}
			
			System.out.printf("%nStack %s%n%n", interpreter.stack());
		}
	}
}





