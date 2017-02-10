import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);

        String[] strings = StdIn.readAllStrings();
        for (int i = 0; i < strings.length; i++) {
            rq.enqueue(strings[i]);
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}