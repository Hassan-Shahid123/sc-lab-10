package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 */
public abstract class GraphInstanceTest {

    /*
     * ============================
     *       TESTING STRATEGY
     * ============================
     *
     * vertices():
     *   - initially empty
     *   - after adding 1 vertex
     *   - after adding multiple vertices
     *   - after removing vertices
     *
     * add(vertex):
     *   - add new vertex → true
     *   - add duplicate → false
     *
     * remove(vertex):
     *   - remove existing vertex → true
     *   - remove non-existing → false
     *   - remove vertex that has incoming and outgoing edges → ensure edges removed
     *
     * set(source,target,weight):
     *   Cases:
     *   - add new edge (previous = 0)
     *   - update existing edge (previous > 0)
     *   - remove existing edge (weight = 0)
     *   - remove non-existing edge (weight = 0 → return 0)
     *   - automatically add missing source/target vertices
     *
     * sources(target):
     *   - no incoming edges
     *   - one incoming edge
     *   - multiple incoming edges
     *
     * targets(source):
     *   - no outgoing edges
     *   - one outgoing edge
     *   - multiple outgoing edges
     */

    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals(Collections.emptySet(), emptyInstance().vertices());
    }

    // ======== add() tests ========

    @Test
    public void testAddSingleVertex() {
        Graph<String> g = emptyInstance();
        assertTrue(g.add("A"));
        assertEquals(Set.of("A"), g.vertices());
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertFalse(g.add("A"));   // duplicate
        assertEquals(Set.of("A"), g.vertices());
    }

    // ======== remove() tests ========

    @Test
    public void testRemoveExistingVertex() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertTrue(g.remove("A"));
        assertEquals(Collections.emptySet(), g.vertices());
    }

    @Test
    public void testRemoveNonExistingVertex() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertFalse(g.remove("B"));
        assertEquals(Set.of("A"), g.vertices());
    }

    @Test
    public void testRemoveVertexWithEdges() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        g.set("C", "A", 7);

        assertTrue(g.remove("A"));

        assertEquals(Set.of("B", "C"), g.vertices());
        assertEquals(Collections.emptyMap(), g.sources("B"));
        assertEquals(Collections.emptyMap(), g.targets("C"));
    }

    // ======== set() tests ========

    @Test
    public void testSetAddNewEdge() {
        Graph<String> g = emptyInstance();
        assertEquals(0, g.set("A", "B", 10));  // new edge
        assertEquals(Set.of("A", "B"), g.vertices());
        assertEquals(Map.of("B", 10), g.targets("A"));
    }

    @Test
    public void testSetUpdateEdge() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        assertEquals(5, g.set("A", "B", 10));  // update
        assertEquals(Map.of("B", 10), g.targets("A"));
    }

    @Test
    public void testSetRemoveExistingEdge() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        assertEquals(5, g.set("A", "B", 0)); // remove edge
        assertEquals(Collections.emptyMap(), g.targets("A"));
    }

    @Test
    public void testSetRemoveNonExistingEdge() {
        Graph<String> g = emptyInstance();
        assertEquals(0, g.set("A", "B", 0)); // no edge existed
        assertEquals(Set.of("A", "B"), g.vertices());
        assertEquals(Collections.emptyMap(), g.targets("A"));
    }

    // ======== vertices() tests ========

    @Test
    public void testVerticesAfterMultipleAdds() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");
        g.add("C");
        assertEquals(Set.of("A", "B", "C"), g.vertices());
    }

    // ======== sources() tests ========

    @Test
    public void testSourcesNone() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertEquals(Collections.emptyMap(), g.sources("A"));
    }

    @Test
    public void testSourcesSingle() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        assertEquals(Map.of("A", 5), g.sources("B"));
    }

    @Test
    public void testSourcesMultiple() {
        Graph<String> g = emptyInstance();
        g.set("A", "C", 5);
        g.set("B", "C", 10);
        assertEquals(Map.of("A", 5, "B", 10), g.sources("C"));
    }

    // ======== targets() tests ========

    @Test
    public void testTargetsNone() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertEquals(Collections.emptyMap(), g.targets("A"));
    }

    @Test
    public void testTargetsSingle() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        assertEquals(Map.of("B", 5), g.targets("A"));
    }

    @Test
    public void testTargetsMultiple() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 5);
        g.set("A", "C", 10);
        assertEquals(Map.of("B", 5, "C", 10), g.targets("A"));
    }
}
