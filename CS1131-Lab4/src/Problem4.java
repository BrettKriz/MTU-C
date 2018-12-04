import java.util.*;// for debug only!
/* *
* Lab 4 - Problem 4
*
* @author Brett Kriz
*/
public class Problem4{
	/* *
	* Moves all the even e l eme n ts in list1 on the end of list0
	* ( in the same order ) and all the odd e l e m e n t s in list0
	* on the end of list1 ( in the same order ).
	* <p >
	* For example , if list0 c o nt a i n s [1 , 2 , 3 , 4] and list1
	* con ta i n s [5 , 6 , 7 , 8] then list0 should contain [2 , 4 , 6 , 8]
	* and list1 should contain [5 , 7 , 1 , 3] upon c o m p l e t i o n .
	*
	* @param list0 the ’ even ’ array
	* @param list1 the ’ odd ’ array
	*/
	public static void split ( List < Integer > list0 , List < Integer > list1 ){

		if (list0.isEmpty() && list1.isEmpty()){
			return;
		}

		// Work with evens list
		for(int x = 0; x < list0.size()-1; x++){
			Integer cur = list0.get(x);
			if ((double)cur%2.0 != 0.0){ // Is odd
				list0.remove(x);
				list1.add(cur);
			}
		}
		// Work with odds list
		for(int y = 0; y < list1.size()-1; y++){
			Integer cur = list1.get(y);
			if ((double)cur%2.0 == 0.0){ // Is even
				list1.remove(y);
				list0.add(cur);
			}
		}
	}// end method
	public static void main(String[] args){
	/*
		List<Integer> t0 = new List<>({'a',' ','v',' ',' ',' ','h'})
		List<Integer> t1 = new List<>(
		for(int x = 0; x < t.size()-1; x++){
			System.out.println(t.get(x));
		}
	*/
	}
}
