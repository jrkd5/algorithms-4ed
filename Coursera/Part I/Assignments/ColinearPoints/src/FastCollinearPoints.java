import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {

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
    public FastCollinearPoints(Point[] points)
    {
        if (points == null || checkNull(points))
            throw new IllegalArgumentException("Argument contains null values.");

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        if (!checkUnique(sortedPoints))
            throw new IllegalArgumentException("Must not contain duplicate points");

        for (int i = 0; i < points.length; i++) {
            Point checkPoint =  points[i];
            Arrays.sort(sortedPoints, checkPoint.slopeOrder());
            int k = 1; // To avoid comparing a point to itself, slope -inf for degenerate point
            int m = 2;
            double prevSlope = checkPoint.slopeTo(sortedPoints[k]);
            while (m < sortedPoints.length) {
                double currSlope = checkPoint.slopeTo(sortedPoints[m]);
                if (currSlope != prevSlope) {
                    if (m - k >= 3 && uniqueSegment(points, checkPoint, prevSlope, i)) { // Check for duplicates
                        addLineSegment(sortedPoints, k, m, checkPoint);
                    }
                    k = m;
                    prevSlope = currSlope;
                }
                m++;
            }
            if (m - k >= 3 && uniqueSegment(points, checkPoint, prevSlope, i)) { // Check for duplicates
                addLineSegment(sortedPoints, k, m, checkPoint);
            }
        }
    }

    private void addLineSegment(Point[] points, int from, int to, Point refPoint)
    {
        Arrays.sort(points, from, to);
        Point minPoint = points[from];
        Point maxPoint = points[to-1];
        if (refPoint.compareTo(minPoint) < 0)
            minPoint = refPoint;
        if (refPoint.compareTo(maxPoint) > 0)
            maxPoint = refPoint;
        LineSegment ls = new LineSegment(minPoint, maxPoint);
        segments.add(ls);
        numSegments++;
    }

    private boolean uniqueSegment(Point[] points, Point checkPoint, double prevSlope, int index)
    {
        for (int i = 0; i < index; i++) {
            if (checkPoint.slopeTo(points[i]) == prevSlope)
                return false;
        }
        return true;
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
        Point[] points = new Point[4];
        points[0] = new Point(7300, 10050);
        points[1] = new Point(7300, 10450);
        points[2] = new Point(7300, 25700);
        points[3] = new Point(7300, 31650);
        FastCollinearPoints br = new FastCollinearPoints(points);
        br.printSegments();
    }
}