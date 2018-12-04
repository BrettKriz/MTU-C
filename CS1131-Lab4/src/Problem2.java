import java.util.*;// for debug only!
/* *
* Lab 4 - Problem 2
*
* @author Brett Kriz
*/
public class Problem2{
	public static final Double ZERO = 0.0;
	/**
	* Removes all the n e g a t i v e values from the given list .
	*
	* @param the list from which to remove negative values
	*/
	public static void removeNegatives ( List < Double > list ){
		if (list.isEmpty()){
			return;
		}
		int zsize = list.size();
		for(int z = 1; z < zsize; z++){
			for(int y = 0; y < list.size(); y++){
				Double cur = list.get(y);
				if (Double.compare(cur,ZERO) < 0){
					list.remove(y); // Resize
				}else{
					//System.out.println(list.get(y));
				}
			}
		}
	}// end method
	public static void main(String[] args){
		
		List<Double> t = new LinkedList<Double>();
		for(int x = -4; x < 5; x++){
			double z = (double)x;
			t.add((Double)z);
			//System.out.println(x);
		}
		//System.out.println();
		removeNegatives(t);
		//System.out.println("<><><>");
		for(int x = 0; x < t.size()-1; x++){
			//System.out.println(t.get(x));
		}
	}
}

