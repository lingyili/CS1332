public class test {
    public static void main(String[] args) {
        LazyDeleteLinkedList list = new LazyDeleteLinkedList();
        for (int i = 0; i < 5; i++) {
            list.add("A" + i);
        }
        list.add("A5");
        list.remove("A5");
        if (list.contains("A5")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}