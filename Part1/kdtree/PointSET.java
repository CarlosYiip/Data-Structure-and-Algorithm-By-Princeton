import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;


public class PointSET {
    private SET<Point2D> pointSet;
    
    public PointSET() {
        pointSet = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return pointSet.size() == 0;
    }
   
    public int size() {
        return pointSet.size();
    }
    
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null argument");
        }
        pointSet.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null argument");
        }
        return pointSet.contains(p);
    }
    
    public void draw() {
        for (Point2D p: pointSet) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("Null argument");
        }        
        Iterator<Point2D> allPoints = pointSet.iterator();
        SET<Point2D> pointsWithinRange = new SET<Point2D>();
        
        while (allPoints.hasNext()) {
            Point2D p = allPoints.next();
            if (rect.contains(p)) {
                pointsWithinRange.add(p);
            }
        }        
        return pointsWithinRange;                
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null argument");
        }        
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        Iterator<Point2D> allPoints = pointSet.iterator();
        
        while (allPoints.hasNext()) {
            Point2D pointInSet = allPoints.next();
            double distance = pointInSet.distanceSquaredTo(p);            
            if (distance < minDistance) {
                nearestPoint = pointInSet;
                minDistance = distance;
            }
        }        
        return nearestPoint;        
    }
}