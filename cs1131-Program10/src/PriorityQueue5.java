import java.util.Iterator;

/**
 * An unbounded minimum priority queue based on a priority heap.
 * @param <E>
 */
public class PriorityQueue<E extends Comparable> implements Iterable<E> {
        //protected List<E> backing;
        private E[] queue;
        private int size  = 0;
        //private E best;
        // Backed by heap!!!

	/**
	 * Creates a PriorityQueue.
	 */
	//public PriorityQueue(List<E> backing) {
        //    this.backing = backing;
        //    best = null;
        //}
        public PriorityQueue(){
            this.queue = ((E[])new Comparable[10]);
        }
	
	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @param e the element to add
	 */
	public void enqueue(E e) {
            if (e == null){
                throw new NullPointerException();
            }
            
            if (size >= queue.length){
                // We need to resize!
                this.queue = resize(queue, size*2);
            }
            if (size > 0){
                // Find its place, size ,e
                int k = size;
                while(k > 0){
                    int parent = (k - 1) >>> 1;
                    E x = queue[parent];
                    
                    // Find the right position
                    if (x != null && e.compareTo(x) >= 0){
                        break;
                    }

                    queue[k] = e;
                    k = parent;
                }
            }else{
                queue[size] = e;
            }
            size++;
	}
        
        /**
         * Resize the given array
         * 
         * @param elems
         * @param newsize
         * @return array of new size
         */
        private E[] resize(E[] elems, int newsize){
            if(newsize < 0){
                throw new IllegalArgumentException();
                // UP FOR CHANGE
            }
            E[] newelems = (E[])(new Comparable[newsize]);
            if (elems == null){
                return newelems;
            }
            // Add elements back in
            System.arraycopy(elems, 0, newelems, 0, Math.min(elems.length, newsize));
            
            return newelems;
        }
	
	/**
	 * Retrieves and removes the head of this queue, or returns null if this
	 * queue is empty.
	 *
	 * @return the head of this queue, or null if this queue is empty
	 */
	public E dequeue() {
            if (size == 0){
                return null;
            }
            
            E ret = queue[0];
            for(int x = 1; x < size; x += 1) {
                queue[x - 1] = queue[x];
            }
            size--;
            return ret;
	}
	
        
	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null
	 * if this queue is empty.
	 *
	 * @return the head of this queue, or null if this queue is empty
	 */
	public E element() {
            if (size == 0){
                return null;
            }
            return queue[0];
	}
	
	/**
	 * Returns an iterator over the elements in this queue. The iterator must
	 * return the elements of the priority queue in heap order. Heap order is 
	 * defined as the root, followed by the root's children, followed by the 
	 * root's grandchildren, etc., always in left to right order.
	 *
	 * @return a heap order iterator
	 */
        @Override
        public Iterator<E> iterator() {
            // Yup.
            Iterator<E> ans = new Iterator<E>(){
                private int curIndex = 0;
                
                @Override
                public boolean hasNext(){
                    return curIndex < size && queue[curIndex] != null;
                }
                @Override
                public E next(){
                    return queue[curIndex++];
                }
                public void remove(){
                    // No.
                }
            };
            return ans;
        }
        public boolean isEmpty() {
            return (size <= 0);
	}
	
	public int size() {
            return size;
	}

}