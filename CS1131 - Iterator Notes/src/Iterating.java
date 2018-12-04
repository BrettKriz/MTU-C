import java.util.Iterator;
//import java.util;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brett Kriz
 */
public class Iterating {
    public static void main(String[] args){
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        
        //Iterator<Integer> iter = new ArrayIterator<>(array);
        //Iterable<E> iterable =  new ArrayIterable<Integer>(array);
        Iterator<Integer> iter =  new Take<>(new Zip<>(new Take<>(new EvensGenerator(), 10), new Take( new OddsGenerator(), 10) ), 6 );
        for(Integer i : iter){
            System.out.printf("%d", iter.next());
        }
        for(Integer i : iter){
            System.out.printf("%d", iter.next());
        }
    }
}

class GenericIterable<E> implements Iterable<E>{
    private Iterator<E> iter;
    
    public GenericIterable(Iterator<E> iter){
        this.iter = iter;
    }
    public Iterator<E> iterator(){
        return iter;
    }
    
}

// Iterable<E> iterable =  new ArrayIterable<Integer>(array);
class ArrayIterable<E> implements Iterable<E>{
    private E[] array;
    
    public ArrayIterable(E[] array){
        this.array = array;
    }
    
    public Iterator<E> iterator(){
        return new ArrayIterator<E>(array);
    }
    
}

class ReverseArrayIterable<E> implements Iterable<E>{
    private E[] array;
    
    public ReverseArrayIterable(E[] array){
        this.array = array;
    }
    
    public Iterator<E> iterator(){
        //return iter;
    }
    
}

class RepeatArrayIterator<E> implements Iterator<E>{
    private E[] array;
    private int times;
    
    private int next;
    private int time;
    public RepeatArrayIterator(E[] array, int times){
        this.array = array;
        this.times = times;
        
        this.time = 0;
        this.next = 0;
       
    }
    
    @Override
    public boolean hasNext(){
        return time < times || next < array.length;
    }
    @Override
    public E next(){
        if(time == times){
            time = 0;
            next += 1;
        }
        
        time += 1;
        
        return array[next];
    }
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class Zip<E> implements Iterator<E>{
    private Iterator<E> iter0;
    private Iterator<E> iter1;
    
    private boolean is0;
    
    public Zip(Iterator<E> iter0, Iterator<E> iter1){
        this.iter0 = iter0;
        this.iter1 = iter1;
        this.is0 = true;
    }
    
    public boolean hasNext(){
        return (iter0.hasNext() || iter1.hasNext()) ;
    }
    public E next(){
        if (is0 && iter0.hasNext()){
            is0 = !is0;
            return iter0.next();
        }
        if( !is0 && iter1.hasNext()){
            is0 = !is0;
            return iter1.next();
        }
        if (iter0.hasNext()){
            return iter0.next();
        }
        if (iter1.hasNext()){
            return iter1.next();
        }
        throw new Error("WHAT????");
    }
       @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class Take<E> implements Iterator<E>{
    private Iterator<E> iter;
    private int n;
    private int i; // taken thus far
    
    public Take(Iterator<E> iter, int n){
        this.iter = iter;
        this.n = n;
    }
    
    public boolean hasNext(){
        return i < n && iter.hasNext();
    }
        
    public E next(){
        i++;
        return iter.next();
    }
    
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class OddsGenerator implements Iterator<Integer>{
    private int next = 1;
    
    @Override
    public boolean hasNext(){
        return true;// INF
    }
    
    @Override
    public Integer next(){
        int ret = next;
        next += 2;
        
        return ret;
    }
    
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class EvensGenerator implements Iterator<Integer>{
    private int next = 0;
    
    @Override
    public boolean hasNext(){
        return true;// INF
    }
    
    @Override
    public Integer next(){
        int ret = next;
        next += 2;
        
        return ret;
    }
    
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class ReverseArrayIterator<E> implements Iterator<E>{
    private E[] array;
    private int next;
    
    public ReverseArrayIterator(E[] array){
        this.array =array;
        this.next = array.length-1;
    }
    
    @Override
    public boolean hasNext(){
        return next >= 0;
    }
    @Override
    public E next(){
        return array[next--];
    }
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
}

class ArrayIterator<E> implements Iterator<E> {
    private E[] array;
    private int next;
    
    public ArrayIterator(E[] array){
        this.array = array;
        this.next = 0;
    }
    @Override
    public boolean hasNext(){
        return next < array.length;
    }
    @Override
    public E next(){
        return array[next++];
    }
    @Override
    public void remove(){
        throw new UnsupportedOperationException("Stupid Op");
    }
    
    
    
}