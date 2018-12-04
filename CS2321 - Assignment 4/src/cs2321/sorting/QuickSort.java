package cs2321.sorting;

public class QuickSort<E extends Comparable> implements Sorter<E> {

    public void sort(E[] array) {
            // TODO Auto-generated method stub
        QuickSort(array, 0, array.length-1);
    }
        
    public E[] QuickSort(E[] A, int p, int r){
        
        if (p < r){
            int q = this.Partition(A, p, r);
            QuickSort(A, p, q-1);
            QuickSort(A, q+1, r);
        }
        
        return A;
    }
    public int Partition(E[] A, int p, int r){
        E x = A[r];
        int i = p-1;
        
        for (int j = p; j < r; j++){
            E cur = A[j];
            if (cur != null && cur.compareTo(x) != 1 ){
                i = i+1;
                swap(A,i,j);
            }
        }
        // Last swap
        swap(A,r,i+1);
        return i+1;
    }
    private void swap(E[] A, int x, int y){
        E t = A[x];
        A[x] = A[y];
        A[y] = t;
    }
}
