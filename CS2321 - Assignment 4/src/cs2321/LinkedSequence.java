package cs2321;

import net.datastructures.*;
import java.util.Iterator;

/**
 * A Linked node based Sequence
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author 
 */
public class LinkedSequence<E> implements Sequence<E> {

	public LinkedSequence() {
        /*# If you create an LinkedSequence remove the following exception*/
		/*# DO NOT submit more than one working sequence */
		throw new RuntimeException();
	}

	public Position<E> atIndex(int r) throws BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public int indexOf(Position<E> p) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addFirst(E element) {
		// TODO Auto-generated method stub
		
	}

	public void addLast(E element) {
		// TODO Auto-generated method stub
		
	}

	public E getFirst() throws EmptyDequeException {
		// TODO Auto-generated method stub
		return null;
	}

	public E getLast() throws EmptyDequeException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public E removeFirst() throws EmptyDequeException {
		// TODO Auto-generated method stub
		return null;
	}

	public E removeLast() throws EmptyDequeException {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void add(int i, E e) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		
	}

	public E get(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	public E remove(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	public E set(int i, E e) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAfter(Position<E> p, E e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	public void addBefore(Position<E> p, E e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	public Position<E> first() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Position<E> last() {
		// TODO Auto-generated method stub
		return null;
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	public E set(Position<E> p, E e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
} // End LinkedSequence
