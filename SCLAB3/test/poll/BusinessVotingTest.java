package poll;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import auxiliary.Proposal;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class BusinessVotingTest {


	BusinessVoting test = new BusinessVoting();

	// test Strategy
	// 增加基础属性,并查看内容


	@Test
	public void test1() {
		Proposal p1 = new Proposal("Business1",Calendar.getInstance());
		test.setInfo(p1);
		VoteType t1 = new VoteType();
		t1.addType("支持",1);
		t1.addType("反对",-1);
		t1.addType("弃权",0);
		assertEquals(test.getVoteType(),t1);
	}

	// test Strategy
	// 测试加入的Business
	@Test
	public void test2() {
		List<Proposal> list2 = new LinkedList<Proposal>();
		Proposal p2 = new Proposal("Business2",Calendar.getInstance());
		list2.add(p2);
		test.addCandidates(list2);
		assertEquals(test.getCandidates(),list2);
	}
	// test Strategy
	// 测试加入的投票者
	@Test
	public void test3() {
		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 0.5);
		voters3.put(voter4, 0.2);
		test.addVoters(voters3);
		assertEquals(test.getVoters(),voters3);
	}
	//测试投票直到给出结果的过程
	@Test
	public void test4() {
		List<Proposal> list2 = new LinkedList<Proposal>();
		Proposal p2 = new Proposal("Business2",Calendar.getInstance());
		list2.add(p2);
		test.addCandidates(list2);
		test.setInfo(p2);

		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 0.8);
		voters3.put(voter4, 0.2);
		test.addVoters(voters3);

		VoteItem<Proposal> item3 = new VoteItem<Proposal>(p2, "支持");
		VoteItem<Proposal> item4 = new VoteItem<Proposal>(p2, "反对");

		Set<VoteItem<Proposal>> voteItems3 = new HashSet<VoteItem<Proposal>>();
		voteItems3.add(item3);
		Set<VoteItem<Proposal>> voteItems4 = new HashSet<VoteItem<Proposal>>();
		voteItems4.add(item4);

		RealNameVote<Proposal> votes3 = new RealNameVote<Proposal>(voter3, voteItems3 );
		RealNameVote<Proposal> votes4 = new RealNameVote<Proposal>(voter4, voteItems4 );

		test.VoterVote(votes3);
		test.addVote(votes3);
		test.VoterVote(votes4);
		test.addVote(votes4);

		test.statistics();
		test.selection();

		assertEquals(test.result(),"通过");
	}

}
