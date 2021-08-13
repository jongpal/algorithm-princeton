package io.jongpal;

import static org.junit.jupiter.api.Assertions.*;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class PercolationTest {
	
	@Test
	void testPercolationOutOfRange() {
		assertThrows(IllegalArgumentException.class, () -> 
			new Percolation(0)
		);
	}

	/**
	 * Whitebox, Integrate test for open(), full(), countUnionSet(), numberOfOpenSites()
	 * 
	 * open() first opens its site, and then look for its neighbor and if neighbors are open, it makes union.
	 * 
	 * Teting Strategy
	 * 
	 * for normal 5 * 5 sites
	 * 1) for possible 4 neighbors case (neighbors all open)
	 * - open site (1, 4), (2, 3), (2, 5), (3, 4) : these are not connected so at first, 
	 *   countUnionSet() will return 25 - 5 * 2 + 2 = 17
	 *   after we open (2, 4) this will trigger all neighbors to be connected to each other.
	 *   In this case, we are connected to first row (1, 4) so these sites should all full 
	 *   which will make countUnionSet() 17 - 4 = 13.
	 *   
	 * 2) for possible 3 neighbors case (neighbors all open, one is blocked for possible neighbor 4)
	 *  2-1)
	 * - same strategy as above, but to check boundary value(white box)
	 * - open site (1, 5), (2, 4), (3, 5). => not connected , so 17 for countUnionSet()
	 * - after opening (2, 5), countUnionSet() should return 17 - 3 = 14
	 *  2-2)
	 * then, check if it successfully detects blocked sites and not make an union with this
	 * - open site (1, 5), (2, 4) => 17 for countUnionSet()
	 * - after opening (2, 5), even though (3, 5) is its neighbor, this site is blocked so could not
	 *   be a full site. 
	 *   countUnionSet() should return only 17 - 2 = 15
	 *   
	 * 3) for possible 2 neighbors case (neighbors all open);
	 * - this should be a 4 each corner
	 *   just test for left-up (1, 1)
	 * - open (1, 2), (2, 1) => 17 for countUnionSet()
	 * - open (1, 1), => as (1, 1) and (1, 2) is already in same set, countUnionSet() should be 16
	 *   also, (2, 1) should be a full site
	 * 4) corner case : when n == 1
	 * 
	 * - opening (1, 1) itself will make this a full site. No change for countUnionSet()
	 * 
	 */
	// testing 1) case
	@Test
	public void testOFCIntegrationFirstCase() {
		int n = 5;
		Percolation perc = new Percolation(n);
		perc.open(1, 4);
		perc.open(2, 3);
		perc.open(2, 5);
		perc.open(3, 4);
		// - 2 * 5 + 2for first row belongs 0, and last row belongs 5 * 5 + 1
		int numberOfTotalSet = n * n - 2 * n + 2;
		assertTrue(perc.numberOfOpenSites() == 4);
		assertTrue(perc.countUnionset() == numberOfTotalSet);
		perc.open(2, 4);
		assertTrue(perc.countUnionset() == (numberOfTotalSet - 4));
		assertTrue(perc.isFull(1, 4));
		assertTrue(perc.isFull(2, 3));
		assertTrue(perc.isFull(2, 5));
		assertTrue(perc.isFull(3, 4));
	}
	// testing 2) case
	@Test
	public void testOFCIntegrationSecondCase() {
		int n = 5;
		// 2-1)
		Percolation perc = new Percolation(n);
		perc.open(1, 5);
		perc.open(2, 4);
		perc.open(3, 5);
		
		int numberOfTotalSet = n * n - 2 * n + 2;
		assertTrue(perc.numberOfOpenSites() == 3);
		assertTrue(perc.countUnionset() == numberOfTotalSet);
		perc.open(2, 5);
		assertTrue(perc.countUnionset() == (numberOfTotalSet - 3));
		assertTrue(perc.isFull(1, 5));
		assertTrue(perc.isFull(2, 4));
		assertTrue(perc.isFull(3, 5));
		
		// 2-2)
		perc = new Percolation(n);
		perc.open(1, 5);
		perc.open(2, 4);
		assertTrue(perc.numberOfOpenSites() == 2);
		perc.open(2, 5);
		assertTrue(perc.countUnionset() == (numberOfTotalSet - 2));
		assertTrue(perc.isFull(1, 5));
		assertTrue(perc.isFull(2, 4));
		assertFalse(perc.isFull(3, 5));
		
	}
	// testing 3) case
	@Test
	public void testOFCIntegrationThirdCase() {
		int n = 5;
		Percolation perc = new Percolation(n);
		perc.open(1, 2);
		perc.open(2, 1);
		int numberOfTotalSet = n * n - 2 * n + 2;
		assertTrue(perc.countUnionset() == numberOfTotalSet);
		
		perc.open(1, 1);
		assertTrue(perc.countUnionset() == (numberOfTotalSet - 1));
		assertTrue(perc.isFull(2, 1));
	}
	// testing 4) case
	@Test
	public void testOFCIntegrationFourthCase() {
		int n = 1;
		Percolation perc = new Percolation(n);
		assertFalse(perc.isFull(1, 1));
		perc.open(1, 1);
		assertTrue(perc.isFull(1, 1));
		int numberOfTotalSet = n * n - 2 * n + 2;
		assertTrue(perc.countUnionset() == numberOfTotalSet);
	}
	
	/**
	 * Unit Testing open()
	 * 
	 * for cases (1, 1), (n, n), (random, random)
	 * 
	 */
	@Test
	void testSitesInitialValue() {
		// 10 * 10
		String answer = sitesInitialValue(10);
		assertEquals("111", answer);
		// 888 * 888
		answer = sitesInitialValue(888);
		assertEquals("111",  answer);
		
	}
	String sitesInitialValue(int n) {
		String answer = "";
		Percolation perc = new Percolation(n);
		
		perc.open(1, 1);
		String percStr = perc.toString();
		String []parsedStrRow = percStr.split("\n");
		String []parsedStrRowCol = parsedStrRow[1].split(" ");
		int actual = Integer.parseInt(parsedStrRowCol[0]);
		int expected = 1;
//		System.out.printf("%d %d\n", actual, expected);
		if (expected == actual) answer += 1;
		else answer += 0;
		
		perc.open(n, n);
		percStr = perc.toString();
		parsedStrRow = percStr.split("\n");
		parsedStrRowCol = parsedStrRow[n].split(" ");
		actual = Integer.parseInt(parsedStrRowCol[n - 1]);
		expected = 1;		
		if (expected == actual) answer += 1;
		else answer += 0;
		
        // generate random among blocked sites
        int randNum = StdRandom.uniform(n * n + 1); // 1 to n*n
        int row = randNum / (n + 1) + 1;
        int col = randNum % n;
        perc.open(row, col);
		percStr = perc.toString();
		parsedStrRow = percStr.split("\n");
		parsedStrRowCol = parsedStrRow[row].split(" ");
		actual = Integer.parseInt(parsedStrRowCol[col - 1]);
		expected = 1;		
		if (expected == actual) answer += 1;
		else answer += 0;
		
		return answer;
	}
	/**
	 * Testing isOpen() 
	 * Strategy
	 * 1) open
	 * 2) check
	 */
	@Test
	void testIsOpen() {
		Percolation perc = new Percolation(10);
		perc.open(3, 4);
		assertTrue(perc.isOpen(3, 4));
	}
	
	/**
	 * Testing method percolates()
	 * 
	 * Strategy
	 * 
	 * 1) corner case : when size is just 1 => in Implementation, unions are made when surfing neighbor sites
	 *  				but in this case there is no neighbor
	 * 
	 * 2) normal case
	 *  - make path
	 *  - check percolates
	 */
	@Test
	public void testPercolatesOne() {
		Percolation perc = new Percolation(1);
		assertFalse(perc.percolates());
		perc.open(1, 1);
		assertTrue(perc.percolates());
	}
	@Test
	void testPercolates() {
		Percolation perc = new Percolation(5);
		perc.open(1, 1);
		perc.open(2, 1);
		perc.open(3, 1);
		perc.open(4, 1);
		assertFalse(perc.percolates());
		perc.open(5, 1);
		assertTrue(perc.percolates());
		perc = new Percolation(1);
		assertFalse(perc.percolates());
	}

//	@BeforeEach 
//	void initPercolation() {
//		perc = new Percolation
//	}
	


}

