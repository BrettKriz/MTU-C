package cs2321.sorting;

import cs2321.OrderedPQ; // EC
import cs2321.TimeComplexity;
import cs2321.SpaceComplexity;
@SpaceComplexity("O(n)")
public class InsertionSort<K extends Comparable<K>> extends PQSort<K> implements Sorter<K> {

	/**
        * sort - Perform an PQ sort using an OrderedPQ
        * @param array - Array to sort
        */
        @TimeComplexity("O(n^2)")
	public void sort(K[] array){
		//super.sort(array, new OrderedPQ<K,K>());
                this.InsertionSort(array);
	}
        
        @TimeComplexity("O(n^2)")
        public void InsertionSort(K[] A){
            if (A.length < 2){
                return;
            }
            int n = A.length;
            
            for (int i = 1; i < n; i++){
                K cur = A[i];
                int j = i-1;
                while (j >= 0 && A[j].compareTo(cur) > 0){
                    A[j+1] = A[j];
                    j=j-1;
                }
                A[j+1] = cur;
            }
        }
}
