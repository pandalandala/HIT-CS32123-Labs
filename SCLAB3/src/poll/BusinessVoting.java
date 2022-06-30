package poll;

import java.util.Calendar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.BusinessSelection;
import pattern.BusinessStatisticsStrategy;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class BusinessVoting extends GeneralPollImpl<Proposal> implements Poll<Proposal> {

	//保存新的投票类型
	private VoteType type = new VoteType();
	//用于对投票人的投票次数进行计算，key为投票人，value为投票次数
	private Map<Voter,Integer> voterVoteCount =  new HashMap<Voter,Integer>();
	//存放实名投票的集
	private Map<RealNameVote<Proposal>,Boolean> realVotes = new HashMap<RealNameVote<Proposal>,Boolean>();
	//
	private BusinessStatisticsStrategy s1;
	//
	private BusinessSelection s2;

	// Rep Invariants
	// 投票活动名称、时间、投票类型，选出的数量，并且都不为null
	// Abstract Function
	// 投票类型，包括合法choice以及分数
	// 实名选票集合
	// Safety from Rep Exposure
	// None


	/**
	 * 构造器
	 */
	public BusinessVoting() {
		super();
	}
	/**
	 * 新的设置基本属性方法
	 * 投票活动的名称为提案的名称
	 *
	 * @param proposal 要进行表决的议题
	 */

	public void setInfo(Proposal proposal)
	{
		Calendar newDate = Calendar.getInstance();
		type.addType("支持", 1);
		type.addType("反对", -1);
		type.addType("弃权", 0);
		super.setInfo(proposal.getTitle(), newDate, type, 1);
	}

	/**
	 * 实名投票时调用该方法，用来对实名投票情况下，某一投票人的投票次数进行计数
	 *
	 * @param vote 实名投票
	 */
	public void VoterVote(RealNameVote<Proposal> vote) {
		if(voterVoteCount.containsKey(vote.getVoter()))//这个人已经投过票了
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
		else//这个人没有投过票
		{
			voterVoteCount.put(vote.getVoter(), 1);
		}
	}

	/**
	 * 接受实名投票要调用的方法
	 *
	 * @param vote 实名投票
	 */
	public void addVote(RealNameVote<Proposal> vote) {
		VoteType voteType = super.getVoteType();
		List<Proposal> candidates = super.getCandidates();
		if(candidates != null)//保证候选人名单不为空
		{
			if(vote.getvoteItems().size() == candidates.size())//保证投票对象和候选人数量相等
			{
				//已经重写了VoteItem<C>的equals方法，可以保证不出现对某一个人投多个相同票（即没有指向的候选人一样，并且投票的类型一样的票）
				//只需要判断没有对一个人的重复投票（即没有对某个候选人投多个类型不同的票），并且保证不出现其他候选人即可
				boolean flag1 = true;//默认选票合法
				for(VoteItem<Proposal> each1 : vote.getvoteItems())
				{
					//保证选票指向的候选人在候选人清单中，并且投票选项是给出的选项之一
					if(!(candidates.contains(each1.getCandidate())
							&& voteType.getVoteType().containsKey(each1.getVoteValue())))
					{
						flag1 = false;
						break;
					}

					//检测票中有没有对同一个候选对象的多次投票项,全部检测还可以排除存在某个候选人漏掉的可能
					//第二次遍历，在找到相同元素后根据flag2进行操作
					//应该有更好的写法，暂时不想了
					boolean flag2 = false;
					Set<VoteItem<Proposal>> tempVoteItems = new HashSet<>(vote.getvoteItems());
					for(VoteItem<Proposal> each2 : tempVoteItems)
					{
						if(each2.getCandidate().equals(each1.getCandidate()))
						{
							if(!flag2)//第一次找到了跟each指向相同候选人的元素
								flag2 = true;
							else //找到其他和each指向的候选人相同的元素，投票作废
							{
								flag1 = false;
								break;
							}
						}
					}
					if(!flag1)//选票作废
						break;
				}
				realVotes.put(vote,flag1);
			}
		}
		else//候选人名单为空，所有票都是不合法的
		{
			realVotes.put(vote,false);
		}
	}

	public void statistics() {
		s1 = new  BusinessStatisticsStrategy(super.getVoters(), voterVoteCount, realVotes);
		s1.Statistics();
	}

	public void selection() {
		s2 = new BusinessSelection(s1.getLegalVotes(),s1.getLegalSupportVotes());
		s2.select();
	}

	@Override
	public String result() {
		if(s2.select().intValue() == 1)
			return new String("通过");
		else
			return new String("不通过");
	}
}