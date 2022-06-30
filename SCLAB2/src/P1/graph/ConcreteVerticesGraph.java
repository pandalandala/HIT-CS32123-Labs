/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements P1.graph.Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    /**
     * List<Vertex<L>>代表图中节点
     * Vertex类涵盖了在ThisVertex中出发或到达的边（入边和出边）
     */

    // Representation invariant:
    /**
     * 边权重不能为负
     * 入边与出边数量必须一致
     * ThisVertex不能为空
     */

    // Safety from rep exposure:
    /**
     * 外部不能获取vertex类
     * vertices是有如下性质的：private final immutable
     */

    // constructor
    ConcreteVerticesGraph() {

    }

    // checkRep
    private void checkRep() {
        for (Vertex<L> vertex : vertices) {
            assert (vertex.ThisVertex() != null);
            Map<L, Integer> sources = vertex.sources();
            for (Map.Entry<L, Integer> entry : sources.entrySet()) {
                assert (entry.getKey() != null);
                assert (entry.getValue() > 0);
            }
            Map<L, Integer> targets = vertex.targets();
            for (Map.Entry<L, Integer> entry : targets.entrySet()) {
                assert (entry.getKey() != null);
                assert (entry.getValue() > 0);
            }
        }
    }

    @Override
    public boolean add(L vertex) {
        for (Vertex<L> V : vertices) {
            if (vertex.equals(V.ThisVertex()))
                return false;
        }
        Vertex<L> newVertex = new Vertex<L>(vertex);
        vertices.add(newVertex);
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("Negative weight");
        if (!vertices.contains(source) || !vertices.contains(target)) {
            if (!vertices.contains(source))
                this.add(source);
            if (!vertices.contains(target))
                this.add(target);
        }
        if (source.equals(target))
            return 0;
        Vertex<L> from = null, to = null;
        for (Vertex<L> vertex : vertices) {
            if (vertex.ThisVertex().equals(source))
                from = vertex;
            if (vertex.ThisVertex().equals(target))
                to = vertex;
        }
        if (from == null || to == null)
            throw new NullPointerException("Inexistent vertex");
        int finalEdgeWeight;
        if (weight > 0) {
            finalEdgeWeight = from.setOutEdge(target, weight);
            finalEdgeWeight = to.setInEdge(source, weight);
        } else {
            finalEdgeWeight = from.removeOutEdge(target);
            finalEdgeWeight = to.removeInEdge(source);
        }
        checkRep();
        return finalEdgeWeight;
    }

    @Override
    public boolean remove(L vertex) {
        for (Vertex<L> THIS : vertices) {
            if (THIS.ThisVertex().equals(vertex)) {
                for (Vertex<L> v : vertices) {
                    if (THIS.sources().containsKey(v))
                        v.removeOutEdge(THIS.ThisVertex());
                    if (THIS.targets().containsKey(v))
                        v.removeInEdge(THIS.ThisVertex());
                }
                vertices.remove(THIS);
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }

    @Override
    public Set<L> vertices() {
        Set<L> VERTICES = new HashSet<>();
        for (Vertex<L> vertex : vertices)
            VERTICES.add((L) vertex.ThisVertex());
        checkRep();
        return VERTICES;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Vertex<L> vertex : vertices) {
            if (vertex.ThisVertex().equals(target)) {
                sources.putAll(vertex.sources());
                break;
            }
        }
        checkRep();
        return sources;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Vertex<L> vertex : vertices) {
            if (vertex.ThisVertex().equals(source)) {
                targets.putAll(vertex.targets());
                break;
            }
        }
        checkRep();
        return targets;
    }

    // toString()
    public String toString() {
        return "This graph has " + vertices.size() + " vertices";
    }
}

/**
 * specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 入边与出边数量必须一致
 * 边权重必须为正
 * 下面的Vertex类对ConcreteVerticesGraph中的rep来说是内部关系。
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */

class Vertex<L> {

    // fields
    private final L ThisVertex;
    private final Map<L, Integer> inEdges = new HashMap<>();
    private final Map<L, Integer> outEdges = new HashMap<>();

    // Abstraction function:
    // ThisVertex表示Vertex自身
    // 入边集合是一切指向该点的边
    // 出边集合是一切从该点发出的边

    // Representation invariant:
    // ThisVertex不能为空
    // 入边和出边的权重都应为正

    // Safety from rep exposure:
    // Edges不能被外部修改

    // constructor
    Vertex(L label) {
        ThisVertex = label;
    }

    // checkRep
    private void checkRep() {
        for (L key : inEdges.keySet())
            assert (inEdges.get(key) > 0);
        for (L key : outEdges.keySet())
            assert (outEdges.get(key) > 0);
    }

    // methods
    public L ThisVertex() {
        return ThisVertex;
    }

    public Map<L, Integer> sources() {
        Map<L, Integer> sources = new HashMap<>();
        sources.putAll(inEdges);
        return sources;
    }

    public Map<L, Integer> targets() {
        Map<L, Integer> targets = new HashMap<>();
        targets.putAll(outEdges);
        return targets;
    }

    public int setInEdge(L source, int weight) {
        if (weight <= 0)
            return 0;
        Iterator<L> iterator =inEdges.keySet().iterator();
        while (iterator.hasNext()) {
            L key = iterator.next();
            if (key.equals(source)) {
                int finalEdgeWeight = inEdges.get(key);
                iterator.remove();
                inEdges.put(source, weight);
                return finalEdgeWeight;
            }
        }
        inEdges.put(source, weight);
        checkRep();
        return 0;
    }

    public int setOutEdge(L target, int weight) {
        if (weight <= 0)
            return 0;
        Iterator<L> iterator =outEdges.keySet().iterator();
        while (iterator.hasNext()) {
            L key = iterator.next();
            if (key.equals(target)) {
                int finalEdgeWeight = outEdges.get(key);
                iterator.remove();
                outEdges.put(target, weight);
                return finalEdgeWeight;
            }
        }
        outEdges.put(target, weight);
        checkRep();
        return 0;
    }

    public int removeInEdge(L source) {
        if (!inEdges.containsKey(source)) {
            return 0;
        }
        int finalEdgeWeight = inEdges.get(source);
        inEdges.remove(source);
        checkRep();
        return finalEdgeWeight;
    }

    public int removeOutEdge(L target) {
        if (!outEdges.containsKey(target)) {
            return 0;
        }
        int finalEdgeWeight = outEdges.get(target);
        outEdges.remove(target);
        checkRep();
        return finalEdgeWeight;
    }

    // toString()
    @Override
    public String toString() {
        return ThisVertex.toString();
    }
}
