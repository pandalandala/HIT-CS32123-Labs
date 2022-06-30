package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//immutable
public class Vote<C> {

	// 缺省为“匿名”投票，即不需要管理投票人的信息

	// 一个投票人对所有候选对象的投票项集合
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// 投票时间
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// 赋值后投票记录和投票时间不可更改,不为空
	// Abstract Function
	// 对所有候选对象的投票记录
	// 投票时间
	// Safety from Rep Exposure
	// voteItem、date为private类型
	// 方法的返回为对应内容的拷贝

	private boolean checkRep() {
		assert voteItems != null;
		assert date != null;
		return false;
	}

	/**
	 * 创建一个选票对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 * 
	 */
	public Vote(Set<VoteItem<C>> voteItems) {
		Set<VoteItem<C>> tempvoteItems = new HashSet<>(voteItems);
		this.voteItems.addAll(tempvoteItems);
	}

	/**
	 * 查询该选票中包含的所有投票项
	 * 
	 * @return 所有投票项
	 */
	public Set<VoteItem<C>> getvoteItems() {
		return new HashSet<VoteItem<C>>(this.voteItems);
	}

	/**
	 * 一个特定候选人是否包含本选票中
	 * 
	 * @param candidate 待查询的候选人
	 * @return 若包含该候选人的投票项，则返回true，否则false
	 */
	public boolean candidateIncluded(C candidate) {
		for(VoteItem<C> iter : this.voteItems)
		{
			if(iter.getCandidate().equals(candidate))
				return true;
		}
		checkRep();
		return false;
	}
	
	/**
	 * 查询该选票的投票时间
	 * 
	 * @return 投票时间
	 */
	public Calendar getCalendar() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.date.get(Calendar.YEAR), 
				this.date.get(Calendar.MONTH), 
				this.date.get(Calendar.DATE), 
				this.date.get(Calendar.HOUR_OF_DAY), 
				this.date.get(Calendar.MINUTE), 
				this.date.get(Calendar.SECOND));
		newDate.setTimeInMillis(this.date.get(Calendar.MILLISECOND));
		return newDate;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(voteItems, date);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		
		if(!(obj instanceof Vote)) return false;

		Vote<C> newVote = (Vote<C>) obj;
		if( newVote.getvoteItems().equals(this.voteItems))
			return true;
		return false;
	}
}
