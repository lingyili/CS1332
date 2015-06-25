import java.util.*;

/**
 * Created by lingyi on 5/30/15.
 * @author Lingyi Li
 * @version 1.0
 */
public class BST<E extends Comparable> implements BinaryTree<E> {
    private Node root;
    private int count;
    public BST() {
        root = null;
        count = 0;
    }
    private class Node<E extends Comparable<E>> {
        E data;
        Node left;
        Node right;
        private Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }
    }
    private class BSTIterator implements Iterator<E> {
        List<E> list;
        int indexForHas;
        int indexForNext;
        public BSTIterator(List<E> assignedList) {
            list = assignedList;
            indexForHas = 0;
            indexForNext = 0;
        }
        public boolean hasNext() {
            if (list == null) {
                return false;
            }
            if (indexForNext >= list.size()) {
                return false;
            }
            indexForHas++;
            return true;
        }
        public E next() {
            if (list == null) {
                return null;
            }
            if (indexForNext >= list.size()) {
                return null;
            }
            E temp = list.get(indexForNext);
            indexForNext++;
            return temp;
        }
    }
    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added.
     * Runs in O(log n) expected time, may be linear time in worst case
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        Node newNode = new Node(item);
        if (item == null) {
            return false;
        }
        if (root == null) {
            root = newNode;
            count++;
            return true;
        } else if (!contains(item)) {
            if (insert(newNode, root)) {
                count++;
                return true;
            }
        }
        return false;
    }

    private boolean insert(Node newNode, Node node) {
        if (node == null) {
            return false;
        }
        int compare = newNode.data.compareTo((E) node.data);
        if (compare < 0) {
            if (node.left == null) {
                node.left = newNode;
                return true;
            } else {
                return insert(newNode, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = newNode;
                return true;
            } else {
                return insert(newNode, node.right);
            }
        }
    }

    /**
     * returns the maximum element held in the tree.  null if tree is empty.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @return maximum item or null if empty
     */
    public E max() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.right != null) {
                currentNode = currentNode.right;
            }
            return (E)currentNode.data;
        }
    }

    /**
     * returns the number of items in the tree
     * runs in O(1)
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * runs in O(1)
     *
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        if (size() == 0 || root == null) {
            return true;
        }
        return false;
    }

    /**
     * runs in O(log(n))
     * @return the minimum element in the tree or null if empty
     */
    public E min() {
        if (isEmpty()) {
            return null;
        } else {
            Node currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return (E)currentNode.data;
        }
    }

    /**
     * Checks for the given item in the tree.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        if (item == null) {
            return false;
        }
        if (isEmpty()) {
            return false;
        } else {
            return ifContains(root, item);
        }
    }
    private boolean ifContains(Node currentNode, E item) {
        if (currentNode != null) {
            int compare = item.compareTo((E)currentNode.data);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return ifContains(currentNode.left, item);
            } else {
                return ifContains(currentNode.right, item);
            }
        }
        return false;
    }

    /**
     * removes the given item from the tree
     * runs in O(log n) expected, may be linear in worst case
     * use in-order successor if necessary
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item) {
        if (item == null) {
            return false;
        }
        if (isEmpty()) {
            return false;
        } else if (!contains(item)) {
            return false;
        } else {
            return removeHelper(root, item, null);
        }
    }
    private boolean removeHelper(Node currentNode, E item, Node previousNode) {
        if (currentNode == null) {
            throw new NullPointerException("can not find data");
        } else {
            int compare = item.compareTo(currentNode.data);
            if (compare == 0) {
                //leaf node
                if (currentNode.left == null && currentNode.right == null) {
                    if (currentNode == root) {
                        root = null;
                        count--;
                        return true;
                    }
                    if (previousNode.right == currentNode) {
                        previousNode.right = null;
                        count--;
                        return true;
                    } else if (previousNode.left == currentNode) {
                        previousNode.left = null;
                        count--;
                        return true;
                    }
                } else if (currentNode.left != null && currentNode.right == null) {
                    if (currentNode == root) {
                        currentNode = currentNode.left;
                        root = currentNode;
                        count--;
                        return true;
                    } else if (previousNode.right == currentNode) {
                        previousNode.right = currentNode.left;
                        count--;
                        return true;
                    } else if (previousNode.left == currentNode) {
                        previousNode.left = currentNode.left;
                        count--;
                        return true;
                    }
                } else if (currentNode.right != null && currentNode.left == null) {
                    if (currentNode == root) {
                        currentNode = currentNode.right;
                        root = currentNode;
                        count--;
                        return true;
                    } else if (previousNode.right == currentNode) {
                        previousNode.right = currentNode.right;
                        count--;
                        return true;
                    } else if (previousNode.left == currentNode) {
                        previousNode.left = currentNode.right;
                        count--;
                        return true;
                    }
                    //two subtrees
                } else if (currentNode.right != null && currentNode.left != null) {
                    if (currentNode == root) {
                        Node temp = rightLeast(currentNode);
                        currentNode.data = temp.data;
                        count--;
                        return true;
                    } else if (previousNode.right == currentNode) {
                        Node temp = rightLeast(currentNode);
                        currentNode.data = temp.data;
                        currentNode.right = temp.right;
                        count--;
                        return true;
                    } else if (previousNode.left == currentNode) {
                        Node temp = rightLeast(currentNode);
                        currentNode.data = temp.data;
                        currentNode.right = temp.right;
                        count--;
                        return true;
                    }
                }
            } else if (compare < 0) {
                return removeHelper(currentNode.left, item, currentNode);
            } else {
                return removeHelper(currentNode.right, item, currentNode);
            }
        }
        return false;
    }
    private Node rightLeast(Node node) {
        Node previousNode = node;
        if (node != null) {
            Node right = node.right;
            while (right.left != null) {
                previousNode = right;
                right  = right.left;
            }
            if (previousNode == node) {
                return right;
            } else {
                previousNode.left = null;
            }
            return right;
        }
        return null;
    }

    /**
     * returns an iterator over this collection
     *
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        List<E> list = getInOrder();
        BSTIterator iter = new BSTIterator(list);
        return iter;
    }

    /**
     * Runs in linear time
     * @return a list of the data in post-order traversal order
     */
    public List<E> getPostOrder() {
        List<E> list = new ArrayList<>();
        if (isEmpty()) {
            return list;
        }
        postOrderRecursive(root, list);
        return list;
    }
    private void postOrderRecursive(Node node, List<E> list) {
        if (node == null) {
            return;
        }
        postOrderRecursive(node.left, list);
        postOrderRecursive(node.right, list);
        list.add((E) node.data);
    }

    /**
     * Runs in linear time
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder() {
        Queue<Node> nodequeue = new ArrayDeque<>();
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        if (root != null)
            nodequeue.add(root);
            list.add((E)root.data);
        while (!nodequeue.isEmpty()) {
            Node next = nodequeue.remove();
            if (next.left != null)
            {
                nodequeue.add(next.left);
                list.add((E)next.left.data);
            }
            if (next.right != null)
            {
                nodequeue.add(next.right);
                list.add((E)next.right.data);
            }
        }
        return list;
    }


    /**
     * Runs in linear time
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getPreOrder() {
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        preOrderRecursive(root, list);
        return list;
    }
    private void preOrderRecursive(Node node, List<E> list) {
        if (node == null) {
            return;
        }
        list.add((E)node.data);
        preOrderRecursive(node.left,list);
        preOrderRecursive(node.right,list);
    }

    /**
     * Runs in linear time
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getInOrder() {
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        inOrderRecursive(root,list);
        return list;
    }
    private void inOrderRecursive(Node node, List<E> list) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left, list);
        list.add((E) node.data);
        inOrderRecursive(node.right, list);
    }

    /**
     * Runs in linear time
     * Removes all the elements from this tree
     */
    public void clear() {
        count = 0;
        root = null;
    }
}
