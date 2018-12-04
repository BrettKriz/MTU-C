package cs2321.sorting;

import cs2321.TimeComplexity;
import cs2321.UnorderedPQ; // EC unordered
import cs2321.SpaceComplexity;
@SpaceComplexity("O(n)")
public class SelectionSort<K extends Comparable<K>> extends PQSort<K> implements Sorter<K> {
	/**
	 * sort - Perform a PQ sort using an UnorderedPQ
	 * @param array - Array to sort
	 */
        @TimeComplexity("O(n^2)")
	public void sort(K[] array){
		//super.sort(array, new UnorderedPQ<K,K>());
                this.SlectionSort(array);
	}
        @TimeComplexity("O(n^2)")
        public void SlectionSort(K[] A){
            if (A.length < 2){
                return;
            }
            
            int n = A.length;
            for (int i = 0; i < n-1; i++){
                int minIndex = i;
                int j;
                //Search for lower
                for (j = i+1; j < n; j++){
                    if (A[j].compareTo( A[minIndex] ) < 0 ){
                        minIndex = j;
                    }
                }
                
                if (minIndex != j ){
                    //SWAP
                    K temp = A[i];
                    A[i] = A[minIndex];
                    A[minIndex] = temp;
                }
            }
        }
}
