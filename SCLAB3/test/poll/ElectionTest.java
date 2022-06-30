package poll;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import auxiliary.Person;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionTest {

	
	Election test = new Election();
	
	// test Strategy
	// 增加基础属性,并查看内容
	
	
	@Test
	public void test1() {
		test.setInfo("Election",2);
		VoteType t1 = new VoteType();
		t1.addType("支持",1);
		t1.addType("反对",-1);
		t1.addType("弃权",0);
		assertEquals(test.getVoteType(),t1);
	}
	
	// test Strategy
	// 测试加入候选人
	@Test
	public void test2() {
		List<Person> list2 = new LinkedList<Person>();
		Person p2 = new Person("zyt",18);
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
		voters3.put(voter3, 1.0);
		voters3.put(voter4, 1.0);
		test.addVoters(voters3);
		assertEquals(test.getVoters(),voters3);
	}
	//测试投票直到给出结果的过程
	@Test
	public void test4() {
		List<Person> list2 = new LinkedList<Person>();
		Person p2 = new Person("zyt",18);
		list2.add(p2);
		Person p3 = new Person("ysf",20);
		list2.add(p3);
		Person p4 = new Person("ltc",21);
		list2.add(p4);
		
		test.addCandidates(list2);
		test.setInfo("Election",2);
		
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
		
		RealNameVote<Person> votes3 = new RealNameVote<Person>(voter3, voteItems3 );
		RealNameVote<Person> votes4 = new RealNameVote<Person>(voter4, voteItems4 );
		
		test.VoterVote(voter3, votes3);
		test.addVote(votes3);
		test.VoterVote(voter4, votes4);
		test.addVote(votes4);
		
		test.statistics();
		test.selection();
		
		assertEquals(test.result(),"投票人姓名为ysf年龄为20\n投票人姓名为zyt年龄为18\n");
	}

}
