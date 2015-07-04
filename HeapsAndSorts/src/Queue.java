import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by lingyi on 6/29/15.
 */
public class Queue implements Container {
    LinkedList<WorkOrder> queue;
    int index;
    Iterator<WorkOrder> list;
    public Queue() {
        queue = new LinkedList<>();
        index = 0;
    }
    /**
     * The comparator that the container will use to arrange the container
     * Note that for some containers like Queue and Stack, this
     * would be a nop.
     *
     * @param comp
     */
    @Override
    public void setComparator(Comparator comp) {
        return;
    }

    /**
     * Add a workorder to the container
     * The actual internal behavior for add would vary by container.
     * For a queue, it just adds at end.
     * For a heap, it does a normal heap insertion
     * For a sorted list, it just adds it to an array which will be sorted later
     *
     * @param wo
     */
    @Override
    public void add(WorkOrder wo) {
        queue.add(wo);
    }

    /**
     * Arranges the workorders in the required order
     * Uses the comparator if necessary
     * Some data structures may not need this method (like Queue)
     * Just make it a no-op for those structures.
     * For a queue, do nothing
     * For a sorted list, run the sorting algorithm
     * For a heap, run make_heap algorithm
     */
    @Override
    public void arrange() {
        return;
    }

    /**
     * This method will print to stdout the contents of the container
     * Useful for debugging.
     */
    @Override
    public void dumpContainer() {
        for (WorkOrder w : queue) {
            System.out.println("WorkOrder Number: " + w.getNumber());
            System.out.println("WorkOrder Name: " + w.getName());
            System.out.println("WorkOrder Hour: " + w.getHours());
            System.out.println("----------------------");
        }
    }

    /**
     * like isEmpty() in a normal collection
     *
     * @return true if there are any more items in this container
     */
    @Override
    public boolean hasNext() {
        return !(queue.size() == index);
    }

    /**
     * This method should return the next item according the rules of the container
     * Heap - acts same as removeMax() in a standard heap.
     * SortedList -- returns next item from the sorted order
     * Queue -- returns next FIFO item
     *
     * @return the next item in this container
     */
    @Override
    public WorkOrder next() {
        if (hasNext()) {
            return queue.get(index++);
        }
        return null;
    }
}
