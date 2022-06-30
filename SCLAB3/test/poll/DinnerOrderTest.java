package poll;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import auxiliary.Dish;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class DinnerOrderTest {

	
	DinnerOrder test = new DinnerOrder();
	
	// test Strategy
	// 增加基础属性
	
	
	@Test
	public void test1() {
		test.setInfo("breakfast",2,7,2);
		VoteType t1 = new VoteType();
		t1.addType("喜欢",2);
		t1.addType("不喜欢",0);
		t1.addType("无所谓",1);
		assertEquals(test.getVoteType(),t1);
	}
	
	// test Strategy
	// 测试加入的Dish
	@Test
	public void test2() {
		List<Dish> list2 = new LinkedList<Dish>();
		Dish p2 = new Dish("Dish1",1.2);
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
		List<Dish> list2 = new LinkedList<Dish>();
		Dish p2 = new Dish("Dish1",1.2);
		list2.add(p2);
		Dish p3 = new Dish("Dish2",2.2);
		list2.add(p3);
		Dish p4 = new Dish("Dish3",3.2);
		list2.add(p4);
		Dish p5 = new Dish("Dish4",1.2);
		list2.add(p5);
		Dish p6 = new Dish("Dish5",1.2);
		list2.add(p6);
		Dish p7 = new Dish("Dish6",1.2);
		list2.add(p7);
		Dish p8 = new Dish("Dish7",1.2);
		list2.add(p8);
		
		test.addCandidates(list2);
		test.setInfo("breakfast",2,7,2);
		
		Map<Voter, Double> voters3 = new HashMap<Voter, Double>();
		Voter voter3 = new Voter("zxr");
		Voter voter4 = new Voter("zf");
		voters3.put(voter3, 0.8);
		voters3.put(voter4, 0.2);
		test.addVoters(voters3);
		
		VoteItem<Dish> i31 = new VoteItem<Dish>(p2, "喜欢");
		VoteItem<Dish> i32 = new VoteItem<Dish>(p3, "喜欢");
		VoteItem<Dish> i33 = new VoteItem<Dish>(p4, "不喜欢");
		VoteItem<Dish> i34 = new VoteItem<Dish>(p5, "不喜欢");
		VoteItem<Dish> i35 = new VoteItem<Dish>(p6, "不喜欢");
		VoteItem<Dish> i36 = new VoteItem<Dish>(p7, "不喜欢");
		VoteItem<Dish> i37 = new VoteItem<Dish>(p8, "不喜欢");
		VoteItem<Dish> i41 = new VoteItem<Dish>(p2, "喜欢");
		VoteItem<Dish> i42 = new VoteItem<Dish>(p3, "无所谓");
		VoteItem<Dish> i43 = new VoteItem<Dish>(p4, "无所谓");
		VoteItem<Dish> i44 = new VoteItem<Dish>(p5, "无所谓");
		VoteItem<Dish> i45 = new VoteItem<Dish>(p6, "无所谓");
		VoteItem<Dish> i46 = new VoteItem<Dish>(p7, "无所谓");
		VoteItem<Dish> i47 = new VoteItem<Dish>(p8, "无所谓");
		
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
		
		RealNameVote<Dish> votes3 = new RealNameVote<Dish>(voter3, voteItems3 );
		RealNameVote<Dish> votes4 = new RealNameVote<Dish>(voter4, voteItems4 );
		
		test.VoterVote(votes3);
		test.addVote(votes3);
		test.VoterVote(votes4);
		test.addVote(votes4);
		
		test.statistics();
		test.selection();
		
		assertEquals(test.result(),"Dish为Dish1价格为1.2\nDish为Dish2价格为2.2\n");
	}

}
