package vote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class VoteTypeTest {

	// test Strategy
	// 添加选项时，选项名相同/选项值相同，考察返回值
	
	@Test
	public void test() {
		VoteType newVoteType = new VoteType();
		newVoteType.addType("choice1", 0);
		newVoteType.addType("choice2", 1);
		newVoteType.addType("choice3", 2);
		assertFalse(newVoteType.addType("choice1", 4));
		assertFalse(newVoteType.addType("choice2", 1));
	}

	// test Strategy
	// 测试判合法的返回值
		
	@Test
	public void test2() {
		VoteType newVoteType = new VoteType();
		newVoteType.addType("choice1", 0);
		assertTrue(newVoteType.checkLegality("choice1"));
		assertFalse(newVoteType.checkLegality("choice2"));
	}
		
	// test Strategy
	// 测试equals()
			
	@Test
	public void test3() {
		VoteType newVoteType = new VoteType();
		newVoteType.addType("choice1", 0);
		VoteType newVoteType2 = new VoteType();
		newVoteType2.addType("choice1", 0);
		assertTrue(newVoteType.equals(newVoteType2));
	}
	
	// test strategy
	// 测试getVoteType()
				
	@Test
	public void test4() {
		VoteType newVoteType = new VoteType();
		newVoteType.addType("choice1", 0);
		newVoteType.addType("choice2", 1);
		newVoteType.addType("choice3", 2);
		assertTrue(Map.of("choice1",0,"choice2", 1,"choice3", 2).equals(newVoteType.getVoteType()));
	}
}
