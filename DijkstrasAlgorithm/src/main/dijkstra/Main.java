/**
 * @file Main.java
 * @author Braiden Gole
 * @version 1.0
 * @date 2025-06-02
 *
 * @brief Entry point for testing Dijkstra's shortest path algorithm.
 *
 * This file defines the `main` method that constructs a sample directed graph,
 * applies Dijkstra's algorithm from source node 0, and prints the shortest
 * distances to all other nodes in the graph.
 *
 * The graph is built using the `Graph` class from the `Dijkstra` utility.
 * Edges are added with non-negative weights. The output reflects the
 * shortest distance from the source to each destination node.
 *
 * Usage:
 * - Run this program to verify that the Dijkstra algorithm works as expected.
 * - Modify the graph or starting node to test different scenarios.
 */
package dijkstra;

public class Main {
    public static void main(String[] args) {
        Dijkstra.Graph graph = new Dijkstra.Graph(6);

        // Add edges.
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 1);

        // Execute Dijkstra's algorithm.
        int[] distances = graph.dijkstra(0);

        System.out.println("Shortest distances from node 0:");

        // Show the shortest distances.
        for (int index = 0; index < distances.length; index++) {
            System.out.printf("To node %d: %d\n", index, distances[index]);
        }
    }
}