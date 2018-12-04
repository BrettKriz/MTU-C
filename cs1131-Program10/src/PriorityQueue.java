

import java.util.Arrays;
import java.util.Iterator;

/**
 * An unbounded minimum priority queue based on a priority heap.
 * @param <E>
 */
public class PriorityQueue2<E extends Comparable> implements Iterable<E> {
    //protected List<E> backing;
    private Object[] queue;
    protected int size  = 0;
    //private E best;
    // Backed by heap!!!

    /**
     * Creates a PriorityQueue.
     */
    //public PriorityQueue(List<E> backing) {
    //    this.backing = backing;
    //    best = null;
    //}
    public PriorityQueue2(){
        this.queue = ((E[])new Comparable[12]);
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

        int x = size;
        size++;

        if (x+1 >= queue.length-1){
            this.queue = resize((E[])queue, queue.length*2);
        }
        //size = size + 1;
        if (x == 0){
            queue[0] = e;
        }else{

            if (size == queue.length){
                resize((E[])queue, queue.length*2);
            }
            queue[findNull()] = e;
            shift();

        }
    }
    public void offer(E e){
        enqueue(e);
    }
    private int findNull(){
        if (queue.length < 1){
            throw new IllegalArgumentException("Bad Array!");
        }
        int x;

        for(x = 0; x <= queue.length; x++){
            if (x == queue.length){
                // Resize then.
                this.queue = resize((E[])queue, queue.length*2);
                x--;
                continue;
            }
            Object cur = queue[x];
            if (cur == null){
                break;
            }
        }

        return x;
    }

    private void shift(){
        // Reduce gaps to retain lifo
        if (queue.length < 1){
            throw new Error("Table self destruction!");
        }
        if (size <= 0){
            return;
        }

        if (queue[0] == null){
            E best = null;
            int bestAT = -1;
            for (int x = 0 ; x < queue.length; x++){
                E cur = (E)queue[x];
                if (cur == null){
                    continue;
                }
                if (best == null || cur.compareTo(best) > 0){
                    best = cur;
                    bestAT = x;
                }
            }
            // Not put best at front;
            queue[bestAT] = null;
            queue[0] = best;
        }

    }

    /**
     * Resize the given array
     * 
     * @param elems
     * @param newsize
     * @return array of new size
     */
    private Object[] resize(E[] elems, int newsize){
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


        E result = (E) queue[0];
        queue[0] = null;
        --size;

        if (size != 0){
            shift();
        }
        return result;
    }
    public E poll(){
        return dequeue();
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
        return (E)queue[0];
    }

    public void fixArray(){
        // It gets just... so messed up...
        if (size == 0 || queue.length < 1){
            return;
        }
        E[] newer = ((E[])new Comparable[queue.length]);

        int x = 0;
        for(Object cur : queue){
            if (cur != null){
                newer[x++] = (E)cur;
            }
        }

        this.queue = newer;
        this.size = x;

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
                return (E)queue[curIndex++];
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