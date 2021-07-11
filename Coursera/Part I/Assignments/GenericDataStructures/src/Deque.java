import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty()
    {
        return first == null;
    }

    // return the number of items on the deque
    public int size()
    {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("Null items not allowed");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (size() > 0) {
            oldFirst.previous = first;
        } else {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException("Null items not allowed");

        Node oldLast = last;
        last = new Node();
        last.item = item;
        oldLast.next = last;
        last.previous = oldLast;
        if (size() == 0) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (size() == 0)
            throw new IllegalArgumentException("Cannot remove from empty deque");

        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (size() == 0)
            throw new IllegalArgumentException("Cannot remove from empty stack");

        Item item = last.item;
        last = last.previous;
        if (last == null)
            first = null;
        else
            last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not allowed");
        }

        public Item next()
        {
            if (!hasNext())
                throw new IllegalArgumentException("Next element does not exist");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> ds1 = new Deque<>();
        Deque<String> ds2 = new Deque<>();
        ds1.addFirst("A"); ds2.addFirst("A");
        ds1.addLast("B"); ds2.addLast("B");
        ds1.addFirst("C"); ds2.addFirst("C");
        ds1.addLast("D"); ds2.addLast("D");
        ds1.addFirst("E"); ds2.addFirst("E");
        ds1.addLast("F"); ds2.addLast("F");

        StdOut.print("DS1: ");
        for (String item : ds1)
            StdOut.print(item + " ");
        StdOut.println();

        StdOut.println("Removing First:");
        StdOut.println("Size: " + ds1.size);
        while (!ds1.isEmpty())
            StdOut.println(ds1.removeLast());
        StdOut.println("Size: " + ds1.size);

        StdOut.print("DS2: ");
        for (String item : ds2)
            StdOut.print(item + " ");
        StdOut.println();

        StdOut.println("Removing First:");
        StdOut.println("Size: " + ds2.size);
        while (!ds2.isEmpty())
            StdOut.println(ds2.removeFirst());
        StdOut.println("Size: " + ds2.size);
    }

}