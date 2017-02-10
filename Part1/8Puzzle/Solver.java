import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int minimumMoves;
    private Stack<Board> path = new Stack<Board>();
    private MinPQ<Node> pq = new MinPQ<Node>();
    private boolean solvable;
    
    private class Node implements Comparable<Node> {
        private Board searchNodeBoard;
        private int movesToReach;
        private Node previous;
        private boolean isTwin;
                    
        public Node(Board b, int m, Node p) {
            searchNodeBoard = b;
            movesToReach = m;
            previous = p;
            this.isTwin = isTwin;
        }
        
        public Board getBoard() {
            return searchNodeBoard;
        }
        
        public int getPriority() {
            return movesToReach + searchNodeBoard.manhattan();
        }
        
        public Node getPrevious() {
            return previous;
        }
        
        public int getMoves() {
            return movesToReach;
        }
        
        public int compareTo(Node that) {
            return this.getPriority() - that.getPriority();
        }
        
        public boolean isTwin() {
            return isTwin;
        }
        
        public void setTwin() {
            isTwin = true;
        }
    }
    
    public Solver(Board initial) {
        AStar(initial);
    }
        
    private void AStar(Board initial) {
       
       Node root = new Node(initial, 0, null);
       pq.insert(root);
        
       Node twin = new Node(initial.twin(), 0, null);
       twin.setTwin();
       pq.insert(twin);
       
        
       int count = 0;
       while (true) {      
           Node currentNode = pq.delMin();
           Board currentBoard = currentNode.getBoard();
           
           if (currentBoard.isGoal()) {
               Node iter = currentNode;
               while (iter.getPrevious() != null) {
                   iter = iter.getPrevious();
               }               
               if (iter.isTwin()) {
                   minimumMoves = -1;
                   path = null;
                   solvable = false;
                   return;
               }
               
               minimumMoves = currentNode.getMoves();
               path.push(currentBoard);
               while (currentNode.getPrevious() != null) {
                   Node previousNode = currentNode.getPrevious();
                   path.push(previousNode.getBoard());
                   currentNode = previousNode;
               }
               solvable = true;
               break;                
           }
           
           for (Board neighbor : currentBoard.neighbors()) {
               Node previousNode = currentNode.getPrevious();
               int nextMove = currentNode.getMoves() + 1;                
               if (previousNode == null) {
                   Node childNode = new Node(neighbor, nextMove, currentNode);
                   pq.insert(childNode);
                   continue;
               }
               Board previousBoard = previousNode.getBoard();
               if (!neighbor.equals(previousBoard)) {
                   Node childNode = new Node(neighbor, nextMove, currentNode);
                   pq.insert(childNode);
                }
           }
           count++;
       }                 
    }
               
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        return minimumMoves;
    }
    
    public Iterable<Board> solution() {          
        return path;
    }
    
    public static void main(String[] args) throws Exception{
        // for each command-line argument
        for (String filename : args) {
            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            // solve the slider puzzle
            long start = System.currentTimeMillis();
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            if (solver.isSolvable()) {
                StdOut.println("Minimum number of moves = "
                                   + solver.moves());            
                for (Board b: solver.solution()) {
                    StdOut.println(b);
                }
            } else {
                StdOut.println("Unsolvable!");
            }
            StdOut.println(System.currentTimeMillis() - start);
        }
    }
}