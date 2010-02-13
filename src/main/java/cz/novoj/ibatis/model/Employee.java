package cz.novoj.ibatis.model;

/**
 * User that is employee of our company.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class Employee extends User {
	private static final long serialVersionUID = 804507049822494757L;
	private String employeeNumber;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "RedundantIfStatement"})
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		if(!super.equals(o)) return false;

		Employee employee = (Employee)o;

		if(employeeNumber != null ? !employeeNumber.equals(employee.employeeNumber) : employee.employeeNumber != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (employeeNumber != null ? employeeNumber.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Employee");
		sb.append("{employeeNumber='").append(employeeNumber).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
