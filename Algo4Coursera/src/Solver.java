import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private final MinPQ<SearchNode> prioQ1 = new MinPQ<>(comp());
  private final MinPQ<SearchNode> prioQ2 = new MinPQ<>(comp());

  private static Comparator<SearchNode> comp() {
    return (n1, n2) -> (n1.moves - n2.moves + n1.b.manhattan() - n2.b.manhattan());
  }

  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    SearchNode root1 = new SearchNode(initial, 0);
    Board twin = initial.twin();
    SearchNode root2 = new SearchNode(twin, 0);
    root1.predecessor = null;
    root2.predecessor = null;
    prioQ1.insert(root1);
    prioQ2.insert(root2);

  }
  private class SearchNode {
    final Board b;
    SearchNode predecessor;
    final int moves;

    public SearchNode(Board b, int moves) {
      this.b = b;
      this.moves = moves;
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
        MinPQ<SearchNode> tmpPQ = i == 0 ? prioQ2 : prioQ1;
        Queue<Board> tmpQ = i == 0 ? d1 : d0;
        SearchNode minNode = tmpPQ.delMin();
        Iterable<Board> neighbors;
        Board currentB = minNode.b;
        tmpQ.enqueue(currentB);
        if (currentB.isGoal()) {
          if (i == 0) {
            return null;
          }
          return constructSolution(minNode);
        } else {
          neighbors = currentB.neighbors();
          for (Board board : neighbors) {
            if (minNode.predecessor == null || !board.equals(minNode.predecessor.b)) {
              SearchNode tmpNode = new SearchNode(board, minNode.moves + 1);
              tmpNode.predecessor = minNode;
              tmpPQ.insert(tmpNode);
            }
          }
        }

      }
    }

  }

  private Iterable<Board> constructSolution(SearchNode minNode) {
    // iterate linked list and construct a queue
    Stack<Board> res = new Stack<>();

    SearchNode node = minNode;
    while (node != null) {
      res.push(node.b);
      node = node.predecessor;
    }

    return res;
  }
}
