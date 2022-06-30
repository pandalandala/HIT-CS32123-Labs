package app;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Proposal;
import auxiliary.Voter;
import poll.BusinessVoting;
import vote.RealNameVote;
import vote.VoteItem;

public class BusinessVotingApp {

	public static void main(String[] args) throws Exception {
		BusinessVoting test = new BusinessVoting();
		List<Proposal> list2 = new LinkedList<Proposal>();
		Proposal p2 = new Proposal("business", Calendar.getInstance());
		list2.add(p2);
		test.addCandidates(list2);
		test.setInfo(p2);

		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 0.7);
		voters3.put(voter4, 0.3);
		test.addVoters(voters3);

		VoteItem<Proposal> item3 = new VoteItem<Proposal>(p2, "支持");
		VoteItem<Proposal> item4 = new VoteItem<Proposal>(p2, "反对");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<VoteItem<Proposal>>();
		voteItems3.add(item3);
		Set<VoteItem<Proposal>> voteItems4 = new HashSet<VoteItem<Proposal>>();
		voteItems4.add(item4);
		RealNameVote<Proposal> votes3 = new RealNameVote<Proposal>(voter3, voteItems3);
		RealNameVote<Proposal> votes4 = new RealNameVote<Proposal>(voter4, voteItems4);

		test.VoterVote(votes3);
		test.addVote(votes3);
		test.VoterVote(votes4);
		test.addVote(votes4);

		test.statistics();
		test.selection();

		System.out.println(test.result());
	}
}
