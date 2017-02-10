import edu.princeton.cs.algs4.StdOut;
public class QuickUnionUF {
    private int[] id;
    private int[] sz;
    
    public QuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) { 
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
    
    public void print() {
        for (int i = 0; i < id.length; i++) {
            StdOut.print(id[i] + " ");
        }
        
        StdOut.println();
        
        for  (int i = 0; i < sz.length; i++) {
            StdOut.print(sz[i] + " ");
        }
    }
}












