import java.util.*;

/**
 * Created by lingyi on 7/19/15.
 */
public class HuffmanEncoder {
    Map<Character, Integer> myMap;
    HashMap<Character, String> hashMap;
    PriorityQueue<Node> priorityQueue;
    Node root;
    public class Node {
        int num;
        Character letter;
        int index;
        Node left;
        Node right;
        Node parent;
        public Node(Character letter, int i) {
            this.letter = letter;
            this.num = i;
            index = -1;
            left = null;
            right = null;
            parent = null;
        }
        public Node() {
            letter = null;
            num = 0;
            index = -1;
            left = null;
            right = null;
            parent = null;
        }
    }
    public HuffmanEncoder() {
        myMap = new HashMap<>();
    }
    public String encode(String text) {
        countLetter(text);
        Set<Character> keyset = myMap.keySet();
        priorityQueue = new PriorityQueue<>(keyset.size(), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.num - o2.num;
            }
        });
        for (Character s : keyset) {
            System.out.println(s + " " + myMap.get(s));
            Node in = new Node(s, myMap.get(s));
            priorityQueue.add(in);
        }
        ArrayList<Node> list = new ArrayList<>();
        while (priorityQueue.size() != 1) {
            Node first = priorityQueue.poll();
            Node second = priorityQueue.poll();
            Node newN = new Node();
            newN.num = first.num + second.num;
            if (first.letter != null && second.letter == null) {
                newN.right = first;
                newN.left = second;
                first.parent = newN;
                second.parent = newN;
                first.index = 1;
                second.index = 0;
            } else {
                newN.left = first;
                newN.right = second;
                first.parent = newN;
                second.parent = newN;
                first.index = 0;
                second.index = 1;
            }
            priorityQueue.add(newN);
            if (first.letter != null) {
                list.add(first);
            }
            if (second.letter != null) {
                list.add(second);
            }
        }
        root = priorityQueue.poll();
        hashMap = new HashMap<>();
        for (Node n : list) {
            Node temp = n;
            StringBuilder code = new StringBuilder();
            while (temp.parent.index != -1) {
                code.append(temp.index);
                temp = temp.parent;
            }
            code.append(temp.index);
            code.reverse();
            hashMap.put(n.letter, code.toString());
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            Character l = text.charAt(i);
            String code = hashMap.get(l);
            str.append(code);
        }
        return str.toString();
    }

    private void countLetter(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (myMap.containsKey(text.charAt(i))) {
                int value = myMap.get(text.charAt(i));
                myMap.put(text.charAt(i), ++value);
            } else {
                myMap.put(text.charAt(i),1);
            }
        }
    }

    public String decode(Map<Character, String> map, String text) {
        StringBuilder str = new StringBuilder();
        StringBuilder l = new StringBuilder();
        for(int i = 0; i < text.length();i++){
            char newChar = text.charAt(i);
            l.append(newChar);
            for(Map.Entry<Character,String> entry: map.entrySet()){
                if(l.toString().equals(entry.getValue())){
                    str.append(entry.getKey());
                    l.setLength(0);
                }
            }
        }
        return str.toString();
    }
    public HashMap getMap() {
        return hashMap;
    }

    public static void main(String[] arg) {
        HuffmanEncoder huffmanEncoder = new HuffmanEncoder();
        String outcome = huffmanEncoder.encode("GGCATTTAGGGGCCATX");
        System.out.println(outcome);
        String decoded  = huffmanEncoder.decode(huffmanEncoder.getMap(), outcome);
        System.out.println(decoded);

    }
}
