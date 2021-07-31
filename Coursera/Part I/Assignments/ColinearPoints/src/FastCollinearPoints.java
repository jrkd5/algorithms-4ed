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
            int l = 2;
            double prevSlope = checkPoint.slopeTo(sortedPoints[k]);
            while (l < sortedPoints.length) {
                double currSlope = checkPoint.slopeTo(sortedPoints[l]);
                if (currSlope != prevSlope) {
                    if (l - k >= 3 && uniqueSegment(points, checkPoint, prevSlope, i)) { // Check for duplicates
                        Arrays.sort(sortedPoints, k, l);
                        Point minPoint = sortedPoints[k];
                        Point maxPoint = sortedPoints[l-1];
                        if (checkPoint.compareTo(minPoint) < 0)
                            minPoint = checkPoint;
                        if (checkPoint.compareTo(maxPoint) > 0)
                            maxPoint = checkPoint;
                        LineSegment ls = new LineSegment(minPoint, maxPoint);
                        segments.add(ls);
                        numSegments++;
                    }
                    k = l;
                    prevSlope = currSlope;
                }
                l++;
            }
        }
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

    private void printArray(Point[] a){
        StdOut.print("[");
        for (Point p : a)
            StdOut.print(p + ", ");
        StdOut.println("]");
    }

    public static void main(String[] args) {

//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();

        Point[] points = new Point[6];
        points[0] = new Point(19000, 10000);
        points[1] = new Point(18000, 10000);
        points[2] = new Point(32000, 10000);
        points[3] = new Point(21000, 10000);
        points[4] = new Point(1234, 5678);
        points[5] = new Point(14000, 10000);
        FastCollinearPoints br = new FastCollinearPoints(points);
        br.printSegments();
    }
}