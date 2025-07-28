/**
 * File             :   Point.java
 * Project          :   OPTICS Clustering
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-27
 * Description      :   This file defines the Point class used in the OPTICS clustering algorithm.
 *                      Each point represents a data item in a multi-dimensional space, and stores
 *                      metadata such as its index in the dataset, reachability distance, and whether
 *                      it has been processed during the clustering process.
 *
 *                      The class provides utility methods for computing Euclidean distance and
 *                      accessors/mutators for internal state.
 */
public class Point {
    private double[] _coordinates;
    private int _index;
    private double _reachability;
    private boolean _processed;

    /**
     * Constructs a Point object with specified coordinates and index.
     *
     * @param coordinates  An array of doubles representing the coordinates of the point.
     * @param index        The index of the point in the input dataset.
     */
    public Point(double[] coordinates, int index) {
        this._coordinates = coordinates;
        this._index = index;
    }

    /**
     * Returns the coordinate vector of the point.
     *
     * @return A double array representing the point's coordinates.
     */
    public double[] getCoordinates() {
        return _coordinates;
    }

    /**
     * Returns the index of the point in the dataset.
     *
     * @return The index of the point.
     */
    public int getIndex() {
        return _index;
    }

    /**
     * Returns the current reachability distance of the point.
     *
     * @return The reachability distance (or infinity if unassigned).
     */
    public double getReachability() {
        return _reachability;
    }

    /**
     * Returns whether the point has been processed by the algorithm.
     *
     * @return True if the point has been processed, false otherwise.
     */
    public boolean isProcessed() {
        return _processed;
    }

    /**
     * Sets the coordinates of the point.
     *
     * @param coordinates A double array representing new coordinates.
     */
    public void setCoordinates(double[] coordinates) {
        this._coordinates = coordinates;
    }

    /**
     * Sets the reachability distance for the point.
     *
     * @param reachability A double value representing the reachability distance.
     */
    public void setReachability(double reachability) {
        this._reachability = reachability;
    }

    /**
     * Marks the point as processed or unprocessed.
     *
     * @param processed Boolean indicating whether the point has been processed.
     */
    public void setProcessed(boolean processed) {
        this._processed = processed;
    }

    /**
     * Calculates the Euclidean distance from this point to another point.
     *
     * @param other Another Point object.
     * @return      The Euclidean distance between this point and the other.
     */
    public double distanceTo(Point other) {
        double sum = 0.0;
        double difference = 0.0;

        double[] otherCoordinates = other.getCoordinates();

        for (int index = 0; index < _coordinates.length; index++) {
            difference = this._coordinates[index] - otherCoordinates[index];
            sum += difference * difference;
        }

        return Math.sqrt(sum);
    }
}
