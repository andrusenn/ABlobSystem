/*
 * 
 */
package hidensource.ablobsystem;

import java.util.ArrayList;
import gab.opencv.Contour;
import processing.core.PVector;
import processing.core.PApplet;

// TODO: Auto-generated Javadoc
/**
 * The Class ABlob.
 */
public class ABlob {

	/** The cy. */
	public float cx, cy;

	/** The y. */
	public float x, y;

	/** The height. */
	public float width, height;

	/** The area. */
	public float area;

	/** The Contour. */
	public Contour contour;

	/** The contour points. */
	protected ArrayList<PVector> contourPoints;

	/** The convex hull points. */
	protected ArrayList<PVector> convexHullPoints;

	/**
	 * Constructor
	 */
	ABlob(PApplet _p) {
	}
/**
 * 
 * @param x
 * @param y
 * @return
 */
	public boolean containsPoint(int x, int y) {
		return contour.containsPoint(x, y);
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<PVector> getContourPoints() {
		return contourPoints;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<PVector> getConvexHullPoints() {
		return convexHullPoints;
	}
}
