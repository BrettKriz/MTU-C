import java.util.*;// for debug only!
/* *
* Lab 4 - Problem 3
*
* @author Brett Kriz
*/
public class Problem3{
	/* *
	 * Merge two sorted queues into a single sorted queue ,
	 * rem ov i n g all d u p l i c a t e s . The given queues do not contain
	 * duplicates , a lth o ugh both queues may contain the same
	 * value .
	 *import java.util.*;// for debug only!
	 * @param queue0 a sorted queue to merge
	 * @param queue1 a sorted queue to merge
	 * @return a sorted , merged queue
	 */
	public static Queue <Integer> merge ( Queue <Integer> queue0 , Queue <Integer> queue1 ){
		Queue<Integer> ans = new LinkedList<>();
		int x;
		int maxsize = Math.max(queue0.size(), queue1.size());
		Integer past = null;

		if (maxsize < 1 ){
			return ans;// were done here
		}

		// Add em in
		for(x = 0; x < maxsize; x++){
			Integer cur0 = queue0.poll();
			Integer cur1 = queue1.poll();
			Integer high = (Integer)Math.max(cur0,cur1);
			Integer low = (Integer)Math.min(cur0,cur1);

			if(low.equals(past)){
				low = null;
			}else if (high.equals(past)){
				high = null;
			}else if ( !low.equals(null) && !high.equals(null) && (low.equals(past) && high.equals(past)) ){
				low = null;
				high = null;
			}else if ( !low.equals(null) && !high.equals(null) && low.equals(high) ){
				low = null;
			}
			
			
			if (high.equals(null) && low.equals(null)){
				// Yeahh....
			}else if (high.equals(null) && !low.equals(null)){
				ans.add(low);
				past = low;
			}else if (low.equals(null) && !high.equals(null)){
				ans.add(high);
				past = high;
			}else{
				// Good
				//if (high.equals(null) && !low.equals(null)){
				ans.add(low);
				ans.add(high);
				past = high;
				//}
			}
			
		}

		return ans;
	}// end method
	public static void main(String[] args){
		System.out.println();
	}
}
