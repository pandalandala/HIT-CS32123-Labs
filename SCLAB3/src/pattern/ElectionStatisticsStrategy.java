package pattern;

import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;

import auxiliary.Person;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionStatisticsStrategy implements StatisticsStrategy<Person>{
    //����ѡƱ�ͳ��ִ���
    private Map<Vote<Person>,Integer> PersonsVotes;
    //���к�ѡ��
    private Map<Person,Integer> Candidates;
    //ͶƱ����
    private VoteType voteType = new VoteType();

    /**
     * �ļ�Ʊ����
     *
     * @param PersonsVotes ����ѡƱ�ͳ��ִ���
     * @param Candidates ���к�ѡ��
     * @param voteType ͶƱ����
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
        //��ѡ�˱���
        for(Entry<Person, Integer> in1 : Candidates.entrySet())
        {
            for(Entry<Vote<Person>, Integer> in2 : PersonsVotes.entrySet())
            {
                //��ÿ��Ʊ��ѡ��������˵����ֲ��ӽ�ȥ
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
     * ��ú�ѡ�������б�
     *
     * @return
     */
    public HashMap<Person, Integer> getDishes()
    {
        return new HashMap<Person, Integer>(Candidates);
    }

}
