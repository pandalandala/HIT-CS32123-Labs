package P2;

import P1.graph.*;

import java.util.*;
import java.util.LinkedList;
import java.util.Queue;

public class FriendshipGraph {
	private final P1.graph.Graph<P2.Person> graph = P1.graph.Graph.empty();

	/**
	 * @param newPerson：添加Person
	 * @return 如果添加成功返回true
	 */

	public boolean addVertex(P2.Person newPerson) {
		return graph.add(newPerson);
	}

	/**
	 * addEdge：添加双向边
	 *
	 * @param a,b 两者以边连接
	 * @param A,B 两人所分别代表的节点
	 */

	public int addEdge(P2.Person a, P2.Person b) {
		int lastEdgeWeight;
		lastEdgeWeight = graph.set(a, b, 1);
		lastEdgeWeight = graph.set(b, a, 1);
		return lastEdgeWeight;
	}

	/**
	 * 获取两者距离
	 *
	 * @param sta：路径起始的一方
	 * @param end：路径终止的一方
	 * @return 两人之间的距离（如果两人未被连接，则返回-1）
	 */

	public int getDistance(P2.Person sta, P2.Person end) {
		if (sta.equals(end))
			return 0;
		Map<P2.Person, Integer> dis = new HashMap<>();
		Map<P2.Person, Boolean> vis = new HashMap<>();
		Queue<P2.Person> qu = new LinkedList<P2.Person>();
		Set<P2.Person> persons = graph.vertices();
		for (P2.Person person : persons) {
			dis.put(person, 0);
			vis.put(person, false);
		}
		vis.remove(sta);
		vis.put(sta, true);
		for (qu.offer(sta); !qu.isEmpty();) {
			P2.Person person = qu.poll();
			for (Map.Entry<P2.Person, Integer> edge : graph.targets(person).entrySet()) {
				P2.Person target = edge.getKey();
				if (!vis.get(target)) {
					qu.offer(target);
					vis.remove(target);
					vis.put(target, true);
					dis.remove(target);
					dis.put(target, dis.get(person) + 1);
					if (target.equals(end))
						return dis.get(target);
				}
			}
		}
		return -1;
	}
}