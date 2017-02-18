import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.Hashtable;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
    private Hashtable<Integer, String> IdToNoun = new Hashtable<Integer, String>();
    private Hashtable<String, Bag<Integer>> NounToId = new Hashtable<String, Bag<Integer>>();
    private Bag<String> all_nouns = new Bag<String>();
    private Digraph G;
    private SAP S;
        
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();
        
        In s = new In(synsets);
        while (s.hasNextLine()) {
            String[] line = s.readLine().split(",");
            
            int id = Integer.parseInt(line[0]);
            IdToNoun.put(id, line[1]);
            
            String[] words = line[1].split(" ");            
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
               
                if (NounToId.containsKey(word)) {
                    NounToId.get(word).add(id);
                } else {
                    Bag<Integer> newIdList = new Bag<Integer>();
                    newIdList.add(id);
                    NounToId.put(word, newIdList);
                    all_nouns.add(word);
                }
            }            
        }
        
        G = new Digraph(IdToNoun.size());
        In h = new In(hypernyms);
        while (h.hasNextLine()) {
            String[] line = h.readLine().split(",");
            if (line.length > 1) {;               
                int v = Integer.parseInt(line[0]);
                for (int i = 1; i < line.length; i++) {
                    int w = Integer.parseInt(line[i]);
                    G.addEdge(v, w);
                }
            }
        }
        
        int rootCount = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                rootCount++;
                if (rootCount > 1)
                    throw new IllegalArgumentException();
            }                
        }
        
        Topological top = new Topological(G);
        if (!top.hasOrder())
            throw new IllegalArgumentException();
        
        S = new SAP(G);       
    }
    
    public Iterable<String> nouns() {
        return all_nouns;
    }
    
    public boolean isNoun(String word) {
        return NounToId.containsKey(word);
    }
    
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return S.length(NounToId.get(nounA), NounToId.get(nounB));
    }
    
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();        
        return IdToNoun.get(S.ancestor(NounToId.get(nounA), NounToId.get(nounB)));
    }
    
    public static void main(String[] args) {                
    }
}

