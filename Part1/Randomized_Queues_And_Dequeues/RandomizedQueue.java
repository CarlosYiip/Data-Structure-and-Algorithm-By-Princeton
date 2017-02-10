import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
    
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 16;
    private Item[] data;
    private int numberOfElements = 0;
    
    public RandomizedQueue() {
        data = (Item[]) new Object[size];
    }
    
    public boolean isEmpty() {
        return numberOfElements == 0;
    }
    
    public int size() {
        return numberOfElements;
    }
    
    public void enqueue(Item element) {
        if (element == null) throw new NullPointerException();
        if (numberOfElements == data.length) {
            data = resize(data.length * 2, data);
        }
        numberOfElements++;
        data[numberOfElements - 1] = element;
    }
    
    public Item dequeue() {
        if (numberOfElements == 0) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(numberOfElements);
        Item element = data[randomIndex];
        data[randomIndex] = data[numberOfElements - 1];
        data[numberOfElements - 1] = null;
        numberOfElements--;
        if (numberOfElements < data.length / 4) {
            data = resize(data.length / 2, data);
        }
        return element;
    }
    
    public Item sample() {
        if (numberOfElements == 0) throw new NoSuchElementException();
        return data[StdRandom.uniform(numberOfElements)];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private RandomizedQueue<Integer> rq;
        
        public RandomizedQueueIterator() {
            rq = new RandomizedQueue<Integer>();
            for (int i = 0; i < numberOfElements; i++) {
                rq.enqueue(i);
            }
        }
        
        public boolean hasNext() {
            return !rq.isEmpty();
        }
        
        public Item next() {
            return data[rq.dequeue()];
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private Item[] resize(int newSize, Item[] oldArray) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < newSize / 2; i++) {
            newArray[i] = oldArray[i];
        } 
        return newArray;
    }
    
    private void print() {
        for (int i = 0; i < data.length; i++) {
            StdOut.print(data[i] + " ");
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        
        Iterator<Integer> iter1 = rq.iterator();
        while (iter1.hasNext()) {
            StdOut.println(iter1.next());
        }
        StdOut.println();
        Iterator<Integer> iter2 = rq.iterator();
        while (iter2.hasNext()) {
            StdOut.println(iter2.next());
        }
    }
}