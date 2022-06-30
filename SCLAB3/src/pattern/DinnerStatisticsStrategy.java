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
    //投票人集合，key为投票人，value为权重
    private Map<Voter, Double> familymembers;
    //用于对投票次数进行计算，key为投票人，value为投票次数
    private Map<Voter,Integer> familymemberVoteCount;
    //存放实名投票的集
    private Map<RealNameVote<Dish>,Boolean> familymemberRealVotes;
    //用来存放所有菜
    private Map<Dish,Integer> Dishes;
    //投票类型
    private VoteType voteType = new VoteType();
    /**
     * 计票规则
     * @param familymembers 投票人集合，key为投票人，value为权重
     * @param familymemberVoteCount 用于对投票次数进行计算，key为投票人，value为投票次数
     * @param familymemberRealVotes 实名投票集合
     * @param Dishes 用来存放所有Dish
     * @param voteType 投票类型
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
        //投票次数只能为1次，删去其他
        for(Map.Entry<Voter,Integer> entry : this.familymemberVoteCount.entrySet())
        {
            if(entry.getValue() != 1)
                this.familymembers.remove(entry.getKey());
        }

        //挑出剩下的选票
        for(Iterator<Map.Entry<RealNameVote<Dish>, Boolean>> it = familymemberRealVotes.entrySet().iterator();it.hasNext();)
        {
            Map.Entry<RealNameVote<Dish>, Boolean> entry = it.next();
            //有效票
            boolean flag = true;
            //确定是否合法票
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

        //此处是所有合法选票，统计获得支持票的数量，对每道菜遍历
        for(Map.Entry<Dish, Integer> in1 : Dishes.entrySet()) {
            for(Map.Entry<RealNameVote<Dish>, Boolean> in2 : familymemberRealVotes.entrySet()) {
                //从每张票中选出对同一个菜的评分并加进去
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
     * 获得最后的菜品评分
     *
     * @return
     */
    public HashMap<Dish, Integer> getDishes()
    {
        return new HashMap<Dish, Integer>(Dishes);
    }
}
