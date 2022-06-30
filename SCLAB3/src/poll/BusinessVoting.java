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

	//�����µ�ͶƱ����
	private VoteType type = new VoteType();
	//���ڶ�ͶƱ�˵�ͶƱ�������м��㣬keyΪͶƱ�ˣ�valueΪͶƱ����
	private Map<Voter,Integer> voterVoteCount =  new HashMap<Voter,Integer>();
	//���ʵ��ͶƱ�ļ�
	private Map<RealNameVote<Proposal>,Boolean> realVotes = new HashMap<RealNameVote<Proposal>,Boolean>();
	//
	private BusinessStatisticsStrategy s1;
	//
	private BusinessSelection s2;

	// Rep Invariants
	// ͶƱ����ơ�ʱ�䡢ͶƱ���ͣ�ѡ�������������Ҷ���Ϊnull
	// Abstract Function
	// ͶƱ���ͣ������Ϸ�choice�Լ�����
	// ʵ��ѡƱ����
	// Safety from Rep Exposure
	// None


	/**
	 * ������
	 */
	public BusinessVoting() {
		super();
	}
	/**
	 * �µ����û������Է���
	 * ͶƱ�������Ϊ�᰸������
	 *
	 * @param proposal Ҫ���б��������
	 */

	public void setInfo(Proposal proposal)
	{
		Calendar newDate = Calendar.getInstance();
		type.addType("֧��", 1);
		type.addType("����", -1);
		type.addType("��Ȩ", 0);
		super.setInfo(proposal.getTitle(), newDate, type, 1);
	}

	/**
	 * ʵ��ͶƱʱ���ø÷�����������ʵ��ͶƱ����£�ĳһͶƱ�˵�ͶƱ�������м���
	 *
	 * @param vote ʵ��ͶƱ
	 */
	public void VoterVote(RealNameVote<Proposal> vote) {
		if(voterVoteCount.containsKey(vote.getVoter()))//������Ѿ�Ͷ��Ʊ��
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
		else//�����û��Ͷ��Ʊ
		{
			voterVoteCount.put(vote.getVoter(), 1);
		}
	}

	/**
	 * ����ʵ��ͶƱҪ���õķ���
	 *
	 * @param vote ʵ��ͶƱ
	 */
	public void addVote(RealNameVote<Proposal> vote) {
		VoteType voteType = super.getVoteType();
		List<Proposal> candidates = super.getCandidates();
		if(candidates != null)//��֤��ѡ��������Ϊ��
		{
			if(vote.getvoteItems().size() == candidates.size())//��֤ͶƱ����ͺ�ѡ���������
			{
				//�Ѿ���д��VoteItem<C>��equals���������Ա�֤�����ֶ�ĳһ����Ͷ�����ͬƱ����û��ָ��ĺ�ѡ��һ��������ͶƱ������һ����Ʊ��
				//ֻ��Ҫ�ж�û�ж�һ���˵��ظ�ͶƱ����û�ж�ĳ����ѡ��Ͷ������Ͳ�ͬ��Ʊ�������ұ�֤������������ѡ�˼���
				boolean flag1 = true;//Ĭ��ѡƱ�Ϸ�
				for(VoteItem<Proposal> each1 : vote.getvoteItems())
				{
					//��֤ѡƱָ��ĺ�ѡ���ں�ѡ���嵥�У�����ͶƱѡ���Ǹ�����ѡ��֮һ
					if(!(candidates.contains(each1.getCandidate())
							&& voteType.getVoteType().containsKey(each1.getVoteValue())))
					{
						flag1 = false;
						break;
					}

					//���Ʊ����û�ж�ͬһ����ѡ����Ķ��ͶƱ��,ȫ����⻹�����ų�����ĳ����ѡ��©���Ŀ���
					//�ڶ��α��������ҵ���ͬԪ�غ����flag2���в���
					//Ӧ���и��õ�д������ʱ������
					boolean flag2 = false;
					Set<VoteItem<Proposal>> tempVoteItems = new HashSet<>(vote.getvoteItems());
					for(VoteItem<Proposal> each2 : tempVoteItems)
					{
						if(each2.getCandidate().equals(each1.getCandidate()))
						{
							if(!flag2)//��һ���ҵ��˸�eachָ����ͬ��ѡ�˵�Ԫ��
								flag2 = true;
							else //�ҵ�������eachָ��ĺ�ѡ����ͬ��Ԫ�أ�ͶƱ����
							{
								flag1 = false;
								break;
							}
						}
					}
					if(!flag1)//ѡƱ����
						break;
				}
				realVotes.put(vote,flag1);
			}
		}
		else//��ѡ������Ϊ�գ�����Ʊ���ǲ��Ϸ���
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
			return new String("ͨ��");
		else
			return new String("��ͨ��");
	}
}