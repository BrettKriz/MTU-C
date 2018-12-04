import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;// Import needed to meet specs.


/**
 * A simple list implementation.
 *
 * @author Brett Kriz
 */
public class List<E> { //implements Referenceable
        protected E[] elems;
        protected int size = 0;
                
	/**
	 * Constructs a stack with sensible defaults.
        * @param <E> Type
	 */
	public List() {
            this.elems = resize(elems, 5);
	}
        public List(List<E> list){
            // Sudo clone
            List<E> arg = ((List<E>)list);
            this.elems = arg.elems;
            this.size = list.size;
            
        }

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 */
	public void add(E e) {
            checkState(size);
            elems[size++] = e;
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
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any 
	 * subsequent elements to the right (adds one to their indices).
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index the index at which the specified element will be added
	 * @param e element to be inserted
	 */
	public void add(int index, E e) {
		checkState(index);
                
                try{
                    Object a = elems[index];
                }catch (ArrayIndexOutOfBoundsException ex){
                    // Resize then, huh
                    this.elems = resize(elems, elems.length*2);
                }
                
                if (elems[index] == null){
                    // Just drop it
                    elems[index] = e;
                    size++;
                    return;
                }
                
                if (index >= elems.length-1){
                    // Time to stretch
                    this.elems = resize(elems,elems.length*2);
                }
                
            
            
                for(int x = elems.length-1; x > index; x--){
                    elems[x] = elems[x-1];
                }
                elems[index] = e;
                size++;
                    
	}
	
        public List<E> getShallowCopy(){
            // no lists in lists
            List<E> ans = new List<>();
            
            for(E cur : this.elems){
                if (!(cur instanceof List)){
                    ans.add(cur);
                }
            }
            
            return ans;
        }
        
        public E[] addListElems(List<E> args){
            // Add elemns of another list of the same type
            for(E cur : args.getArrayCopy()){
                if (cur != null){
                    this.add(cur);
                }
            }
            
            return elems;
        }
        
	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index of the element to return
	 * @return the element at the specified position in this list
	 */
	public E get(int index) {
		checkState(index);
                return elems[index];
	}
	 
        //public int keyAtIndex()
        
	/**
	 * Removes the element at the specified position in this list. Shifts any 
	 * subsequent elements to the left (subtracts one from their indices). 
	 * Returns the element that was removed from the list.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index of the element to be removed
	 * @return the element previously at the specified position
	 */
	public E remove(int index) {
		checkState(index);
                
                E ans = elems[index];
                elems[index] = null;
                size--;
                return ans;
	}
	
	/**
	 * Replaces the element at the specified position in this list with the 
	 * specified element.
	 *
	 * @throws IndexOutOfBoundException if the index is out of range 
	 * [index < 0 || index >= size()]
	 * @param index index of the element to replace
	 * @param e element to be stored at the specified position
	 * @return the element previously at the specified position
	 */
	public void set(int index, E e) {
		checkState(index);
                elems[index] = e;
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
	 * Returns the number of elements in this list. If this list contains more 
	 * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	public int size() {
            int ans = size;
            
            if (size > (int)Integer.MAX_VALUE){
                ans = (int)Integer.MAX_VALUE;
            }
            
            return ans;
	}
	
	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return if this list contains no elements
	 */
	public boolean isEmpty() {
            boolean flag = true;
            for(E cur : elems){
                if (cur != null ){
                    flag = false;
                }
            }
            // Somehow size would leak
            return flag;
	}
        
        /**
         * Make sure were in bounds, 
         * otherwise throw error
         * 
         * @param i 
         */
        private boolean checkBounds(int i){
            if (i == 0 && size() == 0){
                return true;
            }
            boolean flag = (i > -1 && i <= elems.length);// INBOUNDS
            
            return flag;
        }
        
        /**
         * Checks if a resize is needed
         */
        private void checkSize(int index){
            if (index >= elems.length){
                    this.elems = resize(elems, 2*size);
            }
        }
        private void checkSize(){
            if (size >= elems.length){
                    this.elems = resize(elems, 2*size);
            }
        }
        private void checkState(int index) throws IndexOutOfBoundsException{
            checkBounds(index);
            checkSize(index);
        }
        
        
        /**
         * Resize the given array
         * 
         * @param elems
         * @param newsize
         * @return array of new size
         */
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
            System.arraycopy(elems, 0, newelems, 0, Math.min(elems.length, newsize));
            
            return newelems;
        }
        
	/**
	 * Generates a string representation of this list. The list is represented 
	 * by an opening square bracket, followed by a space delineated list of 
	 * elements from front to back, followed by a closing square bracket. The 
	 * list containing the elements 12, 42, and 10 (where 12 is the first
	 * element and 10 is the last) would have the string representation 
	 * "[ 12 42 10 ]". The empty stack has the string representation "[ ]" 
	 * where only a single space separates the square brackets.
	 *
	 * @return the string representation of this list.
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
        public String getBody(){
            if (isEmpty()){
                return "";
            }
            
            String body = "";
            for(int x = 0; x < elems.length; x++){
                if (elems[x] != null){
                    body += elems[x].toString() + " ";

                }
            }// END FOR
            return body.trim();
        }
}