import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head = new Node<Item>(null, null, null);
    private Node<Item> tail = new Node<Item>(null, null, null);
    private int n = 0;

    
    private class Node<Item> {
        private Item element = null;
        private Node<Item> prev = null;
        private Node<Item> next = null;
        
        public Node(Item element) {
            this.element = element;
        }
        
        public Node(Item element, Node<Item> prev, Node<Item> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
        
        public Item getElement() {
            return element;
        }
        
        public Node<Item> getNext() {
            return next;
        }
        
        public Node<Item> getPrev() {
            return prev;
        }
                                       
        public void setElement(Item element) {
            this.element = element;
        }
        
        public void setNext(Node<Item> next) {
            this.next = next;
        }
        
        public void setPrev(Node<Item> prev) {
            this.prev = prev;
        }
    }
    
    public Deque() {
        head.setNext(tail);
        tail.setPrev(head);
    }
    
    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public void addFirst(Item element) {
        if (element == null) throw new NullPointerException();
        Node<Item> oldFirst = head.getNext();
        Node<Item> first = new Node<Item>(element, head, oldFirst);
        head.setNext(first);
        oldFirst.setPrev(first);
        n++;
    }
    
    public void addLast(Item element) {
        if (element == null) throw new NullPointerException();
        Node<Item> oldLast = tail.getPrev();
        Node<Item> last = new Node<Item>(element, oldLast, tail);
        tail.setPrev(last);
        oldLast.setNext(last);
        n++;
    }
    
    public Item removeFirst() {
        if (n == 0) throw new java.util.NoSuchElementException();
        Node<Item> oldFirst = head.getNext();
        Node<Item> first = oldFirst.getNext();
        first.setPrev(head);
        head.setNext(first);
        n--;
        return oldFirst.getElement();
    }
    
    public Item removeLast() {
        if (n == 0) throw new java.util.NoSuchElementException();
        Node<Item> oldLast = tail.getPrev();
        Node<Item> last = oldLast.getPrev();
        last.setNext(tail);
        tail.setPrev(last);
        n--;
        return oldLast.getElement();
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = head.getNext();
        
        public boolean hasNext() {
           return current != tail;
        }
        
        public Item next() {
            Item element = current.getElement();
            current = current.getNext();
            return element;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
            deque.addFirst(i);
        }
        
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}