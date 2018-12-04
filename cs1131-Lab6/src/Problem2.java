import java.util.Iterator;
/**
 *
 * @author Brett Kriz
 */
public class Problem2 {
    /**
    * Returns an Iterator which iterates through only the first
    * N values of the specified iterator.
    * <p>
    * For an list = [1, 2, 3, 4, 5, 6, 7, 8] and N = 3 the resulting
    * iterator will iterate through only the elements 1, 2, 3.
    *
     * @param <T>
    * @param iterator the iterator to summarize
    * @return the summary iterator
    */
    public static <T> Iterator <T> take(Iterator <T> iterator , int n){
        class FirstN implements Iterable<T>{
            public Iterator <T> iterator = null;
            protected int times = 0;
            
            public FirstN(Iterator <T> iterator , int times){
                this.iterator = iterator;
                this.times = times+1;
            }
            
            @Override
            public Iterator<T> iterator(){
                if (times < 1){
                    // nope!
                    return null;
                }
                times--;
                return new Iterator<T>() {
                    private int count=0;
                   
                    @Override
                    public boolean hasNext(){                        
                        return iterator.hasNext() &&  count < times;
                    }
                    @Override
                    public T next(){
                        count++;
                        return iterator.next();
                    }

                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException("NO! Thats not B roll! THATS TOO SPECIFIC!");
                    }
                };
                
            }
        }
        return new FirstN(iterator, n).iterator();
    }
}
