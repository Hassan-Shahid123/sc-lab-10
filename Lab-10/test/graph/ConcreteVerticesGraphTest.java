package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;

/**
 * Tests for ConcreteVerticesGraph.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * ConcreteVerticesGraph.toString() tests
     */

    @Test
    public void testToStringEmptyGraph() {
        ConcreteVerticesGraph g = new ConcreteVerticesGraph();
        assertEquals("", g.toString());
    }

    @Test
    public void testToStringVerticesOnly() {
        ConcreteVerticesGraph g = new ConcreteVerticesGraph();
        g.add("A");
        g.add("B");
        String s = g.toString();
        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
    }

    @Test
    public void testToStringWithEdges() {
        ConcreteVerticesGraph g = new ConcreteVerticesGraph();
        g.set("A", "B", 5);
        g.set("B", "C", 10);
        String s = g.toString();
        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
        assertTrue(s.contains("C"));
        assertTrue(s.contains("5"));
        assertTrue(s.contains("10"));
    }

    /*
     * Vertex class tests using available API
     */

    @Test
    public void testVertexBasic() {
        Vertex v = new Vertex("A");
        assertEquals("A", v.getName());
        assertTrue(v.getEdges().isEmpty());
        assertEquals(0, v.getWeight("B"));
    }

    @Test
    public void testVertexSetEdge() {
        Vertex v = new Vertex("A");
        int prev = v.setEdge("B", 5);
        assertEquals(0, prev); // no previous edge
        assertEquals(5, v.getWeight("B"));
        assertEquals(Map.of("B", 5), v.getEdges());
    }

    @Test
    public void testVertexUpdateEdge() {
        Vertex v = new Vertex("A");
        v.setEdge("B", 5);
        int prev = v.setEdge("B", 10);
        assertEquals(5, prev); // previous weight
        assertEquals(10, v.getWeight("B"));
    }

    @Test
    public void testVertexRemoveEdge() {
        Vertex v = new Vertex("A");
        v.setEdge("B", 5);
        int prev = v.setEdge("B", 0); // remove
        assertEquals(5, prev);
        assertTrue(v.getEdges().isEmpty());
        assertEquals(0, v.getWeight("B"));
    }
}
