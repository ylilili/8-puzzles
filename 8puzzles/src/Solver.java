import java.util.ArrayDeque;
import java.util.Deque;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

	private Deque<Board> solution = new ArrayDeque<>();
	private Boolean isresolvable;
	private searchNode deleteNode;
	private int size;
	
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	validate(initial);
    	
    	MinPQ<searchNode> Queue = new MinPQ<>();
    	MinPQ<searchNode> Queue_twin = new MinPQ<>();
    	
 
    	deleteNode = new searchNode(null, initial);
    	Queue.insert(deleteNode);

    	searchNode deleteNode_twin = new searchNode(null, initial.twin());
    	Queue_twin.insert(deleteNode_twin);
    	
    	while(!deleteNode.board.isGoal() && !deleteNode_twin.board.isGoal()) {
    
    		deleteNode = Queue.delMin();
    		deleteNode_twin = Queue_twin.delMin();
    		
    		for(Board neighbour: deleteNode.board.neighbors()) {
    			if(deleteNode.previous == null || !neighbour.equals(deleteNode.previous.board))
    				Queue.insert(new searchNode(deleteNode, neighbour));
        	}
    		
    		for(Board neighbour: deleteNode_twin.board.neighbors()) {
    			if(deleteNode_twin.previous == null || !neighbour.equals(deleteNode_twin.previous.board))
    				Queue_twin.insert(new searchNode(deleteNode_twin, neighbour));
        	}
    			
    	}
    	
    	if(deleteNode.board.isGoal()) {
    		isresolvable = true;
    		searchNode temp = new searchNode(deleteNode.previous, deleteNode.board);
    		while(temp != null) {
    			solution.addFirst(temp.board);
    			temp = temp.previous;
    		}
    		size = solution.size() - 1;
    	}
    	if(deleteNode_twin.board.isGoal()) {
    		isresolvable = false;
    	}
    }
    
    private class searchNode implements Comparable<searchNode>{
    	private searchNode previous;
    	private final Board board;
    	private final int moves;
    	private final int manhattan;
    	//private final int hamming;
    	private final int priority;
    	
    	public searchNode(searchNode previous, Board board) {
    		this.previous = previous;
    		this.board = board;
    		if(this.previous == null)
    			this.moves = 1;
    		else
    			this.moves = this.previous.moves + 1;
    		this.manhattan = board.manhattan();
    		//this.hamming = board.hamming();
    		this.priority = this.moves + manhattan;		
    	}

		@Override
		public int compareTo(searchNode that) {
			return this.priority - that.priority;
		}
    }

    private void validate(Board argument) {
    	if(argument == null)
    		throw new IllegalArgumentException();
    }
    
    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	return isresolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if(!isSolvable())
    		return -1;
    	//Deque<Board> solution = new ArrayDeque<>();
    	return size;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
    	if(!isSolvable())
    		return null;
    	return solution;
    }

    // test client (see below) 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int[][] puzzle04 = {{0,1,3}, {4,2,5}, {7,8,6}};
		int[][] puzzle04 = {{1,2,3}, {4,5,6}, {8,7,0}};
		Board initial = new Board(puzzle04);
		Solver test = new Solver(initial);

		System.out.println(test.moves());
//		System.out.println(test.solution());
	}

}
