package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Person;
import auxiliary.Voter;
import poll.Election;
import vote.RealNameVote;
import vote.VoteItem;

public class ElectionApp {

	public static void main(String[] args) {
		Election test = new Election();
		List<Person> list2 = new LinkedList<Person>();
		Person p2 = new Person("asd", 15);list2.add(p2);
		Person p3 = new Person("zxc", 25);list2.add(p3);
		Person p4 = new Person("qwe", 36);list2.add(p4);

		test.addCandidates(list2);
		test.setInfo("election", 2);

		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 1.0);
		voters3.put(voter4, 1.0);
		test.addVoters(voters3);

		VoteItem<Person> i31 = new VoteItem<Person>(p2, "支持");
		VoteItem<Person> i32 = new VoteItem<Person>(p3, "支持");
		VoteItem<Person> i33 = new VoteItem<Person>(p4, "弃权");
		VoteItem<Person> i41 = new VoteItem<Person>(p2, "弃权");
		VoteItem<Person> i42 = new VoteItem<Person>(p3, "支持");
		VoteItem<Person> i43 = new VoteItem<Person>(p4, "反对");

		Set<VoteItem<Person>> voteItems3 = new HashSet<VoteItem<Person>>();
		voteItems3.add(i31);
		voteItems3.add(i32);
		voteItems3.add(i33);
		Set<VoteItem<Person>> voteItems4 = new HashSet<VoteItem<Person>>();
		voteItems4.add(i41);
		voteItems4.add(i42);
		voteItems4.add(i43);

		RealNameVote<Person> votes3 = new RealNameVote<Person>(voter3, voteItems3);
		RealNameVote<Person> votes4 = new RealNameVote<Person>(voter4, voteItems4);

		test.VoterVote(voter3, votes3);
		test.addVote(votes3);
		test.VoterVote(voter4, votes4);
		test.addVote(votes4);

		test.statistics();
		test.selection();
		System.out.println(test.result());
	}

}
