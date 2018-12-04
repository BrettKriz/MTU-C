
import java.io.*;

/**
 * BitInputStream
 * @author Brett Kriz
 */
public class BitInputStream {
    private InputStream input;
    private int nextBits;
    private int BitsToGo;
    private boolean isEOS;

    public BitInputStream(InputStream in) {
        if (in == null){
            throw new NullPointerException("Bad InputStream");
        }
        input = in;
        BitsToGo = 0;
        isEOS = false;
    }

    public int read() throws IOException {
        if (isEOS){
            // We're already done!
            return -1;
        }
        
        if (BitsToGo == 0) {
            nextBits = input.read();
            
            if (nextBits == -1) {
                    isEOS = true;
                    return -1;
            }
            BitsToGo = 8;
        }
        BitsToGo--;

        return (nextBits >>> BitsToGo) & 1;
    }

    public int readNoEoS() throws IOException {
        int result = read();
        // Because sometimes, we dont want the EOS
        if (result != -1){
            return result;
        }else{
            throw new EOFException("EOS");
        }
    }

    public void close() throws IOException {
            input.close();
    }
}
