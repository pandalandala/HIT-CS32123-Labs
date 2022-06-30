package vote;

import java.util.Set;

import auxiliary.Voter;

//immutable
public class RealNameVote<C> extends Vote<C>{
	
	//投票人
	private Voter voter;
	
	public RealNameVote(Voter voterID, Set<VoteItem<C>> voteItems) {
		super(voteItems);
		this.voter = new Voter(voterID.getID());
	}

	/**
	 * 返回投票者
	 * 
	 * @return
	 */
	public Voter getVoter() {
		return this.voter;
	}
}
