package cs2321;

import net.datastructures.*;
/**
 * A PriorityQueue based on an Unordered Sequence. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author
 */
@SpaceComplexity("O(n)")
public class OrderedPQ<K extends Comparable<K>,V> extends PriorityQueueBase<K,V> implements MinPriorityQueue<K,V> {
        private QEntry<K,V> min;
        private Integer minI;
    
        @TimeComplexity("O(1)")
        public OrderedPQ(){
            super();
        }
        
        @Override @TimeComplexity("O(n)")
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
            QEntry<K,V> arg = new QEntry<K,V>(key, value);
            int n = backing.size();
            int x = 0;
            if (n == 0){
                arg.index = 0;
                this.backing.addLast(arg);
                this.min = arg;
                this.minI = x;
                return arg;
            }
            
            // TCJ: the insertions are placed in the array then sorted, so O(N)
            
            for (; x < n; x++){
                QEntry<K,V> cur;
                cur = this.backing.get(x);
                
                if (cur != null && key.compareTo( cur.getKey() ) > 0 ){
                    break;
                }
            }

            // Min goes toward END
            arg.index = x;
            
            
            if (x >= n){
                this.backing.addLast(arg);
                this.min = arg;
                this.minI = x;
                
            }else{
                backing.add(x, arg);
            }
            return arg;
	}

        @Override @TimeComplexity("O(1)")
	public boolean isEmpty() {
            return super.isEmpty();
	}

        @Override @TimeComplexity("O(1)")
	public Entry<K,V> min() throws EmptyPriorityQueueException {
            return backing.get( size()-1 ); // backing.size() - 1
	}

        @Override @TimeComplexity("O(1)")
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
            if (this.isEmpty()){
                throw new EmptyPriorityQueueException("No more items!");
            }
            // TCJ: to avoid using backings adjustment process,
            // the array is backward, so that the min is always
            // at the end. All sorting is therefor handled by insert
            // so o(1) 
            return backing.remove( size()-1 );
	}

        @Override @TimeComplexity("O(1)")
	public int size() {
            return super.size();
	}
}
