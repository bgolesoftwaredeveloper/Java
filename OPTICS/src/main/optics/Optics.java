/**
 * File             :   Optics.java
 * Project          :   OPTICS Clustering
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-27
 * Description      :   This file implements the OPTICS (Ordering Points To Identify the Clustering Structure)
 *                      density-based clustering algorithm. The OPTICS algorithm computes a reachability plot
 *                      revealing the cluster structure of a dataset, even with varying densities.
 *
 *                      This class encapsulates:
 *                      - Neighbor discovery based on ε-radius
 *                      - Core-distance calculation
 *                      - Reachability distance updating
 *                      - Cluster ordering based on density connectivity
 *
 *                      This implementation does not explicitly extract clusters but returns an ordered list of
 *                      points with computed reachability distances.
 */
package optics;

import java.util.*;

public class Optics {
    private List<Point> _points;
    private double _epsilon;
    private int _minimumPoints;
    private List<Point> _orderedList;

    /**
     * Constructs an OPTICS instance with the given dataset, epsilon radius, and minimum points.
     *
     * @param points         The list of data points to cluster.
     * @param epsilon        The radius within which neighbors are searched.
     * @param minimumPoints  The minimum number of neighbors required to be a core point.
     */
    public Optics(List<Point> points, double epsilon, int minimumPoints) {
        this._points = points;
        this._epsilon = epsilon;
        this._minimumPoints = minimumPoints;
        this._orderedList = new ArrayList<>();
    }

    /**
     * Returns all neighbors of a given point that lie within ε-distance.
     *
     * @param point  The point for which to find neighbors.
     * @return       A list of points within ε-distance of the given point.
     */
    private List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();

        for (Point other : _points) {
            if (point != other && point.distanceTo(other) <= _epsilon) {
                neighbors.add(other);
            }
        }

        return neighbors;
    }

    /**
     * Calculates the core distance of a point, which is the distance to its (MinPts - 1)th nearest neighbor.
     * If the point is not dense enough, returns positive infinity.
     *
     * @param point      The point for which to calculate core distance.
     * @param neighbors  The list of neighbors of the point.
     * @return           The core distance or positive infinity if not a core point.
     */
    private double coreDistance(Point point, List<Point> neighbors) {
        if (neighbors.size() + 1 < _minimumPoints) {
            return Double.POSITIVE_INFINITY;
        }

        List<Double> distances = new ArrayList<>();

        for (Point neighbor : neighbors) {
            distances.add(point.distanceTo(neighbor));
        }

        Collections.sort(distances);

        return distances.get(_minimumPoints - 2);
    }

    /**
     * Updates the priority queue (seeds) with neighbors of the current core point,
     * adjusting their reachability distances based on the core distance and actual distance.
     *
     * For each unprocessed neighbor, this method calculates a new reachability distance,
     * which is the maximum of the core distance of the center point and the distance between
     * the center point and the neighbor. If this new reachability distance is better (lower)
     * than the neighbor's current reachability, the neighbor's reachability is updated,
     * and the neighbor is added to the priority queue to be processed later.
     *
     * The seedMap keeps track of the PointDistance objects currently in the priority queue
     * to avoid redundant entries and enable efficient updates.
     *
     * @param seeds       The priority queue holding points ordered by their reachability distances.
     * @param seedMap     A map tracking the current PointDistance entries in the seeds queue.
     * @param center      The current core point from which neighbors are being updated.
     * @param neighbors   The list of neighboring points within epsilon distance from the center.
     * @param coreDistance The core distance of the center point (distance to its MinPts-th neighbor).
     */
    private void updateSeeds(PriorityQueue<PointDistance> seeds, Map<Point, PointDistance> seedMap,
                             Point center, List<Point> neighbors, double coreDistance) {
        double newReachability = 0.0;

        PointDistance pointDistance = null;

        for (Point neighbor : neighbors) {
            if (!neighbor.isProcessed()) {
                newReachability = Math.max(coreDistance, center.distanceTo(neighbor));

                // If neighbor has no reachability assigned yet (infinity) or new is better.
                if (neighbor.getReachability() == Double.POSITIVE_INFINITY) {
                    neighbor.setReachability(newReachability);
                    seeds.add(new PointDistance(neighbor, newReachability));
                } else if (newReachability < neighbor.getReachability()) {
                    // Update reachability in neighbor and add new point distance to priority queue.
                    neighbor.setReachability(newReachability);

                    // Add new entry to priority queue; old will remain but will be skipped later.
                    pointDistance = new PointDistance(neighbor, newReachability);

                    seeds.add(pointDistance);

                    seedMap.put(neighbor, pointDistance);
                }
            }
        }
    }

    /**
     * Expands the cluster ordering starting from the given core point.
     * Points are ordered based on density connectivity and reachability distances are updated accordingly.
     *
     * @param point  The starting point for expansion (should be unprocessed).
     */
    private void expandClusterOrder(Point point) {
        PriorityQueue<PointDistance> seeds = new PriorityQueue<>(Comparator.comparingDouble(PointDistance::getDistance));
        Map<Point, PointDistance> seedMap = new HashMap<>();

        point.setProcessed(true);
        point.setReachability(Double.POSITIVE_INFINITY);

        _orderedList.add(point);

        List<Point> neighbors = getNeighbors(point);

        double coreDistance = coreDistance(point, neighbors);

        if (coreDistance != Double.POSITIVE_INFINITY) {
            updateSeeds(seeds, seedMap, point, neighbors, coreDistance);

            PointDistance pointDistance = null;
            Point current = null;

            List<Point> currentNeighbors = null;

            double currentCoreDistance = 0.0;

            while (!seeds.isEmpty()) {
                pointDistance = seeds.poll();
                current = pointDistance.getPoint();

                // Check if this entry is the most up-to-date (avoid stale priority queue entries).
                if (current.isProcessed() || current.getReachability() != pointDistance.getDistance()) {
                    continue;
                }

                current.setProcessed(true);

                _orderedList.add(current);

                currentNeighbors = getNeighbors(current);

                currentCoreDistance = coreDistance(current, currentNeighbors);

                if (currentCoreDistance != Double.POSITIVE_INFINITY) {
                    updateSeeds(seeds, seedMap, current, currentNeighbors, currentCoreDistance);
                }
            }
        }
    }

    /**
     * Executes the OPTICS algorithm on the input dataset.
     * Iteratively expands each unprocessed point to form an ordered reachability list.
     *
     * @return A list of points in order of processing, each with assigned reachability distance.
     */
    public List<Point> execute() {
        // Reset all points.
        for (Point point : _points) {
            point.setReachability(Double.POSITIVE_INFINITY);
            point.setProcessed(false);
        }

        _orderedList.clear();

        for (Point point : _points) {
            if (!point.isProcessed()) {
                expandClusterOrder(point);
            }
        }

        return _orderedList;
    }
}
