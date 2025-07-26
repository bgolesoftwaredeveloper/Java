/**
 * @file DBSCAN.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-06-10
 *
 * @brief Implementation of the DBSCAN (Density-Based Spatial Clustering of Applications with Noise) algorithm in Java.
 *
 * This class provides a complete implementation of the DBSCAN clustering algorithm, which groups spatial data
 * based on density. DBSCAN is a powerful unsupervised learning algorithm capable of discovering clusters
 * of arbitrary shape, while also detecting and labeling noise or outlier points.
 *
 * ### Core Features:
 * - Automatically detects the number of clusters based on density connectivity.
 * - Supports two customizable parameters: epsilon (radius) and minimum number of points per cluster.
 * - Efficiently expands clusters using a BFS-like region-growing approach.
 * - Supports reset and printing of clusters for visualization or analysis.
 *
 * ### Main Components:
 * - `cluster()` - Initiates the DBSCAN clustering process on the dataset.
 * - `expandCluster(...)` - Recursively grows a cluster starting from a core point.
 * - `regionQuery(...)` - Finds all neighbors within epsilon distance of a point.
 * - `reset()` - Resets all points to unclassified for reuse.
 * - `printClusters()` - Outputs clustered points grouped by cluster ID.
 *
 * ### Usage:
 * To use this class, initialize it with a list of `Point` objects, an epsilon value, and a minimum number of points.
 * Call `cluster()` to perform clustering, then `printClusters()` to display the results.
 *
 * This implementation assumes the `Point` class provides appropriate methods:
 * - `getVisited()`, `setVisited(boolean)`
 * - `getClusterId()`, `setClusterId(int)`
 * - `Distance(Point)` - for computing Euclidean distance
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class DBSCAN {
    public final static int NOISE = -1;
    public final static int UNCLASSIFIED = -2;

    private List<Point> _points;
    private double _epsilon;
    private int _minimumPoints;

    /**
     * Constructs a new DBSCAN clustering instance with the specified dataset and parameters.
     *
     * @param points
     * @param epsilon
     * @param minimumPoints
     */
    public DBSCAN(List<Point> points, double epsilon, int minimumPoints) {
        this._points = points;
        this._epsilon = epsilon;
        this._minimumPoints = minimumPoints;
    }

    /**
     * Determines if a given point is a core point based on the minimum points threshold.
     *
     * @param point The point to check.
     * @return true if the point is a core point; false otherwise.
     */
    private boolean isCorePoint(Point point) {
        return regionQuery(point).size() >= _minimumPoints;
    }

    /**
     * Expands a cluster by recursively visiting and assigning the same cluster ID
     * to all density-reachable points from the given seed point.
     *
     * This method uses a breadth-first search (BFS)-like approach to ensure
     * all connected neighbors are included in the cluster.
     *
     * @param point
     * @param neighbors
     * @param clusterId
     */
    private void expandCluster(Point point, List<Point> neighbors, int clusterId) {
        // Assign the cluster id.
        point.setClusterId(clusterId);
        point.setVisited(true);

        Point neighbor = null;
        List<Point> moreNeighbors = null;

        Queue<Point> queue = new LinkedList<>(neighbors);
        Set<Point> enqueued = new HashSet<>(neighbors);

        while (!queue.isEmpty()) {
            neighbor = queue.poll();

            if (neighbor.getVisited()) {
                continue;
            }

            // Mark the neighbor as visited and assign the cluster ID.
            neighbor.setVisited(true);

            if (neighbor.getClusterId() == UNCLASSIFIED || neighbor.getClusterId() == NOISE) {
                neighbor.setClusterId(clusterId);
            }

            moreNeighbors = regionQuery(neighbor);

            if (moreNeighbors.size() >= _minimumPoints) {
                for (Point anotherNeighbor : moreNeighbors) {
                    if (!anotherNeighbor.getVisited() && enqueued.add(anotherNeighbor)) {
                        queue.add(anotherNeighbor);
                    }
                }
            }
        }
    }

    /**
     * Returns all points in the dataset that lie within epsilon distance of the specified point.
     * This method is used to determine whether a point is a core point and to find its neighbors.
     *
     * @param point
     * @return neighbors
     */
    private List<Point> regionQuery(Point point) {
        List<Point> neighbors = new ArrayList<>();

        for (Point position : _points) {
            if (point.distance(position) <= _epsilon) {
                neighbors.add(position);
            }
        }

        return neighbors;
    }

    /**
     * Performs the DBSCAN clustering algorithm on the provided dataset.
     *
     * Iterates through each point, identifying core points based on density
     * and expanding clusters from those points. Points that do not belong
     * to any cluster are marked as noise.
     */
    public void cluster() {
        int clusterId = 0;

        List<Point> neighbors = null;

        // Iterate over all points.
        for (Point point : _points) {
            if (point.getVisited()) {
                continue;
            }

            neighbors = regionQuery(point);

            if (!isCorePoint(point)) {
                point.setClusterId(NOISE);
                point.setVisited(true);
            } else {
                expandCluster(point, neighbors, clusterId);
                clusterId++;
            }
        }
    }

    /**
     * Resets the clustering state of all points in the dataset.
     *
     * This sets all points' cluster IDs to UNCLASSIFIED and marks them as unvisited.
     * Useful for re-running the algorithm on the same dataset.
     */
    public void reset() {
        if (_points != null) {
            if (!_points.isEmpty()) {
                for (Point point : _points) {
                    // Mark as unclassified.
                    point.setClusterId(UNCLASSIFIED);
                    point.setVisited(false);
                }
            }
        }
    }

    /**
     * Prints the current state of clusters in the dataset to the console.
     *
     * Groups points by their assigned cluster ID and prints each group with a label.
     * Labels include "Unclassified", "Noise", and "Cluster N" where N is the cluster ID.
     */
    public void printClusters() {
        if (_points != null) {
            if (!_points.isEmpty()) {
                Map<Integer, List<Point>> grouped = _points.stream()
                        .collect(Collectors.groupingBy(Point::getClusterId));

                int key = 0;
                String label = "";

                for (Map.Entry<Integer, List<Point>> entry : grouped.entrySet()) {
                    key = entry.getKey();

                    if (key == UNCLASSIFIED) {
                        label = "Unclassified";
                    } else if (key == NOISE) {
                        label = "Noise";
                    } else {
                        label = "Cluster " + key;
                    }

                    System.out.println(label + ":");

                    for (Point point : entry.getValue()) {
                        System.out.println("\t" + point);
                    }

                    System.out.println();
                }
            }
        }
    }
}