package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//immutable
public class Vote<C> {

	// ȱʡΪ��������ͶƱ��������Ҫ����ͶƱ�˵���Ϣ

	// һ��ͶƱ�˶����к�ѡ�����ͶƱ���
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// ͶƱʱ��
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// ��ֵ��ͶƱ��¼��ͶƱʱ�䲻�ɸ���,��Ϊ��
	// Abstract Function
	// �����к�ѡ�����ͶƱ��¼
	// ͶƱʱ��
	// Safety from Rep Exposure
	// voteItem��dateΪprivate����
	// �����ķ���Ϊ��Ӧ���ݵĿ���

	private boolean checkRep() {
		assert voteItems != null;
		assert date != null;
		return false;
	}

	/**
	 * ����һ��ѡƱ����
	 * 
	 * ����������Ƹ÷��������õĲ���
	 * 
	 */
	public Vote(Set<VoteItem<C>> voteItems) {
		Set<VoteItem<C>> tempvoteItems = new HashSet<>(voteItems);
		this.voteItems.addAll(tempvoteItems);
	}

	/**
	 * ��ѯ��ѡƱ�а���������ͶƱ��
	 * 
	 * @return ����ͶƱ��
	 */
	public Set<VoteItem<C>> getvoteItems() {
		return new HashSet<VoteItem<C>>(this.voteItems);
	}

	/**
	 * һ���ض���ѡ���Ƿ������ѡƱ��
	 * 
	 * @param candidate ����ѯ�ĺ�ѡ��
	 * @return �������ú�ѡ�˵�ͶƱ��򷵻�true������false
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
	 * ��ѯ��ѡƱ��ͶƱʱ��
	 * 
	 * @return ͶƱʱ��
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
