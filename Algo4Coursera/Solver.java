import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {

  private final MinPQ<SearchNode> q1 = new MinPQ<>(comp());
  private final MinPQ<SearchNode> q2 = new MinPQ<>(comp());

  private static Comparator<SearchNode> comp() {
    return (n1, n2) -> (n1.priority - n2.priority);
  }

  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    SearchNode root1 = new SearchNode(initial, initial.manhattan() + 0);
    Board twin = initial.twin();
    SearchNode root2 = new SearchNode(twin, twin.manhattan() + 0);
    root1.p = null;
    root2.p = null;
    q1.insert(root1);
    q2.insert(root2);

  }
  private class SearchNode {
    final Board b;
    SearchNode p;
    final int priority;

    public SearchNode(Board b, int m) {
      this.b = b;
      this.priority = m;
    }

    public String toString() {
      return b.toString();

    }
  }


  public boolean isSolvable() {
    return solution() != null;
  }

  public int moves() {
    int res = -1;
    Iterable<Board> s = solution();
    if (s == null) {
      return res;
    }
    Iterator<Board> iterator = s.iterator();
    while (iterator.hasNext()) {
      iterator.next();
      res++;
    }
    return res;
  } // min number of moves to solve initial board; -1 if unsolvable

  // sequence of boards without goal board
  public Iterable<Board> solution() {

    Queue<Board> d0 = new Queue<>();
    Queue<Board> d1 = new Queue<>();
    while (true) {
      for (int i = 0; i < 2; i++) {
        MinPQ<SearchNode> tmpPQ = i == 0 ? q2 : q1;
        Queue<Board> tmpQ = i == 0 ? d1 : d0;
        SearchNode minNode = tmpPQ.delMin();
        Board currentB = minNode.b;
        tmpQ.enqueue(currentB);
        if (currentB.isGoal()) {
          if (i == 0) {
            return null;
          }
          return d0;
        } else {
          Iterable<Board> neighbors = currentB.neighbors();
          for (Board board : neighbors) {
            if (minNode.p == null || !board.equals(minNode.p.b)) {
              SearchNode tmpNode = new SearchNode(board, board.manhattan() + minNode.priority);
              tmpNode.p = minNode;
              tmpPQ.insert(tmpNode);
            }
          }
        }

      }
    }

  }
}
