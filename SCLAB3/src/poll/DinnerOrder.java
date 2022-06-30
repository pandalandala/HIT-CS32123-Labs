package poll;

import java.util.Calendar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Dish;
import auxiliary.Voter;
import pattern.DinnerSelection;
import pattern.DinnerStatisticsStrategy;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class DinnerOrder extends GeneralPollImpl<Dish> implements Poll<Dish> {
	//保存新的投票类型
	private VoteType type = new VoteType();
	//计算投票次数，key为投票人，value为投票次数
	private Map<Voter,Integer> voterVoteCount =  new HashMap<Voter,Integer>();
	//存放实名投票的集
	private Map<RealNameVote<Dish>,Boolean> realVotes = new HashMap<RealNameVote<Dish>,Boolean>(); 
	//拟遴选出的候选对象数量
	private int k = 0;
	//存放菜品和对应分数
	private Map<Dish,Integer> Dishes = new HashMap<Dish,Integer>();

	private DinnerStatisticsStrategy s1;

	private DinnerSelection s2;
	//返回最高分的number道菜
	private List<Dish> DishList;
	
	// Rep Invariants
	// 投票活动名称、时间、投票类型，选出的数量，并且都不为null
	// Abstract Function
	// 投票类型，包括合法choice以及分数
	// 实名选票集合
	// Safety from Rep Exposure
	// None
			
			
	/**
	*
	*/
	public DinnerOrder() {
		super();
	}
			
	/**
	* 新的设置基本属性方法
	* 要求 n <= k <= n+5 <= m
	* 
	* @param name 本次聚餐的名称
	* @param k 本次聚餐要选出的Dish个数
	* @param m 候选Dish的个数
	* @param n 参加聚餐的人员个数
	*/
		
	public void setInfo(String name, int k, int m, int n)
	{
		if(n <= k && k <= n+5 && n+5 <= m)
		{
			this.k = k;
			Calendar newDate = Calendar.getInstance();
			type.addType("喜欢", 2);
			type.addType("不喜欢", 0);
			type.addType("无所谓", 1);
			super.setInfo(name, newDate, type, k);
		}
		else
			System.out.println("输入不合法！请重新输入");
	}

	public void VoterVote(RealNameVote<Dish> vote) {
		if(voterVoteCount.containsKey(vote.getVoter()))
		{
			int count = 0;
			for(Map.Entry<Voter, Integer> entry : this.voterVoteCount.entrySet())
			{
				if(entry.getKey().equals(vote.getVoter()))
				{
					count = entry.getValue().intValue() + 1;
					break;
				}
			}
			voterVoteCount.put(vote.getVoter(), count);
			
		}
		else
		{
			voterVoteCount.put(vote.getVoter(), 1);
		}
	}

	public void addVote(RealNameVote<Dish> vote) {
		VoteType voteType = super.getVoteType();
		List<Dish> candidates = super.getCandidates();
		if(candidates != null)
		{
			if(vote.getvoteItems().size() == candidates.size())
			{
				boolean flag1 = true;
				for(VoteItem<Dish> iter1 : vote.getvoteItems())
				{
					if(!(candidates.contains(iter1.getCandidate()) 
							&& voteType.getVoteType().containsKey(iter1.getVoteValue())))
					{
						flag1 = false;
						break;
					}

					boolean flag2 = false;
					Set<VoteItem<Dish>> tempvoteItems = new HashSet<>(vote.getvoteItems());
					for(VoteItem<Dish> iter2 : tempvoteItems)
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
				realVotes.put(vote,flag1);
			}
		}
		else
		{
			realVotes.put(vote,false);
		}
	}
	
	@Override
	public void addCandidates(List<Dish> candidates)
	{
		for(Dish iter : candidates)
		{
			Dishes.put(iter, 0);
		}
		super.addCandidates(candidates);
	}
	
	public void statistics() {
		s1 = new  DinnerStatisticsStrategy(super.getVoters(), voterVoteCount, realVotes, Dishes, type);
		s1.Statistics();
		Dishes = s1.getDishes();
	}

	public void selection() {
		s2 = new DinnerSelection(Dishes,k);
		DishList = s2.select();
	}

	@Override
	public String result() {
		String x = "";
		for(int i = 0; i< DishList.size();i++)
			x = x.concat(DishList.get(i).toString() + "\n");
		return x;
	}
}
