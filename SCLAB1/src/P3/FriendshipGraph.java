package P3;

import P3.Person;

import java.util.*;

public class FriendshipGraph {
	private final List<Person> people = new ArrayList<>(); // vertex set
	private final List<List<Person>> connections = new ArrayList<>(); // graph list

	/**
	 *
	 * @param person (target)
	 * @param people (range)
	 * @return true represents already there；false represents not there
	 */
	private boolean judgeExist(Person person, List<Person> people) {
		for (Person value : people) {
			// give false if already there
			if (value == person) {
				return true;
			}
		}
		return false;
	}

	/**
	 * print all of the network
	 */
	private void showMembers() {
		for (Person person: people) {
			// go through all
			System.out.print(person.getName()+"\t");
		}
		System.out.println();
	}

	/**
	 * print all relations
	 */
	private void showConnections() {
		String currentname;
		for (List<Person> connection: connections) {
			// 开go through all relations
			Iterator<Person> person_iter = connection.iterator();
			Person person = person_iter.next();
			currentname = person.getName();
			for (Person connect: connection) {
				System.out.print(currentname+"->"+connect.getName()+"\t");
			}
		}
		System.out.println();
	}

	/**
	 * supporting constructing oriented gragh/ receiving two Person/ constructing edges & add to FriendshipGraph
	 * @param person_fir starting Person
	 * @param person_sec target Person
	 * @return add successfully?
	 */
	public boolean addEdge(Person person_fir, Person person_sec) {
		if (person_fir == person_sec) {
			System.out.println("cannot point to self");
			return false;
		}
		for (List<Person> connection: connections) {
			// go through all relations
			Iterator<Person> person_iter = connection.iterator();
			Person person = person_iter.next();
			// if the first Person is the Person to be looked for, then enter and look out, otherwise skip
			if (person == person_fir) {
				while (person_iter.hasNext()) {
					// search down
					person = person_iter.next();
					if (person == person_sec) {
						System.out.println("relations: " + person_fir.getName() + "->" + person_sec.getName() +"already there!");
						return false;
					}
				}
				connection.add(person_sec);
				return true;
			}
		}
		return true;
	}

	/**
	 * receive a Person class and add it to the node set of the graph
	 * @param person new one
	 * @return add successfully?
	 */
	public boolean addVertex(Person person) {
		// already there or not
		if (judgeExist(person, people)) {
			System.out.println(person.getName() + " there!");
			return false;
		}
		// otherwise it's added to the chained storage graph
		else {
			people.add(person);
			List<Person> new_connection = new ArrayList<>();
			new_connection.add(person);
			connections.add(new_connection);
			System.out.println(person.getName()+" there!");
			return true;
		}
	}

	/**
	 * Get social distancing between two people and return -1 if two people are not connected
	 * If there is a link between two people, the min of the distance between two people is returned
	 * equal then return 0
	 * @param person_fir person_first
	 * @param person_sec person_second
	 * @return the distance between person_first and person_second
	 */
	public int getDistance(Person person_fir, Person person_sec) {
		int dis = 0;
		Person current_person; // search
		List<Person> checked = new ArrayList<>(); // ring or not
		Queue<Person> main = new LinkedList<>(); // main queue
		Queue<Person> help = new LinkedList<>(); // help queue

		if (person_fir == person_sec) {
			return 0;
		}
		// BFS
		main.add(person_fir); // init
		// BFS start
		while (!main.isEmpty()) {
			current_person = main.poll();
			// skip to avoid ring
			if (!judgeExist(current_person, checked)) {
				checked.add(current_person);
				// go through all relations
				for (List<Person> connection: connections) {
					Iterator<Person> person_iter = connection.iterator();
					Person person = person_iter.next();
					if (person == current_person) {
						while (person_iter.hasNext()) {
							person = person_iter.next();
							help.offer(person);
							// return
							if (person == person_sec) {
								dis++;
								return dis;
							}
						}
					}
				}
			}
			// main=null;help!=null then finish
			if (main.isEmpty() && !help.isEmpty()) {
				dis++;
				// update main queue
				while (!help.isEmpty()) {
					main.offer(help.poll());
				}
			}
		}
		return -1;
	}

	/**
	 * This is the main() function, to show whether it can work
	 * @param args args
	 */
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();

		Person rachel = new Person("Rachel");
		Person ross = new Person ("Ross");
		Person ben = new Person ("Ben");
		Person kramer = new Person("Kramer");

		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.showMembers();

		graph.addEdge(rachel, ross);
		graph.showConnections();
		graph.addEdge(ross, rachel);
		graph.showConnections();
		graph.addEdge(ross, ben);
		graph.showConnections();
		graph.addEdge(ben, ross);
		graph.showConnections();

		System.out.println(graph.getDistance(rachel, ross)); // print 1
		System.out.println(graph.getDistance(rachel, ben)); // print 2
		System.out.println(graph.getDistance(rachel, rachel)); // print 0
		System.out.println(graph.getDistance(rachel, kramer)); // print -1
	}
}