/**
 * 
 */
package cs2321;

import java.util.Comparator;
import java.util.Iterator;

import net.datastructures.*;

/**
 * LINKED BINARY TREE
 * @author cdbrown
 * @author Brett Kriz
 * @version 3
 */
public class LinkedBinaryTree<E> implements LinkTree<E> {
    // LinkedSequence< E > data; 
    // Casting impossible, contrary to in-class answer about it specificly
    
    protected PNode<E> root = null;
    private int size = 0;

    public LinkedBinaryTree(){}

    // CHECK
    public PNode<E> treeSearch(Position<E> arg, int k) // o(H)
                                               throws InvalidPositionException {
        PNode<E> v = validate(arg);
        // Use a loop to cut down on stack memory
        while (v.isInternal()){
            if (v.key() < k){
                v = v.right;
            }else if (v.key() > k){
                v = v.left;
            }else{
                break;
            }
        }
        return v;
    }

    protected PNode<E> createNode(E e, PNode<E> parent, PNode<E> left, PNode<E> right){
        return new PNode<E>(e,parent,left,right);
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.LinkTree#addRoot(java.lang.Object)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)") @TimeComplexityExpected("o(1)")
    public Position<E> addRoot(E e) throws NonEmptyTreeException, BoundaryViolationException {
        if (!this.isEmpty()){
            throw new NonEmptyTreeException("Tree isnt empty");
        }
        
        root = createNode(e,null,null,null);
        size = 1;
        
        return root;
    }

    /* (non-Javadoc)
     * @see net.datastructures.LinkTree#insertLeft(net.datastructures.Position, java.lang.Object)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)") @TimeComplexityExpected("o(1)")
    public Position<E> insertLeft(Position<E> v, E e)
                                               throws InvalidPositionException {
        PNode<E> parent = validate(v);

        if (hasLeft(parent)){
            throw new IllegalArgumentException("P has a left child already!");
        }
        PNode<E> child = createNode(e,parent,null,null);
        parent.setLeft(child);

        size++;
        return child;
        
    }
    public Position<E> addLeft(Position<E> v, E e)
                                               throws InvalidPositionException {
        return this.insertLeft(v, e);
    }

    /* (non-Javadoc)
     * @see net.datastructures.LinkTree#insertRight(net.datastructures.Position, java.lang.Object)
     */
    public Position<E> insertRight(Position<E> v, E e)
                                               throws InvalidPositionException {
        PNode<E> parent = validate(v);

        if (hasRight(parent)){
            throw new IllegalArgumentException("P has a left child already!");
        }
        PNode<E> child = createNode(e,parent,null,null);
        parent.setRight(child);

        size++;
        return child;
    }
    public Position<E> addRight(Position<E> v, E e)
                                               throws InvalidPositionException {
        return this.addRight(v, e);
    }

    public E set(Position<E> p, E e) throws InvalidPositionException {
        PNode<E> node = validate(p);
        E t = node.getElement();
        node.setElement(e);
        return t;
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.LinkTree#attach(net.datastructures.Position, net.datastructures.BinaryTree, net.datastructures.BinaryTree)
     */
    @Override
    public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2)
                                               throws InvalidPositionException {
        // Valid. Setroot needed
        if (!(T1 instanceof LinkedBinaryTree) || !(T2 instanceof LinkedBinaryTree)){
            throw new ClassCastException("T1 or T2 isnt a LinkedBinaryTree");
        }
        if (isInternal(v)){
            throw new IllegalArgumentException("v must be a leaf");
        }

        // Do variable conversions
        PNode<E> n = validate(v);
        LinkedBinaryTree<E> t1 = (LinkedBinaryTree)T1;
        LinkedBinaryTree<E> t2 = (LinkedBinaryTree)T2;

        this.size += T1.size() + T2.size();

        if (!t1.isEmpty()) {    // attach t1 as left subtree of node
            t1.root.setParent(n);
            n.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {    // attach t2 as right subtree of node 
            t2.root.setParent(n);
            n.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.LinkTree#remove(net.datastructures.Position)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)") @TimeComplexityExpected("o(1)")
    public E remove(Position<E> v) throws InvalidPositionException { // Internal
        PNode<E> n = validate(v);
        
        if (this.numChildren(n) == 2 ){
            throw new IllegalArgumentException("P has 2 children");
        }

        PNode<E> child = (n.getLeft() != null ? n.getLeft() : n.getRight());

        if (child != null){
            child.parent = n.parent; // Childs grandparent becomes parent
        }
        if (n.equals(root)){
            root = child;           // child becomes root
        } else {
            if (n.equals( n.parent.getLeft() )){
                n.parent.left = child;
            }else{
                n.parent.right = child;
            }
        }
        // Help garbage collection
        // Mangle our target
        this.size--;
        
        E temp = n.element();
        n.setElement(null);
        n.left = null;
        n.right = null;
        n.parent = n; // defunct node convention
        
        //checkRoot();
        
        return temp;
    }
    
    public void RemoveExternal(Position<E> v) throws InvalidPositionException { // o(n)
        PNode<E> v2 = validate(v);

        if (v2.isInternal()){
            throw new InvalidPositionException("Given position isnt EXTERNAL!");
        }

        PNode<E> parent = v2.parent.parent;
        PNode<E> temp = v2.parent;
        this.remove(v2);
        this.remove(temp);
        this.checkRoot();
        temp = parent; // parent.parent = v2.parent;
        
        // ABOVE usefull for AVL Height Checks
    }

    
    public E BSTRemove(Position<E> arg) throws InvalidPositionException { // o(n)
        // v is an internal node
        if (this.isEmpty()){
            throw new EmptyTreeException("EMPTY!");
        }
        PNode<E> v = validate(arg);
        E old = v.element();
        
        if (v.isExternal()){
            throw new InvalidPositionException("Must be internal");
        }
        
        if (v.left != null && v.left.isExternal()){
            RemoveExternal(v.left);
        }else if (v.right != null && v.right.isExternal()){
            RemoveExternal(v.right);
        }else{ // find v's
            PNode<E> w = v.right;
            while (w != null && w.isInternal()){
                w = w.left;
            }
            if (w != null && w.getParent() != null){
                v.setElement( w.parent.element() );
                RemoveExternal(w);
            }else{
                v.setElement( null );
            }
        }
        //checkRoot();
        
        return old;
    }
    

    protected void checkRoot(){
        // Make sure we dont have an empty tree and yet a root.
        if (this.isEmpty() && this.root != null){
            this.root.setElement(null); //
        }
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.BinaryTree#hasLeft(net.datastructures.Position)
     */
    public boolean hasLeft(Position<E> v) throws InvalidPositionException {
        PNode<E> arg = validate(v);
        return ( arg.left != null && arg.left.element() != null );
    }

    /* (non-Javadoc)
     * @see net.datastructures.BinaryTree#hasRight(net.datastructures.Position)
     */
    public boolean hasRight(Position<E> v) throws InvalidPositionException {
        PNode<E> arg = validate(v);
        return ( arg.right != null && arg.right.element() != null );
    }

    /* (non-Javadoc)
     * @see net.datastructures.BinaryTree#left(net.datastructures.Position)
     */
    public Position<E> left(Position<E> v) throws InvalidPositionException,
                                                BoundaryViolationException {
        PNode<E> arg = validate(v);
        return arg.left;
    }

    /* (non-Javadoc)
     * @see net.datastructures.BinaryTree#right(net.datastructures.Position)
     */
    public Position<E> right(Position<E> v) throws InvalidPositionException,
                                                BoundaryViolationException {
        PNode<E> arg = validate(v);
        return arg.right;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#isEmpty()
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#isExternal(net.datastructures.Position)
     */
    public boolean isExternal(Position<E> v) throws InvalidPositionException {
        PNode<E> arg = validate(v);

        if (arg.left == null && arg.right == null){ // CHECKS
            return true;
        }

        return false;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#isInternal(net.datastructures.Position)
     */
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        return !this.isExternal(v); // See isExternal
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#isRoot(net.datastructures.Position)
     */
    public boolean isRoot(Position<E> v) throws InvalidPositionException, EmptyTreeException { // o(1)
        //PNode<E> arg = validate(v);

        if ( root != null ){
            return ( root.equals(v) );
        }else{
            throw new EmptyTreeException( "Empty Tree!" ); // @@@
        } 
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#parent(net.datastructures.Position)
     */
    
    public Position<E> parent(Position<E> v) throws InvalidPositionException,
                                                BoundaryViolationException {
        PNode<E> ans = validate(v);

        if ( isRoot(ans) ){
            // This isn't going to work? @@@
            throw new BoundaryViolationException("Root has no parent!"); //
        }

        return ans.parent;
    }
    //

    /* (non-Javadoc)
     * @see net.datastructures.Tree#replace(net.datastructures.Position, java.lang.Object)
     */
    public E replace(Position<E> v, E e) throws InvalidPositionException { // o(n)
        // Replace E value
        PNode<E> n = validate(v);

        E temp = n.setElement(e);
        return temp;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#root()
     */
    public Position<E> root() throws EmptyTreeException {
        if (this.isEmpty()){
            throw new EmptyTreeException("The tree is empty!");
        }

        return this.root;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Tree#size()
     */
    public int size() {            
        return size;
    }

    public PNode<E> validate(Position<E> arg) throws InvalidPositionException {
        if ( !(arg instanceof PNode) ){ // CONFIRM CHECK!! @@@
            throw new InvalidPositionException("Wrong Type!");
        }
        PNode<E> node = (PNode<E>)arg; // safe cast
 
        if ( node != null && node.getParent() == node ){
            throw new IllegalArgumentException("P can't be its own parent!");
        }
        
        return node;
    }
    public PNode<E> checkPos(Position<E> arg) throws InvalidPositionException {
        // Because I don't ALWAYS care if node is its own parent
        if (!(arg instanceof PNode)){
            throw new InvalidPositionException("Wrong type! Given "+arg  );
        }
        PNode<E> node = (PNode<E>)arg; // safe cast
        
        return node;
    }
    
    // ~~~~~~~~~~~~~~~~~~ ITERATORS ~~~~~~~~~~~~~~~~~~~
    
    /* (non-Javadoc)
     * @see net.datastructures.Tree#children(net.datastructures.Position)
     */
    @TimeComplexity("o(1)") @TimeComplexityAmortized("o(1)") @TimeComplexityExpected("o(1)")
    public Iterable<Position<E>> children(Position<E> v)
                                           throws InvalidPositionException {
        PNode<E> p = validate(v);
        LinkedSequence<E> ans = new LinkedSequence<>();
        ans.addLast(p.left.element());
        ans.addLast(p.right.element());

        return ans.positions();    // O(n) @@@ OPTIMIZE ME!
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.Tree#positions()
     */
    public Iterable<Position<E>> positions() {  
        LinkedSequence<E> ans = new LinkedSequence<E>();
        
        ///ans.
        return ans.positions();
//throw new UnsupportedOperationException("WIP@@@");
    }
    
    /* (non-Javadoc)
     * @see net.datastructures.Tree#iterator()
     */
    public Iterator<E> iterator() {  // o(n)
        throw new UnsupportedOperationException("WIP@@@");
    }
    
    // Helper functions
    
    public Position<E> sibling(Position<E> arg) throws InvalidPositionException {
        PNode<E> p = validate(arg);
        
        PNode<E> parent = p.getParent();
        if (parent == null){
            return null; // P is root(?)
        }
        if (p.equals( parent.getLeft() )){
            return parent.getRight(); // Might be null
        }else{
            return parent.getLeft(); // Might be null
        }
    }
    
    public int numChildren(Position<E> arg) throws InvalidPositionException {
        PNode<E> p = validate(arg);
        int count = 0;
        
        if (hasLeft(p)){
            count++;
        }
        if (hasRight(p)){
            count++;
        }
            
        return count;
    }
    
    
    public PNode<E> maximum(Position<E> arg) throws InvalidPositionException {
        PNode<E> v = validate(arg);
        if (v.isExternal()){
            // Start over from root maybe?
            throw new InvalidPositionException("Position isn't INTERNAL!");
        }

        while(v.right.isInternal()){
            v = v.right; // Find maximum @ right-most external node
        }
        return v;
    }

    public PNode<E> minimum(Position<E> arg) throws InvalidPositionException {
        PNode<E> v = validate(arg);
        if (v.isExternal()){
            throw new InvalidPositionException("Position isn't INTERNAL!");
        }

        while(v.left.isInternal()){
            v = v.left; // Find maximum @ left-most external node
        }
        return v;
    }

    /*
    public PNode<E> successor(Position<E> arg) throws InvalidPositionException {
        PNode<E> v = checkPos(arg);
        // Start the checks
        // If v has kids, use rightmost
        // else look at parents
        if (v.right != null){
            while (v.right.isInternal()){
                v = v.right;
            }
            return v;
        }else{ // Go up and then right
            //while(v.p)
            PNode<E> p = v.parent;

            while(p != null){
                p.right = v;
                v = p;
                p = v.parent;
            }
            if (p == null){
                return null; // Nothing suitable found
            }else{
                return p ;// @@@
            }
        }
    }
    //*/
        
}
