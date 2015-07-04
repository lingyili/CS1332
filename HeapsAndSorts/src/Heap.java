import java.util.Comparator;

/**
 * Created by lingyi on 6/29/15.
 */
public class Heap implements Container {
    Comparator<WorkOrder> comparator;
    WorkOrder[] heap;
    int index;
    int in;
    public Heap() {
        heap = new WorkOrder[10];
        index = 1;
        in = 1;
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
        comparator = comp;
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
        if (index >= heap.length) {
            regrow();
        }
        heap[index] = wo;
        index++;
        arrange();
    }

    private void regrow() {
        WorkOrder[] old = heap;
        heap = new WorkOrder[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            heap[i] = old[i];
        }
    }

    private void makeHeap(int i) {
        if (i != 1) {
            int parent;
            if (i % 2 == 0) {
                parent = i / 2;

            } else {
                parent = (i - 1) / 2;
            }
            WorkOrder temp;
            if (parent != i) {
                if (comparator.compare(heap[parent], heap[i]) < 0) {
                    temp = heap[parent];
                    heap[parent] = heap[i];
                    heap[i] = temp;
                }
                makeHeap(parent);
            }
        }
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
        makeHeap(index-1);
    }

    /**
     * This method will print to stdout the contents of the container
     * Useful for debugging.
     */
    @Override
    public void dumpContainer() {
        for (WorkOrder w : heap) {
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
        return !(in == index);
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
        if (!hasNext()) {
            return null;
        }
        WorkOrder max = heap[1];
        WorkOrder last = heap[--index];
        WorkOrder temp;
        heap[1] = last;
        int current = 1;
        while (current * 2 < index || current * 2 + 1 < index) {
            int compare = comparator.compare(heap[current*2], heap[current*2+1]);
            if (compare < 0) {
                if (comparator.compare(heap[current], heap[current * 2 + 1]) < 0) {
                    temp = heap[current];
                    heap[current] = heap[current * 2 + 1];
                    heap[current * 2 + 1] = temp;
                }
                current = current * 2 + 1;
            } else {
                if (comparator.compare(heap[current], heap[current * 2]) < 0) {
                    temp = heap[current];
                    heap[current] = heap[current * 2];
                    heap[current * 2] = temp;
                }
                current = current * 2;
            }

        }
        return max;

    }
}
