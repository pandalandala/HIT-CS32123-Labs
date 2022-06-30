package pattern;

import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;

import auxiliary.Person;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionStatisticsStrategy implements StatisticsStrategy<Person>{
    //所有选票和出现次数
    private Map<Vote<Person>,Integer> PersonsVotes;
    //所有候选人
    private Map<Person,Integer> Candidates;
    //投票类型
    private VoteType voteType = new VoteType();

    /**
     * 的计票规则
     *
     * @param PersonsVotes 所有选票和出现次数
     * @param Candidates 所有候选人
     * @param voteType 投票类型
     */
    public ElectionStatisticsStrategy(Map<Vote<Person>,Integer> PersonsVotes,
                                    Map<Person,Integer> Candidates,
                                    VoteType voteType) {
        this.PersonsVotes = new HashMap<Vote<Person>,Integer>(PersonsVotes);
        this.Candidates = new HashMap<Person,Integer>(Candidates);

        Map<String, Integer> options = new HashMap<String, Integer>(voteType.getVoteType());
        for(Map.Entry<String, Integer> entry : options.entrySet())
        {
            this.voteType.addType(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void Statistics() {
        //候选人遍历
        for(Entry<Person, Integer> in1 : Candidates.entrySet())
        {
            for(Entry<Vote<Person>, Integer> in2 : PersonsVotes.entrySet())
            {
                //从每张票中选出对这个人的评分并加进去
                for(VoteItem<Person> iter : in2.getKey().getvoteItems())
                {
                    if(iter.getCandidate().equals(in1.getKey()))
                    {
                        Candidates.put(in1.getKey(), in1.getValue() + voteType.getScoreByOption(iter.getVoteValue()));
                    }
                }
            }
        }
    }

    /**
     * 获得候选人评分列表
     *
     * @return
     */
    public HashMap<Person, Integer> getDishes()
    {
        return new HashMap<Person, Integer>(Candidates);
    }

}
