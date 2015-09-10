/*
 * SplayTree.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */

/**
 * This interface specifies the interface for a bottom-up splay tree
 * 
 * @author Robert
 * @version 1.0
 * 
 * @param<T>  the type of element put into the splay tree as data
 *
 */
public interface SplayTree<T extends Comparable<? super T>> {
    
    /**
     * checks if this tree is empty
     * @return true if nothing in tree, false otherwise
     */
    boolean isEmpty();
    
    /**
     * get number of things in tree
     * @return the number of things currently in the tree
     */
    int size();
    
    /**
     * inserts the item into this splay tree.  You will do a 
     * normal insertion using binary search tree rules, 
     * then do the splay operations to bring this item to 
     * the root.  You are not required to check for duplicates
     * on insert.
     * 
     * @param item the item to insert
     */
    void insert(T item);
    
    /**
     * remove this item from the tree.  if the item was actually
     * found, then remove it and return true, otherwise you
     * should return false.  On a remove, the parent of the 
     * deleted node is rotated to the root.  if not found, the last
     * node visited is rotated to the root.
     * @param item the item to remove
     * @return true if item found and removed, false if item not found
     */
    boolean remove(T item);
    
    /**
     * find the item with the minimum value in the tree.
     * A splay operation is performed on the minimum value
     * node to bring it to the root.
     * 
     * @return the item with a minimum value
     */
    T findMin();
    
    /**
     * find the item with the maximum value in the tree.
     * A splay operation is performed on the max value to 
     * bring it to the root.
     * 
     * @return the item with a maximum value.
     */
    T findMax();
  
    
    /**
     * Finds the item in the tree.  If the item is found, then return true
     * if item is not in the tree return false.  You should splay on the found 
     * node, or the last node that you realized item was not in tree.
     * @param item the item to find
     * @return true if item is present in tree, false otherwise.
     */
    boolean contains(T item);
    
    /**************************************************************
     * The following operations are NOT part of a normal splay tree.  We
     * need them to check your code.  None of the following operations
     * should perform a splay.  Leave the tree as-is and just return the values.
     ***************************************************************/
    
    /**
     * Return the root value of the tree.  This is not a normal 
     * operation but is provided for test purposes.  Just return
     * the value of the node that is currently at the root.  DO NOT
     * DO A SPLAY OPERATION.
     * @return the root value
     */
    T getRootValue();
    
    /**
     * return the value of the leftmost child of the tree.  if there is no leftmost
     * child then return null.  DO NOT DO A SPLAY FOR THIS OPERATION
     * 
     * @return the value of leftmost child or null if not present.
     */
    public T getLeftmostChild();
    
    /**
     * return the value of the rightmost child of the tree.  if there is no rightmost
     * child, then return null. DO NOT DO A SPLAY FOR THIS OPERATION
     * 
     * @return the value of the rightmost child.
     */
    public T getRightmostChild();
}
