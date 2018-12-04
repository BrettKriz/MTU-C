import java.util.Iterator;
/**
* Lab 6 - Problem 1
*
* @author Brett Kriz
*/
public class Problem1 {
    /**
    * Returns an Iterable which iterates through an array the
    * specified number of times.
    * <p>
    * For an array = [1, 2, 3, 4] and n = 3 the iterated values
    * would be 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4.
    *
    * @param array an integer array
    * @param n the number of times to iterate through the array
    * @return the array cycling iterable
    */
    public static Iterable <Integer> cycList(Integer [] array , int n){
        class TimesArray implements Iterable<Integer>{
            public Integer[] arr = null;
            //protected int times = 0;
            protected int total = 0;
            
            public TimesArray(Integer[] arr, int times){
                this.arr = arr;
                //this.times = 0;
                this.total = times * arr.length-0;
            }
            
            @Override
            public Iterator<Integer> iterator(){

                //times--;
                return new Iterator<Integer>() {
                    private int count=0;
                   
                    @Override
                    public boolean hasNext(){      
                        return count < total;
                    }
                    @Override
                    public Integer next(){
                        int len = arr.length-0;
                        int x = (count % len + len) % len;
                        count++;
                        return arr[x];
                    }

                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException("NO!");
                    }
                };
                
            }
        }
        
        return new TimesArray(array,n);
    }
    public void main(String[] args){
        
    }
}
