import java.util.*;
import java.lang.*;
import java.nio.ByteBuffer;

/**
 * Simple cache simulator.
 *
 * @author Brett Kriz
 */
public class JCache {
    public static boolean quiet = false;
    /**
     * A cache line (no data).
     */
    private static class Line {
	private boolean valid = false;
	private int tag = 0;

	public boolean isValid() {return valid;}
	public int getTag() {return tag;}
	public void makeValid() {valid = true;}
	public void setTag(int id) {tag = id;}
    } // end of Line class

    private static Line [] cache;  // simulation of the cache
    private static int lines;      // the number of lines in the cache

    /**
     * Get the log base 2 of n assuming n is a power of 2.
     *
     * @return the log base 2 of n.
     */
    private static int log2(int n) {
	int result = 0;
	while (n > 1) {
	    result += 1;
	    n = n >> 1;
	}
	return result;
    } // end of log2 method

    /**
     * See if a given access is a hit or miss.
     * Update the cache structure as appropriate.
     *
     * @param address The address being looked for.
     *
     * @return true if a hit, false if not
     */
    private static boolean access2(int address) {
        boolean ans = false;
        boolean flag = false;
        String tag = "";
        
           out(null);
              out(null);
        
        String addr = Integer.toString(address)+"00";
        while(addr.length() != 32){
            addr = "0" + addr;
         }
        out("ADDR: "+addr + " (int) "+address);
                
        
        
        // #bits in for line number
         int bits = log2(lines);
        Line val;
        Line val2; // c
        
        boolean valid = false;
        int len = addr.length();
        int zzz = (len-bits)-2;
        //out(""+zzz);
        int tl = zzz;
        tag = addr.substring(0, tl);
        String lineNstr = addr.substring(tl, addr.length()-2);
        int lineN = Integer.parseInt( lineNstr );

           out("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< bits: "+bits+"\t|t1: "+ tl +"\n|Tag: "+tag+"\t|lineN: "+lineN);

        try{
            
                int at = Integer.parseInt(addr);
            out(">>> "+cache.length+ " ===== "+at);
                val = cache[lineN];
                // Convert it and get values
                out("val: "+val.isValid() + " , "+val.getTag());
                

                flag = val.isValid();
        }catch (Error e){
                e.printStackTrace();
                flag = false;
        }

        // Not a hit?1\
        if (!flag){
                val2 = cache[address];
                val2.makeValid();
                val2.setTag(Integer.parseInt(tag));
                cache[address] = val2;
                out("" + val2.isValid() + ","+val2.getTag());
        }

        return ans;

    } // end of access method

    private static boolean access(int address) {
        //out(null);
        out(null);
        // Why couldnt I just get this?
        /*byte[] a = ByteBuffer.allocate(32).putInt(address).array();
        
        for (int x = 0; x < a.length; x++){
            System.out.print(""+a[x]+",");
        }
        */
        
        
        int linenum = log2(lines);
        int tag = address >> linenum;
        int diff = tag << linenum;
        out("diff: "+diff+"\t|tag: "+tag);
        int mask = 0;
        char[] masks = "11111111111111111111111111111100".toCharArray();
        
        for (int x = 2; x < linenum+2; x++){
            masks[masks.length-x] = 0;
        }
        String step = "";
        for(int x = 0; x < masks.length; x++){
            step = ""+masks[x];
        }
        mask = Integer.parseInt(step);
        
        int line = (address - diff);
        out(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+mask+"\t | Linebits: "+linenum+"\t|becomes: "+Integer.toBinaryString(line)
                +" with "+Integer.toBinaryString(address));
        
        //int line = (nlines)
        Line cur;
        boolean isHit = false;
        
        out("ADDRESS = "+address);
        out("tag: "+tag+"\t|line: "+line);
        
        if (line == 4){
            out("DEBUG");
        }
        
        try{
            cur = cache[line];
        }catch (ArrayIndexOutOfBoundsException e){
            //e.addSuppressed(e);
            return false;
        }
        out("CUR: "+cur.isValid() + ","+cur.getTag());
        isHit = cur.isValid();
        
        if (!isHit){
            Line updated = new Line();
            updated.makeValid();
            updated.setTag(tag);
            cache[line] = updated;
        }
        
        
        for (int x = 0; x < cache.length; x++){
            out(""+x+": "+cache[x].isValid()+"-"+Integer.toHexString(cache[x].getTag()));
        }
        
        
        return isHit;
    }
    
    public static void out(String msg){
            if (quiet){ return;};
            if (msg == null || msg.isEmpty()){
                    msg = "";
            }

            System.out.println(msg);
    }
    
    private static int hits;       // count hits

    /**
     * Set up cache and test access method.
     *
     * @param args Unused.
     */
    public static void main(String [] args) {

	Scanner input = new Scanner(System.in);

	// read number of lines and set up cache structure
	lines = input.nextInt();
	if ((lines & (lines-1)) != 0) {
	    System.err.println("Error: lines is not a power of 2");
	    System.exit(1);
	}
	cache = new Line[lines];
	for (int i=0; i<lines; i+=1) {
	    cache[i] = new Line();
	}
	System.out.printf("Simulating a cache with %d lines%n", lines);

	// read addresses
	int accesses = 0;
	while (input.hasNext()) {
	    int addr = input.nextInt();
	    accesses += 1;

	    // check cache
	    boolean hit = access(addr);

	    // print return result
	    if (hit) {
		hits += 1;
		System.out.printf("%d : hit%n", addr);
	    }
	    else
		System.out.printf("%d : miss%n", addr);
	}

	// print cache contents
	String format = "%2d: %c-0x%0zx%n";
	String c = (29-log2(lines))/4+1 + "";
	format = format.replace("z",c);
	for (int i=0; i<lines; i+=1) {
	    System.out.printf(format,
		    i, cache[i].isValid() ? 'v' : 'i', cache[i].getTag());
	}

	// print statistics
	System.out.printf("hit rate = %.1f%%%n", 100.0*hits/accesses);

    } // end of main method

} // end of JCache method
