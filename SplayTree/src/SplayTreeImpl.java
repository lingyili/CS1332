/**
 * Created by lingyi on 7/1/15.
 */
public class SplayTreeImpl<T extends Comparable<? super T>> implements SplayTree<T> {
    Node root;
    int count;
    public class Node {
        T data;
        Node left;
        Node right;
        Node parent;
        public Node(T item) {
            data = item;
            left = null;
            right = null;
            parent = null;
        }
    }

    /**
     * checks if this tree is empty
     *
     * @return true if nothing in tree, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * get number of things in tree
     *
     * @return the number of things currently in the tree
     */
    public int size() {
        return count;
    }

    /**
     * inserts the item into this splay tree.  You will do a
     * normal insertion using binary search tree rules,
     * then do the splay operations to bring this item to
     * the root.  You are not required to check for duplicates
     * on insert.
     *
     * @param item the item to insert
     */
    public void insert(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            root = newNode;
            count++;
        } else {
            count++;
            newNode = add(newNode, root);
            root = splay(newNode, root);
        }
    }

    private Node splay(Node newNode, Node myroot) {
        if (newNode == myroot) {
            return myroot;
        }
        //zig
        if(newNode.parent == myroot) {
            if (newNode.parent.left == newNode) {
                newNode.parent.left = null;
                if (newNode.right != null) {
                    newNode.parent.left = newNode.right;
                    newNode.right.parent = newNode.parent;
                }
                newNode.right = newNode.parent;
                newNode.right.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
            } else if (newNode.parent.right == newNode) {
                newNode.parent.right = null;
                if (newNode.left != null) {
                    newNode.parent.right = newNode.left;
                    newNode.left.parent = newNode.parent;
                }
                newNode.left = newNode.parent;
                newNode.left.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
            }
        } else if (newNode.parent.parent == myroot) {
            //zig zig
            if (newNode.parent.left == newNode && newNode.parent.parent.left == newNode.parent) {
                newNode.parent.left = null;
                newNode.right = newNode.parent;
                newNode.parent.parent.left = null;
                newNode.parent.right = newNode.parent.parent;
                newNode.parent.parent.parent = newNode.parent;
                newNode.parent.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
                //zig zig
            } else if (newNode.parent.right == newNode && newNode.parent.parent.right == newNode.parent) {
                newNode.parent.right = null;
                newNode.left = newNode.parent;
                newNode.parent.parent.right = null;
                newNode.parent.left = newNode.parent.parent;
                newNode.parent.parent.parent = newNode.parent;
                newNode.parent.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
                //zig zag
            } else if (newNode.parent.left == newNode && newNode.parent.parent.right == newNode.parent) {
                newNode.parent.left = null;
                newNode.left = newNode.parent.parent;
                newNode.parent.parent.right = null;
                newNode.right = newNode.parent;
                newNode.parent.parent.parent = newNode;
                newNode.parent.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
            } else if (newNode.parent.right == newNode && newNode.parent.parent.left == newNode.parent) {
                newNode.parent.right = null;
                newNode.left = newNode.parent;
                newNode.parent.parent.left = null;
                newNode.right = newNode.parent.parent;
                newNode.parent.parent.parent = newNode;
                newNode.parent.parent = newNode;
                newNode.parent = null;
                myroot = newNode;
            }
        } else {
            if (newNode.parent.left == newNode && newNode.parent.parent.left == newNode.parent) {
                newNode.parent.left = null;
                newNode.right = newNode.parent;
                newNode.parent.parent.left = null;
                newNode.parent.right = newNode.parent.parent;
                if (newNode.parent.parent.parent.left == newNode.parent.parent) {
                    newNode.parent.parent.parent.left = newNode;
                } else {
                    newNode.parent.parent.parent.right = newNode;
                }
                Node grad = newNode.parent.parent.parent;
                newNode.parent.parent.parent = newNode.parent;
                newNode.parent.parent = newNode;
                newNode.parent = grad;
                return splay(newNode, root);
                //zig zig
            } else if (newNode.parent.right == newNode && newNode.parent.parent.right == newNode.parent) {
                newNode.parent.right = null;
                newNode.left = newNode.parent;
                newNode.parent.parent.right = null;
                newNode.parent.left = newNode.parent.parent;
                if (newNode.parent.parent.parent.left == newNode.parent.parent) {
                    newNode.parent.parent.parent.left = newNode;
                } else {
                    newNode.parent.parent.parent.right = newNode;
                }
                Node grad = newNode.parent.parent.parent;
                newNode.parent.parent.parent = newNode.parent;
                newNode.parent.parent = newNode;
                newNode.parent = grad;
                return splay(newNode, root);
                //zig zag
            } else if (newNode.parent.left == newNode && newNode.parent.parent.right == newNode.parent) {
                newNode.parent.left = null;
                newNode.left = newNode.parent.parent;
                newNode.parent.parent.right = null;
                newNode.right = newNode.parent;
                if (newNode.parent.parent.parent.left == newNode.parent.parent) {
                    newNode.parent.parent.parent.left = newNode;
                } else {
                    newNode.parent.parent.parent.right = newNode;
                }
                Node grad = newNode.parent.parent.parent;
                newNode.parent.parent.parent = newNode;
                newNode.parent.parent = newNode;
                newNode.parent = grad;
                return splay(newNode, root);
            } else if (newNode.parent.right == newNode && newNode.parent.parent.left == newNode.parent) {
                newNode.parent.right = null;
                newNode.left = newNode.parent;
                newNode.parent.parent.left = null;
                newNode.right = newNode.parent.parent;
                if (newNode.parent.parent.parent.left == newNode.parent.parent) {
                    newNode.parent.parent.parent.left = newNode;
                } else {
                    newNode.parent.parent.parent.right = newNode;
                }
                Node grad = newNode.parent.parent.parent;
                newNode.parent.parent.parent = newNode;
                newNode.parent.parent = newNode;
                newNode.parent = grad;
                return splay(newNode, root);
            }
        }
        return myroot;
    }

    private Node add(Node newNode, Node node) {
        if (node == null) {
            return null;
        }
        int compare = newNode.data.compareTo(node.data);
        if (compare < 0) {
            if (node.left == null) {
                node.left = newNode;
                newNode.parent = node;
                return newNode;
            } else {
                return add(newNode, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = newNode;
                newNode.parent = node;
                return newNode;
            } else {
                return add(newNode, node.right);
            }
        }
    }

    /**
     * remove this item from the tree.  if the item was actually
     * found, then remove it and return true, otherwise you
     * should return false.  On a remove, the parent of the
     * deleted node is rotated to the root.  if not found, the last
     * node visited is rotated to the root.
     *
     * @param item the item to remove
     * @return true if item found and removed, false if item not found
     */
    public boolean remove(T item) {
        if (isEmpty()) {
            return false;
        }
        if (contains(item)) {
            if (root.left == null && root.right == null) {
                root = null;
                count--;
                return true;
            } else if (root.right == null && root.left != null) {
                root = root.left;
                count--;
                return true;
            } else if (root.left == null && root.right != null) {
                root = root.right;
                count--;
                return true;
            } else {
                if (root.left.left == null && root.left.right == null) {
                    root.right.parent = root.left;
                    root = root.left;
                    count--;
                    return true;
                } else if (root.right.left == null && root.right.right == null) {
                    root.left.parent = root.right;
                    root = root.right;
                    count--;
                    return true;
                } else {
                    root.left.parent = null;
                    Node subroot = root.left;
                    subroot = findMinR(subroot);
                    if (subroot.right == null) {
                        subroot.right = root.right;
                        root = subroot;
                    }
                    count--;
                    return true;
                }
            }
        }
        return false;
    }

    private Node findMinR(Node subroot) {
        Node currentNode = subroot;
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        root = splay(currentNode, subroot);
        return currentNode;
    }

    /**
     * find the item with the minimum value in the tree.
     * A splay operation is performed on the minimum value
     * node to bring it to the root.
     *
     * @return the item with a minimum value
     */
    public T findMin() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            root = splay(currentNode, root);
            return currentNode.data;
        }
    }

    /**
     * find the item with the maximum value in the tree.
     * A splay operation is performed on the max value to
     * bring it to the root.
     *
     * @return the item with a maximum value.
     */
    public T findMax() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.right != null) {
                currentNode = currentNode.right;
            }
            root = splay(currentNode, root);
            return currentNode.data;
        }
    }

    /**
     * Finds the item in the tree.  If the item is found, then return true
     * if item is not in the tree return false.  You should splay on the found
     * node, or the last node that you realized item was not in tree.
     *
     * @param item the item to find
     * @return true if item is present in tree, false otherwise.
     */
    public boolean contains(T item) {
        if (item == null) {
            return false;
        }
        if (isEmpty()) {
            return false;
        } else {
            return ifContains(root, item);
        }
    }
    private boolean ifContains(Node currentNode, T item) {
        if (currentNode != null) {
            int compare = item.compareTo(currentNode.data);
            if (compare == 0) {
                root = splay(currentNode, root);
                return true;
            } else if (compare < 0) {
                if (currentNode.left != null) {
                    return ifContains(currentNode.left, item);
                }
                root = splay(currentNode,root);
            } else {
                if (currentNode.right != null) {
                    return ifContains(currentNode.right, item);
                }
                root = splay(currentNode, root);
            }
        }
        return false;
    }

    /**
     * Return the root value of the tree.  This is not a normal
     * operation but is provided for test purposes.  Just return
     * the value of the node that is currently at the root.  DO NOT
     * DO A SPLAY OPERATION.
     *
     * @return the root value
     */
    public T getRootValue() {
        if (isEmpty()) {
            return null;
        }
        return root.data;
    }

    /**
     * return the value of the leftmost child of the tree.  if there is no leftmost
     * child then return null.  DO NOT DO A SPLAY FOR THIS OPERATION
     *
     * @return the value of leftmost child or null if not present.
     */
    public T getLeftmostChild() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            if (currentNode == root) {
                return null;
            }
            return currentNode.data;
        }
    }

    /**
     * return the value of the rightmost child of the tree.  if there is no rightmost
     * child, then return null. DO NOT DO A SPLAY FOR THIS OPERATION
     *
     * @return the value of the rightmost child.
     */
    public T getRightmostChild() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.right != null) {
                currentNode = currentNode.right;
            }
            if (currentNode == root) {
                return null;
            }
            return currentNode.data;
        }
    }

}
