import java.util.*;// for debug only!
/* *
* Lab 4 - Problem 1
*
* @author Brett Kriz
*/
public class Problem1{
	/**
	 * Find the index of the first element in the given list
	 * with the given value . If no element is found , return
	 * the index -1.
	 * <p>
	 * For example , given the list [5 ,3 ,0 ,9 ,2] and value 0 this
	 * method should return 2.
	 *
	 * @param list the list to search
	 * @param value the value to search for
	 * @return the index of the first o c c u r r e n c e of the given
	 * value or -1 if no o c c u r r e n c e is found .
	 */
	public static int firstIndex ( List <String> list , String value ){
		int ans = -1;	
		if (list.isEmpty()){
			return ans;
		}		

		for(int y = 0; y < list.size()-1; y++){
			String cur = list.get(y);
			if ( cur.equals(value) ){
				return y;
			}
		}
		return ans;
	}// end method
	public static void main(String[] args){
		List<String> t = new LinkedList<>();
		String[] t1 = {"Hello","World","lol"};
		for(int x = 0; x < t1.length; x++){
			t.add(t1[x]);
		}

		System.out.println(""+(firstIndex(t,"World")) );
	}
}
