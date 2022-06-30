package vote;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//immutable
public class VoteType {

	// keyΪchoice����valueΪchoice����Ӧ�ķ���
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// opinion��ÿһ���valueΪ�ַ����ͣ�valueΪ����
	// ÿһ���valueֵ������ͬ
	// Abstract Function
	// �������choice���ݵ�Map
	// Safety from Rep Exposure
	// ��opinion����Ϊprivate

	private boolean checkRep() {
		for(Map.Entry<String, Integer> entry : this.options.entrySet())
		{
			assert this.options.containsValue(entry.getValue());
			assert this.options.containsKey(entry.getKey());
		}
		return true;
	}

	/**
	 * ����һ��ͶƱ���Ͷ���
	 * 
	 * ����������Ƹ÷��������õĲ���
	 */
	public VoteType() {
	}
	

	/**
	 * ���������ض��﷨������ַ���������һ��ͶƱ���Ͷ���
	 * 
	 * @param regex ��ѭ�ض��﷨�ġ�����ͶƱ������Ϣ���ַ�����������12�ٿ��ǣ�
	 */
	public VoteType(String regex) {
		String[] x = regex.split("|");
		boolean flag = true;
		boolean flag1 = regex.matches("\"[0-9,a-z,A-Z]{1,5}\"");
		boolean flag2 = regex.matches("\"[0-9,a-z,A-Z]{1,5}\"([0-9]+)");
		boolean flag3 = regex.matches("\"[0-9,a-z,A-Z]{1,5}\"(-[0-9]+)");
		for(String s : x)
		{
			if(!s.matches("\"[0-9,a-z,A-Z]{1,5}\""))
				flag = false;
		}
		if(flag)
		{
			for(int i = 0; i < x.length; i++)
				this.options.put(x[i], 1);
		}
	}

	/**
	 * �ж�һ��ͶƱchoice�Ƿ�Ϸ�������Poll�ж�ѡƱ�ĺϷ��Լ�飩
	 * 
	 * ���磬ͶƱ�˸�����ͶƱchoice�ǡ�Strongly reject������options�в��������choice��������ǷǷ���
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱchoice
	 * @return �Ϸ���true������false
	 */
	public boolean checkLegality(String option) {
		if(this.options.containsKey(option))
			return true;
		return false;
	}

	/**
	 * ����һ��ͶƱchoice���õ����Ӧ�ķ���
	 * 
	 * ���磬ͶƱ�˸�����ͶƱchoice�ǡ�֧�֡�����ѯ�õ����Ӧ�ķ�����1
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱ��ȡֵ
	 * @return ��choice����Ӧ�ķ���
	 */
	public int getScoreByOption(String option) {
		for(Map.Entry<String, Integer> entry : this.options.entrySet())
		{
			if(entry.getKey().equals(option))
				return entry.getValue();
		}
		return 0;
	}

	/**
	 * ��ͶƱ���������һ��ͶƱchoice
	 * 
	 * ��������choice����������ظ��������һ����choice
	 * ��������choice������������choice�����ظ�
	 * �����������choice��ֵ������choice�����ظ�
	 * 
	 * @param optionValue choice��Ӧ�ķ���,���ܺ�����choice�ظ�
	 * @param optionKey choice��Ӧ��,���ܺ����е�choice�ظ�
	 * @return ��ӳɹ��򷵻�true��ʧ���򷵻�false����ӡʧ����Ϣ
	 */
	public boolean addType(String optionKey, int optionValue)
	{
		if(this.options.containsValue(optionValue)
				|| this.options.containsKey(optionKey))
			return false;//��������ͬ��choice����choiceֵ
		this.options.put(optionKey, optionValue);
		checkRep();
		return true;
	}
	
	/**
	 * ���ͶƱ��choice��
	 * 
	 * @return
	 */
	public Map<String, Integer> getVoteType()
	{
		return new HashMap<String, Integer>(this.options);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(this.options);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		
		if(!(obj instanceof VoteType)) return false;
		
		VoteType votetype = (VoteType) obj;
		
		if(votetype.options.size() == this.options.size())
		{
			for(Map.Entry<String, Integer> entry : votetype.options.entrySet())
			{
				boolean flag = false;
				for(Map.Entry<String, Integer> in2 : this.options.entrySet())
				{
					if(entry.getKey().equals(in2.getKey())
							&& entry.getValue().equals(in2.getValue()))
						flag = true;
				}
				if(!flag) return false;
			}
			return true;
		}
		return false;
	}
}
