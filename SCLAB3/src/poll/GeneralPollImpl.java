package poll;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.VoteType;
import vote.Vote;
import vote.VoteItem;

public class GeneralPollImpl<C> implements Poll<C> {

	// 投票活动的名称
	private String name;
	// 投票活动发起的时间
	private Calendar date;
	// 候选对象集合
	private List<C> candidates;
	//用于对投票人的投票次数进行计算，key为投票人，value为投票次数
	private Map<Voter,Integer> voterVoteCount =  new HashMap<Voter,Integer>(); 
	// 投票人集合，key为投票人，value为其在本次投票中所占权重
	private Map<Voter, Double> voters;
	// 拟选出的候选对象最大数量
	private int quantity;
	// 本次投票拟采用的投票类型（合法choice及各自对应的分数）
	private VoteType voteType;
	// 所有选票集合,key为选票，value表示选票是否合法
	protected Map<Vote<C>,Boolean> votes;
	// 计票结果，key为候选对象，value为其得分
	protected Map<C, Double> statistics;
	// 遴选结果，key为候选对象，value为其排序位次
	private Map<C, Double> results;

	// Rep Invariants
	// 所有内容确认后不可更改
	// 构造器提供投票活动名称、投票活动发起时间、投票类型，拟选出的数量，这些基本属性经创建后不为空
	// Abstract Function
	// 投票活动的名称
	// 投票活动发起的时间
	// 获选对象集合
	// 投票人集合
	// 拟选出的候选对象最大数量
	// 本次投票拟采用的投票类型（合法choice及各自对应的分数）
	// 所有选票集合
	// 计票结果
	// 遴选结果
	// Safety from Rep Exposure
	// None

	private boolean checkRep() {
		assert name != null;
		assert date != null;
		assert voteType != null;
		return false;
	}

	/**
	 *
	 */
	public GeneralPollImpl() {
	}
	
	public GeneralPollImpl(String name, Calendar date, VoteType type, int quantity) {
		this.setInfo(name, date, type, quantity);
	}

	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		this.name = new String(name);
		
		this.date = Calendar.getInstance();
		this.date.set(date.get(Calendar.YEAR),
					date.get(Calendar.MONTH), 
					date.get(Calendar.DATE), 
					date.get(Calendar.HOUR_OF_DAY), 
					date.get(Calendar.MINUTE), 
					date.get(Calendar.SECOND));
		this.date.setTimeInMillis(date.get(Calendar.MILLISECOND));
		
		Map<String, Integer> options= type.getVoteType();
		this.voteType = new VoteType();
		for(Map.Entry<String, Integer> entry : options.entrySet())
		{
			this.voteType.addType(entry.getKey(), entry.getValue());
		}
		
		this.quantity = quantity;
	}

	@Override
	public void addVoters(Map<Voter, Double> voters) {
		this.voters = new HashMap<Voter, Double>();
		Map<Voter, Double> tempVoters = new HashMap<Voter, Double> (voters);
		this.voters.putAll(tempVoters);
	}

	@Override
	public void addCandidates(List<C> candidates) {
		List<C> tempCandidates = new LinkedList<C>(candidates);
		this.candidates = new LinkedList<C>();
		this.candidates.addAll(tempCandidates);
	}

	@Override
	public void addVote(Vote<C> vote) {
		if(this.candidates != null)
		{
			if(vote.getvoteItems().size() == this.candidates.size())
			{
				boolean flag1 = true;
				for(VoteItem<C> iter1 : vote.getvoteItems())
				{
					if(!(this.candidates.contains(iter1.getCandidate()) 
							&& this.voteType.getVoteType().containsKey(iter1.getVoteValue())))
					{
						flag1 = false;
						break;
					}
					
					boolean flag2 = false;
					Set<VoteItem<C>> tempvoteItems = new HashSet<>(vote.getvoteItems());
					for(VoteItem<C> iter2 : tempvoteItems)
					{
						if(iter2.getCandidate().equals(iter1.getCandidate()))
						{
							if(!flag2)
								flag2 = true;
							else
							{
								flag1 = false;
								break;
							}
						}
					}
					if(!flag1)
						break;	
				}
				votes.put(vote,flag1);
			}
		}
		else
		{
			votes.put(vote,false);
		}
	}
	
	/**
	 * 投票人投票时调用该方法，用来对某一投票人的投票次数进行计数
	 * @param name 投出这一票的人
	 * @param vote 这个人投出的票
	 */
	public void VoterVote(Voter name, Vote<C> vote)
	{
		if(voterVoteCount.containsKey(name))
		{
			int count = 0;
			for(Map.Entry<Voter, Integer> entry : this.voterVoteCount.entrySet())
			{
				if(entry.getKey().equals(name))
				{
					count = entry.getValue().intValue() + 1;
					break;
				}
			}
			voterVoteCount.put(name, count);
		}
		else
		{
			voterVoteCount.put(name, 1);
		}
	}

	public Map<Voter, Double> getVoters()
	{
		return new HashMap<Voter, Double> (voters);
	}
	
	/**
	 * 获得候选人名单
	 * 
	 * @return
	 */
	public List<C> getCandidates()
	{
		return new LinkedList<C>(this.candidates);
	}
	
	/**
	 * 获得投票类型
	 * 
	 * @return
	 */
	public VoteType getVoteType()
	{
		Map<String, Integer> options = this.voteType.getVoteType();
		VoteType v = new VoteType();		
		for(Map.Entry<String, Integer> entry : options.entrySet())
		{
			v.addType(entry.getKey(), entry.getValue());
		}
		return v;
	}
	
	

	@Override
	public void statistics(StatisticsStrategy ss) {
	}

	@Override
	public void selection(SelectionStrategy ss) {
	}

	@Override
	public String result() {
		return null;
	}
}
