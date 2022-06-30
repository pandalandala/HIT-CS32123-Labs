package vote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class VoteTest {

	// test Strategy
	// ������ѡƱ����ȡѡƱ���ϣ��޸�ͶƱ����,���һ����ѡ���Ƿ��ڱ�ѡƱ��
	
	@Test
	public void test() {
		VoteItem<String> newItem1 = new VoteItem<String>("wjh","֧��");
		VoteItem<String> newItem2 = new VoteItem<String>("asq","��Ȩ");
		VoteItem<String> newItem3 = new VoteItem<String>("dmr","����");
		VoteItem<String> newItem4 = new VoteItem<String>("zyt","����");
		
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
