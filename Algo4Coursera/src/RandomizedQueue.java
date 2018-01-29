import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private static final int RESIZING_ARRAY_INITIAL_SIZE = 8;
  private Item[] array = null;
  private int size = 0;

  public RandomizedQueue() {
    array = (Item[]) new Object[RESIZING_ARRAY_INITIAL_SIZE];
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == array.length) {
      resize(2 * size);
    }
    array[size++] = item;
  }

  private int tail() {
    return tail(size);
  }

  private int tail(int offset) {
    return offset < array.length ? offset : offset / array.length;
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int randomIndex = StdRandom.uniform(size);
    Item item = array[randomIndex];
    array[randomIndex] = array[--size];
    if (size > 0 && size == array.length / 4) {
      resize(size);
    }
    return item;
  }

  private void resize(int i) {
    array = Arrays.copyOf(array, i);
  }

  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return array[StdRandom.uniform(size)];
  }

  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    RandomizedQueue<Item> items = new RandomizedQueue<>();

    public RandomizedQueueIterator() {

      for (int i = 0; i < size(); i++) {
        items.enqueue(array[tail(i)]);
      }
    }

    @Override
    public boolean hasNext() {
      return items.size() > 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return items.dequeue();
    }

  }

  public static void main(String[] args) {
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    System.out.println("Queue size should be 0:" + queue.size);
    queue.enqueue("1");
    queue.enqueue("2");
    queue.enqueue("3");
    System.out.println("Queue size should be 3:" + queue.size);
    for (int i = 4; i < 50; i++) {
      queue.enqueue(String.valueOf(i));
    }
    System.out.println("Queue size should be ?:" + queue.size);
    for (int i = 1; i < 50; i++) {
      System.out.println("Item should be " + i + ":" + queue.dequeue());
    }
    System.out.println("Queue size should be 0:" + queue.size);
  }
}