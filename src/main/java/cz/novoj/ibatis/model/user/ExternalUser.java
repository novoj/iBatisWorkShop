package cz.novoj.ibatis.model.user;

/**
 * An user from outside.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class ExternalUser extends User {
	private static final long serialVersionUID = 4553546146023857338L;
	private String companyName;
	private String companyIdNumber;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyIdNumber() {
		return companyIdNumber;
	}

	public void setCompanyIdNumber(String companyIdNumber) {
		this.companyIdNumber = companyIdNumber;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		if(!super.equals(o)) return false;

		ExternalUser that = (ExternalUser)o;

		if(companyIdNumber != null ? !companyIdNumber.equals(that.companyIdNumber) : that.companyIdNumber != null)
			return false;
		if(companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
		result = 31 * result + (companyIdNumber != null ? companyIdNumber.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ExternalUser");
		sb.append("{companyName='").append(companyName).append('\'');
		sb.append(", companyIdNumber='").append(companyIdNumber).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
