import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wnet;
    
    public Outcast(WordNet wordnet) {
        wnet = wordnet;        
    }
    
    public String outcast(String[] nouns) {
        String res = "";
        int dist = 0;
        
        for (String i: nouns) {
            int current_dist = 0;
            for (String j: nouns) {
                current_dist += wnet.distance(i, j);
            }
            if (current_dist > dist) {
                dist = current_dist;
                res = i;
            }
        }                
        return res;
    }
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}