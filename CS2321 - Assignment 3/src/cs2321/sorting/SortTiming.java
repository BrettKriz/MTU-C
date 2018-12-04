package cs2321.sorting;

/**
 * A test driver for Sorts.
 * 
 * Course: CS2321 Section ALL
 * Assignment: #4
 * @author Chris Brown (cdbrown@mtu.edu)
 */

public class SortTiming {

	public static void main(String [] args){
		
		//#Examples of using testSort
		Integer[] testee = {5, 3, 7, 2, 9, 3};
		double avgtime = testSort(testee, new InsertionSort<Integer>());
		
		System.out.println("Insertion: " + avgtime);
		
	}

	/**
	 * Algorithm: testSort
	 * @param arr - an array of Integers to use for empirical measurement of a sort
	 * @param sortClass - the Class representing the sorting algorithm to be run
	 * @param iterations - the number of times the sort is repeated
	 * @return average time taken for a single execution of a sort (in nanoseconds)
	 * 
	 * A copy (clone) of the array is made to test over, so that the original may be reused.
	 */
	public static double testSort(Integer[] arr, Sorter<Integer> sortClass){
		long startTime = 0, endTime = 0;
		int samples = 0;

		System.gc();
		startTime = System.nanoTime();
		//#repeated measurements (no less than .5 seconds worth of repeats)
		do{
			//create a copy of the array for each test case
			Integer[] testCase = arr.clone();
			//the sorting algorithm, based on the Sorter Class
			sortClass.sort(testCase);
			
			samples++;
			endTime = System.nanoTime();
		}while(endTime - startTime < 500000000);
				
		return (double)(endTime - startTime) / samples;
	}
	
}
