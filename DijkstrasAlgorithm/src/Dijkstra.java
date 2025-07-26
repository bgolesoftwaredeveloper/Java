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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Collections;

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
         * Returns the number of vertices in the graph.
         * @return
         */
        public int getVertices() {
            return _vertices;
        }

        /**
         * Returns the adjacency list of the graph.
         * @return
         */
        public List<List<Edge>> getAdjacentList() {
            return _adjacentList;
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
         * @param start
         * @return
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
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(number -> number[0]));

            // Add the start node to the queue with a distance of 0.
            priorityQueue.offer(new int[]{0, start});

            // Declare loop variables with default values.
            int[] current = new int[]{-1, -1};
            int currentDistance = 0;
            int currentNode = 0;
            int neighbor = 0;
            int weight = 0;
            int newDistance = 0;

            List<Edge> neighbors = Collections.emptyList();
            Edge edge = new Edge(-1, 0);

            // Main loop: visit nodes in order of shortest known distance.
            while (!priorityQueue.isEmpty()) {
                current = priorityQueue.poll();
                currentDistance = current[0];
                currentNode = current[1];

                // Skip if we have already found a shorter path.
                if (currentDistance > distance[currentNode]) {
                    continue;
                }

                // Explore each neighbor of the current node.
                neighbors = _adjacentList.get(currentNode);

                for (Edge value : neighbors) {
                    edge = value;
                    neighbor = edge.getTo();
                    weight = edge.getWeight();
                    newDistance = currentDistance + weight;

                    // Update the distance if a shorter path is found.
                    if (newDistance < distance[neighbor]) {
                        distance[neighbor] = newDistance;
                        priorityQueue.offer(new int[]{newDistance, neighbor});
                    }
                }
            }

            return distance;
        }
    }
}
