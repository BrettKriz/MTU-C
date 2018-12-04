import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.*;


/**
 *
 * @author Brett Kriz
 */
public class Problem4 {
    /**
    * Return the elements which occur an even number of times
    * in the given list.
    *
    * @param list the list of elements
    * @return a set of elements which occurred an even number of times
    */
    public static Set <Byte > evens(List <Byte > list){
        // EVEN]
        // EASY, add keys of the list values, then associate with a count
        Set<Byte> ans = new HashSet<Byte>(list.size());
        Map<Byte,Integer> tally = new HashMap<Byte,Integer>(list.size());
        
        for(int x = 0; x < list.size(); x++){
            Byte cur = list.get(x);
            
            if (!tally.containsKey( cur )){
                tally.put(cur, 1);
            }else{
                int count = tally.get(cur)+1;
                tally.put(cur, count);// to replace
            }
        }
        
        for(Map.Entry<Byte, Integer> e : tally.entrySet()){
                if ( e.getValue() % 2 == 0 ){
                    // EVEN
                    ans.add(e.getKey());
                }
        }
        return ans;
    }
    public static void main(String[] args){
        Integer b = (Integer)1;
        Integer c = (Integer)2;
        Byte[] h = {1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};
        
        List<Byte> a = new ArrayList<>();
        for(int x = 0; x<h.length; x++){
            a.add( h[x] );
        }
    }
}
