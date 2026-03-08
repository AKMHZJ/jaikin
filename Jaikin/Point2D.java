package Jaikin;

public class Point2D {
    public double x, y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D copy() {
        return new Point2D(x, y);
    }
}
