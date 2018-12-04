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
public class UnorderedPQ<K extends Comparable<K>,V> extends PriorityQueueBase<K,V> implements MaxPriorityQueue<K,V>{
        protected QEntry<K,V> max = null;
        protected Integer maxI;
        
        @TimeComplexity("O(1)")
        public UnorderedPQ(){
            super();
        }
        
        @Override @TimeComplexity("O(1)")
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
            // Im going to assume this isn't a REAL insert, just an ADD
            if (key == null){
                throw new InvalidKeyException("NULL KEY");
            }
            
            //Val check
            QEntry<K,V> arg = new QEntry<K,V>(key, value);
            checkMax(arg);
            
            this.backing.addLast(arg);
            
            return arg;
	}
        
        @TimeComplexity("O(1)")
        private boolean checkMax(QEntry arg){
            if (arg == null){
                return false;
            }else if ( max == null  ){
                this.max = arg;
                this.maxI = arg.index;
                return true;
            }
            if ( arg.getKey().compareTo( max.getKey() ) > 0 ){
               this.max = arg;
               return true;
            }else{
                return false;
            }
        }
        
        @Override @TimeComplexity("O(1)")
	public boolean isEmpty() {
            return super.isEmpty();
	}

        @Override @TimeComplexity("O(n)") @TimeComplexityExpected("O(n)")
	public Entry<K,V> max() throws EmptyPriorityQueueException { // O(N)
		// TODO Auto-generated method stub
                Entry<K ,V> t = this.backing.get( this.findMax() );
		return t;
	}

        @Override @TimeComplexity("O(n)")
	public Entry<K,V> removeMax() throws EmptyPriorityQueueException { // O(N)
            // CLEAN ME!
            if (this.max == null || this.size() <= 0){
                throw new EmptyPriorityQueueException("Either max is null or size is 0");
            }
            QEntry<K,V> ans = null;
            
            // Keep it running at O(N)
            int at = findMax();
            ans = this.backing.remove(at);

            return ans;
	}
        
        @TimeComplexity("O(n)")
        public int findMax(){
            // Find new max and maxI
            int z = 0;
            this.max = null;
            this.maxI = null;
            
            this.backing.repack();
            
            for (int x = 0; x < size(); x++){
                QEntry<K,V> cur = this.backing.get(x);
                
                if (cur == null){
                    continue;
                }
                
                cur.index = x;
                
                if (this.max == null){
                    this.max = cur;
                    this.maxI = x;
                }else if (this.max.getKey().compareTo( cur.getKey() ) < 0  ){
                    this.max = cur;
                    this.maxI = x;
                }
            }
            
            if (max == null){
                throw new Error("I CANT EVEN. >:(");
            }else{
                this.maxI = max.index;
            }

            int SSIZE = size();
            
            if (this.maxI == null || maxI < 0 || maxI >= SSIZE){
                Boolean debug = true;
                debug = false;
                throw new Error("I CANT EVEN!!!!!!!!!!!. >:(");
            }
            return this.maxI;
        }
        
        @Override @TimeComplexity("O(1)")
	public int size() {
            return super.size();
	}

}
