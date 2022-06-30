package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {

	/**
	 * Basic Network Test
	 */
	@Test
	public void Test1() {
		final P2.FriendshipGraph graph = new P2.FriendshipGraph();

		final P2.Person rachel = new P2.Person("Rachel");
		final P2.Person ross = new P2.Person("Ross");
		final P2.Person ben = new P2.Person("Ben");
		final P2.Person kramer = new P2.Person("Kramer");

		assertEquals(true, graph.addVertex(rachel));
		assertEquals(true, graph.addVertex(ross));
		assertEquals(true, graph.addVertex(ben));
		assertEquals(true, graph.addVertex(kramer));

		assertEquals(0, graph.addEdge(rachel, ross));
		assertEquals(1, graph.addEdge(ross, rachel));
		assertEquals(0, graph.addEdge(ross, ben));
		assertEquals(1, graph.addEdge(ben, ross));

		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
	}

	/**
	 * Further Test
	 */
	@Test
	public void Test2() {
		final P2.FriendshipGraph graph = new P2.FriendshipGraph();

		final P2.Person a = new P2.Person("A");
		final P2.Person b = new P2.Person("B");
		final P2.Person c = new P2.Person("C");
		final P2.Person d = new P2.Person("D");
		final P2.Person e = new P2.Person("E");
		final P2.Person f = new P2.Person("F");
		final P2.Person g = new P2.Person("G");
		final P2.Person h = new P2.Person("H");
		final P2.Person i = new P2.Person("I");
		final P2.Person j = new P2.Person("J");

		assertEquals(true, graph.addVertex(a));
		assertEquals(true, graph.addVertex(b));
		assertEquals(true, graph.addVertex(c));
		assertEquals(true, graph.addVertex(d));
		assertEquals(true, graph.addVertex(e));
		assertEquals(true, graph.addVertex(f));
		assertEquals(true, graph.addVertex(g));
		assertEquals(true, graph.addVertex(h));
		assertEquals(true, graph.addVertex(i));
		assertEquals(true, graph.addVertex(j));

		assertEquals(0, graph.addEdge(a, b));
		assertEquals(0, graph.addEdge(a, d));
		assertEquals(0, graph.addEdge(b, d));
		assertEquals(0, graph.addEdge(c, d));
		assertEquals(0, graph.addEdge(d, e));
		assertEquals(0, graph.addEdge(c, f));
		assertEquals(0, graph.addEdge(e, g));
		assertEquals(0, graph.addEdge(f, g));
		assertEquals(0, graph.addEdge(h, i));
		assertEquals(0, graph.addEdge(i, j));

		assertEquals(2, graph.getDistance(a, e));
		assertEquals(1, graph.getDistance(a, d));
		assertEquals(3, graph.getDistance(a, g));
		assertEquals(3, graph.getDistance(b, f));
		assertEquals(2, graph.getDistance(d, f));
		assertEquals(2, graph.getDistance(h, j));
		assertEquals(0, graph.getDistance(i, i));
		assertEquals(-1, graph.getDistance(d, j));
		assertEquals(-1, graph.getDistance(c, i));
		assertEquals(-1, graph.getDistance(f, h));
	}

}
