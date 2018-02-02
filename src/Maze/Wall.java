package Maze;

public class Wall {
	public Cell c1;
	public Cell c2;
	public boolean isActive = true;
	public Wall(Cell c1, Cell c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	public Wall(Cell c1, Cell c2, boolean isActive) {
		this.c1 = c1;
		this.c2 = c2;
		this.isActive = isActive;
	}
}
