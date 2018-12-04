package cs2321;

import net.datastructures.*;

import java.util.Comparator;
/**
 * A5 Toolbox
 * A static import class
 * @author Brett Kriz
 */
public class A5_Toolbox {
    public static void checkKey(Object arg) throws InvalidKeyException{
        // Conveinence function
        if (arg == null){
            throw new InvalidKeyException("Key is Null!");
        }
    }
    
    public static void checkEntry(Entry<?,?> e) throws InvalidEntryException{
        // Conveinence function
        if (e.getKey() == null || !(e.getKey() instanceof Comparable) ){
            throw new InvalidEntryException("Key is BAD!");
        }
        // Check standards @@@
        if (e.getValue() == null){
            throw new InvalidEntryException("Value is Null!");
        }
    }
    

}
