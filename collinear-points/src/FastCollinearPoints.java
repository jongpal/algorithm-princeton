//package io.jongpal;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;



public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegmentsList;
    private final Point[] originalPoints;
    private final Point[] sortedPoints;
    /*
     * Rep Invariant
     * 
     * - original Points should not be modified
     * - there should not exist duplicate points
     * - all modification(ex)sorting) is acted upon in sortedPoints.
     * 
     * Safety from Rep Exposure
     * 
     * - all construction is done under defensive copying
     * - all reps are called 'private' and 'final'
     */
    
   
    /**
     * 
     * @param points should not contain null value
     */
    public FastCollinearPoints(Point[] points) {
        /*
         * Strategy
         * 1. copy the point data to store initial points
         * 2. make another point array to store sorted results
         * 3. looping 1. points datas, do 3 way partitioning based on the point
         * 4. for every loop of partitioning, check gt - lt and see if duplicate keys are >= 3
         * 5. if so, check if this point is smallest among them
         * 6. if smallest, get biggest Point and put those two Point to lineSegmentList.
         * 
         */
    	if (points == null) throw new IllegalArgumentException();

    	for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            
        }
    	lineSegmentsList = new ArrayList<LineSegment>();
    	originalPoints = points.clone();
        sortedPoints = points.clone();
    	// in sorted result, the first element should always be itself because the slope in this case
    	// would be negative infinity
    	for (Point p: originalPoints) {
    		threeWaySort(sortedPoints, p, 0, originalPoints.length - 1);
    		if (sortedPoints.length > 1 && p.compareTo(sortedPoints[1]) == 0) {
    			throw new IllegalArgumentException("duplicate value error");
    		}
    	}
    	
    }
    /**
     * do 3 way partitioning and check 
     * 
     * 
     * @param c Comparator that has compare() method in it
     * @param pointSet point set to sort, should be randomly shuffled before
     * @param lo lowest index of the point set
     * @param hi highest index of the point set
     */
    private void threeWaySort(Point[] pointSet, Point comparePoint, int lo, int hi) {
    	if (hi <= lo) return;
    	int lt = lo;
    	int i = lt + 1;
    	int gt = hi;
    	while (i <= gt) {
    		int cmp = comparePoint.slopeOrder().compare(pointSet[lt], pointSet[i]);
    		if (cmp < 0) {
    			exch(pointSet, i, gt--);
      		}
    		else if (cmp > 0) {
    			exch(pointSet, lt++, i++);
    		}
    		else i++;
    	}
    	// checking collinear points
    	if ((gt - lt + 1) >= 3) {
    		Point []pointSetCheck = new Point[gt - lt + 1];
    		int j = 0;
    		for (int cur = lt; cur <= gt; cur++) {
    			pointSetCheck[j++] = pointSet[cur];
    		}
    		if (isSmallestPoint(pointSetCheck, comparePoint)) {
    			Point biggest = getBiggestPoint(pointSetCheck);
    			LineSegment ls = new LineSegment(comparePoint, biggest);
                lineSegmentsList.add(ls);
    		}
    	}
    	
    	threeWaySort(pointSet, comparePoint, lo, lt - 1);
    	threeWaySort(pointSet, comparePoint, gt + 1, hi);
    }
    /**
     * exchange elements inside of array
     * @param obj
     * @param i
     * @param j
     */
    private void exch(Object[] obj, int i, int j) {
    	Object temp = obj[i];
    	obj[i] = obj[j];
    	obj[j] = temp;
    }

    /**
     * 
     * @param pointSet
     * @param p1
     * @return
     */
    private boolean isSmallestPoint(Point []pointSet, Point p1) {
    	for (Point p : pointSet) {
    		if (p1.compareTo(p) >= 0) return false;
    	}
    	return true;
    }
    /**
     * 
     * @param pointSet should not be null
     * @return biggest point among this pointSet
     */
    private Point getBiggestPoint(Point []pointSet) {
    	Point biggest = pointSet[0];
    	for (int i = 1; i < pointSet.length; i++) {
    		if (pointSet[i].compareTo(biggest) > 0) biggest = pointSet[i];
    	}
    	return biggest;
    }
    
    public int numberOfSegments() {
        return lineSegmentsList.size();
    }
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[lineSegmentsList.size()];
        int k = 0;
        for (LineSegment l : lineSegmentsList) {
//            LineSegment ls = new LineSegment(l.p, l.q);
//            lineSegments[k++] = ls;
        	lineSegments[k++] = l;
        }
        return lineSegments;        
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }        
        
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);       
        
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//        StdOut.println("here we go");
//        StdOut.printf("%d,%d - %d,%d\n", segment.p.x, segment.p.y, segment.q.x, segment.q.y);
            segment.draw();
        }
        StdDraw.show();    
    }
}
