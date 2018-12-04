// No imports
import java.util.Scanner;
import java.util.Arrays;
//DELTE AFTER FINISHED!!

/**
 *
 * @author Brett Kriz
 */
public class Recursion {
    

    public static long maximum(long [] array){
        return maximum(array, 0, 0);
    }
    private static long maximum(long [] array, long max, int index){
        if (index < array.length && 1 < array.length){
            
            if (array[index] > max){
                max = array[index];
            }
            
            index++;
            if (index == array.length){
                return max; // we have arrived
            } else {
                return maximum(array, max, index);
            }
            
        } else {
            return Integer.MAX_VALUE;
        }
    }
    public static long minimum(long [] array){
        return minimum(array, Integer.MAX_VALUE, 0);
    }
    private static long minimum(long [] array, long min, int index){
        if (index < array.length && 1 < array.length){
            
            if (array[index] < min){
                min = array[index];
            }
            
            index++;
            if (index == array.length){
                return min; // we have arrived
            } else {
                return minimum(array, min, index);
            }
            
        } else {
            return Integer.MAX_VALUE;
        }
    }
    public static boolean isIncreasing(double [] array){
        if (array.length < 2){
            return true;
        } else {
            return isIncreasing(array, 1);
        }
    }
    private static boolean isIncreasing(double [] array, int index){
        //                  if past less than present
        if (index > 0 &&  !(array[index-1] < array[index])){
            return false;
        }

        index++;
        if (index == array.length){
            return true;
        } else {
            return isIncreasing(array, index);
        }
    }
    
    public static boolean isDecreasing(double [] array){
        if (array.length < 2){
            return true;
        } else {
            return isDecreasing(array, 1);
        }
    }
    private static boolean isDecreasing(double [] array, int index){
        //            if past greater than present
        if (index > 0 && !(array[index-1] > array[index])){
            return false;
        }
        
        index++;
        if (index == array.length){
            return true;
        } else {
            return isDecreasing(array, index);
        }
    }
    public static int[] accumulate(int[] array){
        return accumulate(array, 0, 0);
    }
    private static int[] accumulate(int[] array, int index, int total){
        if (index < array.length && array.length > 0){
            total += array[index];
            index++;
            return accumulate(array, index, total);
        } else {
            int[] ans = array;
            ans[index-1] = total;
            return ans;
        }
    }
    public static void sort(int[] array){
        if (array.length > 1){
            sort(array, 0); // Smallest to largets
        } else {
            // Not long enough
        }
        
    }
    private static void sort(int[] array, int index){
        
        if (index < array.length-1){

            sort2(array, index, 0);
            
            index++;
            sort(array, index); // Next itderation will catch it
        }
    }
    private static void sort2(int[] array, int start,  int index){
        if (index < array.length-start-1){
            if ( array[index] > array[index+1] ){
                swap(array, index, index+1);
            }
            index++;
            sort2(array, start, index);
        }
    }
    private static void swap(int[] tbl, int one, int two){
        int temp = tbl[one];
        tbl[one] = tbl[two];
        tbl[two] = temp;
    }
    public static double pi(){
        return pi(0, 1 ,0)*1;
    }
    private static double pi(int x,int n, double pi){
        
         x++; // Must start at 1
        boolean b1 = (x%2 == 1);

        if (b1){
            //pi += 4.0 / (n * (n + 1.0) * (n + 2.0));
            pi += 4.0 / n;
        } else {
            //pi -= 4.0 / (n * (n + 1.0) * (n + 2.0));
            pi -= 4.0 / n;
        }
        n += 2;
        //
        if ( x < 4200 ){
            return pi(x, n, pi);
        } else {
            return pi;
        }
    }
    
    public static double euler(double n){ // 
        n = Math.max(1.0, Math.min(10000.0, n)); // Sudo clamp
        int x = 1; // The first 2 steps = 2
        double e = 2.0;
        return euler(x, n, e, 1.0);
    }
    // Recursive style
    private static double euler(int x, double n, double e, double term){  
        if ( (x < n) && (term > 0.001) ){
            term += term*(double)x;
            e += (1.0/term);
            x++;
            return euler(x,n-1,e,term);
        }else{
            return e; // break the cycle
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                  MAIN
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void main(String[] args){
        
        out("PI:\t"+pi());
        out("e:\t"+euler(100));
        out();
        
        System.out.println("Palindrome Tests");
        String t1 = "Otto";
        String t2 = "Mike";
        String t3 = "xxyyAyyxx";
        String b = " is palindrome?: ";
        String a = " -> ";
        
        
        out(t1 + b + isPalindrome(t1));
        out(t2 + b + isPalindrome(t2));
        out(t3 + b + isPalindrome(t3));
        out();
        
        int[] act = {2,1,1,2};// 6
        out(Arrays.toString(act) +a+ Arrays.toString(accumulate(act)));
        out();
        
        double[] inc = {1., 2, 2.3, 4., 6., 7.5, 7.8, 50.};
        double[] dec = {100.0, 9.9, 9.1, 8.6, 5., 4., 2., 1.};
        double[] flat = {5,5,5,5};
        
        out("Increasing: "+isIncreasing(inc));
        out("~~~~~~~~");
        out("Decreasing: "+isDecreasing(dec));
        out();
        out("NOT Increasing: "+ !isIncreasing(dec));
        out("~~~~~~~~");
        out("NOT Decreasing: "+ !isDecreasing(inc));
        out();
        
        long[] ltbl = {7, -1337, 25, 5, -35, 1337, 1};
        int[] itbl = {7, -1337, 25, 5, -35, 1337, 1};
        
        out("MAX = "+maximum(ltbl));
        out("MIN = "+minimum(ltbl));
        out();
        
        
        out("Unsorted:\t"+Arrays.toString(itbl));
        out();
                sort(itbl);
        out("Sorted:\t"+Arrays.toString( itbl ));
        out();
        
        // Finally, reversal
        String t = "The Brown Fox";
        out("FORWARD: >" + t );
        out("BACKWARD: >" + reverse(t) );
        out();
        
        
    }
    private static void out(){
        System.out.println(" ");
    }
    private static void out(double arg){
        System.out.println(""+arg);
    }
    private static void out(Object arg){
        System.out.println(""+arg.toString());
    }
    public static boolean isPalindrome(String string){
        int half = (string.length())/2;
        int frontl;
        int backl;
        String front = string.substring(0, half).toLowerCase();;
        String back;
        
        if ( string.length() % 2.0 == 0 ){
            back = string.substring(half).toLowerCase();
        } else {
            back = string.substring(half+1).toLowerCase();
        }
        
        if ( front.length() != back.length() ){
            return false; //Cant be!
        }
        return isPalindrome(front, back, 0);
    }
    private static boolean isPalindrome(String front, String back,int index){
        if ( index < front.length() && (0 < front.length()) ) {
            int len = back.length()-1;
            if ( front.charAt(index) == back.charAt(len-index) ) {
                index++;
                if (index == front.length()){
                    return true; // Done
                } else {
                    return isPalindrome(front, back, index);
                }  
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static String reverse(String string){
        return reverse(string, "", 0);
    }
    private static String reverse(String string, String backward, int index){
        if (index < string.length() && string.length() > 1){
            backward = string.charAt(index) + backward;
            index++;
            
            if (index == string.length()){
                return backward;
            } else {
                return reverse(string, backward, index);
            }
        } else {
            return string; // too short
        }
    }
        
}// END CLASS
