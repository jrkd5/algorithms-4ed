import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class ResizingArrayStack<Item> implements Iterable<Item> {

    private Item[] elements = (Item[]) new Object[1];
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = elements[i];
        }
        elements = temp;
    }

    public void push(Item item) {
        if (n == elements.length) resize(2 * elements.length);
        elements[n++] = item;
    }

    public Item pop() {
        Item item = elements[--n];
        elements[n] = null; // avoid loitering
        if (n > 0 && n == elements.length / 4) resize(elements.length / 2);
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = n - 1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            return elements[i--];
        }
    }

    public static void main(String[] args) {

        ResizingArrayStack<String> s = new ResizingArrayStack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
        for (String elem : s) {
            StdOut.println(elem);
        }
    }
}
