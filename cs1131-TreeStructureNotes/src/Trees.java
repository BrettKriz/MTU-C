import java.util.*;

/**
 *
 * @author Jason H.
 * @author Brett Kriz
 */
class Trees {
	public static void main(String[] args) {
		BSTSet<Integer> set = new BSTSet<Integer>();
		set.add(1);
		System.out.println(set.contains(1));
		System.out.println(set.contains(2));
		set.add(6);
		set.add(5);
		System.out.println(set.contains(5));
		System.out.println(set.contains(7));
		set.add(4);
		set.add(-50);
		set.add(-25);
		
		for(Integer i : set.preorder()) { System.out.printf("%d ", i); }
		System.out.println();
		for(Integer i : set.inorder()) { System.out.printf("%d ", i); }
		System.out.println();
		for(Integer i : set.postorder()) { System.out.printf("%d ", i); }
		System.out.println();
	}
}

class BSTSet<E extends Comparable<E>> implements Iterable<E>{
	private Node root;
	
	public BSTSet() {
		this.root = null;
	}
	
	public void add(E e) {
		Node toAdd = new Node(e);
		
		if(root == null) { root = toAdd; }
		else {
			add(root, toAdd);
		}
	}
	
	public void add(Node parent, Node toAdd) {
		if(toAdd.data.compareTo(parent.data) < 0) {
			if(parent.lChild == null) { parent.lChild = toAdd; }
			else { add(parent.lChild, toAdd); }
		}
		if(toAdd.data.compareTo(parent.data) > 0) {
			if(parent.rChild == null) { parent.rChild = toAdd; }
			else { add(parent.rChild, toAdd); }
		}
	}
	
	public void remove(E e) {
		
	}
	
	private void remove(Node parent, Node current, E e) {
		if(current.data.compareTo(e) == 0) {
			if(current.lChild == null && current.rChild == null) {
				if(parent == null) { root = null; }
				else {
					if(parent.lChild == current) { parent.lChild = null; }
					if(parent.rChild == current) { parent.rChild = null; }
				}
			}
			else if(current.lChild != null && current.rChild != null) {
				Node replace = current.lChild;
				while(replace.rChild != null) { replace = replace.rChild; }
				current.data = replace.data;
				
				if(current.lChild == replace) { 
					remove(current, replace, replace.data);
				}
				else {
					Node replaceParent = current.lChild;
					while(replaceParent.rChild != replace) { 
						replaceParent = replaceParent.rChild;
					}
					remove(replaceParent, replace, replace.data);
				}	
			}
			else {
				Node child = 
					current.lChild == null ? 
					current.rChild : 
					current.lChild;
				if(parent == null) { root = child; }
				else {
					if(parent.lChild == current) { parent.lChild = child; }
					if(parent.rChild == current) { parent.rChild = child; }
				}
			}
		}
		else if(current.data.compareTo(e) < 0) {
			remove(current, current.lChild, e);
		}
		else {
			remove(current, current.rChild, e);
		}
	}
	
	public boolean contains(E e) {
		if(root == null) { return false; }
		else {
			return contains(root, e);
		}
	}
	
	private boolean contains(Node parent, E e) {
		if(parent == null) { return false; }
		if(e.compareTo(parent.data) == 0) { 
			return true; 
		}
		else if(e.compareTo(parent.data) < 0) {
			return contains(parent.lChild, e);
		}
		else {
			return contains(parent.rChild, e);
		}
	}
	
	public Iterable<E> preorder() {
		List<E> ordering = new ArrayList<>();
		if(root != null) { preorder(ordering, root); }
		return ordering;
	}
	
	private void preorder(List<E> ordering, Node node) {
		ordering.add(node.data);
		if(node.lChild != null) { preorder(ordering, node.lChild); }
		if(node.rChild != null) { preorder(ordering, node.rChild); }
	}
	
	public Iterable<E> inorder() {
		List<E> ordering = new ArrayList<>();
		if(root != null) { inorder(ordering, root); }
		return ordering;
	}
	
	private void inorder(List<E> ordering, Node node) {
		if(node.lChild != null) { inorder(ordering, node.lChild); }
		ordering.add(node.data);
		if(node.rChild != null) { inorder(ordering, node.rChild); }
	}
	
	public Iterable<E> postorder() {
		List<E> ordering = new ArrayList<>();
		if(root != null) { postorder(ordering, root); }
		return ordering;
	}
	
	private void postorder(List<E> ordering, Node node) {
		if(node.lChild != null) { postorder(ordering, node.lChild); }
		if(node.rChild != null) { postorder(ordering, node.rChild); }
		ordering.add(node.data);
	}
	
	private class Node {
		public E data;
		
		public Node lChild;
		public Node rChild;
		
		public Node(E data) {
			this.data = data;
			this.lChild = this.rChild = null;
		}
	}
}