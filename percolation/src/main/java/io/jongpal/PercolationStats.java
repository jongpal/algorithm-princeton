package io.jongpal;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
  
  private double[] samples;
  private int trials_;
  // perform independent trials on an n-by-n grid
  /**
   * calculate {@code trials} times of percolation
   * 
   * @param n for n by n sites, should be bigger than 0
   * @param trials how many times you want to perform percolation, should be bigger than 0
   * 
   */
  public PercolationStats(int n, int trials) {
      
      if(n <= 0 || trials <= 0) throw new IllegalArgumentException("input invalid");
      Percolation perc;
      samples = new double[trials];
      trials_ = trials;
      for(int i = 0; i < trials; i++) {
          perc = new Percolation(n);
          while(true) {
              if(perc.percolates()) {
                  break;
              }
      
              // generate random among blocked sites
              int row, col, randNum;
              // open the site
              while(true) {
                  randNum = StdRandom.uniform(n*n);
                  row = randNum / n + 1;
                  col = randNum % n + 1;
                  if(!perc.isOpen(row, col)) break;
              }
              perc.open(row, col);
          }
          
          samples[i] = ((double)(perc.numberOfOpenSites())) / ((double)(n * n));
      }
       
  }

  /**
   * calculate mean of sampled percolation
   * @return mean of percolation
   */
  public double mean(){
//      double sum = 0;
//      for(int i = 0 ; i < trials_; i++) {
//          sum += samples[i];
//      }
//      return sum / (double)trials_;
	  return StdStats.mean(samples);
  }
  /**
   * calculate standard deviation of sampled percolation
   * @return standard deviation of percolation
   */
  public double stddev() {
//      double mean = mean();
//      double sum = 0;
//      for(int i = 0 ; i < trials_; i++) {
//          sum += Math.pow(samples[i] - mean, 2);
//      }
//      return Math.sqrt(sum / (double)(trials_ - 1));
	  return StdStats.stddev(samples);
  }
 
  /**
   * @return low endpoint of 95% confidence level
   */
  public double confidenceLo() {
      double mean = mean();
      double stddv = stddev();
      return mean - stddv*1.96 / Math.sqrt((double)trials_);
  }
  /**
   * @return high endpoint of 95% confidence level
   */
  public double confidenceHi() {
      double mean = mean();
      double stddv = stddev();
      return mean + stddv*1.96 / Math.sqrt((double)trials_);    
  }

 // test client
  public static void main(String[] args) {
      int n = Integer.parseInt(args[0]);
      int trials = Integer.parseInt(args[1]);
      
      PercolationStats stats = new PercolationStats(n, trials);
      System.out.print("mean                    = ");
      System.out.println(stats.mean());
//      System.out.printf("should be %f\n", StdStats.mean(samples));
      System.out.print("stddev                  = ");
      System.out.println(stats.stddev());
      System.out.print("95% confidence interval = [");
      System.out.print(stats.confidenceLo());
      System.out.print(",");
      System.out.print(stats.confidenceHi());
      System.out.println("]");
  }

}
