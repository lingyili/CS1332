import java.util.*;

/**
 * Created by lingyi on 6/21/15.
 */
public class CuckooMap<K extends Hashable, V> implements Map<K,V> {
    final double MAX_LOADFACTOR = 0.5;
    Random randomGenerator = new Random();
    int p1 = 1;
    int p2 = 1;
    private class Bucket<K extends Comparable, V> implements Map.Entry<K, V>, Comparable {
        K key;
        V value;
        public Bucket(K key, V value) {
            this.key = key;
            this.value = value;
        }
        /**
         * Compares this object with the specified object for order.
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         */
        public int compareTo(Object o) {
            Bucket bucket = (Bucket) o;
            if (this.key.compareTo(bucket.getKey()) == 0) {
                return 0;
            } else if (this.key.compareTo(bucket.getKey()) < 0) {
                return -1;
            }
            return 1;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         */
        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }
    int count1;
    int count2;
    int count;
    private Bucket<K, V>[] table1;
    private  Bucket<K, V>[] table2;
    public CuckooMap() {
        table1 = new Bucket[11];
        table2 = new Bucket[11];
        count =0;
        count1 = 0;
        count2 =0;
    }
    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return count;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public boolean containsKey(Object key) {
        if (isEmpty()) {
            return false;
        }
        int index = (((K)key).hash1() ^ p1) % table1.length;
        if (index < table1.length) {
            if (table1[index] != null && table1[index].getKey().compareTo(key) == 0) {
                return true;
            }
        }
        index = (((K)key).hash2() ^ p2) % table2.length;
        if (index < table2.length) {
            if (table2[index] != null && table2[index].getKey().compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     * @throws ClassCastException   if the value is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *                              map does not permit null values
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public boolean containsValue(Object value) {
        if (isEmpty()) {
            return false;
        }
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null && table1[i].getValue() == value) {
                return true;
            }
        }
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null && table2[i].getValue() == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * <p>
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     * <p>
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public V get(Object key) {
        if (isEmpty()) {
            return null;
        }
        int index = (((K)key).hash1() ^ p1) % table1.length;
        if (table1[index] != null && table1[index].getKey().compareTo(key) == 0) {
            return table1[index].getValue();
        } else if (table2[index] != null && table2[index].getKey().compareTo(key) == 0){
            index = (((K)key).hash2() ^ p2) % table2.length;
            return table2[index].getValue();
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>,
     * if the implementation supports <tt>null</tt> values.)
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of the specified key or value
     *                                       prevents it from being stored in this map
     * @throws NullPointerException          if the specified key or value is null
     *                                       and this map does not permit null keys or values
     * @throws IllegalArgumentException      if some property of the specified key
     *                                       or value prevents it from being stored in this map
     */
    public V put(K key, V value) {
        if (loadfactor(table1) > MAX_LOADFACTOR || loadfactor(table2) > MAX_LOADFACTOR) {
            regrow();
        }
        int index = (key.hash1() ^ p1) % table1.length;
        Bucket<K, V> newBucket = new Bucket(key, value);
        Bucket<K, V> temp;
        if ( table1[index] != null && key.compareTo(table1[index].getKey()) == 0) {
            V oldValue = table1[index].getValue();
            table1[index].setValue(value);
            return oldValue;
        } else if (table1[index] != null) {
            temp = table1[index];
            table1[index] = newBucket;
            replace(temp, table2, 1);
            return null;
        } else {
            table1[index] = newBucket;
            count++;
            count1++;
            return null;
        }
    }

    private void replace(Bucket<K, V> buck, Bucket[] table, int time) {
        if (time > (table1.length + table2.length)) {
            regrow();
        }
        int index;
        Bucket<K, V> temp;
        if (table.equals(table2)) {
            index = (buck.getKey().hash2() ^ p1) % table2.length;
            if (table2[index] != null && buck.getKey().compareTo(table2[index].getKey()) == 0) {
                table2[index].setValue(buck.getValue());
            }
                if (table2[index] != null) {
                    temp = table2[index];
                    table2[index] = buck;
                    replace(temp, table1,time + 1);
                } else {
                    table2[index] = buck;
                    count++;
                    count2++;
                }
        } else {
            index = (buck.getKey().hash1() ^ p2) % table1.length;
                if (table2[index] != null && buck.getKey().compareTo(table2[index].getKey()) == 0) {
                    table2[index].setValue(buck.getValue());
                }
                if (table1[index] != null) {
                    temp = table1[index];
                    table1[index] = buck;
                    replace(temp, table2, time + 1);
                } else {
                    table1[index] = buck;
                    count++;
                    count1++;
                }
        }
    }

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling {@link #put(Object, Object) put(k, v)} on this map once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     *
     * @param m mappings to be stored in this map
     * @throws UnsupportedOperationException if the <tt>putAll</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of a key or value in the
     *                                       specified map prevents it from being stored in this map
     * @throws NullPointerException          if the specified map is null, or if
     *                                       this map does not permit null keys or values, and the
     *                                       specified map contains null keys or values
     * @throws IllegalArgumentException      if some property of a key or value in
     *                                       the specified map prevents it from being stored in this map
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry entry : m.entrySet()) {
            K key = (K) entry.getKey();
            V value = (V) entry.getValue();
            put(key, value);
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     * <p>
     * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     * <p>
     * <p>If this map permits null values, then a return value of
     * <tt>null</tt> does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to <tt>null</tt>.
     * <p>
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the key is of an inappropriate type for
     *                                       this map
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified key is null and this
     *                                       map does not permit null keys
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    public V remove(Object key) {
        if (isEmpty()) {
            return null;
        }
        V value;
        int index = (((K)key).hash1() ^ p1) % table1.length;
        if (table1[index] != null && table1[index].getKey().compareTo(key) == 0) {
            value = table1[index].getValue();
            table1[index] = null;
            return value;
        }
        index = (((K)key).hash2() ^ p2) % table2.length;
        if (table2[index] != null && table2[index].getKey().compareTo(key) == 0) {
            value = table2[index].getValue();
            table2[index] = null;
            return value;
        }
        return null;
    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this map
     */
    public void clear() {
        table1 = new Bucket[11];
        table2 = new Bucket[11];
        count =0;
        count1 = 0;
        count2 =0;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        if (isEmpty()) {
            return null;
        }
        Set newSet = new TreeSet<>();
        for (int i = 0 ; i < table1.length; ++i) {
            if (table1[i] != null) {
                newSet.add(table1[i].getKey());
            }
        }
        for (int i = 0 ; i < table2.length; ++i) {
            if (table2[i] != null) {
                newSet.add(table2[i].getKey());
            }
        }
        return newSet;
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a collection view of the values contained in this map
     */
    public Collection<V> values() {
        if (isEmpty()) {
            return null;
        }
        Collection arr = new ArrayList<>();
        for (int i = 0 ; i < table1.length; ++i) {
            if (table1[i] != null) {
               arr.add(table1[i].getValue());
            }
        }
        for (int i = 0 ; i < table2.length; ++i) {
            if (table2[i] != null) {
                arr.add(table2[i].getValue());
            }
        }
        return arr;
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Entry<K, V>> entrySet() {
        if (isEmpty()) {
            return null;
        }
        Set newSet = new TreeSet<>();
        for (int i = 0 ; i < table1.length; ++i) {
            if (table1[i] != null) {
                newSet.add(table1[i]);
            }
        }
        for (int i = 0 ; i < table2.length; ++i) {
            if (table2[i] != null) {
                newSet.add(table2[i]);
            }
        }
        return newSet;
    }

    /**
     * This is for growing the table
     */
    private void regrow() {
        Bucket<K, V> [] old = table1;
        table1 = new Bucket[old.length * 2 + 1];
        Bucket<K, V>[] old2 = table2;
        table2 = new Bucket[old2.length * 2 + 1];
        count1 = 0;
        count2 = 0;
        count = 0;
        p1 = randomGenerator.nextInt(4);
        p2 = randomGenerator.nextInt(4);
        for (int i = 0 ; i < old.length; ++i) {
            if (old[i] != null) {
                put(old[i].key, old[i].value);
            }
        }
        for (int i = 0 ; i < old2.length; ++i) {
            if (old2[i] != null) {
                put(old2[i].key, old2[i].value);
            }
        }
    }
    private double loadfactor(Bucket[] table) {
        if(table == table1) {
            return (double) count1 / table.length;
        } else {
            return (double) count2 / table.length;
        }
    }
    public void showInside() {
        System.out.println("Size: " + size() + " TABLE1: " + table1.length + " TABLE2: " + table2.length);
        System.out.println("Table1: ");
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null) {
                System.out.println(" The key is:  " + table1[i].getKey() + " value is:  " + table1[i].getValue() + " index is:  " + i);
            }
        }
        System.out.println("Table2: ");
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null) {
                System.out.println(" the key is:  " + table2[i].getKey() + " value is:  " + table2[i].getValue() + " the index is: " + i);
            }
        }
    }
}

