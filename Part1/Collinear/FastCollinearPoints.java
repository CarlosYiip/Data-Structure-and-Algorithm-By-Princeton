import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int n = 0;
    private ArrayList<Point[]> segmentsArrayList;
    
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);        
        if (points == null) throw new NullPointerException();
        if (points[0] == null) throw new NullPointerException();
        for (int i = 1; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            int x = points[i].compareTo(points[i-1]);
            if (x == 0) throw new IllegalArgumentException();
        }
        
        segmentsArrayList = new ArrayList<Point[]>();
        
        for (int i = 0; i < points.length; i++) {
            if (points.length - i < 4) break;
            
            Point[] temp = new Point[points.length - i];
            System.arraycopy(points, i, temp, 0, points.length - i);
            Arrays.sort(temp, points[i].slopeOrder());
            
            int numberOfCollinearPoints = 2;            
            for (int j = 1; j < temp.length - 1; j++) {
                if (temp[0].slopeTo(temp[j]) == temp[0].slopeTo(temp[j+1])) {
                    numberOfCollinearPoints++;
                    if (j == temp.length - 2 && numberOfCollinearPoints >= 4) {
                        Point[] currentSegment = {temp[0], temp[j+1]};
                        if (isSub(segmentsArrayList, currentSegment)) {
                            break;
                        } else {
                            segmentsArrayList.add(currentSegment);
                            n++;
                        }                            
                    }
                } else {
                    if (numberOfCollinearPoints >= 4) {                       
                        Point[] currentSegment = {temp[0], temp[j]};
                        if (isSub(segmentsArrayList, currentSegment)) {
                            break;
                        } else {
                            segmentsArrayList.add(currentSegment);
                            n++;
                        }
                    }
                    numberOfCollinearPoints = 2;
                }
            }
        }        
    }
    
    public int numberOfSegments() {
        return n;
    }
    
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[this.segmentsArrayList.size()];
        
        for (int i = 0; i < segmentsArrayList.size(); i++) {
            Point[] readSegment = segmentsArrayList.get(i);
            Point start = readSegment[0];
            Point end = readSegment[1];
            LineSegment writeSegment = new LineSegment(start, end);
            segmentsArray[i] = writeSegment;
        }

        return segmentsArray;
    }
    
    private boolean isSub(ArrayList<Point[]> foundSegments, Point[] currentSegment) {
        for (int i = 0; i < foundSegments.size(); i++) {
            Point[] foundSegment = segmentsArrayList.get(i);
            Point foundSegmentStart = foundSegment[0];
            Point foundSegmentEnd = foundSegment[1];
                                
            double slope1 = currentSegment[0].slopeTo(foundSegmentStart);
            double slope2 = currentSegment[0].slopeTo(foundSegmentEnd);
                                
            if (slope1 == slope2 && currentSegment[1] == foundSegmentEnd) 
               return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }
        
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
}












