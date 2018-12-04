import java.util.*;
/**
 *
 * @author Brett Kriz
 */
public class Problem1 {
    enum Ordering{
    Pre, In, Post;
    }

    /**
    * Calculates the height of the given element in the constructed
    * binary search tree.
    *
    * @throws InvalidArgumentException if the tree is empty or the
    * specified element does not exist in the tree
    * @param elements the elements to insert in to the binary
    * search tree
    * @param element the element whose height to calculate
    * @return the height of the given element in the binary
    * search tree
    */
    public static int height(int[] elements , int element){
        if (elements.length < 1){
            throw new IllegalArgumentException();
        }
        boolean flag = false;
        for (int c : elements){
            if (c == element){
                flag = true;
                break;
            }
        }
        
        if (!flag){
            throw new IllegalArgumentException("ELEMENT NOT FOUND");
        }
        
        // ASSUMIING 2 parents
        //2x+1
        int height = 0;
        int len = elements.length;
        if(elements[0] == element){
            return height;
        }
        
        // SORTED
        int root = elements[0];
        BSTSet<Integer> set = new BSTSet<>();
        
        
        for(int x = 0; x < elements.length; x++){
            Integer c = elements[x];
            set.add(c);
        }        
        
        height = set.heightOf(element);
        /*
            TO FIND CHILDREN IN ARRAY: 
                lChild = 2x+1
                rChild = 2x+2
            TO FIND PARENT IN ARRAY:
                (x-1)/2 (INT DIVISION)
    
        */
        
        return height;
    }
    static class BSTSet<E extends Comparable<E>> implements Iterable<E>{
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
        public int heightOf(E e) {
                int h = 0;
		if(root == null) { return h; }
		else {
			return heightOf(root, e, h);
		}
	}
	public int height2 (E e){
            int h = 0;
            if(root == null) { return h; }

            if ()
            
            return h;
        }
	private int heightOf(Node parent, E e,int h) {
		if(parent == null) { return h; }
		if(e.compareTo(parent.data) == 0) { 
			return h; 
		}
		else if(e.compareTo(parent.data) < 0) {
			return heightOf(parent.lChild, e, ++h);
		}else if(e.compareTo(parent.data) > 0){
			return heightOf(parent.rChild, e, ++h);
		}
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
        @Override
         public Iterator<E> iterator(){
             return ordering(Ordering.In).iterator();
         }
         public Iterable<E> ordering(Ordering order){
            List<E> ordering = new ArrayList<>();

            if (root == null){
                return ordering;
            }

            Queue<Node> stack = Collections.asLifoQueue(new LinkedList<Node>());
            stack.add(root);


            while(!stack.isEmpty()){
                Node cur = stack.remove();

                if (order == Ordering.Pre){ordering.add(cur.data);}
                if (cur.rChild != null){stack.add(cur.rChild);}      
                if (order == Ordering.In){ordering.add(cur.data);}
                if (cur.lChild != null){stack.add(cur.lChild);}
                if (order == Ordering.Post){ordering.add(cur.data);}


            }
            return ordering;
        }
    }
}
