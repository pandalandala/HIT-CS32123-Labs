package pattern;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import auxiliary.Proposal;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;

public class BusinessStatisticsStrategy implements StatisticsStrategy<Proposal>
{
	// 投票人集合，key为投票人，value为投票人股权
	private Map<Voter, Double> stockers;
	// 计算投票次数，key为投票人，value为投票次数
	private Map<Voter,Integer> stockerVoteCount; 
	// 实名投票集合
	private Map<RealNameVote<Proposal>,Boolean> stockerRealVotes; 
	// 有效票中股权加和
	private double LegalVotes = 0;
	// 有效票中支持票股权加和
	private double LegalSupportVotes = 0;
	
	/**
	 * 商业表决计票规则
	 * 
	 * @param stockers 投票人集合
	 * @param stockerVoteCount 计算投票次数
	 * @param stockerRealVotes 投票人的选票
	 */
	public BusinessStatisticsStrategy(Map<Voter, Double> stockers, 
			Map<Voter,Integer> stockerVoteCount,
			Map<RealNameVote<Proposal>,Boolean> stockerRealVotes
			) {
		this.stockers = new HashMap<Voter, Double>(stockers);
		this.stockerVoteCount = new HashMap<Voter,Integer>(stockerVoteCount);
		this.stockerRealVotes = new HashMap<RealNameVote<Proposal>,Boolean> (stockerRealVotes);
	}
	
	@Override
	public void Statistics() {
		//先筛除投票次数不为1的投票人
		for(Map.Entry<Voter,Integer> entry : this.stockerVoteCount.entrySet())
		{
			if(entry.getValue() != 1)
				this.stockers.remove(entry.getKey());
		}
		
		//再筛选出剩下的选票
		for(Iterator<Map.Entry<RealNameVote<Proposal>, Boolean>> it = stockerRealVotes.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<RealNameVote<Proposal>, Boolean> entry = it.next();
			boolean flag = true;
			//合法确认
			if(entry.getValue() == true) {
				if(!stockers.containsKey(entry.getKey().getVoter())) {
					flag = false;
					break;
				}
			}
			else
				flag = false;
			if(!flag) 
				it.remove();
		}
		
		for(Map.Entry<Voter, Double> in1 : stockers.entrySet())
		{
			for(Entry<RealNameVote<Proposal>, Boolean> in2 : stockerRealVotes.entrySet())
			{
				if(in2.getKey().getVoter().equals(in1.getKey()))
				{
					this.LegalVotes += in1.getValue();
					Set<VoteItem<Proposal>> voteItems = in2.getKey().getvoteItems();
					Iterator<VoteItem<Proposal>> it = voteItems.iterator();
					if(it.next().getVoteValue().equals("支持"))
						this.LegalSupportVotes += in1.getValue();
				
				}
			}
		}
	}
	
	/**
	 * 获得合法选票的股份占比
	 * 
	 * @return
	 */
	public double getLegalVotes()
	{
		return LegalVotes;
	}
	
	/**
	 * 获得合法选票中支持票的股份占比
	 * 
	 * @return
	 */
	public double getLegalSupportVotes()
	{
		return LegalSupportVotes;
	}
}

