// package io.jongpal;

//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[] sites;
    private int num;
    private final WeightedQuickUnionUF connectionTree;
    private int numOfOpenSites;
    private String sitesString;
    /**
     *  Initialize n by n sites, for Union-Find implementation
     *  - use extra 2 spaces for the common root for the first row, and for the last row.
     *  - root of first row is 0, and root of last row is n*n + 1.
     *  - sites are referenced between 1, n
     *  - connectionTree data structure is used to check the sites' connection (full sites)
     * 
     * @param n should be positive value
     * @throws IllegalArgumentException if {@code n} is not positive
     */
    public Percolation(int n) {
      if (n <= 0) throw new IllegalArgumentException();
      num = n;
      sites = new int[n*n];
      numOfOpenSites = 0;
      
      connectionTree = new WeightedQuickUnionUF(n*n + 2); // + 2 for start(0) / end binding(n*n+1)
      
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
        	// n == 1 : special case 
          if (n == 1) {
        	  connectionTree.union(1, 0);
        	  connectionTree.union(2, 0);
          }
          else {
	          if (i == 0) {
	            connectionTree.union(j + 1, 0); // for first row : bind with start point
	          }
	          else if (i == n - 1) {
	            connectionTree.union(i * num + j + 1, n * n + 1); // for last row : bind with end point n*n + 1
	          }
          }
          sites[i * num + j] = 0; // set sites to 0 : blocked state 
        }
      }
    }
    /**
     * 
     * @return the number of sets in this {@code connectionTree}
     */
    public int countUnionset() {
    	return connectionTree.count();
    }
    /**
     * string representation of this, cached
     * 
     * @return string representation of this sites array
     * 
     */
    public String toString() {
    	if (this.sitesString != null) return this.sitesString;
    	StringBuffer str = new StringBuffer();
    	str.append(this.num);
    	str.append('\n');
    	for (int i = 0; i < this.num; i++) {
    		for (int j = 0; j < this.num; j++) {
    			str.append(sites[i * this.num + j]);
    			if (j != this.num - 1) str.append(' ');
    		}
    		str.append('\n');
    	}
    	String toStr = str.toString();
    	return toStr;
    }
    
        
    /**
     * open one of site given (row, col)
     * this function will also check if neighbor site is also open
     * If that's the case, then make union.
     * 
     * @param row, col should be within range [1, n]
     */
    public void open(int row, int col){
      
      if(row < 1 || col < 1 || row > num || col > num) throw new IllegalArgumentException("out of bounds");
      
      
      if(isOpen(row, col)) return;

      sites[(row - 1) * num + col - 1] = 1; // open : 1
      numOfOpenSites += 1;
      
      if (connectionTree.find((row - 1) * num + col) == connectionTree.find(0)) connectionTree.union((row - 1) * num + col, 0);

      if ((row - 1 >= 1) && isOpen(row - 1, col)) {
         connectionTree.union((row - 1) * num + col, (row - 2) * num + col);
      };    
      if ((row + 1 <= num) && isOpen(row + 1, col)) {
         connectionTree.union((row - 1) * num + col, row * num + col);
      }; 
      if ((col - 1 >= 1) && isOpen(row, col - 1)) {
         connectionTree.union((row - 1) * num + col, (row - 1) * num + col - 1);
      }; 
      if ((col + 1 <= num) && isOpen(row, col + 1)) {
         connectionTree.union((row - 1) * num + col, (row - 1) * num + col + 1);
      }; 
    }
    /**
     * check if given site is open
     * 
     *  @param row, col should be between 1 and n
     *  @throws IllegalArgumentException if given row, col are out of range
     *  @return if the site is open, return true, if the site is blocked, return false.
     */
    public boolean isOpen(int row, int col) {
		if (row < 1 || col < 1 || row > num || col > num) throw new IllegalArgumentException("wrong args");
	
		if (sites[(row - 1) * num + col - 1] == 1) return true;
		return false;
    }
    /**
     * check if given site is full 
     * The site is full when the site is connected to the top of the row.
     * 
     *  @param row, col should be between 1 and n
     *  @throws IllegalArgumentException if given {@code row}, {@code col} are out of range
     *  @return true if the site is full
     */
        
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > num || col > num) throw new IllegalArgumentException("wrong args");
        if (isOpen(row, col) && (connectionTree.find((row - 1) * num + col) == connectionTree.find(0))) return true;
        return false;
    }
        
    public int numberOfOpenSites() {
      return numOfOpenSites;
    }
        
    /**
     * check whether this sites percolate
     * 
     * @return true if one of sites in last row's root is same with the root of first rows
     * else : return false
     */
    public boolean percolates() { 
    	if (num == 1 && !isOpen(1, 1)) return false;
		if (connectionTree.find(num * num + 1) == connectionTree.find(0)) return true;

        return false;
    }
//    private void print() {
//        System.out.println();
//        for(int i = 0 ; i < num; i++) {
//            for(int j = 0 ; j < num; j++) {
//                System.out.print(sites[i*num + j]);   
//            }
//            System.out.println();
//        }
//          
//    }
        
    public static void main(String[] args) {
        
        // initialize
    	
//        int n = Integer.parseInt(args[0]);
//        Percolation perc = new Percolation(n);
//        
//        while(true) {
//            if(perc.percolates()) {
//                System.out.println("percolates");
//                break;
//            }
//            
//            // generate random among blocked sites
//            int randNum = StdRandom.uniform(n*n + 1); // 1 to n*n
//            int row = randNum / (n + 1) + 1;
//            int col = randNum % n;
//            if(col == 0) col = n;
//            // open the site
//            //System.out.println("row");
//            //System.out.println(row-1);
//            //System.out.println("col");
//            //System.out.println(col-1);
//            while(!perc.isBlocked(row-1, col-1)) {
//               randNum = StdRandom.uniform(n*n + 1);
//               row = randNum / (n + 1) + 1;
//               col = randNum % n;
//               if(col == 0) col = n;
//            }
//            //System.out.println("hey");
//            perc.open(row, col);
//            perc.print();
//            
//        }
    }
}
