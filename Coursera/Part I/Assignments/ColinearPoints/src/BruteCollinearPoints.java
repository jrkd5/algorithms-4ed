import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {

    private final Bag<LineSegment> segments = new Bag<>();
    private int numSegments;

    /**
     * Check if all points in an array are non empty.
     */
    private boolean checkNull(Point[] points)
    {
        for (Point p : points) {
            if (p == null) return true;
        }
        return false;
    }

    /**
     * Check if a sorted array contains only unique points.
     */
    private boolean checkUnique(Point[] points)
    {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0)
                return false;
        }
        return true;
    }

    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points)
    {
        if (points == null || checkNull(points))
            throw new IllegalArgumentException("Argument contains null values.");

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        if (!checkUnique(sortedPoints))
            throw new IllegalArgumentException("Must not contain duplicate points");

        int n = sortedPoints.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    for (int l = k+1; l < n; l++) {
                        Point p = sortedPoints[i];
                        Point q = sortedPoints[j];
                        Point r = sortedPoints[k];
                        Point s = sortedPoints[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            segments.add(new LineSegment(p, s));
                            numSegments++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments()
    {
        return numSegments;
    }

    public LineSegment[] segments()
    {
        LineSegment[] found = new LineSegment[numberOfSegments()];
        Iterator<LineSegment> it = segments.iterator();
        int i = 0;
        while (it.hasNext())
            found[i++] = it.next();
        return found;
    }

    private void printSegments()
    {
        for (LineSegment s : segments()) {
            StdOut.println(s);
        }
    }

    public static void main(String[] args) {
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000, 3000);
        points[4] = new Point(20000, 21000);
        points[5] = new Point(3000, 4000);
        points[6] = new Point(14000, 15000);
        points[7] = new Point(6000, 7000);
        BruteCollinearPoints br = new BruteCollinearPoints(points);
        br.printSegments();
    }
}