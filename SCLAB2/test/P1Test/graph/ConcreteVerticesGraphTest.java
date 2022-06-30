/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 *
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends P1.graph.GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public P1.graph.Graph<String> emptyInstance() {
        return new P1.graph.ConcreteVerticesGraph<String>();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Testing strategy for ConcreteVerticesGraph.toString()
    /**
     * 测试返回的字符串是否是我们期望的
     */

    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString() {
        P1.graph.Graph<String> graph = emptyInstance();
        assertTrue(graph.add("a"));
        assertTrue(graph.add("b"));
        assertTrue(graph.add("c"));
        assertEquals(0, graph.set("a", "b", 1));
        assertEquals("This graph has 3 vertices", graph.toString());
    }

    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex
    /**
     * vertex：相同/不同/空
     * 权重：正/负/0
     */

    // tests for operations of Vertex
    @Test
    public void testVertices() {
        P1.graph.Vertex<String> vertex1 = new P1.graph.Vertex<>("first");
        P1.graph.Vertex<String> vertex2 = new P1.graph.Vertex<>("second");
        try {
            vertex1.setInEdge("second", -1);
            fail("not catch weight<=0 error");
        } catch (AssertionError error) {
        }
        try {
            vertex1.setInEdge("first", 0);
            fail("not catch weight<=0 error");
        } catch (AssertionError error) {
        }
        vertex1.setInEdge("second", 1);
        vertex2.setOutEdge("first", 2);
        HashMap<String, Integer> result1 = new HashMap<>(), result2 = new HashMap<>();
        result1.put("second", 1);
        result2.put("first", 2);
        assertEquals(result1, vertex1.sources());
        assertEquals(new HashMap<>(), vertex1.targets());
        assertEquals(new HashMap<>(), vertex2.sources());
        assertEquals(result2, vertex2.targets());
    }
}
