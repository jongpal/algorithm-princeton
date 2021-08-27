//package io.jongpal;

import java.util.Comparator;
//import java.lang.*;
import edu.princeton.cs.algs4.StdDraw;


public class Point implements Comparable<Point> {
//  public final int x, y;
  private final int x, y;
  
//  
  public Point (int x, int y) {
     this.x = x;
     this.y = y;
 }
//  private int[] getPoint() {
//      return new int[]{ this.x, this.y };
//  }
      /**
   * Draws this point to standard draw.
   */
  public void draw() {
      /* DO NOT MODIFY */
      StdDraw.point(x, y);
  }
  public String toString() {
      /* DO NOT MODIFY */
      return "(" + x + ", " + y + ")";
  }
  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
      /* DO NOT MODIFY */
      StdDraw.line(this.x, this.y, that.x, that.y);
  }   

  /**
   * return -1 if this point is smaller, 0 if same, 1 otherwise
   * @param that point to compare
   */
  public int compareTo(Point that) {

      if (that == null) throw new NullPointerException();
      if (this == that) return 0;
      if ( y < that.y ) return -1;
      if ( y > that.y ) return 1;
      if ( x < that.x ) return -1;
      if ( x > that.x ) return 1;
      return 0;
  }
  
  /**
   * 
   * @param that point to calculate a slope with this point
   * @return slope between those two points
   */
  public double slopeTo(Point that) {

      if (that == null) throw new NullPointerException();
      
      if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
      if (that.x - this.x == 0) return Double.POSITIVE_INFINITY;
      if (that.y - this.y == 0) return +0.0;
      
      double slope = ((double)(that.y - this.y))/((double)(that.x - this.x));
      return slope;
  }
  /**
   * Comparator BySlope : comparing slope between two points that is made with this instance's point 
   * @return
   */
  public Comparator<Point> slopeOrder() {
      return new BySlope();
  }
  private class BySlope implements Comparator<Point> {
//      public int[] getPoint() {
//          return new int[]{ x, y };
//      }
      public int compare(Point a, Point b) {
          double slopeA, slopeB;          
          slopeA = slopeTo(a);
          slopeB = slopeTo(b);
          if (slopeA > slopeB) return 1;
          if (slopeA < slopeB) return -1;
          return 0;
      }
  }
}
