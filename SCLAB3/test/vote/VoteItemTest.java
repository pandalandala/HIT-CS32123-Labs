package vote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VoteItemTest {

	// test Strategy
	// ����һ��ͶƱ����ͶƱ�����ͶƱ��
	//
	@Test
	public void test() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","֧��");
		assertEquals(newItem.getCandidate(),"wjh");
		assertEquals(newItem.getVoteValue(),"֧��");
	}
	
	// test Strategy
	// �����޸ķ��ص�����
	//
	//
	@Test
	public void test2() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","֧��");
		String s1 = newItem.getCandidate();
		s1 = "ysf";
		String s2 = newItem.getVoteValue();
		s2 = "����";
		assertFalse(newItem.getCandidate().equals(s1));
		assertFalse(newItem.getVoteValue().equals(s2));
	}
	
	// test Strategy
	// ����equals
			
	@Test
	public void test3() {
		VoteItem<String> newItem = new VoteItem<String>("wjh","֧��");
		VoteItem<String> newItem2 = new VoteItem<String>("wjh","֧��");
		assertTrue(newItem.equals(newItem2));
	}
}
