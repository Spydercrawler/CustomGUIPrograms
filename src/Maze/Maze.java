package Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Maze {
	public Cell[][] cellGrid;
	public Wall[][] sideWallGrid;
	public Wall[][] topWallGrid;
	public int width;
	public int height;
	Random rand;
	public Maze(int width, int height) throws Exception {
		this(width,height,true);
	}
	public Maze(Maze other) {
		width = other.cellGrid[0].length;
		height = other.cellGrid.length;
		cellGrid = new Cell[height][width];
		sideWallGrid = new Wall[height][width-1];
		topWallGrid = new Wall[height-1][width];
		//Make Everything
		for(int r = 0; r<cellGrid.length;r++) {
			for(int c = 0;c<cellGrid[r].length;c++) {
				cellGrid[r][c] = new Cell(r,c);
			}
		}
		for(int r = 0;r<sideWallGrid.length;r++) {
			for(int c = 0;c<sideWallGrid[r].length;c++) {
				sideWallGrid[r][c] = new Wall(cellGrid[r][c],cellGrid[r][c+1],false);
				cellGrid[r][c].rightWall = sideWallGrid[r][c];
				cellGrid[r][c+1].leftWall = sideWallGrid[r][c];
			}
		}
		for(int r = 0;r<topWallGrid.length;r++) {
			for(int c = 0;c<topWallGrid[r].length;c++) {
				topWallGrid[r][c] = new Wall(cellGrid[r][c], cellGrid[r+1][c],false);
				cellGrid[r][c].bottomWall = topWallGrid[r][c];
				cellGrid[r+1][c].topWall = topWallGrid[r][c];
			}
		}
		//Copy everything
		for(int r = 0; r<cellGrid.length;r++) {
			for(int c = 0;c<cellGrid[r].length;c++) {
				cellGrid[r][c].visited = other.cellGrid[r][c].visited;
			}
		}
		for(int r = 0;r<sideWallGrid.length;r++) {
			for(int c = 0;c<sideWallGrid[r].length;c++) {
				sideWallGrid[r][c].isActive = other.sideWallGrid[r][c].isActive;
			}
		}
		for(int r = 0;r<topWallGrid.length;r++) {
			for(int c = 0;c<topWallGrid[r].length;c++) {
				topWallGrid[r][c].isActive = other.topWallGrid[r][c].isActive;
			}
		}
		rand = new Random();
	}
	public Maze(int width,int height, boolean activeWalls) throws Exception {
		if(width<0 || height < 0) {
			throw new Exception("Height and Width must be more than zero!");
		}
		this.width = width;
		this.height = height;
		cellGrid = new Cell[height][width];
		sideWallGrid = new Wall[height][width-1];
		topWallGrid = new Wall[height-1][width];
		for(int r = 0; r<cellGrid.length;r++) {
			for(int c = 0;c<cellGrid[r].length;c++) {
				cellGrid[r][c] = new Cell(r,c);
			}
		}
		for(int r = 0;r<sideWallGrid.length;r++) {
			for(int c = 0;c<sideWallGrid[r].length;c++) {
				sideWallGrid[r][c] = new Wall(cellGrid[r][c],cellGrid[r][c+1],activeWalls);
				cellGrid[r][c].rightWall = sideWallGrid[r][c];
				cellGrid[r][c+1].leftWall = sideWallGrid[r][c];
			}
		}
		for(int r = 0;r<topWallGrid.length;r++) {
			for(int c = 0;c<topWallGrid[r].length;c++) {
				topWallGrid[r][c] = new Wall(cellGrid[r][c], cellGrid[r+1][c],activeWalls);
				cellGrid[r][c].bottomWall = topWallGrid[r][c];
				cellGrid[r+1][c].topWall = topWallGrid[r][c];
			}
		}
		rand = new Random();
	}
	public ArrayList<Wall> getWallList() {
		ArrayList<Wall> allWalls = new ArrayList<Wall>();
		for(Wall[] wallArr : sideWallGrid) {
			for(Wall w : wallArr) {
				allWalls.add(w);
			}
		}
		for(Wall[] wallArr : topWallGrid) {
			for(Wall w : wallArr) {
				allWalls.add(w);
			}
		}
		return allWalls;
	}
	public ArrayList<Cell> getCellList() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(Cell[] CellArr : cellGrid) {
			for(Cell c : CellArr) {
				cells.add(c);
			}
		}
		return cells;
	}
	private void setAllWallsActive(boolean active) {
		for(Wall[] wallArr : sideWallGrid) {
			for(Wall w : wallArr) {
				w.isActive = active;
			}
		}
		for(Wall[] wallArr : topWallGrid) {
			for(Wall w : wallArr) {
				w.isActive = active;
			}
		}
	}
	private void setAllCellsVisited(boolean visited) {
		for(Cell[] cellArr : cellGrid) {
			for(Cell c : cellArr) {
				c.visited = visited;
			}
		}
	}
	//uses recursive backtracking to make a perfect maze.
	public void makePerfectMaze() {
		setAllWallsActive(true);
		Stack<Cell> pastCells = new Stack<Cell>();
		rand.setSeed(System.nanoTime());
		Cell currentCell = cellGrid[rand.nextInt(height)][rand.nextInt(width)];
		boolean hasDoneAnIteration = false;
		while(hasDoneAnIteration==false || pastCells.isEmpty() == false) {
			ArrayList<Cell> adjacentCells = getAdjacentCells(currentCell,true);
			if(adjacentCells.isEmpty()) {
				currentCell.visited = true;
				if(pastCells.isEmpty()) {
					break;
				} else {
					currentCell = pastCells.pop();
					continue;
				}
			}
			rand.setSeed(System.nanoTime());
			Collections.shuffle(adjacentCells,rand);
			Cell nextCell = adjacentCells.get(0);
			if(nextCell.row > currentCell.row) {
				currentCell.bottomWall.isActive = false;
				nextCell.topWall.isActive = false;
			}
			if(nextCell.row < currentCell.row) {
				currentCell.topWall.isActive = false;
				nextCell.bottomWall.isActive = false;
			}
			if(nextCell.col > currentCell.col) {
				currentCell.rightWall.isActive = false;
				nextCell.leftWall.isActive = false;
			}
			if(nextCell.col < currentCell.col) {
				currentCell.leftWall.isActive = false;
				nextCell.rightWall.isActive = false;
			}
			currentCell.visited = true;
			pastCells.push(currentCell);
			currentCell = nextCell;
		}
	}
	
	//makes braid maze
	public void makeBraidMaze() {
		setAllWallsActive(false);
		ArrayList<Wall> walls = getWallList();
		rand.setSeed(System.nanoTime());
		Collections.shuffle(walls,rand);
		Iterator<Wall> wallIterator = walls.iterator();
		while(wallIterator.hasNext()) {
			Wall currentWall = wallIterator.next();
			//if making this wall active would not create a dead end, make this wall active.
			if(currentWall.c1.getAdjacentWalls()<2 && currentWall.c2.getAdjacentWalls()<2) {
				currentWall.isActive = true;
			} else {
				currentWall.isActive = false;
			}
		}
		this.removeIsolation();
	}

	public String toString() {
		String results = "";
		for(Cell[] cellrow : cellGrid) {
			for(Cell cell : cellrow) {
				results += (cell.getAdjacentWalls()>2)?"@":" ";
			}
			results += "\n";
		}
		return results;
	}
	
	public void floodFill() {
		floodFill(0,0);
	}
	
	public void floodFill(int r, int c) {
		setAllCellsVisited(false);
		Cell startCell = cellGrid[r][c];
		Queue<Cell> queuedCells = new LinkedList<Cell>();
		queuedCells.add(startCell);
		while(queuedCells.size() > 0) {
			Cell currentCell = queuedCells.poll();
			if(currentCell == null) {
				break;
			}
			currentCell.visited = true;
			ArrayList<Cell> adjacentCells = getAdjacentCells(currentCell,true);
			for(Cell newCell : adjacentCells) {
				if(currentCell.wallBetween(newCell) == false) {
					queuedCells.add(newCell);
				}
			}
		}
	}
	
	private ArrayList<Cell> getAdjacentCells(Cell c, boolean requiresUnvisited) {
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		int currentRow = c.row;
		int currentCol = c.col;
		if(requiresUnvisited) {
			if(currentRow > 0 && cellGrid[currentRow-1][currentCol].visited == false) {
				adjacentCells.add(cellGrid[currentRow-1][currentCol]);
			}
			if(currentRow < cellGrid.length-1 && cellGrid[currentRow+1][currentCol].visited == false) {
				adjacentCells.add(cellGrid[currentRow+1][currentCol]);
			}
			if(currentCol > 0 && cellGrid[currentRow][currentCol-1].visited == false) {
				adjacentCells.add(cellGrid[currentRow][currentCol-1]);
			}
			if(currentCol < cellGrid[currentRow].length-1 && cellGrid[currentRow][currentCol+1].visited == false) {
				adjacentCells.add(cellGrid[currentRow][currentCol+1]);
			}
		} else {
			if(currentRow > 0) {
				adjacentCells.add(cellGrid[currentRow-1][currentCol]);
			}
			if(currentRow < cellGrid.length-1) {
				adjacentCells.add(cellGrid[currentRow+1][currentCol]);
			}
			if(currentCol > 0) {
				adjacentCells.add(cellGrid[currentRow][currentCol-1]);
			}
			if(currentCol < cellGrid[currentRow].length-1) {
				adjacentCells.add(cellGrid[currentRow][currentCol+1]);
			}
		}
		return adjacentCells;
	}

	private void removeIsolation() {
		int currentRow = 0;
		int currentCol = 0;
		boolean hasChanged = true;
		while(hasChanged == true) {
			hasChanged = false;
			Maze floodedMaze = new Maze(this);
			floodedMaze.floodFill(currentRow,currentCol);
			ArrayList<Cell> allCells = floodedMaze.getCellList();
			rand.setSeed(System.nanoTime());
			Collections.shuffle(allCells, rand);
			boolean hasUnvisited = false;
			for(Cell c: allCells) {
				if(c.visited == false) {
					hasUnvisited = true;
					break;
				}
			}
			if(hasUnvisited == false) {
				break;
			}
			for(Cell c : allCells) {
				if(c.visited) {
					ArrayList<Cell> adjacentCells = getAdjacentCells(c,true);
					if(adjacentCells.size() > 0) {
						rand.setSeed(System.nanoTime());
						Collections.shuffle(adjacentCells, rand);
						Cell newCell = adjacentCells.get(0);
						Cell originalMazeCell1 = cellGrid[c.row][c.col];
						Cell originalMazeCell2 = cellGrid[newCell.row][newCell.col];
						Wall newWall = originalMazeCell1.getWallBetween(originalMazeCell2);
						if(newWall != null) {
							newWall.isActive = false;
							currentRow = newCell.row;
							currentCol = newCell.col;
							hasChanged = true;
							break;
						}
					}
				}
			}
		}
	}
}
