package pattern;

public class BusinessSelection implements SelectionStrategy<Integer>{
	//��ЧƱ�й�Ȩ�Ӻ�
	private double LegalVotes = 0;
	//��ЧƱ��֧��Ʊ�Ĺ�Ȩ�Ӻ�
	private double LegalSupportVotes = 0;
	
	public BusinessSelection(double LegalVotes,double LegalSupportVotes) {
		this.LegalSupportVotes = LegalSupportVotes;
		this.LegalVotes = LegalVotes;
	}
	
	@Override
	public Integer select() {
		if(this.LegalSupportVotes*3 > this.LegalVotes*2)
			return new Integer(1);
		else
			return new Integer(0);
	}

}
