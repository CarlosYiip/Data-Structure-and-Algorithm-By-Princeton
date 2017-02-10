import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] data;
    private int trials;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials must be both greater than 0.");
        data = new double[trials];
        this.trials = trials;
                
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                p.open(a, b);
            }
                                 
            int numberOfOpenSites = 0;
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    if (p.isOpen(j, k))
                        numberOfOpenSites++;
                }
            }
            data[i] = (double) numberOfOpenSites / (double) (n * n);
        }
    }
    
    public double mean() {
        
        return StdStats.mean(data);
    }
    
    public double stddev() {
        return StdStats.stddev(data);
    }
    
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }
    
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }
    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats pstats = new PercolationStats(n, trials);
        StdOut.println(pstats.mean());
        StdOut.println(pstats.stddev());
        StdOut.println(pstats.confidenceLo() + ", " + pstats.confidenceHi());
    } 
}