//    public Solver(Board initial) {
//        Node root = new Node(initial);
//        pq.insert(root);
//        explored.add(root.getBoard());
//        int count = 0;
//        while (count < 100) {
//            Node currentSearchNode = pq.delMin();
//            StdOut.print(currentSearchNode.getBoard());
//            StdOut.println("Manhattan = " + currentSearchNode.getBoard().manhattan()); 
//            StdOut.println("Moves = " + nbOfMoves);            
//            StdOut.println("Priority = " + currentSearchNode.getPriority());
//            Board currentBoard = currentSearchNode.getBoard();
//            path.enqueue(currentBoard);
//            if (currentBoard.isGoal()) {
//                StdOut.println("Minimum number of moves: " + nbOfMoves);
//                StdOut.println(currentBoard + "\nGoal!!!!!");
//                break;
//            }
//            nbOfMoves++;
//            Iterable<Board> neighbors = currentBoard.neighbors();
//                        
//            for (Board n : neighbors) {
//                boolean seen = false;
//                for (Board e : explored) {
//                    if (n.equals(e)) {
//                        seen = true;
//                        break;
//                    }
//                }
//                
//                if (seen == false) {
//                    Node child = new Node(n);
//                    pq.insert(child);
//                    explored.add(n);
//                }
//            }
//            count++;
//        }
//    }