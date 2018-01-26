import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;
  private final int trials;
  private double[] xs;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.trials = trials;
    xs = new double[trials];
    for (int i = 0; i < this.trials; i++) {
      Percolation p = new Percolation(n);
      for (;;) {
        int row;
        int col;
        do {
          row = StdRandom.uniform(1, n + 1);
          col = StdRandom.uniform(1, n + 1);
        } while (p.isOpen(row, col));
        p.open(row, col);
        if (p.percolates()) {
          xs[i] = p.numberOfOpenSites() / (1.0 * n * n);
          break;
        }

      }
    }

  } // perform trials independent experiments on an n-by-n grid

  public double mean() {
    return StdStats.mean(xs);
  } // sample mean of percolation threshold

  public double stddev() {
    return StdStats.stddev(xs);
  } // sample standard deviation of percolation threshold

  public double confidenceLo() {
    return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  } // low endpoint of 95% confidence interval

  public double confidenceHi() {
    return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  } // high endpoint of 95% confidence interval

  public static void main(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException();
    }
    PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    StdOut.println(p.mean());
    StdOut.println(p.stddev());
    StdOut.println(p.confidenceLo());
    StdOut.println(p.confidenceHi());
  } // test client (described below)
}