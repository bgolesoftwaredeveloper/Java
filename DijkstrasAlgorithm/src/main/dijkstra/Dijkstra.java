/**
 * @file Dijkstra.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-06-02
 *
 * @brief Implementation of Dijkstra's Algorithm using an adjacency list and a priority queue.
 *
 * This program computes the shortest paths from a single source node (node 0 in this example)
 * to all other nodes in a directed, weighted graph using Dijkstra's algorithm.
 * The graph is represented using an adjacency list, and edge weights are assumed to be non-negative.
 *
 * Features:
 * - Object-oriented design with encapsulation
 * - Defensive programming practices
 * - Efficient priority queue (min-heap) implementation
 * - Safe loop variable declarations and default initialization
 *
 * Input: Hardcoded graph with directed edges and non-negative weights
 * Output: Shortest distances from the source node to all other nodes
 *
 * Limitations:
 * - Does not handle negative weights (Dijkstra's algorithm is not suitable for them)
 * - Does not reconstruct the actual shortest path (only distances are returned)
 */
package dijkstra;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Main class that includes Edge and graph definitions.
 */
public class Dijkstra {

    /**
     * Represents a directed, weighted edge in the graph.
     * Contains destination node ('to') and the weight of the edge.
     */
    private static class Edge {
        private int _to;
        private int _weight;

        /**
         * Constructs an Edge with a specified destination and weight.
         * @param to
         * @param weight
         */
        public Edge(int to, int weight) {
            this._to = to;
            this._weight = weight;
        }

        /**
         * Returns the destination vertex of the edge.
         * @return
         */
        public int getTo() {
            return _to;
        }

        /**
         * Returns the weight associated with the edge.
         * @return
         */
        public int getWeight() {
            return _weight;
        }
    }

    /**
     * @brief Represents a node and its current known distance from the source.
     *
     * This helper class is used in the priority queue during the execution of Dijkstra's algorithm.
     * Each instance stores a graph node index and its associated distance from the starting node.
     * The class implements the Comparable interface to enable ordering based on distance,
     * allowing efficient extraction of the next closest node using a min-heap (priority queue).
     *
     * This class is internal to the Dijkstra implementation and not intended for external use.
     */
    private static class NodeDistance implements Comparable<NodeDistance> {
        private int _node;
        private int _distance;

        /**
         * Constructs a NodeDistance object with a given node index and distance.
         *
         * @param node     The index of the graph node
         * @param distance The current shortest known distance to this node
         */
        public NodeDistance(int node, int distance) {
            this._node = node;
            this._distance = distance;
        }

        /**
         * Returns the graph node index represented by this object.
         *
         * @return The node index
         */
        public int getNode() {
            return _node;
        }

        /**
         * Returns the shortest known distance to the node.
         *
         * @return The distance value
         */
        public int getDistance() {
            return _distance;
        }

        /**
         * Compares this NodeDistance object to another based on distance.
         * Enables priority queue sorting in ascending order of distance.
         *
         * @param other The other NodeDistance object to compare to
         * @return A negative integer, zero, or a positive integer as this distance
         *         is less than, equal to, or greater than the other's distance
         */
        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this._distance, other._distance);
        }
    }

    /**
     * Represents a graph using adjacency list.
     * Supports adding edges and computing the shortest paths using Dijkstra's algorithm.
     */
    public static class Graph {
        private int _vertices;
        private List<List<Edge>> _adjacentList;

        /**
         * Constructs a graph with a specified number of vertices.
         * Initializes an empty adjacency list.
         * @param vertices
         */
        public Graph(int vertices) {
            // Validate input: number of vertices must be greater than 0.
            if (vertices <= 0) {
                throw new IllegalArgumentException("Number of vertices must be positive.");
            }

            this._vertices = vertices;
            this._adjacentList = new ArrayList<>();

            // Initialize the adjacency list with empty lists for each vertex.
            for (int index = 0; index < vertices; index++) {
                _adjacentList.add(new ArrayList<>());
            }
        }

        /**
         * Adds a directed edge from one vertex to another with a specified weight.
         * @param from
         * @param to
         * @param weight
         */
        public void addEdge(int from, int to, int weight) {
            // Ensure both vertices are within valid range.
            if (from < 0 || from >= _vertices || to < 0 || to >= _vertices) {
                throw new IndexOutOfBoundsException("Invalid vertex index.");
            }

            // Ensure the weight is non-negative (Dijkstra does not support negative weights).
            if (weight < 0) {
                throw new IllegalArgumentException("Edge weight cannot be negative.");
            }

            // Add the edge to the adjacency list for the 'from' node.
            _adjacentList.get(from).add(new Edge(to, weight));
        }

        /**
         * Computes the shortest distances from the given start node to all other nodes
         * using Dijkstra's algorithm.
         *
         * @param start The index of the starting node
         * @return An array of shortest distances from start to each node
         */
        public int[] dijkstra(int start) {
            if (start < 0 || start >= _vertices) {
                throw new IllegalArgumentException("Start node is out of bounds.");
            }

            // Distance array, initialized to "infinity" (MAX_VALUE)
            int[] distance = new int[_vertices];

            Arrays.fill(distance, Integer.MAX_VALUE);

            distance[start] = 0;

            // Priority queue to select the next node with the smallest distance.
            PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();

            // Add the start node to the queue with a distance of 0.
            priorityQueue.offer(new NodeDistance(start, 0));

            // Main loop: visit nodes in order of shortest known distance.
            while (!priorityQueue.isEmpty()) {
                NodeDistance current = priorityQueue.poll();
                int currentDistance = current.getDistance();
                int currentNode = current.getNode();

                // Skip if we have already found a shorter path.
                if (currentDistance > distance[currentNode]) {
                    continue;
                }

                // Explore each neighbor of the current node.
                List<Edge> neighbors = _adjacentList.get(currentNode);

                for (Edge value : neighbors) {
                    int neighbor = value.getTo();
                    int weight = value.getWeight();
                    int newDistance = currentDistance + weight;

                    // Update the distance if a shorter path is found.
                    if (newDistance < distance[neighbor]) {
                        distance[neighbor] = newDistance;
                        priorityQueue.offer(new NodeDistance(neighbor, newDistance));
                    }
                }
            }

            return distance;
        }
    }
}
