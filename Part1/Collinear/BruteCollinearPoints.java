import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private int n = 0;
    private ArrayList<LineSegment> segmentsArrayList;
    
    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        if (points == null) throw new NullPointerException();
        if (points[0] == null) throw new NullPointerException();
        for (int i = 1; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException();
            int x = points[i].compareTo(points[i-1]);
            if (x == 0) throw new IllegalArgumentException();
        }
                
        this.segmentsArrayList = new ArrayList<LineSegment>();
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);
                        if (slopePQ == slopePR && slopePR == slopePS) {
                            LineSegment line = new LineSegment(p, s);
                            segmentsArrayList.add(line);   
                            n++;
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return this.n;
    }
    
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[this.segmentsArrayList.size()];
        segmentsArray = segmentsArrayList.toArray(segmentsArray);
        return segmentsArray;
    }
}