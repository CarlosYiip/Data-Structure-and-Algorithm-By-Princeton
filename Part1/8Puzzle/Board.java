import java.util.ArrayList;

public class Board {
    private final int[] tiles;
    private int d;
    private int manhattan = 0;
    private int indexOfEmptyTile;
    private ArrayList<Board> neighbors = new ArrayList<Board>();
    
    public Board(int[][] blocks) {
       d = blocks.length;        
       tiles = new int[d*d];
       
       for (int i = 0; i < d; i++) {
           for (int j = 0; j < d; j++) {
               int index = d * i + j;
               tiles[index] = blocks[i][j];
               if (blocks[i][j] == 0) {
                   indexOfEmptyTile = d * i + j;
                   continue;
               }
               manhattan += findDistance(d*i+j, tiles[d*i+j]);
               
//               linearConflict(index);
           }
       }
//       linearConflict();
    }
    
    private Board(int[] blocks, int newManhattan) {
        tiles = blocks;
        d = (int) Math.sqrt(blocks.length);
        for (int i = 0; i < d * d; i++) {
            if (tiles[i] == 0) {
                indexOfEmptyTile = i;
                break;
            }
//            linearConflict(i);
        }
        manhattan = newManhattan;
//        linearConflict();
    }
    
    public int dimension() {
        return d;
    }
    
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) continue;
            if (i + 1 != tiles[i]) sum++;
        }
        return sum;
    }
    
    public int manhattan() {
        return manhattan;
    }
   
    public boolean isGoal() {
        return manhattan() == 0;
    }
    
    public Board twin() {
        final int[] twinTiles = copyTiles();
        int newManhattan = 0;
        for (int i = 0; i < twinTiles.length - 1; i++) {
            if (twinTiles[i] != 0 && twinTiles[i+1] != 0) {
                int distanceBeforeSwap = findDistance(i, twinTiles[i]) + 
                    findDistance(i + 1, twinTiles[i+1]);
                int temp = twinTiles[i];
                twinTiles[i] = twinTiles[i+1];
                twinTiles[i+1] = temp;
                int distanceAfterSwap = findDistance(i, twinTiles[i]) +
                    findDistance(i + 1, twinTiles[i+1]);
                int distanceDifference = distanceAfterSwap - distanceBeforeSwap;
                newManhattan = manhattan + distanceDifference;
                break;
            }
        }
        Board twinBoard = new Board(twinTiles, newManhattan);
        return twinBoard;
    }
    
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        return this.toString().equals(that.toString());
    }
    
    public Iterable<Board> neighbors() {
        // Able to move left
        if (indexOfEmptyTile % d < d - 1) {
            int[] copy = copyTiles();
            int index = indexOfEmptyTile + 1;
            int value = copy[index];
            int distanceBeforeSwap = findDistance(index, value);
            copy[indexOfEmptyTile] = copy[indexOfEmptyTile + 1];
            copy[indexOfEmptyTile + 1] = 0;            
            int distanceAfterSwap = findDistance(indexOfEmptyTile, value);
            int distanceDifference = distanceAfterSwap - distanceBeforeSwap;
            int newManhattan = manhattan + distanceDifference;
            neighbors.add(new Board(copy, newManhattan));
        }
        
         // Able to move right
        if (indexOfEmptyTile % d > 0) {
            int[] copy = copyTiles();
            int index = indexOfEmptyTile - 1;
            int value = copy[index];
            int distanceBeforeSwap = findDistance(index, value);
            copy[indexOfEmptyTile] = copy[indexOfEmptyTile - 1];
            copy[indexOfEmptyTile - 1] = 0;            
            int distanceAfterSwap = findDistance(indexOfEmptyTile, value);
            int distanceDifference = distanceAfterSwap - distanceBeforeSwap;
            int newManhattan = manhattan + distanceDifference;
            neighbors.add(new Board(copy, newManhattan));
        }        

        // Able to move up
        if (indexOfEmptyTile / d < d - 1) {
            int[] copy = copyTiles();
            int index = indexOfEmptyTile + d;
            int value = copy[index];
            int distanceBeforeSwap = findDistance(index, value);
            copy[indexOfEmptyTile] = copy[indexOfEmptyTile + d];
            copy[indexOfEmptyTile + d] = 0;            
            int distanceAfterSwap = findDistance(indexOfEmptyTile, value);
            int distanceDifference = distanceAfterSwap - distanceBeforeSwap;
            int newManhattan = manhattan + distanceDifference;
            neighbors.add(new Board(copy, newManhattan));
        } 
                 
        // Able to move down
        if (indexOfEmptyTile / d > 0) {
            int[] copy = copyTiles();
            int index = indexOfEmptyTile - d;
            int value = copy[index];
            int distanceBeforeSwap = findDistance(index, value);
            copy[indexOfEmptyTile] = copy[indexOfEmptyTile - d];
            copy[indexOfEmptyTile - d] = 0;            
            int distanceAfterSwap = findDistance(indexOfEmptyTile, value);
            int distanceDifference = distanceAfterSwap - distanceBeforeSwap;
            int newManhattan = manhattan + distanceDifference;
            neighbors.add(new Board(copy, newManhattan));
        }                
        return neighbors;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(d + "\n");
        s.append(String.format("%2d ", tiles[0]));
        for (int i = 1; i < tiles.length; i++) {
            if ((i + 1) % d != 0) {
                s.append(String.format("%2d ", tiles[i]));                         
            } else {
                s.append(String.format("%2d\n", tiles[i]));
            }
        }
        return s.toString();
    }
    
    private int[] copyTiles() {
        int[] newTiles = new int[d*d];
        for (int i = 0; i < d*d; i++) {
            newTiles[i] = tiles[i];
        }
        return newTiles;
    }
    
    private int findDistance(int index, int value) {
        int currentX = index % d;
        int correctX = (value - 1) % d;
        int horizontalDistance = Math.abs(currentX - correctX);
        int currentY = index / d;
        int correctY = (value - 1) / d;
        int verticalDistance = Math.abs(currentY - correctY);
        int distance = horizontalDistance + verticalDistance;
        return distance;
    }
    
//    private void linearConflict() {
////        int i = index;
//        if (tiles[i] == 0) return;            
//        int currentX = i % d;
//        int correctX = (tiles[i] - 1) % d;
//        int currentY = i / d;
//        int correctY = (tiles[i] - 1) / d;
//            
//        if (currentY == correctY) {
//            if (currentY > 0) {
//                if (tiles[i-d] > tiles[i] && (tiles[i-d] - 1) / d == correctY)
//                    manhattan += 2;
//            }
////            if (currentY < d) {
////                if (tiles[i+d] < tiles[i] && (tiles[i+d] - 1) / d == correctY)
////                    manhattan += 2;
////            }            
//        }
//        if (currentX == correctX) {
//            if (currentX > 0) {
//                if (tiles[i-1] > tiles[i] && (tiles[i-1] - 1) % d == correctX)
//                    manhattan += 2;
//            }
////            if (currentX < d) {
////                if (tiles[i+1] < tiles[i] && (tiles[i+1] - 1) % d == correctX)
////                    manhattan += 2;
////            }               
//        }
//    }
}




















