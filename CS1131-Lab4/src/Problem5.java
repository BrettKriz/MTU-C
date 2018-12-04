import java.util.*;// for debug only!
/* *
* Lab 4 - Problem 5
*
* @author Brett Kriz
*/
public class Problem5{
	/* *
	* Squash c o n s e c u t i v e space c h a r a c t e r s into a single space .
	*
	* @param text the list in which to squash c h a r a c t e r s
	*/
	public static void cleanup2 (List<Character> text){
		if (text.size() < 2){
			return;//done here
		}
		
		boolean spaceFlag = false;// It waves even without wind D:
		String str = "";
		String targ = "                 ";
		for(int x = 0; x < text.size(); x++){
			str += ""+text.get(x);
			
			
		}// end for
		//text.clear();
		for(int y = 0; targ.length() >= 2; y++){
			str.replaceAll(targ,"");
			targ = targ.replaceFirst(" ","");
		}
		//rencode
		for(int z = 0; z < str.length(); z++){
			text.set(z, (Character)str.charAt(z) );
		}
		
		
		System.out.println(str);
		System.out.println();
	}
	public static void cleanup ( List <Character> text ){
		if (text.size() < 2){
			return;//done here
		}
		Character last = text.get(0);
		//text.remove(0);

		for(int x = 1; x < text.size(); x++){
			Character cur = text.get(x);
			if ( last.equals((Character)' ') && cur.equals((Character)' ') ){
				last = cur;
				text.remove(x);
			}else{
				last = cur;
			}
		}
	}// end method
	public static void main (String[] args){
	
		Character[] a = {'a',' ','v',' ',' ',' ','h'};
		//List<Character> t = Arrays.asList(<> a);
		List<Character> t = new LinkedList<>();
		t.add('a');
		t.add(' ');
		t.add(' ');
		t.add('v');
		t.add(' ');
		t.add(' ');
		t.add(' ');
		t.add('d');
		
		System.out.println();
		cleanup(t);
		for(int x = 0; x < t.size(); x++){
			System.out.print(t.get(x));
		}
	
	}

}
