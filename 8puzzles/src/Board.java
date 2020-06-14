import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Board {

	private final int[][] tiles;
	
	// create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	int[][] tiles_clone = copy(tiles);
    	this.tiles = tiles_clone;
    }
     
    // string representation of this board
    public String toString() {
    	StringBuilder tilesRepresent = new StringBuilder();
    	tilesRepresent.append(tiles.length + "\n");
    	for(int i = 0; i < tiles.length; i++) {
    		for(int j = 0; j < tiles[0].length; j++) {
    			tilesRepresent.append(String.format("%6d", tiles[i][j]));
    		}
    		tilesRepresent.append("\n");
    	}
    	return tilesRepresent.toString();
    }

    // board dimension n
    public int dimension() {
    	return tiles.clone().length;
    }

    // number of tiles out of place
    public int hamming() {
    	int hammingDistance = 0;
    	for(int i = 0; i < tiles.length; i++) {
    		for(int j = 0; j < tiles[0].length; j++) {
    			if(tiles[i][j] != i * tiles[0].length + j + 1 && tiles[i][j] != 0)
    				hammingDistance++;
    		}
    	}
    	return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int manhattanDistance = 0;
    	for(int i = 0; i < tiles.length; i++) {
    		for(int j = 0; j < tiles[0].length; j++) {
    			if(tiles[i][j] != i * tiles[0].length + j + 1 && tiles[i][j] != 0) {
    				manhattanDistance += Math.abs((tiles[i][j] - 1) / tiles[0].length - i) + Math.abs((tiles[i][j] - 1) % tiles[0].length - j);
    			}
    		}
    	}
    	return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return hamming() == 0 ? true : false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	if(y == this) return true;
    	//check if y is null
    	if(y == null) return false;
    	//check if y has the same class as this.object
    	if(y.getClass() != this.getClass()) return false;
    	//check all instance variables are the same as this object
    	Board that = (Board) y;
    	return ((this.dimension() == that.dimension()) && Arrays.deepEquals(this.tiles, that.tiles));
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
    	Deque<Board> neightbours = new ArrayDeque<>();
    	int blank_row = 0;
    	int blank_col = 0;
    	A: for(int i = 0; i < tiles.length; i++) {
    		for(int j = 0; j < tiles[0].length; j++) {
    			if(tiles[i][j] == 0) {
    				blank_row = i;
    				blank_col = j;
    				break A;
    			}
    		}
    	}
    	if(blank_row - 1 >= 0) {
    		int[][] copied_tiles = copy(tiles);
    		exchange(blank_row, blank_col, blank_row - 1, blank_col, copied_tiles);
    		Board temp = new Board(copied_tiles);
    		neightbours.add(temp);
    	}
    	if(blank_row + 1 < tiles.length) {
    		int[][] copied_tiles = copy(tiles);
    		exchange(blank_row, blank_col, blank_row + 1, blank_col, copied_tiles);
    		Board temp = new Board(copied_tiles);
    		neightbours.add(temp);
    	}
    	if(blank_col - 1 >= 0) {
    		int[][] copied_tiles = copy(tiles);
    		exchange(blank_row, blank_col, blank_row, blank_col - 1, copied_tiles);
    		Board temp = new Board(copied_tiles);
    		neightbours.add(temp);
    	}
    	if(blank_col + 1 < tiles[0].length) {
    		int[][] copied_tiles = copy(tiles);
    		exchange(blank_row, blank_col, blank_row, blank_col + 1, copied_tiles);
    		Board temp = new Board(copied_tiles);
    		neightbours.add(temp);
    	}
    	return neightbours;
    }
    
    private int[][] exchange(int ex_row, int ex_col, int row, int col, int[][] matrix){
    	int temp = matrix[ex_row][ex_col];
    	matrix[ex_row][ex_col] = matrix[row][col];
    	matrix[row][col] = temp;
    	return matrix;
    }
    
    private int[][] copy(int[][] matrix){
    	int[][] copied = new int[matrix.length][matrix[0].length];
    	for(int i = 0; i < matrix.length; i++)
    		copied[i] = matrix[i].clone();
    	return copied;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
//    	int row1 = 0;
//    	int col1 = 0;
//    	int row2 = 0;
//    	int col2 = 0;
//    	A: for(int i = 0; i < tiles.length; i++) {
//    		for(int j = 0; j < tiles[0].length; j++) {
//    			if(tiles[i][j] != 0) {
//    				row1 = i;
//    				col1 = j;
//    				break A;
//    			}
//    		}
//    	}
//    	B: for(int i = row1; i < tiles.length; i++) {
//    		for(int j = col1 + 1; j < tiles[0].length; j++) {
//    			if(tiles[i][j] != 0) {
//    				row2 = i;
//    				col2 = j;
//    				break B;
//    			}
//    		}
//    	}

    	int[][] copied_tiles = copy(tiles);
    	if(copied_tiles[0][0] != 0) {
    		if(copied_tiles[0][1] != 0)
    			exchange(0, 0, 0, 1, copied_tiles);
    		else
    			exchange(0, 0, 1, 0, copied_tiles);
    	}else 
    		exchange(0, 1, 1, 0, copied_tiles);
    	Board twin_tiles = new Board(copied_tiles);
    	return twin_tiles;
    }

    // unit testing (not graded)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] puzzle04 = {{2, 0}, {3, 1}};
		Board test = new Board(puzzle04);
		
		for(int i = 0; i< 5; i++) {
			System.out.println(test.twin());
		}
		
		

	}

}
