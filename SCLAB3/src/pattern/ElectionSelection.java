package pattern;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import auxiliary.Person;

public class ElectionSelection implements SelectionStrategy<List<Person>>{
	//���к�ѡ��
	private Map<Person,Integer> Candidates;
	//Ҫѡ���ĺ�ѡ������
	private int number = 0;

	public ElectionSelection(Map<Person,Integer> Candidates,int number)
	{
		this.Candidates = new HashMap<Person,Integer> (Candidates);
		this.number = number;
	}

	@Override
	public List<Person> select() {
		if(number >= Candidates.size())
		{
			List<Person> DishList = new LinkedList<Person>();
			DishList.addAll(Candidates.keySet());
			return DishList;
		}
		else
		{
			//��������
			Map.Entry<Person,Integer> temp;
			List<Map.Entry<Person,Integer>> list = new LinkedList<Map.Entry<Person,Integer>>(Candidates.entrySet());
			for(int i = list.size()-1 ; i > 0 ; i--)
			{
				for(int j = 0; j < i; j++)
				{
					if(list.get(j).getValue().intValue() < list.get(j+1).getValue().intValue())
					{
						temp = list.get(j);
						list.set(j,list.get(j+1));
						list.set(j+1,temp);
					}
				}
			}
			//ͬƱ�������
			while(list.get(number).getValue() == list.get(number - 1).getValue())
			{
				number--;
				if(number == 1) break;
			}
			
			if(number == 1 )
			{
				if(list.get(1).getValue() == list.get(0).getValue())
					return null;
			}
			
			//���غ�ѡ���б�
			List<Person> CandidateList = new LinkedList<Person>();
			for(int i = 0; i < number;i++)
			{
				CandidateList.add(list.get(i).getKey());
			}
			return CandidateList;
		}
	}
	
	
}
