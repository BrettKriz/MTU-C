import java.util.*;

/**
 * It's time to think about your priorities...
 * @author Jason Hiebel
 **/
public class Priorities {
	public static void main(String[] args) {
		PriorityQueue<Student> students = new UnsortedPriorityQueue<Student>();
		
		students.enqueue(new Student("Hiebel, Jason", "906.360.8089", 3.8, 3.9));
		students.enqueue(new Student("Hanks, Thomas", "555.555.5501", 3.5, 0.0));
		students.enqueue(new Student("Irontoad, Bob", "545.867.5309", 2.7, 0.0));
		students.enqueue(new Student("MacFly, Marty", "012.345.6789", 2.0, 2.0));
		students.enqueue(new Student("Validictorian", "111.111.111 ", 4.0, 4.0));
		students.enqueue(new Student("Ssulutatorian", "222.222.2222", 4.0, 3.9));
		
		int rank = 1;
		while(!students.isEmpty()) {
			System.out.printf("%d) %s%n", rank++, students.dequeue());
		}
	}
}

class Student implements Comparable<Student> {
	public final String name;
	public final String phone;
	public final double gpa;
	public final double apgpa;
	
	public Student(String name, String phone, double gpa, double apgpa) {
		this.name  = name;
		this.phone = phone;
		this.gpa   = gpa;
		this.apgpa = apgpa;
	}
	
	public String toString() {
		return String.format("[%s, %s, %f, AP:%f]", name, phone, gpa, apgpa);
	}
	
	public int compareTo(Student other) {
		if(gpa != other.gpa) {
			return Double.compare(gpa, other.gpa);
		}
		else {
			return Double.compare(apgpa, other.apgpa);
		}
	}
}

abstract class PriorityQueue<E extends Comparable<E>> implements Iterable<E> {
	protected List<E> backing;
	
	protected PriorityQueue(List<E> backing) {
		this.backing = backing;
	}

	public abstract void enqueue(E e);
	public abstract E    dequeue();
	public abstract E    element();
	
	public boolean isEmpty() {
		return backing.isEmpty();
	}
	
	public int size() {
		return backing.size();
	}
	
	public Iterator<E> iterator() {
		return backing.listIterator();
	}
}

class SortedPriorityQueue<E extends Comparable<E>> extends PriorityQueue<E> {
	
	public SortedPriorityQueue() {
		super(new LinkedList<E>());
	}
	
	public void enqueue(E e) {
		int x = 0;
		for(; x < backing.size(); x += 1) {
			if(backing.get(x).compareTo(e) > 0) { break; }
		}
		backing.add(x, e);
	}
	
	public E dequeue() {
		return backing.remove(backing.size() - 1);
	}
	
	public E element() {
		return backing.get(backing.size() - 1);
	}
}

class UnsortedPriorityQueue<E extends Comparable<E>> extends PriorityQueue<E> {
	private E best;
	
	public UnsortedPriorityQueue() {
		super(new LinkedList<E>());
		best = null;
	}

	public void enqueue(E e) {
		if(best == null || best.compareTo(e) < 0) { best = e; }
		System.out.println(best);
		backing.add(e);
	}
	
	public E dequeue() {
		E ret = best;
		backing.remove(best);
		best = max();
		return ret;
	}
	
	public E element() {
		return best;
	}
	
	private E max() {
		E max = null;
		for(E e : backing) {
			if(max == null || max.compareTo(e) < 0) { 
				max = e; 
			}
		}
		return max;
	}
}