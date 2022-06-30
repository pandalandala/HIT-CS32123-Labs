/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 *
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends P1.graph.GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public P1.graph.Graph<String> emptyInstance() {
        return new P1.graph.ConcreteEdgesGraph<String>();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString()
    /**
     * 测试是否返回预期的字符串
     */

    // tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString() {
        P1.graph.Graph<String> emptyInstance = emptyInstance();
        assertEquals(true, emptyInstance.add("1"));
        assertEquals(true, emptyInstance.add("2"));
        assertEquals(true, emptyInstance.add("3"));
        assertEquals(true, emptyInstance.add("4"));
        assertEquals(0, emptyInstance.set("1", "2", 2));
        assertEquals(0, emptyInstance.set("1", "3", 3));
        assertEquals(0, emptyInstance.set("1", "4", 4));
        assertEquals("the number of vertices is  4 ,the number of edges is 3", emptyInstance.toString());
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    /**
     * vertex：相同/不同/空
     * 权重：正/负/0
     */

    // tests for operations of Edge
    @SuppressWarnings("unused")
    @Test
    public void testStructure() {
        // vertex：相同
        // weight为负
        try {
            P1.graph.Edge<String> e1 = new P1.graph.Edge<>("first", "first", -1);
            fail("not catch weight<=0 error");
        } catch (AssertionError error) {
        }
        // vertex：不同
        // weight为0
        try {
            P1.graph.Edge<String> e2 = new P1.graph.Edge<>("first", "second", 0);
            fail("not catch weight<=0 error");
        } catch (AssertionError error) {
        }
        // weight为正
        P1.graph.Edge<String> e3 = new P1.graph.Edge<>("first", "third", 1);
        assertEquals("first", e3.source());
        assertEquals("third", e3.target());
        assertEquals(1, e3.weight());
    }
}
