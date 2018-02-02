package SeperatingAxis;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Vector {
	public static final int POLAR = 0;
	public static final int RECTANGULAR = 1;
	public double magnitude;
	public double direction;
	public Vector() {
		magnitude = 1;
		direction = 0;
	}
	public Vector(double magnitude, double direction) {
		this.magnitude = magnitude;
		this.direction = direction;
	}
	public Vector(int x, int y) {
		this.magnitude = Math.sqrt((x*x) + (y&y));
		this.direction = Math.atan2(y, x);
	}
	public Vector(Point2D p) {
		this(p.getX(),p.getY());
	}
	public Vector(int x1, int y1, int x2, int y2) {
		this(x2-x1,y2-y1);
	}
	public Vector(Point2D p1, Point2D p2) {
		this(p2.getX()-p1.getX(),p2.getY()-p1.getY());
	}
	public Vector(double numtype, double arg1, double arg2) throws Exception {
		if(numtype == 0) {
			this.magnitude = arg1;
			this.direction = arg2;
		} else if (numtype == 1) {
			this.magnitude = Math.sqrt((arg1*arg1) + (arg2*arg2));
			this.direction = Math.atan2(arg2, arg1);
		} else {
			throw new Exception("Invalid numtype!");
		}
	}
	public double getX() {
		return Math.cos(direction) * magnitude;
	}
	public double getY() {
		return Math.sin(direction) * magnitude;
	}
	public double dot(Vector other) {
		return (getX() * other.getX()) + (getY() * other.getY());
	}
	public void normalize() {
		magnitude = 1;
	}
	public Vector normalized() {
		return new Vector(1.0,direction);
	}
	public static double dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}
}
