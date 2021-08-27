//package io.jongpal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FastCollinearPointsTest {

	/*
	 * Testing FastCollinearPoints
	 * 
	 * Strategy
	 * 
	 * Partitioning
	 * - horizontal line
	 * - vertical line
	 * - single line
	 * - no line
	 * - case that has multiple parallel lines
	 * - in presence of duplicates
	 */
	
	
	// duplicate points test
	@Test
	public void testCollinearDuplicates() {
		Point[] pointSet = new Point[]{new Point(-1, -1), new Point(-2, 2), new Point(5, -3), new Point(7, 2)
				, new Point(3, 4), new Point(2, 2), new Point(-2, 2)};
		
		assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(pointSet));
	}
	
	// horizontal line test
	@Test
	public void testCollinearHorizontal() {
		Point[] pointSet = new Point[]{new Point(-1, -1), new Point(-2, 2), new Point(5, -3), new Point(7, 2)
				, new Point(3, 4), new Point(2, 2), new Point(-4, 2)};
		
		FastCollinearPoints fcp = new FastCollinearPoints(pointSet);
		LineSegment answer = new LineSegment(new Point(-4, 2), new Point(7, 2));
//		System.out.println(fcp.segments()[0].toString());
		assertTrue(fcp.segments()[0].toString().equals(answer.toString()));
			
	}
	
	// vertical line test
	@Test
	public void testCollinearVertical() {
		Point[] pointSet = new Point[]{new Point(-1, -1), new Point(-2, 2), new Point(5, -3), new Point(-2, 7)
				, new Point(3, 4), new Point(-2, -2), new Point(-2, 4)};
		
		FastCollinearPoints fcp = new FastCollinearPoints(pointSet);
		LineSegment answer = new LineSegment(new Point(-2, -2), new Point(-2, 7));
		assertTrue(fcp.segments()[0].toString().equals(answer.toString()));
			
	}

	// single line
	@Test
	public void testCollinearSingleLine() {
		Point[] pointSet = new Point[]{new Point(1, 2), new Point(3, 6), new Point(2, 4), new Point(2, 8)
				, new Point(4, 8), new Point(5, 20)};
		FastCollinearPoints fcp = new FastCollinearPoints(pointSet);
		LineSegment answer = new LineSegment(new Point(1, 2), new Point(4, 8));
		assertTrue(fcp.segments()[0].toString().equals(answer.toString()));
	}
	// no line
	@Test
	public void testCollinearNoLine() {
		Point[] pointSet = new Point[]{new Point(1, 2), new Point(3, 7), new Point(2, 5), new Point(2, 8)
				, new Point(4, 7), new Point(5, 20)};
		FastCollinearPoints fcp = new FastCollinearPoints(pointSet);
		assertTrue(fcp.segments().length == 0);
	}
	// multiple parallel lines
	@Test
	public void testCollinearMultipleParallelLines() {
		Point[] pointSet = new Point[]{new Point(1, 2), new Point(1, 3), new Point(2, 4), new Point(2, 5)
				, new Point(4, 8), new Point(5, 11), new Point(10, 21), new Point(7, 15), new Point(-1, -2)
				, new Point(-4, -7)};
		FastCollinearPoints fcp = new FastCollinearPoints(pointSet);
		LineSegment answer1= new LineSegment(new Point(-4, -7), new Point(10, 21));
		LineSegment answer2= new LineSegment(new Point(-1, -2), new Point(4, 8));
		for (LineSegment ls : fcp.segments()) {
			assertTrue(ls.toString().equals(answer1.toString()) || ls.toString().equals(answer2.toString()));
		}
	}
	
}
