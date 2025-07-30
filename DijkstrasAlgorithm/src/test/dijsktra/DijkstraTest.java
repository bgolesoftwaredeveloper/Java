/**
 * File             :   DijkstraTest.java
 * Project          :   Dijkstra's Algorithm - Unit Tests
 * Programmer       :   Braiden Gole
 * First Version    :   2025-07-30
 * Description      :   This file contains unit tests for the Dijkstra shortest path algorithm.
 *                      The tests verify correct shortest path calculations, input validation,
 *                      graph construction, and handling of disconnected nodes.
 */
package dijsktra;

import dijkstra.Dijkstra;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DijkstraTest {
    /**
     * Test Method      :   TestShortestPathsFromSource
     * Description      :   Verifies that the Dijkstra algorithm correctly computes shortest
     *                      distances from the source node to all other nodes.
     */
    @Test
    public void TestShortestPathsFromSource() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(5);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 4, 3);

        // Act
        int[] distances = graph.dijkstra(0);

        // Assert
        assertEquals(0, distances[0]);   // Source to itself
        assertEquals(2, distances[1]);   // 0 -> 1
        assertEquals(3, distances[2]);   // 0 -> 1 -> 2
        assertEquals(9, distances[3]);   // 0 -> 1 -> 3
        assertEquals(6, distances[4]);   // 0 -> 1 -> 2 -> 4
    }

    /**
     * Test Method      :   TestDisconnectedNode
     * Description      :   Ensures Dijkstra correctly handles a node that cannot be reached
     *                      from the starting node.
     */
    @Test
    public void TestDisconnectedNode() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(4);
        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 2, 10);

        // Node 3 is disconnected

        // Act
        int[] distances = graph.dijkstra(0);

        // Assert
        assertEquals(Integer.MAX_VALUE, distances[3],
            "Disconnected node should have distance of Integer.MAX_VALUE");
    }

    /**
     * Test Method      :   TestInvalidVertexInput
     * Description      :   Verifies that constructing a graph with non-positive vertices
     *                      throws an exception.
     */
    @Test
    public void TestInvalidVertexInput() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Dijkstra.Graph(0);
        }, "Graph must not allow zero or negative vertices.");
    }

    /**
     * Test Method      :   TestNegativeEdgeWeight
     * Description      :   Ensures that adding a negative-weight edge throws an exception.
     */
    @Test
    public void TestNegativeEdgeWeight() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(3);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 1, -1);
        }, "Negative weights should not be allowed.");
    }

    /**
     * Test Method      :   TestOutOfBoundsEdge
     * Description      :   Verifies that adding an edge with invalid indices throws an error.
     */
    @Test
    public void TestOutOfBoundsEdge() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(3);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.addEdge(0, 5, 10);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.addEdge(-1, 2, 10);
        });
    }

    /**
     * Test Method      :   TestStartNodeOutOfBounds
     * Description      :   Ensures that calling Dijkstra with an invalid starting node throws an error.
     */
    @Test
    public void TestStartNodeOutOfBounds() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(3);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            graph.dijkstra(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            graph.dijkstra(3);
        });
    }

    /**
     * Test Method      :   TestSingleVertexGraph
     * Description      :   Ensures Dijkstra returns a distance of 0 for a graph with one node.
     */
    @Test
    public void TestSingleVertexGraph() {
        // Arrange
        Dijkstra.Graph graph = new Dijkstra.Graph(1);

        // Act
        int[] distances = graph.dijkstra(0);

        // Assert
        assertEquals(1, distances.length);
        assertEquals(0, distances[0], "Single node must have distance 0 to itself.");
    }
}
