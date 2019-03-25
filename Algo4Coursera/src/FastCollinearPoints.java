import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
  private final Point[] ps;
  private final LineSegment[] segments;

  public FastCollinearPoints(Point[] points) {


    if (points == null) {
      throw new IllegalArgumentException();
    }
    
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException();
      }
    }
    if (duplicates(points)) {
      throw new IllegalArgumentException();
    }
    this.ps = Arrays.copyOf(points, points.length);
    // easier when creating line segment after sorting
    Arrays.sort(ps);
    segments = init();
  }

  private boolean duplicates(final Point[] points) {
    boolean duplicates = false;
    for (int j = 0; j < points.length; j++)
      for (int k = j + 1; k < points.length; k++)
        if (k != j && points[k].equals(points[j]))
          duplicates = true;
    return duplicates;
  }
  public int numberOfSegments() {
    if (segments != null) {
      return segments.length;
    }
    return 0;
  }

  public LineSegment[] segments() {
    return segments;
  }

  private LineSegment[] init() {
    if (ps.length < 4) {
      return new LineSegment[0];
    }
    List<LineSegment> ls = new ArrayList<>();
    List<Point> checkedPoints = new ArrayList<>();
    //each point is origin point
    for (int i = 0; i < ps.length; i++) {
      
      if (checkedPoints.contains(ps[i])) {
        continue;
      }
      
      Point origin = ps[i];
      Point[] shallowPs = ps.clone();
      Arrays.sort(shallowPs, origin.slopeOrder());

      // check if any 3 adjacent points or more have the same slope to p
      int avance = 3;
      for (int j = i + 1; j < shallowPs.length - 2;) {
        List<Point> memo = new ArrayList<>();
        if (origin.slopeTo(shallowPs[j]) == origin.slopeTo(shallowPs[j + 1])
            && origin.slopeTo(shallowPs[j + 1]) == origin.slopeTo(shallowPs[j + 2])) {
          // find 3 collinear points
          memo.add(origin);
          memo.add(shallowPs[j]);
          memo.add(shallowPs[j + 1]);
          memo.add(shallowPs[j + 2]);
          // find more in the case it has more than 4 collinear points
          int k = j;
          j = j + avance;
          while (j < shallowPs.length - 2) {
            if (origin.slopeTo(shallowPs[k]) != origin.slopeTo(shallowPs[j])) {
              break;
            } else {
              memo.add(shallowPs[j]);
              j++;
            }
          }
          avance = 3;
          Collections.sort(memo);
          LineSegment ls0 = new LineSegment(memo.get(0), memo.get(memo.size() - 1));
          ls.add(ls0);
          memo.stream().forEach(x -> {
            if(!checkedPoints.contains(x)) {
              checkedPoints.add(x);  
            }
          });
        } else {
          j++;
        }

      }
    }
    return ls.toArray(new LineSegment[ls.size()]);
  }
}
