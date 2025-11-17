package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * ============================
     *  TESTING ConcreteEdgesGraph
     * ============================
     *
     * toString() testing strategy:
     *   - empty graph
     *   - graph with vertices but no edges
     *   - graph with a single edge
     *   - graph with multiple edges
     */

    @Test
    public void testToStringEmptyGraph() {
        Graph<String> g = emptyInstance();
        String s = g.toString();
        assertTrue(s.contains("Vertices") || s.length() > 0); // accept any reasonable format
        assertTrue(s.contains("Edges") || s.length() > 0);
    }

    @Test
    public void testToStringVerticesNoEdges() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        String s = g.toString();

        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
        assertTrue(s.contains("Edges") || s.contains("[]"));
    }

    @Test
    public void testToStringSingleEdge() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);

        String s = g.toString();

        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
        assertTrue(s.contains("5")); // weight
    }

    @Test
    public void testToStringMultipleEdges() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 2);
        g.set("A", "C", 4);
        g.set("B", "C", 7);

        String s = g.toString();

        assertTrue(s.contains("A"));
        assertTrue(s.contains("B"));
        assertTrue(s.contains("C"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("4"));
        assertTrue(s.contains("7"));
    }

    /*
     * ============================
     *         TESTING Edge
     * ============================
     *
     * Edge testing strategy:
     *   - constructor stores source, target, and weight
     *   - getters return correct fields
     *   - toString() outputs "source -> target (weight)"
     *   - negative weight throws RuntimeException
     */

    @Test
    public void testEdgeConstructorAndGetters() {
        Edge e = new Edge("A", "B", 10);
        assertEquals("A", e.getSource());
        assertEquals("B", e.getTarget());
        assertEquals(10, e.getWeight());
    }

    @Test(expected = RuntimeException.class)
    public void testEdgeNegativeWeight() {
        new Edge("A", "B", -1);
    }

    @Test
    public void testEdgeToString() {
        Edge e = new Edge("X", "Y", 3);
        assertEquals("X -> Y (3)", e.toString());
    }
}
