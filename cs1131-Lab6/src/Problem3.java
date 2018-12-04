import java.util.Iterator;

/**
 *
 * @author Brett Kriz
 */
public class Problem3 {
    /**
    * Returns an Iterable which iterates through an array repeating
    * each element the specified number of times.
    * <p>
    * For an array = [1, 2, 3, 4] and n = 3 the iterated values
    * would be 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4.
    *
    * @param array an integer array
    * @param n the number of times to repeat each element
    * @return the array stuttering iterable
    */
    public static Iterable <Integer > stutter(Integer [] array , int n){
        class RepeatN implements Iterable<Integer>{
            public Integer[] arr = null;
            protected int times = 0;
            protected int reps = 0;
            private int count = 0;
            //protected int total = 0;
            
            public RepeatN(Integer[] array, int times){
                //this.arr = new Integer[array.length*times];
                this.arr = array;
                this.times = times-1;
                this.count = 0;
                this.reps = 0;
                /*
                for (int z = 0; z < array.length; z++){
                    for (int r = 0; r < times; r++){
                        this.arr 
                    }
                }
                //*/
                //this.total = times * arr.length-0;
            }
            
            @Override
            public Iterator<Integer> iterator(){
                
                //times--;
                return new Iterator<Integer>() {
                    
                   
                    @Override
                    public boolean hasNext(){      
                        return count < arr.length;
                    }
                    @Override
                    public Integer next(){
                        //int len = arr.length-0;
                        int x = (count);
                        
                        if (reps >= times){
                            reps = 0;
                            count++;
                        }else{
                            reps++;
                        }

                        return arr[x];
                    }

                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException("NO!");
                    }
                };
                
            }
        }
        
        return new RepeatN(array,n);
    }
    //
    public static void main(String[] args){
         Integer[] array = { 1, 2, 3, 4, 5 };
	 int n = 3;

        //List<Integer> test = new ArrayList<Integer>();
        for(Integer x : Problem3.stutter(array, n)) { 
            System.out.println(x);
        }
    }
    //*/
}
