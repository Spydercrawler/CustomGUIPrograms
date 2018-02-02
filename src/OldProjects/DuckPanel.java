package OldProjects;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DuckPanel extends JPanel {
	private BufferedImage duck;
	private int duckX;
	private int duckY;
	private int duckWidth = 100;
	private int duckHeight = 100;
	private static void OpenFrame() {
		System.out.println("Created GUI on EDT? "+
		        SwingUtilities.isEventDispatchThread());
		JFrame f = new JFrame();
		f.setTitle("Ducks");
        f.add(new DuckPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OpenFrame();
            }
        });
	}
	public DuckPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		try {
			duck = ImageIO.read(new File("Duck.jpg"));
			duckX = 100;
			duckY = 100;
		} catch (IOException e) {
			System.out.println("Error loading Duck image, closing...");
			System.exit(0);
		}
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveDuck(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveDuck(e.getX(),e.getY());
            }
        });
	}
	private void moveDuck(int x, int y) {
		if ((duckX!=x) || (duckY!=y)) {
			duckX = x;
			duckY = y;
			repaint();
		}
	}
	public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw Text
        g.drawImage(duck, duckX-(duckWidth/2), duckY-(duckHeight/2), duckWidth, duckHeight, null);
    }  
}
