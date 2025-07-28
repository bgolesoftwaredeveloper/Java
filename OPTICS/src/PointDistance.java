/**
 * File             :   PointDistance.java
 * Project          :   OPTICS Clustering
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-28
 * Description      :   This file defines the PointDistance class, which is a helper class used in the OPTICS algorithm.
 *                      It pairs a Point with its associated distance (e.g., reachability distance) to facilitate
 *                      comparisons and sorting in priority queues.
 *
 *                      Equality and hashing are based solely on the Point object to ensure correct behavior in collections.
 */
public class PointDistance {
    private Point _point;
    private double _distance;

    /**
     * Constructs a PointDistance object with the specified point and distance.
     *
     * @param point     The Point object being wrapped.
     * @param distance  The distance associated with the point.
     */
    public PointDistance(Point point, double distance) {
        this._point = point;
        this._distance = distance;
    }

    /**
     * Returns the point associated with this object.
     *
     * @return The Point object.
     */
    public Point getPoint() {
        return _point;
    }

    /**
     * Returns the distance associated with the point.
     *
     * @return The distance value.
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Checks equality between this PointDistance and another object.
     * Equality is based solely on the underlying Point reference.
     *
     * @param object The object to compare with.
     * @return       True if the other object is a PointDistance with the same point, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PointDistance)) {
            return false;
        }

        PointDistance that = (PointDistance)object;

        return Double.compare(_distance, that._distance) == 0 && _point.equals(that._point);
    }

    /**
     * Computes the hash code based on the point, ensuring consistency with equals().
     *
     * @return The hash code of the point.
     */
    @Override
    public int hashCode() {
        return _point.hashCode();
    }
}
