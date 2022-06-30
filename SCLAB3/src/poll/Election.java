package poll;

import java.util.Calendar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.ElectionSelection;
import pattern.ElectionStatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class Election extends GeneralPollImpl<Person> implements Poll<Person> {

	//保存新的投票类型
	private VoteType type = new VoteType();
	//拟遴选出的候选对象数量
	private int k = 0;
	//保存所有投过票的人
	private Set<RealNameVote<Person>> votedPersons = new HashSet<RealNameVote<Person>>();
	//保存合法选票
	private Map<Vote<Person>,Integer> votes = new HashMap<Vote<Person>,Integer>();
	//存放候选人
	private HashMap<Person,Integer> Candidates;

	private ElectionStatisticsStrategy s1;

	private ElectionSelection s2;
	//返回最高分的k个投票人
	private List<Person> PersonList;

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
	public Election() {
		super();
	}
		

	public void setInfo(String name, int quantity)
	{
		this.k = quantity;
		Calendar newDate = Calendar.getInstance();
		type.addType("支持", 1);
		type.addType("反对", -1);
		type.addType("弃权", 0);
		super.setInfo(name, newDate, type, quantity);
	}
	
	@Override
	public void addVote(Vote<Person> vote) {
		if(votes.containsKey(vote))
		{
			for(Map.Entry<Vote<Person>,Integer> entry : votes.entrySet())
			{
				if(entry.getKey().equals(vote)) {
					votes.put(vote, entry.getValue() + 1);
					break;
				}
					
			}
		}
		else
			votes.put(vote, 1);
	}
		

	public void addPersonsVote(Vote<Person> vote,Voter name) {
		boolean flag = false;
		for(RealNameVote<Person> iter : votedPersons)
		{
			if(iter.getVoter().equals(name)) {
				flag=true;
				break;
			}
		}
		if(flag)
		{
			for(Iterator<RealNameVote<Person>> it = votedPersons.iterator(); it.hasNext();)
			{
				RealNameVote<Person> iter = it.next();
				if(iter.getVoter().equals(name))
				{
					Vote<Person> tempVote = new Vote<Person>(iter.getvoteItems());
					for(Map.Entry<Vote<Person>,Integer> entry : votes.entrySet())
					{
						if(entry.getKey().equals(tempVote))
						{
							votes.put(tempVote, entry.getValue() - 1);
							break;
						}
					}
					break;
				}
			}
		}
		else {
			RealNameVote<Person> newPerson = new RealNameVote<Person>(name, vote.getvoteItems());
			votedPersons.add(newPerson);
			int count = 0;
			Set<VoteItem<Person>>newvoteItems = vote.getvoteItems();
			for(VoteItem<Person> iter : newvoteItems)
			{
				if(iter.getVoteValue().equals("支持"))
					count++;
			}
				addVote(vote);
		}
	}
	
	public void statistics() {
		HashMap<Person,Integer> m1 = new HashMap<Person,Integer>();
		List<Person> l = super.getCandidates();
		for(Person iter : l)
		{
			m1.put(iter, 0);
		}
		this.Candidates = m1;
		s1 = new  ElectionStatisticsStrategy(votes, m1, type);
		s1.Statistics();
		this.Candidates = s1.getDishes();
		}

	public void selection() {
		s2 = new ElectionSelection(Candidates,k);
		PersonList = s2.select();
	}

	@Override
	public String result() {
		String x = "";
		for(int i = 0; i< PersonList.size();i++)
			x = x.concat(PersonList.get(i).toString() + "\n");
		return x;
	}
}
