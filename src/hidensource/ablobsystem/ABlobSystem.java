/*
 * 
 */
package hidensource.ablobsystem;

import java.util.ArrayList;
import gab.opencv.Contour;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.awt.Rectangle;

// TODO: Auto-generated Javadoc
/**
 * ABlobSystem
 * 
 * Simple wrap for Processing openCV Blob Detector
 * 
 * requires openCV_for_processing
 * https://github.com/atduskgreg/opencv-processing
 * 
 * @author andru
 * @version 20160101
 *
 */
public class ABlobSystem {

	/** The thres. */
	private int thres = 167;

	/** The polygon approximation factor. */
	private float polygonApproximationFactor;
	/** The blur. */
	private int blur = 4;

	/** The max blob size. */
	private int max_blob_size = 9999999;

	/** The min blob size. */
	private int min_blob_size = 0;

	/** The contours. */
	private ArrayList<Contour> contours;

	/** The parent. */
	private PApplet parent;

	/** The ablobs. */
	// private ArrayList<Contour> polygons;
	private ArrayList<ABlob> ablobs;

	/** The imgout. */
	private PImage imgout;

	public Contour convexHull;
	
	private OpenCV ocv;

	/**
	 * Instantiates a new a blob system.
	 *
	 * @param parent
	 *            the parent
	 * @param _w
	 *            the w
	 * @param _h
	 *            the h
	 */
	public ABlobSystem(PApplet parent, int _w, int _h) {
		// super(parent, _w, _h);
		ocv = new OpenCV(parent, _w, _h);
		contours = new ArrayList<Contour>();
		this.parent = parent;
		polygonApproximationFactor = 1f;
	}

	/**
	 * Gets the blobs.
	 *
	 * @param _img
	 *            the img
	 * @return the blobs
	 */
	public ArrayList<ABlob> getBlobs(PImage _img) {
		ocv.loadImage(_img);
		ocv.gray();
		ocv.blur(blur);
		ocv.threshold(thres);
		imgout = ocv.getOutput();
		ablobs = new ArrayList<ABlob>();

		contours = ocv.findContours();
		for (Contour contour : contours) {
			ABlob ab = new ABlob(this.parent);

			if (contour.area() >= min_blob_size && contour.area() <= max_blob_size) {
				contour.setPolygonApproximationFactor(polygonApproximationFactor);
				convexHull = contour.getConvexHull();
				convexHull.getPoints();
				Rectangle blob = contour.getBoundingBox();
				PVector center = new PVector(blob.x + blob.width / 2, blob.y + blob.height / 2);
				ab.cx = center.x;
				ab.cy = center.y;
				ab.x = blob.x;
				ab.y = blob.y;
				ab.width = blob.width;
				ab.height = blob.height;
				ab.area = contour.area();
				ablobs.add(ab);
				ab.contourPoints = contour.getPolygonApproximation().getPoints();
				ab.convexHullPoints =  contour.getConvexHull().getPoints();
				ab.contour = contour;
			}
		}
		return ablobs;

	}

	/**
	 * Draw box.
	 *
	 * @param _b
	 *            the b
	 * @param _c
	 *            the c
	 */
	public void drawBox(boolean _b, boolean _c) {
		for (Contour contour : contours) {
			if (contour.area() >= min_blob_size && contour.area() <= max_blob_size) {
				Rectangle blob = contour.getBoundingBox();
				PVector center = new PVector(blob.x + blob.width / 2, blob.y + blob.height / 2);
				// Draw bounding box
				if (_b) {
					parent.pushStyle();
					parent.stroke(0, 255, 0);
					parent.noFill();
					parent.rect(blob.x, blob.y, blob.width, blob.height);
					parent.popStyle();
				}
				// Draw center point
				if (_c) {
					parent.pushStyle();
					parent.stroke(255, 0, 0);
					parent.strokeWeight(3);
					parent.point(center.x, center.y);
					parent.popStyle();
				}

			}
		}
	}

	/**
	 * Draw box.
	 *
	 * @param _b
	 *            the b
	 */
	public void drawBox(boolean _b) {
		drawBox(_b, false);
	}

	/**
	 * Sets the max size.
	 *
	 * @param _s
	 *            the new max size
	 */
	public void setMaxSize(int _s) {
		max_blob_size = _s;
	}

	/**
	 * Sets the min size.
	 *
	 * @param _s
	 *            the new min size
	 */
	public void setMinSize(int _s) {
		min_blob_size = _s;
	}

	/**
	 * Sets the threshold.
	 *
	 * @param _t
	 *            the new threshold
	 */
	public void setThreshold(int _t) {
		thres = _t;
	}

	/**
	 * Sets the blur.
	 *
	 * @param _b
	 *            the new blur
	 */
	public void setBlur(int _b) {
		blur = _b;
	}

	/**
	 * Sets the contour detail.
	 *
	 * @param _d
	 *            the new contour detail
	 */
	public void setContourDetail(float _d) {
		polygonApproximationFactor = _d;
	}

	/**
	 * Out img.
	 *
	 * @return the p image
	 */
	public PImage outImg() {
		return imgout;
	}
}
