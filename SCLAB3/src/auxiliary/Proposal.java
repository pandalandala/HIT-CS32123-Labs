package auxiliary;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//�����޸ı�����
public class Proposal {
	
	//�᰸���ƺ��᰸����
	private String title;
	private Calendar date;

	public Proposal(String p, Calendar d) {
		this.title = p;
		this.date = d;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" ) ;
		return new String("�����᰸������Ϊ" + title + "�᰸����Ϊ" + sdf.format(date.getTime()));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Proposal))
			return false;
		Proposal other = (Proposal) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}