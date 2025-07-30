/**
 * File             :   OpticsTest.java
 * Project          :   OPTICS Clustering - Unit Tests
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-30
 * Description      :   This file contains unit tests for the OPTICS clustering algorithm implementation.
 *                      The tests cover basic clustering behavior, reachability calculations, and edge cases
 *                      such as single-point input and disconnected clusters.
 */
package optics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class OpticsTest {
    /**
     * Test Method      :   TestSingleClusterFormation
     * Description      :   Verifies that OPTICS correctly processes a compact single-cluster dataset.
     *                      All points are close enough to each other to meet the minimum points threshold,
     *                      so they should all be processed with reasonable reachability values.
     */
    @Test
    public void TestSingleClusterFormation() {
        // Arrange.
        double[][] data = {
            {1.0, 2.0},
            {1.2, 2.1},
            {0.8, 1.9},
            {1.1, 2.0}
        };

        List<Point> points = new ArrayList<>();

        for (int index = 0; index < data.length; index++) {
            points.add(new Point(data[index], index));
        }

        Optics optics = new Optics(points, 1.0, 2);

        // Act.
        List<Point> ordered = optics.execute();

        // Assert.
        assertEquals(4, ordered.size(), "All points should be included in the ordering.");

        for (Point point : ordered) {
            assertTrue(point.getReachability() >= 0.0 || Double.isInfinite(point.getReachability()),
                "Rechability should be non-negative or infinite.");
        }
    }

    /**
     * Test Method      :   TestDisconnectedClusters
     * Description      :   Tests OPTICS with two well-separated clusters. Ensures that the algorithm
     *                      correctly processes all points and assigns infinite reachability to points
     *                      that are not density-reachable from earlier clusters.
     */
    @Test
    public void TestDisconnectedClusters() {
        // Arrange.
        double[][] data = {
            {1.0, 1.0},
            {1.1, 1.1},
            {10.0, 10.0},
            {10.1, 10.1}
        };

        List<Point> points = new ArrayList<>();

        for (int index = 0; index < data.length; index++) {
            points.add(new Point(data[index], index));
        }

        Optics optics = new Optics(points, 0.5, 2);

        // Act.
        List<Point> ordered = optics.execute();

        // Assert
        assertEquals(4, ordered.size(), "All points should be processed.");

        long infiniteReachabilityCount = ordered.stream()
            .filter(point -> Double.isInfinite(point.getReachability()))
            .count();

        assertTrue(infiniteReachabilityCount >= 2,
            "At least two points should have infinite reachability due to cluster separation.");
    }

    /**
     * Test Method      :   TestSinglePointInput
     * Description      :   Tests OPTICS with a single-point dataset. Verifies that the algorithm
     *                      handles this edge case by returning the point with infinite reachability.
     */
    @Test
    public void TestSinglePointInput() {
        // Arrange.
        double[][] data = {
            {3.0, 3.0}
        };

        List<Point> points = new ArrayList<>();

        points.add(new Point(data[0], 0));

        Optics optics = new Optics(points, 1.0, 2);

        // Act.
        List<Point> ordered = optics.execute();

        // Assert.
        assertEquals(1, ordered.size(), "Only one point should be returned.");
        assertTrue(Double.isInfinite(ordered.getFirst().getReachability()),
            "Single point must have infinite reachability.");
    }

    /**
     * Test Method      :   TestCoreDistanceAndReachability
     * Description      :   Validates that reachability distances are correctly computed for
     *                      nearby points, especially those within the epsilon neighborhood.
     *                      Confirms that the first point has infinite reachability and others
     *                      reflect meaningful distances.
     */
    @Test
    public void TestCoreDistanceAndReachability() {
        // Arrange.
        double[][] data = {
            {0, 0},
            {1, 1},
            {2, 2},
            {8, 8}
        };

        List<Point> points = new ArrayList<>();

        for (int index = 0; index < data.length; index++) {
            points.add(new Point(data[index], index));
        }

        Optics optics = new Optics(points, 3.0, 2);

        // Act.
        List<Point> ordered = optics.execute();

        // Assert.
        assertEquals(4, ordered.size(), "All points must be included in the ordering.");
        assertEquals(Double.POSITIVE_INFINITY, ordered.getFirst().getReachability(),
            "First point should have infinite reachability.");
        assertTrue(ordered.get(1).getReachability() <= 3.0, "Reachability should reflect proximity.");
    }

    /**
     * Test Method      :   TestAllPointsProcessed
     * Description      :   Ensures that all points are marked as processed after the OPTICS
     *                      algorithm is executed, regardless of clustering outcome.
     *                      Includes one distant outlier to test processing completeness.
     */
    @Test
    public void TestAllPointsProcessed() {
        // Arrange.
        double[][] data = {
            {5, 5},
            {6, 5},
            {5, 6},
            {6, 6},
            {50, 50}
        };

        List<Point> points = new ArrayList<>();

        for (int index = 0; index < data.length; index++) {
            points.add(new Point(data[index], index));
        }

        Optics optics = new Optics(points, 2.0, 2);

        // Act.
        List<Point> ordered = optics.execute();

        // Assert.
        for (Point point : ordered) {
            assertTrue(point.isProcessed(), "All points should be marked as processed.");
        }
    }
}
