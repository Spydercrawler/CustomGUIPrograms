package SeperatingAxis;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class CollisionDetector {
	AffineTransform trans;
	
	public CollisionDetector() {
		trans = null;
	}
	
	public CollisionDetector(AffineTransform transform) {
		trans = transform;
	}
	
	public ArrayList<Vector> getAxisList (Shape s1, Shape s2) {
		ArrayList<Vector> finalList = new ArrayList<Vector>();
		finalList.addAll(getSingleAxisList(s1));
		finalList.addAll(getSingleAxisList(s2));
		return finalList;
	}
	
	public boolean detectCollision(Shape s1, Shape s2) {
		ArrayList<Vector> axisList = getAxisList(s1,s2);
		for(Vector axis : axisList) {
			double[] s1Bounds = ProjectPolygon(axis,s1);
			double[] s2Bounds = ProjectPolygon(axis,s2);
			if(notColliding(s1Bounds[0],s1Bounds[1],s2Bounds[0],s2Bounds[1])) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Vector> getSingleAxisList(Shape s) {
		ArrayList<Vector> lst = new ArrayList<Vector>();
		PathIterator i = s.getPathIterator(trans);
		double [] svals = new double[6];
		double [] psvals = new double[6];
		Arrays.fill(psvals, 0);
		while(!i.isDone()) {
			psvals = Arrays.copyOf(svals,svals.length);
			Arrays.fill(svals, 0);
			int type = i.currentSegment(svals);
			i.next();
			if(type == PathIterator.SEG_LINETO) {
				Point2D startingPoint = new Point2D.Double(psvals[0],psvals[1]);
				Point2D endingPoint = new Point2D.Double(svals[0],svals[1]);
				Vector axisVector = new Vector(startingPoint,endingPoint).normalized();
				lst.add(axisVector);
			}
		}
		return lst;
	}
	
	private ArrayList<Vector> getPoints(Shape s) {
		ArrayList<Vector> lst = new ArrayList<Vector>();
		PathIterator i = s.getPathIterator(trans);
		double [] vals = new double[6];
		while(!i.isDone()) {
			Arrays.fill(vals, 0);
			int type = i.currentSegment(vals);
			i.next();
			if(type == PathIterator.SEG_LINETO || type == PathIterator.SEG_MOVETO) {
				Vector vertex = new Vector(vals[0], vals[1]);
				lst.add(vertex);
			}
		}
		return lst;
	}
	
	private double[] ProjectPolygon(Vector axis, Shape s) {
		//To project point onto axis use dot product
		ArrayList<Vector> shapePoints = getPoints(s);
		double dotProduct = axis.dot(shapePoints.get(0));
		//Index 0 will be min, index 1 will be max
		double[] bounds = {dotProduct,dotProduct};
		for(Vector val : shapePoints) {
			dotProduct = axis.dot(val);
			if(dotProduct<bounds[0]) {
				bounds[0] = dotProduct;
			} else if (dotProduct > bounds[1]) {
				bounds[1] = dotProduct;
			}
		}
		return bounds;
	}

	private boolean notColliding(double minA, double maxA, double minB, double maxB) {
		if(minA>minB && maxA<maxB) {
			return false;
		} else if (minA<minB && maxA>maxB) {
			return false;
		}
		if (minA < minB) {
	        return (minB - maxA > 0);
	    } else {
	        return (minA - maxB > 0);
	    }
	}
}
