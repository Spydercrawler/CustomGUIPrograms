package RotatingRectangle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class RectanglePanel extends JPanel{
	private Rectangle rect;
	private float angle;
	public float RPS = 0.5f;
	public static int rectangleWidth = 50;
	public static int rectangleHeight = 50;
	public static final int width = 400;
	public static final int TARGET_FPS = 60;
	public static final long OPTIMAL_LOOP_TIME = 1000000000 / TARGET_FPS;
	final ScheduledExecutorService clockUpdater;
	
	public Dimension getPreferredSize() {
        return new Dimension(width,width);
    }
	public RectanglePanel() {
		angle = 0;
		rect = new Rectangle((width/2)-(rectangleWidth/2),(width/2)-(rectangleHeight/2),rectangleWidth,rectangleHeight);
		clockUpdater = Executors.newSingleThreadScheduledExecutor();
		clockUpdater.scheduleWithFixedDelay(updateScript, 0, OPTIMAL_LOOP_TIME, TimeUnit.NANOSECONDS);
	}
	Runnable updateScript = new Runnable() {
		@Override
        public void run() {
			angle += RPS/TARGET_FPS;
			repaint();
        }
	};
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform t = new AffineTransform();
		t.translate(rect.getCenterX(), rect.getCenterY());
		t.rotate(angle);
		t.translate(-rect.getCenterX(), -rect.getCenterY());
		Shape s = t.createTransformedShape(rect);
		g2.draw(s);
		g2.fill(s);
	}
}
