package cs2321.sorting;

import java.util.Arrays;

/**
 * A test driver for Sorts.
 * 
 * Course: CS2321 Section ALL
 * Assignment: #4
 * @author Chris Brown (cdbrown@mtu.edu)
 * @author Brett Kriz
 */

public class SortTiming {
        public static int SIZE_OF_ARRAY = 500;
        public static final int ITERATIONS = 1;
    
	public static void main(String [] args){
		
            //#Examples of using testSort
            int[] SIZES = {10,25,50,100,1000,2000,4000,8000,16000,32000,64000,128000};
            String[] n = {"Insertion (OrderedPQ)","Selection (unorderedPQ)","Heap","QuickSort","MergeSort"};
            
            double[] t = new double[3*ITERATIONS];
            int s = 0;
            int x = 0;
            int z = 0;
            boolean fullRun = true;
            // CASES:
            do{
                SIZE_OF_ARRAY = SIZES[z];
                Integer[] A = gen();
                // INSERTION:
                while(x < ITERATIONS){
                    t = new double[5];
                    s = 0;
                    //t[s++] = test(n[0],     A , new InsertionSort<Integer>()); // n - n^2
                    //t[s++] = test(n[1],     A , new SelectionSort<Integer>()); // n^2
                    t[s++] = test(n[2],     A , new cs2321.sorting.HeapSort<Integer>()); // N LOG N
                    //t[s++] = test(n[3],     A , new QuickSort<Integer>()); // N LOG N - N^2
                    //t[s++] = test(n[4],     A , new MergeSort<Integer>()); // N LOG N

                    System.out.println();
                    Arrays.toString(t);
                    System.out.println("#" + x + "=============DONE=============\n");

                    x++;
                };
                x=0;
                //System.out.println();
                System.out.println("<><><>\t SIZE " + SIZE_OF_ARRAY + " COMPLETED ABOVE!\t<><><>");
                System.out.println();
                
                z++;
            }while (z < SIZES.length && fullRun);
	}
        public static Integer[] gen(){
            return createTestArray( SIZE_OF_ARRAY );
        }
        public static double test(String title, Integer[] a, Sorter<Integer> sclass){
            // STREAMLINED METHOD
            double t = 0;
            // CASES:
            // INSERTION:
            t = testSort(a, sclass);
            System.out.println( title.toString() + ": " + t + "ms\t= " + t/1000000000. + "s");
            return t;
        }
        public static Integer[] createTestArray(int n){
            if (n <= 0){
                return new Integer[0];
            }
            java.util.Random rand = new java.util.Random();
            Integer[] ans = new Integer[n];

            for (int x = 0; x<n; x++){
                ans[x] = rand.nextInt(100000);
            }
            return ans;
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
