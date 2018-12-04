import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Instructions In the Program3 class, develop a main method that reads and
 * displays data files in the following manner: The data file name is specified
 * as the first command line argument to your main method. The first two
 * characters contain the number of elements your queue may contain. Create a
 * Character Queue of the specified size. Read characters into the queue until
 * it is full. When the queue is full print all the characters in the queue on a
 * single line, followed by a newline. Do not use toString( ) for this. Repeat
 * Steps 4 and 5 until you reach the end of the file. Print any remaining
 * characters in the queue. Note: the queue's toString method should return a
 * string of the elements of the queue in the same format as an ArrayList. i.e:
 * "[1, 2, 3, 4, 5]" where 5 is the first element added to the queue and 1 is
 * the last element added to the queue.
 */
/*
 * Grading guidelines 10 points if your Program 3 produces the correctly
 * transforms the inputs to specified outputs. 10 points for style. 10 points
 * for each method that works: enqueue, dequeue, peek, isEmpty, isFull, size,
 * toString, and the constructor
 */

public class Program3 {

	public static void main(String[] args) throws IOException {

		// Read command line input for filename from user
		Scanner in = new Scanner(System.in);
		System.out.println("Please name your data output file: ");
		// Prompts user to specify the name of the data file
		String dataInput = "C:\\Users\\Brett Kriz\\Documents\\NetBeansProjects\\Program3\\src\\input1.txt";
		String dataOutput = "output1.txt";
		// String dataOutput = in.nextLine();
		in.close();

		// Use FileReader to take in first two characters
		BufferedReader reader = new BufferedReader(new FileReader(dataInput));
		FileWriter writer = new FileWriter(dataOutput);
		char ch1 = (char) reader.read();
		char ch2 = (char) reader.read();
		// Convert char to int
		int num1 = Character.getNumericValue(ch1) * 10;
		int num2 = Character.getNumericValue(ch2);
		int maxElem = num1 + num2; // max number of elements taken from first two chars
                //System.out.println("NUMS: " + num1 + " & " + num2 + " = " + maxElem);

		Queue<Object> test = new Queue<Object>(maxElem);
		int count = 0;
		String line = null;
                String full = "";
                char cur = 0;
		
		// use the readLine method of the BufferedReader to read one line at a time.
		try {
                    StringBuilder builder =  new StringBuilder();
                    boolean first = true;
                    while (reader.ready()) 
                    {
                           int flag = reader.read();
                           if (flag == -1){
                               break;
                           }
                           cur = (char)flag;
                           test.enqueue(cur);
                           
                           full += cur;
                           count++;
                           first = false;
                    }
                } catch (Exception e) {
			
		}

                // Dequeue
                try{
                    while(!test.isEmpty()){
                        char temp = (char) test.dequeue();
                         //System.out.println(temp + "\t-> "+(int)temp);
                        writer.write(temp);
                        writer.flush();
                    }
                }catch (Exception e){

                }
                        
            reader.close();
            writer.close();
	}
}
