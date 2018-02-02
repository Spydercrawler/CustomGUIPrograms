package Maze;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Runner {

	public static void main(String[] args) {
		try {
			Maze blankMaze = new Maze(20,20);
			//The following code makes this maze a perfect maze:
			//blankMaze.makePerfectMaze();
			//The following code makes this made a braid (looping) maze:
			blankMaze.makeBraidMaze();
			//Prints dead ends
			//System.out.println(blankMaze);
			//make the actual panel.
			System.out.println("Created GUI on EDT? "+
			        SwingUtilities.isEventDispatchThread());
			JFrame f = new JFrame();
			f.setTitle("Maze thing");
	        f.add(new MazePanel(blankMaze));
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.pack();
	        f.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
