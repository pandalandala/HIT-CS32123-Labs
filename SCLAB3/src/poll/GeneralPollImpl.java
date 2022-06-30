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

	// ͶƱ�������
	private String name;
	// ͶƱ������ʱ��
	private Calendar date;
	// ��ѡ���󼯺�
	private List<C> candidates;
	//���ڶ�ͶƱ�˵�ͶƱ�������м��㣬keyΪͶƱ�ˣ�valueΪͶƱ����
	private Map<Voter,Integer> voterVoteCount =  new HashMap<Voter,Integer>(); 
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
	private Map<Voter, Double> voters;
	// ��ѡ���ĺ�ѡ�����������
	private int quantity;
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�choice�����Զ�Ӧ�ķ�����
	private VoteType voteType;
	// ����ѡƱ����,keyΪѡƱ��value��ʾѡƱ�Ƿ�Ϸ�
	protected Map<Vote<C>,Boolean> votes;
	// ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
	protected Map<C, Double> statistics;
	// ��ѡ�����keyΪ��ѡ����valueΪ������λ��
	private Map<C, Double> results;

	// Rep Invariants
	// ��������ȷ�Ϻ󲻿ɸ���
	// �������ṩͶƱ����ơ�ͶƱ�����ʱ�䡢ͶƱ���ͣ���ѡ������������Щ�������Ծ�������Ϊ��
	// Abstract Function
	// ͶƱ�������
	// ͶƱ������ʱ��
	// ��ѡ���󼯺�
	// ͶƱ�˼���
	// ��ѡ���ĺ�ѡ�����������
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�choice�����Զ�Ӧ�ķ�����
	// ����ѡƱ����
	// ��Ʊ���
	// ��ѡ���
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
	 * ͶƱ��ͶƱʱ���ø÷�����������ĳһͶƱ�˵�ͶƱ�������м���
	 * @param name Ͷ����һƱ����
	 * @param vote �����Ͷ����Ʊ
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
	 * ��ú�ѡ������
	 * 
	 * @return
	 */
	public List<C> getCandidates()
	{
		return new LinkedList<C>(this.candidates);
	}
	
	/**
	 * ���ͶƱ����
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
