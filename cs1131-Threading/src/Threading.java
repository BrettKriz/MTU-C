import java.util.Map;

/**
 *
 * @author Brett Kriz
 */
public class Threading {
    public static void main(String[] a){
        Counter counter = new Counter();
        Thread[] threads = {
            new IncrementingThread(counter),
            new IncrementingThread(counter),
            new DecrementingThread(counter)
        };
        for (Thread thread : threads){
                thread.start();
                // (new Thread(new CollatzRunnable(answers, input))).start();
        }//creates a new thread
        
        
        for(Thread thread : threads){
            try{
                thread.join(); // Wait until main thread finishes
                //Thread.sleep(10000);
            }catch (InterruptedException e){
                // Not going to happen
            }
            
        }
        
        System.out.println(counter.value());
    }
}
class IncrementingThread extends Thread{
    private Counter counter;
    public IncrementingThread(Counter counter){
        this.counter = counter;
    }
    
    @Override
    public void run(){
        for(int x = 0; x < 100; x++){
            counter.increment();
        }
            
    }
}
class DecrementingThread extends Thread{
    private Counter counter;
    public DecrementingThread(Counter counter){
        this.counter = counter;
    }
    
    @Override
    public void run(){
        for(int x = 0; x < 100; x++){
            counter.decrement();
        }
            
    }
}

//                  implements Runnable{
class CollatzThread extends Thread        {
    private Map<Integer, Integer> ans;
    private int input;
    
    
    public CollatzThread(Map<Integer, Integer> ans, int inoput){
        this.ans = ans;
        this.input = input;
    }
    
    @Override
    public void run(){
        int len = 0;
        int x = input;
        while (x != 1){
            len++;
            x = (x % 2 == 0) ? x/2 : x*3 + 1;
        }
            
    }
}
class Counter{
    private int x = 0;
    
    public synchronized void increment(){
        //synchronized(this){ // LOCK, prevents multiple threads  from executing this methiod at once
            x++;
            try{
                Thread.sleep((int)(Math.random()*10));
            }catch(InterruptedException e){}
        //}
    }
    public synchronized void decrement(){
        //synchronized(this){
            x--;
            try{
                Thread.sleep((int)(Math.random()*10));
            }catch (InterruptedException e){}
        //}
    }
    public int value(){
        return x;
    }
}
