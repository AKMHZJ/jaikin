package Jaikin;

import java.util.ArrayList;

public class Algorithm {
    public static ArrayList<Point2D> smooth(ArrayList<Point2D> inputPoints, int iterations) {
        if (inputPoints.size() < 3 || iterations == 0) {
            return new ArrayList<>(inputPoints);
        }

        ArrayList<Point2D> result = new ArrayList<>(inputPoints);

        for (int iter = 0; iter < iterations; iter++) {
            ArrayList<Point2D> newPoints = new ArrayList<>();
            newPoints.add(result.get(0).copy());
            for (int i = 0; i < result.size() - 1; i++) {
                Point2D p0 = result.get(i);
                Point2D p1 = result.get(i + 1);

                Point2D q = new Point2D(
                        0.75 * p0.x + 0.25 * p1.x,
                        0.75 * p0.y + 0.25 * p1.y);

                Point2D r = new Point2D(
                        0.25 * p0.x + 0.75 * p1.x,
                        0.25 * p0.y + 0.75 * p1.y);
                newPoints.add(q);
                newPoints.add(r);
            }
            newPoints.add(result.get(result.size() - 1).copy());
            result = newPoints;
        }
        return result;
    }
}
