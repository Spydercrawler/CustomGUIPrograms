package RotatingRectangle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Tester {
	private static void OpenFrame() {
		System.out.println("Created GUI on EDT? "+
		        SwingUtilities.isEventDispatchThread());
		JFrame f = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		f.setTitle("Rectangle");
		RectanglePanel p = new RectanglePanel();
        f.add(p);
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
}
