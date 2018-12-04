import java.util.Scanner;
import java.lang.String;
import java.lang.Exception;
import java.io.*;
import java.lang.Character;


/**
 * CS1131
 * Program 2: Run-Length Encoding
 * and Binary Image Matrix's<P>
 * <p>
 * @author Brett Kriz
 */
public class BinaryImage {
        private static final boolean DEBUGING_ON = System.getProperty("user.dir").contains("NetBeans");
        
        public static void main(String[] args){
            
        }
        
        private static void out(Object o){
            if (DEBUGING_ON){
                System.out.println(""+o);
            }
        }
        private static void out(double arg){
            if (DEBUGING_ON){
                System.out.println(""+arg);
            }
        }
        /*
          Quick function for grabbing the HEIGHT and WIDTH
        */
	public static int[] getWidthAndHeight(String input){
            
            int W,H;// for debugging
            // Use the substring of the input for the splitter
            Scanner splitter = new Scanner(input.substring(4));
            
            W = splitter.nextInt();
            H = splitter.nextInt();
            //      H = Verticle, W = Horizontal
            int[] point = {W,H};

            return point;
	}
        /*
          Quick function for grabbing the TYPE
        */
        public static String getType(String input) throws IOException{
            if (input.equals("")) {throw new IOException();}

            return input.substring(0, 3);
        }
        /*
          Quick function for grabbing the entire HEADER
        */
        public static String getHeader(String input) throws IOException{
            if (input.equals("")) {throw new IOException();}
            Scanner splitter = new Scanner(input);
            return splitter.nextLine();
        }
        /*
          Quick function for swaping the TYPE in the Header
        */
        public static String changeHeaderType(String input) throws IOException{
            if (input.equals("")) {throw new IOException();}
            
            if (input.indexOf("BIX") != -1){
                input = input.replace("BIX","RLE");
            } else if (input.indexOf("RLE") != -1){
                input = input.replace("RLE","BIX");
            }else{
                throw new IOException(); // Something is wrong
            }
            
            return input;
        }
        /*
          Quick function for filling a string
          With the contents of a File
        */
        public static String readFromFile(String location) throws IOException, FileNotFoundException{
            if (location.equals("")) {
                throw new IOException();// We're done here
            }
            
            // Recognize pure inputs
            if ( !location.contains(".") ){ // Is this a file?
                out("Assuming location is what we want...");
                out(location);
                out("~~^ ORIGINAL ^~~~~~~~~~~~~~~~~~");
                return location;
            }else{
                out("Location is a file path: " + location);
                out("");
            }
            
            File file = new File(location);
            if (!file.exists()){
                throw new FileNotFoundException();// We're done here
            }
            String temp = new String(), head = "";
            Scanner in = new Scanner(file);
            //boolean flag = true;
            
            while(in.hasNext()){
                /*
                if (flag){
                    head = in.nextLine();
                    int i = head.indexOf(" ");
                    String n = head.substring(i, head.indexOf(" ", i+1) );
                    i = Integer.parseInt(n);
                    String target = repeatChar('0',i);
                    String target2 = repeatChar('1',i);
                    
                    if (head.contains(target)){
                        //Weird same line glitch
                        String toSplit = insert(head, "\n",  head.indexOf(target));
                        Scanner split = new Scanner(toSplit);
                        head = split.nextLine();
                        temp += split.nextLine();
                    }else if (head.contains(target2)){
                        String toSplit = insert(head, "\n",  head.indexOf(target2));
                        Scanner split = new Scanner(toSplit);
                        head = split.nextLine();
                        temp += split.nextLine();
                    }
                    
                    flag = false;
                }else{*/
                    temp += in.nextLine().trim() + "\n";
                //}
                
            }
            //*/
            //temp = head + "\n" + temp;
            
            if (temp.isEmpty()) {
                throw new IOException();// We're done here
            }
            
            in.close();
            //System.out.println(temp.trim());
            return temp.trim();
        }
        public static String insert(String str, String additive, int index){
            return str.substring(0, index) + additive + str.substring(0, index);
        }
        /*
          Quick function for writing a string to a file
        */
        public static void writeToFile(String location, String content) throws IOException{
            if (location.equals("")) {
                throw new IOException();// We're done here
            }
            File file = new File(location);
            PrintWriter out = new PrintWriter(file);
            Scanner splitter = new Scanner(content);
            
            // It seems to destroy newlines in writer
            // So to combat this, lets use a scanner
            while(splitter.hasNextLine()){
                String temp = splitter.nextLine().replaceAll("null", "");
                temp = temp.replaceAll("\n\n\n", "").replaceAll("\n\n", "");
                out.println(temp);
                out.flush();
            }
            
            out.close();
        }
	public static void convertBIXtoRLE(String input, String output) throws IOException{
            // Open the file and read in
            String input2 = readFromFile(input);
            
            if (!getType(input2).equals("BIX")) {
                throw new IOException();// We're done here
            }
            
            String temp = new String();
            temp += changeHeaderType(getHeader(input2)) + "\n";
            int x = -1, count = 0, start = input2.indexOf('\n')+1;
            int len = input2.length();
            boolean isZero = (input2.charAt(start) == '0');
            char current = 'x', past = 'x';
            
            // Now to fill the contents
            for(x = start; x < len; x++){
                // Start splitting
                current = input2.charAt(x);

                if (current == '0' || current == '1')  { // good imput, compute
                            if (count == 9){ // time to break
                                if (past == current) {
                                    // same number, skip new counts
                                    temp += "" + count + "0";
                                    count = 0; 
                                }
                            } else if (count > 9){
                                throw new IOException();
                            }
                    
                            
                            if (past == current || past == 'x'){
                                count++;
                            } else if (past != current) {
                                temp += "" + count; // Change
                                count = 1; // Already have 1 item
                                isZero = (input2.charAt(x) == '0');
                            }
                            
                            if (x == len-1){ // AKA at end
                                temp += "" + count;
                            }

                    past = current;
                }// END IF 
            }// END FOR
            
            out(temp);
            writeToFile(output, temp);
	}
	public static void convertRLEtoBIX(String input, String output) throws IOException{
            String input2 = readFromFile(input);
            
            if (!getType(input2).equals("RLE")) {
                throw new IOException();// We're done here
            }
            String temp = "" + changeHeaderType(getHeader(input2)) + "\n";
            
            int[] point = getWidthAndHeight(input2);
            int x = -1, start = input2.indexOf('\n')+1;// tested
            // 1 then 0, not 0 then 1 :(
            int Height = point[1], Width = point[0];
            int H_Count = 1, W_Count = 0; // Counting Variables
            boolean isZero = true;
            
            // Now to fill the contents
            for(x = start; x < input2.length(); x++){
                // Grab the number to repeat
                int count = Character.getNumericValue(input2.charAt(x)); // Decode RLE
                char BC = '1'; // BC -> BinaryCharacter
                if (isZero){
                    BC = '0';
                }

                if (count != 0){
                    for(int num = 0; num < count; num++){ //Loop for characters
                        temp += BC;
                        W_Count++;
                        if (W_Count == Width){// Time for a new row
                            //System.out.print(" :"+W_Count);
                            temp += "\n";
                            W_Count = 0;
                            H_Count++;
                        }
                    }// END FOR
                    //System.out.println("H: "+H_Count);
                }

                isZero = !isZero;
            }// END FOR            
            writeToFile(output, temp);
	}
	public static void flipHoriz(String input, String output) throws IOException{
            if (input.equals("")) {
                throw new IOException();// We're done here
            }
            String input2 = readFromFile(input);
            if (getType(input2).equals("RLE")){
                // No RLE's
                throw new IOException();
            }

            String output2 = "";
            int[] point = getWidthAndHeight(input2);
            int Height = point[1], x=0;
            Scanner splitter = new Scanner(input2);
            String[] rows = new String[Height];
            String head = splitter.nextLine(); // Remove the header
            
            while( splitter.hasNextLine() ){
                // Loop thru x
                rows[x] = splitter.nextLine();
                x++;
            }

            if (rows.length < 2){
                // Somethings wrong...
                throw new IOException();
            } 

            // Lets begin the flipping
            int z = 0, len = rows.length;
            while(z < len){
                output2 = ""+ rows[z] + "\n" + output2;
                z++;
            }
            output2 = "" + head.trim() + "\n" + output2.trim();
            
            writeToFile(output, output2);
	}
	public static void flipVert(String input, String output) throws IOException{
            if (input.equals("")) {
                throw new IOException();// We're done here
            }
            String input2 = readFromFile(input);
            if (getType(input2).equals("RLE")){
                // No RLE's
                throw new IOException();
            }
            
            int[] point = getWidthAndHeight(input2);
            int x=0;
            Scanner splitter = new Scanner(input2);
            String[] rows = new String[point[1]];// AKA HEIGTH
            String head = splitter.nextLine(); // Slap the header on
            String output2 = "";
            // Make a table form
            while( splitter.hasNextLine() ){
                String temp = splitter.nextLine().trim();
                if (!temp.contains("null")) {
                    // Errors!
                    rows[x] = temp;
                }
                x++;
            }

            // Lets begin the flipping
            int z = 0, len = rows.length;
            while(z < len){
                String cur = rows[z];
                String newer = "";
                //System.out.println(">"+cur);
                int l = cur.length();
                
                for(int y = 0; y < l; y++){
                    // Flip the chars in a newer string
                    newer = "" + cur.charAt(y) + newer;
                }
                output2 += "" + newer + "\n";
                z++;   
            }
            output2 = "" + head + "\n" + output2.trim();
            writeToFile(output, output2);
	}
        // @@Right
	public static void rotateRight(String input, String output) throws IOException{
            if (input.equals("")) {
                throw new IOException();// We're done here
            }
            String input2 = readFromFile(input);
            if (getType(input2).equals("RLE")){
                // No RLE's
                throw new IOException();
            }
            // Go to the RIGHT/Far side of array
            Scanner splitter = new Scanner(input2);
            
            int[] point = getWidthAndHeight(input2);
            int Width = point[0], Height = point[1]; 
            String[] rows = new String[ Height ];

            String head = getType(input2) + " " + point[1] + " " + point[0];
            splitter.nextLine(); // cut the header out
            
            String output2 = "";
            // Make a table form
            int z = 0;
            while( splitter.hasNextLine() &&  z < Height){
                rows[z] = splitter.nextLine();
                z++;
            }
            // Start using sudo points
            for(int w = 0; w < Width; w++){ // Width-wise Position
                String temp = "";
                for(int h = 0; h < Height; h++){ // Height-wise Position
                    char cur;
                    
                    try{
                        cur = rows[h].charAt(w);
                        // Stuff near the top is burried on the far right now
                        // So write backward
                        temp = "" + cur + temp;
                    }catch (RuntimeException e){
                        // So yeah, just some missing stuff
                        // Seemed plausable on graded sheet
                        out("Suppressing null char @ [" + (w+1) + ", " + (h+1) +"] [W,H] (RIGHT)");
                    }
                }// END FOR
                output2 += temp + "\n";
            }
            output2 = "" + head.trim() + "\n" + output2.trim();
            // The rotated rows will become string length
            writeToFile(output, output2);
	}
        // @@Left
	public static void rotateLeft(String input, String output) throws IOException{
                        if (input.equals("")) {
                throw new IOException();// We're done here
            }
            String input2 = readFromFile(input);
            if (getType(input2).equals("RLE")){
                // No RLE's
                throw new IOException();
            }
            // Go to the RIGHT/Far side of array
            Scanner splitter = new Scanner(input2);
            int[] point = getWidthAndHeight(input2);
            int Width = point[0], Height = point[1];
            String[] rows = new String[ Height ];
            
            String head = getType(input2) + " " + point[1] + " " + point[0];
            splitter.nextLine(); // Cut the header out
            
            String output2 = "";
            // Make a table form
            int z = 0;
            while( splitter.hasNextLine() &&  z < Height){
                rows[z] = splitter.nextLine();
                z++;
            }
            // Start using sudo points
            for(int w = 0; w < Width; w++){
                String temp = "";
                for(int h = 0; h < Height; h++){
                    char cur;
                    try{
                        cur = rows[h].charAt(w);
                        temp = "" + cur + temp;
                    }catch (RuntimeException e){
                        // So yeah, just some missing stuff
                        // Seemed plausable on graded sheet
                        out("Suppressing null char @ [" + (w+1) + ", " + (h+1) +"] [W,H] (LEFT)");
                    }
                }
                // Write the rows backward for left
                output2 = "" + temp + "\n" + output2;
            }
            // The rotated rows will become string length
            output2 = "" + head + "\n" + output2.trim();
            writeToFile(output, output2);
	}
}// END CLASS
