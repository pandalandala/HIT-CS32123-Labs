package pattern;

public class BusinessSelection implements SelectionStrategy<Integer>{
	//有效票中股权加和
	private double LegalVotes = 0;
	//有效票中支持票的股权加和
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
