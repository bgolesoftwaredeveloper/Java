/**
 * @file Point.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-06-10
 *
 * @brief Represents a two-dimensional point used in the DBSCAN clustering algorithm.
 *
 * This class defines a `Point` with x and y coordinates, a visited flag, and a cluster ID. It is
 * designed to be used as the fundamental data element in the DBSCAN clustering process.
 *
 * Each point tracks:
 * - Its coordinates in 2D space
 * - Whether it has been visited during clustering
 * - The ID of the cluster it has been assigned to
 *
 * ### Key Features:
 * - Accessors and mutators for x/y coordinates, visited status, and cluster ID.
 * - A method to compute Euclidean distance to another point.
 * - A formatted string representation for easy printing in clustering output.
 */
import java.util.Objects;

public class Point {
    private double _xAxis;
    private double _yAxis;
    private boolean _visited;
    private int _clusterId;

    /**
     * Returns the x-coordinate of this point.
     * @return The x-axis value.
     */
    public double getXAxis() {
        return _xAxis;
    }

    /**
     * Returns the y-coordinate of this point.
     * @return The y-axis value.
     */
    public double getYAxis() {
        return _yAxis;
    }

    /**
     * Returns whether this point has been visited during clustering.
     * @return True if point has been visited; otherwise false.
     */
    public boolean getVisited() {
        return _visited;
    }

    /**
     * Returns the cluster ID assigned to this point.
     * @return An integer representing the cluster ID. May be UNCLASSIFIED or NOISE.
     */
    public int getClusterId() {
        return _clusterId;
    }

    /**
     * Sets the visited status of this point.
     * @param visited True to mark the point as visited; false to mark it as unvisited.
     */
    public void setVisited(boolean visited) {
        this._visited = visited;
    }

    /**
     * Sets the cluster ID of this point.
     * @param clusterId The integer ID of the cluster to assign this point to.
     */
    public void setClusterId(int clusterId) {
        this._clusterId = clusterId;
    }

    /**
     * Constructs a new point with the specified x and y coordinates.
     * The point is initially unvisited and unclustered.
     * @param xAxis
     * @param yAxis
     */
    public Point(double xAxis, double yAxis) {
        this._xAxis = xAxis;
        this._yAxis = yAxis;

        // Not visited by default.
        this._visited = false;

        // Give an unclassified ID.
        this._clusterId = DBSCAN.UNCLASSIFIED;
    }

    /**
     * Calculates the Euclidean distance between this point and another point.
     * @param other
     * @return The distance from one point to another.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this._xAxis - other._xAxis, 2) + Math.pow(this._yAxis - other._yAxis, 2));
    }

    /**
     * Checks whether this point is equal to another object.
     * Two points are considered equal if their x and y coordinates are exactly the same.
     * @param obj
     * @return True if the given object is a Point with the same coordinates; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Point point)) {
            return false;
        }

        return Double.compare(point._xAxis, this._xAxis) == 0 && Double.compare(point._yAxis, this._yAxis) == 0;
    }

    /**
     * Computes a hash code for this point based on its coordinates.
     * This ensures consistency with the equals method when used in hashed collections.
     * @return An integer hash code derived from the x and y coordinates.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this._xAxis, this._yAxis);
    }

    /**
     * Returns a formatted string representation of this point.
     * @return (x, y) rounded to two decimal places.
     */
    @Override
    public String toString() {
        return String.format("\t(%.2f, %.2f)", this._xAxis, this._yAxis);
    }
}
