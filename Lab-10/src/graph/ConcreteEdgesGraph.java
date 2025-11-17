/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //   Graph is represented by a set of vertex names and a list of directed edges.
    //
    // Representation invariant:
    //   - All edges have positive weights.
    //   - Every edge's source and target are in vertices.
    //
    // Safety from rep exposure:
    //   - vertices and edges are private and final.
    //   - Returned sets/maps are defensive copies.

    public ConcreteEdgesGraph() {
        checkRep();
    }

    private void checkRep() {
        for (Edge e : edges) {
            if (!vertices.contains(e.getSource())) throw new RuntimeException("Missing source");
            if (!vertices.contains(e.getTarget())) throw new RuntimeException("Missing target");
            e.checkRep();
        }
    }

    @Override public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override public int set(String source, String target, int weight) {
        add(source);
        add(target);

        int previous = 0;
        Edge toRemove = null;

        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                previous = e.getWeight();
                toRemove = e;
                break;
            }
        }

        if (toRemove != null) edges.remove(toRemove);
        if (weight > 0) edges.add(new Edge(source, target, weight));

        checkRep();
        return previous;
    }

    @Override public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) return false;

        vertices.remove(vertex);
        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));

        checkRep();
        return true;
    }

    @Override public Set<String> vertices() {
        return new HashSet<>(vertices);
    }

    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges)
            if (e.getTarget().equals(target))
                result.put(e.getSource(), e.getWeight());
        return result;
    }

    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges)
            if (e.getSource().equals(source))
                result.put(e.getTarget(), e.getWeight());
        return result;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge e : edges) sb.append("  ").append(e).append("\n");
        return sb.toString();
    }
}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 */
class Edge {

    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    //   Represents a directed edge source -> target with positive weight.
    //
    // Representation invariant:
    //   - weight > 0
    //
    // Safety from rep exposure:
    //   - fields are private and final.

    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    public void checkRep() {
        if (weight <= 0) throw new RuntimeException("Invalid weight");
    }

    public String getSource() { return source; }
    public String getTarget() { return target; }
    public int getWeight() { return weight; }

    @Override public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
