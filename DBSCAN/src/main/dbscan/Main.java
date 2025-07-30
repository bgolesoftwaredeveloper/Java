/**
 * @file Main.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-06-10
 *
 * @brief Entry point for executing and demonstrating the DBSCAN clustering algorithm.
 *
 * This file defines the `Main` class, which initializes a simple dataset of 2D points and runs
 * the DBSCAN (Density-Based Spatial Clustering of Applications with Noise) algorithm on it.
 * The DBSCAN implementation groups nearby points based on a defined epsilon radius and
 * a minimum number of neighbors. The algorithm is capable of detecting noise (outliers)
 * and identifying dense clusters of arbitrary shape.
 *
 * ### Program Flow:
 * - Initializes a list of sample 2D points.
 * - Configures the DBSCAN parameters:
 *   - `epsilon`: the distance threshold for neighborhood inclusion.
 *   - `minimumPoints`: the density threshold to define a cluster.
 * - Instantiates a `DBSCAN` object and runs the clustering process.
 * - Prints the resulting clusters to the console.
 *
 * ### Output:
 * The clusters are printed with clear labels (e.g., "Cluster 0", "Noise") followed by the points
 * that belong to each cluster.
 *
 * ### Dependencies:
 * - Dbscan.java: Contains the clustering logic.
 * - Point.java: Defines the structure and behavior of individual data points.
 */
package dbscan;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize points.
        List<Point> points = Arrays.asList(
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 3),
            new Point(8, 7),
            new Point(8, 8),
            new Point(25, 80),
            new Point(8, 6),
            new Point(1, 3),
            new Point(2, 4)
        );

        double epsilon = 2.0;
        int minimumPoints = 3;

        // Initialize DBSCAN.
        Dbscan dbscan = new Dbscan(points, epsilon, minimumPoints);

        // Execute...
        dbscan.cluster();

        // Print the clusters.
        dbscan.printClusters();
    }
}