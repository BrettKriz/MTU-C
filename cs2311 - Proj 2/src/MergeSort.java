
public class MergeSort<E extends Comparable<E>>{

	public void sort(E[] array) {
	    // find median
            this.MergeSort(array, 0 , array.length-1 );
	}
    public E[] MergeSort(E[] A, int p, int r){
        if (p < r){
            int q  = (int)Math.floor( (p+r)/2 );
            MergeSort(A, p, q);
            MergeSort(A, q+1, r);
            Merge(A,p,q,r);
        }
        return A;
    }
    public E[] Merge(E[] A, int p, int q, int r){
        int n1 = q-p+1;
        int n2 = r-q;
        E[] L = (E[])new Comparable[n1];
        E[] R = (E[])new Comparable[n2];
        
        for (int i = 0; i < n1; i++){
            L[i] = A[p+i];
        }
        for(int i = 0; i < n2; i++){
            R[i] = A[p+1+i];
        }
        
        int i = 0, j = 0, k = 0;
        while (i < n1 && j < n2){
            if ( L[i].compareTo( R[j]) != 1 ){
                A[k] = L[i];
                i = i+1;
            }else{
                A[k] = R[j];
                j = j+1;
            }
            k = k+1;
        }
        
        while (i < n1){
            A[k] = L[i];
            i = i+1;
            k = k+1;
        }
        while (j < n2){
            A[k] = R[j];
            j = j+1;
            k = k+1;
        }
        return A;
    }
    
    public E[] Merge2(E[] A, int p, int q, int r){
        int n1 = q-p+1;
        int n2 = r-q;
        E[] L = (E[]) new Comparable[n1+1];
        E[] R = (E[]) new Comparable[n2+1];
        
        for (int i = 0; i < n1; i++){
            L[i] = A[p+i];
        }
        for(int i = 0; i < n2; i++){
            R[i] = A[p+1+i];
        }
        return null; // WIP
    }
}

