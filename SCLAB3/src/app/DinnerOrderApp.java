package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Dish;
import auxiliary.Voter;
import poll.DinnerOrder;
import vote.RealNameVote;
import vote.VoteItem;

public class DinnerOrderApp {

	public static void main(String[] args) throws Exception {
		DinnerOrder test = new DinnerOrder();
		List<Dish> list2 = new LinkedList<Dish>();
		Dish p2 = new Dish("a", 2);list2.add(p2);
		Dish p3 = new Dish("b", 3);list2.add(p3);
		Dish p4 = new Dish("c", 4);list2.add(p4);
		Dish p5 = new Dish("d", 5);list2.add(p5);
		Dish p6 = new Dish("f", 6);list2.add(p6);
		Dish p7 = new Dish("e", 7);list2.add(p7);
		Dish p8 = new Dish("g", 8);list2.add(p8);

		test.addCandidates(list2);
		test.setInfo("breakfast", 2, 8, 2);

		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 0.7);
		voters3.put(voter4, 0.3);
		test.addVoters(voters3);

		VoteItem<Dish> i31 = new VoteItem<Dish>(p2, "Ï²»¶");
		VoteItem<Dish> i32 = new VoteItem<Dish>(p3, "Ï²»¶");
		VoteItem<Dish> i33 = new VoteItem<Dish>(p4, "²»Ï²»¶");
		VoteItem<Dish> i34 = new VoteItem<Dish>(p5, "Ï²»¶");
		VoteItem<Dish> i35 = new VoteItem<Dish>(p6, "Ï²»¶");
		VoteItem<Dish> i36 = new VoteItem<Dish>(p7, "Ï²»¶");
		VoteItem<Dish> i37 = new VoteItem<Dish>(p8, "²»Ï²»¶");
		VoteItem<Dish> i41 = new VoteItem<Dish>(p2, "Ï²»¶");
		VoteItem<Dish> i42 = new VoteItem<Dish>(p3, "ÎÞËùÎ½");
		VoteItem<Dish> i43 = new VoteItem<Dish>(p4, "ÎÞËùÎ½");
		VoteItem<Dish> i44 = new VoteItem<Dish>(p5, "Ï²»¶");
		VoteItem<Dish> i45 = new VoteItem<Dish>(p6, "ÎÞËùÎ½");
		VoteItem<Dish> i46 = new VoteItem<Dish>(p7, "ÎÞËùÎ½");
		VoteItem<Dish> i47 = new VoteItem<Dish>(p8, "ÎÞËùÎ½");

		Set<VoteItem<Dish>> voteItems3 = new HashSet<VoteItem<Dish>>();
		voteItems3.add(i31);
		voteItems3.add(i32);
		voteItems3.add(i33);
		voteItems3.add(i34);
		voteItems3.add(i35);
		voteItems3.add(i36);
		voteItems3.add(i37);
		Set<VoteItem<Dish>> voteItems4 = new HashSet<VoteItem<Dish>>();
		voteItems4.add(i41);
		voteItems4.add(i42);
		voteItems4.add(i43);
		voteItems4.add(i44);
		voteItems4.add(i45);
		voteItems4.add(i46);
		voteItems4.add(i47);

		RealNameVote<Dish> votes3 = new RealNameVote<Dish>(voter3, voteItems3);
		RealNameVote<Dish> votes4 = new RealNameVote<Dish>(voter4, voteItems4);

		test.VoterVote(votes3);
		test.addVote(votes3);
		test.VoterVote(votes4);
		test.addVote(votes4);

		test.statistics();
		test.selection();
		System.out.println(test.result());
	}
}
