package Physics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class PhysicsPanel extends JPanel {
	public static int rectangleWidth = 50;
	public static int rectangleHeight = 50;
	public static final int width = 400;
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_LOOP_TIME = 1000000000 / TARGET_FPS;
	
	public int timeScale;
	public double gravity = 9.8;
	public double cubeMass = 10;
	
	private Rectangle rect;
	private int localWidth;
	private int localHeight;
	//Physics Stuff
	private double xVelocity = 0;
	private double yVelocity = 0;
	private double gameGravity;
	
	boolean rectBeingClicked = false;
	Point mousePos;
	Point previousMousePos;
	final ScheduledExecutorService clockUpdater;
	
	public Dimension getPreferredSize() {
        return new Dimension(width,width);
    }
	public PhysicsPanel() {
		localWidth = width;
		localHeight = width;
		rect = new Rectangle((width/2)-(rectangleWidth/2),(width/2)-(rectangleHeight/2),rectangleWidth,rectangleHeight);
		gameGravity = gravity/TARGET_FPS;
		mousePos = new Point(0,0);
		previousMousePos = new Point(0,0);
		clockUpdater = Executors.newSingleThreadScheduledExecutor();
		clockUpdater.scheduleWithFixedDelay(updateScript, 0, OPTIMAL_LOOP_TIME, TimeUnit.NANOSECONDS);
		this.addMouseListener(new RectangleMouseListener());
		this.addMouseMotionListener(new RectangleMouseMotionListener());
	}
	Runnable updateScript = new Runnable() {
		@Override
        public void run() {
			Update();
        }
	};
	private void Update() {
		double newX = rect.getCenterX();
		double newY = rect.getCenterY();
		if(rectBeingClicked) {
			//Y Stuff
			if (rect.getHeight()/2 + mousePos.getY() <= 0) {
				newY = rect.getHeight()/2;
			} else if (rect.getHeight()/2 + mousePos.getY() >= this.getHeight()) {
				newY = this.getHeight() - rect.getHeight()/2;
			} else {
				newY = mousePos.getY();
			}
			double yDisplacement = mousePos.getY() - previousMousePos.getY();
			yVelocity = yDisplacement;
			//X Stuff
			if (rect.getWidth()/2 + mousePos.getX() <= 0) {
				newX = rect.getWidth()/2;
			} else if (rect.getWidth()/2 + mousePos.getX() >= this.getWidth()) {
				newX = this.getWidth() - rect.getWidth()/2;
			} else {
				newX = mousePos.getX();
			}
			double xDisplacement = mousePos.getX() - previousMousePos.getX();
			xVelocity = xDisplacement;
		} else {
			//Accelerate due to gravity
			yVelocity += gameGravity;
			//apply drag force
			//x force:
			double xDragAccel = getDragAccel(xVelocity);
			if(Math.abs(xDragAccel) > Math.abs(xVelocity)) {
				xVelocity = 0;
			} else {
				xVelocity += xDragAccel;
			}
			//y force:
			double yDragAccel = getDragAccel(yVelocity);
			if(Math.abs(yDragAccel) > Math.abs(yVelocity)) {
				yVelocity = 0;
			} else {
				yVelocity += yDragAccel;
			}
			//Y Stuff
			if(rect.getMinY() + yVelocity > 0 && rect.getMaxY() + yVelocity < this.getHeight()) {
				newY += yVelocity;
			} else if (rect.getMinY() + yVelocity <= 0) {
				newY = rect.getHeight()/2;
				yVelocity = 0;
			} else if (rect.getMaxY() + yVelocity >= this.getHeight()) {
				newY = this.getHeight() - rect.getHeight()/2;
				yVelocity = 0;
			}
			//X Stuff
			if(rect.getMinX() + xVelocity > 0 && rect.getMaxX() + xVelocity < this.getWidth()) {
				newX += xVelocity;
			} else if (rect.getMinX() + xVelocity <= 0) {
				newX = rect.getWidth()/2;
				xVelocity = 0;
			} else if (rect.getMaxX() + xVelocity >= this.getWidth()) {
				newX = this.getWidth() - rect.getWidth()/2;
				xVelocity = 0;
			}
		}
		updateRectPosition(newX,newY);
		repaint();
	}
	private double getDragAccel(double velocity) {
		//Add a drag force
		//Coefficient of drag for cube is 0.8, so I'll use that
		//The number in the front of the equation is 0.5 * Drag Coefficient (0.8) * Frontal Area (1 m^2) * pressure (1.22 kg/m^3)
		double dragForce = 0.488 * (velocity * velocity);
		//get acceleration from force
		double dragAccel = dragForce / cubeMass;
		//make it accurate with frame time
		dragAccel = dragAccel / TARGET_FPS;
		//give it appropriate direction
		if(velocity > 0) {
			dragAccel *= -1;
		}
		return dragAccel;
	}
	private void updateRectPosition(double x, double y) {
		rect.setLocation((int)(x-(rectangleWidth/2)), (int)(y-(rectangleHeight/2)));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(((float)localWidth)/width, ((float)localHeight) / width);
		g2.draw(rect);
	}
	private class RectangleMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			previousMousePos = mousePos;
			mousePos = e.getPoint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			previousMousePos = mousePos;
			mousePos = e.getPoint();
		}
		
	}
	private class RectangleMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			if(rect.contains(arg0.getPoint())) {
				rectBeingClicked = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			rectBeingClicked = false;
			double yDisplacement = mousePos.getY() - previousMousePos.getY();
		}
		
	}
}
