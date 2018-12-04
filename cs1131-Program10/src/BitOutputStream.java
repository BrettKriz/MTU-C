
import java.io.*;

/**
 * BitOutputStream
 * @author Brett Kriz
 */
public class BitOutputStream {
    private OutputStream output;
    private int currentByte;
    private int BitsInCurByte;

    public BitOutputStream(OutputStream out) {
        if (out == null){
            throw new NullPointerException("Bad OutputStream");
        }
        output = out;
        currentByte = 0;
        BitsInCurByte = 0;
    }

    public void write(int bit) throws IOException {
        if (!(bit == 0 || bit == 1)){
            throw new IllegalArgumentException("Argument wasn't 0 or 1");
        }
        // Drop the bits into place, use bitshifts
        currentByte = currentByte << 1 | bit;
        BitsInCurByte++;
        
        if (BitsInCurByte == 8) {
                output.write(currentByte);
                BitsInCurByte = 0;
        }
    }

    public void close() throws IOException {
        // Make sure we have the padding
        while (BitsInCurByte != 0){
                write(0);
        }
        output.close();
    }
}
