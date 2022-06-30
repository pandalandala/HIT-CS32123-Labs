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
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪͶƱ�˹�Ȩ
	private Map<Voter, Double> stockers;
	// ����ͶƱ������keyΪͶƱ�ˣ�valueΪͶƱ����
	private Map<Voter,Integer> stockerVoteCount; 
	// ʵ��ͶƱ����
	private Map<RealNameVote<Proposal>,Boolean> stockerRealVotes; 
	// ��ЧƱ�й�Ȩ�Ӻ�
	private double LegalVotes = 0;
	// ��ЧƱ��֧��Ʊ��Ȩ�Ӻ�
	private double LegalSupportVotes = 0;
	
	/**
	 * ��ҵ�����Ʊ����
	 * 
	 * @param stockers ͶƱ�˼���
	 * @param stockerVoteCount ����ͶƱ����
	 * @param stockerRealVotes ͶƱ�˵�ѡƱ
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
		//��ɸ��ͶƱ������Ϊ1��ͶƱ��
		for(Map.Entry<Voter,Integer> entry : this.stockerVoteCount.entrySet())
		{
			if(entry.getValue() != 1)
				this.stockers.remove(entry.getKey());
		}
		
		//��ɸѡ��ʣ�µ�ѡƱ
		for(Iterator<Map.Entry<RealNameVote<Proposal>, Boolean>> it = stockerRealVotes.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<RealNameVote<Proposal>, Boolean> entry = it.next();
			boolean flag = true;
			//�Ϸ�ȷ��
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
					if(it.next().getVoteValue().equals("֧��"))
						this.LegalSupportVotes += in1.getValue();
				
				}
			}
		}
	}
	
	/**
	 * ��úϷ�ѡƱ�Ĺɷ�ռ��
	 * 
	 * @return
	 */
	public double getLegalVotes()
	{
		return LegalVotes;
	}
	
	/**
	 * ��úϷ�ѡƱ��֧��Ʊ�Ĺɷ�ռ��
	 * 
	 * @return
	 */
	public double getLegalSupportVotes()
	{
		return LegalSupportVotes;
	}
}

