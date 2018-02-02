package SeperatingAxis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class CollisionPanel extends JPanel {
	private static final long serialVersionUID = -2265446412106619942L;
	public final int width = 1000;
	private List<RotatedRectangle> rlst;
	private CollisionDetector c;
	private RotatedRectangle selectedShape;
	
	public CollisionPanel(List<RotatedRectangle> rlst) {
		this.rlst = rlst;
		c = new CollisionDetector();
		this.addMouseListener(new mouseClicking());
		this.addMouseMotionListener(new mouseMotion());
	}
	public CollisionPanel() {
		this.rlst = new ArrayList<RotatedRectangle>();
		c = new CollisionDetector();
		this.addMouseListener(new mouseClicking());
		this.addMouseMotionListener(new mouseMotion());
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(width,width);
    }
	
	public void add(RotatedRectangle s) {
		rlst.add(s);
	}
	
	public void addAll(List<RotatedRectangle> sList) {
		rlst.addAll(sList);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(Shape r1 : rlst) {
			boolean r1colliding = false;
			for(Shape r2 : rlst) {
				if(!r1.equals(r2)) {
					if(c.detectCollision(r1, r2)) {
						r1colliding = true;
						break;
					}
				}
			}
			if(selectedShape != null && r1.equals(selectedShape)) { 
				g2.setColor(Color.BLUE);
				g2.fill(r1);
			} else if(r1colliding) {
				g2.setColor(Color.RED);
				g2.fill(r1);
			} else {
				g2.setColor(Color.GREEN);
				g2.fill(r1);
			}
		}
	}
	
	public void addRect(RotatedRectangle r) {
		rlst.add(r);
	}
	
	private class mouseMotion implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			if(selectedShape != null) {
				int newX = (int) (e.getX()-(selectedShape.getWidth()/2));
				int newY = (int) (e.getY()-(selectedShape.getHeight()/2));
				selectedShape.setLocation(newX,newY);
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(selectedShape != null) {
				int newX = (int) (e.getX()-(selectedShape.getWidth()/2));
				int newY = (int) (e.getY()-(selectedShape.getHeight()/2));
				selectedShape.setLocation(newX,newY);
			}
			repaint();
		}
		
	}
	private class mouseClicking implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			selectedShape = null;
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			selectedShape = null;
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			for(RotatedRectangle s : rlst) {
				if(s.contains(arg0.getPoint())) {
					selectedShape = s;
				}
			}
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			selectedShape = null;
			repaint();
		}
		
	}
}
