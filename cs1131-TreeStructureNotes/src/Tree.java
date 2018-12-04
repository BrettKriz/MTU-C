import java.util.*;
/**
 *
 * @author Brett Kriz
 */
public class Tree {
    /*
        Root node
        has child nodes
        
        leaf node, has no children, IE at the end
    
        Height of root = 0
        Height of children = 1
                grandchildren = 2
           . . .
        Height of leafe = n
    */
    /*
        BINARY TREE
        Each node has at most 2 children (left and right child)
        
        binary search tree:
        Left children < CUR < right children
    
        for placement
        4 1 2 9 8 5 6
                    ^6>5 so right of 5 node
                  ^5<8 so left of 9 node
                ^8<9 so left of 9 node
              ^9>4 so right
            ^2<1 so right of 1 node
          ^ 1<4 so left
        ^ root
    
        SO, when looking at the tree we can find things
            *Root is arbitrary
        
    */
    /*
        TREE BALANCING:
        4 1 2 9 8 5 6
        sort
        1 2 4 5 6 8 9
        Construct
            5
           2 8
         1 4 6 9
         
        internat nodes: 5,2,8
        height: 3  
        root: 5
        leafs: 1 4 6 9
    */
    /*
        WORST CASE TREE
        1 <- root
          2
            4
              5
                6
                  8
                    9
    */
    /*
        Pre-order   Check parent before children.
        10 8 7 9 20 15 14 19
        in-order    Check left children, then parent, then right.
        7 8 9 10 14 15 19 20
        post-order  Check children before parent.
        7 9 8 14 19 15 20 10
    */
    public static void main(String[] args){
        BSTSet<Integer> set = new BSTSet<>();
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
        set.remove(1);
        set.remove(5);
        
        System.out.println();
        System.out.println();
        
        
        for(Integer i : set){
            System.out.print(i +" ");
        }
        for(Integer i : set.ordering(Ordering.Pre)){
            System.out.print(i +" ");
        }
        for(Integer i : set.ordering(Ordering.In)){
            System.out.print(i +" ");
        }
        for(Integer i : set.ordering(Ordering.Post)){
            System.out.print(i +" ");
        }
        for(Integer i : set.preorder()){
            System.out.print(i +" ");
        }
        System.out.println();
        for(Integer i : set.inorder()){
            System.out.print(i +" ");
        }
        System.out.println();
        for(Integer i : set.postorder()){
            System.out.print(i +" ");
        }
        System.out.println();
    }
}    

enum Ordering{
    Pre, In, Post;
}


class BSTSet<E extends Comparable<E>> implements Iterable<E>{
    //
    private Node root;
    public BSTSet(){
        this.root = null;
    }
    public void add(E e){
        Node toAdd = new Node(e);
        if (root == null){root = toAdd;}
        else{
            add(root, e);
        }
    }
    public void add(Node parent, E toAdd){
        // LEFT
        if (toAdd.compareTo(parent.data) < 0){
            if (parent.lChild == null){
                parent.lChild = new Node(toAdd);
            }else{
                add(parent.lChild, toAdd);
            }
        }
        // RIGHT
        if (toAdd.compareTo(parent.data) > 0){
            if (parent.rChild == null){
                parent.rChild = new Node(toAdd);
            }else{
                add(parent.rChild, toAdd);
            }
        }

    }
    public void remove(E e){
    /* 3 cases possible
        MUST BE DONE RECURRSIVELY
        
        // 1. Leaf
        // 2. Internal with a child(with any number of children)
        // 3. Internal with both chilren
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~
        -for 1: just make parent forget about child
        -for 2: give child new parent, the cur parents parent or make root
        -for 3: CAREFULL! 6 with children 4(to 3&5) & 8(to 7&9)
                SO repleace node with 5 or 7
                IE go left 1 and right as far as we can
                OR go right 1 and left as far as we can
            BUT what if 5 had a subchild to left?
                SO swap 5 & 6 nodes, then consider left child
                    hook 4 to the subnode
        -parent node could be null, so consider it

        //*/
        remove(null, root, e);
    }
    private void remove(Node parent, Node cur, E e){
        // Find thing to remove
        if(cur.data.compareTo(e) == 0){
            // remove cur
           // Find case were workin with
            if (cur.lChild == null && cur.rChild == null){
                // 1. is Leaf
                if(parent == null){
                    root = null;
                    // for GC
                }else{
                    if (parent.lChild == cur){
                        parent.lChild = null;
                    }
                    if (parent.rChild == cur){
                        parent.rChild = null;
                    }
                }
                
            }else if (cur.lChild != null && cur.rChild != null){
                // case 2
                // Recursion or iterative
                
                Node replace = cur.lChild;
                while(replace.rChild != null){ // Step right as far as we can
                    replace = replace.rChild;
                }
                
                cur.data = replace.data;
                // find parent of replace & remove
                if (cur.lChild == replace){
                    remove(cur, replace, replace.data);
                }else{
                    Node replaceParent = cur.lChild;
                    while(replaceParent.rChild != replace){
                        replaceParent = replaceParent.rChild;
                    }
                    remove(replaceParent, replace, replace.data);
                }

            }else{
                // case 1
                Node child = cur.lChild == null ? cur.rChild : cur.lChild;
                
                if(parent == null){
                    root = child;
                }else{
                    if(parent.lChild == cur){
                        parent.lChild = child;
                    }
                    if(parent.rChild == cur){
                        parent.rChild = child;
                    }
                }
                
            }
        }else if (cur.data.compareTo(e) < 0){
            remove(cur, cur.lChild, e);
        }else{
            remove(cur, cur.rChild, e);
        }
        
    }
    public boolean contains(E e){
        if (root == null){
            return false;
        }else{
            return contains(root, e);
        }
    }
    private boolean contains(Node parent, E e){
        if (parent == null){
            return false;
        }else if(e.compareTo(parent.data) == 0){
            return true;
        }else if (e.compareTo(parent.data) < 0){
            return contains(parent.rChild, e);
        }else if (e.compareTo(parent.data) > 0){
            return contains(parent.lChild, e);
        }
        return false;
    }
    
     public Iterable<E> breadth(){
        List<E> ordering = new ArrayList<>();
        // Breadth first search, not complete
        
       //if (root != null){preorder(ordering, root);}
        if (root != null){
            Queue<Node> queue = Collections.asLifoQueue(new LinkedList<Node>());
            queue.add(root);
            
            while(!queue.isEmpty()){
                Node current = queue.remove();
                if (current.rChild != null){
                    queue.add(current.rChild);
                }
                if (current.lChild != null){
                    queue.add(current.lChild);
                }
                
                ordering.add(current.data);
            }
        }
        
        
        
        return ordering;
        
        //return  null;
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
        return //im not sure
    }
     
     
    public Iterable<E> preorder(){
        List<E> ordering = new ArrayList<>();
        // Depth first search
        
       //if (root != null){preorder(ordering, root);}
        if (root != null){
            Queue<Node> stack = Collections.asLifoQueue(new LinkedList<Node>());
            stack.add(root);
            
            while(!stack.isEmpty()){
                Node current = stack.remove();
                if (current.rChild != null){
                    stack.add(current.rChild);
                }
                if (current.lChild != null){
                    stack.add(current.lChild);
                }
                
                ordering.add(current.data);
            }
        }
        
        
        
        return ordering;
        
        //return  null;
    }
    
    private void preorder(List<E> ordering, Node node){
        ordering.add(node.data);
        if(node.lChild != null){
            preorder(ordering, node.lChild);
        }
        if(node.rChild != null){
            preorder(ordering, node.rChild);
        }
        
    }
    public Iterable<E> inorder(){
         List<E> ordering = new ArrayList<>();
        if (root != null){inorder(ordering, root);}
        return ordering;
    }
    private void inorder(List<E> ordering, Node node){
        
        if(node.lChild != null){
            preorder(ordering, node.lChild);
        }
        if(node.rChild != null){
            preorder(ordering, node.rChild);
        }
    }
    public Iterable<E> postorder(){
         List<E> ordering = new ArrayList<>();
        if (root != null){postorder(ordering, root);}
        return ordering;
    }
    private void postorder(List<E> ordering, Node node){
        
        if(node.lChild != null){
            preorder(ordering, node.lChild);
        }
        if(node.rChild != null){
            preorder(ordering, node.rChild);
        }
        ordering.add(node.data);
    }
    
    private class Node{
        public E     data;
        public Node lChild;
        public Node rChild;
        
        public Node(E data){
            this.data = data;
            this.lChild = this.rChild = null;
        }
        
    }
}
