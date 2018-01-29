import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private final Node sentinel = new Node();
  private final Node last = new Node();
  private int size = 0;

  public Deque() {
    sentinel.next = last;
    last.prev = sentinel;
  } // construct an empty deque

  public boolean isEmpty() {
    return size() == 0;
  } // is the deque empty?

  public int size() {
    return size;
  } // return the number of items on the deque

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node tmp = new Node();
    tmp.item = item;
    tmp.next = sentinel.next;
    tmp.prev = sentinel;
    sentinel.next.prev = tmp;
    sentinel.next = tmp;
    size++;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node tmp = new Node();
    tmp.item = item;
    tmp.prev = last.prev;
    tmp.next = last;
    last.prev.next = tmp;
    last.prev = tmp;
    size++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Node toDelete = sentinel.next;
    Item tmp = toDelete.item;
    toDelete.next.prev = sentinel;
    sentinel.next = toDelete.next;
    toDelete = null;
    size--;
    return tmp;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Node toRemove = last.prev;
    Item item = toRemove.item;
    toRemove = null;
    last.prev = last.prev.prev;
    last.prev.next = last;
    size--;
    return item;
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  } // return an iterator over items in order from front to end

  private class Node {
    Item item = null;
    Node next = null;
    Node prev = null;
  }

  private class DequeIterator implements Iterator<Item> {
    Node cur = sentinel.next;

    @Override
    public boolean hasNext() {
      return cur.next != null;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Node tmp = cur;
      cur = tmp.next;
      return tmp.item;
    }

  }

  public static void main(String[] args) {
    Deque<String> deque = new Deque<String>();
    System.out.println("deque should be empty:" + deque.isEmpty());
    System.out.println("deque size should be 0:" + deque.size());
    deque.addFirst("1");
    deque.addFirst("2");
    deque.addFirst("3");
    int size = 0;
    for (String string : deque) {
      size++;
    }
    System.out.println("size should be 3:" + size);
    System.out.println("value should be 3:" + deque.removeFirst());
    System.out.println("value should be 2:" + deque.removeFirst());
    deque.addLast("2");
    System.out.println("value should be 2:" + deque.removeLast());
    System.out.println("value should be 1:" + deque.removeLast());
    System.out.println("deque should be empty:" + deque.isEmpty());
    System.out.println("deque size should be 0:" + deque.size());
  } // unit testing (optional)
}