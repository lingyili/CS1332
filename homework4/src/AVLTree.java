import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
/**
 * Created by lingyi on 6/9/15.
 */
public class AVLTree<E extends Comparable> implements BSTree<E>{
    AVLNode root;
    int count;
    private class AVLNode {
        E data;
        AVLNode left;
        AVLNode right;
        public AVLNode(E item){
            data = item;
            left = null;
            right = null;
        }
        private int balance() {
            if (left == null && right == null) {
                return 0;
            }else if (left == null && right != null) {
                return -1 - right.getHeight();
            } else if (left != null && right == null) {
                return left.getHeight() - (-1);
            }
            return left.getHeight() - right.getHeight();
        }
        private int getHeight() {
            if (right == null && left != null) {
                return left.getHeight() + 1;
            } else if (right != null && left == null) {
                return right.getHeight() +1;
            } else if (right == null && right == null) {
                return 0;
            } else {
                if (right.getHeight() <= left.getHeight()) {
                    return left.getHeight() + 1;
                } else {
                    return right.getHeight() + 1;
                }
            }
        }
        private boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    public AVLTree() {
        root = null;
        count = 0;
    }
    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        if (item == null) {
            return false;
        }
        if (isEmpty()) {
            root = new AVLNode(item);
            count++;
            return true;
        }
        if (!contains(item)){
            boolean flag = addEntry(root, item);
            root = rebalance(root);
            return flag;
        }
        return false;
    }
    private boolean addEntry(AVLNode node, E item) {
        boolean flag;
        if (item.compareTo(node.data) < 0) {
            if (node.left != null) {
                flag = addEntry(node.left, item);
                node.left = rebalance(node.left);
                return flag;
            } else {
                node.left = new AVLNode(item);
                count++;
                return true;
            }
        } else {
            if (node.right != null) {
                flag = addEntry(node.right, item);
                node.right = rebalance(node.right);
                return flag;
            } else {
                node.right = new AVLNode(item);
                count++;
                return true;
            }
        }
    }
    private AVLNode rebalance(AVLNode node) {
        if (node == null) {
            return node;
        }
        if (node.balance() > 1) {
            if (node.left.balance() > 0) {
                node = rotateRight(node);
            } else {
                node = rotateLeftRight(node);
            }
        } else if (node.balance() < -1) {
            if (node.right.balance() < 0) {
                node = rotateLeft(node);
            } else {
                node = rotateRightLeft(node);
            }
        }
        return node;
    }
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot;
    }
    private AVLNode rotateRight(AVLNode node) {
        AVLNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot;
    }
    private AVLNode rotateLeftRight(AVLNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }
    private AVLNode rotateRightLeft(AVLNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    /**
     * returns the maximum element held in the tree.  null if tree is empty. O(log n)
     * @return maximum item or null if empty
     */
    public E max() {
        if (isEmpty()) {
            return null;
        } else {
            AVLNode currentNode = root;
            while (currentNode.right != null) {
                currentNode = currentNode.right;
            }
            return currentNode.data;
        }
    }

    /**
     * returns the number of items in the tree O(1) with variable
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * O(1)
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        if (count == 0 && root == null) {
            return true;
        }
        return false;
    }

    /**
     * O(log n)
     * @return the minimum element in the tree or null if empty
     */
    public E min() {
        if (isEmpty()) {
            return null;
        } else {
            AVLNode currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return currentNode.data;
        }
    }

    /**
     * Checks for the given item in the tree. O(log n)
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
    private boolean ifContains(AVLNode currentNode, E item) {
        if (currentNode != null) {
            int compare = item.compareTo(currentNode.data);
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
     * removes the given item from the tree O(log n)
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item) {
        if (item == null) {
            return false;
        }
        if (!contains(item)) {
            return false;
        }
        root = removeRecurse(item, root);
        count--;
        return true;
    }
    private AVLNode removeRecurse(E item, AVLNode node) {
        if (node == null) {
            return node;
        }
        if (item.compareTo(node.data) < 0) {
            node.left = removeRecurse(item, node.left);
        } else if (item.compareTo(node.data) > 0) {
            node.right = removeRecurse(item, node.right);
        } else if (node.hasTwoChildren()) {
            AVLNode succ = findMin(node);
            node.data = succ.data;
            node.right = removeRecurse(node.data, node.right);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }
        return rebalance(node);
    }
    private AVLNode findMin(AVLNode node) {
        AVLNode previousNode = node;
        if (node != null) {
            AVLNode right = node.right;
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
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        List<E> list = getInOrder();
        return list.iterator();
    }
    public List<E> getInOrder() {
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        inOrderRecursive(root,list);
        return list;
    }
    private void inOrderRecursive(AVLNode node, List<E> list) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left, list);
        list.add(node.data);
        inOrderRecursive(node.right, list);
    }

    /**
     * O(n)
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
    private void postOrderRecursive(AVLNode node, List<E> list) {
        if (node == null) {
            return;
        }
        postOrderRecursive(node.left, list);
        postOrderRecursive(node.right, list);
        list.add((E) node.data);
    }

    /**
     * O(n)
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder() {
        Queue<AVLNode> nodequeue = new ArrayDeque<>();
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        if (root != null)
            nodequeue.add(root);
        list.add(root.data);
        while (!nodequeue.isEmpty()) {
            AVLNode next = nodequeue.remove();
            if (next.left != null)
            {
                nodequeue.add(next.left);
                list.add(next.left.data);
            }
            if (next.right != null)
            {
                nodequeue.add(next.right);
                list.add(next.right.data);
            }
        }
        return list;
    }

    /**
     * O(n)
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
    private void preOrderRecursive(AVLNode node, List<E> list) {
        if (node == null) {
            return;
        }
        list.add(node.data);
        preOrderRecursive(node.left,list);
        preOrderRecursive(node.right,list);
    }

    /**
     * O(1) [ignore garbage collection costs]
     *
     * Removes all the elements from this tree
     */
    public void clear() {
        count = 0;
        root = null;
    }
}
