//package io.jongpal;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;

public class BruteCollinearPoints {
	/*
	 * Rep Invariant
	 * - points should not be null
	 * - points should not contain duplicate points
	 * - and create new Object every time at constructing phase
	 * 
	 * Abstract Function
	 * - points 			refer to the points randomly located inside of coordinates
	 * - lineSegmentsList   refers to two points that consitute a line.
	 * 
	 * Safety From Rep Exposure
	 * - all reps are declared with 'private', and 'final' keyword.
	 * - when this instance has to return rep info, it returns using new object
	 */
    private final ArrayList<ArrayList<Point>> lineSegmentsList;
    private final Point[] points;
    
    
    /**
     * 
     * @param points
     * @throws IllegalArgumentException with point that is null, or there exist duplicate points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
       
        for (int i = 0; i < points.length; i++) {
        	if (points[i] == null) throw new IllegalArgumentException(); 
        }
 
        this.points = points.clone();
        
        lineSegmentsList = new ArrayList<ArrayList<Point>>();
        Quick.sort(this.points);
        
        // check duplicates & nulls
        for (int i = 0; i < this.points.length - 1; i++) {
        	if (this.points[i].compareTo(this.points[i+1]) == 0)  throw new IllegalArgumentException("duplicate point");
        }
        
        for (int i = 0; i < this.points.length - 3; i++) {
            for (int j = i + 1; j < this.points.length - 2; j++) {
            	if (this.points[i].compareTo(this.points[j]) == 0)  throw new IllegalArgumentException("duplicate point");
                for (int k = j + 1; k < this.points.length - 1; k++) {    
                	if (this.points[j].compareTo(this.points[k]) == 0)  throw new IllegalArgumentException("duplicate point");          
                    // if two slopes are different -> go to next move
                    if (this.points[i].slopeOrder().compare(this.points[j], this.points[k]) != 0) continue;
                    for (int l = k + 1; l < this.points.length; l++) {  
                        if (this.points[k].compareTo(this.points[l]) == 0)  throw new IllegalArgumentException("duplicate point");                   
                        if (this.points[i].slopeOrder().compare(this.points[k], this.points[l]) != 0) continue;
                        // if all three slopes are equal, add it to solutionList
                        if(!isSameLine(this.points[i], this.points[l])) {
                            ArrayList<Point> ls = new ArrayList<Point>(); 
                            ls.add(this.points[i]);
                            ls.add(this.points[l]);
                            lineSegmentsList.add(ls);
                        }
                    }
                }
            }
        }
        
    }

    /**
     * Determine if there already exists a same line containing these two points
     * and set a line segment again if same line exists
     * 
     * @param p1 point1
     * @param p2 point2
     * @return false if there isn't a same line 
     */
    private boolean isSameLine(Point p1, Point p2) {
        for (int i = 0; i < lineSegmentsList.size(); i++) {
            Point a = lineSegmentsList.get(i).get(0);
            Point b = lineSegmentsList.get(i).get(1);
          
            // could be same slope but with different y intercept, so cross-validate it
            if (a.slopeTo(b) == p1.slopeTo(p2) && a.slopeTo(p2) == p1.slopeTo(b)) {
                // if slope is same, determine which one covers wider range
                // adjusting same line
                if (a.compareTo(p1) == -1) return true;
                else {
                    if (b.compareTo(p2) >= 0) return true;
                    else {
                        lineSegmentsList.get(i).set(0, p1);
                        lineSegmentsList.get(i).set(1, p2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int numberOfSegments() {
        return this.lineSegmentsList.size();
    }
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[lineSegmentsList.size()];
        int k = 0;
        for (ArrayList<Point> l : this.lineSegmentsList) {
        	Point[] pointArr = new Point[l.size()];
            pointArr = l.toArray(pointArr);
            Point[] copied = pointArr.clone();
        	LineSegment ls = new LineSegment(copied[0], copied[1]);
            lineSegments[k++] = ls;
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
        
        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);       
        StdOut.printf("%d\n", collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//        StdOut.println("here we go");
//        StdOut.printf("%d,%d - %d,%d\n", segment.p.x, segment.p.y, segment.q.x, segment.q.y);
            segment.draw();
        }
        StdDraw.show();       
    }
}
