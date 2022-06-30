package vote;

import java.util.Objects;

//immutable
public class VoteItem<C> {

	// 本投票项所针对的候选对象
	private C candidate;
	// 对候选对象的具体投票choice，例如“支持”、“反对”等
	// 无需保存具体数值，需要时可以从投票活动的VoteType对象中查询得到
	private String value;

	// Rep Invariants
	// 选票针对的候选对象和投票choice赋值后不可更改，且不为空
	// Abstract Function
	// 本投票项所针对的候选对象
	// 对候选对象的具体投票choice，例如“支持”、“反对”等
	// Safety from Rep Exposure
	// candidate和value设置为private

	private boolean checkRep() {
		assert candidate != null;
		assert value != null;
		return true;
	}

	/**
	 * 创建一个投票项对象 例如：针对候选对象“张三”，投票choice是“支持”
	 * 
	 * @param candidate 所针对的候选对象
	 * @param value     所给出的投票choice
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
		checkRep();
	}

	/**
	 * 得到该投票choice的具体投票项
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return new String(this.value);
	}

	/**
	 * 得到该投票choice所对应的候选人
	 * 
	 * @return
	 */
	public C getCandidate() {
		return this.candidate;
	}
	
	/**
	 * 得到该投票choice所对应的候选人的字符串表达形式
	 * 
	 * @return
	 */
	public String getCandidateToString() {
		return this.candidate.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(candidate, value);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		
		if(!(obj instanceof VoteItem)) return false;

		VoteItem<C> voteitem = (VoteItem<C>) obj;
		if(voteitem.getCandidate().equals(this.getCandidate())
				&& voteitem.getVoteValue().equals(this.getVoteValue()))
			return true;
		return false;
	}
}
