/**
 * @file DbscanTest.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-07-30
 *
 * @brief Unit tests for the DBSCAN clustering algorithm.
 *
 * This test suite validates the core functionality of the Dbscan class, including:
 * - Cluster formation and assignment
 * - Noise point detection
 * - Cluster reset behavior
 * - Euclidean distance validation
 */
package dbscan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DbscanTest {
    private List<Point> _points;
    private Dbscan _dbscan;

    /**
     * Test Setup       :   Initializes a basic dataset of 2D points and the DBSCAN instance
     *                      before each test. Used to evaluate clustering and noise behavior.
     */
    @BeforeEach
    public void setUp() {
        _points = Arrays.asList(
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

        _dbscan = new Dbscan(_points, 2.0, 3);
    }

    /**
     * Test Method      :   TestClusterFormation
     * Description      :   Verifies that the DBSCAN algorithm successfully forms clusters and
     *                      assigns valid cluster IDs to appropriate points.
     */
    @Test
    public void TestClusterFormation() {
        // Arrange.
        _dbscan.cluster();

        // Act.
        long clustered = _points.stream()
            .filter(point -> point.getClusterId() >= 0)
            .count();

        // Assert.
        assertTrue(clustered > 0, "Expected at least one clustered point.");
    }

    /**
     * Test Method      :   TestNoiseDetection
     * Description      :   Confirms that DBSCAN correctly marks points as noise (-1) when they
     *                      do not meet the density requirement.
     */
    @Test
    public void TestNoiseDetection() {
        // Arrange.
        _dbscan.cluster();

        // Act.
        long noise = _points.stream()
            .filter(point -> point.getClusterId() == Dbscan.NOISE)
            .count();

        // Assert.
        assertTrue(noise > 0, "Expected at least one noise point.");
    }

    /**
     * Test Method      :   TestResetClusters
     * Description      :   Tests the reset() method by checking that all points are returned to
     *                      an unclassified, unvisited state.
     */
    @Test
    public void TestResetClusters() {
        // Arrange.
        _dbscan.cluster();

        // Act.
        _dbscan.reset();

        // Assert.
        for (Point point : _points) {
            assertEquals(Dbscan.UNCLASSIFIED, point.getClusterId(), "Cluster ID should be UNCLASSIFIED.");
            assertFalse(point.getVisited(), "Visited flag should be false after reset.");
        }
    }

    /**
     * Test Method      :   TestClusterIdRange
     * Description      :   Checks that all cluster IDs after clustering are valid,
     *                      i.e., either NOISE or a non-negative integer.
     */
    @Test
    public void TestClusterIdRange() {
        // Arrange.
        _dbscan.cluster();

        // Act.
        int clusterId = Dbscan.UNCLASSIFIED;

        // Assert.
        for (Point point : _points) {
            clusterId = point.getClusterId();

            assertTrue(clusterId >= Dbscan.NOISE, "Cluster ID must be >= NOISE.");
        }
    }

    /**
     * Test Method      :   TestDistanceCalculation
     * Description      :   Ensures that the Euclidean distance function behaves correctly for
     *                      both identical and spatially separated points.
     */
    @Test
    public void TestDistanceCalculation() {
        // Arrange.
        Point point = new Point(0, 0);
        Point identical = new Point(0, 0);
        Point otherPoint = new Point(3, 4);

        // Act.

        // Assert.
        assertEquals(0.0, point.distance(identical), 0.0001,
            "Distance between identical points should be 0.");
        assertEquals(5.0, point.distance(otherPoint),
            "Expected distance of 5.0 between (0, 0) and (3, 4).");
    }

    /**
     * Test Method      :   TestClusterIdRange
     * Description      :   Checks that all cluster IDs after clustering are valid,
     *                      i.e., either NOISE or a non-negative integer.
     */
    @Test
    public void TestClusterUniqueness() {
        // Arrange.
        _dbscan.cluster();

        // Act.
        int clusterId = Dbscan.UNCLASSIFIED;

        // Assert.
        for (Point point : _points) {
            clusterId = point.getClusterId();

            assertTrue(clusterId == Dbscan.NOISE || clusterId >= 0,
                "Point must belong to one cluster or be noise.");
        }
    }
}
