import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Stack;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class KdTree {
    private Node root;
    private int size = 0;
    
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
                
        private Node(Point2D point) {
            p = point;
        }
        
        private void setLB(Node n) {
            lb = n;
        }
        
        private void setRT(Node n) {
            rt = n;
        }
                
        private void setRect(double xmin, double ymin, 
                             double xmax, double ymax) {
            rect = new RectHV(xmin, ymin, xmax, ymax);
        }
                
        private Point2D getPoint() {
            return p;
        }
        
        private Node getLB() {
            return lb;
        }
        
        private Node getRT() {
            return rt;
        }
        
        private RectHV getRect() {
            return rect;
        }
    }
    
    public KdTree() {
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null argument");
        }
        if (root == null) {
            root = new Node(p);
            root.setRect(0, 0, 1, 1);
//            StdOut.print(root.getPoint());
//            StdOut.println(root.getRect());
            size++;
        } else {
            int depth = 0;
            Node node = root;
            while (true) {
                if (p.equals(node.getPoint())) {
                    break;
                }
                if (depth % 2 != 0) {
                    if (p.y() < node.getPoint().y()) {
                        if (node.getLB() == null) {
                            node.setLB(new Node(p));
                            Node leafNode = node.getLB();
                            
                            double xmin = node.getRect().xmin();
                            double xmax = node.getRect().xmax();
                            double ymin = node.getRect().ymin();
                            double ymax = node.getPoint().y();
                            leafNode.setRect(xmin, ymin, xmax, ymax);
//                            StdOut.print(leafNode.getPoint());
//                            StdOut.print(node.getPoint());
//                            StdOut.println(leafNode.getRect());
 
                            size++;
                            break;
                        } else {
                            node = node.getLB();
                        }
                    } else {
                        if (node.getRT() == null) {
                            node.setRT(new Node(p));
                            Node leafNode = node.getRT();

                            double xmin = node.getRect().xmin();
                            double xmax = node.getRect().xmax();
                            double ymin = node.getPoint().y();
                            double ymax = node.getRect().ymax();
                            leafNode.setRect(xmin, ymin, xmax, ymax);
//                            StdOut.print(leafNode.getPoint());
//                            StdOut.print(node.getPoint());
//                            StdOut.println(leafNode.getRect());
                                                       
                            size++;
                            break;
                        } else {
                            node = node.getRT();
                        }
                    }                    
                } else {                    
                    if (p.x() < node.getPoint().x()) {
                        if (node.getLB() == null) {
                            node.setLB(new Node(p));
                            Node leafNode = node.getLB();
                            
                            double xmin = node.getRect().xmin();
                            double xmax = node.getPoint().x();
                            double ymin = node.getRect().ymin();
                            double ymax = node.getRect().ymax();
                            leafNode.setRect(xmin, ymin, xmax, ymax);
//                            StdOut.print(leafNode.getPoint());
//                            StdOut.print(node.getPoint());
//                            StdOut.println(leafNode.getRect());
                            
                            size++;
                            break;
                        } else {
                            node = node.getLB();
                        }
                    } else {
                        if (node.getRT() == null) {
                            node.setRT(new Node(p));
                            Node leafNode = node.getRT();
                            
                            double xmin = node.getPoint().x();
                            double xmax = node.getRect().xmax();
                            double ymin = node.getRect().ymin();
                            double ymax = node.getRect().ymax();
                            leafNode.setRect(xmin, ymin, xmax, ymax);
//                            StdOut.print(leafNode.getPoint());
//                            StdOut.print(node.getPoint());
//                            StdOut.println(leafNode.getRect());
                                                          
                            size++;
                            break;
                        } else {
                            node = node.getRT();
                        }
                    }
                }
                depth++;
            }            
        }
    }
    
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null argument");
        }
        if (size == 0) {
            return false;
        }
        Node node = root;
        int depth = 0;
        while (true) {
            if (node == null) {
                return false;
            }
            
            if (p.equals(node.getPoint())) {
                return true;
            }
                
            if (depth % 2 == 0) {
                if (p.x() < node.getPoint().x()) {
                    node = node.getLB();
                } else {
                    node = node.getRT();
                }
            } else {
                if (p.y() < node.getPoint().y()) {
                    node = node.getLB();
                } else {
                    node = node.getRT();
                }
            }
            depth++;
        }
    }
    
    public void draw() {
        Draw draw = new Draw();
        preorder(root, draw, 0);
        draw.show();
    }
    
    private void preorder(Node node, Draw draw, int depth) {
        if (node == null) {
            return;
        }
        
        Point2D p = node.getPoint();
        draw.filledCircle(p.x(), p.y(), 0.01);
        if (depth % 2 == 0) {            
            draw.setPenColor(Draw.RED);
            RectHV rect = node.getRect();
            double x = node.getPoint().x();
            double y0 = rect.ymin();
            double y1 = rect.ymax();
            draw.line(x, y0, x, y1);
            draw.setPenColor();
        } else {
            draw.setPenColor(Draw.BLUE);
            RectHV rect = node.getRect();
            double x0 = rect.xmin();
            double x1 = rect.xmax();
            double y = node.getPoint().y();
            draw.line(x0, y, x1, y);
            draw.setPenColor();
        }
        Node lb = node.getLB();
        Node rt = node.getRT();
        preorder(lb, draw, depth + 1);
        preorder(rt, draw, depth + 1);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        if (root == null) return pointsInRange;
        if (rect == null) {
            throw new NullPointerException("Null argument");
        }
        Node node = root;
        int depth = 0;
        rangeSearch(node, pointsInRange, rect, depth);
        return pointsInRange;
    }
        
    private Stack<Point2D> rangeSearch(Node node, Stack<Point2D> stack,
                                       RectHV rect, int depth) {
        if (node == null) return null;
                
        if (rect.contains(node.getPoint())) stack.push(node.getPoint());
        
        Node lb = node.getLB();
        Node rt = node.getRT();
                
        if (depth % 2 == 0) {
            double xmin = node.getPoint().x();
            double ymin = node.getRect().ymin();
            double xmax = node.getRect().xmax();
            double ymax = node.getRect().ymax();
            RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);
            
            xmin = node.getRect().xmin();
            xmax = node.getPoint().x();
            RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);
            
            if (rect.intersects(rtRect) && rect.intersects(lbRect)) {
                rangeSearch(rt, stack, rect, depth+1);
                rangeSearch(lb, stack, rect, depth+1);
            } else if (rect.intersects(rtRect)) {
                rangeSearch(rt, stack, rect, depth+1);                
            } else {
                rangeSearch(lb, stack, rect, depth+1);
            }            
        } else {
            double xmin = node.getRect().xmin();
            double ymin = node.getPoint().y();
            double xmax = node.getRect().xmax();
            double ymax = node.getRect().ymax();
            RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);
            
            ymin = node.getRect().ymin();
            ymax = node.getPoint().y();
            RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);
            
            if (rect.intersects(rtRect) && rect.intersects(lbRect)) {
                rangeSearch(rt, stack, rect, depth+1);
                rangeSearch(lb, stack, rect, depth+1);
            } else if (rect.intersects(rtRect)) {
                rangeSearch(rt, stack, rect, depth+1);                
            } else {
                rangeSearch(lb, stack, rect, depth+1);
            }  
        }        
        return stack;
    }
    
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        Node node = root;
        Point2D nearestPoint = root.getPoint();
        nearestPoint = nearestSearch(p, node, nearestPoint, 0);
        return nearestPoint;
    }
    
    private Point2D nearestSearch(Point2D p, Node node, 
                                  Point2D nearestPoint, int depth) {
        if (node == null) return nearestPoint;
        
        double disToThisPoint = p.distanceSquaredTo(node.getPoint());
        double disToNearestPoint = p.distanceSquaredTo(nearestPoint); 
        if (disToThisPoint < disToNearestPoint) {
            nearestPoint = node.getPoint();
        }
        
        Node lb = node.getLB();
        Node rt = node.getRT();
        
        if (depth % 2 == 0) {
            if (p.x() < node.getPoint().x()) {
                nearestPoint = nearestSearch(p, lb, nearestPoint, depth+1); 
                disToNearestPoint = p.distanceSquaredTo(nearestPoint);
                
                double xmin = node.getPoint().x();
                double ymin = node.getRect().ymin();
                double xmax = node.getRect().xmax();
                double ymax = node.getRect().ymax();                
                RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);
                double disToRTRect = rtRect.distanceSquaredTo(p);
                
                if (disToRTRect < disToNearestPoint)
                    nearestPoint = nearestSearch(p, rt, nearestPoint, depth+1);                                                    
            } else {
                nearestPoint = nearestSearch(p, rt, nearestPoint, depth+1); 
                disToNearestPoint = p.distanceSquaredTo(nearestPoint);
                
                double xmin = node.getRect().xmin();
                double ymin = node.getRect().ymin();
                double xmax = node.getPoint().x();
                double ymax = node.getRect().ymax();                
                RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);
                double disToLBRect = lbRect.distanceSquaredTo(p);
                
                if (disToLBRect < disToNearestPoint)
                    nearestPoint = nearestSearch(p, lb, nearestPoint, depth+1);                                
            }
        } else {
            if (p.y() < node.getPoint().y()) {
                nearestPoint = nearestSearch(p, lb, nearestPoint, depth+1); 
                disToNearestPoint = p.distanceSquaredTo(nearestPoint);
                
                double xmin = node.getRect().xmin();
                double ymin = node.getPoint().y();
                double xmax = node.getRect().xmax();
                double ymax = node.getRect().ymax();  
                RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);
                double disToRTRect = rtRect.distanceSquaredTo(p);
                
                if (disToRTRect < disToNearestPoint)
                    nearestPoint = nearestSearch(p, rt, nearestPoint, depth+1);                                                    
            } else {
                nearestPoint = nearestSearch(p, rt, nearestPoint, depth + 1); 
                disToNearestPoint = p.distanceSquaredTo(nearestPoint);
                
                double xmin = node.getRect().xmin();
                double ymin = node.getRect().ymin();
                double xmax = node.getRect().xmax();
                double ymax = node.getPoint().y();
                RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);
                double disToLBRect = lbRect.distanceSquaredTo(p);
                
                if (disToLBRect < disToNearestPoint)
                    nearestPoint = nearestSearch(p, lb, nearestPoint, depth+1);                                
            }
        }
        return nearestPoint;
    }
    
    public static void main(String[] args) {
        KdTree kdtree = new KdTree();

//        String filename = args[0];
//        In in = new In(filename);
//        
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            kdtree.insert(new Point2D(x, y));
//        }
//        kdtree.draw();
        kdtree.nearest(new Point2D(0.81, 0.30));        
    }
    
    
}