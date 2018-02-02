package SeperatingAxis;

import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JFrame;

import Physics.PhysicsPanel;

public class CollisionRunner {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		// f.setUndecorated(true);
		// f.setBackground(new Color(0, 0, 0, 0));
		f.setTitle("Collision");
		CollisionPanel p = new CollisionPanel();
		RotatedRectangle r1 = new RotatedRectangle(200,200,40,40);
		RotatedRectangle r2 = new RotatedRectangle(250,200,40,40);
		//RotatedRectangle r3 = new RotatedRectangle(190,190,20,20);
		//RotatedRectangle r4 = new RotatedRectangle(150,150,30,30);
		p.add(r1);
		p.add(r2);
		//p.add(r3);
		//p.add(r4);
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
	}

}
