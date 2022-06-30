/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements P1.graph.Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    /**
     * 用L代表所有顶点；用Edge class代表边
     */

    // Representation invariant:
    /**
     * 顶点不能是null的；权重必须为重
     */

    // Safety from rep exposure:
    /**
     * 所有的rep都是private并且final的
     * 不能在代码中给出public的函数，不然某些字段可能会被更改
     * 严禁返回mutable rep，只能返回clone data
     */

    // constructor
    ConcreteEdgesGraph() {

    }

    // checkRep
    private void checkRep() {
        for (Edge<L> edge : edges)
            assert (edge != null);
        for (L vertex : vertices)
            assert (vertex != null);
    }

    @Override
    public boolean add(L vertex) {
        checkRep();
        if (vertices.contains(vertex))
            return false;
        vertices.add(vertex);
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("Negative weight");
        if (!vertices.contains(source))
            this.add(source);
        if (!vertices.contains(target))
            this.add(target);
        if (source.equals(target)) // 如果起点终点相同，则不设置边
            return 0;
        // 寻找相同边
        Iterator<Edge<L>> it = edges.iterator();
        while (it.hasNext()) {
            Edge<L> edge = it.next();
            if (edge.sameEdge(source, target)) {
                int finalEdgeWeight = edge.weight();
                it.remove();
                if (weight > 0) {
                    Edge<L> newEdge = new Edge<L>(source, target, weight);
                    edges.add(newEdge);
                }
                checkRep();
                return finalEdgeWeight;
            }
        }
        // 权重为0则删除边（这一步在寻找后执行）
        if (weight == 0)
            return 0;
        // 新的权重为正的边
        Edge<L> newEdge = new Edge<L>(source, target, weight);
        edges.add(newEdge);
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(L vertex) {
        if (!vertices.contains(vertex))
            return false;
        edges.removeIf(edge -> edge.source().equals(vertex) || edge.target().equals(vertex));
        vertices.remove(vertex);
        checkRep();
        return true;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (target.equals(edge.target())) {
                sources.put((L) edge.source(), edge.weight());
            }
        }
        checkRep();
        return sources;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (source.equals(edge.source())) {
                targets.put((L) edge.target(), edge.weight());
            }
        }
        checkRep();
        return targets;
    }

    // toString()
    @Override
    public String toString() {
        return "the number of vertices is  " + vertices.size() + " ,the number of edges is " + edges.size();
    }
}

/**
 * specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {

    // fields
    private final L source, target;
    private final int weight;

    // Abstraction function:
    /**
     * source: 边的起点
     * target: 边的终点
     * weight: 边的权重
     */
    // Representation invariant:
    /**
     * 边权重必须为正；起点终点不能相同
     */
    // Safety from rep exposure:
    /**
     * 起点、终点、边权重都不能被更改
     */

    Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // checkRep
    private void checkRep() {
        assert (weight > 0 && !source.equals(target));
    }

    // methods
    public L source() {
        return this.source;
    }

    public L target() {
        return this.target;
    }

    public int weight() {
        return this.weight;
    }

    public boolean sameEdge(L source, L target) {
        return source.equals(this.source) && target.equals(this.target);
    }

    // toString()
    @Override
    public String toString() {
        return "Source: " + this.source + "; Target: " + this.target + "; Weight: " + this.weight;
    }
}
