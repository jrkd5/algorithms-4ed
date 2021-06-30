import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Queue<Item> {

    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else {
            oldLast.next = last;
        }
        n++;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    public static void main(String[] args) {
        Queue<String> q = new Queue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                q.enqueue(item);
            else if (!q.isEmpty()) StdOut.println(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
