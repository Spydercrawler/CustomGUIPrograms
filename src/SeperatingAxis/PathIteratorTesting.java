package SeperatingAxis;

import java.awt.Rectangle;
import java.awt.geom.PathIterator;
import java.util.Arrays;

public class PathIteratorTesting {

	public static void main(String[] args) {
		Rectangle r = new Rectangle();
		PathIterator p = r.getPathIterator(null);
		double [] vals = new double[6];
		while(!p.isDone()) {
			Arrays.fill(vals, 0);
			int type = p.currentSegment(vals);
			p.next();
			System.out.println(type);
		}
	}

}
