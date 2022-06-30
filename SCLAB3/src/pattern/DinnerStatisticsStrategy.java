package pattern;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import auxiliary.Dish;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

public class DinnerStatisticsStrategy implements StatisticsStrategy<Dish> {
    //ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪȨ��
    private Map<Voter, Double> familymembers;
    //���ڶ�ͶƱ�������м��㣬keyΪͶƱ�ˣ�valueΪͶƱ����
    private Map<Voter,Integer> familymemberVoteCount;
    //���ʵ��ͶƱ�ļ�
    private Map<RealNameVote<Dish>,Boolean> familymemberRealVotes;
    //����������в�
    private Map<Dish,Integer> Dishes;
    //ͶƱ����
    private VoteType voteType = new VoteType();
    /**
     * ��Ʊ����
     * @param familymembers ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪȨ��
     * @param familymemberVoteCount ���ڶ�ͶƱ�������м��㣬keyΪͶƱ�ˣ�valueΪͶƱ����
     * @param familymemberRealVotes ʵ��ͶƱ����
     * @param Dishes �����������Dish
     * @param voteType ͶƱ����
     */
    public DinnerStatisticsStrategy(Map<Voter, Double> familymembers,
                                  Map<Voter,Integer>familymemberVoteCount,
                                  Map<RealNameVote<Dish>,Boolean> familymemberRealVotes,
                                  Map<Dish,Integer> Dishes,
                                  VoteType voteType
    ) {
        this.familymembers = new HashMap<Voter, Double>(familymembers);
        this.familymemberVoteCount = new HashMap<Voter,Integer>(familymemberVoteCount);
        this.familymemberRealVotes = new HashMap<RealNameVote<Dish>,Boolean> (familymemberRealVotes);
        this.Dishes = new HashMap<Dish,Integer>(Dishes);

        Map<String, Integer> options = new HashMap<String, Integer>(voteType.getVoteType());
        for(Map.Entry<String, Integer> entry : options.entrySet())
        {
            this.voteType.addType(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void Statistics() {
        //ͶƱ����ֻ��Ϊ1�Σ�ɾȥ����
        for(Map.Entry<Voter,Integer> entry : this.familymemberVoteCount.entrySet())
        {
            if(entry.getValue() != 1)
                this.familymembers.remove(entry.getKey());
        }

        //����ʣ�µ�ѡƱ
        for(Iterator<Map.Entry<RealNameVote<Dish>, Boolean>> it = familymemberRealVotes.entrySet().iterator();it.hasNext();)
        {
            Map.Entry<RealNameVote<Dish>, Boolean> entry = it.next();
            //��ЧƱ
            boolean flag = true;
            //ȷ���Ƿ�Ϸ�Ʊ
            if(entry.getValue() == true) {
                if(!familymembers.containsKey(entry.getKey().getVoter())) {
                    flag = false;
                    break;
                }
            }
            else
                flag = false;
            if(!flag)
                it.remove();
        }

        //�˴������кϷ�ѡƱ��ͳ�ƻ��֧��Ʊ����������ÿ���˱���
        for(Map.Entry<Dish, Integer> in1 : Dishes.entrySet()) {
            for(Map.Entry<RealNameVote<Dish>, Boolean> in2 : familymemberRealVotes.entrySet()) {
                //��ÿ��Ʊ��ѡ����ͬһ���˵����ֲ��ӽ�ȥ
                for(VoteItem<Dish> iter : in2.getKey().getvoteItems())
                {
                    if(iter.getCandidate().equals(in1.getKey()))
                    {
                        Dishes.put(in1.getKey(), in1.getValue() + voteType.getScoreByOption(iter.getVoteValue()));
                    }
                }
            }
        }
    }

    /**
     * ������Ĳ�Ʒ����
     *
     * @return
     */
    public HashMap<Dish, Integer> getDishes()
    {
        return new HashMap<Dish, Integer>(Dishes);
    }
}
