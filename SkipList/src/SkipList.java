import java.util.*;

/**
 * Created by lingyi on 7/10/15.
 */
public class SkipList<K extends Integer, V>  implements Map<K, V> {
    final int MAX_LEVEL = 8;
    private int num;
    Node header;
    Random random;
    private class Node implements Comparable<Node>, Map.Entry<K,V> {
        Node up;
        Node down;
        Node next;
        Node prev;
        Integer key;
        V data;
        int level;

        public Node(Integer key, V item, int level) {
            data = item;
            this.key = key;
            up = null;
            down = null;
            next = null;
            prev = null;
            this.level = level;
        }
        @Override
        public int compareTo(Node o) {
            return (Integer)key - (Integer)o.key;
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
            return (K)key;
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
            return data;
        }

        @Override
        public V setValue(V value) {
            V temp = data;
            data = value;
            return temp;
        }
    }
    public SkipList() {
        random = new Random();
        header = new Node(Integer.MIN_VALUE,null,0);
        num = 0;
    }

    @Override
    public int size() {
        return num;
    }

    @Override
    public boolean isEmpty() {
        return num == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if(key == null || num == 0){return false;}
        Node currentNode;
        int currentLevel;
        currentNode = header;
        currentLevel = header.level;
        while(currentLevel >= 0) {
            while (currentNode.next != null && currentNode.next.key.compareTo((K) key) < 0) {
                currentNode = currentNode.next;
            }
            if (currentNode.next != null && currentNode.next.key.compareTo((K) key) == 0) {
                return true;
            }
            if(currentLevel > 0){
                currentNode = currentNode.down;
            }
            currentLevel--;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if( num == 0){return false;}
        Node currentNode;
        int currentLevel;
        currentNode = header;
        currentLevel = header.level;
        while(currentLevel > 0) {
            currentNode = currentNode.down;
            currentLevel--;
        }
        while (currentNode.next != null) {
            if (currentNode.next != null &&currentNode.next.data != null && currentNode.next.data.equals((V) value)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }


    @Override
    public V get(Object key) {
        if(key == null || num == 0){return null;}
        Node currentNode;
        int currentLevel;
        currentNode = header;
        currentLevel = header.level;
        while(currentLevel >= 0) {
            while (currentNode.next != null && currentNode.next.key.compareTo((K) key) < 0) {
                currentNode = currentNode.next;
            }
            if (currentNode.next != null && currentNode.next.key.compareTo((K) key) == 0) {
                return currentNode.next.data;
            }
            if(currentLevel > 0){
                currentNode = currentNode.down;
            }
            currentLevel--;
        }
        return null;
    }


    @Override
    public V put(K key, V value) {
        if (key == null) {return null;}
        Node currentNode;
        V returnValue = null;
        random = new Random();
        boolean ht = true;
        int gen = 0;
        int currentLevel;
        currentNode = header;
        currentLevel = header.level;
        while(currentLevel >= 0) {
            while (currentNode.next != null && currentNode.next.key.compareTo((K) key) < 0) {
                currentNode = currentNode.next;
            }
            if (currentNode.next != null && currentNode.next.key.compareTo((K) key) == 0) {
                currentNode = currentNode.next;
                returnValue = currentNode.data;
                while(currentLevel >= 0) {
                    currentNode.data = value;
                    currentNode = currentNode.down;
                    currentLevel--;
                }
                return returnValue;
            }
            currentNode = currentNode.down;
            currentLevel--;
        }
        while (ht) {
            ht = random.nextBoolean();
            gen++;
        }
        if (gen > header.level) {
            for (int i = 0; i < gen - header.level; i++) {
                Node oldHead = header;
                Node nodeHead = new Node(Integer.MIN_VALUE, null,oldHead.level + 1);
                oldHead.up = nodeHead;
                header = nodeHead;
                header.down = oldHead;
            }
        }
        currentNode = header;
        currentLevel = header.level;
        Node upNode = null;
        while(currentLevel >= 0) {
            while (currentLevel > gen) {
                while (currentNode.next != null && currentNode.next.key.compareTo(key) < 0) {
                    currentNode = currentNode.next;
                }
                currentNode = currentNode.down;
                currentLevel--;
            }
            //System.out.println(currentNode);

            while (currentNode.next != null && currentNode.next.key.compareTo(key) < 0) {
                currentNode = currentNode.next;
            }
            Node old = currentNode.next;
            currentNode.next = new Node(key, value, currentLevel);
            if (old != null) {
                old.prev = currentNode.next;
            }
            currentNode.next.prev = currentNode;
            currentNode.next.next = old;

            if (upNode != null && currentLevel < gen) {
                upNode.down = currentNode.next;
                currentNode.next.up = upNode;
            }
            upNode = currentNode.next;
            if (currentLevel > 0) currentNode = currentNode.down;
            currentLevel--;
        }

        num++;
        return returnValue;
    }


    @Override
    public V remove(Object key) {
        if(key == null || num == 0){return null;}
        Node currentNode;
        int currentLevel;
        currentNode = header;
        currentLevel = header.level;
        while(currentLevel >= 0) {
            while (currentNode.next != null && currentNode.next.key.compareTo((K) key) < 0) {
                currentNode = currentNode.next;
            }
            if (currentNode.next != null && currentNode.next.key.compareTo((K) key) == 0) {
                currentNode = currentNode.next;
                V returnValue = null;
                while(currentLevel >= 0){
                    if(currentNode.next!=null)currentNode.next.prev = currentNode.prev;
                    currentNode.prev.next = currentNode.next;
                    returnValue = currentNode.data;
                    currentNode = currentNode.down;
                    currentLevel--;
                }
                num--;
                return returnValue;
            }
            currentNode = currentNode.down;
            currentLevel--;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry entry: m.entrySet()){
            put((K)entry.getKey(),(V)entry.getValue());
        }

    }
    @Override
    public void clear() {
        header = new Node(Integer.MIN_VALUE,null,0);
        num = 0;

    }
    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        Node current = header;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                set.add((K)current.key);
            }
            current = current.next;
        }
        return set;
    }


    @Override
    public Collection<V> values() {
        Collection<V> set = new TreeSet<>();
        Node current = header;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                set.add(current.data);
            }
            current = current.next;
        }
        return set;
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> set = new TreeSet<>();
        Node current = header;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                set.add(current);
            }
            current = current.next;
        }
        return set;
    }
}
