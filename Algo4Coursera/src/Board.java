import edu.princeton.cs.algs4.Queue;

public class Board {
  private final int[][] board;
  private final int mValue;

  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new IllegalArgumentException("ERROR");
    }
    board = deepCopy(blocks);
    int res = 0;
    int d = dimension();
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < d; j++) {
        if (board[i][j] != 0) {
          int pi = (board[i][j] - 1) / d;
          int pj = (board[i][j] - 1) % d;
          res += Math.abs(i - pi) + Math.abs(j - pj);
        }
      }
    }
    mValue = res;
  }

  public int dimension() {
    return board.length;
  }

  public int hamming() {
    int res = 0;
    int d = dimension();
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < d; j++) {
        if (board[i][j] != 0 && board[i][j] != i * d + j + 1) {
          res += 1;
        }
      }
    }

    return res;
  }

  public int manhattan() {
    return mValue;
  }

  public boolean isGoal() {
    int d = dimension();
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < d; j++) {
        if (board[i][j] != 0 && board[i][j] != i * d + j + 1) {
          return false;
        }
      }
    }
    return true;
  }

  public Board twin() {
    int d = dimension();
    int[][] clone = deepCopy(board);
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < d; j++) {
        if (clone[i][j] != 0) {
          int nj = (j + 1) % d;
          int ni = ((j + 1) / d + i) % d;
          if (clone[ni][nj] != 0) {
            int tmp = clone[i][j];
            clone[i][j] = clone[ni][nj];
            clone[ni][nj] = tmp;
            return new Board(clone);
          }
        }
      }
    }
    return null;
  }

  private int[][] deepCopy(int[][] original) {
    final int[][] result = new int[original.length][original.length];
    for (int i = 0; i < original.length; i++) {
      System.arraycopy(original[i], 0, result[i], 0, original[i].length);
    }
    return result;
  }
  public boolean equals(Object y) {
    if (y != null && y.getClass() == this.getClass()) {
      Board by = (Board) y;

      int d = dimension();
      if (d == by.dimension()) {
        for (int i = 0; i < d; i++) {
          for (int j = 0; j < d; j++) {
            if (board[i][j] != by.board[i][j]) {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }

  public Iterable<Board> neighbors() {
    Queue<Board> deque = new Queue<>();
    int d = dimension();
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < d; j++) {
        if (board[i][j] == 0) {
          int[][] clone;
          // up
          if (i - 1 >= 0) {
            clone = deepCopy(board);
            clone[i][j] = clone[i - 1][j];
            clone[i - 1][j] = 0;
            deque.enqueue(new Board(clone));
          }
          // down
          if (i + 1 < d) {
            clone = deepCopy(board);
            clone[i][j] = clone[i + 1][j];
            clone[i + 1][j] = 0;
            deque.enqueue(new Board(clone));
          }
          // left
          if (j - 1 >= 0) {
            clone = deepCopy(board);
            clone[i][j] = clone[i][j - 1];
            clone[i][j - 1] = 0;
            deque.enqueue(new Board(clone));
          }

          // right
          if (j + 1 < d) {
            clone = deepCopy(board);
            clone[i][j] = clone[i][j + 1];
            clone[i][j + 1] = 0;
            deque.enqueue(new Board(clone));
          }
        }
      }
    }
    return deque;
  }

  public String toString() {
    int n = dimension();
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
}
