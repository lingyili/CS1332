import java.util.Comparator;

/**
 * Created by lingyi on 6/29/15.
 */
public class SortedList implements Container {
    Comparator<WorkOrder> comparator;
    WorkOrder[] arr;
    int index;
    int in;
    public SortedList() {
        arr = new WorkOrder[10];
        index = 0;
        in = 0;
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
        if (index >= arr.length) {
            regrow();
        }
        arr[index] = wo;
        index++;
    }

    private void regrow() {
        WorkOrder[] old = arr;
        arr = new WorkOrder[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            arr[i] = old[i];
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
        if (index <= 10) {
            WorkOrder[] w = {arr[0]};
            insertionSort(1);
        } else {
            quickSort(arr);
        }

    }

    private void quickSort(WorkOrder[] w) {
        quickSort(w, 0, index - 1);
    }

    private void quickSort(WorkOrder[] w, int min, int max) {
        if (max - min > 0) {
            int partitionIndex = findPartition(w, min, max);
            quickSort(w, min, partitionIndex -1);
            quickSort(w, partitionIndex + 1, max);
        }
    }

    private int findPartition(WorkOrder[] w, int min, int max) {
        WorkOrder partitionElement;
        WorkOrder middle = w[(min + max)/2];
        WorkOrder temp;
        if (comparator.compare(middle, w[min]) > 0) {
            if (comparator.compare(middle, w[max]) < 0) {
                partitionElement = middle;
                temp = w[min];
                w[min] = partitionElement;
                w[(min + max)/2] = temp;
            } else {
                partitionElement = w[max];
                temp = w[min];
                w[min] = partitionElement;
                w[max] = temp;
            }
        } else {
            if (comparator.compare(w[min], w[max]) > 0) {
                partitionElement = w[min];
            } else {
                partitionElement = w[max];
                temp = w[min];
                w[min] = partitionElement;
                w[max] = temp;
            }
        }
        int left = min+1;
        int right = max;
        while (left < right) {
            while (comparator.compare(w[left], partitionElement) <= 0 && left < right) {
                left++;
            }
            while (comparator.compare(w[right], partitionElement) > 0) {
                right--;
            }
            if (left < right) {
                temp = w[left];
                w[left] = w[right];
                w[right] = temp;
            }
        }
        temp = w[min];
        w[min] = w[right];
        w[right] = temp;
        return right;
    }

    private void insertionSort(int next) {
        int i = next;
        WorkOrder temp;
        if (i >= index) {
            return;
        }
        while (i - 1 >= 0) {
            if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                temp = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = temp;
            }
            i--;
        }
        insertionSort(next + 1);
    }

    /**
     * This method will print to stdout the contents of the container
     * Useful for debugging.
     */
    @Override
    public void dumpContainer() {
        for (WorkOrder w : arr) {
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
        if (hasNext()) {
            return arr[in++];
        }
        return null;
    }
}
