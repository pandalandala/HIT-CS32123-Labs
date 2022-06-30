package vote;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//immutable
public class VoteType {

	// key为choice名、value为choice名对应的分数
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// opinion的每一项的value为字符串型，value为整型
	// 每一项的value值不能相同
	// Abstract Function
	// 用来存放choice内容的Map
	// Safety from Rep Exposure
	// 将opinion设置为private

	private boolean checkRep() {
		for(Map.Entry<String, Integer> entry : this.options.entrySet())
		{
			assert this.options.containsValue(entry.getValue());
			assert this.options.containsKey(entry.getKey());
		}
		return true;
	}

	/**
	 * 创建一个投票类型对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 */
	public VoteType() {
	}
	

	/**
	 * 根据满足特定语法规则的字符串，创建一个投票类型对象
	 * 
	 * @param regex 遵循特定语法的、包含投票类型信息的字符串（待任务12再考虑）
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
	 * 判断一个投票choice是否合法（用于Poll中对选票的合法性检查）
	 * 
	 * 例如，投票人给出的投票choice是“Strongly reject”，但options中不包含这个choice，因此它是非法的
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票choice
	 * @return 合法则true，否则false
	 */
	public boolean checkLegality(String option) {
		if(this.options.containsKey(option))
			return true;
		return false;
	}

	/**
	 * 根据一个投票choice，得到其对应的分数
	 * 
	 * 例如，投票人给出的投票choice是“支持”，查询得到其对应的分数是1
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票项取值
	 * @return 该choice所对应的分数
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
	 * 向投票类型中添加一个投票choice
	 * 
	 * 如果输入的choice与分数都不重复，则添加一个新choice
	 * 如果输入的choice的名称在已有choice中有重复
	 * 不允许输入的choice的值在已有choice中有重复
	 * 
	 * @param optionValue choice对应的分数,不能和已有choice重复
	 * @param optionKey choice对应名,不能和已有的choice重复
	 * @return 添加成功则返回true，失败则返回false并打印失败信息
	 */
	public boolean addType(String optionKey, int optionValue)
	{
		if(this.options.containsValue(optionValue)
				|| this.options.containsKey(optionKey))
			return false;//不允许相同的choice名或choice值
		this.options.put(optionKey, optionValue);
		checkRep();
		return true;
	}
	
	/**
	 * 获得投票的choice集
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
