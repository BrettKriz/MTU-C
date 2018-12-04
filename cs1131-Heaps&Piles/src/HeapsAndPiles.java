import java.util.*;

/**
 *
 * @author Brett Kriz
 */
public class HeapsAndPiles {
    /*
        Balanced trees via Arrays
        
        diff between min and max height is at most 1 (BALANCED TREE) [<= 1]
        
        Complete Binary Tree: One that can be read left to right without gaps        
        un-even trees dont count
        #Completeness
        
        ~~~ HEAP ~~~~ WILL BE ON EXAM
            Binary          -easy
            Complete        -1st
            Root > children -2nd
    
        (Similar to a priority queue, best thing at root)
            add     (enqueue) O(Log(N))
            remove  (dequeue) O(Log(N))
            best    (element) O(1)
    
            15
          7    8
        6 4   1 10
        
        SWAP to correct tree
            swap 10 & 8
        ~~~~~~~~~~~~~~~~
            15
          7    8
        6 4   1 200
        Swap 200 & 8
        Swap 200 & parent (15)
        NOW, when 15 drops down, we already know that
            it was bigger than everything else
        Worst case runtime is Log(N), BC balanced and only compairing
            to everything above leaf
        ~~~~~~~~~~~~~~~~~
            200
          7    15
       6  4   1 8
     2 3  0
        To REMOVE: Swap with root, then bubble down while needed
    ~~~~~~~~~~~~~~~~
    
            0
          1   2
        3 4  5 6
    [0,1,2,3,4,5,6]
     ^Root
    Complete, no gaps
    
    TO FIND CHILDREN IN ARRAY: 
        lChild = 2x+1
        rChild = 2x+2
    TO FIND PARENT IN ARRAY:
        (x-1)/2 (INT DIVISION)
    
    
    */
    public static void main(String[] args){
        Heap heap = new Heap();
        heap.add(4,6,2,7,8,3,0,1,15); // add with 9 int args will be called over variatics
        //heap.add(new int[] {4,6,2,7,8,3,0,1,15});
        heap.add(30);
    }

}

class Heap{
    private List<Integer> heap = new ArrayList<Integer>();
    private int lChild(int x){
        return 2x+1;
    }
    private int rChild(int x){
        return 2x+2;
    }
    
    private int parent(int x){
        return (x-1)/2;
    }
    
    public void add(int val){
        int index = heap.size();
        heap.add(val);
        //Bubble up
        while( index > 0 && heap.get(index) > heap.get(parent(index)) ){
            //SWAP is standard in collections
            Collections.swap(heap, index, parent(index));
            index = parent(index);
        }
    }
    public void add(int... vals){
        // VARARGS = Variatic Argument
        // Can only be at end
        //Vals is iterable on int
        for(int v : vals){
            add(v);
        }
    }
    
    private int get(int index){
        return heap.get(index);
    }
    public int remove(){
        // Swap final with new location
        Collections.swap(heap, 0, heap.size() - 1);
        int ret  = heap.remove(heap.size() - 1);
        int index = 0;
        
        while(true){
            if(lChild(index) >= heap.size()){
                break; // no kids
            }
            if (rChild(index) >= heap.size()){
                if (get(lChild(index)) > get(index)){
                    Collections.swap(heap, lChild(index), index);
                }
                break; // @ END
            }
            if(get(rChild(index)) > get(lChild(index))){
                if(get(rChild(index)) > get(index)){
                    Collections.swap(heap, rChild(index), index);
                    index = rChild(index);
                    continue;
                }
                break;
            }
            if(get(lChild(index)) > get(index)){
                Collections.swap(heap, lChild(index),index);
                index = lChild(index);
            }
        }
        // He might have deleted this portion below
        
        // BUBBLE
        while(get(index) < Math.max(get(lChild(index)), get(rChild(index))) ){
            if (get(lChild(index)) > get(rChild(index)) && get(lChild(index)) > get(index)){
                Collections.swap(heap, index, lChild(index));
                index = lChild(index);
            }else{
                Collections.swap(heap, index, rChild(index));
                index = rChild(index);
            }
        }
        
        
        return ret;
    }
    public int element(){
        return 0;
    }
    
    public String toString(){
        return heap.toString();
    }
            
}