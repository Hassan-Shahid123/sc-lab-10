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
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   The graph is represented as a list of Vertex objects, where each Vertex
    //   stores its outgoing edges in a map target→weight.
    //
    // Representation invariant:
    //   - No duplicate vertex names.
    //   - All edge weights > 0.
    //
    // Safety from rep exposure:
    //   - vertices is private and final.
    //   - Vertex objects are private to the package.
    //   - Returned sets/maps are defensive copies.

    public ConcreteVerticesGraph() {
        checkRep();
    }

    private void checkRep() {
        Set<String> names = new HashSet<>();
        for (Vertex v : vertices) {
            if (!names.add(v.getName())) throw new RuntimeException("Duplicate vertex");
            v.checkRep();
        }
    }

    @Override public boolean add(String vertex) {
        for (Vertex v : vertices)
            if (v.getName().equals(vertex))
                return false;
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override public int set(String source, String target, int weight) {
        add(source);
        add(target);

        Vertex s = getVertex(source);
        int previous = s.setEdge(target, weight);
        checkRep();
        return previous;
    }

    @Override public boolean remove(String vertex) {
        Vertex toRemove = getVertex(vertex);
        if (toRemove == null) return false;

        vertices.remove(toRemove);
        for (Vertex v : vertices) {
            v.setEdge(vertex, 0); // remove outgoing edges to this vertex
        }
        checkRep();
        return true;
    }

    @Override public Set<String> vertices() {
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices)
            result.add(v.getName());
        return result;
    }

    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Vertex v : vertices) {
            int w = v.getWeight(target);
            if (w > 0) result.put(v.getName(), w);
        }
        return result;
    }

    @Override public Map<String, Integer> targets(String source) {
        Vertex v = getVertex(source);
        if (v == null) return new HashMap<>();
        return new HashMap<>(v.getEdges());
    }

    private Vertex getVertex(String name) {
        for (Vertex v : vertices)
            if (v.getName().equals(name))
                return v;
        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Mutable.
 * Internal to the rep of ConcreteVerticesGraph.
 */
class Vertex {

    private final String name;
    private final Map<String, Integer> edges = new HashMap<>();

    // Abstraction function:
    //   Represents a node with outgoing edges stored in edges map.
    //
    // Representation invariant:
    //   - All weights > 0.
    //
    // Safety from rep exposure:
    //   - name and edges are private.
    //   - Getters return defensive copies.

    public Vertex(String name) {
        this.name = name;
        checkRep();
    }

    public void checkRep() {
        for (int w : edges.values())
            if (w <= 0)
                throw new RuntimeException("Invalid weight");
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges);
    }

    public int getWeight(String target) {
        return edges.getOrDefault(target, 0);
    }

    public int setEdge(String target, int weight) {
        int prev = edges.getOrDefault(target, 0);
        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }
        checkRep();
        return prev;
    }

    @Override public String toString() {
        return name + " -> " + edges.toString();
    }
}
