package Structures;

public class Collections {
	public static void main(String[] args) {
		Collection<String> queue = new LinkedQueue<String>();
		queue.add("Hello");
		System.out.println(queue.element());
		queue.add("World");
		System.out.println(queue.element());
		System.out.println(queue.size());
		System.out.println(queue.remove());
		System.out.println(queue.element());
		System.out.println(queue.size());
		
		for(int x = 0; x < 30; x += 1) { queue.add("" + x); }
		System.out.println(queue.element());
		System.out.println(queue.size());
	}
}

class Arrays {
	@SuppressWarnings("unchecked")
	public static <E> E[] copy(E[] elements, int newSize) {
		if(newSize < 0) { throw new IllegalArgumentException(); }
		E[] newElements = (E[])(new Object[newSize]);
		
		if(elements != null) {
			for(int x = 0; x < Math.min(elements.length, newSize); x += 1) {
				newElements[x] = elements[x];
			}
		}
		return newElements;
	}
}

interface Collection<E> {
	public void add    (E elem);
	public E    remove ();
	public E    element();
	
	public int size();
	public boolean isEmpty();
}

abstract class AbstractCollection<E> implements Collection<E> {
	public boolean isEmpty() {
		return size() == 0;
	}
}

abstract class AbstractLinkedCollection<E> extends AbstractCollection<E> {
	protected int size;

	public AbstractLinkedCollection() {
		size = 0;
	}

	public int size() {
		return size;
	}
}

class LinkedQueue<E> extends AbstractLinkedCollection<E> {
	private Node element;
	private Node back;
	
	public LinkedQueue() {
		this.element = this.back = null;
		this.size = 0;
	}
	
	// O(1)
	public void add(E elem) {
		if(element == null) {
			element = back = new Node(elem, null);
		}
		else {
			back.next = new Node(elem, null);
			back = back.next;
		}
		size += 1;
	}
	
	// O(1)
	public E remove() {
		E ret = element.value;
		element = element.next;
		size -= 1;
		
		if(isEmpty()) { back = null; }
		
		return ret;
	}
	
	// O(1)
	public E element() {
		return element.value;
	}
	
	private class Node {
		public final E    value;
		public       Node next;
		
		public Node(E value, Node next) {
			this.value = value;
			this.next  = next;
		}
	}
}

class ArrayQueue<E> extends AbstractCollection<E> {
	private E[] elements;
	private int last;
	
	public ArrayQueue() {
		elements = Arrays.copy(null, 10);
		last = 0;
	}
	
	// O(n)
	public void add(E elem) {
		if(last == elements.length) {
			elements = Arrays.copy(elements, 2 * elements.length);
		}
		elements[last++] = elem;
	}
	
	// remove (remove from queue) != deque (double ended queue)
	// O(n)
	public E remove() {
		E ret = elements[0];
		for(int x = 1; x < last; x += 1) {
			elements[x - 1] = elements[x];
		}
		last -= 1;
		return ret;
	}
	
	// O(1)
	public E element() {
		return elements[0];
	}
	
	// O(1)
	public int size() {
		return last;
	}
}





class LinkedStack<E> extends AbstractLinkedCollection<E> {
	private Node element;
	private int  size;
	
	public LinkedStack() {
		this.element  = null;
		this.size = 0;
	}
	
	// O(1)
	public void add(E elem) {
		Node newelement = new Node(elem, element);
		element = newelement;
		size += 1;
	}
	
	// O(1)
	public E remove() {
		E elem = element.elem;
		element = element.next;
		size -= 1;
		return elem;
	}
	
	// O(1)
	public E element() {
		return element.elem;
	}
	
	private class Node {
		public final E    elem;
		public final Node next;
		
		public Node(E elem, Node next) {
			this.elem = elem;
			this.next = next;
		}
	}
}

class ArrayStack<E> extends AbstractCollection<E> {
	private E[] elements;
	private int element;
	
	public ArrayStack() {
		this.elements = Arrays.copy(null, 10);
		this.element = 0;
	}
	
	// O(n)
	public void add(E elem) {
		if(element == elements.length) { 
			this.elements = Arrays.copy(elements, 2 * element);
		}
		elements[element++] = elem;
	}
	
	// O(1)
	public E remove() {
		E ret = elements[--element];
		elements[element] = null;
		return ret;
	}
	
	// O(1)
	public E element() {
		return elements[element - 1];
	}
	
	// O(1)
	public int size() {
		return element;
	}
}