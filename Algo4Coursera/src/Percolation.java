import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] sites;
  private WeightedQuickUnionUF uf;
  private int openSites = 0;
  private int num = 0;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    num = n;
    uf = new WeightedQuickUnionUF(n * n + 2);
    for (int i = 1; i <= n; i++) {
      uf.union(0, i);
      uf.union(n * n + 1, i + n * (n - 1));
    }

    sites = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sites[i][j] = false;
      }
    }
  } // create n-by-n grid, with all sites blocked

  public void open(int row, int col) {
    if (row <= 0 || col <= 0 || row > sites.length || col > sites.length) {
      throw new IllegalArgumentException();
    }
    if (sites[row - 1][col - 1] != true) {
      sites[row - 1][col - 1] = true;
      if (col - 2 >= 0 && sites[row - 1][col - 2] == true) {
        uf.union(col + (row - 1) * num, col + (row - 1) * num - 1);
      }
      if (col < num && sites[row - 1][col] == true) {
        uf.union(col + (row - 1) * num, col + (row - 1) * num + 1);
      }
      if (row - 2 >= 0 && sites[row - 2][col - 1] == true) {
        uf.union(col + (row - 1) * num, col + (row - 2) * num);
      }
      if (row < num && sites[row][col - 1] == true) {
        uf.union(col + (row - 1) * num, col + (row) * num);
      }
      openSites++;
    }

  } // open site (row, col) if it is not open already

  public boolean isOpen(int row, int col) {
    if (row <= 0 || col <= 0 || row > sites.length || col > sites.length) {
      throw new IllegalArgumentException();
    }
    return sites[row - 1][col - 1] == true;
  } // is site (row, col) open?

  public boolean isFull(int row, int col) {
    if (row <= 0 || col <= 0 || row > sites.length || col > sites.length) {
      throw new IllegalArgumentException();
    }
    return isOpen(row, col) && uf.connected(0, col + (row - 1) * num);
  } // is site (row, col) full?

  public int numberOfOpenSites() {
    return openSites;
  } // number of open sites

  public boolean percolates() {
    return uf.connected(0, num * num + 1);
  } // does the system percolate?

  @Deprecated
  public static void main(String[] args) {
  }// test client (optional)
}
