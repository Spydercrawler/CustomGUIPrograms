package Maze;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MazePanel extends JPanel {
	Maze internalMaze;
	int cellSize = 20;
	int wallSize = 2;
	public MazePanel(Maze maze) {
		internalMaze = maze;
	}
	public Dimension getPreferredSize() {
		int cellWidths = internalMaze.width * cellSize;
		int cellHeights = internalMaze.height *  cellSize;
		int wallWidths = (internalMaze.width-1) * wallSize;
		int wallHeights = (internalMaze.height-1) * wallSize;
        return new Dimension(cellWidths+wallWidths,cellHeights+wallHeights);
    }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//Add points between walls so maze looks nice
		for(int i = 1;i<internalMaze.width;i++) {
			for(int j = 1;j<internalMaze.height;j++) {
				int x = i*cellSize + ((i-1)*wallSize);
				int y = j*cellSize + ((j-1)*wallSize);
				g2.fillRect(x, y, wallSize, wallSize);
			}
		}
		//Add side walls
		for(int i = 0;i<internalMaze.width-1;i++) {
			for(int j = 0;j<internalMaze.height;j++) {
				if(internalMaze.sideWallGrid[j][i].isActive == false) {
					continue;
				}
				int x = (i+1)*cellSize + (i*wallSize);
				int y = j*cellSize + (j*wallSize);
				g2.fillRect(x, y, wallSize, cellSize);
			}
		}
		//Add top walls
		for(int i = 0;i<internalMaze.width;i++) {
			for(int j = 0;j<internalMaze.height-1;j++) {
				if(internalMaze.topWallGrid[j][i].isActive == false) {
					continue;
				}
				int x = i*cellSize + (i*wallSize);
				int y = (j+1)*cellSize + (j*wallSize);
				g2.fillRect(x, y, cellSize, wallSize);
			}
		}
	}
}
