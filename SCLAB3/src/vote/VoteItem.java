package vote;

import java.util.Objects;

//immutable
public class VoteItem<C> {

	// ��ͶƱ������Եĺ�ѡ����
	private C candidate;
	// �Ժ�ѡ����ľ���ͶƱchoice�����硰֧�֡��������ԡ���
	// ���豣�������ֵ����Ҫʱ���Դ�ͶƱ���VoteType�����в�ѯ�õ�
	private String value;

	// Rep Invariants
	// ѡƱ��Եĺ�ѡ�����ͶƱchoice��ֵ�󲻿ɸ��ģ��Ҳ�Ϊ��
	// Abstract Function
	// ��ͶƱ������Եĺ�ѡ����
	// �Ժ�ѡ����ľ���ͶƱchoice�����硰֧�֡��������ԡ���
	// Safety from Rep Exposure
	// candidate��value����Ϊprivate

	private boolean checkRep() {
		assert candidate != null;
		assert value != null;
		return true;
	}

	/**
	 * ����һ��ͶƱ����� ���磺��Ժ�ѡ������������ͶƱchoice�ǡ�֧�֡�
	 * 
	 * @param candidate ����Եĺ�ѡ����
	 * @param value     ��������ͶƱchoice
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
		checkRep();
	}

	/**
	 * �õ���ͶƱchoice�ľ���ͶƱ��
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return new String(this.value);
	}

	/**
	 * �õ���ͶƱchoice����Ӧ�ĺ�ѡ��
	 * 
	 * @return
	 */
	public C getCandidate() {
		return this.candidate;
	}
	
	/**
	 * �õ���ͶƱchoice����Ӧ�ĺ�ѡ�˵��ַ��������ʽ
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
