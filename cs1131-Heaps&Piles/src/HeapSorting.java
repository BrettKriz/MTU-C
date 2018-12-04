
/**
 * Heap sort
 * 
 * @author Brett Kriz
 */
public class HeapSorting {
    /*
      Priority Queue -> Sort Function
        Sorted  Unsorted    Heap
    A     O(N)      O(1)    O(Log N)
    R     O(1)      O(N)    O(Log N)
    S     O(N^2)   O(N^2)   O(N Log N) // based on above
    
        for val : array
            PQ.add(val)
    
        for x in [0,N]
            array[x]=PQ.remove()
    
    ~~~ HEAP SORT IN PLACE ~~~~
        Split array into 2 parts
        [ | | | | | | ]
      PQ  | unadded
        Add to the first space of unadded
        Shift partition over and all other elements
        
    When we get to the end, everythings in PQ
    SO now we reverse
        Swap what we wantto remove to the place we want rto remove it
         \/----------\/
        [ | | | | | | ]
       PQ           | Sorted
        
        Heapify: array -> heap // Yes tetxtbooks call it this
        
        ~~~ MERG SORT ~~~ // Works opposite of quick sort
             O(N LogN)
        Start halfing until leaves
        Arrays of 3 or 2 get sorted then merged
        
        [ | | | | | | | ]
                | 1. split in half O(N)
        then half thosse arrays     O(N)
        [ | | | ] [ | | | ]            
            |         |
        and now sort for base caes, return: EACH LEVEL COSTS O(N)
            Height is O(Log N)
        SO, compare the first items, compare for lesser,
        keep pointer on looser of compairison
        
        Do it for each segment up until the top
        
        "Any sort that relies on compairsons and swaps is at best O(N Log N)
    
        ~~~ Merg sort can be done in place, but its hard ~~~
            
    
        ~~~ Bucket Sort ~~~
            Integers [0,256)    O(N)
            Create 256 buckets  
            incrementing each repective bucket for each of that number
            [ | | | ... | | | ]
            K = 256
                O(N) + O(N) + O(K) = O( N + K )
                // K is unknown at calulation time, SO
                O(N + 8 ) = O(N)
                O( MAX(N, K) )
    
            THEN, you can put them back in order
            
        What about when we want only a certian number of buckets?
            Integers [0, 256)
            8 buckets, 32 numbers in each
            [ | | | | | | | ]
             ^
           [0,32)
    
           Fill buckets with lists each containing unsorted occurences
           
            1 to 1 buckets, the trade off is memory for speed O(N)
                otherwise its going toward O(N^2) runtime
    
        Radix : base of a number
    
            When we have a fixed Radix, we can use a special type of bucket sort
                called
    
        ~~~ RADIX SORT ~~~
           # of Buckets = radix
           Each bucket has a list aswell, with possible values of similar size
           
           00156
           Buckets can be created in the second bucket for things with the same first 2 radixs
           
           Not O(N) lol
           
           ~~~~~~ STABILITY ~~~~~~
           With sorts, theres something called stability.
                Grades
            Jim=A Sally=A Jimmy=F John=C
                We want to sort on increasing order
            Jimmy John Sally Jim
                        ^-----^
                            Either or
            
            Stable sort, retains the order of things with equal value pre-sort
            Jimmy John Jim Sally
            
           Bubble sort is Stable
            
           Insertion Sort:
           Dont allow equality to over-write 
           
           Slecection SOrt: Stable
           
    
    
    Heap: dont bubble up past equality? (or do,)
            When remove, if equal... idk
    */
    
    
    
}
