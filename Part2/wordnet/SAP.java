import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.util.ArrayList;

public class SAP {
    final private Digraph G;
    private int A;
    private int D;
    private int AA;
    private int DD;
    
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }
    
    public int ancestor(int v, int w) {
       if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
           throw new IndexOutOfBoundsException();
        
       A = -1;
       D = Integer.MAX_VALUE;
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
       for (int i = 0; i < G.V(); i++) {
           if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
               if (bfsV.distTo(i) + bfsW.distTo(i) < D) {
                   A = i;
                   D = bfsV.distTo(i) + bfsW.distTo(i);
               }
           }
       }
       return A;
    }
    
    public int length(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IndexOutOfBoundsException();
        
        ancestor(v, w);
        return D != Integer.MAX_VALUE ? D : -1;
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        
        for (int i: v) {
            if (i < 0 || i >= G.V())
                throw new IndexOutOfBoundsException();
        }
        
        for (int i: w) {
            if (i < 0 || i >= G.V())
                throw new IndexOutOfBoundsException();
        }

        AA = -1;
        DD = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (bfsV.distTo(i) + bfsW.distTo(i) < DD) {
                    AA = i;
                    DD = bfsV.distTo(i) + bfsW.distTo(i);
                }
            }
        }
        return AA;
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {  
        ancestor(v, w);
        return DD != Integer.MAX_VALUE ? DD : -1;
    }
    
    
    public static void main(String[] args) {
        
    }
}