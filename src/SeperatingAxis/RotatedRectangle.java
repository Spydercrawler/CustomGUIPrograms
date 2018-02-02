package SeperatingAxis;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RotatedRectangle implements Shape{
	private Rectangle rect;
	private double rotation;
	private Shape transformedShape;
	
	//Constructors
	public RotatedRectangle() {
		rect = new Rectangle();
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(Dimension d) {
		rect = new Rectangle(d);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(int width, int height) {
		rect = new Rectangle(width,height);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(int x, int y, int width, int height) {
		rect = new Rectangle(x,y,width,height);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(Point p) {
		rect = new Rectangle(p);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(Point p, Dimension d) {
		rect = new Rectangle(p,d);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(Rectangle r) {
		rect = new Rectangle(r);
		rotation = 0;
		transformShape();
	}
	public RotatedRectangle(int width, int height, double theta) {
		rect = new Rectangle(width,height);
		rotation = theta;
		transformShape();
	}
	public RotatedRectangle(int x, int y, int width, int height, double theta) {
		rect = new Rectangle(x,y,width,height);
		rotation = theta;
		transformShape();
	}
	public RotatedRectangle(Dimension d, double theta) {
		rect = new Rectangle(d);
		rotation = theta;
		transformShape();
	}
	public RotatedRectangle(Point p, double theta) {
		rect = new Rectangle(p);
		rotation = theta;
		transformShape();
	}
	public RotatedRectangle(Point p, Dimension d, double theta) {
		rect = new Rectangle(p,d);
		rotation = theta;
		transformShape();
	}
	public RotatedRectangle(RotatedRectangle r) {
		rect = new Rectangle(r.getRect());
		rotation = r.getRotation();
		transformShape();
	}
	//Shape methods
	@Override
	public boolean contains(Point2D p) {
		return transformedShape.contains(p);
	}
	@Override
	public boolean contains(Rectangle2D r) {
		return transformedShape.contains(r);
	}
	@Override
	public boolean contains(double x, double y) {
		return transformedShape.contains(x, y);
	}
	@Override
	public boolean contains(double x, double y, double w, double h) {
		return transformedShape.contains(x, y, w, h);
	}
	@Override
	public Rectangle getBounds() {
		return transformedShape.getBounds();
	}
	@Override
	public Rectangle2D getBounds2D() {
		return transformedShape.getBounds2D();
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return transformedShape.getPathIterator(at);
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return transformedShape.getPathIterator(at, flatness);
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		return transformedShape.intersects(r);
	}
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return transformedShape.intersects(x, y, w, h);
	}
	
	//Class methods
	//	Getters
	public Rectangle getRect() {
		return rect;
	}
	public double getRotation() {
		return rotation;
	}
	public double getCenterX() {
		return rect.getCenterX();
	}
	public double getCenterY() {
		return rect.getCenterY();
	}
	//	Setters and other methods
	public void setRotation(double rotation) {
		this.rotation = rotation;
		transformShape();
	}
	public void rotate(double theta) {
		rotation += theta;
		transformShape();
	}
	public void translate(int dx, int dy) {
		rect.translate(dx, dy);
		transformShape();
	}
	public void setLocation(int x, int y) {
		rect.setLocation(x,y);
		transformShape();
	}
	public void setLocation(Point p) {
		rect.setLocation(p);
		transformShape();
	}
	public void grow(int h, int v) {
		rect.grow(h, v);
		transformShape();
	}
	public void setSize(Dimension d) {
		rect.setSize(d);
		transformShape();
	}
	public void setSize(int width, int height) {
		rect.setSize(width, height);
		transformShape();
	}
	public double getWidth() {
		return rect.getWidth();
	}
	public double getHeight() {
		return rect.getHeight();
	}
	
	//Private methods
	private void transformShape() {
		AffineTransform t = new AffineTransform();
		t.translate(rect.getCenterX(), rect.getCenterY());
		t.rotate(rotation);
		t.translate(-rect.getCenterX(), -rect.getCenterY());
		transformedShape = t.createTransformedShape(rect);
	}
}
