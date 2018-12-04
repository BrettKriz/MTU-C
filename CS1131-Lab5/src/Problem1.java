import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeSet;
//import CS1131-Lab5.Problem4;
/**
 *
 * @author Brett Kriz
 */
public class Problem1 {
    /**
    * Compute the dot product of two sparse vectors.
    *
    * @param v0 A sparse vector represented as a map.
    * @param v1 A sparse vector represented as a map.
    * @return the dot product of the two sparse vectors.
    */
    public static double dot(Map <Integer , Double > v0 , Map <Integer , Double > v1){
        if (v0.isEmpty() && v1.isEmpty()){
            return -1;
        }
        
        double dotprod = 0;
        for(Map.Entry<Integer,Double> cur : v0.entrySet()){
            for(Map.Entry<Integer,Double> cur2 : v1.entrySet()){
                if ( cur.getKey().equals(cur2.getKey()) ){
                    dotprod += cur.getValue() * cur2.getValue();
                }
            }
        }

        return dotprod;
    }

}
