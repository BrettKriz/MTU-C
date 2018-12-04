package net.datastructures;

/**
 * An interface for a linked binary tree.
  * @author Michael Goodrich
  * @see BinaryTree
  */
public interface LinkTree<E> extends BinaryTree<E> {
  /** Adds a root node to an empty tree */
  public Position<E> addRoot(E e) throws NonEmptyTreeException;
  /** Inserts a left child at a given node. */
  public Position<E> insertLeft(Position<E> v, E e) throws InvalidPositionException;
  /** Inserts a right child at a given node. */
  public Position<E> insertRight(Position<E> v, E e) throws InvalidPositionException;
  /** Removes a node with zero or one child. */
  public E remove(Position<E> v) throws InvalidPositionException;
  /** Attaches two trees to be subtrees of an external node. */
  public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException;
}
