import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;
    private int head;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int n)
    {
        Item[] newItems = (Item[]) new Object[n];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[head+i];
        }
        items = newItems;
        tail = tail - head;
        head = 0;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("Null items are not allowed");

        items[tail++] = item;
        size++;
        if (tail == items.length) {
            resize(size * 2);
        }
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException("Cannot deque from empty queue");

        int index = StdRandom.uniform(size) + head;
        Item item = items[index];
        items[index] = items[head];
        items[head++] = null;
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException("Cannot deque from empty queue");

        int index = StdRandom.uniform(size) + head;
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        int[] samples = StdRandom.permutation(size);
        int current;

        public boolean hasNext() {
            return head + current < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not allowed");
        }

        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException("Next element does not exist");
            int index = head + samples[current++];
            return items[index];
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<String> r = new RandomizedQueue<>();
        r.enqueue("A");
        r.enqueue("B");
        r.enqueue("C");
        r.enqueue("D");
        r.enqueue("E");

        StdOut.print("20 samples:");
        for (int i = 0; i < 20; i++) {
            StdOut.print(r.sample() + "");
        }
        StdOut.println();

        StdOut.print("Print in random order: ");
        for (String s : r) {
            StdOut.print(s + " ");
        }
        StdOut.println();

        StdOut.println("Starting dequeue.");
        while (!r.isEmpty()) {
            StdOut.println(r.dequeue());
        }
        StdOut.println(r.size());
    }

}
