package vote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class VoteTest {

	// test Strategy
	// 创建新选票，获取选票集合，修改投票内容,检测一个候选人是否在本选票中
	
	@Test
	public void test() {
		VoteItem<String> newItem1 = new VoteItem<String>("wjh","支持");
		VoteItem<String> newItem2 = new VoteItem<String>("asq","弃权");
		VoteItem<String> newItem3 = new VoteItem<String>("dmr","反对");
		VoteItem<String> newItem4 = new VoteItem<String>("zyt","反对");
		
		Set<VoteItem<String>> voteItems = new HashSet<>();
		voteItems.add(newItem1);
		voteItems.add(newItem2);
		voteItems.add(newItem3);
		
		Vote<String> newVote = new Vote<String>(voteItems);
		assertTrue(newVote.getvoteItems().equals(voteItems));
		
		voteItems.add(newItem4);
		assertFalse(newVote.getvoteItems().equals(voteItems));
		
		assertFalse(newVote.candidateIncluded("zyt"));
	}

}
