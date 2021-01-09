import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FixedCapacityStack<Item> {

    private Item[] items;
    private int n;

    public FixedCapacityStack(int capacity) {
        items = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        items[n++] = item;
    }

    public Item pop() {
        return items[--n];
    }

    public static void main(String[] args) {

        FixedCapacityStack<String> s;
        s = new FixedCapacityStack<>(100);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
    }
}
