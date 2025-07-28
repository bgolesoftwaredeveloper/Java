/**
 * File             :   Main.java
 * Project          :   OPTICS Clustering
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-28
 * Description      :   Entry point for the OPTICS clustering application. This class is responsible
 *                      for initializing sample data, executing the OPTICS algorithm, and displaying
 *                      the results (e.g., reachability distances and ordering).
 *
 *                      Modify this class to load real datasets or perform clustering on different inputs.
 */
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example usage of the OPTICS clustering algorithm.

        // Define sample data points.
        double[][] data = {
            {1.0, 2.0},
            {2.0, 2.0},
            {2.5, 2.1},
            {8.0, 8.0},
            {8.5, 8.2},
            {9.0, 8.0}
        };

        // Convert raw data into Point objects.
        List<Point> points = new ArrayList<>();

        for (int index = 0; index < data.length; index++) {
            points.add(new Point(data[index], index));
        }

        // Initialize and run the OPTICS algorithm.
        double epsilon = 3.0;
        int minimumPoints = 2;

        Optics optics = new Optics(points, epsilon, minimumPoints);
        List<Point> ordered = optics.execute();

        // Print clustering results.
        System.out.println("OPTICS Ordering and Reachability Distances:");

        for (Point point : ordered) {
            System.out.printf("Point %d: Reachability = %.2f\n",
                point.getIndex(),
                point.getReachability());
        }
    }
}
