import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by lingyi on 7/10/15.
 */
public class SkipListTest {
    Random random;
    Map<Integer, Integer> skiplist;
    private ArrayList<Integer> generateKey(int i) {
        random = new Random();
        ArrayList<Integer> arr = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            arr.add(random.nextInt(100));
        }
        return arr;
    }
    @Before
    public void setUp() {
        skiplist = new SkipList<>();
    }
    @Test
    public void testAddOne() {
        skiplist.put(5, 6);
    }
    @Test
    public void testAddEmpty() {
        assertEquals(null, skiplist.put(null, null));
    }
    @Test
    public void testAddMany() {
        ArrayList<Integer> list = generateKey(9);
        for (int i = 0; i <list.size(); i++) {
            skiplist.put(list.get(i),i);
        }
    }
    @Test
    public void testRemove() {
        ArrayList<Integer> list = generateKey(7);
        for (int i = 0; i <list.size(); i++) {
            skiplist.put(list.get(i),i);
        }
        skiplist.remove(list.get(4));
        assertFalse(skiplist.containsKey(4));
    }

}