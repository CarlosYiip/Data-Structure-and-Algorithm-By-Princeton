import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;

public class Percolation {
    private byte[] grid;
    private int n;
    private WeightedQuickUnionUF realSites;
    private WeightedQuickUnionUF virtualSites;

    // Create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N must be greater than zero");
        realSites = new WeightedQuickUnionUF(n * n + 2);
        virtualSites = new WeightedQuickUnionUF(n * n + 2);
        grid = new byte[n * n + 1];
        this.n = n;
        
        for (int i = 1; i <= n * n; i++) {
            grid[i] = 0;
        }   
    }

    // Open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (i > n || i < 1 || j > n || j < 1)
            throw new IndexOutOfBoundsException("Index out of bounds");
        int index = n * (i - 1) + j;
        grid[index] = 1;
        
        if (index > n && n > 1) {
            if (isOpen(i - 1, j)) {
                int upIndex = index - n;
                virtualSites.union(upIndex, index);
                realSites.union(upIndex, index);
            }
        }
        
        if (index <= n * (n - 1) && n > 1) {
            if (isOpen(i + 1, j)) {
                int downIndex = index + n;
                virtualSites.union(downIndex, index);
                realSites.union(downIndex, index);
            }
        }

        if (index % n != 1 && n > 1) {
            if (isOpen(i, j - 1)) {
                int leftIndex = index - 1;
                virtualSites.union(leftIndex, index);
                realSites.union(leftIndex, index);
            }
        }

        if (index % n != 0 && n > 1) {
            if (isOpen(i, j + 1)) {
                int rightIndex = index + 1;
                virtualSites.union(rightIndex, index);
                realSites.union(rightIndex, index);
            }
        }
        
        if (index <= n) {
            realSites.union(index, 0);
            virtualSites.union(index, 0);
        }
        
        if (index > n * (n - 1)) {
            virtualSites.union(index, n * n + 1);
        }
    }

    // Is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i > n || i < 1 || j > n || j < 1)
            throw new IndexOutOfBoundsException("Index out of bounds");
        int index = n * (i - 1) + j;
        return grid[index] == 1;
    }

    // Is site(row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i > n || i < 1 || j > n || j < 1)
            throw new IndexOutOfBoundsException("Index out of bounds");
        int index = n * (i - 1) + j;
        return realSites.connected(0, index);
    }

    // Does the system percolate?
    public boolean percolates() {
        return virtualSites.connected(0, n * n + 1);
    }
       
    private void printGrid() {
        for (int i = 1; i <= n * n; i++) {
            if (i % n == 0)
                System.out.println(grid[i]);            
            else
                System.out.print(grid[i] + " ");
        }
    }
    
    public static void main(String[] args) {
//        In in = new In(args[0]);      
//        int n = in.readInt();
//        Percolation perc = new Percolation(n);
//        
//        while (!in.isEmpty()) {
//            int i = in.readInt();
//            int j = in.readInt();
//            perc.open(i, j);
//        }
//        perc.printGrid();
//        System.out.println(perc.isFull(3, 1));
//        System.out.println(perc.percolates());
    }
}