package vote;

import java.util.Set;

import auxiliary.Voter;

//immutable
public class RealNameVote<C> extends Vote<C>{
	
	//ͶƱ��
	private Voter voter;
	
	public RealNameVote(Voter voterID, Set<VoteItem<C>> voteItems) {
		super(voteItems);
		this.voter = new Voter(voterID.getID());
	}

	/**
	 * ����ͶƱ��
	 * 
	 * @return
	 */
	public Voter getVoter() {
		return this.voter;
	}
}
