import java.util.NoSuchElementException;

/**
 * A simple stack implementation.
 *
 * @author Brett Kriz
 * @param <E> Type
 */
public class Stack<E> {
        private E[] elems;
        private int size = 0;// Subtract 1 to reference array
        // LIFO PLZ

	/**
	 * Constructs a stack with sensible defaults.
	 */
	public Stack() {
		this.elems = resize(elems, 10);
                
	}
        
        /**
         * Returns an copy of the current array
         * 
         * @return array copy
         */
        public E[] getArrayCopy(){
            return resize(elems,size);
        }
	/**
	 * Pushes the specified element on to the stack.
	 *
	 * @param e the specified element
	 */
	public void push(E e) {
		if (e == null){
                    // Add nothing? Done.
                    return;
                }
                if (size == elems.length){
                    //Resize
                    this.elems = resize(elems, size*2);
                }
                
                elems[size++] = e;    
	}
        
        public void push(E[] e) { //Push tables
		if (e == null){
                    // Add nothing? Done.
                    return;
                }
                if (size == elems.length){
                    //Resize
                    this.elems = resize(elems, size*2);
                }
                
                for (int x = 0; x < e.length; x++){
                    elems[size++] = e[x];
                }
	}
        
        
        /**
         * Resizes the a given array
         * 
         * @param elems
         * @param newsize
         * @return elems with a new size
         */
        @SuppressWarnings("unchecked")
        private E[] resize(E[] elems, int newsize){
            if(newsize < 0){
                throw new IllegalArgumentException();
                // UP FOR CHANGE
            }
            E[] newelems = (E[])(new Object[newsize]);
            if (elems == null){
                return newelems;
            }
            // Add elements back in
            // Netbeans cant believe im not using System.arraycopy
            for(int x = 0; x < Math.min(elems.length, newsize); x++){
                newelems[x] = elems[x];
            }
            
            return newelems;
        }
	
	/**
	 * Pops the top element off the stack, returning the element.
	 *
	 * @throws NoSuchElementException if the stack is empty
	 * @return the popped value
	 */
	public E pop() {
                if (isEmpty()){
                    throw new NoSuchElementException();
                }
		E ret = elems[size-1];
                elems[--size] = null;
                return ret;
	}
	
	/**
	 * Returns the top element.
	 *
	 * @throws NoSuchElementException if the stack is empty
	 * @return the top element
	 */
	public E top() {
		if (isEmpty()){
                    throw new NoSuchElementException();
                }
                return elems[size-1];
	}
        public E safeTop() {
            try{
                return top();
            }catch (NoSuchElementException e){
                return null;
            }
        }
	
	/**
	 * Clears every element from the stack.
	 */
	public void clear() {
		for(int x = 0; x < elems.length; x++){
                    elems[x] = null;
                }
                size = 0;
	}
	
	/**
	 * Returns the number of elements contained in this stack.
	 *
	 * @return the number of elements contained in this stack.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Determines if the stack is empty (size == 0).
	 *
	 * @return true if the stack is empty; false otherwise
	 */
	public boolean isEmpty() {
		return (size <= 0);
	}
	
	/**
	 * Generates a string representation of this stack. The stack is
	 * represented by an opening square bracket, followed by a space delineated
	 * list of elements from bottom to top, followed by a closing square 
	 * bracket. The stack containing the elements 12, 42, and 10 (where 10 is
	 * the top element) would have the string representation "[ 12 42 10 ]".
	 * The empty stack has the string representation "[ ]" where only a single
	 * space separates the square brackets.
	 *
	 * @return the string representation of this stack.
	 */
        @Override
	public String toString() {
                if (isEmpty()){
                    return "[ ]";
                }
                
		String body = "";
                for(int x = 0; x < elems.length; x++){
                    if (elems[x] != null){
                        body += elems[x].toString() + " ";
                    }
                }// END FOR
                
                //             replace duplicate spaces
                return "[ " + body.replaceAll("\\s+", " ") + "]";
	}
}