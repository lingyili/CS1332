import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Iterator;
/**
 * Created by lingyi on 5/20/15.
 */
public class LazyDeleteLinkedList<T> implements LazyDeleteList<T> {
    Node head;
    Node tail;
    int count;
    Stack<Node> deleted;
    int deletedNode;
    private class Node {
        T data;
        Node next;
        Node prev;
        boolean isDeleted;
        public Node(T data) {
            this.data = data;
            prev = null;
            next = null;
            isDeleted = false;
        }
    }
    public class LazyDeleteLinkedListIterator implements Iterator<T> {
        Node current;
        public LazyDeleteLinkedListIterator(Node head){
            current = head;
        }
        public boolean hasNext() {
            current = current.next;
            return current != null;
        }
        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
    public LazyDeleteLinkedList() {
        head = null;
        tail = null;
        count = 0;
        deletedNode = 0;
        deleted = new Stack<>();
    }
    /**
     * Checks whether this list is empty
     * This should be an O(1) operation
     * @return true if there are no undeleted elements in the list, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Get the number of undeleted elements in the list
     * This should be an O(1) operation
     * @return the count of undeleted elements
     */
    public int size() {
        return count;
    }

    /**
     * Add a new element to the list.  This element is placed into a deleted node,
     * or if none exists at the end of the list.
     * This should be an O(1) operation for all cases
     * @param data the data element to add to the list
     */
    public void add(T data) {
        Node newNode = new Node(data);
        if (isEmpty() && deleted.empty()) {
            head = newNode;
            tail = newNode;
        } else if (!deleted.empty()) {
            Node tempNode = deleted.pop();
            tempNode.data = data;
            tempNode.isDeleted = false;
            deletedNode--;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        count++;

    }

    /**
     * Remove all the deleted nodes and ensure list contains only undeleted nodes.
     * This is an O(n)
     * @return the number of nodes removed (should count all nodes
     *              that are removed from the list
     */
    public int compress() {
        Node currentNode = head;
        int num = 0;
        while (currentNode != null) {
            if (currentNode.isDeleted) {
                if (head == tail) {
                    clear();
                }else if(currentNode == head) {
                    currentNode.next.prev = null;
                    head = currentNode.next;
                } else if(currentNode == tail) {
                    currentNode.prev.next = null;
                    tail = currentNode.prev;
                } else {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                }
                num++;
            }
            currentNode = currentNode.next;
        }
        deletedNode = 0;
        deleted.clear();
        return num;
    }

    /**
     * Remove everything from the list
     * This is an O(1) operation (you may assume the Stack's clear operation is O(1) )
     */
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Checks whether the list contains a certain value
     * This is an O(n) operation
     * @param data the item to check for
     * @return true if list has this item and it is undeleted, false otherwise
     */
    public boolean contains(T data) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.data.equals(data) && !currentNode.isDeleted) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * Removes an item from the list using lazy deletion (the node is marked deleted, but
     * not actually removed.  If duplicate items are present, this removes the first one found.
     * This is an O(n) operation based on having to find the appropriate item
     *
     * @param data the item being deleted
     * @return true if item was in the list and undeleted, false otherwise.
     */
    public boolean remove(T data) {
        if (isEmpty()) {
            return false;
        }
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.data.equals(data) && !currentNode.isDeleted) {
                currentNode.isDeleted = true;
                deleted.push(currentNode);
                deletedNode++;
                count--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * This is an O(1) operation (You may assume that Stack.size() is O(1) )
     * @return the number of deleted nodes in the list that are available for use
     */
    public int deletedNodeCount() {
        return deletedNode;
    }

    /**
     * This is an O(n) operation due to the compress function.
     * @return the iterator for this collection.  Asking for an iterator causes a compress of the collection.
     */
    public Iterator<T> iterator() {
        compress();
        return new LazyDeleteLinkedListIterator(head);
    }
}
