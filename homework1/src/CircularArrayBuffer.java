/**
 * Created by lingyi on 5/16/15.
 */
public class CircularArrayBuffer<T> implements CircularBuffer<T> {
    BufferGrowMode currentMode;
    T[] data;
    int front;
    int rear;
    int num;
    /**
     * create a new CircularArrayBuffer object
     */
    public CircularArrayBuffer() {
        data = (T[]) new Object[10];
        front = 0;
        rear = 0;
        num = 0;
        currentMode = BufferGrowMode.REGROW;
    }
    /**
     * @return true if buffer is empty (has no elements) false otherwise
     */
    public boolean isEmpty() {
       if (size() == 0) {
           return true;
       }
        return false;
    }

    /**
     * @return the maximum number of elements that this buffer can hold without
     *         either a regrow or an overwrite
     */
    public int capacity() {
        return data.length;
    }

    /**
     * @return the number of actual elements in the buffer
     */
    public int size() {
        return num;
    }

    /**
     * Sets the correct action for the buffer to take when it is full
     *
     * @param newMode the mode to use when the buffer gets full
     */
    public void setMode(BufferGrowMode newMode) {
        currentMode = newMode;
    }

    /**
     * Adds an item to the buffer.  This may cause either a regrow operation or an overwrite of the information
     * in the buffer.
     *
     * @param item the item to add to the buffer
     */
    public void add(T item) {
        if (size() == data.length) {
            if (currentMode == BufferGrowMode.REGROW) {
                T[] temp = (T[]) new Object[data.length * 2];
                int j = front;
                for (int i = 0; i < data.length; i++) {
                    temp[i] = data[j];
                    j = (j + 1) % data.length;
                }
                front = 0;
                rear = data.length;
                data = temp;
            }
            if (currentMode == BufferGrowMode.OVERWRITE) {
                front = (front + 1) % data.length;
                num--;
            }
        }
        data[rear] = item;
        rear = (rear + 1) % data.length;
        num++;

    }

    /**
     * Removes the oldest item (front) from the buffer.  This also removes that item from the buffer.
     *
     *
     * @return the oldest item in the buffer,  or null if buffer is empty
     *
     */
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T item = data[front];
        front = (front + 1) % data.length;
        num--;
        return item;
    }

    /**
     * Tests whether this buffer has the requested item
     *
     * @param item the item to check for
     *
     * @return true if buffer has this item stored, false otherwise
     */
    public boolean contains(T item) {
        if (isEmpty()) {
            return false;
        }
        for (int i = front; i < size() + front; i++) {
            if (data[i % data.length].equals(item)) {
                return true;
            }
        }
        return false;
    }
}
