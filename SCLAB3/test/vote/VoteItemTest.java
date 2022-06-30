package vote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VoteItemTest {

	// test Strategy
	// 建立一个投票项，获得投票对象和投票项
	//
	@Test
	public void test() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","支持");
		assertEquals(newItem.getCandidate(),"wjh");
		assertEquals(newItem.getVoteValue(),"支持");
	}
	
	// test Strategy
	// 尝试修改返回的内容
	//
	//
	@Test
	public void test2() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","支持");
		String s1 = newItem.getCandidate();
		s1 = "ysf";
		String s2 = newItem.getVoteValue();
		s2 = "反对";
		assertFalse(newItem.getCandidate().equals(s1));
		assertFalse(newItem.getVoteValue().equals(s2));
	}
	
	// test Strategy
	// 测试equals
			
	@Test
	public void test3() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","支持");
		VoteItem<String> newItem2 = new VoteItem<String>("wjh","支持");
		assertTrue(newItem.equals(newItem2));
	}
}
