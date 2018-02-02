package Maze;

public class Cell {
	public Wall topWall = null;
	public Wall bottomWall = null;
	public Wall leftWall = null;
	public Wall rightWall = null;
	public int row;
	public int col;
	public boolean visited = false;
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getAdjacentWalls() {
		int adjacentWalls = 0;
		//Check if there is a wall to the top
		if(topWall == null)
			adjacentWalls += 1;
		else if (topWall.isActive)
			adjacentWalls += 1;
		//Check if there is a wall on the bottom
		if(bottomWall == null)
			adjacentWalls += 1;
		else if (bottomWall.isActive)
			adjacentWalls += 1;
		//Check if there is a wall on the left
		if(leftWall == null)
			adjacentWalls += 1;
		else if (leftWall.isActive)
			adjacentWalls += 1;
		//Check if there is a wall on the right
		if(rightWall == null)
			adjacentWalls += 1;
		else if (rightWall.isActive)
			adjacentWalls += 1;
		//Check number of walls
		return adjacentWalls;
	}
	
	public boolean wallBetween(Cell other) {
		if(	other.row == this.row+1 && 
				other.col == this.col && 
				this.bottomWall.isActive) {
			return true;
		} else if (	other.row == this.row-1 && 
				other.col == this.col && 
				this.topWall.isActive) {
			return true;
		} else if ( other.col == this.col+1 &&
				other.row == this.row &&
				this.rightWall.isActive) {
			return true;
		} else if (	other.col == this.col-1 &&
				other.row == this.row &&
				this.leftWall.isActive) {
			return true;
		} else {
			return false;
		}
	}

	public Wall getWallBetween(Cell other) {
		if(	other.row == this.row+1 && 
				other.col == this.col) {
			return this.bottomWall;
		} else if (	other.row == this.row-1 && 
				other.col == this.col) {
			return this.topWall;
		} else if ( other.col == this.col+1 &&
				other.row == this.row) {
			return this.rightWall;
		} else if (	other.col == this.col-1 &&
				other.row == this.row) {
			return this.leftWall;
		} else {
			return null;
		}
	}
}
