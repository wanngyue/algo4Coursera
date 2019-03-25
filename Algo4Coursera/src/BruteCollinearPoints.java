import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
  private final Point[] ps;
  private final LineSegment[] segments;

  public BruteCollinearPoints(Point[] points) {

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
    List<LineSegment> ls = new ArrayList<>();
    if (ps.length < 4) {
    } else {
      for (int i = 0; i < ps.length; i++) {
        for (int j = i + 1; j < ps.length; j++) {
          for (int j2 = j + 1; j2 < ps.length; j2++) {
            for (int k = j2 + 1; k < ps.length; k++) {
              // find 4 differents points to check if they are collinear
              if (!ps[i].equals(ps[j]) && !ps[j].equals(ps[j2]) && !ps[j2].equals(ps[k])) {
                double slope0 = ps[i].slopeTo(ps[j]);
                double slope1 = ps[j].slopeTo(ps[j2]);
                double slope2 = ps[j2].slopeTo(ps[k]);
                if (slope0 == slope1 && slope1 == slope2) {
                  LineSegment ls0 = new LineSegment(ps[i], ps[k]);
                  ls.add(ls0);
                }
              }
            }
          }
        }
      }
    }
    return ls.toArray(new LineSegment[ls.size()]);
  }

}